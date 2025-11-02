package daglp;

import graph.Edge;
import graph.Graph;
import metrics.Metrics;

import java.util.*;

public class DAGlongestPath {

    public static class LongestPathResult {
        public int length;
        public List<Integer> path;

        public LongestPathResult(int length, List<Integer> path) {
            this.length = length;
            this.path = path;
        }
    }

    public static LongestPathResult longestPath(Graph dag, int source, Metrics metrics) {
        if (!dag.getVertices().contains(source)) {
            throw new IllegalArgumentException("Source vertex not found in graph");
        }

        metrics.start();
        final int NEG_INF = Integer.MIN_VALUE / 2;
        Map<Integer, Integer> distance = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();

        for (int v : dag.getVertices()) {
            distance.put(v, NEG_INF);
            prev.put(v, null);
        }
        distance.put(source, 0);

        List<Integer> topoOrder = topo.Kahn.topoSort(dag, metrics);

        for (int u : topoOrder) {
            if (distance.get(u) != NEG_INF) {
                for (Edge e : dag.getAdjacencyList().getOrDefault(u, new ArrayList<>())) {
                    int v = e.getTo();
                    int w = e.getWeight();
                    metrics.addEdgeProcessed();
                    if (distance.get(v) < distance.get(u) + w) {
                        distance.put(v, distance.get(u) + w);
                        prev.put(v, u);
                        metrics.addOperation();
                    }
                }
            }
        }

        int maxDist = NEG_INF;
        int endVertex = source;
        for (int v : dag.getVertices()) {
            if (distance.get(v) > maxDist) {
                maxDist = distance.get(v);
                endVertex = v;
            }
        }

        List<Integer> path = new ArrayList<>();
        Integer current = endVertex;
        while (current != null) {
            path.add(current);
            current = prev.get(current);
        }
        Collections.reverse(path);

        metrics.stop();
        return new LongestPathResult(maxDist, path);
    }
}