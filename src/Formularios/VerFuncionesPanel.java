package Formularios;

import Modelos.Funcion;
import Modelos.Usuario;
import Servicios.FuncionService;
import Utilidades.Estilos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VerFuncionesPanel extends JFrame {
    private JPanel panelPrincipal;
    private JTable tablaFunciones;
    private JButton botonEliminar;
    private JButton botonRegresar;
    private DefaultTableModel modeloTabla;

    private FuncionService funcionService = new FuncionService();
    private Usuario usuario;

    public VerFuncionesPanel(Usuario usuario) {
        this.usuario = usuario;


        setTitle("Buscar Funciones");
        setContentPane(panelPrincipal);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        // Inicializar modelo de tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Película", "Sala", "Fecha y Hora"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaFunciones.setModel(modeloTabla);


        // Ocultar botón si no es admin
        if (!usuario.getRol().equalsIgnoreCase("administrador")) {
            botonEliminar.setVisible(false);
        }

        botonEliminar.addActionListener(e -> eliminarFuncionSeleccionada());

        cargarFunciones();
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (usuario.getRol().equalsIgnoreCase("administrador")) {
                    new FormularioCRUD(usuario).setVisible(true);
                } else {
                    new FormularioCRUTCajero(usuario).setVisible(true);
                }

            }
        });

        Estilos.estiloPanel(panelPrincipal);
        Estilos.estiloBoton(botonEliminar);
        Estilos.estiloBoton(botonRegresar);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panelPrincipal, Color.WHITE, new Font("Arial", Font.BOLD, 14));
        Estilos.estilizarTabla(tablaFunciones);



    }

    private void cargarFunciones() {
        modeloTabla.setRowCount(0); // limpiar tabla
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        List<Funcion> funciones = funcionService.obtenerTodasLasFunciones();
        for (Funcion funcion : funciones) {
            modeloTabla.addRow(new Object[]{
                    funcion.getTituloPelicula(),
                    funcion.getSala(),
                    formato.format(funcion.getFechaHora())
            });
        }
    }

    private void eliminarFuncionSeleccionada() {
        int filaSeleccionada = tablaFunciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una función para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar esta función?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String titulo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String sala = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            String fechaHoraStr = (String) modeloTabla.getValueAt(filaSeleccionada, 2);

            try {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date fechaHora = formato.parse(fechaHoraStr);
                Funcion funcion = new Funcion(titulo, sala, fechaHora);

                if (funcionService.eliminarFuncion(funcion)) {
                    JOptionPane.showMessageDialog(this, "Función eliminada correctamente.");
                    cargarFunciones();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la función.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al procesar la fecha.");
            }
        }
    }

    public JPanel getPanel() {
        return panelPrincipal;
    }
}
