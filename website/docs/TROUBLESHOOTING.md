# Guía de Solución de Problemas - SensorMouse

## Diagnóstico Inicial

### Herramientas de Diagnóstico

Antes de comenzar, recopila esta información:

```bash
# Información del sistema
python --version
pip list
uname -a  # Linux/macOS
systeminfo  # Windows

# Información de red
ipconfig  # Windows
ifconfig  # Linux/macOS
netstat -an | grep 8080

# Logs del servidor
tail -f server.log
```

## Problemas Comunes y Soluciones

### 1. Servidor No Inicia

#### Síntomas
- Error al ejecutar `python main.py`
- Puerto 8080 ocupado
- Permisos denegados

#### Diagnóstico
```bash
# Verificar si el puerto está ocupado
netstat -an | grep 8080

# Verificar permisos
ls -la main.py

# Verificar dependencias
pip check
```

#### Soluciones

**Puerto ocupado:**
```bash
# Cambiar puerto
export DROIDMOUSE_PORT=8081
python main.py

# O matar el proceso que usa el puerto
sudo lsof -ti:8080 | xargs kill -9
```

**Permisos insuficientes:**
```bash
# En Linux/macOS
chmod +x main.py

# En Windows (ejecutar como administrador)
# Click derecho en CMD/PowerShell -> "Ejecutar como administrador"
```

**Dependencias faltantes:**
```bash
# Reinstalar dependencias
pip install -r requirements.txt --force-reinstall

# Crear nuevo entorno virtual
python -m venv venv_new
source venv_new/bin/activate  # Linux/macOS
venv_new\Scripts\activate     # Windows
pip install -r requirements.txt
```

### 2. Conexión Fallida

#### Síntomas
- "No se puede conectar al servidor"
- Timeout de conexión
- Error de red

#### Diagnóstico
```bash
# Verificar que el servidor esté ejecutándose
ps aux | grep python

# Verificar conectividad
ping [IP_DEL_SERVIDOR]
telnet [IP_DEL_SERVIDOR] 8080

# Verificar firewall
sudo ufw status  # Linux
netsh advfirewall show allprofiles  # Windows
```

#### Soluciones

**Firewall bloqueando:**
```bash
# Linux
sudo ufw allow 8080/tcp

# Windows
netsh advfirewall firewall add rule name="SensorMouse" dir=in action=allow protocol=TCP localport=8080

# macOS
sudo /usr/libexec/ApplicationFirewall/socketfilterfw --add /usr/bin/python3
```

**IP incorrecta:**
```bash
# Obtener IP correcta
hostname -I  # Linux
ipconfig | grep "IPv4"  # Windows
ifconfig | grep "inet "  # macOS
```

**Red diferente:**
- Asegúrate de que ambos dispositivos estén en la misma red WiFi
- Verifica que no estén en redes separadas (2.4GHz vs 5GHz)

### 3. Movimiento Impreciso

#### Síntomas
- Cursor se mueve de forma errática
- Movimiento no responde a gestos
- Latencia alta

#### Diagnóstico
```python
# Verificar valores de sensores en la app Android
# Los valores deben cambiar al mover el dispositivo
# Giroscopio: ±2000°/s
# Acelerómetro: ±20g
```

#### Soluciones

**Recalibrar sensores:**
1. Coloca el dispositivo en una superficie plana
2. Presiona "Calibrar" en la aplicación
3. Mantén inmóvil durante 3 segundos
4. Verifica que los valores se estabilicen

**Ajustar sensibilidad:**
```python
# En la aplicación Android
# Configuración > Sensibilidad > Reducir a 0.5-1.0
```

**Verificar interferencias:**
- Aleja el dispositivo de imanes
- Evita campos electromagnéticos
- Usa en ambiente estable

### 4. Sensores No Funcionan

#### Síntomas
- Valores de sensores no cambian
- Error "Sensores no disponibles"
- App se cierra al acceder a sensores

#### Diagnóstico
```bash
# Verificar permisos en Android
# Configuración > Aplicaciones > SensorMouse > Permisos
# Asegúrate de que "Sensores" esté habilitado
```

#### Soluciones

**Permisos denegados:**
1. Ve a Configuración > Aplicaciones > SensorMouse
2. Permisos > Habilitar "Sensores"
3. Reinicia la aplicación

**Hardware no compatible:**
- Verifica que tu dispositivo tenga giroscopio
- Usa apps como "Sensor Box" para verificar sensores
- Consulta las especificaciones del fabricante

**Apps conflictivas:**
- Cierra apps que usen sensores (juegos, VR, etc.)
- Reinicia el dispositivo Android
- Desinstala apps sospechosas

### 5. Latencia Alta

#### Síntomas
- Movimiento del cursor con retraso
- Valores de latencia > 100ms
- Experiencia de usuario pobre

