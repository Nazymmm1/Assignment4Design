package org.example;

import dagsp.DAGshortestPath;
import graph.Edge;
import graph.Graph;
import metrics.Metrics;
import model.GraphReader;
import scc.Tarjan;
import topo.Kahn;
import util.CondensationGraphBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        Graph graph = GraphReader.readGraphFromJson("tasks.json");
//
//        if (graph != null) {
//            System.out.println(graph);
//            System.out.println("Adjacency list: " + graph.getAdjacencyList());
//        }
        // 1️⃣ Build graph manually or from JSON
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1,2,5));
        edges.add(new Edge(2,3,2));
        edges.add(new Edge(3,1,4));
        edges.add(new Edge(1,4,-2));
        edges.add(new Edge(4,5,8));
        edges.add(new Edge(5,4,1));

        Graph graph = new Graph(true, 5, edges);
        System.out.println("Graph: " + graph.getAdjacencyList());

        // 2️⃣ Run Tarjan
        Metrics tarjanMetrics = new Metrics();
        List<List<Integer>> sccs = Tarjan.run(graph, tarjanMetrics);
        System.out.println("SCCs: " + sccs);
        System.out.println("Tarjan time: " + tarjanMetrics.getExecutionTimeMs() + " ms");

        // 3️⃣ Build condensed graph
        Graph dag = CondensationGraphBuilder.buildGraphFromScc(graph, sccs);
        System.out.println("Condensed DAG: " + dag);
        System.out.println(dag.getAdjacencyList());

        // 4️⃣ Topological sort
        Metrics topoMetrics = new Metrics();
        List<Integer> topoOrder = Kahn.topoSort(dag, topoMetrics);
        System.out.println("Topo Order: " + topoOrder);

        // 5️⃣ Shortest path (you can pick source=1)
        Metrics spMetrics = new Metrics();
        Map<Integer, Integer> dist = DAGshortestPath.shortestPath(dag, 1, spMetrics);
        System.out.println("Shortest paths from 1: " + dist);
        System.out.println("Shortest path time: " + spMetrics.getExecutionTimeMs() + " ms");
    }
    }