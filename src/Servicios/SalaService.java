package Servicios;

import DataBase.ConexionMongoDB;
import Modelos.Sala;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Servicio para gestionar salas de cine.
 */
public class SalaService {
    private MongoCollection<Document> salasCollection;

    /**
     * Constructor que inicializa la coleccion "Salas".
     */
    public SalaService() {
        MongoDatabase database = ConexionMongoDB.obtenerInstancia().getDatabase();
        salasCollection = database.getCollection("Salas");
    }

    /**
     * Inserta una nueva sala en la base de datos.
     *
     * @param sala Objeto Sala.
     * @return true si fue insertada correctamente.
     */
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

    /**
     * Retorna todas las salas registradas.
     *
     * @return Lista de salas.
     */
    public List<Sala> obtenerTodasLasSalas() {
        List<Sala> lista = new ArrayList<>();
        for (Document doc : salasCollection.find()) {
            lista.add(new Sala(
                    doc.getString("nombre"),
                    doc.getInteger("numeroAsientos", 0),
                    doc.getString("tipo")));
        }
        return lista;
    }

    /**
     * Busca una sala por su nombre.
     *
     * @param nombreSala Nombre de la sala.
     * @return Sala encontrada o null si no existe.
     */
    public Sala obtenerSalaPorNombre(String nombreSala) {
        Document doc = salasCollection.find(eq("nombre", nombreSala)).first();
        if (doc != null) {
            return new Sala(
                    doc.getString("nombre"),
                    doc.getInteger("numeroAsientos", 0),
                    doc.getString("tipo"));
        }
        return null;
    }
}
