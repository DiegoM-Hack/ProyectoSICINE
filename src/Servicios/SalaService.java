package Servicios;

import DataBase.ConexionMongoDB;
import Modelos.Sala;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class SalaService {
    private MongoCollection<Document> salasCollection;
    private static MongoCollection<Document> salasCollection2;

    public SalaService() {
        MongoDatabase database = ConexionMongoDB.obtenerInstancia().getDatabase();
        salasCollection = database.getCollection("Salas");
        salasCollection2 = database.getCollection("Salas");
    }

    public boolean agregarSala(Sala sala) {
        try {
            Document doc = new Document("nombre", sala.getNombre())
                    .append("numeroAsientos", sala.getNumeroAsientos())
                    .append("tipo", sala.getTipo());
            salasCollection.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Sala> obtenerTodasLasSalas() {
        List<Sala> lista = new ArrayList<>();
        for (Document doc : salasCollection.find()) {
            String nombre = doc.getString("nombre");
            int asientos = doc.getInteger("numeroAsientos", 0);
            String tipo = doc.getString("tipo");

            lista.add(new Sala(nombre, asientos, tipo));
        }
        return lista;
    }





    public Sala obtenerSalaPorNombre(String nombreSala) {
        Document doc = salasCollection.find(eq("nombre", nombreSala)).first();
        if (doc != null) {
            String nombre = doc.getString("nombre");
            int asientos = doc.getInteger("numeroAsientos", 0);
            String tipo = doc.getString("tipo");
            return new Sala(nombre, asientos, tipo);
        }
        return null;
    }
}
