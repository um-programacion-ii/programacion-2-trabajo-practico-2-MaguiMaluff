package Servicios;

import Alertas.NivelUrgencia;

public class ConfiguracionNotificaciones {
    private NivelUrgencia nivelUrgenciaPreferido;

    public ConfiguracionNotificaciones() {
        this.nivelUrgenciaPreferido = NivelUrgencia.INFO; 
    }

    public void establecerNivelUrgencia(NivelUrgencia nivelUrgencia) {
        this.nivelUrgenciaPreferido = nivelUrgencia;
    }

    public NivelUrgencia obtenerNivelUrgencia() {
        return nivelUrgenciaPreferido;
    }
}
