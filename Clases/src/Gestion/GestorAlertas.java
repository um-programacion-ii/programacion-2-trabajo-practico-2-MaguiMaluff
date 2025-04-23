package Gestion;

import Alertas.Alerta;
import Alertas.NivelUrgencia;

import java.util.ArrayList;
import java.util.List;

public class GestorAlertas {
    private final List<Alerta> historialAlertas;

    public GestorAlertas() {
        this.historialAlertas = new ArrayList<>();
    }

    public void agregarAlerta(String mensaje, NivelUrgencia nivelUrgencia) {
        Alerta alerta = new Alerta(mensaje, nivelUrgencia);
        historialAlertas.add(alerta);
    }

    public void mostrarHistorial() {
        if (historialAlertas.isEmpty()) {
            System.out.println("No hay alertas registradas.");
        } else {
            for (Alerta alerta : historialAlertas) {
                System.out.println(alerta);
            }
        }
    }
}

