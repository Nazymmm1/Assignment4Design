package topo;

import graph.Edge;
import graph.Graph;
import metrics.Metrics;

import java.util.*;

public class Kahn {
    public static List<Integer> topoSort(Graph dag, Metrics metrics){
        metrics.reset();
        metrics.start();
        Map<Integer, List<Edge>> adj = dag.getAdjacencyList();
        Map<Integer, Integer> indegree = new HashMap<>();

        /*First we put for every node indeg 0 as a default*/
        for(int node: adj.keySet()){
            indegree.putIfAbsent(node,0);
            /*for every vertex in iterating node we iterate with neighbours its
            * connected to, and if the node have a neighbour neighbours indegree is going to increase  */
            for(Edge edge: adj.get(node)){
                int neighbour= edge.getTo();
                indegree.put(neighbour,indegree.getOrDefault(neighbour,0)+1);

            }
        }

        Queue<Integer> queue= new LinkedList<>();
        for(int node: indegree.keySet()){
            if(indegree.get(node)==0){
                queue.add(node);
                metrics.addQueuePush();
            }
        }

        List<Integer> topoOrder= new ArrayList<>();

        while (!queue.isEmpty()){
            int node=queue.poll();
            topoOrder.add(node);
            metrics.addQueuePoll();

            for(Edge edge : adj.getOrDefault(node, new ArrayList<>())) {
                int neighbour = edge.getTo();
                indegree.put(neighbour, indegree.get(neighbour) - 1);
                if (indegree.get(neighbour) == 0) {
                    queue.add(neighbour);
                    metrics.addQueuePush();
                }
            }

        }
        metrics.stop();
        return topoOrder;
    }
}