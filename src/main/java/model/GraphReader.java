package model;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import graph.Edge;
import graph.Graph;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class GraphReader {
    public static Graph readGraphFromJson(String fileName) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(fileName)) {
            Type graphDataType = new TypeToken<GraphData>() {}.getType();
            GraphData data = gson.fromJson(reader, graphDataType);

            return new Graph(
                    data.isDirected,
                    data.vertexAmount,
                    data.edges
            );

        } catch (IOException e) {
            System.err.println("Error reading JSON: " + e.getMessage());
            return null;
        }
    }
    private static class GraphData {
        boolean isDirected;
        int vertexAmount;
        List<Edge> edges;

    }
}