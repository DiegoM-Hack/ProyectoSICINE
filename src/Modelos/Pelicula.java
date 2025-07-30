package Modelos;

public class Pelicula {
    private String titulo;
    private String genero;
    private int duracion;
    private String clasificacion;
    private String sinopsis;
    private String director;
    private int anio;
    //private String rutaImagen;  // NUEVO

    // Constructor actualizado
    public Pelicula(String titulo, String genero, int duracion, String clasificacion,
                    String sinopsis, String director, int anio) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.sinopsis = sinopsis;
        this.director = director;
        this.anio = anio;
        //this.rutaImagen = rutaImagen; // NUEVO
    }

    // Getters y Setters
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

    // NUEVOS: Getter y Setter para la imagen
    //public String getRutaImagen() { return rutaImagen; }
    //public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
}
