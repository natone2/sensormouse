# Guía de Instalación - SensorMouse

## Requisitos Previos

### Para el Servidor (PC)
- Python 3.8 o superior
- pip (gestor de paquetes de Python)
- Conexión de red local

### Para la Aplicación Android
- Android 6.0 (API 23) o superior
- Permisos de sensores habilitados
- Conexión WiFi

## Instalación del Servidor

### 1. Clonar el Repositorio
```bash
git clone https://github.com/natone2/sensormouse.git
cd sensormouse
```

### 2. Crear Entorno Virtual (Recomendado)
```bash
python -m venv venv

# En Windows
venv\Scripts\activate

# En macOS/Linux
source venv/bin/activate
```

### 3. Instalar Dependencias
```bash
pip install -r server/requirements.txt
```

### 4. Configurar el Servidor
```bash
cd server
python main.py
```

El servidor se iniciará en `http://localhost:8080` por defecto.

## Instalación de la Aplicación Android

### Opción 1: APK Precompilado
1. Descarga el archivo APK desde la [página de releases](releases/sensormouse-debug.apk)
2. Habilita "Fuentes desconocidas" en tu dispositivo Android
3. Instala el APK descargado

### Opción 2: Compilar desde el Código Fuente
```bash
cd android-app
./gradlew assembleDebug
```

El APK se generará en `android-app/app/build/outputs/apk/debug/`.

## Configuración Inicial

### 1. Configurar Permisos
La aplicación solicitará los siguientes permisos:
- **Sensores**: Para acceder al giroscopio y acelerómetro
- **Red**: Para comunicarse con el servidor
- **Almacenamiento**: Para guardar configuraciones

### 2. Conectar Dispositivos
1. Asegúrate de que ambos dispositivos estén en la misma red WiFi
2. Inicia el servidor en tu PC
3. Abre la aplicación SensorMouse en tu teléfono
4. Introduce la IP de tu PC en la aplicación
5. Presiona "Conectar"

### 3. Calibración
1. Coloca el teléfono en una superficie plana
2. Presiona "Calibrar" en la aplicación
3. Mantén el dispositivo inmóvil durante 3 segundos
4. La calibración se completará automáticamente

## Verificación de la Instalación

### Prueba Básica
1. Mueve suavemente el teléfono
2. El cursor en tu PC debería moverse en respuesta
3. Verifica que la latencia sea aceptable (< 50ms)

### Prueba de Sensores
En la aplicación Android, verifica que:
- Los valores del giroscopio cambien al rotar el dispositivo
- Los valores del acelerómetro cambien al mover el dispositivo
- El indicador de estado muestre "Conectado"

## Solución de Problemas

### El servidor no inicia
- Verifica que el puerto 8080 esté libre
- Asegúrate de tener Python 3.8+ instalado
- Revisa los logs de error

### La aplicación no se conecta
- Verifica que ambos dispositivos estén en la misma red
- Comprueba que el firewall no bloquee la conexión
- Confirma que la IP del servidor sea correcta

### Movimiento impreciso
- Recalibra los sensores
- Ajusta la sensibilidad en la configuración
- Verifica que no haya interferencias magnéticas

## Configuración Avanzada

### Variables de Entorno
```bash
export DROIDMOUSE_PORT=8080
export DROIDMOUSE_HOST=0.0.0.0
export DROIDMOUSE_DEBUG=true
```

### Configuración del Firewall
```bash
# En Windows
netsh advfirewall firewall add rule name="SensorMouse" dir=in action=allow protocol=TCP localport=8080

# En Linux
sudo ufw allow 8080/tcp
```

## Siguiente Paso

Una vez completada la instalación, consulta la [documentación técnica](TECHNICAL_SPECS.md) para entender cómo funciona el sistema o la [guía de API](API.md) para desarrollo avanzado.

---

**¿Necesitas ayuda?** Consulta nuestra [guía de solución de problemas](TROUBLESHOOTING.md) o [reporta un issue](https://github.com/natone2/sensormouse/issues). 