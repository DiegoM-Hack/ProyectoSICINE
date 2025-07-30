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

public class PeliculaService {

    private final MongoCollection<Document> peliculasCollection;

    public PeliculaService() {
        MongoDatabase db = ConexionMongoDB.obtenerInstancia().getDatabase();
        this.peliculasCollection = db.getCollection("Pelicula");
    }
    public List<Pelicula> obtenerTodasLasPeliculas() {
        List<Pelicula> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = peliculasCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Pelicula peli = new Pelicula(
                        doc.getString("titulo"),
                        doc.getString("genero"),
                        doc.getInteger("duracion"),
                        doc.getString("clasificacion"),
                        doc.getString("sinopsis"),
                        doc.getString("director"),
                        doc.getInteger("anio")
                );
                lista.add(peli);
            }
        }
        return lista;
    }

    public void insertarPelicula(Pelicula peli) {
        Document doc = new Document("titulo", peli.getTitulo())
                .append("genero", peli.getGenero())
                .append("duracion", peli.getDuracion())
                .append("clasificacion", peli.getClasificacion())
                .append("sinopsis", peli.getSinopsis())
                .append("director", peli.getDirector())
                .append("anio", peli.getAnio());

        peliculasCollection.insertOne(doc);
        System.out.println("Película '" + peli.getTitulo() + "' insertada correctamente.");
    }

    public boolean eliminarPeliculaPorTitulo(String titulo) {
        Document filtro = new Document("titulo", titulo);
        DeleteResult resultado = peliculasCollection.deleteOne(filtro);
        return resultado.getDeletedCount() > 0;
    }

    //
    public List<Pelicula> buscarPeliculasPorTitulo(String titulo) {
        List<Pelicula> resultados = new ArrayList<>();

        Document filtro = new Document("titulo", new Document("$regex", titulo).append("$options", "i"));

        FindIterable<Document> docs = peliculasCollection.find(filtro);

        for (Document doc : docs) {
            Pelicula peli = new Pelicula(
                    doc.getString("titulo"),
                    doc.getString("genero"),
                    doc.getInteger("duracion", 0),
                    doc.getString("clasificacion"),
                    doc.getString("sinopsis"),
                    doc.getString("director"),
                    doc.getInteger("anio", 0)
            );
            resultados.add(peli);
        }

        return resultados;
    }
}

