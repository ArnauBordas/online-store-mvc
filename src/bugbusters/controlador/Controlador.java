package bugbusters.controlador;

import bugbusters.modelo.Articulo;
import bugbusters.modelo.Cliente;
import bugbusters.modelo.ClienteEstandar;
import bugbusters.modelo.ClientePremium;
import bugbusters.modelo.Datos;
import bugbusters.modelo.Pedido;

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
                               double gastosEnvio, int tiempoPreparacionMin) {

        Articulo articulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacionMin);
        datos.anadirArticulo(articulo);
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
    public Articulo buscarArticulo(String codigo) {
        return datos.buscarArticulo(codigo);
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
    public Pedido anadirPedido(String emailCliente, String nombreCliente, boolean esPremium,
                               String codigoArticulo, int cantidad, String domicilio, String nif) {

        // Buscar cliente
        Cliente cliente = datos.buscarCliente(emailCliente);

        // Crear cliente si no existe
        if (cliente == null) {
            if (esPremium) {
                cliente = new ClientePremium(emailCliente, nombreCliente, domicilio, nif);
            } else {
                cliente = new ClienteEstandar(emailCliente, nombreCliente, domicilio, nif);
            }
            datos.anadirCliente(cliente);
        }

        // Buscar artículo
        Articulo articulo = datos.buscarArticulo(codigoArticulo);
        if (articulo == null) {
            return null; // artículo inexistente
        }

        // Crear pedido
        int numeroPedido = datos.generarNumeroPedido();
        Pedido pedido = new Pedido(numeroPedido, cliente, articulo, cantidad, LocalDateTime.now());

        // Añadir pedido
        datos.anadirPedido(pedido);

        return pedido;
    }

    /**
     * Elimina un pedido.
     * Devuelve:
     * - true si se eliminó correctamente
     * - false si no existe o no se puede eliminar
     */
    public boolean eliminarPedido(int numeroPedido) {
        Pedido pedido = datos.buscarPedido(numeroPedido);
        if (pedido == null) return false;
        if (!pedido.puedeCancelar()) return false;

        datos.eliminarPedido(pedido);
        return true;
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
    public boolean anadirCliente(String email, String nombre, String domicilio, String nif,  int tipoCliente) {
        Cliente nuevoCliente;

        // Aquí es donde decidimos qué "forma" toma el objeto
        if (tipoCliente == 1) {
            nuevoCliente = new ClienteEstandar(email, nombre, domicilio, nif);
        } else if (tipoCliente == 2) {
            nuevoCliente = new ClientePremium(email, nombre, domicilio, nif);
        } else
            return false;

        return datos.anadirCliente(nuevoCliente);
    }

    /**
     * eliminarCliente()
     * Solicita al modelo la eliminar un cliente por email.
     */
    public boolean eliminarCliente(String email) {
        return datos.eliminarCliente(email);
    }

    /**
     * buscarCliente()
     * Devuelve el objeto cliente buscado per email.
     */
    public Cliente buscarCliente(String email) {
        return datos.buscarCliente(email);
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