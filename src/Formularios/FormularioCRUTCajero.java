package Formularios;

import Modelos.Usuario;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;


/**
 * Clase FormularioCRUTCajero representa el menú principal de funcionalidades disponibles para un cajero.
 * Desde aquí se puede acceder a ver funciones, vender entradas, consultar historial de ventas o cerrar sesión.
 */
public class FormularioCRUTCajero extends JFrame {

    private JPanel panelPrincipal;
    private JLabel nombreCajeroLabel;
    private JButton buscarFuncionesButton;
    private JButton venderEntradasButton;
    private JButton historialVentasButton;
    private JButton salirButton;

    private Usuario usuario;

    /**
     * Constructor que inicializa el menú del cajero y aplica los estilos visuales y eventos.
     *
     * @param usuario Usuario autenticado con rol de cajero
     */
    public FormularioCRUTCajero(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Menú de Cajero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(500, 200);
        setLocationRelativeTo(null);

        // Aplicar estilos visuales personalizados
        Estilos.estiloPanel(panelPrincipal);
        Estilos.estiloBoton(salirButton);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panelPrincipal, Color.WHITE, new Font("Arial", Font.BOLD, 14));

        // Mostrar el nombre del cajero
        nombreCajeroLabel.setText("Cajero: " + usuario.getUsuario());

        // Botón para ver funciones
        buscarFuncionesButton.addActionListener(e -> {
            dispose(); // Cierra esta ventana
            new VerFuncionesPanel(usuario); // Abre panel de funciones
        });

        // Botón para vender entradas
        venderEntradasButton.addActionListener(e -> {
            dispose(); // Cierra esta ventana
            new FormularioVentaEntradas(usuario).setVisible(true); // Abre formulario de venta
        });

        // Botón para ver historial de ventas
        historialVentasButton.addActionListener(e -> {
            dispose();
            new HistorialVentas(usuario).setVisible(true);
        });

        // Botón para salir (cerrar sesión y volver al login)
        salirButton.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(
                    panelPrincipal,
                    "¿Deseas cerrar sesión?",
                    "Cerrar sesión",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                JFrame login = new Login(); // Volver al login
                login.setVisible(true);
                dispose(); // Cierra esta ventana actual
            }
        });
    }

    /**
     * Devuelve el panel principal (util para integraciones o pruebas).
     *
     * @return panel principal del formulario
     */
    public JPanel getPanel() {
        return panelPrincipal;
    }
}

