package Formularios;

import Modelos.Usuario;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase FormularioCRUD representa el panel principal para el administrador del sistema.
 * Desde este formulario se pueden gestionar peliculas, funciones, salas, usuarios
 * y ver las funciones programadas. Tambien permite cerrar sesion o salir del sistema.
 */
public class FormularioCRUD extends JFrame {

    private JPanel panel;
    private JLabel nombreUsuario;
    private JButton gestionarPeliculasButton;
    private JButton programarFuncionesButton;
    private JButton gestionarSalasButton;
    private JButton gestionarUsuariosButton;
    private JButton salirButton;
    private JButton buscarFuncionesButton;

    private Usuario usuario;

    /**
     * Constructor que inicializa la interfaz para el rol administrador.
     * Aplica los estilos personalizados y configura los eventos de los botones.
     * @param usuario Usuario autenticado con rol administrador
     */
    public FormularioCRUD(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Panel Administrador");
        setContentPane(panel);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Aplicar estilos visuales
        Estilos.estiloPanel(panel);
        Estilos.estiloBoton(salirButton);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panel, Color.WHITE, new Font("Arial", Font.BOLD, 14));

        // Mostrar nombre del usuario
        nombreUsuario.setText("Bienvenido, " + usuario.getUsuario());

        // Evento: Gestionar peliculas
        gestionarPeliculasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormularioAgregarPeliculas(usuario).setVisible(true);
                dispose();
            }
        });

        // Evento: Programar funciones
        programarFuncionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProgramarFunciones(usuario).setVisible(true);
                dispose();
            }
        });

        // Evento: Gestionar salas
        gestionarSalasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormularioSalas(usuario).setVisible(true);
                dispose();
            }
        });

        // Evento: Gestionar usuarios
        gestionarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FormularioGestionUsuarios(usuario).setVisible(true);
            }
        });

        // Evento: Ver funciones
        buscarFuncionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VerFuncionesPanel(usuario);
            }
        });

        // Evento: Cerrar sesion o salir
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showOptionDialog(
                        FormularioCRUD.this,
                        "¿Qué deseas hacer?",
                        "Salir",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Cerrar sesión", "Salir"},
                        "Cerrar sesión"
                );

                if (opcion == 0) {
                    new Login().setVisible(true);
                    dispose();
                } else if (opcion == 1) {
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Devuelve el panel principal del formulario.
     * @return panel principal
     */
    public JPanel getPanel() {
        return panel;
    }
}

