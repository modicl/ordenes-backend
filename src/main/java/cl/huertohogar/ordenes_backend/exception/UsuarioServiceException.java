package cl.huertohogar.ordenes_backend.exception;

public class UsuarioServiceException extends RuntimeException {
    public UsuarioServiceException(String mensaje) {
        super(mensaje);
    }

    public UsuarioServiceException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}