# Guía de Instalación - DroidMouse

## Requisitos Previos

### Para el Servidor (PC)
- Python 3.7 o superior
- pip (gestor de paquetes de Python)
- Sistema operativo: Linux, Windows o macOS

### Para la Aplicación Android
- Android 7.0 (API 24) o superior
- Dispositivo con giroscopio y acelerómetro
- Android Studio (para compilar desde código fuente)

## Instalación del Servidor

### 1. Clonar el Repositorio
```bash
git clone https://github.com/natone2/droidmouse.git
cd droid-mouse
```

### 2. Instalar Dependencias
```bash
cd server
pip install -r requirements.txt
```

### 3. Ejecutar el Servidor
```bash
python main.py
```

#### Opciones de Línea de Comandos
```bash
# Puerto personalizado
python main.py --port 9090

# Host específico
python main.py --host 127.0.0.1 --port 8080

# Ver ayuda
python main.py --help
```

### 4. Verificar la Instalación
El servidor debería mostrar:
```
🐭 DroidMouse Server iniciando...
Escuchando en 0.0.0.0:8080
✓ Servidor iniciado correctamente

Comandos disponibles:
  'quit' - Salir del servidor
  'status' - Estado del servidor
  'clients' - Clientes conectados
  'calibrate' - Recalibrar sensores
  'sensitivity <valor>' - Ajustar sensibilidad

DroidMouse>
```

## Instalación de la Aplicación Android

### Opción 1: APK Precompilado (Recomendado)

1. Descargar el APK desde la sección de releases
2. Habilitar "Instalar aplicaciones de fuentes desconocidas" en tu dispositivo
3. Instalar el APK descargado

### Opción 2: Compilar desde Código Fuente

#### 1. Configurar Android Studio
- Descargar e instalar Android Studio
- Abrir Android Studio y configurar el SDK

#### 2. Importar el Proyecto
```bash
cd android-app
# Abrir Android Studio y seleccionar "Open an existing project"
# Navegar a la carpeta android-app y seleccionarla
```

#### 3. Configurar el Dispositivo
- Conectar tu dispositivo Android via USB
- Habilitar "Depuración USB" en las opciones de desarrollador
- Autorizar la conexión en el dispositivo

#### 4. Compilar e Instalar
- En Android Studio, hacer clic en "Run" (▶️)
- Seleccionar tu dispositivo
- La aplicación se compilará e instalará automáticamente

## Configuración Inicial

### 1. Configurar la Red
Asegúrate de que tanto tu PC como tu smartphone estén conectados a la misma red WiFi.

### 2. Obtener la IP del Servidor
En el PC donde ejecutas el servidor:

**Linux/macOS:**
```bash
ifconfig | grep "inet "
```

**Windows:**
```cmd
ipconfig
```

Busca la IP de tu interfaz WiFi (generalmente empieza con 192.168.x.x)

### 3. Configurar la Aplicación Android
1. Abrir DroidMouse en tu smartphone
2. Introducir la IP del servidor en el campo "IP del Servidor"
3. Verificar que el puerto sea 8080 (o el que hayas configurado)
4. Hacer clic en "Conectar"

## Primer Uso

### 1. Conectar al Servidor
- Ejecutar el servidor en tu PC
- Abrir la app en tu smartphone
- Introducir la IP del servidor y conectar

### 2. Calibrar los Sensores
- Una vez conectado, hacer clic en "Calibrar"
- Mantener el dispositivo completamente quieto durante 2 segundos
- Esperar a que aparezca "Calibración completada"

### 3. Activar el Control
- Hacer clic en "Activar"
- Mover suavemente el dispositivo para controlar el ratón
- Ajustar la sensibilidad según sea necesario

## Solución de Problemas

### Servidor No Inicia
```bash
# Verificar que Python esté instalado
python --version

# Verificar dependencias
pip list | grep pyautogui

# Reinstalar dependencias
pip install --force-reinstall -r requirements.txt
```

### Error de Conexión
1. Verificar que ambos dispositivos estén en la misma red
2. Comprobar que el firewall no bloquee el puerto 8080
3. Verificar que la IP sea correcta

### Sensores No Funcionan
1. Verificar que tu dispositivo tenga giroscopio y acelerómetro
2. Reiniciar la aplicación
3. Verificar permisos de sensores

### Movimiento Impreciso
1. Recalibrar los sensores
2. Ajustar la sensibilidad
3. Verificar que no haya interferencias magnéticas

## Comandos del Servidor

Una vez que el servidor esté ejecutándose, puedes usar estos comandos:

- `status` - Ver estado del servidor y clientes
- `clients` - Listar clientes conectados
- `calibrate` - Iniciar nueva calibración
- `sensitivity 1.5` - Ajustar sensibilidad
- `quit` - Salir del servidor

## Configuración Avanzada

### Variables de Entorno
```bash
# Puerto personalizado
export DROIDMOUSE_PORT=9090

# Host específico
export DROIDMOUSE_HOST=127.0.0.1
```

### Configuración de Firewall
**Linux (ufw):**
```bash
sudo ufw allow 8080
```

**Windows:**
- Abrir "Firewall de Windows Defender"
- Permitir Python a través del firewall

**macOS:**
- Ir a Preferencias del Sistema > Seguridad y Privacidad
- Permitir conexiones entrantes para Python

## Desinstalación

### Servidor
```bash
# Eliminar dependencias
pip uninstall pyautogui numpy scipy psutil colorama

# Eliminar archivos
rm -rf droid-mouse
```

### Aplicación Android
- Ir a Configuración > Aplicaciones
- Buscar "DroidMouse"
- Seleccionar "Desinstalar" 