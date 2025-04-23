import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import Alertas.AlertaVencimiento;
import Excepciones.DatosErroneosException;
import Excepciones.RecursoNoDisponibleException;
import Excepciones.RecursoNoEncontradoException;
import Excepciones.UsuarioNoEncontradoException;
import Gestion.GestorPrestamos;
import Gestion.GestorRecursos;
import Gestion.GestorReservas;
import Gestion.GestorUsuarios;
import Interfaces.Prestable;
import Interfaces.Renovable;
import Recursos.CategoriaRecurso;
import Recursos.EstadoRecurso;
import Modelos.Usuario;
import Recursos.RecursoDigital;

public class Consola {
    Scanner myObj = new Scanner(System.in);
    GestorUsuarios myGestorUsuarios = new GestorUsuarios();
    GestorRecursos myGestorRecursos = new GestorRecursos();
    GestorReservas myGestorReservas = new GestorReservas();
    GestorPrestamos myGestorPrestamos = new GestorPrestamos(myGestorReservas);

    public boolean menu() {
        int choice = -1;
        AlertaVencimiento alertaVencimiento = new AlertaVencimiento(myGestorPrestamos);
        alertaVencimiento.verificarAlertas();
        do {
            System.out.println("""
                1. Gestion Usuarios
                2. Gestion Recursos
                3. Gestion Prestamos
                4. Gestion Reservas
                5. Mostrar Reportes
                0. Salir
                """);

            try {
                choice = Integer.parseInt(myObj.nextLine());
            } catch (Exception e) {
                System.out.println("Opcion no disponible");
            }

            switch (choice) {
                case 1 -> gestionUsuario();
                case 2 -> gestionRecursos();
                case 3 -> gestionPrestamos();
                case 4 -> gestionReservas();
                case 5 -> mostrarReporte();
                case 0 -> {
                    System.out.println("Adios");
                    myGestorPrestamos.getMyGestorNotificaciones().cerrar();
                    return false;
                }
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

    public void mostrarReporte() {
        System.out.println("====== Recursos Más Prestados ======");
        myGestorRecursos.obtenerRecursosMasPrestados().forEach(r ->
                System.out.println(r.getNombre() + " - Prestado " + (r).getVecesPrestado() + " veces")
        );

        System.out.println("\n====== Usuarios Más Activos ======");
        myGestorUsuarios.obtenerUsuariosMasActivos().forEach(u ->
                System.out.println(u.getName() + " - " + u.getPrestamosRealizados() + " préstamos")
        );

        System.out.println("\n====== Estadísticas por Categoría ======");
        myGestorRecursos.estadisticasPorCategoria().forEach((categoria, totalPrestamos) ->
                System.out.println(categoria + ": " + totalPrestamos + " préstamos")
        );
    }

}

