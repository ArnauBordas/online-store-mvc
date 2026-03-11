package bugbusters.modelo.excepciones;

public class RecursoNoEncontradoException extends Exception {
    public RecursoNoEncontradoException(String tipo, String identificador) {
        super("[EXCEPCION] " + tipo + " no encontrado: " + identificador + ". Vuelve a intentarlo.");
    }
}