package dagsp;

import graph.Edge;
import graph.Graph;
import metrics.Metrics;
import topo.Kahn;
import java.util.*;

public class DAGshortestPath {
    public static Map<Integer, Integer> shortestPath(Graph dag, int source, Metrics metrics) {
        if (!dag.getVertices().contains(source)) {
            throw new IllegalArgumentException("Source vertex not found in graph");
        }
        metrics.start();
        final int INF = 1_000_000_000;
        Map<Integer,Integer> distance= new HashMap<>();
        List<Integer> topoOrder = topo.Kahn.topoSort(dag, metrics);

        for(int node: dag.getVertices()){
            distance.put(node,INF);
        }
        distance.put(source,0);

        for(int node: topoOrder){
            if(distance.get(node)!=INF){
                List<Edge> edges= dag.getAdjacencyList().getOrDefault(node, new ArrayList<>());
                for(Edge edge: edges){
                    System.out.println(edge);
                    int v = edge.getTo();
                    int weight = edge.getWeight();
                    metrics.addEdgeProcessed();

                    if (distance.get(v) > distance.get(node) + weight) {
                        System.out.println(distance.get(v));
                        System.out.println(distance.get(node) + weight);
                        distance.put(v, distance.get(node) + weight);
                        metrics.addOperation();
                    }
                }
            }
        }

        metrics.stop();
        return distance;

    }
}