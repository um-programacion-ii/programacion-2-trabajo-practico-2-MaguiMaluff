# Documentacion
# Guía de Uso del Sistema de Gestión de Recursos Digitales

## ✨ Descripción General del Sistema

Este sistema de gestión de recursos digitales permite administrar usuarios y recursos (libros, revistas, audiolibros), realizar préstamos, renovaciones, reservas, generar reportes y recibir alertas de vencimiento y disponibilidad.

El objetivo principal es facilitar la organización y el control del acceso a materiales digitales de una biblioteca.

---

## ⚖️ Arquitectura del Sistema

El sistema está dividido en los siguientes componentes principales:

### 1. **Clases de Dominio**
- `Usuario`: representa al usuario del sistema.
- `RecursoDigital` (abstracta): base para `Libro`, `Revista` y `AudioLibro`.
- `CategoriaRecurso`: enum para categorizar los recursos.

### 2. **Interfaces**
- `Prestable`: para recursos que pueden ser prestados.
- `Renovable`: para recursos que se pueden renovar.

### 3. **Gestores**
- `GestorUsuarios`: manejo de usuarios.
- `GestorRecursos`: manejo de recursos.
- `GestorPrestamos`: manejo de préstamos y renovaciones.
- `GestorReservas`: manejo de reservas y notificaciones de disponibilidad.
- `GestorNotificaciones`: envio de notificaciones concurrentes.

### 4. **Alertas**
- `AlertaVencimiento`: detecta préstamos próximos a vencer.
- `AlertaDisponibilidad`: notifica cuando un recurso reservado está disponible.
- `Alertas`: notifican con un grado de urgencia.

### 5. **Consola**
Clase principal de interacción con el usuario. Muestra menús y captura las acciones.

---

## 🔄 Flujo de Trabajo del Sistema
1. Se cargan usuarios y recursos en el sistema.
2. El usuario accede al menú desde consola.
3. Puede realizar acciones como:
    - Agregar/buscar/listar usuarios o recursos.
    - Realizar préstamos o renovaciones.
    - Reservar recursos no disponibles.
    - Recibir alertas de vencimiento y disponibilidad.
    - Generar reportes en segundo plano.

---

## 🚀 Puesta en Marcha

### Requisitos Previos
- JDK 17 o superior
- IntelliJ IDEA o cualquier IDE compatible con Java

### Proceso de Compilación y Ejecución
1. Clonar el repositorio o copiar el código fuente.
2. Abrir el proyecto en tu IDE.
3. Compilar el proyecto.
4. Ejecutar la clase `Main.java` que instancia y lanza el menú de `Consola`.

---

## ✅ Ejemplos de Uso y Pruebas

### 1. **Agregar un Usuario**
- Ir a "Gestión de Usuarios > Agregar Usuario"
- Ingresar nombre, ID, etc.

### 2. **Agregar un Recurso**
- Seleccionar "Gestión de Recursos > Agregar Recurso"
- Elegir tipo: Libro, Revista, etc. y completar datos.

### 3. **Realizar un Préstamo**
- Seleccionar "Gestión de Préstamos > Realizar Préstamo"
- Ingresar usuario y recurso.

### 4. **Reservar un Recurso No Disponible**
- Intentar prestar un recurso ya prestado.

### 5. **Alertas de Vencimiento y Disponibilidad**
- Se muestran al iniciar el menú.

### 6. **Generar Reporte en Segundo Plano**
- Elegir "Mostrar Reportes"
- Se verá un mensaje de carga y luego el resultado.

---

## 🔧 Preferencias de Alerta

Cada usuario puede configurar:
- Nivel de alerta: INFO, WARNING, ERROR
- Frecuencia de recordatorios
- Si desea recibir notificaciones automáticas al iniciar sesión

---

## 🔄 Ejemplo de Flujo Completo
1. Agregar usuario "Lucia".
2. Agregar libro "Clean Code".
3. Realizar préstamo de "Clean Code" a "Lucia".
4. Intentar prestar "Clean Code" a "Sofia": se reserva.
5. Se devuelve "Clean Code": alerta de disponibilidad para "Sofia".
6. Se genera un reporte con estadísticas completas.

---

## 🌟 Valor Agregado
- Sistema multihilo con `ExecutorService`
- Notificaciones y alertas interactivas
- Reportes asincrónicos con barra de progreso
- Historial y configuración de preferencias del usuario

---

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

Etapa 4 Issue #19

GeneradorReportes fue generado con IA

Este README tambien fue generado con IA 😄