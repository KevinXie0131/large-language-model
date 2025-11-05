package com.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoDBLocalConnectExample {
    public static void main(String[] args) {
        // Connect to MongoDB running locally on default port 27017
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Get database (will create if not exists)
            MongoDatabase database = mongoClient.getDatabase("hello");

            // Get collection (will create if not exists)
            MongoCollection<Document> collection = database.getCollection("sample1");

            // Insert a document
         /*   Document doc = new Document("name", "Kevin")
                    .append("age", 28)
                    .append("city", "Shanghai");
            collection.insertOne(doc);
*/
            // Find and print all documents
            for (Document document : collection.find()) {
                System.out.println(document.toJson());
            }
        }
    }
}
