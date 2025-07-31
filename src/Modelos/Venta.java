package Modelos;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Representa una venta de entradas en el sistema.
 */
public class Venta {
    private String pelicula;
    private String sala;
    private Date fechaHoraFuncion;
    private int cantidadEntradas;
    private double total;
    private Date fechaVenta;
    private String usuario;
    private String codigoQR;

    /**
     * Constructor para venta sin usuario.
     */
    public Venta(String pelicula, String sala, Date fechaHoraFuncion, int cantidadEntradas, double total, Date fechaVenta) {
        this(pelicula, sala, fechaHoraFuncion, cantidadEntradas, total, fechaVenta, null);
    }

    /**
     * Constructor para venta con usuario.
     */
    public Venta(String pelicula, String sala, Date fechaHoraFuncion, int cantidadEntradas, double total, Date fechaVenta, String usuario) {
        this.pelicula = pelicula;
        this.sala = sala;
        this.fechaHoraFuncion = fechaHoraFuncion;
        this.cantidadEntradas = cantidadEntradas;
        this.total = total;
        this.fechaVenta = fechaVenta;
        this.usuario = usuario;
        this.codigoQR = generarContenidoQR();
    }

    /**
     * Genera el contenido del codigo QR basado en los datos de la venta.
     *
     * @return String con la informacion serializada.
     */
    public String generarContenidoQR() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return "pelicula=" + pelicula +
                ";sala=" + sala +
                ";fecha=" + formato.format(fechaHoraFuncion) +
                ";entradas=" + cantidadEntradas +
                ";total=" + total +
                ";usuario=" + usuario;
    }

    public String getCodigoQR() { return codigoQR; }
    public void setCodigoQR(String codigoQR) { this.codigoQR = codigoQR; }

    public String getUsuario() { return usuario; }
    public String getPelicula() { return pelicula; }
    public String getSala() { return sala; }
    public Date getFechaHoraFuncion() { return fechaHoraFuncion; }
    public int getCantidadEntradas() { return cantidadEntradas; }
    public double getTotal() { return total; }
    public Date getFechaVenta() { return fechaVenta; }
}

