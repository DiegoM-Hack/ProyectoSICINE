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

public class VentaService {

    private MongoCollection<Document> ventasCollection;

    public VentaService() {
        MongoDatabase db = ConexionMongoDB.obtenerInstancia().getDatabase();
        ventasCollection = db.getCollection("Ventas");
    }

    public boolean registrarVenta(Venta venta) {
        try {
            Document doc = new Document("pelicula", venta.getPelicula())
                    .append("sala", venta.getSala())
                    .append("fechaHoraFuncion", venta.getFechaHoraFuncion())
                    .append("cantidadEntradas", venta.getCantidadEntradas())
                    .append("total", venta.getTotal())
                    .append("fechaVenta", venta.getFechaVenta())
                    .append("usuario",venta.getUsuario())
                    .append("codigoQR", venta.getCodigoQR());

            ventasCollection.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Venta> obtenerTodasLasVentas() {
        List<Venta> lista = new ArrayList<>();
        try {
            FindIterable<Document> documentos = ventasCollection.find();
            for (Document doc : documentos) {
                String pelicula = doc.getString("pelicula");
                String sala = doc.getString("sala");

                Date fechaHoraFuncion = doc.getDate("fechaHoraFuncion"); // ¡Nombre correcto!
                int cantidad = doc.getInteger("cantidadEntradas", 0);
                double total = doc.getDouble("total");

                Date fechaVenta = doc.getDate("fechaVenta");
                String usuario = doc.getString("usuario");

                lista.add(new Venta(pelicula, sala, fechaHoraFuncion, cantidad, total, fechaVenta, usuario));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


    public List<Venta> obtenerVentasPorUsuario(String nombreUsuario) {
        List<Venta> lista = new ArrayList<>();
        try {
            Bson filtro = Filters.eq("usuario", nombreUsuario);
            FindIterable<Document> documentos = ventasCollection.find(filtro);
            for (Document doc : documentos) {
                String pelicula = doc.getString("pelicula");
                String sala = doc.getString("sala");
                Date fechaHora = doc.getDate("fechaHora");
                int cantidad = doc.getInteger("cantidadEntradas", 0);
                double total = doc.getDouble("total");
                String usuario = doc.getString("usuario");
                Date fechaVenta = doc.getDate("fechaVenta"); // <-- NUEVO

                lista.add(new Venta(pelicula, sala, fechaHora, cantidad, total, fechaVenta));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


}
