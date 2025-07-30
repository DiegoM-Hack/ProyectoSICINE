package Formularios;

import Modelos.Usuario;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public FormularioCRUD(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Panel Administrador");
        setContentPane(panel);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        Estilos.estiloPanel(panel);
        Estilos.estiloBoton(salirButton);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panel, Color.WHITE, new Font("Arial", Font.BOLD, 14));


        nombreUsuario.setText("Bienvenido, " + usuario.getUsuario());

        gestionarPeliculasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormularioAgregarPeliculas(usuario).setVisible(true);
                dispose();
            }
        });

        programarFuncionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProgramarFunciones(usuario).setVisible(true);
                dispose();
            }
        });

        gestionarSalasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormularioSalas(usuario).setVisible(true);
                dispose();
            }
        });

        gestionarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FormularioGestionUsuarios(usuario).setVisible(true);

            }
        });

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


        buscarFuncionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra esta ventana
                new VerFuncionesPanel(usuario);
            }
        });
    }
    public JPanel getPanel() {
        return panel;
    }

}
