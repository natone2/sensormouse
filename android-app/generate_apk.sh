#!/bin/bash

# Script simple para generar APK de DroidMouse
echo "🐭 Generando APK de DroidMouse..."

# Verificar que estamos en el directorio correcto
if [ ! -f "build.gradle" ]; then
    echo "❌ Error: No se encontró build.gradle"
    echo "Ejecuta este script desde el directorio android-app"
    exit 1
fi

# Limpiar build anterior
echo "🧹 Limpiando build anterior..."
./gradlew clean

# Generar APK de debug
echo "🔨 Generando APK de debug..."
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo "✅ APK de debug generado exitosamente!"
    echo "📱 Ubicación: app/build/outputs/apk/debug/app-debug.apk"
    
    # Crear directorio de releases si no existe
    mkdir -p ../releases
    
    # Copiar APK a releases
    cp app/build/outputs/apk/debug/app-debug.apk ../releases/droidmouse-debug.apk
    
    echo "📦 APK copiado a: ../releases/droidmouse-debug.apk"
    echo ""
    echo "🎉 ¡APK listo para instalar!"
    echo "📋 Instrucciones:"
    echo "1. Transfiere el APK a tu dispositivo Android"
    echo "2. Habilita 'Instalar aplicaciones de fuentes desconocidas'"
    echo "3. Instala el APK"
    echo "4. Ejecuta el servidor Python en tu PC"
    echo "5. Conecta desde la app usando la IP del servidor"
else
    echo "❌ Error generando APK"
    exit 1
fi 