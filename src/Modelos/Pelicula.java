package Modelos;

/**
 * Representa una pelicula en el sistema.
 */
public class Pelicula {
    private String titulo;
    private String genero;
    private int duracion;
    private String clasificacion;
    private String sinopsis;
    private String director;
    private int anio;

    /**
     * Constructor para crear una nueva pelicula.
     *
     * @param titulo Titulo de la película.
     * @param genero Género de la película.
     * @param duracion Duracion en minutos.
     * @param clasificacion Clasificacion por edades.
     * @param sinopsis Resumen de la trama.
     * @param director Nombre del director.
     * @param anio Año de estreno.
     */
    public Pelicula(String titulo, String genero, int duracion, String clasificacion,
                    String sinopsis, String director, int anio) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.sinopsis = sinopsis;
        this.director = director;
        this.anio = anio;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }

    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
}
