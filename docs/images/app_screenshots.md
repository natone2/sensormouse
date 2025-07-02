# 📱 Capturas de Pantalla - SensorMouse

## Pantalla Principal (MainActivity)

### Estado: Desconectado
```
┌─────────────────────────────────────┐
│ 📱 SensorMouse                    ⚙️ │
├─────────────────────────────────────┤
│                                     │
│    🖱️                              │
│                                     │
│  Estado: Desconectado ❌            │
│                                     │
│  IP Servidor: [192.168.1.100    ]  │
│  Puerto:      [5000             ]  │
│                                     │
│  [    Conectar    ]                │
│                                     │
│  Sensibilidad: [━━━━●━━━━] 1.0x    │
│                                     │
│  [Calibrar]                        │
│                                     │
│  Sesiones restantes: 30 días        │
│  [Actualizar a Pro]                 │
│                                     │
│  🖱️ 🖱️ 🖱️                          │
│  L   R   M                          │
│                                     │
└─────────────────────────────────────┘
```

### Estado: Conectado
```
┌─────────────────────────────────────┐
│ 📱 SensorMouse                    ⚙️ │
├─────────────────────────────────────┤
│                                     │
│    🖱️                              │
│                                     │
│  Estado: Conectado ✅               │
│  Servidor: 192.168.1.100:5000      │
│                                     │
│  [  Desconectar  ]                  │
│                                     │
│  Sensibilidad: [━━━━●━━━━] 1.0x    │
│                                     │
│  [Calibrar]                        │
│                                     │
│  Sesiones restantes: 30 días        │
│  [Actualizar a Pro]                 │
│                                     │
│  🖱️ 🖱️ 🖱️                          │
│  L   R   M                          │
│                                     │
└─────────────────────────────────────┘
```

## Pantalla de Bienvenida (WelcomeActivity)

```
┌─────────────────────────────────────┐
│                                     │
│           📱 SensorMouse            │
│                                     │
│    Convierte tu smartphone en       │
│        un ratón inalámbrico         │
│                                     │
│    🖱️                              │
│                                     │
│  Usa los sensores de tu dispositivo │
│  para controlar el cursor del PC    │
│                                     │
│  ✅ Fácil de usar                   │
│  ✅ Sin cables                      │
│  ✅ Baja latencia                   │
│  ✅ Compatible con Android 6.0+     │
│                                     │
│  [    Comenzar    ]                 │
│                                     │
│  Versión gratuita: 30 días de prueba│
│  [Ver más información]              │
│                                     │
└─────────────────────────────────────┘
```

## Diálogo de Calibración

```
┌─────────────────────────────────────┐
│           🔧 Calibración            │
├─────────────────────────────────────┤
│                                     │
│  Coloca el dispositivo sobre una    │
│  superficie plana y no lo muevas    │
│                                     │
│  Calibrando sensores...             │
│  [██████████] 100%                  │
│                                     │
│  ✅ Calibración completada          │
│                                     │
│  [    Aceptar    ]                  │
│                                     │
└─────────────────────────────────────┘
```

## Diálogo de Configuración

```
┌─────────────────────────────────────┐
│           ⚙️ Configuración          │
├─────────────────────────────────────┤
│                                     │
│  IP del Servidor:                   │
│  [192.168.1.100                ]    │
│                                     │
│  Puerto:                            │
│  [5000                          ]   │
│                                     │
│  Sensibilidad por defecto:          │
│  [━━━━●━━━━] 1.0x                   │
│                                     │
│  Auto-conectar al abrir:            │
│  ☐ Habilitado                       │
│                                     │
│  Vibración al hacer clic:           │
│  ☑ Habilitado                       │
│                                     │
│  [   Guardar   ] [  Cancelar  ]     │
│                                     │
└─────────────────────────────────────┘
```

## Diálogo de Actualización Pro

```
┌─────────────────────────────────────┐
│           ⭐ SensorMouse Pro         │
├─────────────────────────────────────┤
│                                     │
│  Desbloquea todas las funciones:    │
│                                     │
│  ✅ Sensibilidad ilimitada          │
│  ✅ Perfiles guardados              │
│  ✅ Soporte prioritario             │
│  ✅ Sin límites de tiempo           │
│  ✅ Funciones avanzadas             │
│                                     │
│  Precio: $2.99 (pago único)         │
│                                     │
│  [   Comprar Pro   ]                │
│  [   Restaurar     ]                │
│  [   Cancelar      ]                │
│                                     │
└─────────────────────────────────────┘
```

## Notificación de Estado

```
┌─────────────────────────────────────┐
│ 📱 SensorMouse                      │
│ Conectado a 192.168.1.100:5000     │
│ Sensibilidad: 1.0x                  │
│                                     │
│ [Configurar] [Desconectar]          │
└─────────────────────────────────────┘
```

## Diagrama de Flujo de Uso

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Iniciar   │───▶│  Conectar   │───▶│  Calibrar   │
│   Servidor  │    │   App       │    │ Sensores    │
└─────────────┘    └─────────────┘    └─────────────┘
                           │                   │
                           ▼                   ▼
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  Usar como  │◀───│  Ajustar    │◀───│  Configurar │
│   Ratón     │    │Sensibilidad │    │ Conexión    │
└─────────────┘    └─────────────┘    └─────────────┘
```

## Diagrama de Arquitectura

```
┌─────────────────┐    WiFi    ┌─────────────────┐
│   Smartphone    │◄──────────►│      PC         │
│   Android       │            │                 │
│                 │            │                 │
│ 📱 SensorMouse  │            │ 🖥️ Python       │
│ App             │            │ Server          │
│                 │            │                 │
│ • Giroscopio    │            │ • Flask API     │
│ • Acelerómetro  │            │ • PyAutoGUI     │
│ • WiFi          │            │ • Mouse Control │
└─────────────────┘            └─────────────────┘
```

## Casos de Uso Visuales

### Presentación
```
┌─────────────────┐
│   📱 Móvil      │
│   (control)     │
└─────────────────┘
        │
        ▼
┌─────────────────┐
│   🖥️ PC         │
│   (PowerPoint)  │
└─────────────────┘
```

### Gaming
```
┌─────────────────┐
│   📱 Móvil      │
│   (control)     │
└─────────────────┘
        │
        ▼
┌─────────────────┐
│   🖥️ PC         │
│   (Juego)       │
└─────────────────┘
```

### Diseño
```
┌─────────────────┐
│   📱 Móvil      │
│   (control)     │
└─────────────────┘
        │
        ▼
┌─────────────────┐
│   🖥️ PC         │
│   (Photoshop)   │
└─────────────────┘
``` 