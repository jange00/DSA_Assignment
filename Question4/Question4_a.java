// a)
// Imagine you're a city planner tasked with improving the road network between key locations (nodes) represented 
// by numbers 0 to n-1. Some roads (edges) have known travel times (positive weights), while others are under 
// construction (weight -1). Your goal is to modify the construction times on these unbuilt roads to achieve a specific 
// travel time (target) between two important locations (from source to destination).
// Constraints:
// You can only modify the travel times of roads under construction (-1 weight).
// The modified times must be positive integers within a specific range. [1, 2 * 10^9]
// You need to find any valid modification that achieves the target travel time.
// Examples:
// Input:
// City: 5 locations
// Roads: [[4, 1,-1], [2, 0,-1],[0,3,-1],[4,3,-1]]
// Source: 0, Destination: 1, Target time: 5 minutes
// Output: [[4,1,1],[2,0,1],[0,3,3],[4,3,1]]
// Solution after possible modification

package Question4;

import java.util.*;

public class Question4_a {
    // Method to modify the road network
    public int[][] modifyRoadNetwork(int[][] roads, int source, int destination, int targetTime) {
        // Determine the number of nodes
        int n = 0;
        for (int[] road : roads) {
            n = Math.max(n, Math.max(road[0], road[1]) + 1);
        }

        // Step 1: Build the graph using an adjacency list
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int w = road[2];
            if (w == -1) {
                w = Integer.MAX_VALUE; // Unknown travel time set to a very large number
            }
            graph.get(u).add(new int[]{v, w});
            graph.get(v).add(new int[]{u, w}); // Add both directions for undirected road
        }

        // Step 2: Run Dijkstra's algorithm to find the shortest path from source to all other nodes
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, source});
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int dist = curr[0];
            int node = curr[1];
            if (dist > distances[node]) {
                continue;
            }
            for (int[] neighbor : graph.get(node)) {
                int newDist = dist + (neighbor[1] == Integer.MAX_VALUE ? 0 : neighbor[1]);
                if (newDist < distances[neighbor[0]]) {
                    distances[neighbor[0]] = newDist;
                    pq.offer(new int[]{newDist, neighbor[0]});
                }
            }
        }

        // Step 3: Modify the graph to meet the target time if necessary
        if (distances[destination] < targetTime) {
            return new int[][]{}; // Not possible to achieve the target time
        }
        for (int[] road : roads) {
            if (road[2] == -1) {
                int u = road[0];
                int v = road[1];
                int w = targetTime - distances[u] - distances[v];
                if (w >= 1 && w <= 2 * (int) Math.pow(10, 9)) {
                    road[2] = w; // Set the travel time to meet the target
                }
            }
        }

        return roads; // Return the modified roads
    }

    // Main method to test the function
    public static void main(String[] args) {
        Question4_a planner = new Question4_a();
        int[][] roads = {{4, 1, -1}, {2, 0, -1}, {0, 3, -1}, {4, 3, -1}};
        int source = 0;
        int destination = 1;
        int targetTime = 5;
        int[][] modifiedRoads = planner.modifyRoadNetwork(roads, source, destination, targetTime);
        for (int[] road : modifiedRoads) {
            System.out.println(Arrays.toString(road)); // Print each modified road
        }
    }
}


