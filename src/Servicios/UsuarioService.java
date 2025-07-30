package Servicios;

import DataBase.ConexionMongoDB;
import Modelos.Usuario;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UsuarioService {
    private final MongoCollection<Document> usuariosCollection;

    public UsuarioService() {
        MongoDatabase db = ConexionMongoDB.obtenerInstancia().getDatabase();
        this.usuariosCollection = db.getCollection("Usuarios");
    }

    public Usuario autenticar(String usuario, String clave) {
        Document doc = usuariosCollection.find(eq("usuario", usuario)).first();
        if (doc != null && doc.getString("clave").equals(clave)) {
            return new Usuario(
                    doc.getString("usuario"),
                    doc.getString("clave"),
                    doc.getString("rol")
            );
        }
        return null;
    }

    public boolean agregarUsuario(Usuario usuario) {
        try {
            Document filtro = new Document("usuario", usuario.getUsuario());
            if (usuariosCollection.find(filtro).first() != null) {
                return false; // Modelos.Usuario ya existe
            }

            Document nuevoUsuario = new Document("usuario", usuario.getUsuario())
                    .append("clave", usuario.getClave())
                    .append("rol", usuario.getRol());

            usuariosCollection.insertOne(nuevoUsuario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(String nombreUsuario) {
        try {
            Document filtro = new Document("usuario", nombreUsuario);
            usuariosCollection.deleteOne(filtro);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        for (Document doc : usuariosCollection.find()) {
            String usuario = doc.getString("usuario");
            String clave = doc.getString("clave");
            String rol = doc.getString("rol");
            lista.add(new Usuario(usuario, clave, rol));
        }
        return lista;
    }

    public boolean actualizarUsuario(String nombreUsuario, String nuevaClave, String nuevoRol) {
        try {
            MongoDatabase database = ConexionMongoDB.obtenerInstancia().getDatabase();
            MongoCollection<Document> usuariosCollection = database.getCollection("Usuarios");

            Document filtro = new Document("usuario", nombreUsuario);
            Document actualizacion = new Document();

            if (nuevaClave != null && !nuevaClave.isEmpty()) {
                actualizacion.append("clave", nuevaClave);
            }
            if (nuevoRol != null && !nuevoRol.isEmpty()) {
                actualizacion.append("rol", nuevoRol);
            }

            if (actualizacion.isEmpty()) {
                return false; // Nada que actualizar
            }

            Document updateDoc = new Document("$set", actualizacion);
            UpdateResult resultado = usuariosCollection.updateOne(filtro, updateDoc);
            return resultado.getModifiedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}
