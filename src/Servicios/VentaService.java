package Servicios;

import DataBase.ConexionMongoDB;
import Modelos.Venta;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio que gestiona las ventas de entradas de cine.
 */
public class VentaService {
    private MongoCollection<Document> ventasCollection;

    /**
     * Inicializa la coleccion "Ventas".
     */
    public VentaService() {
        MongoDatabase db = ConexionMongoDB.obtenerInstancia().getDatabase();
        ventasCollection = db.getCollection("Ventas");
    }

    /**
     * Registra una nueva venta.
     *
     * @param venta Venta a registrar.
     * @return true si se inserto correctamente.
     */
    public boolean registrarVenta(Venta venta) {
        try {
            Document doc = new Document("pelicula", venta.getPelicula())
                    .append("sala", venta.getSala())
                    .append("fechaHoraFuncion", venta.getFechaHoraFuncion())
                    .append("cantidadEntradas", venta.getCantidadEntradas())
                    .append("total", venta.getTotal())
                    .append("fechaVenta", venta.getFechaVenta())
                    .append("usuario", venta.getUsuario())
                    .append("codigoQR", venta.getCodigoQR());

            ventasCollection.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todas las ventas registradas.
     *
     * @return Lista de ventas.
     */
    public List<Venta> obtenerTodasLasVentas() {
        List<Venta> lista = new ArrayList<>();
        try {
            for (Document doc : ventasCollection.find()) {
                lista.add(new Venta(
                        doc.getString("pelicula"),
                        doc.getString("sala"),
                        doc.getDate("fechaHoraFuncion"),
                        doc.getInteger("cantidadEntradas", 0),
                        doc.getDouble("total"),
                        doc.getDate("fechaVenta"),
                        doc.getString("usuario")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Obtiene las ventas realizadas por un usuario.
     *
     * @param nombreUsuario Usuario a consultar.
     * @return Lista de ventas.
     */
    public List<Venta> obtenerVentasPorUsuario(String nombreUsuario) {
        List<Venta> lista = new ArrayList<>();
        try {
            FindIterable<Document> documentos = ventasCollection.find(Filters.eq("usuario", nombreUsuario));
            for (Document doc : documentos) {
                lista.add(new Venta(
                        doc.getString("pelicula"),
                        doc.getString("sala"),
                        doc.getDate("fechaHoraFuncion"),
                        doc.getInteger("cantidadEntradas", 0),
                        doc.getDouble("total"),
                        doc.getDate("fechaVenta"),
                        doc.getString("usuario")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}

