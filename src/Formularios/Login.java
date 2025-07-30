package Formularios;

import Modelos.Usuario;
import Servicios.UsuarioService;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JPanel principal;
    private JTextField usuario;
    private JPasswordField Clave;
    private JButton ingresarButton;
    private JButton salirButton;

    private UsuarioService usuarioService = new UsuarioService();

    public Login() {
        setTitle("PoliCine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(principal);
        setSize(550, 200);
        setLocationRelativeTo(null);

        // Aplicar estilos
        Estilos.estiloPanel(principal);
        Estilos.estiloBoton(ingresarButton);
        Estilos.estiloBoton(salirButton);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(principal, Color.WHITE, new Font("Arial", Font.BOLD, 14));
        Estilos.estiloCampoTexto(usuario);
        Estilos.estiloCampoTexto(Clave);

        setVisible(true);

        ingresarButton.addActionListener(e -> {
            String nombreUsuario = usuario.getText();
            String claveIngresada = new String(Clave.getPassword());

            Usuario usuarioAutenticado = usuarioService.autenticar(nombreUsuario, claveIngresada);
            if (usuarioAutenticado != null) {
                Estilos.personalizarJOptionPane();
                JOptionPane.showMessageDialog(principal, "Bienvenido, " + usuarioAutenticado.getUsuario());

                if (usuarioAutenticado.getRol().equalsIgnoreCase("administrador")) {
                    mostrarPanelAdministrador(usuarioAutenticado);
                } else if (usuarioAutenticado.getRol().equalsIgnoreCase("cajero")) {
                    mostrarPanelCajero(usuarioAutenticado);
                }

                dispose();
            } else {
                Estilos.personalizarJOptionPane();
                JOptionPane.showMessageDialog(principal, "Credenciales incorrectas.");
            }
        });

        salirButton.addActionListener(e -> System.exit(0));
    }

    private void mostrarPanelAdministrador(Usuario usuario) {
        new FormularioCRUD(usuario).setVisible(true);
    }

    private void mostrarPanelCajero(Usuario usuario) {
        new FormularioCRUTCajero(usuario).setVisible(true);
    }

    public JPanel getPanel() {
        return principal;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}



