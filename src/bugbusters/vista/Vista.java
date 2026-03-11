package bugbusters.vista;

import bugbusters.controlador.Controlador;
import bugbusters.modelo.Articulo;
import bugbusters.modelo.Cliente;
import bugbusters.modelo.Pedido;

import bugbusters.modelo.excepciones.RecursoNoEncontradoException;
import bugbusters.modelo.excepciones.ClienteYaExisteException;
import bugbusters.modelo.excepciones.TipoClienteInvalidoException;
import bugbusters.modelo.excepciones.PedidoNoCancelableException;

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
       ========== IMPLEMENTADO POR ERICK COLL RODRÍGUEZ ========
       ========================================================= */

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

    /*
     * anadirArticulo()
     * Pide datos por teclado, crea el artículo desde el controlador y lo guarda.
     */
    private void anadirArticulo() {
        System.out.println("\n--- Añadir artículo ---");
        String codigo = leerTexto("Código: ");
        String descripcion = leerTexto("Descripción: ");
        double precioVenta = leerDouble("Precio de venta: ");
        double gastosEnvio = leerDouble("Gastos de envío: ");
        int tiempoPreparacionMin = leerEntero("Tiempo de preparación (minutos): ");


        controlador.anadirArticulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacionMin);

        System.out.println("\n[INFO] Artículo añadido correctamente.");
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
            System.out.println("\n------------------------------");
            System.out.println("      GESTIÓN DE CLIENTES     ");
            System.out.println("------------------------------");
            System.out.println("1. Añadir cliente");
            System.out.println("2. Buscar cliente");
            System.out.println("3. Mostrar todos los clientes");
            System.out.println("4. Mostrar clientes estandar");
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

    /*
     * anadirArticulo()
     * Pide datos por teclado, crea el artículo desde el controlador y lo guarda.
     */
    private void anadirCliente() {
        System.out.println("\n--- Añadir Cliente ---");

        String email = leerTexto("Email: ");
        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nif = leerTexto("NIF: ");
        int tipoCliente = leerEntero("Tipo de cliente (1- Estándar, 2- Premium): ");

        // Llamamos al controlador y guardamos el resultado
        boolean resultado = controlador.anadirCliente(email, nombre, domicilio, nif, tipoCliente);

        // Mostramos el mensaje adecuado según lo que pasó en el Controlador/Modelo
        if (resultado) {
            System.out.println("\n[INFO] Cliente añadido correctamente.");
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
        imprimirClientes("No hay clientes registrados.", controlador.obtenerTodosClientes());
    }


    private void obtenerClientesEstandar(){
        System.out.println("\nListado de Clientes Estándar:");
        imprimirClientes("No hay clientes estándar registrados.", controlador.obtenerClientesEstandar());
    }


    private void obtenerClientesPremium(){
        System.out.println("\nListado de Clientes Premium:");
        imprimirClientes("No hay clientes premium registrados.", controlador.obtenerClientesPremium());
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
                System.out.println("\n[INFO] Cliente eliminado con éxito:");
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

    private void anadirPedido() {
        System.out.println("\n--- Añadir pedido ---");

        // 1. Pedimos email del cliente
        String emailCliente = leerTexto("Email del cliente: ");
        Cliente cliente = controlador.buscarCliente(emailCliente);

        // 2. Si el cliente no existe, lo creamos inmediatamente
        if (cliente == null) {
            System.out.println("[INFO] Cliente no existe. Se creará automáticamente.");

            String nombre = leerTexto("Nombre: ");
            String domicilio = leerTexto("Domicilio: ");
            String nif = leerTexto("NIF: ");
            int tipo = leerEntero("Tipo cliente (1-Estándar, 2-Premium): ");

            boolean creado = controlador.anadirCliente(emailCliente, nombre, domicilio, nif, tipo);
            if (!creado) {
                System.out.println("[ERROR] No se pudo crear el cliente. Pedido cancelado.");
                return;
            }

            cliente = controlador.buscarCliente(emailCliente); // Actualizamos referencia
            System.out.println("[INFO] Cliente creado correctamente. Continuamos con la creación del pedido.\n");
        }

        try {
            // 3. Pedimos código del artículo
            String codigoArticulo = leerTexto("Código del artículo: ");

            // 4. Verificamos que el artículo exista; si no, lanza excepción
            Articulo articulo = controlador.buscarArticulo(codigoArticulo);
            if (articulo == null) {
                throw new RecursoNoEncontradoException("Artículo", codigoArticulo);
            }

            // 5. Pedimos cantidad
            int cantidad = leerEntero("Cantidad: ");

            // 6. Creamos el pedido
            Pedido pedido = controlador.anadirPedido(emailCliente, codigoArticulo, cantidad);
            System.out.println("\n[INFO] Pedido creado correctamente:");
            System.out.println(pedido);

        } catch (RecursoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void eliminarPedido() {
        System.out.println("\nEliminar pedido");
        int numeroPedido = leerEntero("Número de pedido: ");

        try {
            controlador.eliminarPedido(numeroPedido);
            System.out.println("\n[OK] Pedido eliminado correctamente.");
        } catch (RecursoNoEncontradoException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("[ERROR] " + e.getMessage());
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

    /*
     * imprimirClientes()
     * Metodo para imprimir clientes manteniendo el mensaje personalizado pero simplificando codigo
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