package bugbusters.modelo.excepciones;

public class TipoClienteInvalidoException extends Exception {
    public TipoClienteInvalidoException(int tipo) {
        super("[EXCEPCION] Tipo de cliente inválido: " + tipo + ". Debes escribir 1 para Estándar o 2 para Premium.");
    }
}