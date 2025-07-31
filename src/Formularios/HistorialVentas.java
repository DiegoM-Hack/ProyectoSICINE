package Formularios;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import Modelos.Usuario;
import Modelos.Venta;
import Servicios.VentaService;
import Utilidades.Estilos;

/**
 * Clase HistorialVentas representa una ventana que permite visualizar
 * el historial de ventas realizadas por el cajero actualmente autenticado.
 * Presenta los datos en una tabla y permite regresar al menú de cajero.
 */
public class HistorialVentas extends JFrame {

    private JTable tablaHistorial;
    private JButton botonCerrar;
    private JPanel principal;
    private JScrollPane contenedorTabla;
    private VentaService ventaService = new VentaService();
    private Usuario usuario;

    /**
     * Constructor que inicializa la ventana de historial de ventas.
     * Aplica los estilos visuales, carga los datos del usuario y configura eventos.
     *
     * @param usuario Usuario autenticado (se usa para obtener su historial de ventas)
     */
    public HistorialVentas(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Historial de Ventas");
        setContentPane(principal);
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Aplicar estilos visuales personalizados
        Estilos.aplicarEstiloVentana(this);
        Estilos.estiloPanel(principal);
        Estilos.estiloTabla(tablaHistorial);
        Estilos.estiloBoton(botonCerrar);

        // Cargar ventas en la tabla
        cargarHistorialVentas(usuario.getUsuario());

        setVisible(true);

        // Acción del botón cerrar: regresar al panel del cajero
        botonCerrar.addActionListener(e -> {
            dispose();
            new FormularioCRUTCajero(usuario).setVisible(true);
        });
    }

    /**
     * Carga el historial de ventas del usuario en la tabla.
     *
     * @param nombreUsuario Nombre del usuario cajero
     */
    private void cargarHistorialVentas(String nombreUsuario) {
        List<Venta> ventas = ventaService.obtenerVentasPorUsuario(nombreUsuario);

        String[] columnas = {"Película", "Sala", "Entradas", "Total", "Fecha Venta"};
        String[][] datos = new String[ventas.size()][5];
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (int i = 0; i < ventas.size(); i++) {
            Venta v = ventas.get(i);
            datos[i][0] = v.getPelicula() != null ? v.getPelicula() : "No registrada";
            datos[i][1] = v.getSala() != null ? v.getSala() : "No registrada";
            datos[i][2] = String.valueOf(v.getCantidadEntradas());
            datos[i][3] = String.format("%.2f", v.getTotal());
            datos[i][4] = v.getFechaVenta() != null ? formato.format(v.getFechaVenta()) : "No registrada";
        }

        tablaHistorial.setModel(new DefaultTableModel(datos, columnas));
        Estilos.estiloTabla(tablaHistorial); // Aplicar estilo después del setModel
    }

    /**
     * Devuelve el panel principal (útil para integraciones o pruebas).
     *
     * @return panel principal del formulario
     */
    public JPanel getPanel() {
        return principal;
    }
}




