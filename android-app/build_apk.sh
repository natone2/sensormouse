#!/bin/bash

# Script para generar el APK de DroidMouse
# Compatible con Linux, macOS y Windows (WSL)

set -e

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para imprimir mensajes
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Función para verificar Java
check_java() {
    if command -v java &> /dev/null; then
        local version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
        print_success "Java encontrado: $version"
        return 0
    else
        print_error "Java no encontrado"
        print_status "Por favor, instala Java 11 o superior"
        return 1
    fi
}

# Función para verificar Android SDK
check_android_sdk() {
    if [ -n "$ANDROID_HOME" ]; then
        print_success "ANDROID_HOME configurado: $ANDROID_HOME"
        return 0
    elif [ -d "$HOME/Android/Sdk" ]; then
        export ANDROID_HOME="$HOME/Android/Sdk"
        print_success "ANDROID_HOME detectado: $ANDROID_HOME"
        return 0
    else
        print_warning "ANDROID_HOME no configurado"
        print_status "Configurando ANDROID_HOME por defecto..."
        export ANDROID_HOME="$HOME/Android/Sdk"
        return 0
    fi
}

# Función para limpiar build anterior
clean_build() {
    print_status "Limpiando build anterior..."
    ./gradlew clean
    print_success "Build limpiado"
}

# Función para generar APK de debug
build_debug_apk() {
    print_status "Generando APK de debug..."
    ./gradlew assembleDebug
    
    if [ $? -eq 0 ]; then
        local apk_path="app/build/outputs/apk/debug/app-debug.apk"
        if [ -f "$apk_path" ]; then
            local size=$(du -h "$apk_path" | cut -f1)
            print_success "APK de debug generado: $apk_path ($size)"
            return 0
        else
            print_error "APK no encontrado en la ubicación esperada"
            return 1
        fi
    else
        print_error "Error generando APK de debug"
        return 1
    fi
}

# Función para generar APK de release
build_release_apk() {
    print_status "Generando APK de release..."
    
    # Crear keystore si no existe
    if [ ! -f "app/release.keystore" ]; then
        print_status "Creando keystore para release..."
        keytool -genkey -v -keystore app/release.keystore \
            -alias droidmouse -keyalg RSA -keysize 2048 \
            -validity 10000 -storepass droidmouse \
            -keypass droidmouse \
            -dname "CN=DroidMouse, OU=Development, O=DroidMouse, L=City, S=State, C=ES"
    fi
    
    ./gradlew assembleRelease
    
    if [ $? -eq 0 ]; then
        local apk_path="app/build/outputs/apk/release/app-release.apk"
        if [ -f "$apk_path" ]; then
            local size=$(du -h "$apk_path" | cut -f1)
            print_success "APK de release generado: $apk_path ($size)"
            return 0
        else
            print_error "APK de release no encontrado"
            return 1
        fi
    else
        print_error "Error generando APK de release"
        return 1
    fi
}

# Función para firmar APK
sign_apk() {
    local apk_path="$1"
    local output_path="${apk_path%.apk}-signed.apk"
    
    print_status "Firmando APK..."
    
    if [ -f "app/release.keystore" ]; then
        jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
            -keystore app/release.keystore \
            -storepass droidmouse \
            -keypass droidmouse \
            "$apk_path" droidmouse
        
        if [ $? -eq 0 ]; then
            print_success "APK firmado: $output_path"
            return 0
        else
            print_error "Error firmando APK"
            return 1
        fi
    else
        print_warning "Keystore no encontrado, APK sin firmar"
        return 0
    fi
}

# Función para optimizar APK
optimize_apk() {
    local apk_path="$1"
    local output_path="${apk_path%.apk}-optimized.apk"
    
    if command -v zipalign &> /dev/null; then
        print_status "Optimizando APK..."
        zipalign -v 4 "$apk_path" "$output_path"
        
        if [ $? -eq 0 ]; then
            local original_size=$(du -h "$apk_path" | cut -f1)
            local optimized_size=$(du -h "$output_path" | cut -f1)
            print_success "APK optimizado: $output_path"
            print_status "Tamaño original: $original_size, Optimizado: $optimized_size"
            return 0
        else
            print_error "Error optimizando APK"
            return 1
        fi
    else
        print_warning "zipalign no encontrado, saltando optimización"
        return 0
    fi
}

# Función para copiar APK al directorio de releases
copy_to_releases() {
    local apk_path="$1"
    local build_type="$2"
    
    mkdir -p ../releases
    local timestamp=$(date +"%Y%m%d_%H%M%S")
    local release_name="droidmouse-${build_type}-${timestamp}.apk"
    local release_path="../releases/$release_name"
    
    cp "$apk_path" "$release_path"
    
    if [ $? -eq 0 ]; then
        local size=$(du -h "$release_path" | cut -f1)
        print_success "APK copiado a releases: $release_path ($size)"
        return 0
    else
        print_error "Error copiando APK a releases"
        return 1
    fi
}

# Función principal
main() {
    echo -e "${BLUE}"
    echo "╔══════════════════════════════════════════════════════════════╗"
    echo "║                  DroidMouse APK Builder                      ║"
    echo "║                Generador de APK v1.0                         ║"
    echo "╚══════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
    
    print_status "Iniciando generación de APK..."
    
    # Verificar que estamos en el directorio correcto
    if [ ! -f "build.gradle" ]; then
        print_error "No se encontró build.gradle"
        print_error "Ejecuta este script desde el directorio android-app"
        exit 1
    fi
    
    # Verificar Java
    if ! check_java; then
        exit 1
    fi
    
    # Verificar Android SDK
    check_android_sdk
    
    # Limpiar build anterior
    clean_build
    
    # Determinar tipo de build
    local build_type="debug"
    if [ "$1" = "release" ]; then
        build_type="release"
    fi
    
    # Generar APK
    local apk_path=""
    if [ "$build_type" = "release" ]; then
        if build_release_apk; then
            apk_path="app/build/outputs/apk/release/app-release.apk"
        else
            exit 1
        fi
    else
        if build_debug_apk; then
            apk_path="app/build/outputs/apk/debug/app-debug.apk"
        else
            exit 1
        fi
    fi
    
    # Firmar APK (solo para release)
    if [ "$build_type" = "release" ]; then
        sign_apk "$apk_path"
    fi
    
    # Optimizar APK
    optimize_apk "$apk_path"
    
    # Copiar a releases
    copy_to_releases "$apk_path" "$build_type"
    
    echo -e "${GREEN}"
    echo "╔══════════════════════════════════════════════════════════════╗"
    echo "║                    APK Generado Exitosamente                 ║"
    echo "╚══════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
    
    print_success "¡APK de DroidMouse generado correctamente!"
    print_status "Tipo de build: $build_type"
    print_status "Ubicación: ../releases/"
    print_status ""
    print_status "Para instalar en tu dispositivo:"
    print_status "1. Transfiere el APK a tu dispositivo Android"
    print_status "2. Habilita 'Instalar aplicaciones de fuentes desconocidas'"
    print_status "3. Instala el APK"
    print_status ""
    print_status "Para usar con el servidor:"
    print_status "1. Ejecuta el servidor Python en tu PC"
    print_status "2. Abre DroidMouse en tu dispositivo"
    print_status "3. Conecta usando la IP del servidor"
}

# Ejecutar función principal
main "$@" 