#!/bin/bash

# Script de instalación automática para DroidMouse
# Compatible con Ubuntu, Debian, Arch Linux y derivados

set -e

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para imprimir mensajes
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Función para detectar el gestor de paquetes
detect_package_manager() {
    if command -v apt-get &> /dev/null; then
        echo "apt"
    elif command -v pacman &> /dev/null; then
        echo "pacman"
    elif command -v yum &> /dev/null; then
        echo "yum"
    elif command -v dnf &> /dev/null; then
        echo "dnf"
    else
        echo "unknown"
    fi
}

# Función para instalar dependencias del sistema
install_system_dependencies() {
    local pkg_manager=$(detect_package_manager)
    
    print_status "Detectado gestor de paquetes: $pkg_manager"
    
    case $pkg_manager in
        "apt")
            print_status "Instalando dependencias del sistema (Ubuntu/Debian)..."
            sudo apt-get update
            sudo apt-get install -y python3 python3-pip python3-venv python3-dev
            sudo apt-get install -y libx11-dev libxtst-dev libxrandr-dev
            sudo apt-get install -y scrot
            ;;
        "pacman")
            print_status "Instalando dependencias del sistema (Arch Linux)..."
            sudo pacman -Syu --noconfirm
            sudo pacman -S --noconfirm python python-pip python-virtualenv
            sudo pacman -S --noconfirm xorg-server xorg-xrandr
            sudo pacman -S --noconfirm scrot
            ;;
        "yum"|"dnf")
            print_status "Instalando dependencias del sistema (Fedora/RHEL)..."
            sudo $pkg_manager update -y
            sudo $pkg_manager install -y python3 python3-pip python3-devel
            sudo $pkg_manager install -y libX11-devel libXtst-devel libXrandr-devel
            sudo $pkg_manager install -y scrot
            ;;
        *)
            print_warning "Gestor de paquetes no reconocido. Instalación manual requerida."
            print_status "Por favor, instala manualmente:"
            print_status "- Python 3.7+"
            print_status "- pip"
            print_status "- python3-dev"
            print_status "- libx11-dev libxtst-dev libxrandr-dev"
            print_status "- scrot"
            return 1
            ;;
    esac
}

# Función para verificar Python
check_python() {
    if command -v python3 &> /dev/null; then
        local version=$(python3 --version | cut -d' ' -f2)
        print_success "Python encontrado: $version"
        return 0
    else
        print_error "Python 3 no encontrado"
        return 1
    fi
}

# Función para crear entorno virtual
create_virtual_env() {
    print_status "Creando entorno virtual..."
    
    if [ -d "venv" ]; then
        print_warning "Entorno virtual ya existe. Eliminando..."
        rm -rf venv
    fi
    
    python3 -m venv venv
    source venv/bin/activate
    
    print_success "Entorno virtual creado y activado"
}

# Función para instalar dependencias de Python
install_python_dependencies() {
    print_status "Instalando dependencias de Python..."
    
    # Actualizar pip
    pip install --upgrade pip
    
    # Instalar dependencias
    pip install -r server/requirements.txt
    
    print_success "Dependencias de Python instaladas"
}

# Función para configurar firewall
setup_firewall() {
    print_status "Configurando firewall..."
    
    # Detectar gestor de firewall
    if command -v ufw &> /dev/null; then
        sudo ufw allow 8080/tcp
        print_success "Puerto 8080 abierto en UFW"
    elif command -v firewall-cmd &> /dev/null; then
        sudo firewall-cmd --permanent --add-port=8080/tcp
        sudo firewall-cmd --reload
        print_success "Puerto 8080 abierto en firewalld"
    else
        print_warning "Firewall no detectado. Abre manualmente el puerto 8080"
    fi
}

# Función para crear script de inicio
create_startup_script() {
    print_status "Creando script de inicio..."
    
    cat > start_droidmouse.sh << 'EOF'
#!/bin/bash

# Script de inicio para DroidMouse
cd "$(dirname "$0")"

# Activar entorno virtual
source venv/bin/activate

# Ejecutar servidor
python server/main.py "$@"
EOF
    
    chmod +x start_droidmouse.sh
    print_success "Script de inicio creado: ./start_droidmouse.sh"
}

# Función para crear servicio systemd
create_systemd_service() {
    if ! command -v systemctl &> /dev/null; then
        print_warning "systemd no disponible. Saltando creación de servicio."
        return
    fi
    
    print_status "Creando servicio systemd..."
    
    local current_dir=$(pwd)
    local user=$(whoami)
    
    sudo tee /etc/systemd/system/droidmouse.service > /dev/null << EOF
[Unit]
Description=DroidMouse Server
After=network.target

[Service]
Type=simple
User=$user
WorkingDirectory=$current_dir
ExecStart=$current_dir/venv/bin/python $current_dir/server/main.py
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF
    
    sudo systemctl daemon-reload
    print_success "Servicio systemd creado: droidmouse.service"
    print_status "Para habilitar el servicio: sudo systemctl enable droidmouse"
    print_status "Para iniciar el servicio: sudo systemctl start droidmouse"
}

# Función principal
main() {
    echo -e "${BLUE}"
    echo "╔══════════════════════════════════════════════════════════════╗"
    echo "║                    DroidMouse Installer                      ║"
    echo "║                Instalador Automático v1.0                    ║"
    echo "╚══════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
    
    print_status "Iniciando instalación de DroidMouse..."
    
    # Verificar que estamos en el directorio correcto
    if [ ! -f "server/main.py" ]; then
        print_error "No se encontró server/main.py"
        print_error "Ejecuta este script desde el directorio raíz de DroidMouse"
        exit 1
    fi
    
    # Instalar dependencias del sistema
    install_system_dependencies
    
    # Verificar Python
    if ! check_python; then
        print_error "Python 3 no está instalado. Instalación fallida."
        exit 1
    fi
    
    # Crear entorno virtual
    create_virtual_env
    
    # Instalar dependencias de Python
    install_python_dependencies
    
    # Configurar firewall
    setup_firewall
    
    # Crear script de inicio
    create_startup_script
    
    # Crear servicio systemd
    create_systemd_service
    
    echo -e "${GREEN}"
    echo "╔══════════════════════════════════════════════════════════════╗"
    echo "║                    Instalación Completada                    ║"
    echo "╚══════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
    
    print_success "DroidMouse ha sido instalado correctamente!"
    print_status "Para iniciar el servidor:"
    print_status "  ./start_droidmouse.sh"
    print_status ""
    print_status "Para habilitar el servicio automático:"
    print_status "  sudo systemctl enable droidmouse"
    print_status "  sudo systemctl start droidmouse"
    print_status ""
    print_status "Para ver el estado del servicio:"
    print_status "  sudo systemctl status droidmouse"
    print_status ""
    print_status "Documentación disponible en: docs/INSTALLATION.md"
}

# Ejecutar función principal
main "$@" 