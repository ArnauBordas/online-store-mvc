package bugbusters.modelo.excepciones;

public class ClienteYaExisteException extends Exception {
    public ClienteYaExisteException(String email) {
        super("[EXCEPCION] El cliente " + email + " ya existe ");
    }
}