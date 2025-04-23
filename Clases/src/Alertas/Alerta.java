package Alertas;

import java.time.LocalDateTime;

public class Alerta {
    private final String mensaje;
    private final NivelUrgencia nivelUrgencia;
    private final LocalDateTime fechaHora;

    public Alerta(String mensaje, NivelUrgencia nivelUrgencia) {
        this.mensaje = mensaje;
        this.nivelUrgencia = nivelUrgencia;
        this.fechaHora = LocalDateTime.now();
    }

    public String getMensaje() {
        return mensaje;
    }

    public NivelUrgencia getNivelUrgencia() {
        return nivelUrgencia;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    @Override
    public String toString() {
        return "[" + fechaHora + "] " + nivelUrgencia + ": " + mensaje;
    }
}
