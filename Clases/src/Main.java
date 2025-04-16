import Gestion.GestorRecursos;
import Recursos.EstadoRecurso;

public class Main {
    public static void main(String[] args) {


        Consola consola = new Consola();
        GestorRecursos gestor = consola.myGestorRecursos;
        consola.myGestorUsuarios.newUser("X", "fifi@example.com");
        consola.myGestorRecursos.agregarLibro(EstadoRecurso.DISPONIBLE,  "N", "Autor Ejemplo");
        consola.myGestorRecursos.agregarLibro(EstadoRecurso.DISPONIBLE, "Java Avanzado", "Juan Pérez");
        gestor.agregarLibro(EstadoRecurso.DISPONIBLE, "Patrones de Diseño", "Erich Gamma");
        gestor.agregarLibro(EstadoRecurso.PRESTADO, "Estructuras de Datos", "Ana Torres");


        // Agregamos revistas
        gestor.agregarRevista(EstadoRecurso.DISPONIBLE, "Tech Monthly", 42, "Revista Tech");
        gestor.agregarRevista(EstadoRecurso.PRESTADO, "Ciencia Hoy", 15, "Revista Ciencia");

        // Agregamos audiolibros
        gestor.agregarAudioLibro(EstadoRecurso.DISPONIBLE, "El Principito", "Saint-Exupéry", "Carlos Lector");
        gestor.agregarAudioLibro(EstadoRecurso.PRESTADO, "1984", "Orwell", "Lucía Voz");

        consola.menu();
    }
}
