#!/bin/bash

# Script para descargar gradle-wrapper.jar
echo "📥 Descargando gradle-wrapper.jar..."

# URL del gradle-wrapper.jar
GRADLE_WRAPPER_URL="https://github.com/gradle/gradle/raw/v8.0.0/gradle/wrapper/gradle-wrapper.jar"

# Crear directorio si no existe
mkdir -p gradle/wrapper

# Descargar el archivo
curl -L -o gradle/wrapper/gradle-wrapper.jar "$GRADLE_WRAPPER_URL"

if [ $? -eq 0 ]; then
    echo "✅ gradle-wrapper.jar descargado exitosamente"
    echo "📁 Ubicación: gradle/wrapper/gradle-wrapper.jar"
else
    echo "❌ Error descargando gradle-wrapper.jar"
    exit 1
fi 