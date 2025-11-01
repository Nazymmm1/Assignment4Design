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

    public Graph(Boolean isDirected, List<Edge> edges,List<Integer> vertices){
        if(!isDirected){
            System.out.println("I work only with directed graphs");
            return;
        }
        this.edges=edges;
        this.isDirected=isDirected;
        this.vertices=vertices;
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
    public int getVerticeSize(){
        return vertices.size();
    }
    public int getEdgeSize(){
        return edges.size();
    }
    @Override
    public String toString(){
        return "Directed: "+ isDirected+ "with "+ getVerticeSize()+" vertices and "+getEdgeSize()+" edges";
    }
}