package com.empresa.hito2_programacion_hector;

import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManagerApp {
    private static final String CONNECTION_STRING = "mongodb+srv://admin:admin@hito2.18p3lxz.mongodb.net/?retryWrites=true&w=majority&appName=Hito2";
    private static final String DATABASE_NAME = "hito2";
    private static final String USERS_COLLECTION = "hito";
    private static final String PIECES_COLLECTION = "hito2";

    private MongoCollection<Document> piecesCollection;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> usersCollection;

    public DatabaseManagerApp() {
        ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase(DATABASE_NAME);
        usersCollection = database.getCollection(USERS_COLLECTION);
        piecesCollection = database.getCollection(PIECES_COLLECTION);

        System.out.println("Conexión a la base de datos establecida correctamente.");
    }
    public void addPiece(Pieza pieza) {
        Document doc = new Document("_id", pieza.get_id())
                .append("nombre", pieza.getNombre())
                .append("numero_serie", pieza.getNumero_serie())
                .append("fabricante", pieza.getFabricante())
                .append("descripcion", pieza.getDescripcion())
                .append("vehiculo", new Document("marca", pieza.getMarcaVehiculo())
                        .append("modelo", pieza.getModeloVehiculo()));
        piecesCollection.insertOne(doc);
    }
    public void modifyPiece(String id, Pieza newPiece) {
        int idAsInt = Integer.parseInt(id);
        Document newDoc = new Document("_id", idAsInt)
                .append("nombre", newPiece.getNombre())
                .append("numero_serie", newPiece.getNumero_serie())
                .append("fabricante", newPiece.getFabricante())
                .append("descripcion", newPiece.getDescripcion())
                .append("vehiculo", new Document("marca", newPiece.getMarcaVehiculo())
                        .append("modelo", newPiece.getModeloVehiculo()));
        piecesCollection.updateOne(new Document("_id", idAsInt), new Document("$set", newDoc));
    }
    public void removePiece(int id) {
        piecesCollection.deleteOne(new Document("_id", id));
    }
    public List<Pieza> getAllPieces() {
        List<Pieza> pieces = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("hito2");
        for (Document doc : collection.find()) {
            pieces.add(new Pieza(doc));
        }
        return pieces;
    }
}