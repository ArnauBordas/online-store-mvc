package bugbusters.vista;

import bugbusters.controlador.Controlador;
import bugbusters.modelo.Articulo;
import bugbusters.modelo.Cliente;
import bugbusters.modelo.Pedido;
import bugbusters.modelo.excepciones.*;

import java.util.List;
import java.util.Scanner;

/**
 * Clase que gestiona la interacción con el usuario a través de la consola.
 * Solo se ha mejorado la presentación visual.
 * No modifica la lógica del programa.
 */
public class Vista {

    private final Scanner teclado;
    private final Controlador controlador;

    public Vista() {
        teclado = new Scanner(System.in);
        controlador = new Controlador();
    }

    public void iniciar() {
        int opcion;

        TerminalUI.showWelcome();

        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    menuArticulos();
                    break;
                case 2:
                    menuClientes();
                    break;
                case 3:
                    menuPedidos();
                    break;
                case 0:
                    TerminalUI.info("Saliendo del programa...");
                    TerminalUI.showGoodbye();
                    break;
                default:
                    TerminalUI.error("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        TerminalUI.showMenu("MENÚ PRINCIPAL", new String[]{
                "1. Gestión de artículos",
                "2. Gestión de clientes",
                "3. Gestión de pedidos",
                "0. Salir"
        });
    }

    private void menuArticulos() {
        int opcion;

        do {
            TerminalUI.showMenu("GESTIÓN DE ARTÍCULOS", new String[]{
                    "1. Añadir artículo",
                    "2. Mostrar artículos",
                    "0. Volver"
            });

            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    anadirArticulo();
                    break;
                case 2:
                    mostrarArticulos();
                    break;
                case 0:
                    break;
                default:
                    TerminalUI.error("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private void anadirArticulo() {
        TerminalUI.sectionTitle("AÑADIR ARTÍCULO");

        String codigo = leerTexto("Código: ");

        try {
            controlador.buscarArticulo(codigo);
            TerminalUI.error("Ya existe un artículo con código: " + codigo);
            return;
        } catch (RecursoNoEncontradoException e) {
        }

        String descripcion = leerTexto("Descripción: ");
        double precioVenta = leerDouble("Precio de venta: ");
        double gastosEnvio = leerDouble("Gastos de envío: ");
        int tiempoPreparacionMin = leerEntero("Tiempo de preparación (minutos): ");

        try {
            controlador.anadirArticulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacionMin);
            TerminalUI.success("Artículo añadido correctamente.");
            TerminalUI.sciFiDivider();
        } catch (YaExisteException e) {
            TerminalUI.error(e.getMessage());
        }
    }

    private void mostrarArticulos() {
        TerminalUI.sectionTitle("LISTADO DE ARTÍCULOS");
        TerminalUI.showArticlesTable(controlador.obtenerTodosArticulos());
    }

    private void menuClientes() {
        int opcion;

        do {
            TerminalUI.showMenu("GESTIÓN DE CLIENTES", new String[]{
                    "1. Añadir cliente",
                    "2. Buscar cliente",
                    "3. Mostrar todos los clientes",
                    "4. Mostrar clientes estándar",
                    "5. Mostrar clientes premium",
                    "6. Eliminar cliente",
                    "0. Volver"
            });

            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    anadirCliente();
                    break;
                case 2:
                    buscarCliente();
                    break;
                case 3:
                    obtenerTodosClientes();
                    break;
                case 4:
                    obtenerClientesEstandar();
                    break;
                case 5:
                    obtenerClientesPremium();
                    break;
                case 6:
                    eliminarCliente();
                    break;
                case 0:
                    break;
                default:
                    TerminalUI.error("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private void anadirCliente() {
        TerminalUI.sectionTitle("AÑADIR CLIENTE");

        String email = leerTexto("Email: ");

        if (!controlador.emailValido(email)) {
            TerminalUI.error("Email inválido: " + email);
            return;
        }

        try {
            controlador.buscarCliente(email);
            TerminalUI.error("Ya existe un cliente con email: " + email);
            return;
        } catch (RecursoNoEncontradoException e) {
        }

        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nif = leerTexto("NIF: ");
        int tipoCliente = leerEntero("Tipo de cliente (1- Estándar, 2- Premium): ");

        try {
            controlador.anadirCliente(email, nombre, domicilio, nif, tipoCliente);
            TerminalUI.success("Cliente añadido correctamente.");
            TerminalUI.sciFiDivider();
        } catch (EmailInvalidoException | TipoClienteInvalidoException | YaExisteException e) {
            TerminalUI.error(e.getMessage());
        }
    }

    private void buscarCliente() {
        TerminalUI.sectionTitle("BUSCAR CLIENTE");

        String email = leerTexto("Introduce el Email del cliente: ");

        try {
            Cliente clienteEncontrado = controlador.buscarCliente(email);
            TerminalUI.showClientCard(clienteEncontrado);
        } catch (RecursoNoEncontradoException e) {
            TerminalUI.error(e.getMessage());
        }
    }

    private void obtenerTodosClientes() {
        TerminalUI.sectionTitle("LISTADO DE TODOS LOS CLIENTES");
        imprimirClientes("No hay clientes registrados.", controlador.obtenerTodosClientes());
    }

    private void obtenerClientesEstandar() {
        TerminalUI.sectionTitle("LISTADO DE CLIENTES ESTÁNDAR");
        imprimirClientes("No hay clientes estándar registrados.", controlador.obtenerClientesEstandar());
    }

    private void obtenerClientesPremium() {
        TerminalUI.sectionTitle("LISTADO DE CLIENTES PREMIUM");
        imprimirClientes("No hay clientes premium registrados.", controlador.obtenerClientesPremium());
    }

    private void eliminarCliente() {
        TerminalUI.sectionTitle("ELIMINAR CLIENTE");

        String email = leerTexto("Introduce el Email del cliente: ");

        try {
            Cliente aEliminar = controlador.buscarCliente(email);

            TerminalUI.info("Cliente localizado correctamente.");
            TerminalUI.showClientCard(aEliminar);

            controlador.eliminarCliente(email);

            TerminalUI.success("Cliente eliminado con éxito.");
            TerminalUI.spotlight("REGISTRO ELIMINADO DEL SISTEMA");

        } catch (RecursoNoEncontradoException e) {
            TerminalUI.error(e.getMessage());
        }
    }

    private void menuPedidos() {
        int opcion;

        do {
            TerminalUI.showMenu("GESTIÓN DE PEDIDOS", new String[]{
                    "1. Añadir pedido",
                    "2. Eliminar pedido",
                    "3. Mostrar pedidos pendientes",
                    "4. Mostrar pedidos enviados",
                    "0. Volver"
            });

            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    anadirPedido();
                    break;
                case 2:
                    eliminarPedido();
                    break;
                case 3:
                    mostrarPedidosPendientes();
                    break;
                case 4:
                    mostrarPedidosEnviados();
                    break;
                case 0:
                    break;
                default:
                    TerminalUI.error("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private void anadirPedido() {
        TerminalUI.sectionTitle("AÑADIR PEDIDO");

        String emailCliente = leerTexto("Email del cliente: ");
        Cliente cliente;

        try {
            cliente = controlador.buscarCliente(emailCliente);
            TerminalUI.info("Cliente encontrado: " + cliente.getNombre());

        } catch (RecursoNoEncontradoException e) {
            TerminalUI.warning("Cliente no existe. Se creará automáticamente.");

            String nombre = leerTexto("Nombre: ");
            String domicilio = leerTexto("Domicilio: ");
            String nif = leerTexto("NIF: ");
            int tipo = leerEntero("Tipo cliente (1-Estándar, 2-Premium): ");

            try {
                boolean creado = controlador.anadirCliente(emailCliente, nombre, domicilio, nif, tipo);
                if (!creado) {
                    TerminalUI.error("No se pudo crear el cliente. Pedido cancelado.");
                    return;
                }

                cliente = controlador.buscarCliente(emailCliente);
                TerminalUI.success("Cliente creado correctamente: " + cliente.getNombre());

            } catch (EmailInvalidoException | TipoClienteInvalidoException | YaExisteException |
                     RecursoNoEncontradoException ex) {
                TerminalUI.error(ex.getMessage());
                return;
            }
        }

        try {
            String codigoArticulo = leerTexto("Código del artículo: ");
            Articulo articulo = controlador.buscarArticulo(codigoArticulo);

            TerminalUI.showArticleCard(articulo);

            int cantidad = leerEntero("Cantidad: ");
            int tiempoTotal = articulo.getTiempoPreparacionMin() * cantidad;

            Pedido pedido = controlador.anadirPedido(emailCliente, codigoArticulo, cantidad);

            TerminalUI.success("Pedido creado correctamente para " + cliente.getNombre() + ".");
            TerminalUI.info("Tiempo estimado: " + tiempoTotal + " minutos");
            TerminalUI.showOrderCard(pedido);
            TerminalUI.spotlight("OPERACIÓN COMPLETADA CON ÉXITO");

        } catch (RecursoNoEncontradoException e) {
            TerminalUI.error(e.getMessage());
        }
    }

    private void eliminarPedido() {
        TerminalUI.sectionTitle("ELIMINAR PEDIDO");

        int numeroPedido = leerEntero("Número de pedido: ");

        try {
            controlador.eliminarPedido(numeroPedido);
            TerminalUI.success("Pedido eliminado correctamente.");
            TerminalUI.spotlight("PEDIDO CANCELADO");
        } catch (RecursoNoEncontradoException | PedidoNoCancelableException e) {
            TerminalUI.error(e.getMessage());
        }
    }

    private void mostrarPedidosPendientes() {
        TerminalUI.sectionTitle("PEDIDOS PENDIENTES");

        String emailFiltro = leerTexto("Filtrar por email del cliente (dejar vacío para todos): ");
        if (emailFiltro.isEmpty()) emailFiltro = null;

        List<Pedido> pedidos = controlador.obtenerPedidosPendientes(emailFiltro);

        if (pedidos.isEmpty()) {
            TerminalUI.empty("No hay pedidos pendientes que mostrar.");
        } else {
            TerminalUI.showOrdersTable(pedidos);
        }
    }

    private void mostrarPedidosEnviados() {
        TerminalUI.sectionTitle("PEDIDOS ENVIADOS");

        String emailFiltro = leerTexto("Filtrar por email del cliente (dejar vacío para todos): ");
        if (emailFiltro.isEmpty()) emailFiltro = null;

        List<Pedido> pedidos = controlador.obtenerPedidosEnviados(emailFiltro);

        if (pedidos.isEmpty()) {
            TerminalUI.empty("No hay pedidos enviados que mostrar.");
        } else {
            TerminalUI.showOrdersTable(pedidos);
        }
    }

    private String leerTexto(String mensaje) {
        TerminalUI.prompt(mensaje);
        return teclado.nextLine();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            TerminalUI.prompt(mensaje);
            String linea = teclado.nextLine().trim();

            if (linea.isEmpty()) {
                TerminalUI.error("No se permiten valores vacíos.");
                continue;
            }

            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                TerminalUI.error("Debes introducir un número válido.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        TerminalUI.prompt(mensaje);
        return Double.parseDouble(teclado.nextLine());
    }

    private void imprimirClientes(String mensajePersonalizado, List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            TerminalUI.empty(mensajePersonalizado);
        } else {
            TerminalUI.showClientsTable(clientes);
        }
    }
}