package org.example;

import graph.Edge;
import graph.Graph;
import model.GraphReader;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Graph graph = GraphReader.readGraphFromJson("tasks.json");
//
//        if (graph != null) {
//            System.out.println(graph);
//            System.out.println("Adjacency list: " + graph.getAdjacencyList());
//        }
        List<Edge> edges= new ArrayList<>();
        edges.add(new Edge(1,2,5));
        edges.add(new Edge(2,3,2));
        edges.add(new Edge(3,1,4));
        edges.add(new Edge(1,4,-2));
        edges.add(new Edge(4,5,8));
        edges.add(new Edge(5,4,1));
        int vertexAmount=5;
        Graph graph1= new Graph(true,vertexAmount,edges);
        System.out.println(graph1);
        System.out.println(graph1.getAdjacencyList());

    }
}