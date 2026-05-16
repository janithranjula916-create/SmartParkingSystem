package services;

public class GraphManager {

    private int vertices;
    private int[][] graph;

    // Constructor
    public GraphManager(int vertices) {

        this.vertices = vertices;

        graph = new int[vertices][vertices];
    }

    // Add edge between nodes
    public void addEdge(int source,
            int destination,
            int distance) {

        graph[source][destination] = distance;
        graph[destination][source] = distance;
    }

    // Find minimum distance node
    private int minDistance(int[] distance,
            boolean[] visited) {

        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < vertices; v++) {

            if (!visited[v]
                    && distance[v] < min) {

                min = distance[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    // Dijkstra Algorithm
    public void dijkstra(int startNode) {

        int[] distance = new int[vertices];

        boolean[] visited = new boolean[vertices];

        // Initialize distances
        for (int i = 0; i < vertices; i++) {

            distance[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        distance[startNode] = 0;

        // Find shortest paths
        for (int count = 0; count < vertices - 1; count++) {

            int u = minDistance(
                    distance,
                    visited);

            visited[u] = true;

            for (int v = 0; v < vertices; v++) {

                if (!visited[v]
                        && graph[u][v] != 0
                        && distance[u] != Integer.MAX_VALUE
                        && distance[u]
                                + graph[u][v] < distance[v]) {

                    distance[v] = distance[u]
                            + graph[u][v];
                }
            }
        }

        // Display shortest distances
        System.out.println(
                "\n===== SHORTEST DISTANCES =====");

        for (int i = 0; i < vertices; i++) {

            System.out.println(
                    "From Gate to Slot "
                            + i
                            + " = "
                            + distance[i]
                            + "m");
        }
    }
}