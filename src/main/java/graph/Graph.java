package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private List<Edge> edges;
    private Map<Integer,List<Edge>> adjacencyList;
    private Boolean isDirected;
    private List<Integer> vertices;
    private int vertexAmount;

    public Graph(Boolean isDirected, int vertexAmount, List<Edge> edges) {
        this.isDirected = isDirected;
        this.edges = edges;
        this.vertexAmount = vertexAmount;
        this.vertices = new ArrayList<>();
        for (Integer i = 1; i <= vertexAmount; i++) {
            this.vertices.add(i);
        }
        buildAdjacencyList();
    }

    public void buildAdjacencyList(){
        adjacencyList= new HashMap<>();
        for(Integer vertex: vertices){
            adjacencyList.put(vertex,new ArrayList<>());
        }
        for(Edge edge: edges){
            adjacencyList.get(edge.getFrom()).add(edge);
        }
    }

    public int getVertexAmount() {
        return vertexAmount;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Boolean getDirected() {
        return isDirected;
    }

    public List<Integer> getVertices() {
        return vertices;
    }

    public Map<Integer, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getEdgeSize(){
        return edges.size();
    }
    @Override
    public String toString(){
        return "Directed: "+ isDirected+ " with "+ getVertexAmount()+" vertices and "+getEdgeSize()+" edges";
    }
}