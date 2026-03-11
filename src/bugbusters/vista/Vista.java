package bugbusters.vista;

import bugbusters.controlador.Controlador;
import bugbusters.modelo.Articulo;
import bugbusters.modelo.Cliente;
import bugbusters.modelo.Pedido;

import bugbusters.modelo.excepciones.RecursoNoEncontradoException;
import bugbusters.modelo.excepciones.YaExisteException;
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

        // Primero comprobamos si el código ya existe
        try {
            controlador.buscarArticulo(codigo);  // Si existe, NO lanza excepción
            // Si llegamos aquí, el artículo SÍ existe
            throw new YaExisteException("artículo", codigo);  // Lanzamos excepción

        } catch (RecursoNoEncontradoException e) {
            // El artículo NO existe, podemos pedir el resto de datos
            String descripcion = leerTexto("Descripción: ");
            double precioVenta = leerDouble("Precio de venta: ");
            double gastosEnvio = leerDouble("Gastos de envío: ");
            int tiempoPreparacionMin = leerEntero("Tiempo de preparación (minutos): ");

            try {
                controlador.anadirArticulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacionMin);
                System.out.println("\n[INFO] Artículo añadido correctamente.");
            } catch (YaExisteException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (YaExisteException e) {
            // Capturamos la excepción que lanzamos si el código ya existe
            System.out.println(e.getMessage());
        }
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
     * anadirCliente()
     * Pide datos por teclado, crea el cliente desde el controlador y lo guarda.
     */
    private void anadirCliente() {
        System.out.println("\n--- Añadir Cliente ---");

        String email = leerTexto("Email: ");

        // Comprobamos si el email ya existe
        try {
            controlador.buscarCliente(email);  // Si existe, NO lanza excepción
            // Si llegamos aquí, el cliente SÍ existe y lanza la excepción
            throw new YaExisteException("cliente", email);

        } catch (RecursoNoEncontradoException e) {
            // Si el cliente NO existe, continuamos
            String nombre = leerTexto("Nombre: ");
            String domicilio = leerTexto("Domicilio: ");
            String nif = leerTexto("NIF: ");
            int tipoCliente = leerEntero("Tipo de cliente (1- Estándar, 2- Premium): ");

            try {
                boolean resultado = controlador.anadirCliente(email, nombre, domicilio, nif, tipoCliente);
                if (resultado) {
                    System.out.println("\n[INFO] Cliente añadido correctamente.");
                }
            } catch (TipoClienteInvalidoException | YaExisteException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (YaExisteException e) {
            System.out.println(e.getMessage());  // Se imprime el mensaje de la excepción
        }
    }

    private void buscarCliente(){
        String email = leerTexto("Introduce el Email del cliente: ");

        try {
            Cliente clienteEncontrado = controlador.buscarCliente(email);
            System.out.println("\n[Datos del Cliente]");
            System.out.println(clienteEncontrado);
        } catch (RecursoNoEncontradoException e) {
            System.out.println(e.getMessage());
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
        System.out.println("\nEliminar Cliente");
        String email = leerTexto("Introduce el Email del cliente: ");

        try {
            // Primero obtenemos sus datos para mostrarlos después (opcional)
            Cliente aEliminar = controlador.buscarCliente(email);

            // Luego lo eliminamos
            controlador.eliminarCliente(email);

            System.out.println("\n[INFO] Cliente eliminado con éxito:");
            System.out.println(aEliminar);

        } catch (RecursoNoEncontradoException e) {
            System.out.println(e.getMessage());
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
        Cliente cliente = null;

        // 2. Intentamos buscar el cliente (ahora lanza excepción)
        try {
            cliente = controlador.buscarCliente(emailCliente);
        } catch (RecursoNoEncontradoException e) {
            // Cliente no existe, lo creamos automáticamente
            System.out.println("[INFO] Cliente no existe. Se creará automáticamente.");

            String nombre = leerTexto("Nombre: ");
            String domicilio = leerTexto("Domicilio: ");
            String nif = leerTexto("NIF: ");
            int tipo = leerEntero("Tipo cliente (1-Estándar, 2-Premium): ");

            //Crea al cliente
            try {
                boolean creado = controlador.anadirCliente(emailCliente, nombre, domicilio, nif, tipo);
                if (!creado) {
                    System.out.println("[ERROR] No se pudo crear el cliente. Pedido cancelado.");
                    return;
                }
            } catch (TipoClienteInvalidoException | YaExisteException ex) {
                System.out.println(ex.getMessage());
                return;
            }

            // Recuperamos el cliente recién creado
            try {
                cliente = controlador.buscarCliente(emailCliente);
                System.out.println("[INFO] Cliente creado correctamente. Continuamos con la creación del pedido.\n");
            } catch (RecursoNoEncontradoException ex) {
                System.out.println("[ERROR] Error inesperado al recuperar el cliente creado.");
                return;
            }
        }

        // 3. Procedemos con la creación del pedido
        try {
            // Pedimos código del artículo
            String codigoArticulo = leerTexto("Código del artículo: ");

            // Verificamos que el artículo exista
            Articulo articulo = controlador.buscarArticulo(codigoArticulo);

            // Pedimos cantidad
            int cantidad = leerEntero("Cantidad: ");

            // Creamos el pedido
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