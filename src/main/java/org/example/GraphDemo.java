package org.example;

import daglp.DAGlongestPath;
import dagsp.DAGshortestPath;
import graph.Graph;
import metrics.Metrics;
import model.GraphReader;
import model.GraphResult;
import model.GraphWriter;
import scc.Tarjan;
import topo.Kahn;
import util.CondensationGraphBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphDemo {
    public static void main(String[] args) {
        List<GraphReader.GraphWithSource> graphs = GraphReader.readGraphsFromJson("tasks.json");
        List<GraphResult> results = new ArrayList<>();

        for (GraphReader.GraphWithSource gws : graphs) {
            Graph graph = gws.graph;
            Integer source = gws.source;

            GraphResult result = new GraphResult();


            Metrics tarjanMetrics = new Metrics();
            result.sccs = Tarjan.run(graph, tarjanMetrics);
            result.tarjanTimeMs = tarjanMetrics.getExecutionTimeMs();

            // Condensation + Topo
            Graph dag = CondensationGraphBuilder.buildGraphFromScc(graph, result.sccs);
            System.out.println("Condensation DAG:");
            System.out.println(dag);
            Metrics topoMetrics = new Metrics();
            result.topoOrder = Kahn.topoSort(dag, topoMetrics);
            result.topoTimeMs = topoMetrics.getExecutionTimeMs();


            // Shortest paths if source provided
            if (source != null) {
                Metrics spMetrics = new Metrics();
                result.shortestPaths = DAGshortestPath.shortestPath(dag, source, spMetrics);
                result.spTimeMs = spMetrics.getExecutionTimeMs();
            }

            Metrics lpMetrics=new Metrics();
            DAGlongestPath dagLP = new DAGlongestPath();  // create instance
            DAGlongestPath.LongestPathResult lpResult = dagLP.longestPath(dag, source, lpMetrics);

            result.longestPath = lpResult.path;
            result.longestPathLength = lpResult.length;
            results.add(result);
        }


        Map<String, Object> output = new HashMap<>();
        output.put("results", results);

        GraphWriter.writeResultsToJson("output.json", output);

    }
}