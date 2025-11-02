import graph.Edge;
import graph.Graph;
import metrics.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scc.Tarjan;
import topo.Kahn;
import dagsp.DAGshortestPath;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class SCCTest {

    private Graph dag;
    private Metrics metrics;

    @BeforeEach
    void setup() {
        metrics = new Metrics();

        List<Edge> dagEdges = new ArrayList<>();
        dagEdges.add(new Edge(1, 2, 3));
        dagEdges.add(new Edge(1, 3, 6));
        dagEdges.add(new Edge(2, 3, 4));
        dagEdges.add(new Edge(2, 4, 11));
        dagEdges.add(new Edge(3, 4, -2)); // negative edge allowed in DAG
        dag = new Graph(true, 4, dagEdges);
    }


    @Test
    void testTarjanDetectsTwoSCCs() {
        List<Edge> edges = List.of(
                new Edge(1, 2, 5),
                new Edge(2, 3, 2),
                new Edge(3, 1, 4),
                new Edge(1, 4, -2),
                new Edge(4, 5, 8),
                new Edge(5, 4, 1)
        );
        Graph graph = new Graph(true, 5, new ArrayList<>(edges));

        Metrics m = new Metrics();
        List<List<Integer>> sccs = Tarjan.run(graph, m);

        assertEquals(2, sccs.size(), "Tarjan should find exactly 2 SCCs");
        assertTrue(sccs.stream().anyMatch(c -> c.containsAll(List.of(1, 2, 3))));
        assertTrue(sccs.stream().anyMatch(c -> c.containsAll(List.of(4, 5))));
    }


    @Test
    void testTarjanWithNoEdges() {
        List<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(true, 3, edges);
        List<List<Integer>> sccs = Tarjan.run(graph, new Metrics());
        assertEquals(3, sccs.size(), "Each vertex should be its own SCC in an empty graph");
    }


    @Test
    void testKahnTopologicalSort() {
        Metrics m = new Metrics();
        List<Integer> topoOrder = Kahn.topoSort(dag, m);

        assertEquals(4, topoOrder.size());
        assertEquals(1, topoOrder.get(0));
        assertEquals(4, topoOrder.get(3));
        assertTrue(
                topoOrder.equals(Arrays.asList(1, 2, 3, 4)) ||
                        topoOrder.equals(Arrays.asList(1, 3, 2, 4)),
                "Topo order should be one of the valid orders"
        );
    }


    @Test
    void testKahnSingleVertex() {
        Graph singleNode = new Graph(true, 1, new ArrayList<>());
        List<Integer> topoOrder = Kahn.topoSort(singleNode, new Metrics());
        assertEquals(List.of(1), topoOrder);
    }


    @Test
    void testDAGShortestPathFrom1() {
        Map<Integer, Integer> dist = DAGshortestPath.shortestPath(dag, 1, metrics);

        assertEquals(0, dist.get(1));
        assertEquals(3, dist.get(2));
        assertEquals(6, dist.get(3));
        assertEquals(4, dist.get(4));
    }

    @Test
    void testDAGShortestPathUnreachable() {
        // Graph with isolated vertex 4
        List<Edge> edges = List.of(new Edge(1, 2, 1), new Edge(2, 3, 2));
        Graph isolated = new Graph(true, 4, new ArrayList<>(edges));

        Map<Integer, Integer> dist = DAGshortestPath.shortestPath(isolated, 1, metrics);
        assertEquals(1_000_000_000, dist.get(4), "Unreachable vertex should have INF distance");
    }

    @Test
    void testDAGShortestPathInvalidSource() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                DAGshortestPath.shortestPath(dag, 10, metrics)
        );
        assertEquals("Source vertex not found in graph", exception.getMessage());
    }
}