package Formularios;

import Modelos.Pelicula;
import Modelos.Usuario;
import Servicios.PeliculaService;
import Utilidades.Estilos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;


/**
 * Clase BuscarPelicula permite buscar una pelicula por su titulo,
 * mostrar los resultados en una tabla, visualizar su sinopsis y eliminarla si se desea.
 */
public class BuscarPelicula extends JPanel {

    private JPanel principal;
    private JTextField nombrePelicula;
    private JButton buscarButton;
    private JTable informacionPelicula;
    private JButton eliminarButton;
    private JButton cancelarButton;
    private Usuario usuario;

    /** Servicio para gestionar las operaciones de pelicula */
    private PeliculaService servicio = new PeliculaService();

    /**
     * Constructor que inicializa la interfaz de busqueda de peliculas
     * @param usuario Usuario que accede a la interfaz
     */
    public BuscarPelicula(Usuario usuario) {
        this.usuario = usuario;

        JFrame frame = new JFrame("Buscar Pelicula");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(principal);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Aplicar estilos visuales
        Estilos.estiloPanel(principal);
        Estilos.aplicarEstiloVentana(frame);
        Estilos.estilizarLabels(principal, Color.WHITE, new Font("Arial", Font.BOLD, 14));
        Estilos.estilizarTabla(informacionPelicula);

        /**
         * Doble clic en una fila muestra la sinopsis de la pelicula seleccionada
         */
        informacionPelicula.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && informacionPelicula.getSelectedRow() != -1) {
                    int fila = informacionPelicula.getSelectedRow();
                    String titulo = (String) informacionPelicula.getValueAt(fila, 0);

                    List<Pelicula> resultados = servicio.buscarPeliculasPorTitulo(titulo);
                    if (!resultados.isEmpty()) {
                        Pelicula seleccionada = resultados.get(0);
                        JOptionPane.showMessageDialog(
                                null,
                                "Sinopsis:\n" + seleccionada.getSinopsis(),
                                "Sinopsis de " + seleccionada.getTitulo(),
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró la sinopsis para esa película.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        /**
         * Boton para eliminar la pelicula seleccionada de la tabla
         */
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPelicula();
                frame.dispose();
                new FormularioCRUD(usuario);
            }
        });

        /**
         * Boton para buscar una pelicula segun el titulo ingresado
         */
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tituloBuscado = nombrePelicula.getText().trim();
                if (tituloBuscado.isEmpty()) {
                    Estilos.personalizarJOptionPane();
                    JOptionPane.showMessageDialog(principal, "Ingrese un título para buscar.");
                    return;
                }

                List<Pelicula> resultados = servicio.buscarPeliculasPorTitulo(tituloBuscado);
                if (resultados.isEmpty()) {
                    JOptionPane.showMessageDialog(principal, "Película no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    mostrarResultados(resultados);
                }
            }
        });

        /**
         * Boton para cancelar la busqueda y volver al panel CRUD
         */
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new FormularioCRUD(usuario);
            }
        });
    }

    /**
     * Elimina la pelicula seleccionada de la tabla y la base de datos.
     * Muestra mensajes de confirmacion y resultado.
     */
    private void eliminarPelicula() {
        int filaSeleccionada = informacionPelicula.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(principal, "Seleccione una película para eliminar.");
            return;
        }

        String titulo = (String) informacionPelicula.getValueAt(filaSeleccionada, 0);

        int confirmacion = JOptionPane.showConfirmDialog(
                principal,
                "¿Está seguro que desea eliminar la película '" + titulo + "'?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = servicio.eliminarPeliculaPorTitulo(titulo);
            if (eliminado) {
                JOptionPane.showMessageDialog(principal, "Película eliminada exitosamente.");
                ((DefaultTableModel) informacionPelicula.getModel()).removeRow(filaSeleccionada);
            } else {
                Estilos.personalizarJOptionPane();
                JOptionPane.showMessageDialog(principal, "No se pudo eliminar la película.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Muestra en la tabla todas las peliculas encontradas segun el titulo buscado.
     * @param peliculas Lista de objetos Pelicula encontrados
     */
    private void mostrarResultados(List<Pelicula> peliculas) {
        String[] columnas = {"Título", "Género", "Duración", "Clasificación", "Director", "Año"};

        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La tabla no es editable
            }
        };

        for (Pelicula peli : peliculas) {
            Object[] fila = {
                    peli.getTitulo(),
                    peli.getGenero(),
                    peli.getDuracion(),
                    peli.getClasificacion(),
                    peli.getDirector(),
                    peli.getAnio()
            };
            model.addRow(fila);
        }

        informacionPelicula.setModel(model);
    }

    /**
     * Devuelve el panel principal para integracion externa.
     * @return panel principal
     */
    public JPanel getPanel() {
        return principal;
    }
}


