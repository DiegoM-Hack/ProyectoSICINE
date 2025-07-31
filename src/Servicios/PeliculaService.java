package Servicios;

import DataBase.ConexionMongoDB;
import Modelos.Pelicula;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.FindIterable;

/**
 * Servicio para gestionar la coleccion de peliculas.
 */
public class PeliculaService {
    private final MongoCollection<Document> peliculasCollection;

    /**
     * Inicializa la conexion con la colección "Pelicula".
     */
    public PeliculaService() {
        MongoDatabase db = ConexionMongoDB.obtenerInstancia().getDatabase();
        peliculasCollection = db.getCollection("Pelicula");
    }

    /**
     * Obtiene todas las peliculas almacenadas.
     *
     * @return Lista de peliculas.
     */
    public List<Pelicula> obtenerTodasLasPeliculas() {
        List<Pelicula> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = peliculasCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                lista.add(new Pelicula(
                        doc.getString("titulo"),
                        doc.getString("genero"),
                        doc.getInteger("duracion"),
                        doc.getString("clasificacion"),
                        doc.getString("sinopsis"),
                        doc.getString("director"),
                        doc.getInteger("anio")));
            }
        }
        return lista;
    }

    /**
     * Inserta una nueva pelicula.
     *
     * @param peli Objeto Pelicula a insertar.
     */
    public void insertarPelicula(Pelicula peli) {
        Document doc = new Document("titulo", peli.getTitulo())
                .append("genero", peli.getGenero())
                .append("duracion", peli.getDuracion())
                .append("clasificacion", peli.getClasificacion())
                .append("sinopsis", peli.getSinopsis())
                .append("director", peli.getDirector())
                .append("anio", peli.getAnio());
        peliculasCollection.insertOne(doc);
    }

    /**
     * Elimina una pelicula por su título.
     *
     * @param titulo Titulo de la pelicula.
     * @return true si se elimino correctamente.
     */
    public boolean eliminarPeliculaPorTitulo(String titulo) {
        Document filtro = new Document("titulo", titulo);
        return peliculasCollection.deleteOne(filtro).getDeletedCount() > 0;
    }

    /**
     * Busca películas por coincidencia de titulo (regex).
     *
     * @param titulo Titulo parcial o completo.
     * @return Lista de películas coincidentes.
     */
    public List<Pelicula> buscarPeliculasPorTitulo(String titulo) {
        List<Pelicula> resultados = new ArrayList<>();
        Document filtro = new Document("titulo", new Document("$regex", titulo).append("$options", "i"));
        for (Document doc : peliculasCollection.find(filtro)) {
            resultados.add(new Pelicula(
                    doc.getString("titulo"),
                    doc.getString("genero"),
                    doc.getInteger("duracion", 0),
                    doc.getString("clasificacion"),
                    doc.getString("sinopsis"),
                    doc.getString("director"),
                    doc.getInteger("anio", 0)));
        }
        return resultados;
    }
}

