package Servicios;

import DataBase.ConexionMongoDB;
import Modelos.Venta;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Servicio para gestionar la colección de boletos en la base de datos MongoDB.
 */
public class BoletoService {
    private MongoCollection<Document> boletosCollection;

    /**
     * Constructor que inicializa la conexion con la coleccion "Boletos".
     */
    public BoletoService() {
        MongoDatabase db = ConexionMongoDB.obtenerInstancia().getDatabase();
        boletosCollection = db.getCollection("Boletos");
    }

    /**
     * Guarda un boleto en la base de datos.
     *
     * @param venta Venta relacionada al boleto.
     * @param codigoQR Código QR generado.
     * @return true si se inserto correctamente, false si ocurrio un error.
     */
    public boolean guardarBoleto(Venta venta, String codigoQR) {
        try {
            Document doc = new Document("codigoQR", codigoQR)
                    .append("pelicula", venta.getPelicula())
                    .append("sala", venta.getSala())
                    .append("fechaHoraFuncion", venta.getFechaHoraFuncion())
                    .append("cantidadEntradas", venta.getCantidadEntradas())
                    .append("total", venta.getTotal())
                    .append("fechaVenta", venta.getFechaVenta())
                    .append("usuario", venta.getUsuario())
                    .append("estado", "valido");

            boletosCollection.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
