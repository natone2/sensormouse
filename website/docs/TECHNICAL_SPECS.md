# Especificaciones Técnicas - DroidMouse

## Arquitectura del Sistema

DroidMouse utiliza una arquitectura cliente-servidor distribuida con los siguientes componentes:

```
┌─────────────────┐    TCP/IP    ┌─────────────────┐
│   Android App   │ ──────────── │  Python Server  │
│   (Cliente)     │              │   (Servidor)    │
└─────────────────┘              └─────────────────┘
        │                                  │
        │                                  │
        ▼                                  ▼
┌─────────────────┐              ┌─────────────────┐
│   Sensores      │              │   Sistema OS    │
│ - Giroscopio    │              │ - Control Ratón │
│ - Acelerómetro  │              │ - Eventos       │
└─────────────────┘              └─────────────────┘
```

## Componentes del Cliente (Android)

### Tecnologías Utilizadas
- **Lenguaje**: Kotlin
- **SDK Mínimo**: API 23 (Android 6.0)
- **Arquitectura**: MVVM con LiveData
- **Comunicación**: Sockets TCP/IP

### Sensores Implementados

#### Giroscopio
- **Frecuencia de muestreo**: 50Hz
- **Rango**: ±2000°/s
- **Precisión**: 0.1°/s
- **Ejes**: X, Y, Z

#### Acelerómetro
- **Frecuencia de muestreo**: 50Hz
- **Rango**: ±20g
- **Precisión**: 0.01g
- **Ejes**: X, Y, Z

### Algoritmos de Procesamiento

#### Filtro de Kalman
```kotlin
class KalmanFilter {
    private var x = 0.0      // Estado estimado
    private var P = 1.0      // Covarianza del error
    private val Q = 0.001    // Ruido del proceso
    private val R = 0.1      // Ruido de medición
    
    fun update(measurement: Double): Double {
        // Predicción
        val x_pred = x
        val P_pred = P + Q
        
        // Actualización
        val K = P_pred / (P_pred + R)
        x = x_pred + K * (measurement - x_pred)
        P = (1 - K) * P_pred
        
        return x
    }
}
```

#### Calibración Automática
- **Detección de deriva**: Análisis de tendencias temporales
- **Compensación**: Ajuste dinámico de offset
- **Validación**: Verificación de integridad de datos

## Componentes del Servidor (Python)

### Tecnologías Utilizadas
- **Lenguaje**: Python 3.8+
- **Framework**: Socket TCP/IP nativo
- **Control de ratón**: pyautogui
- **Procesamiento**: NumPy para cálculos matemáticos

### Estructura del Servidor

```python
class DroidMouseServer:
    def __init__(self, host='0.0.0.0', port=8080):
        self.host = host
        self.port = port
        self.clients = []
        self.sensitivity = 1.0
        
    def start(self):
        # Inicializar socket
        # Escuchar conexiones
        # Procesar datos de sensores
        
    def process_sensor_data(self, data):
        # Aplicar filtros
        # Calcular movimiento del cursor
        # Actualizar posición del ratón
```

### Protocolo de Comunicación

#### Formato de Mensajes
```json
{
    "type": "sensor_data",
    "timestamp": 1640995200.123,
    "gyroscope": {
        "x": 0.12,
        "y": -0.08,
        "z": 0.01
    },
    "accelerometer": {
        "x": 0.05,
        "y": -0.02,
        "z": 9.81
    }
}
```

#### Comandos de Control
```json
{
    "type": "command",
    "action": "calibrate|connect|disconnect|set_sensitivity",
    "value": 1.5
}
```

## Especificaciones de Rendimiento

### Latencia
- **Objetivo**: < 50ms
- **Medida**: Tiempo desde sensor hasta movimiento del cursor
- **Optimización**: Buffering inteligente y procesamiento asíncrono

### Precisión
- **Resolución**: 0.1 píxeles
- **Reproducibilidad**: ±2% en condiciones normales
- **Calibración**: Automática cada 30 segundos

### Frecuencia de Actualización
- **Cliente**: 50Hz (20ms por muestra)
- **Servidor**: 60Hz (16.67ms por frame)
- **Sincronización**: Interpolación temporal

## Configuración del Sistema

### Parámetros Ajustables

#### Sensibilidad
```python
SENSITIVITY_RANGE = (0.1, 5.0)
DEFAULT_SENSITIVITY = 1.0
```

#### Filtros
```python
KALMAN_Q = 0.001  # Ruido del proceso
KALMAN_R = 0.1    # Ruido de medición
LOW_PASS_ALPHA = 0.8  # Factor de suavizado
```

#### Red
```python
BUFFER_SIZE = 1024
CONNECTION_TIMEOUT = 30
RECONNECT_ATTEMPTS = 3
```

## Seguridad

### Autenticación
- **Método**: Token de sesión
- **Duración**: 24 horas
- **Renovación**: Automática

### Validación de Datos
- **Rangos**: Verificación de límites físicos
- **Integridad**: Checksum CRC32
- **Sanitización**: Filtrado de valores anómalos

### Red
- **Protocolo**: TCP/IP con verificación de conexión
- **Firewall**: Configuración automática de puertos
- **Encriptación**: Opcional con TLS/SSL

## Compatibilidad

### Sistemas Operativos Soportados

#### Servidor
- **Windows**: 10, 11
- **macOS**: 10.15+, 11+, 12+
- **Linux**: Ubuntu 20.04+, Debian 11+, Arch Linux

#### Cliente
- **Android**: 6.0 (API 23) - 13 (API 33)
- **Arquitecturas**: ARM, ARM64, x86, x86_64

### Hardware Mínimo

#### Servidor
- **CPU**: Dual-core 1.5GHz
- **RAM**: 2GB
- **Red**: WiFi 802.11n o Ethernet

#### Cliente
- **CPU**: ARM Cortex-A7 o superior
- **RAM**: 1GB
- **Sensores**: Giroscopio y acelerómetro integrados

## Métricas y Monitoreo

### Logs del Sistema
```python
LOGGING_CONFIG = {
    'level': 'INFO',
    'format': '%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    'handlers': ['file', 'console']
}
```

### Métricas Recopiladas
- **Latencia**: Tiempo de respuesta
- **Precisión**: Error de posicionamiento
- **Estabilidad**: Variación de la señal
- **Uso de recursos**: CPU, memoria, red

### Alertas
- **Conexión perdida**: > 5 segundos
- **Latencia alta**: > 100ms
- **Error de sensor**: Valores fuera de rango

## Optimizaciones

### Rendimiento
- **Multithreading**: Procesamiento paralelo de sensores
- **Memory pooling**: Reutilización de buffers
- **JIT compilation**: Optimización de código crítico

### Energía
- **Adaptive sampling**: Frecuencia variable según uso
- **Sleep modes**: Suspensión inteligente
- **Power management**: Control de recursos

---

**Para más detalles técnicos**, consulta la [documentación de la API](API.md) o el [código fuente](https://github.com/natone2/droidmouse). 