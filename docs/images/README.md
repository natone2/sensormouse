# 📸 Documentación Visual - SensorMouse

Esta carpeta contiene toda la documentación visual del proyecto SensorMouse, incluyendo capturas de pantalla simuladas, diagramas de instalación y guías visuales.

## 📁 Archivos Disponibles

### 📱 [Capturas de la App](app_screenshots.md)
- Pantalla principal (conectado/desconectado)
- Pantalla de bienvenida
- Diálogos de configuración
- Diálogo de calibración
- Diálogo de actualización Pro
- Notificaciones de estado
- Diagramas de flujo de uso

### 🖥️ [Capturas del Servidor](server_screenshots.md)
- Terminal del servidor (iniciando, conectado, múltiples conexiones)
- Logs detallados
- Interfaz web (dashboard, logs en tiempo real)
- Configuración del servidor
- Diagrama de red
- Monitoreo del sistema
- Instalación del servidor
- Troubleshooting

### 🔧 [Diagramas de Instalación](installation_diagram.md)
- Flujo de instalación completo
- Diagramas por plataforma (Windows, macOS, Linux)
- Configuración de red
- Flujo de uso
- Solución de problemas
- Arquitectura técnica
- Casos de uso

## 🎯 Casos de Uso Visuales

### 📊 Presentaciones
- Control de PowerPoint/Keynote desde el móvil
- Sensibilidad recomendada: 1.5x - 2.0x
- Ideal para presentaciones profesionales

### 🎮 Gaming
- Control de juegos simples desde el sofá
- Sensibilidad recomendada: 0.8x - 1.2x
- Perfecto para juegos de estrategia

### 🎨 Diseño/Edición
- Control preciso para Photoshop, Illustrator
- Sensibilidad recomendada: 0.3x - 0.8x
- Calibración frecuente recomendada

### 📺 Media Center
- Control de reproductores multimedia
- Sensibilidad recomendada: 1.0x - 1.5x
- Ideal para ver películas/series

## 🔧 Configuración de Red

```
┌─────────────────┐    WiFi    ┌─────────────────┐
│   Smartphone    │◄──────────►│      PC         │
│   192.168.1.50  │            │  192.168.1.100  │
│                 │            │                 │
│ 📱 SensorMouse  │            │ 🖥️ Python       │
│ App             │            │ Server          │
│                 │            │                 │
│ Puerto: 5000    │            │ Puerto: 5000    │
│ Protocolo: HTTP │            │ Protocolo: HTTP │
└─────────────────┘            └─────────────────┘
```

## 📋 Checklist de Instalación

### ✅ Requisitos Previos
- [ ] Smartphone Android con giroscopio y acelerómetro
- [ ] PC/Laptop con Python 3.8+
- [ ] Red WiFi compartida entre ambos dispositivos
- [ ] Android 6.0 (API 23) o superior

### ✅ Instalación del Servidor
- [ ] Clonar repositorio
- [ ] Crear entorno virtual
- [ ] Instalar dependencias
- [ ] Ejecutar servidor
- [ ] Verificar puerto 5000 abierto

### ✅ Instalación de la App
- [ ] Descargar desde Google Play Store
- [ ] Abrir aplicación
- [ ] Configurar IP del servidor
- [ ] Conectar dispositivos
- [ ] Calibrar sensores

### ✅ Configuración
- [ ] Ajustar sensibilidad
- [ ] Probar movimientos
- [ ] Configurar botones
- [ ] Verificar latencia

## 🚨 Solución de Problemas Comunes

### ❌ La app no se conecta
- ✅ Verificar que ambos dispositivos estén en la misma red WiFi
- ✅ Comprobar que el servidor esté ejecutándose
- ✅ Revisar la IP del servidor
- ✅ Verificar que el puerto 5000 esté libre

### ❌ Movimiento impreciso
- ✅ Recalibrar los sensores
- ✅ Ajustar la sensibilidad
- ✅ Evitar interferencias magnéticas
- ✅ Mantener el dispositivo estable

### ❌ Latencia alta
- ✅ Usar red WiFi 5GHz si es posible
- ✅ Cerrar apps innecesarias en el móvil
- ✅ Reducir la distancia entre dispositivos
- ✅ Verificar la velocidad de tu red

## 📞 Soporte Visual

Si necesitas ayuda adicional:

1. **Consulta la documentación**: [docs/](../)
2. **Reporta un bug**: [GitHub Issues](https://github.com/natone2/sensormouse/issues)
3. **Contacta**: [me@natone.pro](mailto:me@natone.pro)

---

**¡Disfruta usando SensorMouse! 🚀** 