package Excepciones;

public class DatosErroneosException extends RuntimeException {
    public DatosErroneosException(String message) {
        super(message);
    }
}
