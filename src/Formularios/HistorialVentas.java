package Formularios;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import Modelos.Usuario;
import Modelos.Venta;
import Servicios.VentaService;
import Utilidades.Estilos;

public class HistorialVentas extends JFrame {
    private JTable tablaHistorial;
    private JButton botonCerrar;
    private JPanel principal;
    private JScrollPane contenedorTabla;
    private VentaService ventaService = new VentaService();
    private Usuario usuario;

    public HistorialVentas(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Historial de Ventas");
        setContentPane(principal);
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Aplicar estilos
        Estilos.aplicarEstiloVentana(this);
        Estilos.estiloPanel(principal);
        Estilos.estiloTabla(tablaHistorial);
        Estilos.estiloBoton(botonCerrar);
        Estilos.estiloTabla(tablaHistorial);

        // Cargar datos en la tabla
        cargarHistorialVentas(usuario.getUsuario());

        setVisible(true);

        botonCerrar.addActionListener(e -> {
            dispose();
            new FormularioCRUTCajero(usuario);
        });
    }

    private void cargarHistorialVentas(String nombreUsuario) {
        List<Venta> ventas = ventaService.obtenerVentasPorUsuario(nombreUsuario);

        String[] columnas = {"Película", "Sala", "Entradas", "Total", "Fecha Venta"};
        String[][] datos = new String[ventas.size()][6];
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (int i = 0; i < ventas.size(); i++) {
            Venta v = ventas.get(i);
            datos[i][0] = v.getPelicula() != null ? v.getPelicula() : "No registrada";
            datos[i][1] = v.getSala() != null ? v.getSala() : "No registrada";
            //datos[i][2] = v.getFechaHoraFuncion() != null ? formato.format(v.getFechaHoraFuncion()) : "No registrada";
            datos[i][2] = String.valueOf(v.getCantidadEntradas());
            datos[i][3] = String.format("%.2f", v.getTotal());
            datos[i][4] = v.getFechaVenta() != null ? formato.format(v.getFechaVenta()) : "No registrada";
        }

        tablaHistorial.setModel(new DefaultTableModel(datos, columnas));
        Estilos.estiloTabla(tablaHistorial); // aplicar estilo después del setModel
    }

    public JPanel getPanel() {
        return principal;
    }
}



