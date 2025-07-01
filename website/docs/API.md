# Documentación de la API - DroidMouse

## Descripción General

La API de DroidMouse proporciona una interfaz programática para controlar y monitorear el sistema de ratón virtual. Está diseñada para desarrolladores que quieren integrar DroidMouse en sus aplicaciones o crear extensiones personalizadas.

## Endpoints Base

### Servidor Principal
```
Host: localhost (por defecto)
Puerto: 8080 (configurable)
Protocolo: TCP/IP
Formato: JSON
```

## Estructura de Mensajes

### Formato General
Todos los mensajes siguen este formato JSON:
```json
{
    "type": "message_type",
    "timestamp": 1640995200.123,
    "data": {},
    "metadata": {}
}
```

## Tipos de Mensajes

### 1. Datos de Sensores (`sensor_data`)

#### Envío desde Cliente
```json
{
    "type": "sensor_data",
    "timestamp": 1640995200.123,
    "data": {
        "gyroscope": {
            "x": 0.12,
            "y": -0.08,
            "z": 0.01
        },
        "accelerometer": {
            "x": 0.05,
            "y": -0.02,
            "z": 9.81
        },
        "magnetometer": {
            "x": 25.3,
            "y": -12.7,
            "z": 45.2
        }
    },
    "metadata": {
        "device_id": "android_001",
        "battery_level": 85,
        "signal_strength": -45
    }
}
```

#### Respuesta del Servidor
```json
{
    "type": "sensor_ack",
    "timestamp": 1640995200.124,
    "data": {
        "status": "processed",
        "latency_ms": 12,
        "cursor_position": {
            "x": 1024,
            "y": 768
        }
    }
}
```

### 2. Comandos de Control (`command`)

#### Conectar/Desconectar
```json
{
    "type": "command",
    "timestamp": 1640995200.123,
    "data": {
        "action": "connect",
        "parameters": {
            "device_name": "Samsung Galaxy S21",
            "sensitivity": 1.0,
            "calibration_mode": "auto"
        }
    }
}
```

#### Configurar Sensibilidad
```json
{
    "type": "command",
    "timestamp": 1640995200.123,
    "data": {
        "action": "set_sensitivity",
        "parameters": {
            "value": 1.5,
            "axis": "all"  // "x", "y", "z", "all"
        }
    }
}
```

#### Calibrar Sensores
```json
{
    "type": "command",
    "timestamp": 1640995200.123,
    "data": {
        "action": "calibrate",
        "parameters": {
            "duration_seconds": 3,
            "sensors": ["gyroscope", "accelerometer"]
        }
    }
}
```

### 3. Estado del Sistema (`status`)

#### Solicitar Estado
```json
{
    "type": "status_request",
    "timestamp": 1640995200.123,
    "data": {
        "include_metrics": true,
        "include_config": true
    }
}
```

#### Respuesta de Estado
```json
{
    "type": "status_response",
    "timestamp": 1640995200.124,
    "data": {
        "server": {
            "version": "1.0.0",
            "uptime_seconds": 3600,
            "active_connections": 1,
            "cpu_usage_percent": 2.5,
            "memory_usage_mb": 45.2
        },
        "clients": [
            {
                "device_id": "android_001",
                "connected_since": 1640991600.000,
                "last_activity": 1640995200.123,
                "status": "active"
            }
        ],
        "configuration": {
            "sensitivity": 1.0,
            "calibration_offset": {
                "gyro_x": 0.001,
                "gyro_y": -0.002,
                "gyro_z": 0.000
            },
            "filters": {
                "kalman_q": 0.001,
                "kalman_r": 0.1,
                "low_pass_alpha": 0.8
            }
        },
        "metrics": {
            "average_latency_ms": 15,
            "packets_per_second": 50,
            "error_rate_percent": 0.01
        }
    }
}
```

## Códigos de Error

### Formato de Error
```json
{
    "type": "error",
    "timestamp": 1640995200.123,
    "data": {
        "code": "E001",
        "message": "Invalid sensor data format",
        "details": {
            "field": "gyroscope.x",
            "expected": "number",
            "received": "string"
        }
    }
}
```

### Códigos de Error Comunes

