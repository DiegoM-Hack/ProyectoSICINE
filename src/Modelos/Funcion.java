package Modelos;

import java.util.Date;


/**
 * Representa una funcion de cine programada.
 */
public class Funcion {
    private String tituloPelicula;
    private String sala;
    private Date fechaHora;

    /**
     * Constructor que inicializa una funcion.
     *
     * @param tituloPelicula Titulo de la pelicula.
     * @param sala Nombre de la sala donde se proyectara.
     * @param fechaHora Fecha y hora de la funcion.
     */
    public Funcion(String tituloPelicula, String sala, Date fechaHora) {
        this.tituloPelicula = tituloPelicula;
        this.sala = sala;
        this.fechaHora = fechaHora;
    }

    public String getTituloPelicula() { return tituloPelicula; }
    public void setTituloPelicula(String tituloPelicula) { this.tituloPelicula = tituloPelicula; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public Date getFechaHora() { return fechaHora; }
    public void setFechaHora(Date fechaHora) { this.fechaHora = fechaHora; }
}
