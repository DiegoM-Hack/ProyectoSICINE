package Formularios;

import Modelos.*;
import Servicios.*;
import Utilidades.Estilos;
import Utilidades.QRUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static Utilidades.QRUtil.generarImagenBoletoConQR;

public class FormularioVentaEntradas extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<String> comboPeliculas;
    private JComboBox<String> comboFunciones;
    private JTextField campoCantidad;
    private JButton botonVender;
    private Usuario usuario;

    private PeliculaService peliculaService = new PeliculaService();
    private FuncionService funcionService = new FuncionService();
    private SalaService salaService = new SalaService();
    private VentaService ventaService = new VentaService();
    private BoletoService boletoService = new BoletoService();


    public FormularioVentaEntradas(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Vender entradas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(600, 300);
        setLocationRelativeTo(null);

        cargarPeliculas();

        Estilos.estiloPanel(panelPrincipal);
        Estilos.estiloBoton(botonVender);
        Estilos.aplicarEstiloVentana(this);
        Estilos.estilizarLabels(panelPrincipal, Color.WHITE, new Font("Arial", Font.BOLD, 14));
        Estilos.estilizarComboBox(comboFunciones);
        Estilos.estilizarComboBox(comboPeliculas);
        Estilos.estiloCampoTexto(campoCantidad);



        comboPeliculas.addActionListener(e -> cargarFunciones());

        botonVender.addActionListener(e ->
                venderEntradas());

                new FormularioCRUTCajero(usuario).setVisible(true);
    }

    private void cargarPeliculas() {
        comboPeliculas.removeAllItems();
        for (Pelicula p : peliculaService.obtenerTodasLasPeliculas()) {
            comboPeliculas.addItem(p.getTitulo());
        }
    }

    private void cargarFunciones() {
        comboFunciones.removeAllItems();
        String titulo = (String) comboPeliculas.getSelectedItem();
        if (titulo == null) return;

        List<Funcion> funciones = funcionService.obtenerFuncionesPorPelicula(titulo);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Funcion f : funciones) {
            String info = f.getSala() + " - " + formato.format(f.getFechaHora());
            comboFunciones.addItem(info);
        }
    }

    private void venderEntradas() {
        if (!validarSeleccion()) return;

        int cantidad = obtenerCantidadEntradas();
        if (cantidad <= 0) return;

        try {
            String funcionSeleccionada = (String) comboFunciones.getSelectedItem();
            String[] partes = funcionSeleccionada.split(" - ");
            String sala = partes[0];
            String fechaStr = partes[1];

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date fechaHora = formato.parse(fechaStr);

            Sala salaObj = salaService.obtenerSalaPorNombre(sala);
            if (salaObj == null) {
                JOptionPane.showMessageDialog(panelPrincipal, "Sala no encontrada.");
                return;
            }

            double total = calcularTotal(cantidad, salaObj.getTipo());
            Date fechaVenta = new Date();
            String codigoQR = UUID.randomUUID().toString();

            Venta venta = new Venta(
                    (String) comboPeliculas.getSelectedItem(),
                    sala,
                    fechaHora,
                    cantidad,
                    total,
                    fechaVenta,
                    usuario.getUsuario()
            );
            venta.setCodigoQR(codigoQR);

            if (ventaService.registrarVenta(venta)) {
                generarBoleto(venta);
                generarYGuardarQR(venta);
                boletoService.guardarBoleto(venta, codigoQR);
                JOptionPane.showMessageDialog(panelPrincipal, "Venta registrada. Total: $" + total);
                dispose();

            } else {
                JOptionPane.showMessageDialog(panelPrincipal, "Error al registrar la venta.");
            }


        } catch (ParseException e) {
            JOptionPane.showMessageDialog(panelPrincipal, "Error al procesar la fecha de la función.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panelPrincipal, "Ocurrió un error inesperado.");
            e.printStackTrace();
        }
    }

    private String comoTextoQR(Venta venta) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return "Pelicula: " + venta.getPelicula() + "\n" +
                "Sala: " + venta.getSala() + "\n" +
                "Fecha: " + formato.format(venta.getFechaHoraFuncion()) + "\n" +
                "Entradas: " + venta.getCantidadEntradas() + "\n" +
                "Total: $" + venta.getTotal();
    }


    private boolean validarSeleccion() {
        String pelicula = (String) comboPeliculas.getSelectedItem();
        String funcion = (String) comboFunciones.getSelectedItem();

        if (pelicula == null || funcion == null) {
            JOptionPane.showMessageDialog(panelPrincipal, "Selecciona una película y una función.");
            return false;
        }
        return true;
    }

    private int obtenerCantidadEntradas() {
        try {
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            if (cantidad <= 0) throw new NumberFormatException();
            return cantidad;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panelPrincipal, "Cantidad inválida. Debe ser un número mayor que 0.");
            return -1;
        }
    }

    private double calcularTotal(int cantidad, String tipoSala) {
        return cantidad * obtenerPrecioPorTipoSala(tipoSala);
    }

    private void generarYGuardarQR(Venta venta) {
        try {
            String contenidoQR = venta.getCodigoQR();
            BufferedImage qr = QRUtil.generarQR(comoTextoQR(venta), 200, 200);

            // Generar imagen completa
            BufferedImage boletoConQR = generarImagenBoletoConQR(venta, qr);

            // Guardar como imagen PNG
            String nombreArchivo = "C:\\Users\\User\\Desktop\\DriversMongo\\Boleto" + System.currentTimeMillis() + ".png";
            ImageIO.write(boletoConQR, "png", new File(nombreArchivo));
            System.out.println("Boleto guardado en: " + nombreArchivo);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panelPrincipal, "Error al generar imagen del boleto.");
        }
    }

    private double obtenerPrecioPorTipoSala(String tipo) {
        switch (tipo.toUpperCase()) {
            case "2D": return 4.0;
            case "3D": return 6.0;
            case "VIP": return 8.0;
            default: return 5.0;
        }
    }

    private void generarBoleto(Venta venta) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String contenidoQR = "Película: " + venta.getPelicula() + "\n" +
                "Sala: " + venta.getSala() + "\n" +
                "Fecha y Hora: " + formato.format(venta.getFechaHoraFuncion()) + "\n" +
                "Entradas: " + venta.getCantidadEntradas() + "\n" +
                "Total: $" + venta.getTotal() + "\n" +
                "Fecha Venta: " + formato.format(venta.getFechaVenta());

        // Generar imagen QR
        BufferedImage imagenQR = QRUtil.generarCodigoQR(contenidoQR, 150, 150); // Asegúrate de tener esta clase

        // Crear mensaje de texto
        String boleto = "<html><body>" +
                "<h3>BOLETO</h3>" +
                "<p><strong>Película:</strong> " + venta.getPelicula() + "<br>" +
                "<strong>Sala:</strong> " + venta.getSala() + "<br>" +
                "<strong>Fecha y Hora:</strong> " + formato.format(venta.getFechaHoraFuncion()) + "<br>" +
                "<strong>Entradas:</strong> " + venta.getCantidadEntradas() + "<br>" +
                "<strong>Total:</strong> $" + venta.getTotal() + "<br>" +
                "<strong>Fecha Venta:</strong> " + formato.format(venta.getFechaVenta()) + "</p>" +
                "</body></html>";

        // Mostrar mensaje con QR en JOptionPane
        JLabel etiqueta = new JLabel(boleto);
        JLabel imagen = new JLabel(new ImageIcon(imagenQR));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(etiqueta, BorderLayout.CENTER);
        panel.add(imagen, BorderLayout.EAST); // o SOUTH si quieres debajo

        JOptionPane.showMessageDialog(panelPrincipal, panel, "Boleto generado", JOptionPane.INFORMATION_MESSAGE);
    }


    public JPanel getPanel() {
        return panelPrincipal;
    }
}

