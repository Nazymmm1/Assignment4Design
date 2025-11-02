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

    public static List<GraphWithSource> readGraphsFromJson(String fileName) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(fileName)) {
            Type listType = new TypeToken<List<GraphData>>() {}.getType();
            List<GraphData> graphDataList = gson.fromJson(reader, listType);

            return graphDataList.stream()
                    .map(data -> new GraphWithSource(new Graph(data.isDirected, data.vertexAmount, data.edges), data.source))
                    .toList();

        } catch (IOException e) {
            System.err.println("Error reading JSON: " + e.getMessage());
            return null;
        }
    }

    private static class GraphData {
        boolean isDirected;
        int vertexAmount;
        List<Edge> edges;
        Integer source;
    }

    public static class GraphWithSource {
        public final Graph graph;
        public final Integer source;

        public GraphWithSource(Graph graph, Integer source) {
            this.graph = graph;
            this.source = source;
        }
    }
}