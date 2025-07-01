# Preguntas Frecuentes - DroidMouse

## Instalación y Configuración

### ¿Qué requisitos necesito para usar DroidMouse?

**Servidor (PC):**
- Python 3.8 o superior
- Conexión de red local (WiFi o Ethernet)
- Sistema operativo: Windows 10+, macOS 10.15+, o Linux

**Cliente (Android):**
- Android 6.0 (API 23) o superior
- Giroscopio y acelerómetro integrados
- Conexión WiFi

### ¿Cómo instalo el servidor en mi PC?

1. Clona el repositorio: `git clone https://github.com/natone2/droidmouse.git`
2. Navega al directorio: `cd droid-mouse`
3. Crea un entorno virtual: `python -m venv venv`
4. Activa el entorno virtual:
   - Windows: `venv\Scripts\activate`
   - macOS/Linux: `source venv/bin/activate`
5. Instala dependencias: `pip install -r server/requirements.txt`
6. Ejecuta el servidor: `cd server && python main.py`

### ¿Puedo usar DroidMouse sin compilar la aplicación Android?

Sí, puedes descargar el APK precompilado desde la [página de releases](releases/droidmouse-debug.apk) e instalarlo directamente en tu dispositivo Android.

## Problemas de Conexión

### La aplicación no se conecta al servidor

**Verifica lo siguiente:**
1. **Misma red**: Ambos dispositivos deben estar en la misma red WiFi
2. **IP correcta**: Usa la IP local de tu PC (ej: 192.168.1.100)
3. **Puerto libre**: El puerto 8080 debe estar disponible
4. **Firewall**: Permite la conexión en el firewall de Windows
5. **Servidor activo**: El servidor Python debe estar ejecutándose

**Comandos de diagnóstico:**
```bash
# Verificar IP en Windows
ipconfig

# Verificar IP en Linux/macOS
ifconfig

# Verificar puerto en uso
netstat -an | grep 8080
```

### El servidor no inicia

**Posibles causas:**
- Puerto 8080 ocupado por otra aplicación
- Python no instalado o versión incorrecta
- Dependencias faltantes
- Permisos insuficientes

**Soluciones:**
```bash
# Cambiar puerto
export DROIDMOUSE_PORT=8081
python main.py

# Verificar Python
python --version

# Reinstalar dependencias
pip install -r requirements.txt --force-reinstall
```

### La conexión se pierde frecuentemente

**Causas comunes:**
- Señal WiFi débil
- Interferencias de red
- Configuración de energía del dispositivo
- Firewall agresivo

**Soluciones:**
1. Acerca ambos dispositivos al router
2. Desactiva el ahorro de energía WiFi en Android
3. Configura el firewall para permitir conexiones persistentes
4. Usa una conexión Ethernet si es posible

## Problemas de Rendimiento

### El movimiento del cursor es impreciso

**Soluciones:**
1. **Recalibrar sensores**: Presiona "Calibrar" en la aplicación
2. **Ajustar sensibilidad**: Reduce la sensibilidad si es muy alta
3. **Verificar superficie**: Coloca el teléfono en una superficie estable
4. **Evitar interferencias**: Aleja el dispositivo de campos magnéticos

### Hay latencia alta (>100ms)

**Optimizaciones:**
1. **Red dedicada**: Usa una red WiFi 5GHz si es posible
2. **Cerrar aplicaciones**: Cierra apps innecesarias en ambos dispositivos
3. **Verificar CPU**: Asegúrate de que no haya procesos pesados ejecutándose
4. **Actualizar drivers**: Mantén actualizados los drivers de red

### El cursor se mueve de forma errática

**Causas y soluciones:**
- **Vibraciones**: Mantén el dispositivo estable
- **Calibración incorrecta**: Recalibra los sensores
- **Interferencias magnéticas**: Aleja de imanes o dispositivos electrónicos
- **Sensores defectuosos**: Verifica que los sensores funcionen correctamente

## Problemas de Sensores

### Los valores de los sensores no cambian

**Verificaciones:**
1. **Permisos**: Asegúrate de que la app tenga permisos de sensores
2. **Hardware**: Verifica que tu dispositivo tenga giroscopio y acelerómetro
3. **Apps de terceros**: Cierra apps que puedan estar usando los sensores
4. **Reiniciar**: Reinicia la aplicación y el dispositivo

