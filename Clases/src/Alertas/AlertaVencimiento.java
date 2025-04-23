package Alertas;

import Gestion.GestorPrestamos;
import Interfaces.Renovable;
import Recursos.RecursoDigital;
import Servicios.Prestamos;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AlertaVencimiento {
    private final GestorPrestamos gestorPrestamos;
    private final Scanner scanner;

    public AlertaVencimiento(GestorPrestamos gestorPrestamos) {
        this.gestorPrestamos = gestorPrestamos;
        this.scanner = new Scanner(System.in);
    }

    public void verificarAlertas() {
        List<Prestamos> prestamosActivos = gestorPrestamos.getTodosLosPrestamos();
        LocalDate hoy = LocalDate.now();

        for (Prestamos prestamo : prestamosActivos) {
            LocalDate fechaDevolucion = prestamo.getFechaDevolucionEstimada();

            if (fechaDevolucion.equals(hoy) || fechaDevolucion.equals(hoy.plusDays(1))) {
                mostrarAlerta(prestamo, fechaDevolucion.equals(hoy));
            }
        }
    }

    private void mostrarAlerta(Prestamos prestamo, boolean esHoy) {
        System.out.println("\n⚠️ *** ALERTA DE VENCIMIENTO ***");
        System.out.println("Recurso: " + ((RecursoDigital) prestamo.getRecurso()).getNombre());
        System.out.println("Usuario: " + prestamo.getUsuario().getName());
        System.out.println("Fecha de devolución: " + prestamo.getFechaDevolucionEstimada());
        System.out.println("Estado: " + (esHoy ? "HOY vence el préstamo" : "Vence MAÑANA"));

        System.out.print("¿Desea renovarlo ahora? (s/n): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            try {
                gestorPrestamos.renovarPrestamo((Renovable) prestamo.getRecurso(), prestamo.getUsuario());
                System.out.println("✅ Renovación exitosa.");
            } catch (Exception e) {
                System.out.println("❌ Error al renovar: " + e.getMessage());
            }
        }
    }
}
