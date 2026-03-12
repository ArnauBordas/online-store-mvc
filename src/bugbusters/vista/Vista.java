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
 *
 * Se encarga de mostrar los menús, solicitar datos al usuario y mostrar los resultados.
 * En el patrón MVC, la Vista no accede directamente al Modelo, sino que se comunica
 * únicamente a través del Controlador.
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public class Vista {

    private final Scanner teclado;
    private final Controlador controlador;

    /**
     * Constructor que inicializa la vista.
     * Crea el Scanner para leer de teclado y el Controlador para comunicarse con el modelo.
     */
    public Vista() {
        teclado = new Scanner(System.in);
        controlador = new Controlador();
    }

    /**
     * Inicia el bucle principal del programa.
     * Muestra el menú principal y procesa las opciones hasta que el usuario elija salir.
     */
    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("> Selecciona una opción: ");

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
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /* =========================================================
       =================== MENÚ PRINCIPAL ======================
       ========================================================= */

    private void mostrarMenuPrincipal() {
        System.out.println("\n====================================");
        System.out.println("         MENÚ PRINCIPAL");
        System.out.println("====================================");
        System.out.println("1. Gestión de artículos");
        System.out.println("2. Gestión de clientes");
        System.out.println("3. Gestión de pedidos");
        System.out.println("0. Salir");
        System.out.println("====================================");
    }

    /* =========================================================
       ================= MENÚ DE ARTÍCULOS =====================
       ========================================================= */
    /**
     * Gestiona el submenú de artículos.
     * Permite añadir artículos o mostrar el listado completo.
     */
    private void menuArticulos() {
        int opcion;

        do {
            System.out.println("\n------------------------------");
            System.out.println("     GESTIÓN DE ARTÍCULOS     ");
            System.out.println("------------------------------");
            System.out.println("1. Añadir artículo");
            System.out.println("2. Mostrar artículos");
            System.out.println("0. Volver");
            System.out.println("------------------------------");
            opcion = leerEntero("> Selecciona una opción: ");

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
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /**
     * Solicita los datos de un nuevo artículo y lo añade al sistema.
     * Primero verifica si el código ya existe para evitar duplicados.
     */
    private void anadirArticulo() {
        System.out.println("\n--- Añadir artículo ---");

        // 1. Pedimos el código
        String codigo = leerTexto("Código: ");

        // 2. Verificamos si existe, puede lanzar excepción RecursoNoEncontrado si no existe
        try {
            controlador.buscarArticulo(codigo);
            // Si no lanza excepción, es que existe
            System.out.println("[ERROR] Ya existe un artículo con código: " + codigo);
            return;
        } catch (RecursoNoEncontradoException e) {
            // Excepción capturada: El artículo no existe (podemos continuar)
        }

        // 3. Pedimos resto de datos
        String descripcion = leerTexto("Descripción: ");
        double precioVenta = leerDouble("Precio de venta: ");
        double gastosEnvio = leerDouble("Gastos de envío: ");
        int tiempoPreparacionMin = leerEntero("Tiempo de preparación (minutos): ");

        // 4. Añadimos el artículo, pero puede lanzar excepción YaExiste así que se maneja
        try {
            controlador.anadirArticulo(codigo, descripcion, precioVenta,
                    gastosEnvio, tiempoPreparacionMin);
            System.out.println("\n[INFO] Artículo añadido correctamente.");
        } catch (YaExisteException e) {
            // Excepción capturada: Alguien añadió el mismo artículo justo después de nuestra comprobación
            System.out.println(e.getMessage());
        }
    }

    /**
     * Muestra por pantalla todos los artículos registrados.
     * Si no hay artículos, muestra un mensaje informativo.
     */
    private void mostrarArticulos() {
        System.out.println("\nListado de artículos:");

        List<Articulo> articulos = controlador.obtenerTodosArticulos();

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos registrados.");
        } else {
            for (Articulo a : articulos) {
                System.out.println(a);
            }
        }
    }
    /* =========================================================
       ================= MENÚ DE CLIENTES =====================
       ========================================================= */
    /**
     * Gestiona el submenú de clientes.
     * Permite añadir, buscar, mostrar y eliminar clientes.
     */
    private void menuClientes() {
        int opcion;

        do {
            System.out.println("\n------------------------------");
            System.out.println("      GESTIÓN DE CLIENTES     ");
            System.out.println("------------------------------");
            System.out.println("1. Añadir cliente");
            System.out.println("2. Buscar cliente");
            System.out.println("3. Mostrar todos los clientes");
            System.out.println("4. Mostrar clientes estándar");
            System.out.println("5. Mostrar clientes premium");
            System.out.println("6. Eliminar cliente");
            System.out.println("0. Volver");
            System.out.println("------------------------------");
            opcion = leerEntero("> Selecciona una opción: ");

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
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /**
     * Solicita los datos de un nuevo cliente y lo añade al sistema.
     * Valída el formato del email y que no exista previamente.
     */
    private void anadirCliente() {
        System.out.println("\n--- Añadir Cliente ---");
        // 1. Pedimos el email
        String email = leerTexto("Email: ");

        // 2. Validamos formato del email usando el controlador
        if (!controlador.emailValido(email)) {
            System.out.println("[ERROR] Email inválido: " + email);
            return;
        }

        // 3. Verificamos si existe email, puede lanzar excepción RecursoNoEncontrado si no existe
        try {
            controlador.buscarCliente(email); // Si no lanza excepción, es que ya existe
            System.out.println("[ERROR] Ya existe un cliente con email: " + email);
            return;
        } catch (RecursoNoEncontradoException e) {
            // Excepción capturada: El cliente no existe (podemos continuar)
        }

        // 4. Pedimos el resto de datos
        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nif = leerTexto("NIF: ");
        int tipoCliente = leerEntero("Tipo de cliente (1- Estándar, 2- Premium): ");

        // 5. Añadimos el cliente, puede lanzar varias excepciones así que se manejan
        try {
            controlador.anadirCliente(email, nombre, domicilio, nif, tipoCliente);
            System.out.println("\n[INFO] Cliente añadido correctamente.");
        } catch (EmailInvalidoException | TipoClienteInvalidoException | YaExisteException e) {
            System.out.println(e.getMessage()); // Capturamos cualquier excepción durante la creación
        }
    }

    /**
     * Busca un cliente por su email y muestra sus datos.
     */
    private void buscarCliente(){
        String email = leerTexto("Introduce el Email del cliente: ");

        try {
            // Buscamos el cliente, puede lanzar excepción RecursoNoEncontrado si no existe
            Cliente clienteEncontrado = controlador.buscarCliente(email);
            System.out.println("\n[Datos del Cliente]");
            System.out.println(clienteEncontrado);
        } catch (RecursoNoEncontradoException e) {
            System.out.println(e.getMessage()); // Excepción capturada: El cliente no existe
        }
    }

    /**
     * Muestra todos los clientes registrados.
     */
    private void obtenerTodosClientes(){
        System.out.println("\nListado de Clientes:");
        imprimirClientes("No hay clientes registrados.", controlador.obtenerTodosClientes());
    }

    /**
     * Muestra solo los clientes de tipo Estándar.
     */
    private void obtenerClientesEstandar(){
        System.out.println("\nListado de Clientes Estándar:");
        imprimirClientes("No hay clientes estándar registrados.", controlador.obtenerClientesEstandar());
    }

    /**
     * Muestra solo los clientes de tipo Premium.
     */
    private void obtenerClientesPremium(){
        System.out.println("\nListado de Clientes Premium:");
        imprimirClientes("No hay clientes premium registrados.", controlador.obtenerClientesPremium());
    }

    /**
     * Elimina un cliente del sistema previa confirmación.
     */
    private void eliminarCliente(){
        System.out.println("\nEliminar Cliente");
        String email = leerTexto("Introduce el Email del cliente: ");

        try {
            // 1. Buscamos el cliente, puede lanzar excepción RecursoNoEncontrado si no existe
            Cliente aEliminar = controlador.buscarCliente(email);
            // 2. Lo eliminamos (no lanza excepción)
            controlador.eliminarCliente(email);

            System.out.println("\n[INFO] Cliente eliminado con éxito:");
            System.out.println(aEliminar);

        } catch (RecursoNoEncontradoException e) {
            System.out.println(e.getMessage()); // Excepción capturada: El cliente no existe
        }
    }

    /* =========================================================
        ================= MENÚ DE PEDIDOS =====================
       ========================================================= */

    /**
     * Gestiona el submenú de pedidos.
     * Permite añadir, eliminar y mostrar pedidos.
     */
    private void menuPedidos() {
        int opcion;

        do {
            System.out.println("\n------------------------------");
            System.out.println("      GESTIÓN DE PEDIDOS      ");
            System.out.println("------------------------------");
            System.out.println("1. Añadir pedido");
            System.out.println("2. Eliminar pedido");
            System.out.println("3. Mostrar pedidos pendientes");
            System.out.println("4. Mostrar pedidos enviados");
            System.out.println("0. Volver");
            System.out.println("------------------------------");
            opcion = leerEntero("> Selecciona una opción: ");

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
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /**
     * Añade un nuevo pedido al sistema.
     * Si el cliente no existe, lo crea automáticamente.
     */
    private void anadirPedido() {
        System.out.println("\n--- Añadir pedido ---");

        // 1. Pedimos email del cliente
        String emailCliente = leerTexto("Email del cliente: ");
        Cliente cliente;

        // 2. Buscamos el cliente, puede lanzar excepción RecursoNoEncontrado si no existe
        try {
            cliente = controlador.buscarCliente(emailCliente);
            System.out.println("[INFO] Cliente encontrado: " + cliente.getNombre());

        } catch (RecursoNoEncontradoException e) {   // Excepción capturada: Cliente no existe, procedemos a crearlo
            System.out.println("[INFO] Cliente no existe. Se creará automáticamente.\n");
            String nombre = leerTexto("Nombre: ");
            String domicilio = leerTexto("Domicilio: ");
            String nif = leerTexto("NIF: ");
            int tipo = leerEntero("Tipo cliente (1-Estándar, 2-Premium): ");

            try { // Intentamos crear el cliente, puede lanzar varias excepciones
                boolean creado = controlador.anadirCliente(emailCliente, nombre, domicilio, nif, tipo);
                if (!creado) {
                    System.out.println("[ERROR] No se pudo crear el cliente. Pedido cancelado.");
                    return;
                }
                // 3. Cliente creado
                cliente = controlador.buscarCliente(emailCliente);
                System.out.println("[INFO] Cliente creado correctamente: " + cliente.getNombre() + "\n");

            } catch (EmailInvalidoException | TipoClienteInvalidoException | YaExisteException |
                     RecursoNoEncontradoException ex) {
                System.out.println(ex.getMessage()); // Capturamos cualquier excepción durante la creación
                return;
            }
        }

        // 4. Creación del pedido, puede lanzar excepción RecursoNoEncontrado
        try {
            String codigoArticulo = leerTexto("Código del artículo: ");
            // Buscamos el artículo, puede lanzar excepción RecursoNoEncontrado si no existe
            Articulo articulo = controlador.buscarArticulo(codigoArticulo);

            System.out.println("[Artículo: " + articulo.getDescripcion() + " - Precio: " + articulo.getPrecioVenta() + "€]");

            int cantidad = leerEntero("Cantidad: ");
            int tiempoTotal = articulo.getTiempoPreparacionMin() * cantidad;

            // Creamos el pedido, puede lanzar excepción RecursoNoEncontrado
            Pedido pedido = controlador.anadirPedido(emailCliente, codigoArticulo, cantidad);

            System.out.println("\n[INFO] Pedido creado correctamente para " + cliente.getNombre() + ":");
            System.out.println("Artículo: " + articulo.getDescripcion() + " x" + cantidad);
            System.out.println("Tiempo estimado: " + tiempoTotal + " minutos");
            System.out.println(pedido);

        } catch (RecursoNoEncontradoException e) {
            System.out.println(e.getMessage()); // Excepción capturada: Artículo no encontrado
        }
    }

    /**
     * Elimina un pedido si aún puede cancelarse.
     */
    private void eliminarPedido() {
        System.out.println("\nEliminar pedido");
        int numeroPedido = leerEntero("Número de pedido: ");

        try { // Eliminamos el pedido, puede lanzar excepciones: RecursoNoEncontrado / PedidoNoCancelable
            controlador.eliminarPedido(numeroPedido);
            System.out.println("\n[OK] Pedido eliminado correctamente.");
        } catch (RecursoNoEncontradoException | PedidoNoCancelableException e) {
            System.out.println(e.getMessage()); // Excepciones capturadas durante la eliminación
        }
    }

    /**
     * Muestra los pedidos pendientes, opcionalmente filtrados por email.
     */
    private void mostrarPedidosPendientes() {
        System.out.println("\nMostrar pedidos pendientes");
        String emailFiltro = leerTexto("Filtrar por email del cliente (dejar vacío para todos): ");
        if(emailFiltro.isEmpty()) emailFiltro = null;

        List<Pedido> pedidos = controlador.obtenerPedidosPendientes(emailFiltro);
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos pendientes que mostrar.");
        } else {
            pedidos.forEach(System.out::println);
        }
    }

    /**
     * Muestra los pedidos enviados, opcionalmente filtrados por email.
     */
    private void mostrarPedidosEnviados() {
        System.out.println("\nMostrar pedidos enviados");
        String emailFiltro = leerTexto("Filtrar por email del cliente (dejar vacío para todos): ");
        if(emailFiltro.isEmpty()) emailFiltro = null;

        List<Pedido> pedidos = controlador.obtenerPedidosEnviados(emailFiltro);
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos enviados que mostrar.");
        } else {
            pedidos.forEach(System.out::println);
        }
    }
    /* =========================================================
       ================== MÉTODOS AUXILIARES ===================
       ========================================================= */

    /**
     * Lee una línea de texto introducida por el usuario.
     *
     * @param mensaje Mensaje a mostrar al usuario
     * @return Texto introducido
     */
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine();
    }

    /**
     * Lee un número entero introducido por el usuario con validación.
     * Reintenta hasta que se introduzca un valor válido.
     *
     * @param mensaje Mensaje a mostrar al usuario
     * @return Número entero introducido
     */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = teclado.nextLine().trim();

            if (linea.isEmpty()) {
                System.out.println("[ERROR] No se permiten valores vacíos.");
                continue;
            }

            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Debes introducir un número válido.");
            }
        }
    }

    /**
     * Lee un número decimal introducido por el usuario.
     *
     * @param mensaje Mensaje a mostrar al usuario
     * @return Número decimal introducido
     */
    private double leerDouble(String mensaje) {
        System.out.print(mensaje);
        return Double.parseDouble(teclado.nextLine());
    }

    /**
     * Imprime una lista de clientes con un mensaje personalizado si está vacía.
     *
     * @param mensajePersonalizado Mensaje a mostrar si la lista está vacía
     * @param clientes Lista de clientes a imprimir
     */
    private void imprimirClientes(String mensajePersonalizado, List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println(mensajePersonalizado);
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }
}