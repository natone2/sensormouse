#!/usr/bin/env python3
"""
SensorMouse Server
Servidor multiplataforma para control de ratón virtual usando sensores IMU
"""

import socket
import threading
import json
import time
import math
import argparse
import sys
from typing import Dict, Tuple, Optional
import numpy as np
from scipy.spatial.transform import Rotation
import pyautogui
from colorama import init, Fore, Style

# Inicializar colorama para colores en terminal
init()

class KalmanFilter:
    """Filtro de Kalman para suavizado de movimiento"""
    
    def __init__(self, process_variance: float = 1e-5, measurement_variance: float = 1e-2):
        self.process_variance = process_variance
        self.measurement_variance = measurement_variance
        self.estimate = 0.0
        self.estimate_error = 1.0
    
    def update(self, measurement: float) -> float:
        """Actualiza el filtro con una nueva medición"""
        # Predicción
        prediction_error = self.estimate_error + self.process_variance
        
        # Actualización
        kalman_gain = prediction_error / (prediction_error + self.measurement_variance)
        self.estimate = self.estimate + kalman_gain * (measurement - self.estimate)
        self.estimate_error = (1 - kalman_gain) * prediction_error
        
        return self.estimate

class SensorCalibrator:
    """Sistema de calibración dinámica para compensar deriva del sensor"""
    
    def __init__(self, window_size: int = 100):
        self.window_size = window_size
        self.gyro_samples = []
        self.accel_samples = []
        self.calibration_complete = False
        self.bias = {'x': 0.0, 'y': 0.0, 'z': 0.0}
    
    def add_sample(self, gyro: Dict[str, float], accel: Dict[str, float]):
        """Añade una muestra para calibración"""
        self.gyro_samples.append(gyro)
        self.accel_samples.append(accel)
        
        # Mantener solo las últimas muestras
        if len(self.gyro_samples) > self.window_size:
            self.gyro_samples.pop(0)
            self.accel_samples.pop(0)
        
        # Calcular bias cuando tengamos suficientes muestras
        if len(self.gyro_samples) >= self.window_size and not self.calibration_complete:
            self._calculate_bias()
    
    def _calculate_bias(self):
        """Calcula el bias del sensor"""
        if len(self.gyro_samples) < self.window_size:
            return
        
        # Calcular bias del giroscopio
        gyro_x = [s['x'] for s in self.gyro_samples]
        gyro_y = [s['y'] for s in self.gyro_samples]
        gyro_z = [s['z'] for s in self.gyro_samples]
        
        self.bias['x'] = np.mean(gyro_x)
        self.bias['y'] = np.mean(gyro_y)
        self.bias['z'] = np.mean(gyro_z)
        
        self.calibration_complete = True
        print(f"{Fore.GREEN}✓ Calibración completada - Bias: {self.bias}{Style.RESET_ALL}")
    
    def get_calibrated_gyro(self, gyro: Dict[str, float]) -> Dict[str, float]:
        """Retorna valores del giroscopio compensados por bias"""
        if not self.calibration_complete:
            return gyro
        
        return {
            'x': gyro['x'] - self.bias['x'],
            'y': gyro['y'] - self.bias['y'],
            'z': gyro['z'] - self.bias['z']
        }

class MouseController:
    """Controlador del ratón del sistema"""
    
    def __init__(self, sensitivity: float = 1.0):
        self.sensitivity = sensitivity
        self.kalman_x = KalmanFilter()
        self.kalman_y = KalmanFilter()
        self.calibrator = SensorCalibrator()
        self.is_active = False
        self.last_position = pyautogui.position()
        
        # Configurar pyautogui
        pyautogui.FAILSAFE = False
        pyautogui.PAUSE = 0.001  # 1ms de pausa para baja latencia
    
    def process_sensor_data(self, gyro: Dict[str, float], accel: Dict[str, float]):
        """Procesa datos del sensor y mueve el ratón"""
        if not self.is_active:
            return
        
        # Calibrar sensores
        self.calibrator.add_sample(gyro, accel)
        calibrated_gyro = self.calibrator.get_calibrated_gyro(gyro)
        
        # Aplicar filtro de Kalman
        filtered_x = self.kalman_x.update(calibrated_gyro['x'])
        filtered_y = self.kalman_y.update(calibrated_gyro['y'])
        
        # Calcular movimiento del ratón
        delta_x = int(filtered_x * self.sensitivity * 10)
        delta_y = int(filtered_y * self.sensitivity * 10)
        
        # Mover ratón
        current_x, current_y = pyautogui.position()
        new_x = max(0, min(pyautogui.size().width, current_x + delta_x))
        new_y = max(0, min(pyautogui.size().height, current_y + delta_y))
        
        pyautogui.moveTo(new_x, new_y)
    
    def set_active(self, active: bool):
        """Activa/desactiva el control del ratón"""
        self.is_active = active
        status = "activado" if active else "desactivado"
        print(f"{Fore.YELLOW}Control del ratón {status}{Style.RESET_ALL}")
    
    def set_sensitivity(self, sensitivity: float):
        """Ajusta la sensibilidad del ratón"""
        self.sensitivity = max(0.1, min(5.0, sensitivity))
        print(f"{Fore.CYAN}Sensibilidad ajustada a: {self.sensitivity}{Style.RESET_ALL}")

