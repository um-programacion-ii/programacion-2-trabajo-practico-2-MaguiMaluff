import Gestion.GestorRecursos;
import Recursos.CategoriaRecurso;
import Recursos.EstadoRecurso;

public class Main {
    public static void main(String[] args) {


        Consola consola = new Consola();
        GestorRecursos gestor = consola.myGestorRecursos;
        consola.myGestorUsuarios.newUser("X", "fifi@example.com");
        consola.myGestorRecursos.agregarLibro(EstadoRecurso.DISPONIBLE,  "N", "Autor Ejemplo", CategoriaRecurso.CIENCIA);
        consola.myGestorRecursos.agregarLibro(EstadoRecurso.DISPONIBLE, "Java Avanzado", "Juan Pérez", CategoriaRecurso.EDUCACION);
        gestor.agregarLibro(EstadoRecurso.DISPONIBLE, "Patrones de Diseño", "Erich Gamma", CategoriaRecurso.EDUCACION);
        gestor.agregarLibro(EstadoRecurso.PRESTADO, "Estructuras de Datos", "Ana Torres", CategoriaRecurso.EDUCACION);


        // Agregamos revistas
        gestor.agregarRevista(EstadoRecurso.DISPONIBLE, "Tech Monthly", 42, "Revista Tech", CategoriaRecurso.TECNOLOGIA);
        gestor.agregarRevista(EstadoRecurso.PRESTADO, "Ciencia Hoy", 15, "Revista Ciencia", CategoriaRecurso.EDUCACION);

        // Agregamos audiolibros
        gestor.agregarAudioLibro(EstadoRecurso.DISPONIBLE, "El Principito", "Saint-Exupéry", "Carlos Lector", CategoriaRecurso.ENTRETENIMIENTO);
        gestor.agregarAudioLibro(EstadoRecurso.PRESTADO, "1984", "Orwell", "Lucía Voz", CategoriaRecurso.ENTRETENIMIENTO);

        consola.menu();
    }
}
