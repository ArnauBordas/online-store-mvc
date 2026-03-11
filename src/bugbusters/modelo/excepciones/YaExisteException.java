package bugbusters.modelo.excepciones;

public class YaExisteException extends Exception {
    public YaExisteException(String tipo, String identificador) {
        super("[EXCEPCION] Ya existe " + tipo + " con el identificador: " + identificador + ". Vuelve a intentarlo con otro código.");
    }
}