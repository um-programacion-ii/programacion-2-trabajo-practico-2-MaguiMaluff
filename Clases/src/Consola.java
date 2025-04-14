import java.util.Scanner;

import Gestion.GestorRecursos;
import Gestion.GestorUsuarios;
import Interfaces.Prestable;
import Interfaces.Renovable;
import Modelos.EstadoRecurso;
import Modelos.Usuario;
import Recursos.RecursoDigital;

public class Consola {
    Scanner myObj = new Scanner(System.in);
    GestorUsuarios myGestorUsuarios = new GestorUsuarios();
    GestorRecursos myGestorRecursos = new GestorRecursos();

    public void menu(){
        int choice;
        do{
        System.out.println("""
                1. Gestion Usuarios
                \
                2. Gestion Recursos
                \
                0. Salir
                """);
        choice = Integer.parseInt(myObj.nextLine());
            switch (choice){
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
            }while(choice != 0);
    }
    public void gestionUsuario() {
        int choice;
        do{
        System.out.println("""
                1. Agregar Usuarios
                \
                2. Buscar Usuario por Nombre
                \
                3. Buscar Usuario por Email
                \
                0. Menu Anterior""");
        choice = Integer.parseInt(myObj.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Nombre: ");
                    String name = myObj.nextLine();
                    System.out.println("Mail: ");
                    String mail = myObj.nextLine();
                    myGestorUsuarios.newUser(name, mail);
                    break;

                case 2:
                    System.out.println("Nombre: ");
                    String name_2 = myObj.nextLine();
                    myGestorUsuarios.searchUserName(name_2);
                    break;

                case 3:
                    System.out.println("Email: ");
                    String email = myObj.nextLine();
                    myGestorUsuarios.searchUserEmail(email);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion Invalida");
                    break;
            }
        }while(choice != 0);
    }
    public void gestionRecursos(){
        int choice;
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
                    0. Menu Anterior
                    """);
            choice = Integer.parseInt(myObj.nextLine());

            switch (choice) {
                case 1:
                    agregarRecurso();
                    break;

                case 2:
                    System.out.println("Nombre del recurso: ");
                    String buscarNombre = myObj.nextLine().trim();
                    myGestorRecursos.buscarPorNombre(buscarNombre);
                    break;

                case 3:
                    myGestorRecursos.listarRecursos();
                    break;
                case 4:
                    System.out.println("Nombre del recurso: ");
                    String name_5 = myObj.nextLine();
                    System.out.println("Nombre del usuario: ");
                    String name_4 = myObj.nextLine();

                    RecursoDigital recurso = myGestorRecursos.buscarPorNombre(name_5);
                    Usuario usuario = myGestorUsuarios.searchUserName(name_4);

                    if (recurso instanceof Prestable prestable && usuario != null) {
                        prestable.prestar(usuario);
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
                        renovable.renovar(usuario5);
                    } else {
                        System.out.println("El recurso no permite renovación.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion Invalida");
            }
        }while (choice != 0);

    }
    public void agregarRecurso() {
        int choice;
        do{

        System.out.println("""
                1. Agregar Libro
                \
                2. Agregar Revista
                \
                3. Agregar Audiolibre
                \
                0. Menu Anterior
                """);
        choice = Integer.parseInt(myObj.nextLine());


        switch (choice) {
            case 1:
                System.out.println("Nombre: ");
                String name = myObj.nextLine();
                System.out.println("Autor: ");
                String autor = myObj.nextLine();

                myGestorRecursos.agregarLibro(EstadoRecurso.DISPONIBLE, autor, name);
                break;
            case 2:
                System.out.println("Nombre: ");
                String name_2 = myObj.nextLine();
                System.out.println("Marca: ");
                String marca = myObj.nextLine();
                System.out.println("Issue: ");
                Integer issue = Integer.valueOf(myObj.nextLine());;

                myGestorRecursos.agregarRevista(EstadoRecurso.DISPONIBLE, marca, issue, name_2);
                break;
            case 3:
                System.out.println("Nombre: ");
                String name_3 = myObj.nextLine();
                System.out.println("Autor: ");
                String autor_3 = myObj.nextLine();
                System.out.println("Lector: ");
                String lector = myObj.nextLine();

                myGestorRecursos.agregarAudioLibro(EstadoRecurso.DISPONIBLE, autor_3, lector, name_3);
                break;
            case 0:
                break;
            default:
                System.out.println("Opcion Invalida");
                break;
        }
        }while(choice != 0);
    }
}



