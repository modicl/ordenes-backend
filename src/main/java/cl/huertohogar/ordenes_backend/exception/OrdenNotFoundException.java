package cl.huertohogar.ordenes_backend.exception;

public class OrdenNotFoundException extends RuntimeException {
    public OrdenNotFoundException(String mensaje) {
        super(mensaje);
    }

    public OrdenNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}