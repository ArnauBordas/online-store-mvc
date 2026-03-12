package bugbusters.modelo.excepciones;

/**
 * Excepción lanzada cuando se intenta crear un recurso que ya existe en el sistema.
 *
 * Esta excepción se utiliza para indicar que no se puede completar una operación
 * de creación porque ya existe un objeto con el mismo identificador único.
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public class YaExisteException extends Exception {
    public YaExisteException(String tipo, String identificador) {
        super("[EXCEPCION] Ya existe " + tipo + " con el identificador: " + identificador + ". Vuelve a intentarlo con otro código.");
    }
}