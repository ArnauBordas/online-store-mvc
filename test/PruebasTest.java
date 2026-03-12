import bugbusters.modelo.Articulo;
import bugbusters.modelo.Cliente;
import bugbusters.modelo.ClienteEstandar;
import bugbusters.modelo.Pedido;
import bugbusters.controlador.Controlador;

import bugbusters.modelo.excepciones.RecursoNoEncontradoException;
import bugbusters.modelo.excepciones.YaExisteException;
import bugbusters.modelo.excepciones.TipoClienteInvalidoException;
import bugbusters.modelo.excepciones.EmailInvalidoException; // IMPORTANTE: Añadir esta importación

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class PruebasTest {

    private Controlador controlador;
    private Articulo articulo;
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        controlador = new Controlador();

        // Artículo: un ratón de escritorio
        articulo = new Articulo("RAT001", "Ratón inalámbrico Logitech", 29.99, 3.50, 15);

        // Cliente: Juan Pérez de Barcelona
        cliente = new ClienteEstandar("juan.perez@email.com", "Juan Pérez", "Calle Mayor 123, Barcelona", "12345678A");

        try {
            controlador.anadirArticulo(articulo.getCodigo(), articulo.getDescripcion(),
                    articulo.getPrecioVenta(), articulo.getGastosEnvio(),
                    articulo.getTiempoPreparacionMin());
        } catch (YaExisteException e) {
            fail("Error al añadir artículo en setUp: " + e.getMessage());
        }

        try {
            controlador.anadirCliente(cliente.getEmail(), cliente.getNombre(),
                    cliente.getDomicilio(), cliente.getNif(), 1);
        } catch (YaExisteException | TipoClienteInvalidoException | EmailInvalidoException e) {
            fail("Error al añadir cliente en setUp: " + e.getMessage());
        }
    }

    @Test
    public void testAnadirPedidoCorrecto() {
        // Juan Pérez compra 2 ratones
        Pedido pedido = null;
        try {
            pedido = controlador.anadirPedido("juan.perez@email.com", "RAT001", 2);
        } catch (RecursoNoEncontradoException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }

        assertNotNull(pedido);
        assertEquals("juan.perez@email.com", pedido.getCliente().getEmail());
        assertEquals("RAT001", pedido.getArticulo().getCodigo());
        assertEquals(2, pedido.getCantidad());
        assertTrue(pedido.getNumeroPedido() > 0);

        // Calculamos el total: (29.99 * 2) + (gastos de envío con descuento)
        // Cliente estándar: sin descuento → gastosEnvio = 3.50
        // Total = 59.98 + 3.50 = 63.48€
        assertEquals(63.48, pedido.calcularTotal(), 0.01);
    }

    @Test
    public void testPuedeCancelarFalse() {
        // Hace una hora que se hizo el pedido (ya no se puede cancelar)
        LocalDateTime haceUnaHora = LocalDateTime.now().minusHours(1);
        Pedido pedido = new Pedido(1, cliente, articulo, 2, haceUnaHora);

        boolean puede = pedido.puedeCancelar();

        assertFalse(puede);
    }
}