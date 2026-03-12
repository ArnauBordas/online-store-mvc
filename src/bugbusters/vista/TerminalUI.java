package bugbusters.vista;

import bugbusters.modelo.Articulo;
import bugbusters.modelo.Cliente;
import bugbusters.modelo.Pedido;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class TerminalUI {

    private TerminalUI() {
    }

    public static final String RESET = "\u001B[0m";

    public static final String CYAN = "\u001B[38;5;51m";
    public static final String ELECTRIC = "\u001B[38;5;45m";
    public static final String VIOLET = "\u001B[38;5;141m";
    public static final String GREEN = "\u001B[38;5;46m";
    public static final String YELLOW = "\u001B[38;5;226m";
    public static final String RED = "\u001B[38;5;196m";
    public static final String SILVER = "\u001B[38;5;250m";
    public static final String WHITE = "\u001B[97m";
    public static final String SOFT_BLUE = "\u001B[38;5;117m";

    private static final int MENU_WIDTH = 56;

    // Ajuste fino del separador central
    private static final int DIVIDER_WIDTH = 44;
    private static final String DIVIDER_LEFT_MARGIN = "       ";

    // Dos celdas por fila, cada una con "Etiqueta: valor"
    private static final int BOX_CELL_WIDTH = 27;
    private static final int GRID_INNER_WIDTH = BOX_CELL_WIDTH + BOX_CELL_WIDTH + 1;

    private static String color(String text, String color) {
        return color + text + RESET;
    }

    private static String repeat(String text, int times) {
        return text.repeat(Math.max(0, times));
    }

    private static String truncate(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        if (max <= 3) return text.substring(0, max);
        return text.substring(0, max - 3) + "...";
    }

    private static String money(double value) {
        return String.format("%.2f €", value);
    }

    private static String rightPad(String text, int width) {
        if (text == null) text = "";
        if (text.length() > width) {
            text = truncate(text, width);
        }
        return text + repeat(" ", width - text.length());
    }

    private static String center(String text, int width) {
        if (text == null) text = "";
        if (text.length() > width) {
            text = truncate(text, width);
        }

        int totalSpaces = width - text.length();
        int left = totalSpaces / 2;
        int right = totalSpaces - left;

        return repeat(" ", left) + text + repeat(" ", right);
    }

    private static String padCell(String text, int width) {
        if (text == null) text = "";
        if (text.length() > width) {
            text = truncate(text, width);
        }
        return " " + text + repeat(" ", width - text.length() - 1);
    }

    private static String topDouble(String borderColor) {
        return color("╔" + repeat("═", MENU_WIDTH) + "╗", borderColor);
    }

    private static String midDouble(String borderColor) {
        return color("╠" + repeat("═", MENU_WIDTH) + "╣", borderColor);
    }

    private static String bottomDouble(String borderColor) {
        return color("╚" + repeat("═", MENU_WIDTH) + "╝", borderColor);
    }

    private static String topSingleMenu(String borderColor) {
        return color("┌" + repeat("─", MENU_WIDTH) + "┐", borderColor);
    }

    private static String bottomSingleMenu(String borderColor) {
        return color("└" + repeat("─", MENU_WIDTH) + "┘", borderColor);
    }

    private static String rowDoubleCentered(String text, String borderColor) {
        return color("║", borderColor)
                + center(truncate(text, MENU_WIDTH), MENU_WIDTH)
                + color("║", borderColor);
    }

    private static String rowDoubleLeft(String text, String borderColor) {
        return color("║", borderColor)
                + rightPad(truncate(text, MENU_WIDTH), MENU_WIDTH)
                + color("║", borderColor);
    }

    private static String rowSingleCenteredMenu(String text, String borderColor) {
        return color("│", borderColor)
                + center(truncate(text, MENU_WIDTH), MENU_WIDTH)
                + color("│", borderColor);
    }

    private static String gridTop(String borderColor) {
        return color(
                "┌" + repeat("─", BOX_CELL_WIDTH) + "┬" + repeat("─", BOX_CELL_WIDTH) + "┐",
                borderColor
        );
    }

    private static String gridHeaderSep(String borderColor) {
        return color("├" + repeat("─", GRID_INNER_WIDTH) + "┤", borderColor);
    }

    private static String gridMiddle(String borderColor) {
        return color(
                "├" + repeat("─", BOX_CELL_WIDTH) + "┼" + repeat("─", BOX_CELL_WIDTH) + "┤",
                borderColor
        );
    }

    private static String gridBottom(String borderColor) {
        return color(
                "└" + repeat("─", BOX_CELL_WIDTH) + "┴" + repeat("─", BOX_CELL_WIDTH) + "┘",
                borderColor
        );
    }

    private static String gridTitle(String title, String borderColor) {
        return color("│", borderColor)
                + center(truncate(title, GRID_INNER_WIDTH), GRID_INNER_WIDTH)
                + color("│", borderColor);
    }

    private static String gridRow(String leftCell, String rightCell, String borderColor) {
        return color("│", borderColor)
                + padCell(truncate(leftCell, BOX_CELL_WIDTH), BOX_CELL_WIDTH)
                + color("│", borderColor)
                + padCell(truncate(rightCell, BOX_CELL_WIDTH), BOX_CELL_WIDTH)
                + color("│", borderColor);
    }

    private static String gridRowFull(String content, String borderColor) {
        return color("│", borderColor)
                + rightPad(truncate(content, GRID_INNER_WIDTH), GRID_INNER_WIDTH)
                + color("│", borderColor);
    }

    private static void printGridCard(String title, List<String[]> rows, String borderColor) {
        System.out.println(gridTop(borderColor));
        System.out.println(gridTitle(title, borderColor));
        System.out.println(gridHeaderSep(borderColor));

        for (int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);

            String left = row.length > 0 ? row[0] : "";
            String right = row.length > 1 ? row[1] : "";

            if (!left.isEmpty() && right.isEmpty()) {
                System.out.println(gridRowFull(left, borderColor));
            } else {
                System.out.println(gridRow(left, right, borderColor));
            }

            if (i < rows.size() - 1) {
                System.out.println(gridMiddle(borderColor));
            }
        }

        System.out.println(gridBottom(borderColor));
    }

    public static void showWelcome() {
        System.out.println();
        System.out.println(topDouble(CYAN));
        System.out.println(rowDoubleCentered("◈ BUGBUSTERS SHOP MANAGER ◈", CYAN));
        System.out.println(midDouble(CYAN));
        System.out.println(rowDoubleCentered("Sistema de gestión en consola · Premium Terminal Edition", CYAN));
        System.out.println(bottomDouble(CYAN));

        // Misma distancia arriba que abajo respecto al separador
        System.out.println();
        sciFiDivider();
        System.out.println();
    }

    public static void showGoodbye() {
        System.out.println();
        System.out.println(topDouble(VIOLET));
        System.out.println(rowDoubleCentered("◈ SESIÓN FINALIZADA ◈", VIOLET));
        System.out.println(midDouble(VIOLET));
        System.out.println(rowDoubleCentered("Gracias por usar BugBusters Shop", VIOLET));
        System.out.println(bottomDouble(VIOLET));
    }

    public static void showMenu(String title, String[] options) {
        System.out.println();
        System.out.println(topDouble(ELECTRIC));
        System.out.println(rowDoubleCentered("◈ " + title + " ◈", ELECTRIC));
        System.out.println(midDouble(ELECTRIC));

        for (String option : options) {
            String content = "  " + truncate(option, MENU_WIDTH - 2);
            System.out.println(rowDoubleLeft(content, ELECTRIC));
        }

        System.out.println(bottomDouble(ELECTRIC));
    }

    public static void sectionTitle(String title) {
        System.out.println();
        System.out.println(topSingleMenu(CYAN));
        System.out.println(rowSingleCenteredMenu("◆ " + title + " ◆", CYAN));
        System.out.println(bottomSingleMenu(CYAN));
    }

    public static void sciFiDivider() {
        String centerSymbol = "◆";
        int left = (DIVIDER_WIDTH - 1) / 2;
        int right = DIVIDER_WIDTH - 1 - left;

        String line = DIVIDER_LEFT_MARGIN + repeat("─", left) + centerSymbol + repeat("─", right);
        System.out.println(color(line, SOFT_BLUE));
    }

    public static void prompt(String message) {
        System.out.print(color("» ", VIOLET) + color(message, WHITE));
    }

    public static void success(String message) {
        System.out.println(color("[OK] ", GREEN) + color(message, WHITE));
    }

    public static void info(String message) {
        System.out.println(color("[INFO] ", CYAN) + color(message, WHITE));
    }

    public static void warning(String message) {
        System.out.println(color("[AVISO] ", YELLOW) + color(message, WHITE));
    }

    public static void error(String message) {
        System.out.println(color("[ERROR] ", RED) + color(message, WHITE));
    }

    public static void empty(String message) {
        System.out.println(color("[SIN DATOS] ", YELLOW) + color(message, SILVER));
    }

    public static void spotlight(String message) {
        System.out.println();
        System.out.println(color("╭" + repeat("─", MENU_WIDTH) + "╮", VIOLET));
        System.out.println(color("│", VIOLET) + center(truncate(message, MENU_WIDTH), MENU_WIDTH) + color("│", VIOLET));
        System.out.println(color("╰" + repeat("─", MENU_WIDTH) + "╯", VIOLET));
    }

    public static void showArticlesTable(List<Articulo> articles) {
        if (articles == null || articles.isEmpty()) {
            empty("No hay artículos registrados.");
            return;
        }

        System.out.println(color("┌────────────┬──────────────────────────────┬────────────┬────────────┬────────────┐", ELECTRIC));
        System.out.println(color("│", ELECTRIC) + center("CÓDIGO", 12) + color("│", ELECTRIC)
                + center("DESCRIPCIÓN", 30) + color("│", ELECTRIC)
                + center("PRECIO", 12) + color("│", ELECTRIC)
                + center("ENVÍO", 12) + color("│", ELECTRIC)
                + center("PREP.", 12) + color("│", ELECTRIC));
        System.out.println(color("├────────────┼──────────────────────────────┼────────────┼────────────┼────────────┤", ELECTRIC));

        for (Articulo a : articles) {
            System.out.println(color("│", ELECTRIC)
                    + padCell(truncate(a.getCodigo(), 12), 12)
                    + color("│", ELECTRIC)
                    + padCell(truncate(a.getDescripcion(), 30), 30)
                    + color("│", ELECTRIC)
                    + padCell(money(a.getPrecioVenta()), 12)
                    + color("│", ELECTRIC)
                    + padCell(money(a.getGastosEnvio()), 12)
                    + color("│", ELECTRIC)
                    + padCell(a.getTiempoPreparacionMin() + " min", 12)
                    + color("│", ELECTRIC));
        }

        System.out.println(color("└────────────┴──────────────────────────────┴────────────┴────────────┴────────────┘", ELECTRIC));
    }

    public static void showClientsTable(List<Cliente> clients) {
        if (clients == null || clients.isEmpty()) {
            empty("No hay clientes para mostrar.");
            return;
        }

        int count = 1;

        for (Cliente c : clients) {
            String type = c.esPremium() ? "Premium" : "Estándar";
            String discount = String.format("%.0f%%", c.descuentoEnvio() * 100);

            List<String[]> rows = new ArrayList<>();
            rows.add(new String[]{"Tipo: " + type, "Nombre: " + c.getNombre()});
            rows.add(new String[]{"Email: " + c.getEmail(), "NIF: " + c.getNif()});
            rows.add(new String[]{"Domicilio: " + c.getDomicilio(), "Cuota: " + money(c.calcularCuota())});
            rows.add(new String[]{"Descuento: " + discount, ""});

            printGridCard("CLIENTE " + count, rows, ELECTRIC);
            count++;
        }
    }

    public static void showOrdersTable(List<Pedido> orders) {
        if (orders == null || orders.isEmpty()) {
            empty("No hay pedidos para mostrar.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println(color("┌────────┬──────────────────────┬──────────────────────────┬──────────┬──────────────────┬────────────┬──────────────┐", ELECTRIC));
        System.out.println(color("│", ELECTRIC) + center("NÚMERO", 8) + color("│", ELECTRIC)
                + center("CLIENTE", 22) + color("│", ELECTRIC)
                + center("ARTÍCULO", 26) + color("│", ELECTRIC)
                + center("CANT.", 10) + color("│", ELECTRIC)
                + center("FECHA", 18) + color("│", ELECTRIC)
                + center("TOTAL", 12) + color("│", ELECTRIC)
                + center("ESTADO", 14) + color("│", ELECTRIC));
        System.out.println(color("├────────┼──────────────────────┼──────────────────────────┼──────────┼──────────────────┼────────────┼──────────────┤", ELECTRIC));

        for (Pedido p : orders) {
            String state = p.puedeCancelar() ? "Pendiente" : "Enviado";

            System.out.println(color("│", ELECTRIC)
                    + padCell(String.valueOf(p.getNumeroPedido()), 8)
                    + color("│", ELECTRIC)
                    + padCell(truncate(p.getCliente().getNombre(), 22), 22)
                    + color("│", ELECTRIC)
                    + padCell(truncate(p.getArticulo().getDescripcion(), 26), 26)
                    + color("│", ELECTRIC)
                    + padCell(String.valueOf(p.getCantidad()), 10)
                    + color("│", ELECTRIC)
                    + padCell(p.getFechaHora().format(formatter), 18)
                    + color("│", ELECTRIC)
                    + padCell(money(p.calcularTotal()), 12)
                    + color("│", ELECTRIC)
                    + padCell(state, 14)
                    + color("│", ELECTRIC));
        }

        System.out.println(color("└────────┴──────────────────────┴──────────────────────────┴──────────┴──────────────────┴────────────┴──────────────┘", ELECTRIC));
    }

    public static void showClientCard(Cliente client) {
        if (client == null) return;

        String type = client.esPremium() ? "Premium" : "Estándar";
        String discount = String.format("%.0f%%", client.descuentoEnvio() * 100);

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Tipo: " + type, "Nombre: " + client.getNombre()});
        rows.add(new String[]{"Email: " + client.getEmail(), "NIF: " + client.getNif()});
        rows.add(new String[]{"Domicilio: " + client.getDomicilio(), "Cuota: " + money(client.calcularCuota())});
        rows.add(new String[]{"Descuento: " + discount, ""});

        printGridCard("CLIENTE ENCONTRADO", rows, CYAN);
    }

    public static void showOrderCard(Pedido order) {
        if (order == null) return;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String state = order.puedeCancelar() ? "Pendiente / cancelable" : "Enviado / no cancelable";

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Número: " + order.getNumeroPedido(), "Cantidad: " + order.getCantidad()});
        rows.add(new String[]{"Cliente: " + order.getCliente().getNombre(), "Total: " + money(order.calcularTotal())});
        rows.add(new String[]{"Artículo: " + order.getArticulo().getDescripcion(), "Fecha: " + order.getFechaHora().format(formatter)});
        rows.add(new String[]{"Estado: " + state, ""});

        printGridCard("PEDIDO CREADO", rows, CYAN);
    }

    public static void showArticleCard(Articulo article) {
        if (article == null) return;

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Código: " + article.getCodigo(), "Precio: " + money(article.getPrecioVenta())});
        rows.add(new String[]{"Descripción: " + article.getDescripcion(), "Envío: " + money(article.getGastosEnvio())});
        rows.add(new String[]{"Preparación: " + article.getTiempoPreparacionMin() + " min", ""});

        printGridCard("ARTÍCULO SELECCIONADO", rows, CYAN);
    }
}