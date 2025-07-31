package Servicios;

import DataBase.ConexionMongoDB;
import Modelos.Funcion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Servicio que permite gestionar funciones de peliculas en MongoDB.
 */
public class FuncionService {
    private MongoCollection<Document> funcionesCollection;

    /**
     * Inicializa la conexion con la coleccion "Funciones".
     */
    public FuncionService() {
        MongoDatabase database = ConexionMongoDB.obtenerInstancia().getDatabase();
        funcionesCollection = database.getCollection("Funciones");
    }

    /**
     * Inserta una nueva funcion en la base de datos.
     *
     * @param funcion Funcion a insertar.
     * @return true si se inserta correctamente.
     */
    public boolean insertarFuncion(Funcion funcion) {
        try {
            Document doc = new Document("pelicula", funcion.getTituloPelicula())
                    .append("sala", funcion.getSala())
                    .append("fechaHora", funcion.getFechaHora());
            funcionesCollection.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una funcion existente.
     *
     * @param funcion Función a eliminar.
     * @return true si se elimino al menos un documento.
     */
    public boolean eliminarFuncion(Funcion funcion) {
        try {
            Document filtro = new Document("pelicula", funcion.getTituloPelicula())
                    .append("sala", funcion.getSala())
                    .append("fechaHora", funcion.getFechaHora());
            return funcionesCollection.deleteOne(filtro).getDeletedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todas las funciones registradas.
     *
     * @return Lista de funciones.
     */
    public List<Funcion> obtenerTodasLasFunciones() {
        List<Funcion> lista = new ArrayList<>();
        for (Document doc : funcionesCollection.find()) {
            lista.add(new Funcion(
                    doc.getString("pelicula"),
                    doc.getString("sala"),
                    doc.getDate("fechaHora")));
        }
        return lista;
    }

    /**
     * Obtiene funciones por titulo de pelicula.
     *
     * @param titulo Titulo de la pelicula.
     * @return Lista de funciones.
     */
    public List<Funcion> obtenerFuncionesPorPelicula(String titulo) {
        List<Funcion> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = funcionesCollection.find(Filters.eq("pelicula", titulo)).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                lista.add(new Funcion(
                        doc.getString("pelicula"),
                        doc.getString("sala"),
                        doc.getDate("fechaHora")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}

