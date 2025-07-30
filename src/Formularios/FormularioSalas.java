package Formularios;

import Modelos.Sala;
import Modelos.Usuario;
import Servicios.SalaService;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioSalas extends JFrame {
    private JPanel panel;
    private JTextField campoNombre;
    private JTextField campoAsientos;
    private JComboBox<String> comboTipo;
    private JButton guardarButton;
    private JButton regresarButton;
    private Usuario usuario;
    private SalaService salaService = new SalaService();

    public FormularioSalas(Usuario usuario) {
        this.usuario = usuario;

        setLayout(null);
        setTitle("Gestion de Salas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        comboTipo.addItem("2D");
        comboTipo.addItem("3D");
        comboTipo.addItem("VIP");

        Estilos.estiloPanel(panel);
        Estilos.estiloBoton(guardarButton);
        Estilos.estiloBoton(regresarButton);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panel, Color.WHITE, new Font("Arial", Font.BOLD, 14));
        Estilos.estilizarComboBox(comboTipo);
        Estilos.estiloCampoTexto(campoNombre);
        Estilos.estiloCampoTexto(campoAsientos);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText();
                int asientos = Integer.parseInt(campoAsientos.getText());
                String tipo = (String) comboTipo.getSelectedItem();


                Sala nuevaSala = new Sala(nombre, asientos, tipo);
                if (salaService.agregarSala(nuevaSala)) {
                    JOptionPane.showMessageDialog(panel, "Sala guardada correctamente.");

                    SwingUtilities.getWindowAncestor(panel).dispose();

                    // Volver al formulario CRUD
                    new FormularioCRUD(null);

                } else {
                    JOptionPane.showMessageDialog(panel, "Error al guardar la sala.");
                }
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FormularioCRUD(usuario).setVisible(true);
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
