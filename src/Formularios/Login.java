package Formularios;

import Modelos.Usuario;
import Servicios.UsuarioService;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;

/**
 * Clase Login que representa la interfaz de inicio de sesion para los usuarios del sistema PoliCine.
 * Permite autenticarse como administrador o cajero.
 * Aplica estilos visuales personalizados desde la clase Estilos.
 */
public class Login extends JFrame {
    /** Panel principal del formulario */
    private JPanel principal;
    private JTextField usuario;
    private JPasswordField Clave;
    private JButton ingresarButton;
    private JButton salirButton;

    /** Servicio que gestiona la autenticacion de usuarios */
    private UsuarioService usuarioService = new UsuarioService();

    /**
     * Constructor que inicializa la interfaz de login y aplica los estilos visuales
     */
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
                JOptionPane.showMessageDialog(principal, "Credenciales incorrectas.","Error", JOptionPane.ERROR_MESSAGE);

            }
        });

        salirButton.addActionListener(e -> System.exit(0));
    }

    /**
     * Muestra el panel de administrador tras autenticarse
     * @param usuario Usuario autenticado con rol administrador
     */
    private void mostrarPanelAdministrador(Usuario usuario) {
        new FormularioCRUD(usuario).setVisible(true);
    }

    /**
     * Muestra el panel de cajero tras autenticarse
     * @param usuario Usuario autenticado con rol cajero
     */
    private void mostrarPanelCajero(Usuario usuario) {
        new FormularioCRUTCajero(usuario).setVisible(true);
    }

    /**
     * Devuelve el panel principal (util en pruebas o integracion)
     * @return panel principal
     */
    public JPanel getPanel() {
        return principal;
    }

    /**
     * Metodo main que lanza la interfaz de login
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}


