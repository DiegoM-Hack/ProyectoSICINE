package Utilidades;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.net.URL;

/**
 * Clase Estilos proporciona un conjunto de utilidades estéticas reutilizables
 * para personalizar la apariencia visual de componentes Swing en el sistema PoliCine.
 * Aplica una paleta de colores oscuros con detalles rojos y tipografía consistente.
 */
public class Estilos {

    // Colores y fuentes centrales
    public static final Color COLOR_FONDO = new Color(64, 64, 64);            // Negro suave
    public static final Color COLOR_BOTON = new Color(159, 7, 18);           // Rojo cine
    public static final Color COLOR_TEXTO = Color.WHITE;
    public static final Font FUENTE_TEXTO = new Font("Arial", Font.BOLD, 14);
    public static final Color COLOR_BORDES = new Color(180, 0, 0);
    public static final Color COLOR_SELECCION = new Color(180, 0, 0); // Rojo para pestaña activa



    // Aplicar fondo y color de texto a un panel (incluye componentes)
    public static void estiloPanel(JPanel panel) {
        panel.setBackground(COLOR_FONDO);
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel label) {
                label.setForeground(COLOR_TEXTO);
                label.setFont(FUENTE_TEXTO);
            } else if (comp instanceof JButton boton) {
                estiloBoton(boton);
            } else if (comp instanceof JTextComponent campo) {
                estiloCampoTexto(campo);
            } else if (comp instanceof JPanel subPanel) {
                estiloPanel(subPanel);
            }
        }
    }

    // Estilo para botones
    public static void estiloBoton(JButton boton) {
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(FUENTE_TEXTO);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    /**
     * Aplica estilo a campos de texto como JTextField o JPasswordField.
     * @param campo el campo de texto
     */
    public static void estiloCampoTexto(JTextComponent campo) {
        campo.setBackground(new Color(40, 40, 40));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setFont(FUENTE_TEXTO);
        campo.setBorder(BorderFactory.createLineBorder(COLOR_BOTON));
    }

    /**
     * Aplica estilo general a una ventana (JFrame): icono, barra superior.
     * @param frame la ventana a personalizar
     */
    public static void aplicarEstiloVentana(JFrame frame) {
        // Cambia el ícono (usa un archivo en /resources/icono.png)
        ImageIcon icono = new ImageIcon(Estilos.class.getResource("/Img/buho2.png"));
        frame.setIconImage(icono.getImage());

        // Cambia la barra superior si se desea (requiere LookAndFeel)
        UIManager.put("RootPane.background", COLOR_FONDO);
        UIManager.put("activeCaption", COLOR_BOTON);
        UIManager.put("activeCaptionText", COLOR_TEXTO);
    }

    /**
     * Estiliza etiquetas (JLabel) dentro de un panel recursivamente.
     * @param panel panel que contiene etiquetas
     * @param color color del texto
     * @param fuente fuente a aplicar
     */
    public static void estilizarLabels(JPanel panel, Color color, Font fuente) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel label) {
                label.setForeground(color);
                label.setFont(fuente);
            } else if (comp instanceof JPanel subPanel) {
                estilizarLabels(subPanel, color, fuente);
            }
        }
    }

    // Personalizar los JOptionPane
    public static void personalizarJOptionPane() {
        UIManager.put("OptionPane.background", COLOR_FONDO);
        UIManager.put("Panel.background", COLOR_FONDO);
        UIManager.put("OptionPane.messageForeground", COLOR_TEXTO);
        UIManager.put("OptionPane.messageFont", FUENTE_TEXTO);
        UIManager.put("OptionPane.buttonFont", FUENTE_TEXTO);
        UIManager.put("Button.background", COLOR_BOTON);
        UIManager.put("Button.foreground", Color.WHITE);

        // Cargar íconos personalizados para cada tipo de mensaje
        UIManager.put("OptionPane.informationIcon", cargarIconoEscalado("/Img/ok.png"));
        UIManager.put("OptionPane.warningIcon", cargarIconoEscalado("/Img/error.png"));
        UIManager.put("OptionPane.errorIcon", cargarIconoEscalado("/Img/error.png"));
        UIManager.put("OptionPane.questionIcon", cargarIconoEscalado("/Img/interrogacion.png"));
    }

    /**
     * Escala y retorna un icono personalizado.
     * @param ruta ruta del recurso en el proyecto
     * @return el icono escalado
     */
    private static Icon cargarIconoEscalado(String ruta) {
        URL url = Estilos.class.getResource(ruta);
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            Image imagenEscalada = icono.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        }
        return null; // Fallback en caso de que no se encuentre el recurso
    }

    /**
     * Aplica estilo personalizado a una tabla.
     * @param tabla tabla a estilizar
     */
    public static void estiloTabla(JTable tabla) {
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setForeground(Color.WHITE);
        tabla.setBackground(new Color(40, 40, 40));
        tabla.setGridColor(Color.GRAY);
        tabla.setRowHeight(25);
        tabla.setSelectionBackground(new Color(90, 0, 0));

        JTableHeader header = tabla.getTableHeader();
        if (header != null) {
            header.setFont(new Font("Arial", Font.BOLD, 14));
            header.setForeground(Color.WHITE);
            header.setBackground(new Color(120, 0, 0));
            header.setOpaque(true);
        }

        if (tabla.getParent() instanceof JViewport) {
            Component parent = tabla.getParent().getParent(); // JScrollPane
            if (parent instanceof JScrollPane scrollPane) {
                scrollPane.getViewport().setBackground(new Color(30, 30, 30));
                scrollPane.setBorder(BorderFactory.createEmptyBorder());
            }
        }
    }

    /**
     * Aplica estilo personalizado a un JComboBox.
     * @param comboBox ComboBox a estilizar
     */
    // En tu clase Estilos
    public static void estilizarComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(new Color(30, 30, 30));
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(FUENTE_TEXTO);
        comboBox.setBorder(BorderFactory.createLineBorder(COLOR_BORDES));
        comboBox.setFocusable(false);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setBackground(isSelected ? COLOR_BOTON : new Color(30, 30, 30));
                c.setForeground(Color.WHITE);
                return c;
            }
        });
    }

    public static void estilizarSpinner(JSpinner spinner) {
        spinner.setFont(FUENTE_TEXTO);
        spinner.setForeground(Color.WHITE);
        spinner.setBackground(new Color(30, 30, 30));
        spinner.setBorder(BorderFactory.createLineBorder(COLOR_BORDES));

        // Estilizar los componentes internos del spinner
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            tf.setBackground(new Color(30, 30, 30));
            tf.setForeground(Color.WHITE);
            tf.setFont(FUENTE_TEXTO);
            tf.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        }
    }

    public static void estilizarTabla(JTable tabla) {
        tabla.setBackground(new Color(30, 30, 30));
        tabla.setForeground(Color.WHITE);
        tabla.setFont(FUENTE_TEXTO);
        tabla.setGridColor(new Color(80, 0, 0));
        tabla.setSelectionBackground(new Color(120, 0, 0));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setRowHeight(25);

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(150, 0, 0));
        header.setForeground(Color.WHITE);
        header.setFont(FUENTE_TEXTO);
    }

    public static void estilizarTabbedPane(JTabbedPane tabbedPane) {
        tabbedPane.setFont(FUENTE_TEXTO);
        tabbedPane.setBackground(COLOR_FONDO);
        tabbedPane.setForeground(COLOR_TEXTO);

        UIManager.put("TabbedPane.selected", COLOR_SELECCION);
        UIManager.put("TabbedPane.contentAreaColor", COLOR_FONDO);
        UIManager.put("TabbedPane.focus", COLOR_SELECCION);
        UIManager.put("TabbedPane.darkShadow", COLOR_FONDO);
        UIManager.put("TabbedPane.borderHightlightColor", COLOR_SELECCION);
    }

    public static void estilizarTabbedPaneCompleto(JTabbedPane tabbedPane) {
        // Personaliza el propio TabbedPane
        tabbedPane.setBackground(COLOR_FONDO);
        tabbedPane.setForeground(COLOR_TEXTO);
        tabbedPane.setFont(FUENTE_TEXTO);

        // Recorre cada pestaña
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component componente = tabbedPane.getComponentAt(i);
            aplicarEstiloRecursivo(componente);
        }
    }

    public static void aplicarEstiloRecursivo(Component comp) {
        if (comp instanceof JPanel) {
            comp.setBackground(COLOR_FONDO);
            for (Component child : ((JPanel) comp).getComponents()) {
                aplicarEstiloRecursivo(child);
            }
        } else if (comp instanceof JLabel) {
            comp.setForeground(COLOR_TEXTO);
            comp.setFont(FUENTE_TEXTO);
        } else if (comp instanceof JButton) {
            estiloBoton((JButton) comp);
        } else if (comp instanceof JTextField || comp instanceof JTextArea) {
            comp.setBackground(new Color(60, 60, 60));
            comp.setForeground(Color.WHITE);
            comp.setFont(FUENTE_TEXTO);
        } else if (comp instanceof JComboBox) {
            estilizarComboBox((JComboBox<String>) comp);
        } else if (comp instanceof JSpinner) {
            estilizarSpinner((JSpinner) comp);
        } else if ((comp instanceof JTable)) {
            estilizarTabla((JTable) comp);

        }
    }





}

