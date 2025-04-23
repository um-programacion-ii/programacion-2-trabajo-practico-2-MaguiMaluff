import Gestion.GestorPrestamos;
import Gestion.GestorRecursos;
import Gestion.GestorUsuarios;
import Interfaces.Prestable;
import Modelos.Libro;
import Modelos.Usuario;
import Recursos.CategoriaRecurso;
import Recursos.EstadoRecurso;
import Recursos.RecursoDigital;
import Servicios.Prestamos;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Consola consola = new Consola();
        GestorRecursos gestor = consola.myGestorRecursos;
        GestorPrestamos prestamos = consola.myGestorPrestamos;
        GestorUsuarios usuarios = consola.myGestorUsuarios;
        GestorRecursos recurso = consola.myGestorRecursos;



        consola.myGestorUsuarios.newUser("Carlos", "carlos@mail.com");
        consola.myGestorUsuarios.newUser("Lucia", "lucia@mail.com");
        consola.myGestorUsuarios.newUser("Mateo", "mateo@mail.com");
        consola.myGestorUsuarios.newUser("Sofia", "sofia@mail.com");


        gestor.agregarLibro(EstadoRecurso.DISPONIBLE, "La Breve Historia del Tiempo", "Stephen Hawking", CategoriaRecurso.CIENCIA);
        gestor.agregarLibro(EstadoRecurso.DISPONIBLE, "El Hobbit", "J.R.R. Tolkien", CategoriaRecurso.ENTRETENIMIENTO);
        gestor.agregarLibro(EstadoRecurso.DISPONIBLE, "El Arte de la Guerra", "Sun Tzu", CategoriaRecurso.HISTORIA);
        gestor.agregarLibro(EstadoRecurso.DISPONIBLE, "El Gran Gatsby", "F. Scott Fitzgerald", CategoriaRecurso.ENTRETENIMIENTO);

        gestor.agregarRevista(EstadoRecurso.DISPONIBLE, "National Geographic", 300, "NG Edición Especial", CategoriaRecurso.CIENCIA);
        gestor.agregarRevista(EstadoRecurso.DISPONIBLE, "Historia Viva", 120, "HV Ed. Julio", CategoriaRecurso.HISTORIA);
        gestor.agregarRevista(EstadoRecurso.DISPONIBLE, "Playcode", 89, "Ed. Java", CategoriaRecurso.TECNOLOGIA);
        gestor.agregarRevista(EstadoRecurso.DISPONIBLE, "Arte Hoy", 45, "Edición de Verano", CategoriaRecurso.ENTRETENIMIENTO);
        gestor.agregarRevista(EstadoRecurso.DISPONIBLE, "Ingeniería Moderna", 78, "Ed. Energía Renovable", CategoriaRecurso.EDUCACION);


        gestor.agregarAudioLibro(EstadoRecurso.DISPONIBLE, "Matar a un ruiseñor", "Harper Lee", "Narrador A", CategoriaRecurso.ENTRETENIMIENTO);
        gestor.agregarAudioLibro(EstadoRecurso.DISPONIBLE, "Cien años de soledad", "Gabriel García Márquez", "Narrador B", CategoriaRecurso.ENTRETENIMIENTO);
        gestor.agregarAudioLibro(EstadoRecurso.DISPONIBLE, "Steve Jobs", "Walter Isaacson", "Narrador C", CategoriaRecurso.HISTORIA);
        gestor.agregarAudioLibro(EstadoRecurso.DISPONIBLE, "Meditaciones", "Marco Aurelio", "Narrador D", CategoriaRecurso.HISTORIA);
        gestor.agregarAudioLibro(EstadoRecurso.DISPONIBLE, "Curso de Python", "Ana Dev", "Narrador E", CategoriaRecurso.EDUCACION);

        gestor.agregarLibro(EstadoRecurso.DISPONIBLE, "Clean Code", "Robert C. Martin", CategoriaRecurso.EDUCACION);
        consola.myGestorUsuarios.newUser("Ana", "ana@mail.com");
        prestamos.registrarPrestamo(usuarios.searchUserName("Ana"), ((Prestable) recurso.buscarPorNombre("Clean Code")));
        Prestamos prestamo = prestamos.buscarPrestamo(recurso.buscarPorNombre("Clean Code"));

        consola.myGestorReservas.reservarRecurso(usuarios.searchUserName("Sofia"), ((Prestable) recurso.buscarPorNombre("Clean Code")), LocalDate.parse("2025-05-07"));

        consola.myGestorPrestamos.devolverPrestamo(recurso.buscarPorNombre("Clean Code"));

        boolean continuar = true;
        while (continuar) {
            continuar = consola.menu();
        }
    }
}
