# Especificaciones Técnicas - DroidMouse

## Arquitectura del Sistema

### Componentes Principales

1. **Servidor Python** (`server/`)
   - Control del ratón del sistema
   - Filtro de Kalman para suavizado
   - Sistema de calibración dinámica
   - Comunicación TCP/IP

2. **Aplicación Android** (`android-app/`)
   - Lectura de sensores IMU
   - Filtrado y procesamiento de datos
   - Interfaz de usuario
   - Comunicación con servidor

### Protocolo de Comunicación

#### Formato de Mensajes JSON

**Datos de Sensor:**
```json
{
  "type": "sensor_data",
  "gyro": {
    "x": 0.123,
    "y": -0.456,
    "z": 0.789
  },
  "accel": {
    "x": 9.81,
    "y": 0.12,
    "z": 0.34
  }
}
```

**Comandos:**
```json
{
  "type": "command",
  "command": "activate|deactivate|calibrate|sensitivity:1.5"
}
```

#### Características del Protocolo

- **Throughput**: <3KB/s
- **Latencia**: <50ms en redes locales
- **Frecuencia de muestreo**: 50Hz
- **Formato**: JSON con saltos de línea
- **Transporte**: TCP/IP con sockets

## Algoritmos Implementados

### Filtro de Kalman

**Propósito**: Suavizado de movimiento para eliminar ruido del sensor

**Parámetros**:
- `process_variance`: 1e-5 (varianza del proceso)
- `measurement_variance`: 1e-2 (varianza de la medición)

**Ecuaciones**:
```
Predicción:
  prediction_error = estimate_error + process_variance

Actualización:
  kalman_gain = prediction_error / (prediction_error + measurement_variance)
  estimate = estimate + kalman_gain * (measurement - estimate)
  estimate_error = (1 - kalman_gain) * prediction_error
```

### Sistema de Calibración

**Propósito**: Compensar la deriva del sensor

**Proceso**:
1. Recolección de 100 muestras en estado estático
2. Cálculo del bias promedio
3. Aplicación de compensación en tiempo real

**Fórmula**:
```
calibrated_value = raw_value - bias
```

## Sensores Utilizados

### Giroscopio
- **Tipo**: Sensor.TYPE_GYROSCOPE
- **Unidades**: rad/s
- **Rango**: ±35 rad/s (típico)
- **Uso**: Movimiento angular del ratón

### Acelerómetro
- **Tipo**: Sensor.TYPE_ACCELEROMETER
- **Unidades**: m/s²
- **Rango**: ±2g a ±16g
- **Uso**: Referencia de orientación

## Configuración de Red

### Servidor
- **Puerto por defecto**: 8080
- **Host**: 0.0.0.0 (todas las interfaces)
- **Protocolo**: TCP/IP
- **Máximo de clientes**: 5 simultáneos

### Cliente Android
- **Timeout de conexión**: 5 segundos
- **Reconexión automática**: No implementada
- **Validación de IP**: Formato IPv4

## Rendimiento

### Métricas Objetivo
- **Latencia**: <50ms
- **Throughput**: <3KB/s
- **Precisión**: ±1 píxel
- **Frecuencia de actualización**: 50Hz

### Optimizaciones
- Filtro de Kalman para suavizado
- Calibración dinámica
- Protocolo JSON ligero
- Muestreo optimizado de sensores

## Seguridad

### Consideraciones
- No hay autenticación implementada
- Comunicación en red local únicamente
- No hay cifrado de datos
- Validación básica de entrada

### Recomendaciones
- Usar en redes WiFi seguras
- Implementar autenticación para uso en producción
- Considerar cifrado TLS para redes públicas

## Compatibilidad

### Servidor
- **Sistemas**: Linux, Windows, macOS
- **Python**: 3.7+
- **Dependencias**: pyautogui, numpy, scipy

### Cliente Android
- **API mínima**: 24 (Android 7.0)
- **API objetivo**: 34 (Android 14)
- **Sensores requeridos**: Giroscopio, Acelerómetro

## Limitaciones Conocidas

1. **Deriva del sensor**: Compensada con calibración
2. **Latencia de red**: Dependiente de la calidad de la conexión
3. **Precisión**: Limitada por la resolución del sensor
4. **Batería**: Uso continuo de sensores consume energía

## Futuras Mejoras

1. **Autenticación**: Sistema de autenticación
2. **Cifrado**: Comunicación cifrada
3. **Gestos**: Reconocimiento de gestos
4. **Configuración**: Interfaz de configuración avanzada
5. **Logs**: Sistema de logging detallado 