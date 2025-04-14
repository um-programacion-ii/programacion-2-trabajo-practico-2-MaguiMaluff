import Interfaces.Prestable;
import Interfaces.Renovable;
import Modelos.*;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {


        Consola consola = new Consola();
        consola.myGestorUsuarios.newUser("X", "sofia@example.com");
        consola.myGestorRecursos.agregarLibro(EstadoRecurso.DISPONIBLE,  "N", "Autor Ejemplo");
        consola.menu();
    }
}
