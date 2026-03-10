package bugbusters.vista;

import bugbusters.controlador.Controlador;
import bugbusters.modelo.Articulo;
import bugbusters.modelo.Cliente;
import bugbusters.modelo.Pedido;
import bugbusters.modelo.ClientePremium;

import java.util.List;
import java.util.Scanner;

/*
 * Clase Vista
 *
 * Se encarga de toda la interacción con el usuario por consola:
 * - mostrar menús
 * - pedir datos
 * - mostrar resultados
 *
 * En MVC:
 * - La vista NO debe acceder al modelo directamente.
 * - La vista solo usa el Controlador.
 *
 * =========================================================
 * BLOQUE IMPLEMENTADO POR: Erick Coll Rodríguez
 * PARTE DESARROLLADA: Menú de artículos
 * =========================================================
 */
public class Vista {

    private Scanner teclado;
    private Controlador controlador;

    /*
     * Constructor
     * Inicializa el Scanner y el Controlador.
     */
    public Vista() {
        teclado = new Scanner(System.in);
        controlador = new Controlador();
    }

    /*
     * iniciar()
     * Muestra el menú principal en bucle hasta que el usuario salga.
     */
    public void iniciar() {
        int opcion;

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
       ========== IMPLEMENTADO POR ERICK COLL RODRÍGUEZ ========
       ========================================================= */

    private void menuArticulos() {
        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE ARTÍCULOS ---");
            System.out.println("1. Añadir artículo");
            System.out.println("2. Mostrar artículos");
            System.out.println("0. Volver");
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
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /*
     * anadirArticulo()
     * Pide datos por teclado, crea el artículo desde el controlador y lo guarda.
     */
    private void anadirArticulo() {
        System.out.println("\nAñadir artículo");

        String codigo = leerTexto("Código: ");
        String descripcion = leerTexto("Descripción: ");
        double precioVenta = leerDouble("Precio de venta: ");
        double gastosEnvio = leerDouble("Gastos de envío: ");
        int tiempoPreparacionMin = leerEntero("Tiempo de preparación (minutos): ");

        // Opción simple: permitir sobrescribir si ya existe (como está en Datos)
        // Si luego queréis evitar repetidos, se mejora con validación/excepciones.
        controlador.anadirArticulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacionMin);

        System.out.println("Artículo añadido correctamente.");
    }

    /*
     * mostrarArticulos()
     * Obtiene la lista desde el controlador y la imprime.
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
    // ==========================================
    //       MENÚ DE CLIENTES
    // ==========================================

    private void menuClientes() {
        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Añadir cliente");
            System.out.println("2. Buscar cliente");
            System.out.println("3. Mostrar todos los clientes");
            System.out.println("4. Mostrar clientes estandar");
            System.out.println("5. Mostrar clientes premium");
            System.out.println("6. Eliminar cliente");
            System.out.println("0. Volver");
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
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /*
     * anadirArticulo()
     * Pide datos por teclado, crea el artículo desde el controlador y lo guarda.
     */
    private void anadirCliente() {
        System.out.println("\nAñadir Cliente");

        String email = leerTexto("Email: ");
        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nif = leerTexto("NIF: ");
        int tipoCliente = leerEntero("Tipo de cliente (1- Estándar, 2- Premium): ");

        // Llamamos al controlador y guardamos el resultado
        boolean resultado = controlador.anadirCliente(email, nombre, domicilio, nif, tipoCliente);

        // Mostramos el mensaje adecuado según lo que pasó en el Controlador/Modelo
        if (resultado) {
            System.out.println("\n[OK] Cliente añadido correctamente.");
        } else {
            System.out.println("\n[ERROR] No se pudo añadir el cliente.");
            System.out.println("Causa posible: Tipo de cliente no válido o el Email ya está registrado.");
        }
    }

    private void buscarCliente(){
        String email = leerTexto("Introduce el Email del cliente: ");

        // Llamamos al controlador, que nos devuelve el objeto Cliente o null
        Cliente clienteEncontrado = controlador.buscarCliente(email);

        if (clienteEncontrado != null) {
            System.out.println("\n[Datos del Cliente]");
            // Al imprimir el objeto, Java llama automáticamente al toString()
            System.out.println(clienteEncontrado);
        } else {
            System.out.println("\n[ERROR] No existe ningún cliente registrado con el email: " + email);
        }

    }

    private void obtenerTodosClientes(){
        System.out.println("\nListado de Clientes:");

        List<Cliente> clientes = controlador.obtenerTodosClientes();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }

    private void obtenerClientesEstandar(){
        System.out.println("\nListado de Clientes Estándar:");

        List<Cliente> clientes = controlador.obtenerClientesEstandar();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes estándar registrados.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }

    }

