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

## 📸 Galería Visual

### 📱 Capturas de la App
![Pantalla Principal](docs/images/app/main_screen_disconnected.png)
*Pantalla principal en estado desconectado*

![Pantalla Conectada](docs/images/app/main_screen_connected.png)
*Pantalla principal en estado conectado*

### 🖥️ Servidor en Acción
![Terminal del Servidor](docs/images/server/terminal_connected.png)
*Servidor funcionando con cliente conectado*

### 🔧 Diagramas de Instalación
![Flujo de Instalación](docs/images/diagrams/installation_flow.png)
*Proceso de instalación paso a paso*

### 🎯 Casos de Uso
![Presentaciones](docs/images/use_cases/presentations.png)
*Control de presentaciones desde el móvil*

### 📊 Infografías
![Comparación de Versiones](docs/images/infographics/version_comparison.png)
*Diferencias entre versión gratuita y Pro*

---

📖 **[Ver documentación completa](docs/images/)** - Capturas detalladas y guías visuales

## 📁 Estructura del Proyecto

```
sensormouse/
├── android-app/                    # Aplicación Android (Kotlin)
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── java/com/sensormouse/android/
│   │   │   │   ├── billing/          # Sistema de pagos Google Play
│   │   │   │   ├── premium/          # Gestión de funcionalidades Pro
│   │   │   │   ├── MainActivity.kt   # Actividad principal
│   │   │   │   └── WelcomeActivity.kt # Pantalla de bienvenida
│   │   │   └── res/                  # Recursos (layouts, drawables, etc.)
│   │   └── build.gradle
│   ├── build.gradle
│   ├── gradle.properties
│   └── settings.gradle
├── server/                          # Servidor Python
│   ├── main.py                     # Servidor principal Flask
│   ├── utils.py                    # Utilidades y funciones
│   ├── requirements.txt            # Dependencias Python
│   └── venv/                       # Entorno virtual (no incluido en git)
├── docs/                           # Documentación
│   ├── USAGE_GUIDE.md              # Guía completa de uso
│   ├── INSTALLATION.md             # Instrucciones de instalación
│   ├── TECHNICAL_SPECS.md          # Especificaciones técnicas
│   └── images/                     # Imágenes y capturas de pantalla
│       ├── app/                    # Capturas de la app Android
│       ├── server/                 # Capturas del servidor
│       ├── diagrams/               # Diagramas técnicos
│       ├── use_cases/              # Casos de uso
│       ├── infographics/           # Infografías
│       └── README_GENERATED.md     # Guía para generar imágenes
├── scripts/                        # Scripts de instalación y utilidades
│   └── install.sh                  # Script de instalación automática
├── releases/                       # APKs compilados (no incluido en git)
├── .gitignore                      # Archivos ignorados por git
├── LICENSE                         # Licencia GPL-3.0
├── README.md                       # Este archivo
├── CONTRIBUTING.md                 # Guía para contribuir
├── CHANGELOG.md                    # Historial de cambios
└── pyproject.toml                  # Configuración del proyecto Python
```

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! 

### Cómo contribuir:
1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

### Áreas de mejora:
- 🎨 Mejoras de UI/UX
- 🐛 Corrección de bugs
- 📱 Nuevas funcionalidades
- 🌍 Traducciones
- 📚 Documentación

## 📄 Licencia

Este proyecto está bajo la Licencia GPL-3.0 - ver el archivo [LICENSE](LICENSE) para detalles.

## 🔗 Enlaces

- 📱 [Google Play Store](https://play.google.com/store/apps/details?id=com.sensormouse.android)
- 🐛 [Reportar un bug](https://github.com/natone2/sensormouse/issues)
- 💡 [Solicitar feature](https://github.com/natone2/sensormouse/issues)
- 📧 [Contacto](mailto:me@natone.pro)

## ⭐ Agradecimientos

- Comunidad Android por las librerías
- Google Play Billing por el sistema de pagos
- Contribuidores del proyecto

---

**¿Te gusta SensorMouse? ¡Dale una ⭐ al repositorio!**