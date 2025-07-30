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


public class FuncionService {
    private MongoCollection<Document> funcionesCollection;

    public FuncionService() {
        MongoDatabase database = ConexionMongoDB.obtenerInstancia().getDatabase();
        funcionesCollection = database.getCollection("Funciones");
    }



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

    public boolean eliminarFuncion(Funcion funcion) {
        try {
            Document filtro = new Document("pelicula", funcion.getTituloPelicula())
                    .append("sala", funcion.getSala())
                    .append("fechaHora", funcion.getFechaHora());
            long eliminados = funcionesCollection.deleteOne(filtro).getDeletedCount();
            return eliminados > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Funcion> obtenerTodasLasFunciones() {
        List<Funcion> lista = new ArrayList<>();
        FindIterable<Document> documentos = funcionesCollection.find();
        for (Document doc : documentos) {
            String titulo = doc.getString("pelicula");
            String sala = doc.getString("sala");
            Date fechaHora = doc.getDate("fechaHora");
            lista.add(new Funcion(titulo, sala, fechaHora));
        }
        return lista;
    }

    public List<Funcion> obtenerFuncionesPorPelicula(String titulo) {
        List<Funcion> lista = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = funcionesCollection.find(Filters.eq("pelicula", titulo)).iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String pelicula = doc.getString("pelicula");
                String sala = doc.getString("sala");
                Date fechaHora = doc.getDate("fechaHora");
                lista.add(new Funcion(pelicula, sala, fechaHora));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
