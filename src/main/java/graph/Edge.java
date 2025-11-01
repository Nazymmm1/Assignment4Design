package graph;

public class Edge {
    /*
    * I write variables with their full names I really have tried to
    * write vertices as u and v but I get confused all the times*/
    private int from; //u
    private int to; //v
    private int weight; //w

    public Edge(int u, int v, int w){
        this.from=from;
        this.to=to;
        this.weight=weight;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }
     @Override
    public String toString(){
        return "u: "+ getFrom() + ",v: "+ getTo()+ "weight: "+ getWeight();
     }
}