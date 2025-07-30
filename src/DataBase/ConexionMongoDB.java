package DataBase;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongoDB {

    private static final String URI = "mongodb+srv://diegomontaluisa01:Aries1840@cluster0.6iwcoyc.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static ConexionMongoDB instancia;
    private MongoClient mongoClient;
    private MongoDatabase database;

    // Constructor privado para evitar instancias externas
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

    // Método público para obtener la instancia única
    public static ConexionMongoDB obtenerInstancia() {
        if (instancia == null) {
            instancia = new ConexionMongoDB();
        }
        return instancia;
    }

    // Método para obtener la base de datos
    public  MongoDatabase getDatabase() {
        return database;
    }
}

