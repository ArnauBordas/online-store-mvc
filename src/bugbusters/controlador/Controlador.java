package bugbusters.controlador;

import bugbusters.modelo.Articulo;
import bugbusters.modelo.Cliente;
import bugbusters.modelo.ClienteEstandar;
import bugbusters.modelo.ClientePremium;
import bugbusters.modelo.Datos;
import bugbusters.modelo.Pedido;
import bugbusters.modelo.excepciones.RecursoNoEncontradoException;
import bugbusters.modelo.excepciones.YaExisteException;
import bugbusters.modelo.excepciones.TipoClienteInvalidoException;
import bugbusters.modelo.excepciones.PedidoNoCancelableException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;



/*
 * Clase Controlador
 *
 * Esta clase actúa como puente entre la Vista y el Modelo.
 *
 * En el patrón MVC:
 * - la Vista no debe acceder directamente al Modelo
 * - la Vista solo debe comunicarse con el Controlador
 * - el Controlador es quien llama a la clase Datos
 *
 * =========================================================
 * BLOQUE IMPLEMENTADO POR: Erick Coll Rodríguez
 * PARTE DESARROLLADA: Gestión de artículos
 * =========================================================
 */
public class Controlador {

    /*
     * Referencia al modelo principal de la aplicación.
     *
     * La clase Datos se encargará de almacenar toda la información
     * del sistema.
     */
    private Datos datos;

    /*
     * Constructor
     *
     * Crea el objeto Datos para poder trabajar con la información
     * de la aplicación.
     */
    public Controlador() {
        datos = new Datos();
    }

    /* =========================================================
       ================= GESTIÓN DE ARTÍCULOS ==================
       ========== IMPLEMENTADO POR ERICK COLL RODRÍGUEZ ========
       ========================================================= */

