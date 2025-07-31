package Formularios;
import Modelos.Pelicula;
import Modelos.Usuario;
import Servicios.PeliculaService;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa el formulario para agregar peliculas al sistema PoliCine.
 * Permite ingresar los datos de una pelicula y guardarla en la base de datos.
 */
public class FormularioAgregarPeliculas extends JFrame {
    private Usuario usuario;
    private JPanel principal;
    private JTextField titulo;
    private JTextField genero;
    private JTextField duracion;
    private JTextField clasificacion;
    private JTextField sipnopsis;
    private JTextField ano;
    private JTextField directort;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton buscarButton;

    /**
     * Constructor del formulario que recibe el usuario autenticado y configura la interfaz.
     * @param usuario Usuario que ingreso al sistema
     */
    public FormularioAgregarPeliculas(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Agregar Pelicula");

        // Aplicar estilos visuales
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
        Estilos.estiloCampoTexto(ano);
        Estilos.estiloCampoTexto(directort);

        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(principal);
        setVisible(true);

        // Accion guardar
        guardarButton.addActionListener(e -> guardarPelicula());

        // Accion cancelar
        cancelarButton.addActionListener(e -> {
            dispose();
            new FormularioCRUD(usuario);
        });

        // Accion buscar
        buscarButton.addActionListener(e -> {
            dispose();
            new BuscarPelicula(usuario);
        });
    }

    /**
     * Metodo que obtiene los datos del formulario, crea un objeto Pelicula
     * y lo guarda en la base de datos.
     */
    private void guardarPelicula() {
        try {
            String t = titulo.getText();
            String g = genero.getText();
            int d = Integer.parseInt(duracion.getText());
            String c = clasificacion.getText();
            String s = sipnopsis.getText();
            String dir = directort.getText();
            int an = Integer.parseInt(ano.getText());

            Pelicula peli = new Pelicula(t, g, d, c, s, dir, an);
            PeliculaService servicio = new PeliculaService();
            servicio.insertarPelicula(peli);

            Estilos.personalizarJOptionPane();
            JOptionPane.showMessageDialog(this, "Pelicula guardada con exito.");
            limpiarFormulario();
            dispose();
            new FormularioCRUD(usuario);
        } catch (NumberFormatException ex) {
            Estilos.personalizarJOptionPane();
            JOptionPane.showMessageDialog(this, "Error: duracion y anio deben ser numericos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            Estilos.personalizarJOptionPane();
            JOptionPane.showMessageDialog(this, "Ocurrio un error al guardar la pelicula.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarFormulario() {
        titulo.setText("");
        genero.setText("");
        duracion.setText("");
        clasificacion.setText("");
        sipnopsis.setText("");
        directort.setText("");
        ano.setText("");
    }

    /**
     * Retorna el panel principal para integracion externa
     * @return panel principal del formulario
     */
    public JPanel getPanel() {
        return principal;
    }
}