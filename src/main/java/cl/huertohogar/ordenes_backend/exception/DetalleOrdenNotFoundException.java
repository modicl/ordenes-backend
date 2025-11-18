package cl.huertohogar.ordenes_backend.exception;

public class DetalleOrdenNotFoundException extends RuntimeException {
    public DetalleOrdenNotFoundException(String mensaje) {
        super(mensaje);
    }

    public DetalleOrdenNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
