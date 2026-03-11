package bugbusters.modelo.excepciones;

public class EmailInvalidoException extends Exception {
    public EmailInvalidoException(String email) {
        super("[EXCEPCION] El email '" + email + "' no tiene un formato válido");
    }
}