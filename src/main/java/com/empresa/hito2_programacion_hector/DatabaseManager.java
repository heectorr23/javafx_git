package com.empresa.hito2_programacion_hector;

import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseManager {
    private static final String CONNECTION_STRING = "mongodb+srv://admin:admin@hito2.18p3lxz.mongodb.net/?retryWrites=true&w=majority&appName=Hito2";
    private static final String DATABASE_NAME = "hito";
    private static final String USERS_COLLECTION = "hito";
    private static final String PIECES_COLLECTION = "hito2";

    private MongoCollection<Document> piecesCollection;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> usersCollection;

    public DatabaseManager() {
        ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase(DATABASE_NAME);
        usersCollection = database.getCollection(USERS_COLLECTION);
        piecesCollection = database.getCollection(PIECES_COLLECTION);
    }

    public void registerUser(String username, String email, String password) {
        Document newUser = new Document("nombre", username)
                .append("email", email)
                .append("contraseña", password);
        usersCollection.insertOne(newUser);
    }

    public boolean verifyCredentials(String username, String password) {
        Document query = new Document("nombre", username)
                .append("contraseña", password);
        boolean isValid = usersCollection.countDocuments(Filters.and(Filters.eq("nombre", username), Filters.eq("contraseña", password))) > 0;

        if (!isValid) {
            System.out.println("Error: Las credenciales proporcionadas no son válidas.");
        } else {
            Document loginRecord = new Document("nombre", username)
                    .append("loginTime", new Date());
            usersCollection.insertOne(loginRecord);
        }

        return isValid;
    }

    public List<Pieza> getAllPieces() {
        List<Pieza> pieces = new ArrayList<>();
        FindIterable<Document> findIterable = piecesCollection.find();
        for (Document doc : findIterable) {
            pieces.add(new Pieza(doc));
        }
        return pieces;
    }
}
