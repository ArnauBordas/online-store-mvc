package bugbusters.modelo.excepciones;

public class PedidoNoCancelableException extends Exception {
    public PedidoNoCancelableException(int numeroPedido) {
        super("[EXCEPCION] El pedido " + numeroPedido + " no puede ser cancelado porque ya ha sido enviado.");
    }
}