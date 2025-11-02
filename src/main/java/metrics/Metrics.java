package metrics;

public class Metrics {
    private int totalCost;
    private int operationsCount;
    private int dfsVisits;      // For Tarjan (SCC)
    private int edgesProcessed; // For Tarjan and DAG-SP relaxations
    private int queuePush;
    private int queuePoll;
    private long startTime;
    private long endTime;

    public int getQueuePoll() {
        return queuePoll;
    }

    public int getQueuePush() {
        return queuePush;
    }
    public void addQueuePush(){
        queuePush++;
    }
    public void addQueuePoll(){
        queuePoll++;
    }
    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public double getExecutionTimeMs() {
        return (endTime - startTime) / 1_000_000.0;
    }

    public void addOperation() {
        operationsCount++;
    }

    public void addCost(int cost) {
        totalCost += cost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void addDfsVisit() { dfsVisits++; }
    public void addEdgeProcessed() { edgesProcessed++; }


    public int getDfsVisits() { return dfsVisits; }
    public int getEdgesProcessed() { return edgesProcessed; }


    public void reset(){
        totalCost=0;
        operationsCount=0;
        startTime=0;
        endTime=0;
        dfsVisits=0;
        queuePoll=0;
        queuePush=0;
        edgesProcessed=0;
    }
    public double measureExecutionTime(Runnable algorithm, int repetitions) {
        long totalTime = 0;
        for (int i = 0; i < repetitions; i++) {
            start();
            algorithm.run();
            stop();
            totalTime += (endTime - startTime);
        }
        return totalTime / 1_000_000.0 / repetitions;
    }
    public int getOperationsCount() {
        return operationsCount;
    }
}