| Código | Descripción | Solución |
|--------|-------------|----------|
| E001 | Formato de datos inválido | Verificar estructura JSON |
| E002 | Valores fuera de rango | Comprobar límites de sensores |
| E003 | Conexión perdida | Reintentar conexión |
| E004 | Servidor ocupado | Esperar y reintentar |
| E005 | Autenticación fallida | Verificar credenciales |
| E006 | Comando no soportado | Consultar documentación |

## Ejemplos de Implementación

### Cliente Python
```python
import socket
import json
import time

class DroidMouseClient:
    def __init__(self, host='localhost', port=8080):
        self.host = host
        self.port = port
        self.socket = None
        
    def connect(self):
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.connect((self.host, self.port))
        
    def send_sensor_data(self, gyro_x, gyro_y, gyro_z, accel_x, accel_y, accel_z):
        message = {
            "type": "sensor_data",
            "timestamp": time.time(),
            "data": {
                "gyroscope": {"x": gyro_x, "y": gyro_y, "z": gyro_z},
                "accelerometer": {"x": accel_x, "y": accel_y, "z": accel_z}
            }
        }
        self.socket.send(json.dumps(message).encode())
        
    def close(self):
        if self.socket:
            self.socket.close()

# Uso
client = DroidMouseClient()
client.connect()
client.send_sensor_data(0.1, -0.05, 0.0, 0.02, -0.01, 9.81)
client.close()
```

### Cliente JavaScript (Node.js)
```javascript
const net = require('net');

class DroidMouseClient {
    constructor(host = 'localhost', port = 8080) {
        this.host = host;
        this.port = port;
        this.client = null;
    }
    
    connect() {
        return new Promise((resolve, reject) => {
            this.client = new net.Socket();
            this.client.connect(this.port, this.host, () => {
                resolve();
            });
            this.client.on('error', reject);
        });
    }
    
    sendSensorData(gyroX, gyroY, gyroZ, accelX, accelY, accelZ) {
        const message = {
            type: 'sensor_data',
            timestamp: Date.now() / 1000,
            data: {
                gyroscope: { x: gyroX, y: gyroY, z: gyroZ },
                accelerometer: { x: accelX, y: accelY, z: accelZ }
            }
        };
        this.client.write(JSON.stringify(message));
    }
    
    close() {
        if (this.client) {
            this.client.destroy();
        }
    }
}

// Uso
async function main() {
    const client = new DroidMouseClient();
    await client.connect();
    client.sendSensorData(0.1, -0.05, 0.0, 0.02, -0.01, 9.81);
    client.close();
}
```

## Configuración Avanzada

### Parámetros de Conexión
```python
CONNECTION_CONFIG = {
    'timeout': 30,
    'keepalive': True,
    'keepalive_interval': 60,
    'reconnect_attempts': 3,
    'reconnect_delay': 5
}
```

### Configuración de Filtros
```python
FILTER_CONFIG = {
    'kalman': {
        'q': 0.001,  # Ruido del proceso
        'r': 0.1     # Ruido de medición
    },
    'low_pass': {
        'alpha': 0.8,  # Factor de suavizado
        'cutoff_freq': 10  # Frecuencia de corte (Hz)
    },
    'median': {
        'window_size': 5  # Tamaño de ventana
    }
}
```

## Monitoreo y Métricas

### Métricas Disponibles
- **Latencia**: Tiempo de procesamiento
- **Throughput**: Paquetes por segundo
- **Error Rate**: Tasa de errores
- **CPU Usage**: Uso de procesador
- **Memory Usage**: Uso de memoria
- **Network I/O**: Tráfico de red

### Logs Estructurados
```json
{
    "timestamp": "2024-01-01T12:00:00Z",
    "level": "INFO",
    "component": "sensor_processor",
    "message": "Processing sensor data",
    "metrics": {
        "latency_ms": 12,
        "queue_size": 5
    }
}
```

## Seguridad

### Autenticación
```json
{
    "type": "auth",
    "timestamp": 1640995200.123,
    "data": {
        "method": "token",
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "expires": 1641078000
    }
}
```

### Encriptación (Opcional)
- **TLS/SSL**: Soporte para conexiones seguras
- **Certificados**: Auto-generados o personalizados
- **Puerto seguro**: 8443 (configurable)

---

**Para más información**, consulta las [especificaciones técnicas](TECHNICAL_SPECS.md) o el [repositorio de GitHub](https://github.com/natone2/droidmouse). 