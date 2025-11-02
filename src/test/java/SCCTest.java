import graph.Edge;
import graph.Graph;
import metrics.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scc.Tarjan;
import topo.Kahn;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class SCCTest {
    private Graph dag;

    @BeforeEach
    void setup() {
        List<Edge> dagEdges = new ArrayList<>();
        dagEdges.add(new Edge(1, 2, 5));
        dagEdges.add(new Edge(1, 3, 3));
        dagEdges.add(new Edge(2, 4, 6));
        dagEdges.add(new Edge(3, 4, 4));
        dag = new Graph(true, 4, dagEdges);
    }

    @Test
    void testTarjanSCC() {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1, 2, 5));
        edges.add(new Edge(2, 3, 2));
        edges.add(new Edge(3, 1, 4));
        edges.add(new Edge(1, 4, -2));
        edges.add(new Edge(4, 5, 8));
        edges.add(new Edge(5, 4, 1));

        Graph graph = new Graph(true, 5, edges);
        System.out.println("Graph: " + graph.getAdjacencyList());

        Metrics tarjanMetrics = new Metrics();
        List<List<Integer>> sccs = Tarjan.run(graph, tarjanMetrics);

        assertEquals(2, sccs.size(), "Should detect 2 SCCs");
    }

    @Test
    void testKahnTopologicalSort() {
        Metrics topoMetrics = new Metrics();
        List<Integer> topoOrder = Kahn.topoSort(dag, topoMetrics);

        assertEquals(4, topoOrder.size());
        assertEquals(1, topoOrder.get(0));
        assertEquals(4, topoOrder.get(3));
        assertTrue(
                topoOrder.equals(Arrays.asList(1, 2, 3, 4)) ||
                        topoOrder.equals(Arrays.asList(1, 3, 2, 4)),
                "Topo order should be one of the valid orders"
        );
    }
}