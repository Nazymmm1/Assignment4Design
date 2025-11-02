# Assignment 4 — SCCs, Topological Sort, and DAG Shortest/Longest Paths

**Author:** Kurmanbayeva Nazym, SE-2435_  
**Course:** Algorithms and Data Structures  
**Date:** November 2025  

---


## Implemented Components

### 1. SCC Detection — *Tarjan’s Algorithm*
- Finds all strongly connected components in a directed graph.  
- Uses DFS traversal and low-link indices.  
- Outputs each SCC as a list of vertices.  
- Metrics: DFS visits, edges processed, and runtime (ms).

### 2. Condensation Graph Builder
- Converts the list of SCCs into a **condensation DAG**.  
- Each SCC becomes a single node.  
- Edges between components are preserved if they connect different SCCs.

### 3. Topological Sort — *Kahn’s Algorithm*
- Computes a valid topological ordering of the condensation DAG.  
- Used as the base for shortest and longest path computations.  
- Metrics: queue operations, processed edges, runtime.

### 4. Shortest Path in DAG
- Implemented via **Dynamic Programming** using topological order.  
- Supports weighted edges (weights represent task durations).  
- Outputs the shortest distance from a given source and reconstructs one optimal path.

### 5. Longest Path in DAG
- Implemented by inverting edge weights or by max-DP over topological order.  
- Identifies the **critical path** and its total length.

### 6. Metrics and Timing
- Common `Metrics` interface measures runtime and key operation counts.  
- Time measured using `System.nanoTime()` (converted to milliseconds).  
- Algorithms run on multiple datasets with increasing graph size and density.

---

## Dataset Summary

A total of **9 graphs** were generated and tested:

| Category | Nodes | Description | Edges | Cyclic |
|-----------|--------|-------------|--------|---------|
| Small | 6 | Pure DAG, sparse | 5 | No |
| Small | 8 | 1 cycle (1→2→3→1) | 8 | Yes |
| Small | 10 | Pure DAG, medium density | 12 | No |
| Medium | 12 | 2 SCCs (2→3→4→2) and (5→6→7→5) | 13 | Yes |
| Medium | 15 | Pure DAG, sparse | 16 | No |
| Medium | 18 | 2 SCCs (3→4→5→3), (6→7→8→6) | 20 | Yes |
| Large | 25 | Pure DAG, tree-like | 27 | No |
| Large | 35 | Pure DAG, medium density | 38 | No |
| Large | 50 | Multiple cycles (3 SCCs) | 62 | Yes |

All graphs use edge weights between **1–7**, representing task durations.

---

## 📈 Experimental Results

| Graph | Nodes | Edges | SCCs | Has Cycles | Tarjan (ms) | Topo Sort (ms) | Shortest Path (ms) | Longest Path Length | Reachable from Source |
|--------|-------|--------|--------|--------------|----------------|------------------|--------------------|----------------------|------------------------|
| 1 | 6 | 5 | 6 | No | 0.064 | 0.409 | 5.192 | 4 | 2 nodes |
| 2 | 8 | 8 | 6 | Yes | 0.017 | 0.016 | 0.122 | 1 | 2 nodes |
| 3 | 10 | 12 | 10 | No | 0.016 | 0.028 | 0.025 | 0 | 1 node |
| 4 | 12 | 13 | 8 | Yes | 0.016 | 0.023 | 0.027 | 0 | 1 node |
| 5 | 15 | 16 | 15 | No | 0.031 | 0.030 | 0.036 | 0 | 1 node |
| 6 | 18 | 20 | 14 | Yes | 0.026 | 0.026 | 0.030 | 0 | 1 node |
| 7 | 25 | 27 | 25 | No | 0.032 | 0.073 | 0.051 | 0 | 1 node |
| 8 | 35 | 38 | 35 | No | 0.053 | 0.069 | 0.083 | 0 | 1 node |
| 9 | 50 | 62 | 40 | Yes | 0.056 | 0.085 | 0.203 | 4 | 3 nodes |


<img width="497" height="604" alt="image" src="https://github.com/user-attachments/assets/2be9cff2-5e99-44cb-b3f6-c61ed2397668" />

---

## 🔍 Observations and Analysis

### Algorithm correctness
- Tarjan correctly groups all cycles into SCCs:
  - Graph 2: (1→2→3→1)
  - Graph 4: (2→3→4→2), (5→6→7→5)
  - Graph 6: (3→4→5→3), (6→7→8→6)
  - Graph 9: multiple nested cycles  
- Condensation graphs produced proper DAGs.
- Topological order consistent with SCC compression.
- Shortest path algorithm handled unreachable nodes correctly (marked as 1000000000).

---

## Conclusions

- **SCC detection** (Tarjan) is critical for identifying and compressing cycles before scheduling.
- **Condensation graphs** allow topological sorting of complex cyclic systems.
- **Kahn’s topological sort** performs consistently across all datasets and scales well.
- **Shortest path in DAGs** provides minimal completion time scheduling.
- **Longest path (critical path)** helps find the maximum dependency chain for timing analysis.
- All algorithms show **linear scalability** and **high stability** even for larger inputs.
- In a Smart City context, this enables reliable scheduling of maintenance or monitoring tasks where dependency constraints exist.

---


