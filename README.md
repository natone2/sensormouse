# 🖱️ SensorMouse

**Controla tu PC con el móvil usando el giroscopio**

[![License: GPL-3.0](https://img.shields.io/badge/License-GPL%203.0-blue.svg)](https://opensource.org/licenses/GPL-3.0)
[![Platform: Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Language: Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)

## 📱 ¿Qué es SensorMouse?

SensorMouse es una aplicación Android que convierte tu smartphone en un ratón inalámbrico usando el giroscopio y acelerómetro. Perfecta para presentaciones, gaming, o control remoto de tu PC.

### ✨ Características

- 🎯 **Control preciso** con giroscopio y acelerómetro
- 🔧 **Calibración personalizable** para mayor precisión
- ⚡ **Baja latencia** con conexión WiFi directa
- 🎮 **Botones de ratón** (izquierdo, derecho, central)
- 📊 **Sensibilidad ajustable** (0.1x - 5.0x)
- 🌙 **Temas personalizables** (versión Pro)
- 📈 **Estadísticas de uso** (versión Pro)

## 🚀 Versiones

### 📦 Versión Gratuita (30 días de prueba)
- ✅ Control completo del ratón
- ✅ Calibración básica
- ✅ Sensibilidad estándar (1.0x)
- ✅ Sin límites de uso durante la prueba

### 💎 Versión Pro (€3.99 - Pago único)
- ✅ Uso ilimitado (sin límite de 30 días)
- ✅ Sensibilidad avanzada (0.1x - 5.0x)
- ✅ Calibración avanzada con múltiples puntos
- ✅ Múltiples perfiles guardados
- ✅ Sin publicidad
- ✅ Temas personalizados
- ✅ Estadísticas de uso
- ✅ Backup en la nube

## 🛠️ Instalación

### Para usuarios finales:
1. Descarga desde [Google Play Store](https://play.google.com/store/apps/details?id=com.sensormouse.android)
2. Instala el servidor Python en tu PC
3. Conecta ambos dispositivos a la misma red WiFi
4. ¡Disfruta!

📖 **[Ver guía completa de uso](docs/USAGE_GUIDE.md)** con capturas de pantalla y pasos detallados

### Para desarrolladores:

```bash
# Clonar el repositorio
git clone https://github.com/natone2/sensormouse.git
cd sensormouse

# Configurar Android Studio
# Abrir android-app/ en Android Studio
# Sincronizar proyecto y compilar
```

## 🖥️ Servidor Python

El servidor Python debe ejecutarse en tu PC:

```bash
cd server
python -m venv venv
source venv/bin/activate  # En Windows: venv\Scripts\activate
pip install -r requirements.txt
python main.py
```

🖥️ **[Ver capturas del servidor](docs/images/server_screenshots.md)** - Ejemplos de terminal y configuración

## 📸 Capturas de Pantalla

📱 **[Capturas de la app](docs/images/app_screenshots.md)** - Interfaz de usuario y estados
🔧 **[Diagramas de instalación](docs/images/installation_diagram.md)** - Flujos paso a paso

## 📁 Estructura del Proyecto

```