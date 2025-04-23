package Alertas;

import Gestion.GestorPrestamos;
import Gestion.GestorReservas;
import Interfaces.Prestable;
import Recursos.RecursoDigital;
import Servicios.Reservas;

import java.util.Map;
import java.util.Queue;

import java.util.concurrent.PriorityBlockingQueue;

public class AlertaDisponibilidad{
    private final GestorReservas gestorReservas;
    private final GestorPrestamos gestorPrestamos;

    public AlertaDisponibilidad(GestorReservas gestorReservas, GestorPrestamos gestorPrestamos) {
        this.gestorReservas = gestorReservas;
        this.gestorPrestamos = gestorPrestamos;
    }

    public void arrancar() {
        // Obtener las reservas pendientes de recursos
        Map<Prestable, PriorityBlockingQueue<Reservas>> reservasPendientes = gestorReservas.getReservas();

        for (Map.Entry<Prestable, PriorityBlockingQueue<Reservas>> entry : reservasPendientes.entrySet()) {
            RecursoDigital recurso = (RecursoDigital) entry.getKey();

            // Verificar si el recurso está disponible
            if (recurso instanceof Prestable && ((Prestable) recurso).estaDisponible()) {
                Queue<Reservas> cola = entry.getValue();

                if (!cola.isEmpty()) {
                    Reservas reserva = cola.peek();

                    // Notificar que el recurso está disponible
                    System.out.println("\n📢 *** NOTIFICACIÓN DE DISPONIBILIDAD *** 📢");
                    System.out.println("Recurso disponible: " + recurso.getNombre());
                    System.out.println("Usuario en espera: " + reserva.getUsuario().getName());

                    // Realizar el préstamo de forma automática
                    try {
                        System.out.println("Realizando préstamo automático...");
                        gestorPrestamos.registrarPrestamo(reserva.getUsuario(), ((Prestable) recurso));
                        cola.poll(); // Eliminar la reserva de la cola
                        System.out.println("✅ Préstamo realizado con éxito.");
                    } catch (Exception e) {
                        System.out.println("❌ Error al realizar el préstamo: " + e.getMessage());
                    }
                }
            }
        }
    }
}
