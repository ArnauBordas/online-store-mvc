package bugbusters.controlador;

import bugbusters.modelo.Articulo;
import bugbusters.modelo.Cliente;
import bugbusters.modelo.ClienteEstandar;
import bugbusters.modelo.ClientePremium;
import bugbusters.modelo.Datos;
import bugbusters.modelo.Pedido;
import bugbusters.modelo.excepciones.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase Controlador que actúa como puente entre la Vista y el Modelo.
 *
 * En el patrón MVC (Modelo-Vista-Controlador), esta clase es el intermediario
 * que procesa las solicitudes de la vista, interactúa con el modelo (Datos)
 * y devuelve los resultados. La vista nunca accede directamente al modelo,
 * solo se comunica a través del controlador.
 *
 * El controlador se encarga de:
 * <ul>
 *   <li>Recibir y validar los datos provenientes de la vista</li>
 *   <li>Crear los objetos del modelo (Artículo, Cliente, Pedido)</li>
 *   <li>Invocar los métodos correspondientes en la capa de datos</li>
 *   <li>Manejar las excepciones y transformarlas cuando sea necesario</li>
 *   <li>Devolver los resultados a la vista para su presentación</li>
 * </ul>
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public class Controlador {

    /**
     * Referencia al modelo principal de la aplicación.
     * La clase Datos se encargará de almacenar toda la información del sistema.
     */
    private Datos datos;

    /**
     * Constructor que inicializa el controlador.
     * Crea una nueva instancia de la clase Datos para poder trabajar
     * con la información de la aplicación.
     */
    public Controlador() {
        datos = new Datos();
    }

    /* =========================================================
       ================= GESTIÓN DE ARTÍCULOS ==================
       ========================================================= */
    /**
     * Busca un artículo por su código.
     *
     * @param codigo Código del artículo a buscar
     * @return El objeto Artículo si existe
     * @throws RecursoNoEncontradoException Si no existe un artículo con ese código
     */
    public Articulo buscarArticulo(String codigo) throws RecursoNoEncontradoException {
        Articulo articulo = datos.buscarArticulo(codigo);
        if (articulo == null) { // Lanzamos excepción si no existe
            throw new RecursoNoEncontradoException("Artículo", codigo);
        }
        return articulo;
    }

    /**
     * Añade un nuevo artículo al sistema.
     *
     * @param codigo Código único identificador del artículo
     * @param descripcion Descripción textual del artículo
     * @param precioVenta Precio de venta del artículo en euros
     * @param gastosEnvio Gastos de envío asociados al artículo
     * @param tiempoPreparacionMin Tiempo de preparación en minutos
     * @throws YaExisteException Si ya existe un artículo con el mismo código
     */
    public void anadirArticulo(String codigo, String descripcion, double precioVenta,
                               double gastosEnvio, int tiempoPreparacionMin)
            throws YaExisteException {

        if (datos.existeArticulo(codigo)) { // Lanzamos excepción si ya existe
            throw new YaExisteException("artículo", codigo);
        }

        Articulo articulo = new Articulo(codigo, descripcion, precioVenta,
                gastosEnvio, tiempoPreparacionMin);
        datos.anadirArticulo(articulo); // No lanza excepciones
    }

    /**
     * Obtiene una lista con todos los artículos almacenados.
     *
     * @return Lista de todos los artículos
     */
    public List<Articulo> obtenerTodosArticulos() {
        return datos.obtenerTodosArticulos();
    }

    /* =========================================================
       =================== GESTIÓN DE PEDIDOS ==================
       ========================================================= */

    /**
     * Añade un nuevo pedido al sistema.
     *
     * @param emailCliente Email del cliente que realiza el pedido
     * @param codigoArticulo Código del artículo solicitado
     * @param cantidad Cantidad de unidades del artículo
     * @return El objeto Pedido creado
     * @throws RecursoNoEncontradoException Si el cliente o el artículo no existen
     */
    public Pedido anadirPedido(String emailCliente, String codigoArticulo, int cantidad)
            throws RecursoNoEncontradoException {

        // Buscar cliente
        Cliente cliente = datos.buscarCliente(emailCliente);
        if (cliente == null) { // Lanzamos excepción si el cliente no existe
            throw new RecursoNoEncontradoException("Cliente", emailCliente);
        }

        // Buscar artículo
        Articulo articulo = datos.buscarArticulo(codigoArticulo);
        if (articulo == null) { // Lanzamos excepción si el artículo no existe
            throw new RecursoNoEncontradoException("Artículo", codigoArticulo);
        }

        // Crear pedido
        int numeroPedido = datos.generarNumeroPedido();
        Pedido pedido = new Pedido(numeroPedido, cliente, articulo, cantidad, LocalDateTime.now());

        // Guardar pedido
        datos.anadirPedido(pedido); // No lanza excepciones

        return pedido;
    }


    /**
     * Elimina un pedido del sistema si es cancelable.
     *
     * @param numeroPedido Número identificador del pedido a eliminar
     * @throws RecursoNoEncontradoException Si no existe un pedido con ese número
     * @throws PedidoNoCancelableException Si el pedido ya ha sido enviado y no se puede cancelar
     */
    public void eliminarPedido(int numeroPedido) throws RecursoNoEncontradoException, PedidoNoCancelableException {
        Pedido pedido = datos.buscarPedido(numeroPedido);
        if (pedido == null) { // Lanzamos excepción si no existe
            throw new RecursoNoEncontradoException("Pedido", String.valueOf(numeroPedido));
        }
        if (!pedido.puedeCancelar()) { // Lanzamos excepción si ya está enviado
            throw new PedidoNoCancelableException(numeroPedido);
        }
        datos.eliminarPedido(pedido); // No lanza excepciones
    }

    /**
     * Obtiene los pedidos pendientes (no enviados).
     *
     * @param emailCliente Email para filtrar por cliente (si es null o vacío, devuelve todos)
     * @return Lista de pedidos pendientes
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
     * Obtiene los pedidos ya enviados.
     *
     * @param emailCliente Email para filtrar por cliente (si es null o vacío, devuelve todos)
     * @return Lista de pedidos enviados
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
    /* =========================================================
       =================== GESTIÓN DE CLIENTES ==================
       ========================================================= */

    /**
     * Valida el formato de una dirección de correo electrónico.
     *
     * @param email Email a validar
     * @return true si el email tiene un formato válido, false en caso contrario
     */
    public boolean emailValido(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$";
        return email != null && email.matches(regex);
    }

    /**
     * Añade un nuevo cliente al sistema.
     *
     * @param email Email único del cliente
     * @param nombre Nombre completo del cliente
     * @param domicilio Dirección física del cliente
     * @param nif NIF del cliente
     * @param tipoCliente Tipo de cliente (1-Estándar, 2-Premium)
     * @return true si el cliente se añadió correctamente
     * @throws EmailInvalidoException Si el email no tiene un formato válido
     * @throws TipoClienteInvalidoException Si el tipo de cliente no es 1 o 2
     * @throws YaExisteException Si ya existe un cliente con el mismo email
     */
    public boolean anadirCliente(String email, String nombre, String domicilio, String nif, int tipoCliente)
            throws EmailInvalidoException, TipoClienteInvalidoException, YaExisteException {

        // 1. Validar formato del email
        if (!emailValido(email)) { // Lanzamos excepción si el email no es válido
            throw new EmailInvalidoException(email);
        }

        // 2. Verificar si ya existe
        if (datos.existeCliente(email)) { // Lanzamos excepción si ya existe
            throw new YaExisteException("cliente", email);
        }

        // 3. Crear cliente según tipo
        Cliente nuevoCliente;
        if (tipoCliente == 1) {
            nuevoCliente = new ClienteEstandar(email, nombre, domicilio, nif);
        } else if (tipoCliente == 2) {
            nuevoCliente = new ClientePremium(email, nombre, domicilio, nif);
        } else { // Lanzamos excepción si el tipo no es válido
            throw new TipoClienteInvalidoException(tipoCliente);
        }

        // 4. Guardar cliente
        datos.anadirCliente(nuevoCliente); // No lanza excepciones
        return true;
    }

    /**
     * Elimina un cliente del sistema por su email.
     *
     * @param email Email del cliente a eliminar
     * @return true si el cliente se eliminó correctamente
     * @throws RecursoNoEncontradoException Si no existe un cliente con ese email
     */
    public boolean eliminarCliente(String email) throws RecursoNoEncontradoException {
        //Comprobamos que existe
        Cliente cliente = datos.buscarCliente(email);
        if (cliente == null) { // Lanzamos excepción si no existe
            throw new RecursoNoEncontradoException("Cliente", email);
        }
        // Si existe lo eliminamos
        return datos.eliminarCliente(email);
    }

    /**
     * Busca un cliente por su email.
     *
     * @param email Email del cliente a buscar
     * @return El objeto Cliente si existe
     * @throws RecursoNoEncontradoException Si no existe un cliente con ese email
     */
    public Cliente buscarCliente(String email) throws RecursoNoEncontradoException {
        Cliente cliente = datos.buscarCliente(email);
        if (cliente == null) {
            throw new RecursoNoEncontradoException("Cliente", email);
        }
        return cliente;
    }

    /**
     * Obtiene una lista con todos los clientes.
     *
     * @return Lista de todos los clientes
     */
    public List<Cliente> obtenerTodosClientes() {
        return datos.obtenerTodosClientes();
    }

    /**
     * Obtiene una lista con todos los clientes de tipo Estándar.
     *
     * @return Lista de clientes estándar
     */
    public List<Cliente> obtenerClientesEstandar() {
        return datos.obtenerClientesEstandar();
    }

    /**
     * Obtiene una lista con todos los clientes de tipo Premium.
     *
     * @return Lista de clientes premium
     */
    public List<Cliente> obtenerClientesPremium() {
        return datos.obtenerClientesPremium();
    }
}