### Los sensores muestran valores incorrectos

**Soluciones:**
1. **Calibración manual**: Usa la función de calibración
2. **Reiniciar sensores**: Reinicia el dispositivo Android
3. **Verificar apps**: Desinstala apps que puedan interferir
4. **Contactar soporte**: Si persiste, puede ser un problema de hardware

## Problemas de Compatibilidad

### ¿Funciona en todos los dispositivos Android?

DroidMouse requiere:
- Android 6.0 o superior
- Giroscopio integrado
- Acelerómetro integrado
- Conexión WiFi

**Dispositivos compatibles:**
- Samsung Galaxy S6 y posteriores
- Google Pixel (todas las generaciones)
- OnePlus 5 y posteriores
- Xiaomi Mi 8 y posteriores
- Huawei P20 y posteriores

### ¿Funciona en iOS?

Actualmente DroidMouse solo está disponible para Android. Una versión iOS está en desarrollo.

### ¿Puedo usar múltiples dispositivos simultáneamente?

Sí, el servidor puede manejar múltiples conexiones. Cada dispositivo controlará el cursor de forma independiente.

## Problemas de Seguridad

### ¿Es seguro usar DroidMouse?

**Medidas de seguridad:**
- Conexión local (no requiere internet)
- Validación de datos de entrada
- Timeouts de conexión
- Logs de actividad

**Recomendaciones:**
- Usa solo en redes confiables
- No expongas el puerto a internet
- Mantén actualizado el software

### ¿DroidMouse envía datos a servidores externos?

No, DroidMouse funciona completamente de forma local. No se envían datos a servidores externos.

## Problemas de Desarrollo

### ¿Puedo modificar el código?

Sí, DroidMouse es software de código abierto bajo licencia GPL3. Puedes modificar, distribuir y contribuir al proyecto.

### ¿Cómo reporto un bug?

1. Verifica que no esté en esta FAQ
2. Busca en los [issues existentes](https://github.com/natone2/droidmouse/issues)
3. Crea un nuevo issue con:
   - Descripción detallada del problema
   - Pasos para reproducir
   - Información del sistema
   - Logs de error

### ¿Cómo contribuyo al proyecto?

1. Haz fork del repositorio
2. Crea una rama para tu feature
3. Implementa los cambios
4. Añade tests si es necesario
5. Envía un pull request

## Problemas Específicos por Sistema Operativo

### Windows

**Problema**: El servidor no puede controlar el ratón
**Solución**: Ejecuta como administrador o ajusta la configuración de UAC

**Problema**: Firewall bloquea la conexión
**Solución**: Permite Python en el firewall de Windows

### macOS

**Problema**: Permisos de accesibilidad
**Solución**: Ve a Preferencias del Sistema > Seguridad y Privacidad > Accesibilidad y añade Terminal/Python

**Problema**: Puerto ocupado
**Solución**: Cambia el puerto o mata el proceso que lo usa

### Linux

**Problema**: Permisos de X11
**Solución**: Asegúrate de que DISPLAY esté configurado correctamente

**Problema**: Dependencias faltantes
**Solución**: Instala paquetes de desarrollo necesarios

## Contacto y Soporte

### ¿Dónde puedo obtener ayuda adicional?

- **Documentación**: Consulta las [guías de instalación](INSTALLATION.md) y [técnicas](TECHNICAL_SPECS.md)
- **GitHub Issues**: [Reporta problemas](https://github.com/natone2/droidmouse/issues)
- **Discord**: Únete a nuestro [servidor de Discord](https://discord.gg/droidmouse)
- **Email**: Envía un email a support@droidmouse.com

### ¿Hay una comunidad de usuarios?

Sí, puedes unirte a:
- [GitHub Discussions](https://github.com/natone2/droidmouse/discussions)
- [Discord Server](https://discord.gg/droidmouse)
- [Reddit Community](https://reddit.com/r/droidmouse)

---

**¿No encontraste la respuesta?** Consulta nuestra [guía de solución de problemas](TROUBLESHOOTING.md) o [contacta con soporte](mailto:support@droidmouse.com). 