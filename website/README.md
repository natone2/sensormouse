# SensorMouse Website

Sitio web profesional para el proyecto SensorMouse - Control Virtual de Ratón con Android.

## 🚀 Características

- **Diseño Moderno**: Interfaz limpia y profesional con gradientes y animaciones
- **Responsive**: Adaptable a todos los dispositivos (móvil, tablet, desktop)
- **Accesible**: Cumple con estándares de accesibilidad web
- **Rápido**: Optimizado para rendimiento y carga rápida
- **SEO Friendly**: Meta tags y estructura optimizada para motores de búsqueda

## 📁 Estructura del Proyecto

```
website/
├── index.html              # Página principal
├── assets/
│   ├── css/
│   │   ├── style.css       # Estilos principales
│   │   └── responsive.css  # Estilos responsive
│   ├── js/
│   │   └── main.js         # JavaScript interactivo
│   └── images/
│       └── logo.svg        # Logo del proyecto
└── README.md               # Este archivo
```

## 🎨 Tecnologías Utilizadas

- **HTML5**: Estructura semántica moderna
- **CSS3**: Flexbox, Grid, Variables CSS, Animaciones
- **JavaScript ES6+**: Interactividad y animaciones
- **Font Awesome**: Iconos vectoriales
- **Google Fonts**: Tipografía Inter

## 🚀 Cómo Usar

### Desarrollo Local

1. **Clona el repositorio**:
   ```bash
   git clone https://github.com/yourusername/sensormouse.git
   cd sensormouse/website
   ```

2. **Abre en tu navegador**:
   ```bash
   # Con Python
   python -m http.server 8000
   
   # Con Node.js
   npx serve .
   
   # O simplemente abre index.html en tu navegador
   ```

3. **Visita**: `http://localhost:8000`

### Producción

1. **Sube los archivos** a tu servidor web
2. **Configura el servidor** para servir archivos estáticos
3. **Actualiza las URLs** en el código según tu dominio

## 📱 Secciones de la Web

### 1. **Hero Section**
- Título principal con efecto de escritura
- Descripción del proyecto
- Botones de descarga y documentación
- Estadísticas del proyecto
- Mockup interactivo del teléfono

### 2. **Características**
- 6 características principales del proyecto
- Iconos animados
- Efectos hover

### 3. **Cómo Funciona**
- Diagrama de flujo del proceso
- 4 pasos explicativos
- Animaciones de flechas

### 4. **Descarga**
- APK para Android
- Servidor Python
- Requisitos del sistema
- Información de versiones

### 5. **Documentación**
- Enlaces a guías
- API y desarrollo
- FAQ y soporte

### 6. **GitHub**
- Información del repositorio
- Estadísticas del proyecto
- Vista previa de código

## 🎯 Personalización

### Colores
Los colores se definen en variables CSS en `assets/css/style.css`:

```css
:root {
    --primary-color: #6366f1;
    --secondary-color: #8b5cf6;
    --accent-color: #06b6d4;
    /* ... más colores */
}
```

### Contenido
- **Títulos y descripciones**: Edita el HTML directamente
- **Imágenes**: Reemplaza los archivos en `assets/images/`
- **Enlaces**: Actualiza las URLs en el HTML

### Funcionalidades
- **Animaciones**: Modifica `assets/js/main.js`
- **Estilos**: Edita `assets/css/style.css`
- **Responsive**: Ajusta `assets/css/responsive.css`

## 🔧 Configuración Avanzada

### SEO
- Meta tags optimizados
- Open Graph para redes sociales
- Twitter Cards
- Schema.org markup

### Performance
- Lazy loading de imágenes
- CSS y JS minificados (recomendado para producción)
- Optimización de fuentes web

### Accesibilidad
- Navegación por teclado
- ARIA labels
- Contraste de colores adecuado
- Soporte para lectores de pantalla

## 📊 Analytics

Para agregar Google Analytics:

1. Obtén tu ID de seguimiento
2. Agrega este código antes de `</head>`:

```html
<!-- Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=GA_MEASUREMENT_ID"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'GA_MEASUREMENT_ID');
</script>
```

## 🌐 Despliegue

### GitHub Pages
1. Sube el código a GitHub
2. Ve a Settings > Pages
3. Selecciona la rama `main`
4. Tu sitio estará en `https://username.github.io/repo-name`

### Netlify
1. Conecta tu repositorio de GitHub
2. Configura el directorio de build como `website`
3. Despliega automáticamente

### Vercel
1. Importa tu proyecto desde GitHub
2. Configura el directorio raíz como `website`
3. Despliega con un clic

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 📞 Soporte

- **Issues**: [GitHub Issues](https://github.com/natone2/sensormouse/issues)
- **Email**: contact@sensormouse.com
- **Documentación**: [docs/](docs/)

---

Hecho con ❤️ para la comunidad open source 