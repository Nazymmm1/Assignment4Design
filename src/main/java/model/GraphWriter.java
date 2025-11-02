package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.List;

public class GraphWriter {

    public static void writeResultsToJson(String fileName,
                                          Map<String, Object> results) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(results, writer);
        } catch (IOException e) {
            System.err.println("Error writing JSON: " + e.getMessage());
        }
    }
}