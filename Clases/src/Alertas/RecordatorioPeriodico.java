package Alertas;

import Gestion.GestorAlertas;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RecordatorioPeriodico {
    private final GestorAlertas gestorAlertas;
    private final ScheduledExecutorService scheduler;

    public RecordatorioPeriodico(GestorAlertas gestorAlertas) {
        this.gestorAlertas = gestorAlertas;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void iniciarRecordatorios() {
        scheduler.scheduleAtFixedRate(() -> {
            gestorAlertas.agregarAlerta("Este es un recordatorio informativo", NivelUrgencia.INFO);
            System.out.println("Recordatorio informativo agregado.");
        }, 0, 10, TimeUnit.SECONDS);
    }

    public void detenerRecordatorios() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}

