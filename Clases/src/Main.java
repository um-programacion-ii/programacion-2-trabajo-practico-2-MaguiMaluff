import Interfaces.Prestable;
import Interfaces.Renovable;
import Modelos.*;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {


        Consola consola = new Consola();
        consola.myGestorUsuarios.newUser("Nombre ejemplo", "sofia@example.com");
        consola.myGestorRecursos.agregarLibro(EstadoRecurso.DISPONIBLE,  "Java Avanzado", "Autor Ejemplo");
        consola.menu();
    }
}
