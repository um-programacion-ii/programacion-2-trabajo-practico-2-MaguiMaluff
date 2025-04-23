import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Alertas.AlertaDisponibilidad;
import Alertas.AlertaVencimiento;
import Alertas.NivelUrgencia;
import Alertas.RecordatorioPeriodico;
import Excepciones.DatosErroneosException;
import Excepciones.RecursoNoDisponibleException;
import Excepciones.RecursoNoEncontradoException;
import Excepciones.UsuarioNoEncontradoException;
import Gestion.*;
import Interfaces.Prestable;
import Interfaces.Renovable;
import Recursos.CategoriaRecurso;
import Recursos.EstadoRecurso;
import Modelos.Usuario;
import Recursos.RecursoDigital;
import Servicios.ConfiguracionNotificaciones;
import Servicios.GeneradorReportes;

public class Consola {
    Scanner myObj = new Scanner(System.in);
    GestorUsuarios myGestorUsuarios = new GestorUsuarios();
    GestorRecursos myGestorRecursos = new GestorRecursos();
    GestorReservas myGestorReservas = new GestorReservas();
    GestorPrestamos myGestorPrestamos = new GestorPrestamos(myGestorReservas);
    GestorAlertas gestorAlertas = new GestorAlertas();
    RecordatorioPeriodico recordatorioPeriodico = new RecordatorioPeriodico(gestorAlertas);
    ConfiguracionNotificaciones configuracionNotificaciones = new ConfiguracionNotificaciones();



    public boolean menu() {
        int choice = -1;

        do {
            AlertaVencimiento alertaVencimiento = new AlertaVencimiento(myGestorPrestamos);
            alertaVencimiento.verificarAlertas();

            AlertaDisponibilidad AlertaDisponibilidad = new AlertaDisponibilidad(myGestorReservas, myGestorPrestamos);
            AlertaDisponibilidad.arrancar();

            System.out.println("""
                1. Gestion Usuarios
                \
                2. Gestion Recursos
                \
                3. Gestion Prestamos
                \
                4. Gestion Reservas
                \
                5. Mostrar Reportes
                \
                6. Gestion Alertas
                \
                0. Salir
                """);

            try {
                choice = Integer.parseInt(myObj.nextLine());
            } catch (Exception e) {
                System.out.println("Opcion no disponible");
            }

            switch (choice) {
                case 1:
                    gestionUsuario();
                    break;
                case 2:
                    gestionRecursos();break;
                case 3:
                    gestionPrestamos();
                    break;
                case 4:
                    gestionReservas();
                    break;
                case 5:
                    generarReporteEnSegundoPlano();
                    break;
                case 6:
                    gestionAlertas();
                    break;
                case 0:
                    System.out.println("Adios");
                    myGestorPrestamos.getMyGestorNotificaciones().cerrar();
                    return false;
            }
        } while (choice != 0);
        return true;
    }

