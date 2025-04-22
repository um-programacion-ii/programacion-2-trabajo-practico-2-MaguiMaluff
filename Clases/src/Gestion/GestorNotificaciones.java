package Gestion;

import Interfaces.Notificacion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GestorNotificaciones {
    private List<Notificacion> canales;
    private ExecutorService executor;

    public GestorNotificaciones() {
        this.canales = new ArrayList<>();
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void agregarCanal(Notificacion notificacion) {
        canales.add(notificacion);
    }

    public void enviarNotificacion(String mensaje) {
        List<Future<?>> tareas = new ArrayList<>();

        for (Notificacion canal : canales) {
            Future<?> tarea = executor.submit(() -> canal.enviar(mensaje));
            tareas.add(tarea);
        }

        for (Future<?> tarea : tareas) {
            try {
                tarea.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    public void cerrar() {
        executor.shutdown();
    }
}