    private void obtenerClientesPremium(){
        System.out.println("\nListado de Clientes Premium:");
        List<Cliente> clientes = controlador.obtenerClientesPremium();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes premium registrados.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }

    private void eliminarCliente(){
        System.out.println("\nEiminar Cliente");
        String email = leerTexto("Introduce el Email del cliente: ");

        // Buscamos si existe para tener sus datos
        Cliente aEliminar = controlador.buscarCliente(email);

        if (aEliminar != null) {
            // Si existe, lo borramos
            boolean eliminado = controlador.eliminarCliente(email);

            if (eliminado) {
                System.out.println("\n[OK] Cliente eliminado con éxito:");
                System.out.println(aEliminar);
            }
        } else {
            System.out.println("\n[ERROR] No existe ningún cliente registrado con el email: " + email);
        }
    }
    // ==========================================
    //       MENÚ DE PEDIDOS
    // ==========================================

    private void menuPedidos() {
        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Añadir pedido");
            System.out.println("2. Eliminar pedido");
            System.out.println("3. Mostrar pedidos pendientes");
            System.out.println("4. Mostrar pedidos enviados");
            System.out.println("0. Volver");
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
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private void anadirPedido() {
        System.out.println("\nAñadir pedido");

        // Email del cliente
        String emailCliente = leerTexto("Email del cliente: ");

        // Comprobar si el cliente existe
        Cliente clienteExistente = controlador.buscarCliente(emailCliente);

        String nombreCliente;
        boolean esPremium;
        String domicilio;
        String nif;

        if (clienteExistente == null) {
            System.out.println("El cliente no existe. Introduce sus datos:");

            nombreCliente = leerTexto("Nombre: ");
            domicilio = leerTexto("Domicilio: ");
            nif = leerTexto("NIF: ");
            esPremium = leerTexto("¿Cliente premium? (s/n): ").equalsIgnoreCase("s");
        } else {
            nombreCliente = clienteExistente.getNombre();
            esPremium = clienteExistente instanceof ClientePremium;
            domicilio = clienteExistente.getDomicilio();
            nif = clienteExistente.getNif();
        }

        // Datos del artículo
        String codigoArticulo = leerTexto("Código del artículo: ");
        int cantidad = leerEntero("Cantidad: ");

        // Llamada al controlador
        Pedido pedido = controlador.anadirPedido(emailCliente, nombreCliente, esPremium,
                codigoArticulo, cantidad, domicilio, nif);

        if (pedido != null) {
            if (clienteExistente == null) {
                System.out.println("[OK] Cliente creado automáticamente: " + nombreCliente);
            }
            System.out.println("[OK] Pedido añadido correctamente:");
            System.out.println(pedido);
        } else {
            System.out.println("[ERROR] No se pudo crear el pedido. Artículo inexistente.");
        }
    }

    private void eliminarPedido() {
        System.out.println("\nEliminar pedido");
        int numeroPedido = leerEntero("Número de pedido: ");

        boolean eliminado = controlador.eliminarPedido(numeroPedido);
        if (eliminado) {
            System.out.println("[OK] Pedido eliminado correctamente.");
        } else {
            System.out.println("[ERROR] No se pudo eliminar el pedido. Puede que ya haya sido enviado o no exista.");
        }
    }

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

    /*
     * leerTexto()
     * Lee una línea completa de texto.
     */
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine();
    }

    /*
     * leerEntero()
     * Lee un entero. (Versión simple)
     * Si el usuario escribe algo que no es número, puede fallar.
     * Más adelante se puede mejorar con validación.
     */
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Integer.parseInt(teclado.nextLine());
    }

    /*
     * leerDouble()
     * Lee un decimal. (Versión simple)
     * Más adelante se puede mejorar con validación.
     */
    private double leerDouble(String mensaje) {
        System.out.print(mensaje);
        return Double.parseDouble(teclado.nextLine());
    }
}