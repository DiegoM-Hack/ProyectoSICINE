package Modelos;

/**
 * Representa una sala de cine.
 */
public class Sala {
    private String nombre;
    private int numeroAsientos;
    private String tipo; // Ejemplo: "2D", "3D", "VIP"

    /**
     * Constructor de la sala.
     *
     * @param nombre Nombre de la sala.
     * @param numeroAsientos Cantidad total de asientos.
     * @param tipo Tipo de sala (2D, 3D, VIP).
     */
    public Sala(String nombre, int numeroAsientos, String tipo) {
        this.nombre = nombre;
        this.numeroAsientos = numeroAsientos;
        this.tipo = tipo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getNumeroAsientos() { return numeroAsientos; }
    public void setNumeroAsientos(int numeroAsientos) { this.numeroAsientos = numeroAsientos; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}

