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

/**
 * Clase ProgramarFunciones permite al usuario (administrador o cajero) programar o eliminar funciones de películas.
 * Se conectan los combos a la base de datos para mostrar películas y salas disponibles.
 * Aplica estilos personalizados definidos en la clase Estilos.
 */
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
    private SalaService salaService = new SalaService();
    private Usuario usuario;

    /**
     * Constructor que inicializa el formulario para programar funciones.
     * @param usuario El usuario autenticado que utiliza el sistema.
     */
    public ProgramarFunciones(Usuario usuario) {
        this.usuario = usuario;

        cargarPeliculas();
        cargarSalas();

        setTitle("Gestion de Funciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(500, 250);
        setLocationRelativeTo(null);
        setVisible(true);

        // Aplicar estilos visuales
        Estilos.estiloPanel(panelPrincipal);
        Estilos.estiloBoton(guardarButton);
        Estilos.estiloBoton(eliminarButton);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panelPrincipal, Color.WHITE, new Font("Arial", Font.BOLD, 14));
        Estilos.estilizarComboBox(comboPeliculas);
        Estilos.estilizarComboBox(comboSalas);
        Estilos.estilizarSpinner(spinnerHora);

        selectorFecha.setDateFormatString("dd/MM/yyyy");
        selectorFecha.setPreferredSize(new Dimension(200, 25));

        SpinnerDateModel horaModel = new SpinnerDateModel();
        spinnerHora.setModel(horaModel);
        spinnerHora.setEditor(new JSpinner.DateEditor(spinnerHora, "HH:mm"));

        eliminarButton.setVisible(usuario.getRol().equalsIgnoreCase("administrador"));

        guardarButton.addActionListener(e -> registrarFuncion());
        eliminarButton.addActionListener(e -> {
            dispose();
            new FormularioCRUD(usuario);
        });
    }

    /**
     * Registra una nueva funcion con los datos seleccionados.
     * Valida que todos los campos esten llenos antes de guardar en la base de datos.
     */
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
            JOptionPane.showMessageDialog(panelPrincipal, "Funcion registrada correctamente.");
            dispose();
            new FormularioCRUD(usuario).setVisible(true);
        } else {
            Estilos.personalizarJOptionPane();
            JOptionPane.showMessageDialog(panelPrincipal, "Error al registrar funcion.","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina la funcion seleccionada (si existe) en base a pelicula, sala y fecha/hora.
     */
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
            JOptionPane.showMessageDialog(panelPrincipal, "Funcion eliminada correctamente.");
            dispose();
            new FormularioCRUD(usuario).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(panelPrincipal, "Error al eliminar funcion.");
        }
    }

    /**
     * Combina una fecha y una hora en un solo objeto Date.
     * @param fecha La fecha seleccionada
     * @param hora La hora seleccionada
     * @return Date combinada con fecha y hora
     */
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

    /**
     * Carga todas las peliculas desde la base de datos y las agrega al combo.
     */
    private void cargarPeliculas() {
        comboPeliculas.removeAllItems();
        List<Pelicula> peliculas = peliculaService.obtenerTodasLasPeliculas();
        if (peliculas.isEmpty()) {
            JOptionPane.showMessageDialog(panelPrincipal, "No hay peliculas registradas.","Message", JOptionPane.QUESTION_MESSAGE);
        } else {
            for (Pelicula peli : peliculas) {
                comboPeliculas.addItem(peli.getTitulo());
            }
        }
    }

    /**
     * Carga todas las salas desde la base de datos y las agrega al combo.
     */
    private void cargarSalas() {
        comboSalas.removeAllItems();
        List<Sala> salas = salaService.obtenerTodasLasSalas();
        for (Sala sala : salas) {
            comboSalas.addItem(sala.getNombre());
        }
    }

    /**
     * Devuelve el panel principal (util para integracion con otros paneles)
     * @return panel principal
     */
    public JPanel getPanel() {
        return panelPrincipal;
    }

    /**
     * Metodo utilizado por el diseñador de interfaces para crear componentes personalizados
     */
    private void createUIComponents() {
        selectorFecha = new JDateChooser();
    }
}


