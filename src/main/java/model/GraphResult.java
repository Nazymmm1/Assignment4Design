package model;

import metrics.Metrics;
import graph.Graph;

import java.util.List;
import java.util.Map;

public class GraphResult {
    public List<List<Integer>> sccs;
    public List<Integer> topoOrder;
    public Map<Integer, Integer> shortestPaths;
    public List<Integer> longestPath;          // just store the longest path
    public int longestPathLength;              // store the length of the path
    public double tarjanTimeMs;
    public double topoTimeMs;
    public double spTimeMs;

    // Nested class to represent result from DAGlongestPath
    public static class LongestPathResult {
        public List<Integer> path;
        public int length;
    }
}