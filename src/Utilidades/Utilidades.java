package Utilidades;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Utilidades {

    /**
     * Aplica el ícono personalizado a un JFrame.
     */
    public static void aplicarIcono(JFrame frame) {
        try {
            URL url = frame.getClass().getResource("/Img/buho.jpg");
            if (url != null) {
                ImageIcon icono = new ImageIcon(url);
                Image imagenGrande = icono.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
                frame.setIconImage(imagenGrande);
            } else {
                System.out.println("⚠️ No se encontró el ícono.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Establece una fuente común para un componente.
     */
    public static void aplicarFuente(JComponent componente, int estilo, int tamaño) {
        componente.setFont(new Font("Segoe UI", estilo, tamaño));
    }

    /**
     * Aplica colores estándar a botones.
     */
    public static void estiloBoton(JButton boton) {
        boton.setBackground(new Color(43, 127, 255));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    /**
     * Aplica un estilo general al panel principal.
     */
    public static void estiloPanel(JPanel panel) {
        panel.setBackground(new Color(244, 244, 245));
    }

    /**
     * Aplica estilos a las tablas.
     */
    public static void estiloTabla(JTable tabla) {
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(33, 150, 243));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setGridColor(new Color(200, 200, 200));
    }

    public static void personalizarJOptionPane() {
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Button.background", new Color(220, 220, 220));
        UIManager.put("Button.foreground", Color.BLACK);
    }

    public static void aplicarEstilo(JFrame frame) {
        // Cambiar el color del fondo
        frame.getContentPane().setBackground(new Color(193, 16, 7)); // negro suave

        // Cambiar el ícono
        aplicarIcono(frame);

        // Cambiar la barra superior con decoraciones personalizadas
        frame.getRootPane().putClientProperty("JRootPane.titleBarBackground", Color.RED);
        frame.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
    }

    public static void estilizarLabels(JPanel panel, Color colorTexto, Font fuente) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel) {
                c.setForeground(colorTexto);
                c.setFont(fuente);
            }
        }
    }






}
