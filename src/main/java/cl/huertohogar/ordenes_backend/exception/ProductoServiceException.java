package cl.huertohogar.ordenes_backend.exception;

public class ProductoServiceException extends RuntimeException {
    public ProductoServiceException(String mensaje) {
        super(mensaje);
    }

    public ProductoServiceException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