    public void gestionUsuario() {
        int choice = -1;
        do {
            System.out.println("""
                    1. Agregar Usuarios
                    \
                    2. Buscar Usuario por Nombre
                    \
                    3. Buscar Usuario por Email
                    \
                    0. Menú anterior
                    """);
            try {
                choice = Integer.parseInt(myObj.nextLine());
            } catch (Exception e) {
                System.out.println("Opcion no disponible");
            }
            switch (choice) {
                case 1:
                    try {
                        System.out.println("Nombre: ");
                        String name = myObj.nextLine();
                        System.out.println("Mail: ");
                        String mail = myObj.nextLine();
                        myGestorUsuarios.newUser(name, mail);
                    } catch (DatosErroneosException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        myGestorUsuarios.showInfo(this.pedirUsuario());
                    } catch (UsuarioNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Email: ");
                    String email = myObj.nextLine();
                    try {
                        myGestorUsuarios.searchUserEmail(email);
                    } catch (UsuarioNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 0:
                    break;
                default:
                    System.out.println("Opcion Invalida");
                    break;
            }
        } while (choice != 0);
    }

    public void gestionRecursos() {
        int choice = -1;
        do {
            System.out.println("""
                    1. Agregar Recurso
                    \
                    2. Buscar Recurso por Nombre
                    \
                    3. Listar Todos los Recursos
                    \
                    4. Filtrar por Tipo
                    \
                    5. Filtrar Recursos Ordenados
                    \
                    6. Buscar por Categoria
                    \
                    0. Menú anterior
                    """);
            try {
                choice = Integer.parseInt(myObj.nextLine());
            } catch (Exception e) {
                System.out.println("Opcion no disponible");
            }
            switch (choice) {
                case 1:
                    agregarRecurso();
                    break;

                case 2:
                    try {
                        RecursoDigital recurso = this.pedirRecurso();
                        recurso.showInfo();
                    } catch (RecursoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    myGestorRecursos.listarRecursos();
                    break;

                case 4:
                    System.out.println("Tipo de recurso (Libro, Revista, AudioLibro): ");
                    String tipo = "";
                    try {
                        tipo = myObj.nextLine().trim().toLowerCase();
                    } catch (Exception e) {
                        System.out.println("Opcion no disponible");
                    }

                    try {
                        switch (tipo) {
                            case "libro":
                                myGestorRecursos.buscarPorTipo(Modelos.Libro.class);
                                break;
                            case "revista":
                                myGestorRecursos.buscarPorTipo(Modelos.Revista.class);
                                break;
                            case "audiolibro":
                                myGestorRecursos.buscarPorTipo(Modelos.AudioLibro.class);
                                break;
                            default:
                                System.out.println("Tipo no válido");
                        }
                    } catch (RecursoNoDisponibleException e) {
                        System.out.println(e.getMessage());
                    }
                case 5:
                    myGestorRecursos.ordenarPorNombre();
                    break;
                case 6:
                    myGestorRecursos.mostrarCategoriasDisponibles();
                    System.out.println("Ingrese categoría:");
                    String cat = myObj.nextLine().trim().toUpperCase();
                    try {
                        CategoriaRecurso categoria = CategoriaRecurso.valueOf(cat);
                        myGestorRecursos.buscarPorCategoria(categoria);
                    } catch (IllegalArgumentException e) {
                        System.out.println("La categoría ingresada no es válida.");
                    } catch (DatosErroneosException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion Invalida");
            }
        } while (choice != 0);

    }

    public void agregarRecurso() {
        int choice = -1;
        do {

            System.out.println("""
                    1. Agregar Libro
                    \
                    2. Agregar Revista
                    \
                    3. Agregar Audiolibre
                    \
                    0. Menú anterior
                    """);
            try {
                choice = Integer.parseInt(myObj.nextLine());
            } catch (Exception e) {
                System.out.println("Opcion no disponible");
            }
            try {
                switch (choice) {
                    case 1:
                        System.out.println("Nombre: ");
                        String name = myObj.nextLine();
                        System.out.println("Autor: ");
                        String autor = myObj.nextLine();
                        System.out.println("Categoria (EDUCACION, ENTRETENIMIENTO, TECNOLOGIA, CIENCIA, HISTORIA, OTROS):");
                        String cat = myObj.nextLine().trim().toUpperCase();
                        CategoriaRecurso categoria = CategoriaRecurso.valueOf(cat);

                        myGestorRecursos.agregarLibro(EstadoRecurso.DISPONIBLE, autor, name, categoria);
                        break;
                    case 2:
                        System.out.println("Nombre: ");
                        String name_2 = myObj.nextLine();
                        System.out.println("Marca: ");
                        String marca = myObj.nextLine();
                        System.out.println("Issue: ");
                        Integer issue = Integer.valueOf(myObj.nextLine());
                        System.out.println("Categoria (EDUCACION, ENTRETENIMIENTO, TECNOLOGIA, CIENCIA, HISTORIA, OTROS):");
                        String cat_2 = myObj.nextLine().trim().toUpperCase();
                        CategoriaRecurso categoria_2 = CategoriaRecurso.valueOf(cat_2);

                        myGestorRecursos.agregarRevista(EstadoRecurso.DISPONIBLE, marca, issue, name_2, categoria_2);
                        break;
                    case 3:
                        System.out.println("Nombre: ");
                        String name_3 = myObj.nextLine();
                        System.out.println("Autor: ");
                        String autor_3 = myObj.nextLine();
                        System.out.println("Lector: ");
                        String lector = myObj.nextLine();
                        System.out.println("Categoria (EDUCACION, ENTRETENIMIENTO, TECNOLOGIA, CIENCIA, HISTORIA, OTROS):");
                        String cat_3 = myObj.nextLine().trim().toUpperCase();
                        CategoriaRecurso categoria_3 = CategoriaRecurso.valueOf(cat_3);

                        myGestorRecursos.agregarAudioLibro(EstadoRecurso.DISPONIBLE, autor_3, lector, name_3, categoria_3);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion Invalida");
                        break;
                }

            } catch (DatosErroneosException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("La categoría ingresada no es válida.");
            }
        } while (choice != 0);
    }

    public void gestionPrestamos() {
        int choice = -1;
        do {
            System.out.println("""
                    1. Solicitar Prestamo
                    \
                    2. Renovar Prestamo
                    \
                    3. Devolver Prestamo
                    \
                    4. Buscar Prestamos
                    \
                    5. Listar Prestamos
                    \
                    6. Mostrar Recursos Disponibles
                    \
                    0. Menú anterior
                    """);
            try {
                choice = Integer.parseInt(myObj.nextLine());
            } catch (Exception e) {
                System.out.println("Opcion no disponible");
            }
            switch (choice) {
                case 1:
                    RecursoDigital recurso = null;
                    Usuario usuario = null;
                    try {
                        recurso = this.pedirRecurso();
                        usuario = this.pedirUsuario();


                        if (recurso instanceof Prestable prestable && usuario != null) {
                            try {
                                myGestorPrestamos.registrarPrestamo(usuario, ((Prestable) recurso));
                                System.out.println("Recurso prestado exitosamente.");
                            } catch (RecursoNoDisponibleException | UsuarioNoEncontradoException | DatosErroneosException | RecursoNoEncontradoException e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (recurso == null) {
                            System.out.println("Recurso no encontrado.");
                        } else {
                            System.out.println("No se puede prestar.");
                        }
                    } catch (UsuarioNoEncontradoException | RecursoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    RecursoDigital recurso_2 = null;
                    Usuario usuario_2 = null;
                    try {
                        recurso_2 = this.pedirRecurso();
                        usuario_2 = this.pedirUsuario();
                        if (recurso_2 instanceof Renovable renovable) {
                            myGestorPrestamos.renovarPrestamo(renovable, usuario_2);
                        } else {
                            System.out.println("No se puede renovar este recurso.");
                        }
                    } catch (RecursoNoDisponibleException | UsuarioNoEncontradoException | RecursoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try{
                        RecursoDigital recursoDevolver = this.pedirRecurso();
                        myGestorPrestamos.devolverPrestamo(recursoDevolver);
                        System.out.println("Recurso devuelto correctamente.");
                    }catch (RecursoNoEncontradoException | RecursoNoDisponibleException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:

                    try {
                        myGestorPrestamos.buscarPrestamo(this.pedirRecurso());
                    } catch (RecursoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    myGestorPrestamos.listarPrestamos();
                    break;
                case 6:
                    myGestorRecursos.mostrarRecursosDisponibles();
                case 0:
                    break;
                default:
                    System.out.println("Opcion Invalida");
                    break;
            }
        } while (choice != 0);
    }

    public void gestionReservas() {
        int choice = -1;
        do {
            System.out.println("""
                1. Reservar Recurso
                \
                2. Ver Reservas de un Recurso
                \
                0. Menú anterior
                """);

            try {
                choice = Integer.parseInt(myObj.nextLine());
            } catch (Exception e) {
                System.out.println("Opción inválida.");
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.println("Ingrese la fecha deseada para la reserva (formato: yyyy-MM-dd HH:mm):");
                        String fechaStr = myObj.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate fecha = LocalDate.parse(fechaStr, formatter);

                        RecursoDigital recurso = this.pedirRecurso();
                        recurso.showInfo();
                        Usuario usuario = this.pedirUsuario();

                        if (recurso instanceof Prestable prestable) {
                            myGestorReservas.reservarRecurso(usuario, prestable, fecha);
                        } else {
                            System.out.println("Este recurso no puede ser reservado.");
                        }
                    } catch (RecursoNoEncontradoException | UsuarioNoEncontradoException |
                             RecursoNoDisponibleException | IllegalArgumentException
                             | DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        RecursoDigital recurso = this.pedirRecurso();
                        myGestorReservas.mostrarReservas(recurso);
                    } catch (RecursoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (choice != 0);
    }

    public void gestionAlertas() {
        int choice = -1;
        do{
            System.out.println("""
                1. Configurar nivel de urgencia de notificaciones
                \
                2. Ver historial de alertas
                \
                3. Iniciar recordatorios periódicos
                \
                4. Detener recordatorios periódicos
                \
                0. Salir
                """);

            int opcion = myObj.nextInt();
            myObj.nextLine();

            switch (opcion) {
                case 1:
                    configurarNivelUrgencia();
                    break;
                case 2:
                    gestorAlertas.mostrarHistorial();
                    break;
                case 3:
                    recordatorioPeriodico.iniciarRecordatorios();
                    break;
                case 4:
                    recordatorioPeriodico.detenerRecordatorios();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }while (choice !=0);
    }

    private void configurarNivelUrgencia() {
        System.out.println("Seleccione el nivel de urgencia para las notificaciones:");
        System.out.println("1. INFO");
        System.out.println("2. WARNING");
        System.out.println("3. ERROR");

        int opcion = myObj.nextInt();
        myObj.nextLine();

        switch (opcion) {
            case 1:
                configuracionNotificaciones.establecerNivelUrgencia(NivelUrgencia.INFO);
                break;
            case 2:
                configuracionNotificaciones.establecerNivelUrgencia(NivelUrgencia.WARNING);
                break;
            case 3:
                configuracionNotificaciones.establecerNivelUrgencia(NivelUrgencia.ERROR);
                break;
            case 0:
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

        System.out.println("Nivel de urgencia configurado a: " + configuracionNotificaciones.obtenerNivelUrgencia());
    }


    private RecursoDigital pedirRecurso() throws RecursoNoEncontradoException{
        System.out.print("Nombre del recurso: ");
        String nombre = myObj.nextLine();
        return myGestorRecursos.buscarPorNombre(nombre);
        }


    private Usuario pedirUsuario() {
        System.out.print("Nombre del usuario: ");
        String nombre = myObj.nextLine();
        try {
            return myGestorUsuarios.searchUserName(nombre);
        } catch (UsuarioNoEncontradoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void generarReporteEnSegundoPlano() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new GeneradorReportes(myGestorRecursos, myGestorUsuarios));
        executor.shutdown();
    }

}

