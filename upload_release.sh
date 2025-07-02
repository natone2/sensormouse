#!/bin/bash

# Script para crear un release en GitHub y subir el APK
# Uso: ./upload_release.sh [VERSION] [GITHUB_TOKEN]

set -e

# Configuración
REPO="natone2/sensormouse"
APK_FILE="releases/sensormouse-debug.apk"
VERSION=${1:-"v1.0.0"}
GITHUB_TOKEN=${2:-""}

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}🚀 Subiendo SensorMouse APK a GitHub Releases...${NC}"

# Verificar que el APK existe
if [ ! -f "$APK_FILE" ]; then
    echo -e "${RED}❌ Error: No se encontró el archivo APK en $APK_FILE${NC}"
    exit 1
fi

# Verificar token
if [ -z "$GITHUB_TOKEN" ]; then
    echo -e "${YELLOW}⚠️  No se proporcionó token de GitHub.${NC}"
    echo -e "${YELLOW}Para crear un release automáticamente, necesitas:${NC}"
    echo -e "${YELLOW}1. Ir a GitHub.com → Settings → Developer settings → Personal access tokens${NC}"
    echo -e "${YELLOW}2. Crear un token con permisos 'repo'${NC}"
    echo -e "${YELLOW}3. Ejecutar: ./upload_release.sh $VERSION TU_TOKEN${NC}"
    echo ""
    echo -e "${GREEN}📋 Alternativa manual:${NC}"
    echo -e "${GREEN}1. Ve a https://github.com/$REPO/releases${NC}"
    echo -e "${GREEN}2. Haz clic en 'Create a new release'${NC}"
    echo -e "${GREEN}3. Sube manualmente el archivo: $APK_FILE${NC}"
    exit 0
fi

echo -e "${GREEN}✅ APK encontrado: $APK_FILE${NC}"
echo -e "${GREEN}📦 Versión: $VERSION${NC}"

# Crear release usando la API de GitHub
echo -e "${YELLOW}📝 Creando release en GitHub...${NC}"

RELEASE_RESPONSE=$(curl -s -X POST \
    -H "Authorization: token $GITHUB_TOKEN" \
    -H "Accept: application/vnd.github.v3+json" \
    "https://api.github.com/repos/$REPO/releases" \
    -d "{
        \"tag_name\": \"$VERSION\",
        \"name\": \"SensorMouse $VERSION\",
        \"body\": \"## 🐭 SensorMouse $VERSION\\n\\n### 📱 Aplicación Android\\n- Control de ratón virtual usando giroscopio\\n- Filtro de Kalman para movimiento suave\\n- Calibración dinámica\\n- Interfaz moderna y responsive\\n\\n### 🚀 Instalación\\n1. Descarga el APK\\n2. Habilita 'Fuentes desconocidas' en Android\\n3. Instala y ejecuta\\n4. Conecta al servidor Python\\n\\n### 🔧 Requisitos\\n- Android 6.0+\\n- Giroscopio y acelerómetro\\n- Servidor Python ejecutándose\\n\\n### 📊 Características\\n- Baja latencia (<50ms)\\n- Protocolo TCP/IP ligero\\n- Multiplataforma (Linux/Windows/macOS)\\n- Código abierto GPLv3\",
        \"draft\": false,
        \"prerelease\": false
    }")

# Extraer el ID del release
RELEASE_ID=$(echo "$RELEASE_RESPONSE" | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)

if [ -z "$RELEASE_ID" ]; then
    echo -e "${RED}❌ Error al crear el release:${NC}"
    echo "$RELEASE_RESPONSE"
    exit 1
fi

echo -e "${GREEN}✅ Release creado con ID: $RELEASE_ID${NC}"

# Subir el APK al release
echo -e "${YELLOW}📤 Subiendo APK...${NC}"

UPLOAD_RESPONSE=$(curl -s -X POST \
    -H "Authorization: token $GITHUB_TOKEN" \
    -H "Accept: application/vnd.github.v3+json" \
    -H "Content-Type: application/vnd.android.package-archive" \
    --data-binary "@$APK_FILE" \
    "https://uploads.github.com/repos/$REPO/releases/$RELEASE_ID/assets?name=sensormouse-$VERSION.apk")

# Verificar si la subida fue exitosa
if echo "$UPLOAD_RESPONSE" | grep -q '"id"'; then
    echo -e "${GREEN}✅ APK subido exitosamente!${NC}"
    echo -e "${GREEN}🔗 Release: https://github.com/$REPO/releases/tag/$VERSION${NC}"
    echo -e "${GREEN}📱 APK: https://github.com/$REPO/releases/download/$VERSION/sensormouse-$VERSION.apk${NC}"
else
    echo -e "${RED}❌ Error al subir el APK:${NC}"
    echo "$UPLOAD_RESPONSE"
    exit 1
fi

echo -e "${GREEN}🎉 ¡Release creado y APK subido exitosamente!${NC}" 