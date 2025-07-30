package Formularios;
import Utilidades.Utilidades;
import Modelos.Pelicula;
import Modelos.Usuario;
import Servicios.PeliculaService;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;

public class FormularioAgregarPeliculas extends JFrame {

    private Usuario usuario;
    private JPanel principal;
    private JTextField titulo;
    private JTextField genero;
    private JTextField duracion;
    private JTextField clasificacion;
    private JTextField sipnopsis;
    private JTextField año;
    private JTextField directort;
    private JButton guardarButton;
    private JButton cancelarButton;

    public FormularioAgregarPeliculas(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Agregar Película");



        // Aplicar estilos
        Estilos.estiloPanel(principal);
        Estilos.estiloBoton(guardarButton);
        Estilos.estiloBoton(cancelarButton);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(principal, Color.WHITE, new Font("Arial", Font.BOLD, 14));
        Estilos.estiloCampoTexto(titulo);
        Estilos.estiloCampoTexto(genero);
        Estilos.estiloCampoTexto(duracion);
        Estilos.estiloCampoTexto(clasificacion);
        Estilos.estiloCampoTexto(sipnopsis);
        Estilos.estiloCampoTexto(año);
        Estilos.estiloCampoTexto(directort);


        this.usuario = usuario;

        setTitle("Agregar Película");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(principal);
        setVisible(true);



        // Acción guardar
        guardarButton.addActionListener(e -> guardarPelicula());

        // Acción cancelar
        cancelarButton.addActionListener(e -> {
            dispose();
            new FormularioCRUD(usuario);
        });

        principal.setPreferredSize(new Dimension(700, 350)); // o el tamaño que desees
        setContentPane(principal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack(); // ajusta la ventana al tamaño preferido del contenido
        setLocationRelativeTo(null); // centrar
        setVisible(true);
    }

    private void guardarPelicula() {
        try {
            String t = titulo.getText();
            String g = genero.getText();
            int d = Integer.parseInt(duracion.getText());
            String c = clasificacion.getText();
            String s = sipnopsis.getText();
            String dir = directort.getText();
            int an = Integer.parseInt(año.getText());

            Pelicula peli = new Pelicula(t, g, d, c, s, dir, an);
            PeliculaService servicio = new PeliculaService();
            servicio.insertarPelicula(peli);

            Estilos.personalizarJOptionPane(); // aplicar estilo antes del mensaje
            JOptionPane.showMessageDialog(this, "Película guardada con éxito.");
            limpiarFormulario();
            dispose();
            new FormularioCRUD(usuario);
    }catch (NumberFormatException ex) {
            Estilos.personalizarJOptionPane();
            JOptionPane.showMessageDialog(this, "Error: duración y año deben ser numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            Estilos.personalizarJOptionPane();
            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar la película.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limpiarFormulario() {
        titulo.setText("");
        genero.setText("");
        duracion.setText("");
        clasificacion.setText("");
        sipnopsis.setText("");
        directort.setText("");
        año.setText("");
    }

    public JPanel getPanel() {
        return principal;
    }

}