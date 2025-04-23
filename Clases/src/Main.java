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

        boolean continuar = true;
        while (continuar) {
            continuar = consola.menu();
        }
    }
}
