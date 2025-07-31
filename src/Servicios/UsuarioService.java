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

/**
 * Servicio para gestionar usuarios.
 */
public class UsuarioService {
    private final MongoCollection<Document> usuariosCollection;

    /**
     * Constructor que establece conexion con la coleccion "Usuarios".
     */
    public UsuarioService() {
        MongoDatabase db = ConexionMongoDB.obtenerInstancia().getDatabase();
        usuariosCollection = db.getCollection("Usuarios");
    }

    /**
     * Autentica un usuario comparando usuario y contraseña.
     *
     * @param usuario Nombre de usuario.
     * @param clave Clave del usuario.
     * @return Usuario autenticado o null si falla.
     */
    public Usuario autenticar(String usuario, String clave) {
        Document doc = usuariosCollection.find(eq("usuario", usuario)).first();
        if (doc != null && doc.getString("clave").equals(clave)) {
            return new Usuario(doc.getString("usuario"), doc.getString("clave"), doc.getString("rol"));
        }
        return null;
    }

    /**
     * Agrega un nuevo usuario si no existe.
     *
     * @param usuario Objeto Usuario.
     * @return true si fue agregado, false si ya existia.
     */
    public boolean agregarUsuario(Usuario usuario) {
        try {
            if (usuariosCollection.find(new Document("usuario", usuario.getUsuario())).first() != null) {
                return false;
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

    /**
     * Elimina un usuario por su nombre.
     *
     * @param nombreUsuario Nombre del usuario.
     * @return true si fue eliminado.
     */
    public boolean eliminarUsuario(String nombreUsuario) {
        try {
            return usuariosCollection.deleteOne(new Document("usuario", nombreUsuario)).getDeletedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de usuarios.
     */
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        for (Document doc : usuariosCollection.find()) {
            lista.add(new Usuario(
                    doc.getString("usuario"),
                    doc.getString("clave"),
                    doc.getString("rol")));
        }
        return lista;
    }

    /**
     * Actualiza los datos de un usuario.
     *
     * @param nombreUsuario Usuario a modificar.
     * @param nuevaClave Nueva contraseña.
     * @param nuevoRol Nuevo rol.
     * @return true si se modifico.
     */
    public boolean actualizarUsuario(String nombreUsuario, String nuevaClave, String nuevoRol) {
        try {
            Document actualizacion = new Document();
            if (nuevaClave != null && !nuevaClave.isEmpty()) actualizacion.append("clave", nuevaClave);
            if (nuevoRol != null && !nuevoRol.isEmpty()) actualizacion.append("rol", nuevoRol);

            if (actualizacion.isEmpty()) return false;
            Document updateDoc = new Document("$set", actualizacion);
            return usuariosCollection.updateOne(new Document("usuario", nombreUsuario), updateDoc).getModifiedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
