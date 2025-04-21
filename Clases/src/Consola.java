import java.util.Scanner;

import Excepciones.DatosErroneosException;
import Excepciones.RecursoNoDisponibleException;
import Excepciones.RecursoNoEncontradoException;
import Excepciones.UsuarioNoEncontradoException;
import Gestion.GestorRecursos;
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

    public void menu() {
        int choice = -1;
        do {
            System.out.println("""
                    1. Gestion Usuarios
                    \
                    2. Gestion Recursos
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
                    gestionRecursos();
                    break;
                case 0:
                    System.out.println("Adios");
                    break;
            }
        } while (choice != 0);
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
                    0. Menu Anterior""");
            try {
                choice = Integer.parseInt(myObj.nextLine());
            } catch (Exception e) {
                System.out.println("Opcion no disponible");
            }
            switch (choice) {
                case 1:
                    try{
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
                    System.out.println("Nombre: ");
                    String name_2 = myObj.nextLine();
                    try {
                        myGestorUsuarios.searchUserName(name_2);
                    } catch (UsuarioNoEncontradoException e) {
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
                    4. Solicitar Prestamo
                    \
                    5. Renovar Prestamo
                    \
                    6. Filtrar por Tipo
                    \
                    7. Filtrar Recursos Ordenados
                    \
                    8. Buscar por Categoria
                    \
                    0. Menu Anterior
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
                    System.out.println("Nombre del recurso: ");
                    String buscarNombre = myObj.nextLine().trim();
                    try {
                        myGestorRecursos.buscarPorNombre(buscarNombre);
                    } catch (RecursoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    myGestorRecursos.listarRecursos();
                    break;
                case 4:
                    System.out.println("Nombre del recurso: ");
                    String name_5 = myObj.nextLine();
                    System.out.println("Nombre del usuario: ");
                    String name_4 = myObj.nextLine();
                    RecursoDigital recurso = null;
                    Usuario usuario = null;
                    try {
                        recurso = myGestorRecursos.buscarPorNombre(name_5);
                        usuario = myGestorUsuarios.searchUserName(name_4);
                    } catch (UsuarioNoEncontradoException | RecursoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }

                    if (recurso instanceof Prestable prestable && usuario != null) {
                        try {
                            prestable.prestar(usuario);
                            System.out.println("Recurso prestado exitosamente.");
                        } catch (RecursoNoDisponibleException | DatosErroneosException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (recurso == null) {
                        System.out.println("Recurso no encontrado.");
                    } else {
                        System.out.println("No se puede prestar.");
                    }

                    break;
                case 5:
                    System.out.println("Nombre del recurso: ");
                    String recursoNombre = myObj.nextLine();
                    System.out.println("Nombre del usuario: ");
                    String usuario_5 = myObj.nextLine();
                    RecursoDigital recursoRenovable = myGestorRecursos.buscarPorNombre(recursoNombre);
                    Usuario usuario5 = myGestorUsuarios.searchUserName(usuario_5);

                    if (recursoRenovable instanceof Renovable renovable) {
                        try {
                            renovable.renovar(usuario5);
                        } catch (RecursoNoDisponibleException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (recursoRenovable == null) {
                        System.out.println("Recurso no encontrado.");
                    } else {
                        System.out.println("No se puede prestar.");
                    }
                    break;
                case 6:
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
                case 7:
                    myGestorRecursos.ordenarPorNombre();
                    break;
                case 8:
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
                    0. Menu Anterior
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

            } catch (DatosErroneosException  e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("La categoría ingresada no es válida.");
            }
        }while (choice != 0);
    }
}

