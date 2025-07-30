package Formularios;

import Modelos.Usuario;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;


public class FormularioCRUTCajero extends JFrame {
    private JPanel panelPrincipal;
    private JLabel nombreCajeroLabel;
    private JButton buscarFuncionesButton;
    private JButton venderEntradasButton;
    private JButton historialVentasButton;
    private JButton salirButton;

    private Usuario usuario;

    public FormularioCRUTCajero(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Menú de Cajero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(500, 200);
        setLocationRelativeTo(null);

        Estilos.estiloPanel(panelPrincipal);
        Estilos.estiloBoton(salirButton);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panelPrincipal, Color.WHITE, new Font("Arial", Font.BOLD, 14));


        // Mostrar el nombre del cajero
        nombreCajeroLabel.setText("Cajero: " + usuario.getUsuario());

        // BOTÓN: Ver funciones
        buscarFuncionesButton.addActionListener(e -> {
            dispose(); // Cierra esta ventana
            new VerFuncionesPanel(usuario); // Asegúrate de que VerFuncionesPanel es JFrame
        });

        // BOTÓN: Vender entradas
        venderEntradasButton.addActionListener(e -> {
            dispose(); // Cierra esta ventana
            new FormularioVentaEntradas(usuario).setVisible(true); // Asegúrate de que este también hereda de JFrame
        });

        //  BOTÓN: Historial de ventas
        historialVentasButton.addActionListener(e -> {
            dispose();
            new HistorialVentas(usuario).setVisible(true);
        });

        //  BOTÓN: Salir (volver al login)
        salirButton.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(
                    panelPrincipal,
                    "¿Deseas cerrar sesión?",
                    "Cerrar sesión",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                JFrame login = new Login(); // Asegúrate de que Login es JFrame
                login.setVisible(true);
                dispose(); // Cierra esta ventana actual
            }
        });
    }

    public JPanel getPanel() {
        return panelPrincipal;
    }
}
