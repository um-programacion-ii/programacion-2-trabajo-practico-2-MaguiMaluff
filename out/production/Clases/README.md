# Documentacion

### Uso de IA

Etapa 1 - Issue #6

Enum EstadoRecurso fue generado por IA

```java
public enum EstadoRecurso {
    DISPONIBLE,
    PRESTADO,
    RESERVADO,
    EN_REPARACION,
    NO_DISPONIBLE
}
```

Etapa 3 - Issue #12

Funcion ReservarRecurso fue generado con IA

```java
public void reservarRecurso(Usuario usuario, Prestable recurso, LocalDateTime fechaDeseada) {
        if (fechaDeseada.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha deseada debe ser futura.");
        }
    
        if (!recurso.estaDisponible()) {
            LocalDateTime fechaDevolucion = recurso.getFechaDevolucion();
            if (fechaDeseada.isBefore(fechaDevolucion)) {
                throw new RecursoNoDisponibleException("El recurso está prestado hasta el " + fechaDevolucion + ", no se puede reservar para " + fechaDeseada + ".");
            }
        }
    
        reservasPorRecurso.putIfAbsent(recurso, new PriorityBlockingQueue<>());
        PriorityBlockingQueue<Reservas> cola = reservasPorRecurso.get(recurso);
    
        boolean yaReservadoEseDia = cola.stream()
                .anyMatch(r -> r.getFechaReserva().toLocalDate().equals(fechaDeseada.toLocalDate()));
    
        if (yaReservadoEseDia) {
            throw new RecursoNoDisponibleException("Ya existe una reserva para esa fecha.");
        }
    
        Reservas nueva = new Reservas(usuario, fechaDeseada);
        cola.add(nueva);
        notificacionesEmail.enviarNotificacion("Reserva realizada para el " + fechaDeseada);
        nueva.showInfo();
    }
```

Etapa 3 Issue #12

Parte del menú de reservas en consola fue generado con IA

```java
    System.out.println("Ingrese la fecha deseada para la reserva (formato: yyyy-MM-dd HH:mm):");
    String fechaStr = myObj.nextLine();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime fecha = LocalDateTime.parse(fechaStr, formatter);
```

Etapa 3 Issue #13

La funcion enviarNotificaciones, perteneciente a GestorNotificaciones fue generada con IA

```java
    public void enviarNotificacion(String mensaje) {
        List<Future<?>> tareas = new ArrayList<>();
    
        for (Notificacion canal : canales) {
            Future<?> tarea = executor.submit(() -> canal.enviar(mensaje));
            tareas.add(tarea);
        }
    
        for (Future<?> tarea : tareas) {
            try {
                tarea.get(); // Espera a que la notificación termine
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace(); // Manejo simple de errores por ahora
            }
        }
    }

```

Etapa 4 Issue #14

En gestorRecursos, la función que busca las categorias más usadas fue generada cin IA.

```java
    public Map<CategoriaRecurso, Long> estadisticasPorCategoria() {
        return recursos.stream()
                .filter(r -> r instanceof Prestable && (r).getVecesPrestado() > 0)
                .collect(Collectors.groupingBy(
                        RecursoDigital::getCategoria,
                        Collectors.summingLong(r -> (r).getVecesPrestado())
                ));
    }
    
    
```

ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

public void iniciarAlertasDisponibilidad() {
Runnable alerta = new AlertaDisponibilidad(myGestorReservas, myGestorPrestamos, myObj);
scheduler.scheduleAtFixedRate(alerta, 10, 30, TimeUnit.SECONDS); // comienza en 10s, se repite cada 30s
}