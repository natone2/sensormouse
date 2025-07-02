# 🖱️ SensorMouse

**Controla tu PC con el móvil usando el giroscopio**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
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

## 🚀 Modelo de Negocio

Este proyecto es **código abierto** pero **monetizado**:

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

## 📁 Estructura del Proyecto

```
sensormouse/
├── android-app/          # Aplicación Android (Kotlin)
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── java/com/sensormouse/android/
│   │   │   │   ├── billing/          # Sistema de pagos
│   │   │   │   ├── premium/          # Gestión de funcionalidades Pro
│   │   │   │   ├── MainActivity.kt   # Actividad principal
│   │   │   │   └── WelcomeActivity.kt
│   │   │   └── res/                  # Recursos (layouts, drawables, etc.)
│   │   └── build.gradle
├── server/               # Servidor Python
│   ├── main.py          # Servidor principal
│   ├── utils.py         # Utilidades
│   └── requirements.txt
├── docs/                # Documentación
├── scripts/             # Scripts de instalación
└── README.md
```

## 💰 Monetización

### ¿Por qué monetizar código abierto?

1. **Transparencia**: Los usuarios pueden ver exactamente qué hace la app
2. **Confianza**: Código revisable = mayor confianza
3. **Comunidad**: Desarrolladores pueden contribuir y mejorar
4. **Sostenibilidad**: Permite seguir desarrollando y manteniendo

### Estrategia de precios:
- **€3.99** - Precio psicológico atractivo
- **Pago único** - Sin suscripciones molestas
- **30 días gratis** - Tiempo suficiente para valorar la app

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

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

**Nota**: Aunque el código es open source, la distribución de APKs compilados está restringida. Solo el desarrollador original puede distribuir builds oficiales.

## 🔗 Enlaces

- 📱 [Google Play Store](https://play.google.com/store/apps/details?id=com.sensormouse.android)
- 🐛 [Reportar un bug](https://github.com/natone2/sensormouse/issues)
- 💡 [Solicitar feature](https://github.com/natone2/sensormouse/issues)
- 📧 [Contacto](mailto:alex@example.com)

## ⭐ Agradecimientos

- Comunidad Android por las librerías
- Google Play Billing por el sistema de pagos
- Contribuidores del proyecto

---

**¿Te gusta SensorMouse? ¡Dale una ⭐ al repositorio!** 