package util;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CondensationGraphBuilder {
    public static Graph buildGraphFromScc(Graph graph, List<List<Integer>> sccList) {
        Map<Integer, Integer> vertexToScc = new HashMap<>();
        for (int i = 0; i < sccList.size(); i++) {
            for (int vertex : sccList.get(i)) {
                vertexToScc.put(vertex, i + 1); // make SCC IDs 1-based
            }
        }

        List<Edge> edges = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            int fromComp = vertexToScc.get(edge.getFrom());
            int toComp = vertexToScc.get(edge.getTo());
            if (fromComp != toComp) {
                edges.add(new Edge(fromComp, toComp, edge.getWeight()));
            }
        }

        return new Graph(true, sccList.size(), edges);
    }

}