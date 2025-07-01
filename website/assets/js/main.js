// SensorMouse Website JavaScript
document.addEventListener('DOMContentLoaded', function() {
    
    // Mobile Navigation Toggle
    const hamburger = document.querySelector('.hamburger');
    const navMenu = document.querySelector('.nav-menu');
    
    if (hamburger && navMenu) {
        hamburger.addEventListener('click', function() {
            hamburger.classList.toggle('active');
            navMenu.classList.toggle('active');
        });
        
        // Close mobile menu when clicking on a link
        document.querySelectorAll('.nav-link').forEach(link => {
            link.addEventListener('click', () => {
                hamburger.classList.remove('active');
                navMenu.classList.remove('active');
            });
        });
    }
    
    // Smooth scrolling for navigation links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                const offsetTop = target.offsetTop - 70; // Account for fixed navbar
                window.scrollTo({
                    top: offsetTop,
                    behavior: 'smooth'
                });
            }
        });
    });
    
    // Navbar background on scroll
    const navbar = document.querySelector('.navbar');
    if (navbar) {
        window.addEventListener('scroll', function() {
            if (window.scrollY > 50) {
                navbar.style.background = 'rgba(255, 255, 255, 0.98)';
                navbar.style.boxShadow = '0 2px 20px rgba(0, 0, 0, 0.1)';
            } else {
                navbar.style.background = 'rgba(255, 255, 255, 0.95)';
                navbar.style.boxShadow = 'none';
            }
        });
    }
    
    // Intersection Observer for animations
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };
    
    const observer = new IntersectionObserver(function(entries) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('animate-fade-in-up');
            }
        });
    }, observerOptions);
    
    // Observe elements for animation
    const animateElements = document.querySelectorAll('.feature-card, .download-card, .doc-card, .workflow-step');
    animateElements.forEach(el => {
        observer.observe(el);
    });
    
    // Phone mockup animation
    const phoneMockup = document.querySelector('.phone-mockup');
    if (phoneMockup) {
        let isAnimating = false;
        
        phoneMockup.addEventListener('mouseenter', function() {
            if (!isAnimating) {
                isAnimating = true;
                this.style.transform = 'perspective(1000px) rotateY(-10deg) rotateX(2deg) scale(1.05)';
                setTimeout(() => {
                    isAnimating = false;
                }, 300);
            }
        });
        
        phoneMockup.addEventListener('mouseleave', function() {
            if (!isAnimating) {
                isAnimating = true;
                this.style.transform = 'perspective(1000px) rotateY(-15deg) rotateX(5deg) scale(1)';
                setTimeout(() => {
                    isAnimating = false;
                }, 300);
            }
        });
    }
    
    // Sensor data animation in phone mockup
    const sensorValues = document.querySelectorAll('.sensor-item .value');
    if (sensorValues.length > 0) {
        function updateSensorValues() {
            sensorValues.forEach((value, index) => {
                const randomValue = (Math.random() - 0.5) * 2;
                const formattedValue = randomValue.toFixed(2);
                value.textContent = formattedValue;
                
                // Add color animation
                value.style.color = randomValue > 0 ? '#06b6d4' : '#ef4444';
                setTimeout(() => {
                    value.style.color = '#06b6d4';
                }, 500);
            });
        }
        
        // Update every 2 seconds
        setInterval(updateSensorValues, 2000);
    }
    
    // Status indicator animation
    const statusIndicator = document.querySelector('.status-indicator');
    if (statusIndicator) {
        function toggleStatus() {
            statusIndicator.classList.toggle('online');
            if (statusIndicator.classList.contains('online')) {
                statusIndicator.style.background = '#10b981';
                statusIndicator.style.boxShadow = '0 0 10px #10b981';
            } else {
                statusIndicator.style.background = '#ef4444';
                statusIndicator.style.boxShadow = '0 0 10px #ef4444';
            }
        }
        
        // Toggle status every 3 seconds
        setInterval(toggleStatus, 3000);
    }
    
    // Button hover effects
    const buttons = document.querySelectorAll('.btn');
    buttons.forEach(button => {
        button.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-2px) scale(1.02)';
        });
        
        button.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0) scale(1)';
        });
    });
    
    // Feature cards hover effects
    const featureCards = document.querySelectorAll('.feature-card');
    featureCards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-5px) scale(1.02)';
        });
        
        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0) scale(1)';
        });
    });
    
    // Download progress simulation
    const downloadButtons = document.querySelectorAll('.btn-primary[download]');
    downloadButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            const originalText = this.innerHTML;
            this.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Descargando...';
            this.disabled = true;
            
            // Simulate download progress
            setTimeout(() => {
                this.innerHTML = '<i class="fas fa-check"></i> ¡Descargado!';
                this.style.background = 'linear-gradient(135deg, #10b981 0%, #059669 100%)';
                
                setTimeout(() => {
                    this.innerHTML = originalText;
                    this.disabled = false;
                    this.style.background = '';
                }, 2000);
            }, 1500);
        });
    });
    
    // Parallax effect for hero section
    const hero = document.querySelector('.hero');
    if (hero) {
        window.addEventListener('scroll', function() {
            const scrolled = window.pageYOffset;
            const rate = scrolled * -0.5;
            hero.style.transform = `translateY(${rate}px)`;
        });
    }
    
    // Typing effect for hero title
    const heroTitle = document.querySelector('.hero-title');
    if (heroTitle) {
        const text = heroTitle.textContent;
        heroTitle.textContent = '';
        
        let i = 0;
        function typeWriter() {
            if (i < text.length) {
                heroTitle.textContent += text.charAt(i);
                i++;
                setTimeout(typeWriter, 50);
            }
        }
        
        // Start typing effect when page loads
        setTimeout(typeWriter, 1000);
    }
    
    // Counter animation for stats
    const statNumbers = document.querySelectorAll('.stat-number');
    statNumbers.forEach(stat => {
        const target = parseInt(stat.textContent);
        const increment = target / 50;
        let current = 0;
        
        function updateCounter() {
            if (current < target) {
                current += increment;
                stat.textContent = Math.ceil(current) + (stat.textContent.includes('%') ? '%' : '');
                requestAnimationFrame(updateCounter);
            } else {
                stat.textContent = target + (stat.textContent.includes('%') ? '%' : '');
            }
        }
        
        // Start counter animation when element is visible
        const statObserver = new IntersectionObserver(function(entries) {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    updateCounter();
                    statObserver.unobserve(entry.target);
                }
            });
        });
        
        statObserver.observe(stat);
    });
    
    // Code syntax highlighting simulation
    const codeContent = document.querySelector('.code-content code');
    if (codeContent) {
        const keywords = ['class', 'def', 'self', 'import', 'from', 'if', 'else', 'try', 'except'];
        const strings = ['"🐭 SensorMouse Server iniciando..."', '"Servidor TCP/IP para control remoto"'];
        
        let html = codeContent.textContent;
        
        // Highlight keywords
        keywords.forEach(keyword => {
            const regex = new RegExp(`\\b${keyword}\\b`, 'g');
            html = html.replace(regex, `<span class="keyword">${keyword}</span>`);
        });
        
        // Highlight strings
        strings.forEach(string => {
            html = html.replace(string, `<span class="string">${string}</span>`);
        });
        
        codeContent.innerHTML = html;
    }
    
    // Lazy loading for images
    const images = document.querySelectorAll('img[data-src]');
    const imageObserver = new IntersectionObserver(function(entries) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                img.src = img.dataset.src;
                img.classList.remove('lazy');
                imageObserver.unobserve(img);
            }
        });
    });
    
    images.forEach(img => imageObserver.observe(img));
    
    // Form validation (if forms are added later)
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            // Add form validation logic here
        });
    });
    
    // Performance monitoring
    window.addEventListener('load', function() {
        const loadTime = performance.now();
        console.log(`SensorMouse website loaded in ${loadTime.toFixed(2)}ms`);
    });
    
    // Error handling
    window.addEventListener('error', function(e) {
        console.error('Website error:', e.error);
    });
    
    // Service Worker registration (for PWA features)
    if ('serviceWorker' in navigator) {
        window.addEventListener('load', function() {
            navigator.serviceWorker.register('/sw.js')
                .then(function(registration) {
                    console.log('SW registered: ', registration);
                })
                .catch(function(registrationError) {
                    console.log('SW registration failed: ', registrationError);
                });
        });
    }
    
    // Keyboard shortcuts
    document.addEventListener('keydown', function(e) {
        // Ctrl/Cmd + K to focus search (if implemented)
        if ((e.ctrlKey || e.metaKey) && e.key === 'k') {
            e.preventDefault();
            // Add search functionality here
        }
        
        // Escape to close mobile menu
        if (e.key === 'Escape') {
            hamburger.classList.remove('active');
            navMenu.classList.remove('active');
        }
    });
    
    // Accessibility improvements
    const focusableElements = document.querySelectorAll('a, button, input, textarea, select, [tabindex]:not([tabindex="-1"])');
    focusableElements.forEach(element => {
        element.addEventListener('focus', function() {
            this.style.outline = '2px solid var(--primary-color)';
            this.style.outlineOffset = '2px';
        });
        
        element.addEventListener('blur', function() {
            this.style.outline = 'none';
        });
    });
    
    // Console welcome message
    console.log(`
    🐭 SensorMouse Website
    
    Welcome to the SensorMouse project website!
    
    Features:
    - Mobile-responsive design
    - Smooth animations
    - Interactive elements
    - Accessibility compliant
    
    For more information, visit: https://github.com/yourusername/sensormouse
    
    Made with ❤️ for the open source community
    `);
    
    // --- Mouse click buttons demo logic ---
    const btnLeft = document.getElementById('btn-left');
    const btnMiddle = document.getElementById('btn-middle');
    const btnRight = document.getElementById('btn-right');
    const demoMouse = document.getElementById('demo-mouse');

    function flashMouse(color) {
        if (!demoMouse) return;
        const original = demoMouse.style.boxShadow;
        demoMouse.style.boxShadow = `0 0 24px 6px ${color}`;
        setTimeout(() => {
            demoMouse.style.boxShadow = original;
        }, 180);
    }

    if (btnLeft) {
        btnLeft.addEventListener('click', () => {
            flashMouse('#6366f1'); // lila
            // Aquí se podría enviar evento de clic izquierdo al servidor
        });
    }
    if (btnMiddle) {
        btnMiddle.addEventListener('click', () => {
            flashMouse('#06b6d4'); // azul-acento
            // Aquí se podría enviar evento de clic central al servidor
        });
    }
    if (btnRight) {
        btnRight.addEventListener('click', () => {
            flashMouse('#8b5cf6'); // lila secundario
            // Aquí se podría enviar evento de clic derecho al servidor
        });
    }
    
}); 