package DataBase;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Clase ConexionMongoDB implementa el patrón Singleton para establecer una conexión
 * única y reutilizable con una base de datos MongoDB Atlas.
 * Esta clase se conecta a la base de datos "PoliCine".
 */
public class ConexionMongoDB {

    /** URI de conexión al clúster de MongoDB Atlas */
    private static final String URI = "mongodb+srv://diegomontaluisa01:Aries1840@cluster0.6iwcoyc.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

    /** Instancia única de la clase */
    private static ConexionMongoDB instancia;

    /** Cliente de MongoDB */
    private MongoClient mongoClient;

    /** Referencia a la base de datos */
    private MongoDatabase database;

    /**
     * Constructor privado para aplicar el patrón Singleton.
     * Configura el cliente con la versión de la API del servidor y obtiene la base de datos.
     */
    private ConexionMongoDB() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(URI))
                .serverApi(serverApi)
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("PoliCine");
    }

    /**
     * Obtiene la instancia única de la clase ConexionMongoDB.
     *
     * @return instancia única de la clase
     */
    public static ConexionMongoDB obtenerInstancia() {
        if (instancia == null) {
            instancia = new ConexionMongoDB();
        }
        return instancia;
    }

    /**
     * Retorna la base de datos MongoDB a la que se está conectado.
     *
     * @return objeto MongoDatabase conectado
     */
    public MongoDatabase getDatabase() {
        return database;
    }
}