class SensorMouseServer:
    """Servidor principal de SensorMouse"""
    
    def __init__(self, host: str = '0.0.0.0', port: int = 8080):
        self.host = host
        self.port = port
        self.server_socket = None
        self.clients = []
        self.mouse_controller = MouseController()
        self.running = False
        
        print(f"{Fore.BLUE}🐭 SensorMouse Server iniciando...{Style.RESET_ALL}")
        print(f"{Fore.CYAN}Escuchando en {host}:{port}{Style.RESET_ALL}")
    
    def start(self):
        """Inicia el servidor"""
        try:
            self.server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            self.server_socket.bind((self.host, self.port))
            self.server_socket.listen(5)
            
            self.running = True
            print(f"{Fore.GREEN}✓ Servidor iniciado correctamente{Style.RESET_ALL}")
            
            # Thread para aceptar conexiones
            accept_thread = threading.Thread(target=self._accept_connections)
            accept_thread.daemon = True
            accept_thread.start()
            
            # Thread para comandos del servidor
            command_thread = threading.Thread(target=self._command_interface)
            command_thread.daemon = True
            command_thread.start()
            
            # Mantener el servidor corriendo
            while self.running:
                time.sleep(0.1)
                
        except Exception as e:
            print(f"{Fore.RED}✗ Error al iniciar servidor: {e}{Style.RESET_ALL}")
            sys.exit(1)
    
    def _accept_connections(self):
        """Acepta conexiones de clientes"""
        while self.running:
            try:
                client_socket, address = self.server_socket.accept()
                print(f"{Fore.GREEN}✓ Cliente conectado desde {address}{Style.RESET_ALL}")
                
                # Thread para manejar cada cliente
                client_thread = threading.Thread(
                    target=self._handle_client,
                    args=(client_socket, address)
                )
                client_thread.daemon = True
                client_thread.start()
                
                self.clients.append(client_socket)
                
            except Exception as e:
                if self.running:
                    print(f"{Fore.RED}✗ Error aceptando conexión: {e}{Style.RESET_ALL}")
    
    def _handle_client(self, client_socket: socket.socket, address: Tuple[str, int]):
        """Maneja la comunicación con un cliente"""
        try:
            while self.running:
                data = client_socket.recv(1024)
                if not data:
                    break
                
                try:
                    message = json.loads(data.decode('utf-8'))
                    self._process_message(message)
                except json.JSONDecodeError:
                    print(f"{Fore.RED}✗ Mensaje inválido recibido{Style.RESET_ALL}")
                    
        except Exception as e:
            print(f"{Fore.RED}✗ Error con cliente {address}: {e}{Style.RESET_ALL}")
        finally:
            client_socket.close()
            if client_socket in self.clients:
                self.clients.remove(client_socket)
            print(f"{Fore.YELLOW}Cliente {address} desconectado{Style.RESET_ALL}")
    
    def _process_message(self, message: Dict):
        """Procesa mensajes recibidos del cliente"""
        msg_type = message.get('type')
        
        if msg_type == 'sensor_data':
            gyro = message.get('gyro', {})
            accel = message.get('accel', {})
            self.mouse_controller.process_sensor_data(gyro, accel)
            
        elif msg_type == 'command':
            command = message.get('command')
            if command == 'activate':
                self.mouse_controller.set_active(True)
            elif command == 'deactivate':
                self.mouse_controller.set_active(False)
            elif command == 'calibrate':
                self.mouse_controller.calibrator.calibration_complete = False
                print(f"{Fore.CYAN}Iniciando nueva calibración...{Style.RESET_ALL}")
            elif command.startswith('sensitivity:'):
                try:
                    sensitivity = float(command.split(':')[1])
                    self.mouse_controller.set_sensitivity(sensitivity)
                except (ValueError, IndexError):
                    print(f"{Fore.RED}✗ Sensibilidad inválida{Style.RESET_ALL}")
    
    def _command_interface(self):
        """Interfaz de comandos del servidor"""
        print(f"\n{Fore.CYAN}Comandos disponibles:{Style.RESET_ALL}")
        print("  'quit' - Salir del servidor")
        print("  'status' - Estado del servidor")
        print("  'clients' - Clientes conectados")
        print("  'calibrate' - Recalibrar sensores")
        print("  'sensitivity <valor>' - Ajustar sensibilidad")
        
        while self.running:
            try:
                command = input(f"\n{Fore.GREEN}SensorMouse> {Style.RESET_ALL}").strip().lower()
                
                if command == 'quit':
                    self.stop()
                    break
                elif command == 'status':
                    self._print_status()
                elif command == 'clients':
                    self._print_clients()
                elif command == 'calibrate':
                    self.mouse_controller.calibrator.calibration_complete = False
                    print(f"{Fore.CYAN}Calibración reiniciada{Style.RESET_ALL}")
                elif command.startswith('sensitivity'):
                    try:
                        sensitivity = float(command.split()[1])
                        self.mouse_controller.set_sensitivity(sensitivity)
                    except (ValueError, IndexError):
                        print(f"{Fore.RED}✗ Uso: sensitivity <valor>{Style.RESET_ALL}")
                        
            except KeyboardInterrupt:
                self.stop()
                break
            except EOFError:
                self.stop()
                break
    
    def _print_status(self):
        """Imprime el estado del servidor"""
        print(f"\n{Fore.CYAN}=== Estado del Servidor ==={Style.RESET_ALL}")
        print(f"Servidor: {'Activo' if self.running else 'Inactivo'}")
        print(f"Clientes conectados: {len(self.clients)}")
        print(f"Control del ratón: {'Activo' if self.mouse_controller.is_active else 'Inactivo'}")
        print(f"Sensibilidad: {self.mouse_controller.sensitivity}")
        print(f"Calibración: {'Completada' if self.mouse_controller.calibrator.calibration_complete else 'Pendiente'}")
    
    def _print_clients(self):
        """Imprime información de clientes conectados"""
        print(f"\n{Fore.CYAN}=== Clientes Conectados ==={Style.RESET_ALL}")
        if not self.clients:
            print("No hay clientes conectados")
        else:
            for i, client in enumerate(self.clients, 1):
                try:
                    address = client.getpeername()
                    print(f"Cliente {i}: {address}")
                except:
                    print(f"Cliente {i}: Desconectado")
    
    def stop(self):
        """Detiene el servidor"""
        self.running = False
        print(f"\n{Fore.YELLOW}Deteniendo servidor...{Style.RESET_ALL}")
        
        # Cerrar conexiones de clientes
        for client in self.clients:
            try:
                client.close()
            except:
                pass
        self.clients.clear()
        
        # Cerrar socket del servidor
        if self.server_socket:
            try:
                self.server_socket.close()
            except:
                pass
        
        print(f"{Fore.GREEN}✓ Servidor detenido{Style.RESET_ALL}")

def main():
    """Función principal"""
    parser = argparse.ArgumentParser(description='SensorMouse Server')
    parser.add_argument('--host', default='0.0.0.0', help='Host del servidor (default: 0.0.0.0)')
    parser.add_argument('--port', type=int, default=8080, help='Puerto del servidor (default: 8080)')
    
    args = parser.parse_args()
    
    try:
        server = SensorMouseServer(args.host, args.port)
        server.start()
    except KeyboardInterrupt:
        print(f"\n{Fore.YELLOW}Interrumpido por el usuario{Style.RESET_ALL}")
    except Exception as e:
        print(f"{Fore.RED}Error fatal: {e}{Style.RESET_ALL}")
        sys.exit(1)

if __name__ == '__main__':
    main() 