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