# 🖥️ Capturas del Servidor - SensorMouse

## Terminal del Servidor

### Iniciando el servidor:
```bash
$ cd sensormouse/server
$ python main.py

🚀 Servidor SensorMouse iniciado
📡 Escuchando en puerto 5000
🌐 IP del servidor: 192.168.1.100
✅ Listo para conexiones
⏳ Esperando dispositivos...
```

### Cliente conectado:
```bash
🚀 Servidor SensorMouse iniciado
📡 Escuchando en puerto 5000
🌐 IP del servidor: 192.168.1.100
✅ Listo para conexiones
⏳ Esperando dispositivos...

📱 Dispositivo conectado: 192.168.1.50
🖱️ Movimiento detectado: x=15, y=-8
🖱️ Clic izquierdo
🖱️ Movimiento detectado: x=3, y=12
🖱️ Movimiento detectado: x=-5, y=2
🖱️ Clic derecho
```

### Múltiples conexiones:
```bash
🚀 Servidor SensorMouse iniciado
📡 Escuchando en puerto 5000
🌐 IP del servidor: 192.168.1.100
✅ Listo para conexiones
⏳ Esperando dispositivos...

📱 Dispositivo conectado: 192.168.1.50 (Alex)
📱 Dispositivo conectado: 192.168.1.51 (Maria)
🖱️ [Alex] Movimiento: x=10, y=-5
🖱️ [Maria] Clic izquierdo
🖱️ [Alex] Movimiento: x=2, y=8
📱 Dispositivo desconectado: 192.168.1.51 (Maria)
```

### Logs detallados:
```bash
🚀 Servidor SensorMouse iniciado
📡 Escuchando en puerto 5000
🌐 IP del servidor: 192.168.1.100
✅ Listo para conexiones
⏳ Esperando dispositivos...

[2024-01-15 14:30:15] 📱 Nueva conexión desde 192.168.1.50
[2024-01-15 14:30:16] 🔧 Calibración iniciada
[2024-01-15 14:30:19] ✅ Calibración completada
[2024-01-15 14:30:20] 🖱️ Movimiento: x=5, y=-3 (sensibilidad: 1.0x)
[2024-01-15 14:30:21] 🖱️ Clic izquierdo
[2024-01-15 14:30:22] 🖱️ Movimiento: x=-2, y=7 (sensibilidad: 1.0x)
[2024-01-15 14:30:23] 🖱️ Clic derecho
[2024-01-15 14:30:25] 📱 Desconexión: 192.168.1.50
```

## Interfaz Web (si se implementa)

### Dashboard del servidor:
```
┌─────────────────────────────────────┐
│ 🖥️ SensorMouse Server Dashboard     │
├─────────────────────────────────────┤
│                                     │
│  Estado: Activo ✅                  │
│  Puerto: 5000                       │
│  IP: 192.168.1.100                  │
│                                     │
│  Dispositivos conectados: 1         │
│  • 192.168.1.50 (Alex) - Activo    │
│                                     │
│  Estadísticas:                      │
│  • Movimientos: 1,247               │
│  • Clics: 89                        │
│  • Tiempo activo: 2h 15m            │
│                                     │
│  [Reiniciar] [Configurar] [Logs]    │
│                                     │
└─────────────────────────────────────┘
```

### Logs en tiempo real:
```
┌─────────────────────────────────────┐
│ 📋 Logs en Tiempo Real              │
├─────────────────────────────────────┤
│                                     │
│ [14:30:15] 📱 Conexión: 192.168.1.50│
│ [14:30:16] 🔧 Calibración iniciada  │
│ [14:30:19] ✅ Calibración completada│
│ [14:30:20] 🖱️ Movimiento: x=5, y=-3 │
│ [14:30:21] 🖱️ Clic izquierdo        │
│ [14:30:22] 🖱️ Movimiento: x=-2, y=7 │
│ [14:30:23] 🖱️ Clic derecho          │
│ [14:30:25] 📱 Desconexión: 192.168.1.50│
│                                     │
│ [Actualizar] [Limpiar] [Exportar]   │
│                                     │
└─────────────────────────────────────┘
```

## Configuración del Servidor

### Archivo de configuración:
```python
# config.py
SERVER_CONFIG = {
    'host': '0.0.0.0',
    'port': 5000,
    'debug': False,
    'max_connections': 5,
    'timeout': 30,
    'log_level': 'INFO'
}

MOUSE_CONFIG = {
    'sensitivity': 1.0,
    'acceleration': True,
    'smoothing': True,
    'deadzone': 0.1
}
```

### Variables de entorno:
```bash
# .env
SENSORMOUSE_HOST=0.0.0.0
SENSORMOUSE_PORT=5000
SENSORMOUSE_DEBUG=false
SENSORMOUSE_LOG_LEVEL=INFO
SENSORMOUSE_MAX_CONNECTIONS=5
```

## Diagrama de Red

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

## Monitoreo del Sistema

### Uso de recursos:
```bash
$ htop

CPU: 2.3% | Memoria: 45MB | Red: 1.2KB/s
Proceso: python main.py
```

### Puertos abiertos:
```bash
$ netstat -tulpn | grep 5000

tcp        0      0 0.0.0.0:5000    0.0.0.0:*       LISTEN      12345/python
```

### Logs del sistema:
```bash
$ journalctl -u sensormouse -f

Jan 15 14:30:15 pc sensormouse[12345]: Servidor iniciado
Jan 15 14:30:15 pc sensormouse[12345]: Escuchando en puerto 5000
Jan 15 14:30:20 pc sensormouse[12345]: Cliente conectado: 192.168.1.50
Jan 15 14:30:25 pc sensormouse[12345]: Cliente desconectado: 192.168.1.50
```

## Instalación del Servidor

### Instalación automática:
```bash
$ curl -sSL https://raw.githubusercontent.com/natone2/sensormouse/main/scripts/install.sh | bash

🚀 Instalando SensorMouse Server...
📦 Descargando dependencias...
🔧 Configurando entorno virtual...
✅ Instalación completada
🎯 Para iniciar: cd sensormouse/server && python main.py
```

### Instalación manual:
```bash
$ git clone https://github.com/natone2/sensormouse.git
$ cd sensormouse/server
$ python -m venv venv
$ source venv/bin/activate
$ pip install -r requirements.txt
$ python main.py
```

## Troubleshooting del Servidor

### Error: Puerto ocupado
```bash
$ python main.py

❌ Error: Puerto 5000 ya está en uso
💡 Solución: Cambia el puerto o mata el proceso

$ lsof -i :5000
$ kill -9 12345
$ python main.py
```

### Error: Permisos insuficientes
```bash
$ python main.py

❌ Error: Permisos insuficientes para controlar el ratón
💡 Solución: Ejecuta con sudo o configura permisos

$ sudo python main.py
```

### Error: Dependencias faltantes
```bash
$ python main.py

❌ Error: Módulo 'flask' no encontrado
💡 Solución: Instala las dependencias

$ pip install -r requirements.txt
``` 