#### Diagnóstico
```bash
# Verificar uso de CPU
top
htop

# Verificar uso de red
iftop
nethogs

# Verificar memoria
free -h
```

#### Soluciones

**Optimizar red:**
- Usa conexión WiFi 5GHz si es posible
- Acerca dispositivos al router
- Cierra aplicaciones que usen mucha red

**Optimizar sistema:**
```bash
# Cerrar procesos innecesarios
pkill -f "chrome"
pkill -f "firefox"

# Limpiar memoria
sudo sync && sudo sysctl -w vm.drop_caches=3
```

**Configurar prioridades:**
```bash
# En Linux, dar prioridad al proceso
sudo nice -n -10 python main.py

# En Windows, establecer prioridad alta
# Administrador de tareas > Procesos > Python > Prioridad alta
```

### 6. Errores de Compilación (Android)

#### Síntomas
- Error al compilar APK
- Dependencias faltantes
- Versiones incompatibles

#### Diagnóstico
```bash
# Verificar versión de Gradle
./gradlew --version

# Verificar SDK
sdkmanager --list

# Verificar variables de entorno
echo $ANDROID_HOME
echo $JAVA_HOME
```

#### Soluciones

**Actualizar Gradle:**
```bash
# En android-app/gradle/wrapper/gradle-wrapper.properties
distributionUrl=https\://services.gradle.org/distributions/gradle-7.5-bin.zip
```

**Instalar SDK faltante:**
```bash
sdkmanager "platforms;android-33"
sdkmanager "build-tools;33.0.0"
```

**Limpiar proyecto:**
```bash
./gradlew clean
./gradlew assembleDebug
```

## Logs y Debugging

### Habilitar Logs Detallados

```python
# En main.py
import logging
logging.basicConfig(
    level=logging.DEBUG,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler('sensormouse.log'),
        logging.StreamHandler()
    ]
)
```

### Interpretar Logs

**Errores comunes en logs:**
```
ERROR - Connection refused
# Solución: Verificar que el servidor esté ejecutándose

ERROR - Permission denied
# Solución: Ejecutar con permisos adecuados

WARNING - High latency detected
# Solución: Optimizar red o sistema
```

### Herramientas de Monitoreo

```bash
# Monitorear conexiones en tiempo real
watch -n 1 "netstat -an | grep 8080"

# Monitorear uso de CPU
htop

# Monitorear red
iftop -i wlan0

# Monitorear logs
tail -f sensormouse.log
```

## Problemas Específicos por Plataforma

### Windows

**Problema: UAC bloquea el control del ratón**
```cmd
# Ejecutar como administrador
# O deshabilitar UAC temporalmente
```

**Problema: Antivirus bloquea la conexión**
- Añadir excepción en el antivirus
- Verificar Windows Defender

### macOS

**Problema: Permisos de accesibilidad**
1. Preferencias del Sistema > Seguridad y Privacidad
2. Accesibilidad > Añadir Terminal/Python
3. Reiniciar aplicación

**Problema: Puerto ocupado por AirPlay**
```bash
# Cambiar puerto
export DROIDMOUSE_PORT=8081
```

### Linux

**Problema: Permisos de X11**
```bash
# Verificar DISPLAY
echo $DISPLAY

# Configurar si es necesario
export DISPLAY=:0
```

**Problema: Dependencias del sistema**
```bash
# Ubuntu/Debian
sudo apt-get install python3-dev python3-pip

# Arch Linux
sudo pacman -S python-pip

# Fedora
sudo dnf install python3-devel python3-pip
```

## Recuperación de Emergencia

### Reset Completo

```bash
# 1. Detener todos los procesos
pkill -f "python.*main.py"
pkill -f "sensormouse"

# 2. Limpiar archivos temporales
rm -rf __pycache__
rm -f *.log

# 3. Reinstalar dependencias
pip uninstall -r requirements.txt -y
pip install -r requirements.txt

# 4. Reiniciar servidor
python main.py
```

### Backup y Restauración

```bash
# Crear backup de configuración
cp config.json config.json.backup

# Restaurar configuración
cp config.json.backup config.json
```

## Contacto para Problemas Críticos

### Información Necesaria

Al reportar un problema, incluye:

1. **Información del sistema:**
   - Sistema operativo y versión
   - Versión de Python
   - Versión de SensorMouse

2. **Logs de error:**
   - Logs del servidor
   - Logs de la aplicación Android
   - Mensajes de error específicos

3. **Pasos para reproducir:**
   - Secuencia exacta de acciones
   - Configuración utilizada
   - Comportamiento esperado vs actual

### Canales de Soporte

- **GitHub Issues**: Para bugs y problemas técnicos
- **Email**: support@sensormouse.com para problemas críticos
- **Discord**: Para ayuda en tiempo real

---

**¿Necesitas ayuda adicional?** Consulta la [FAQ](FAQ.md) o [contacta con soporte](mailto:support@sensormouse.com). 