package Alertas;

import Gestion.GestorPrestamos;
import Servicios.Prestamos;

import java.time.LocalDate;
import java.util.List;

public class AlertaThread extends Thread {
    private GestorPrestamos gestor;
    private List<Prestamos> alertasPendientes;
    private boolean activo = true;

    public AlertaThread(GestorPrestamos gestor, List<Prestamos> alertasPendientes) {
        this.gestor = gestor;
        this.alertasPendientes = alertasPendientes;
    }

    public void detener() {
        activo = false;
    }

    @Override
    public void run() {
        while (activo) {
            List<Prestamos> prestamos = gestor.getTodosLosPrestamos();
            LocalDate hoy = LocalDate.now();
            for (Prestamos p : prestamos) {
                if (p.getFechaDevolucionEstimada().equals(hoy.plusDays(1))) {
                    synchronized (alertasPendientes) {
                        if (!alertasPendientes.contains(p)) {
                            alertasPendientes.add(p);
                        }
                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
