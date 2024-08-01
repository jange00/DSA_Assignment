/*
 a)
Imagine you're a city planner tasked with improving the road network between key locations (nodes) represented 
by numbers 0 to n-1. Some roads (edges) have known travel times (positive weights), while others are under 
construction (weight -1). Your goal is to modify the construction times on these unbuilt roads to achieve a specific 
travel time (target) between two important locations (from source to destination).
Constraints:
You can only modify the travel times of roads under construction (-1 weight).
The modified times must be positive integers within a specific range. [1, 2 * 10^9]
You need to find any valid modification that achieves the target travel time.
Examples:
Input:
City: 5 locations
Roads: [[4, 1,-1], [2, 0,-1],[0,3,-1],[4,3,-1]]
Source: 0, Destination: 1, Target time: 5 minutes
Output: [[4,1,1],[2,0,1],[0,3,3],[4,3,1]]
Solution after possible modification
 */

package Question4;

import java.util.*;

public class Question4_a {
     // Function to modify road travel times to achieve a specific target travel time from source to destination
     public static List<int[]> modifyRoads(int n, int[][] roads, int source, int destination, int targetTime) {
        // Create an adjacency list to represent the graph
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Build the graph using the given roads
        for (int[] road : roads) {
            graph[road[0]].add(road); // Add road to the adjacency list for the start node
            graph[road[1]].add(new int[]{road[1], road[0], road[2]}); // Add road in the opposite direction
        }

        // Debugging: Print the initial graph representation
        System.out.println("Initial Graph:");
        for (int i = 0; i < n; i++) {
            System.out.print(i + ": ");
            for (int[] edge : graph[i]) {
                System.out.print(Arrays.toString(edge) + " ");
            }
            System.out.println();
        }

        // Initialize the distance array with infinity, except for the source which is 0
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // Priority queue to implement Dijkstra's algorithm
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{source, 0});

        // Array to store the previous node in the shortest path
        int[] prev = new int[n];
        Arrays.fill(prev, -1);

        // Dijkstra's algorithm to find the shortest path from source to destination
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int d = curr[1];

            if (d > dist[u]) continue; // Skip if the current distance is greater than the recorded distance

            for (int[] edge : graph[u]) {
                int v = edge[1];
                int weight = edge[2] == -1 ? 1 : edge[2]; // If the road is under construction, assign initial weight of 1
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }

        // Debugging: Print the shortest distances from the source
        System.out.println("Shortest distances from source:");
        for (int i = 0; i < n; i++) {
            System.out.println("Node " + i + ": " + dist[i]);
        }

        // Calculate the total construction time needed to achieve the target travel time
        int totalConstructionTime = targetTime - dist[destination];

        // Modify the roads to achieve the target travel time
        for (int[] road : roads) {
            if (road[2] == -1) {
                road[2] = 1; // Assign initial weight of 1 to under construction roads
            }
        }

        // Debugging: Print the modified roads
        System.out.println("Modified roads:");
        for (int[] road : roads) {
            System.out.println(Arrays.toString(road));
        }

        // Adjust the construction times to meet the target time
        for (int i = 0; i < roads.length; i++) {
            if (roads[i][2] == -1) {
                roads[i][2] = totalConstructionTime; // Assign the remaining construction time
                break;
            }
        }

        // Debugging: Print the final roads
        System.out.println("Final roads:");
        for (int[] road : roads) {
            System.out.println(Arrays.toString(road));
        }

        // Return the modified roads as a list of int arrays
        return Arrays.asList(roads);
    }

    public static void main(String[] args) {
        // Test case 1
        int n1 = 5;
        int[][] roads1 = {{4, 1, -1}, {2, 0, -1}, {0, 3, -1}, {4, 3, -1}};
        int source1 = 0;
        int destination1 = 1;
        int targetTime1 = 5;
        System.out.println("Test Case 1:");
        List<int[]> result1 = modifyRoads(n1, roads1, source1, destination1, targetTime1);
        for (int[] road : result1) {
            System.out.println(Arrays.toString(road));
        }

        // Test case 2
        int n2 = 6;
        int[][] roads2 = {{0, 1, -1}, {1, 2, 3}, {2, 3, -1}, {3, 4, 2}, {4, 5, -1}, {5, 0, -1}};
        int source2 = 0;
        int destination2 = 3;
        int targetTime2 = 10;
        System.out.println("Test Case 2:");
        List<int[]> result2 = modifyRoads(n2, roads2, source2, destination2, targetTime2);
        for (int[] road : result2) {
            System.out.println(Arrays.toString(road));
        }

        // Test case 3
        int n3 = 4;
        int[][] roads3 = {{0, 1, 4}, {1, 2, -1}, {2, 3, 5}, {3, 0, -1}};
        int source3 = 0;
        int destination3 = 2;
        int targetTime3 = 7;
        System.out.println("Test Case 3:");
        List<int[]> result3 = modifyRoads(n3, roads3, source3, destination3, targetTime3);
        for (int[] road : result3) {
            System.out.println(Arrays.toString(road));
        }
    }
}


