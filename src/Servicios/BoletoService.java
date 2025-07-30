package Servicios;

import DataBase.ConexionMongoDB;
import Modelos.Venta;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class BoletoService {
    private MongoCollection<Document> boletosCollection;

    public BoletoService() {
        MongoDatabase db = ConexionMongoDB.obtenerInstancia().getDatabase();
        boletosCollection = db.getCollection("Boletos");
    }
    public boolean guardarBoleto(Venta venta, String codigoQR) {
        try {
            Document doc = new Document("codigoQR", codigoQR)
                    .append("pelicula", venta.getPelicula())
                    .append("sala", venta.getSala())
                    .append("fechaHoraFuncion", venta.getFechaHoraFuncion())
                    .append("cantidadEntradas", venta.getCantidadEntradas())
                    .append("total", venta.getTotal())
                    .append("fechaVenta", venta.getFechaVenta())
                    .append("usuario", venta.getUsuario()) // Asegúrate que venta tenga el usuario
                    .append("estado", "valido");

            boletosCollection.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
