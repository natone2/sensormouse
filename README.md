# DroidMouse 🐭

**Ratón virtual usando el giroscopio del móvil**

Transforma tu smartphone Android en un periférico de control inalámbrico mediante conexión WiFi. Nace de la necesidad de controlar presentaciones o equipos multimedia sin depender de hardware adicional.

## 🚀 Características

- **Control inalámbrico**: Usa tu smartphone como ratón virtual
- **Sensores IMU**: Aprovecha giroscopio y acelerómetro para movimientos precisos
- **Filtro de Kalman**: Suavizado de movimiento para control fluido
- **Calibración dinámica**: Compensa la deriva del sensor automáticamente
- **Baja latencia**: Protocolo ligero (<3KB/s de throughput)
- **Multiplataforma**: Servidor compatible con Linux/Windows/macOS
- **Código Abierto**: Proyecto completamente open source bajo licencia GPL3. Contribuye, modifica y distribuye libremente.

## 📁 Estructura del Proyecto

```
droid-mouse/
├── android-app/          # Aplicación Android (Kotlin)
├── server/              # Servidor multiplataforma (Python)
├── docs/                # Documentación técnica
└── scripts/             # Scripts de utilidad
```

## 🛠️ Instalación

### Servidor (Python)

```bash
cd server
pip install -r requirements.txt
python main.py
```

### Aplicación Android

1. Abre el proyecto en Android Studio
2. Compila e instala en tu dispositivo Android
3. Conecta al servidor usando la IP del ordenador

## 🔧 Configuración

1. Ejecuta el servidor en tu ordenador
2. Abre la app en tu smartphone
3. Introduce la IP del servidor
4. Calibra los sensores moviendo el dispositivo
5. ¡Listo para usar!

## 📊 Especificaciones Técnicas

- **Protocolo**: TCP/IP con sockets
- **Throughput**: <3KB/s
- **Latencia**: <50ms en redes locales
- **Sensores**: Giroscopio + Acelerómetro
- **Filtro**: Kalman para suavizado
- **Calibración**: Dinámica automática

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature
3. Commit tus cambios
4. Push a la rama
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está licenciado bajo la **GNU General Public License v3.0** - ver el archivo [LICENSE](LICENSE) para más detalles. 