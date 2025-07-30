package Formularios;

import Modelos.Funcion;
import Modelos.Pelicula;
import Modelos.Sala;
import Modelos.Usuario;
import Servicios.FuncionService;
import Servicios.PeliculaService;
import Servicios.SalaService;
import Utilidades.Estilos;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ProgramarFunciones extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<String> comboPeliculas;
    private JComboBox<String> comboSalas;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JSpinner spinnerHora;
    private JDateChooser selectorFecha;

    private PeliculaService peliculaService = new PeliculaService();
    private FuncionService funcionService = new FuncionService();
    private SalaService salaService = new SalaService(); // NUEVO: para obtener salas desde MongoDB
    private Usuario usuario;

    public ProgramarFunciones(Usuario usuario) {
        this.usuario = usuario;

        cargarPeliculas();
        cargarSalas();

        setTitle("Gestión de Funciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(500, 250);
        setLocationRelativeTo(null);
        setVisible(true);

        // Aplicar estilos
        Estilos.estiloPanel(panelPrincipal);
        Estilos.estiloBoton(guardarButton);
        Estilos.estiloBoton(eliminarButton);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panelPrincipal, Color.WHITE, new Font("Arial", Font.BOLD, 14));
        Estilos.estilizarComboBox(comboPeliculas);
        Estilos.estilizarComboBox(comboSalas);
        Estilos.estilizarSpinner(spinnerHora);


        // Configurar JDateChooser
        selectorFecha.setDateFormatString("dd/MM/yyyy");
        selectorFecha.setPreferredSize(new Dimension(200, 25));

        // Configurar Spinner de hora
        SpinnerDateModel horaModel = new SpinnerDateModel();
        spinnerHora.setModel(horaModel);
        spinnerHora.setEditor(new JSpinner.DateEditor(spinnerHora, "HH:mm"));

        // Mostrar botón eliminar solo a administradores
        eliminarButton.setVisible(usuario.getRol().equalsIgnoreCase("administrador"));

        // Eventos
        guardarButton.addActionListener(e -> registrarFuncion());
        eliminarButton.addActionListener(e ->{
            dispose();
            new FormularioCRUD(usuario);

        });

    }

    private void registrarFuncion() {
        String pelicula = (String) comboPeliculas.getSelectedItem();
        String sala = (String) comboSalas.getSelectedItem();
        Date fecha = selectorFecha.getDate();
        Date hora = (Date) spinnerHora.getValue();

        if (pelicula == null || sala == null || fecha == null || hora == null) {
            JOptionPane.showMessageDialog(panelPrincipal, "Todos los campos deben estar llenos.","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date fechaHora = combinarFechaYHora(fecha, hora);

        Funcion nuevaFuncion = new Funcion(pelicula, sala, fechaHora);
        if (funcionService.insertarFuncion(nuevaFuncion)) {
            JOptionPane.showMessageDialog(panelPrincipal, "Función registrada correctamente.");

            dispose();
            new FormularioCRUD(usuario).setVisible(true);

        } else {
            Estilos.personalizarJOptionPane();
            JOptionPane.showMessageDialog(panelPrincipal, "Error al registrar función.","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarFuncion() {
        String pelicula = (String) comboPeliculas.getSelectedItem();
        String sala = (String) comboSalas.getSelectedItem();
        Date fecha = selectorFecha.getDate();
        Date hora = (Date) spinnerHora.getValue();

        if (pelicula == null || sala == null || fecha == null || hora == null) {
            Estilos.personalizarJOptionPane();
            JOptionPane.showMessageDialog(panelPrincipal, "Todos los campos deben estar llenos.","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date fechaHora = combinarFechaYHora(fecha, hora);

        Funcion funcion = new Funcion(pelicula, sala, fechaHora);
        if (funcionService.eliminarFuncion(funcion)) {
            JOptionPane.showMessageDialog(panelPrincipal, "Función eliminada correctamente.");
            SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
            dispose();
            new FormularioCRUD(usuario).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(panelPrincipal, "Error al eliminar función.");
        }
    }

    private Date combinarFechaYHora(Date fecha, Date hora) {
        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);

        Calendar calHora = Calendar.getInstance();
        calHora.setTime(hora);

        calFecha.set(Calendar.HOUR_OF_DAY, calHora.get(Calendar.HOUR_OF_DAY));
        calFecha.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
        calFecha.set(Calendar.SECOND, 0);
        calFecha.set(Calendar.MILLISECOND, 0);

        return calFecha.getTime();
    }

    private void cargarPeliculas() {
        comboPeliculas.removeAllItems();
        List<Pelicula> peliculas = peliculaService.obtenerTodasLasPeliculas();
        if (peliculas.isEmpty()) {
            JOptionPane.showMessageDialog(panelPrincipal, "No hay películas registradas.","Message", JOptionPane.QUESTION_MESSAGE);
        } else {
            for (Pelicula peli : peliculas) {
                comboPeliculas.addItem(peli.getTitulo());
            }
        }
    }

    private void cargarSalas() {
        comboSalas.removeAllItems();
        List<Sala> salas = salaService.obtenerTodasLasSalas();
        for (Sala sala : salas) {
            comboSalas.addItem(sala.getNombre());
        }
    }

    public JPanel getPanel() {
        return panelPrincipal;
    }

    private void createUIComponents() {
        selectorFecha = new JDateChooser();
    }
}


