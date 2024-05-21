package com.empresa.hito2_programacion_hector;

import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import org.bson.Document;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
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
    }

    public void registerUser(String username, String email, String password) {
        String hashedPassword = hashPassword(password);
        Document newUser = new Document("nombre", username)
                .append("email", email)
                .append("contraseña", hashedPassword);
        usersCollection.insertOne(newUser);
    }

    public boolean verifyCredentials(String username, String password) {
        Document userDoc = usersCollection.find(new Document("nombre", username)).first();
        if (userDoc != null) {
            String storedPassword = userDoc.getString("contraseña");
            String hashedPassword = hashPassword(password);
            return storedPassword.equals(hashedPassword);
        }
        return false;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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