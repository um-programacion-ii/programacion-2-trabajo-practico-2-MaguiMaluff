package Servicios;

import Gestion.GestorRecursos;
import Gestion.GestorUsuarios;

public class GeneradorReportes implements Runnable {
    private final GestorRecursos gestorRecursos;
    private final GestorUsuarios gestorUsuarios;

    public GeneradorReportes(GestorRecursos gestorRecursos, GestorUsuarios gestorUsuarios) {
        this.gestorRecursos = gestorRecursos;
        this.gestorUsuarios = gestorUsuarios;
    }

    @Override
    public void run() {
        try {
            System.out.println("Generando reporte...");

            Thread.sleep(1000);
            System.out.println(" Procesando recursos más prestados...");
            gestorRecursos.obtenerRecursosMasPrestados().forEach(r ->
                    System.out.println(r.getNombre() + " - Prestado " + r.getVecesPrestado() + " veces")
            );

            Thread.sleep(1000);
            System.out.println("\n Procesando usuarios más activos...");
            gestorUsuarios.obtenerUsuariosMasActivos().forEach(u ->
                    System.out.println(u.getName() + " - " + u.getPrestamosRealizados() + " préstamos")
            );

            Thread.sleep(1000);
            System.out.println("\nProcesando estadísticas por categoría...");
            gestorRecursos.estadisticasPorCategoria().forEach((categoria, totalPrestamos) ->
                    System.out.println(categoria + ": " + totalPrestamos + " préstamos")
            );

            System.out.println("\n✅ Reporte generado con éxito.");

        } catch (InterruptedException e) {
            System.out.println("La generación del reporte fue interrumpida.");
            Thread.currentThread().interrupt();
        }
    }
}

