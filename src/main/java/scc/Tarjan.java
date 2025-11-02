package scc;

import graph.Edge;
import graph.Graph;
import metrics.Metrics;

import java.util.*;

public class Tarjan {
    /*
    * sccList is a list off SCCs
    * index is the order the vertex was discovered
    *lowLink the smallest rechable vertexs index */
    private static int time;
    private static List<List<Integer>> sccList;
    private static int[] index;
    private static int[] lowLink;
    private static Stack<Integer> stack;
    private static boolean[] onStack;


    public static List<List<Integer>> run(Graph graph, Metrics metrics){
        int n = graph.getVertexAmount();
        time = 0;
        lowLink = new int[n+1];
        index = new int[n+1];
        stack = new Stack<>();
        onStack = new boolean[n+1];
        sccList = new ArrayList<>();
        metrics.reset();
        metrics.start();

        Arrays.fill(index,-1);

        for(int vertex = 1; vertex <=n; vertex++){
            if(index[vertex]!=-1){
                continue;
            }
            dfs(graph,vertex,metrics);
        }
        metrics.stop();
        return sccList;

    }

    public static void dfs(Graph graph, int vertex,Metrics metrics){
        index[vertex]=lowLink[vertex]=time++;
        stack.push(vertex);
        onStack[vertex]=true;
        metrics.addDfsVisit();

        for(Edge edge: graph.getAdjacencyList().get(vertex)){
            metrics.addEdgeProcessed();
            /*So whats going on here:
            * if my first vertex is 1 and its adjList.get(1)=[1: from: 1, to: 2...]
            * then at first iteration vertexTo= 2*/
            int vertexTo=edge.getTo();
            /*If vertexTo wasnt still discovered do this: */
            if(index[vertexTo]==-1){
                dfs(graph, vertexTo, metrics);
                lowLink[vertexTo]=Math.min(lowLink[vertex], lowLink[vertexTo]);
            }
            else if(onStack[vertexTo]==true){
                lowLink[vertex]=Math.min(lowLink[vertex], lowLink[vertexTo]);
            }
        }

        if(lowLink[vertex]==index[vertex]){
            List<Integer> component = new ArrayList<>();
            int node;
            do{
                node=stack.pop();
                onStack[node]=false;
                component.add(node);
            }while (node!=vertex);
            sccList.add(component);
        }
    }




}