    /*
     * anadirArticulo()
     *
     * Recibe los datos de un artículo desde la vista,
     * crea el objeto Articulo y lo envía al modelo.
     *
     * Parámetros:
     * - codigo
     * - descripcion
     * - precioVenta
     * - gastosEnvio
     * - tiempoPreparacionMin
     */
    public void anadirArticulo(String codigo, String descripcion, double precioVenta,
                               double gastosEnvio, int tiempoPreparacionMin) throws YaExisteException {
        Articulo articulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacionMin);
        datos.anadirArticulo(articulo);  // Esta línea puede lanzar YaExisteException
    }

    /*
     * buscarArticulo()
     *
     * Busca un artículo a partir de su código.
     *
     * Parámetro:
     * - codigo -> código del artículo
     *
     * Devuelve:
     * - el objeto Articulo si existe
     * - null si no existe
     */
    public Articulo buscarArticulo(String codigo) throws RecursoNoEncontradoException {
        Articulo articulo = datos.buscarArticulo(codigo);
        if (articulo == null) {
            throw new RecursoNoEncontradoException("Artículo", codigo);
        }
        return articulo;
    }

    /*
     * existeArticulo()
     *
     * Comprueba si ya existe un artículo con ese código.
     *
     * Devuelve:
     * - true si existe
     * - false si no existe
     */
    public boolean existeArticulo(String codigo) {
        return datos.existeArticulo(codigo);
    }

    /*
     * obtenerTodosArticulos()
     *
     * Devuelve una lista con todos los artículos almacenados.
     *
     * Esta lista se utilizará desde la vista para mostrar
     * los artículos por pantalla.
     */
    public List<Articulo> obtenerTodosArticulos() {
        return datos.obtenerTodosArticulos();
    }

    /* =========================================================
       =================== GESTIÓN DE PEDIDOS ==================
       ========== BLOQUE PARA NUEVA FUNCIONALIDAD =============
       ========================================================= */

    /**
     * Añade un pedido.
     * Devuelve el pedido creado si se añadió correctamente,
     * o null si no se pudo crear (por ejemplo, artículo inexistente).
     */
    /**
     * Añade un pedido.
     * Devuelve:
     * - el pedido creado si se pudo crear correctamente
     * - null si el cliente o el artículo no existen
     */
    public Pedido anadirPedido(String emailCliente, String codigoArticulo, int cantidad)
            throws RecursoNoEncontradoException {

        // Buscar cliente
        Cliente cliente = datos.buscarCliente(emailCliente);
        if (cliente == null) {
            throw new RecursoNoEncontradoException("Cliente", emailCliente);
        }

        // Buscar artículo
        Articulo articulo = datos.buscarArticulo(codigoArticulo);
        if (articulo == null) {
            throw new RecursoNoEncontradoException("Artículo", codigoArticulo);
        }

        // Crear pedido
        int numeroPedido = datos.generarNumeroPedido();
        Pedido pedido = new Pedido(numeroPedido, cliente, articulo, cantidad, LocalDateTime.now());

        // Guardar pedido
        datos.anadirPedido(pedido);

        return pedido;
    }

    /**
     * Elimina un pedido.
     * Devuelve:
     * - true si se eliminó correctamente
     * - false si no existe o no se puede eliminar
     */
    /**
     * Elimina un pedido.
     * Lanza RecursoNoEncontradoException si no existe el pedido.
     */
    public void eliminarPedido(int numeroPedido) throws RecursoNoEncontradoException {
        Pedido pedido = datos.buscarPedido(numeroPedido);
        if (pedido == null) {
            throw new RecursoNoEncontradoException("Pedido", String.valueOf(numeroPedido));
        }
        if (!pedido.puedeCancelar()) {
            // Si no se puede cancelar, dejamos que siga devolviendo false o podemos crear otra excepción específica
            throw new IllegalStateException("El pedido no puede ser cancelado porque ya ha sido enviado.");
        }

        datos.eliminarPedido(pedido);
    }

    /**
     * Obtiene los pedidos pendientes.
     * Si emailCliente no es null ni vacío, filtra por email.
     */
    public List<Pedido> obtenerPedidosPendientes(String emailCliente) {
        List<Pedido> pedidos = datos.getPedidosPendientes();

        if (emailCliente != null && !emailCliente.isEmpty()) {
            pedidos = pedidos.stream()
                    .filter(p -> p.getCliente().getEmail().equalsIgnoreCase(emailCliente))
                    .collect(Collectors.toList());
        }
        return pedidos;
    }

    /**
     * Obtiene los pedidos enviados.
     * Si emailCliente no es null ni vacío, filtra por email.
     */
    public List<Pedido> obtenerPedidosEnviados(String emailCliente) {
        List<Pedido> pedidos = datos.getPedidosEnviados();

        if (emailCliente != null && !emailCliente.isEmpty()) {
            pedidos = pedidos.stream()
                    .filter(p -> p.getCliente().getEmail().equalsIgnoreCase(emailCliente))
                    .collect(Collectors.toList());
        }
        return pedidos;
    }
    // ==========================================
    //       GESTIÓN DE CLIENTES
    // ==========================================

    /**
     * añadirCliente()
     * Crea el objeto específico según el tipo y lo guarda en el modelo.
     */
    public boolean anadirCliente(String email, String nombre, String domicilio, String nif, int tipoCliente)
            throws TipoClienteInvalidoException, YaExisteException {
        Cliente nuevoCliente;

        if (tipoCliente == 1) {
            nuevoCliente = new ClienteEstandar(email, nombre, domicilio, nif);
        } else if (tipoCliente == 2) {
            nuevoCliente = new ClientePremium(email, nombre, domicilio, nif);
        } else {
            throw new TipoClienteInvalidoException(tipoCliente);
        }

        return datos.anadirCliente(nuevoCliente);
    }

    /**
     * eliminarCliente()
     * Solicita al modelo la eliminar un cliente por email.
     */
    public boolean eliminarCliente(String email) throws RecursoNoEncontradoException {
        //Comprobamos que existe
        Cliente cliente = datos.buscarCliente(email);
        if (cliente == null) {
            throw new RecursoNoEncontradoException("Cliente", email);
        }
        // Si existe lo eliminamos
        return datos.eliminarCliente(email);
    }

    /**
     * buscarCliente()
     * Devuelve el objeto cliente buscado per email.
     */
    public Cliente buscarCliente(String email) throws RecursoNoEncontradoException {
        Cliente cliente = datos.buscarCliente(email);
        if (cliente == null) {
            throw new RecursoNoEncontradoException("Cliente", email);
        }
        return cliente;
    }

    /**
     * listarClientes()
     * Devuelve la lista completa de clientes.
     */
    public List<Cliente> obtenerTodosClientes() {
        return datos.obtenerTodosClientes();
    }

    /**
     * listarClientesEstandar()
     * Filtra a través del modelo los clientes estándar.
     */
    public List<Cliente> obtenerClientesEstandar() {
        return datos.obtenerClientesEstandar();
    }

    /**
     * listarClientesPremium()
     * Filtra a través del modelo los clientes premium.
     */
    public List<Cliente> obtenerClientesPremium() {
        return datos.obtenerClientesPremium();
    }
}