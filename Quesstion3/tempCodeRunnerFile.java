package Quesstion3;


import java.util.*;


public class Question3_a {
    // Union-Find data structure with path compression and union by rank
    static class UnionFind {
        int[] parent; // Parent array to track the parent of each element
        int[] rank;   // Rank array to keep track of the rank of each element
        
        // Constructor to initialize Union-Find with size
        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;  // Initialize each element as its own parent
                rank[i] = 1;     // Initialize rank of each element as 1
            }
        }
        
        // Find operation with path compression
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression: set parent to root
            }
            return parent[x]; // Return the root of x
        }
        
        // Union operation with union by rank
        public boolean union(int x, int y) {
            int rootX = find(x); // Find root of x
            int rootY = find(y); // Find root of y
            
            if (rootX == rootY) {
                return false; // If x and y are already in the same set, return false
            }
            
            // Union by rank: attach smaller rank tree under root of higher rank tree
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX; // Attach rootY under rootX
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY; // Attach rootX under rootY
            } else {
                parent[rootY] = rootX; // Attach rootY under rootX (arbitrary choice)
                rank[rootX]++;         // Increment rank of rootX
            }
            
            return true; // Return true to indicate successful union
        }
    }
    
    // Function to process friend requests with given restrictions
    public static List<String> processFriendRequests(int n, int[][] restrictions, int[][] requests) {
        UnionFind uf = new UnionFind(n); // Initialize Union-Find structure for n houses
        List<String> results = new ArrayList<>(); // List to store approval results
        
        // Process each request
        for (int[] request : requests) {
            int houseA = request[0]; // House A in the request
            int houseB = request[1]; // House B in the request
            
            System.out.println("Processing request: [" + houseA + ", " + houseB + "]");
            
            // Find roots of houseA and houseB
            int rootA = uf.find(houseA);
            int rootB = uf.find(houseB);
            System.out.println("Root of " + houseA + " is " + rootA);
            System.out.println("Root of " + houseB + " is " + rootB);
            
            boolean canBeFriends = true; // Assume the request can be approved initially
            
            // Check each restriction to see if it would be violated by this request
            for (int[] restriction : restrictions) {
                int restrictedA = restriction[0]; // First restricted house
                int restrictedB = restriction[1]; // Second restricted house
                
                // Find roots of restrictedA and restrictedB
                int rootRestrictedA = uf.find(restrictedA);
                int rootRestrictedB = uf.find(restrictedB);
                
                System.out.println("Checking restriction: [" + restrictedA + ", " + restrictedB + "]");
                System.out.println("Root of restricted house " + restrictedA + " is " + rootRestrictedA);
                System.out.println("Root of restricted house " + restrictedB + " is " + rootRestrictedB);
                
                // Check if the current request violates this restriction
                if ((rootA == rootRestrictedA && rootB == rootRestrictedB) || 
                    (rootA == rootRestrictedB && rootB == rootRestrictedA)) {
                    canBeFriends = false; // If violated, mark request as denied
                    System.out.println("Restriction violated. Request denied.");
                    break; // No need to check further restrictions
                }
            }
            
            // If no restrictions were violated, approve the request and union houseA and houseB
            if (canBeFriends) {
                uf.union(houseA, houseB); // Union houseA and houseB
                results.add("approved"); // Add "approved" to results
                System.out.println("Request approved.");
            } else {
                results.add("denied"); // Add "denied" to results
            }
            
            System.out.println(); // Print a newline for clarity
        }
        
        return results; // Return the list of approval results
    }
    
    public static void main(String[] args) {
        int n = 5; // Number of houses
        int[][] restrictions = { {0, 1}, {1, 2}, {2, 3} }; // Restrictions between houses
        int[][] requests = { {0, 4}, {1, 2}, {3, 1}, {3, 4} }; // Friendship requests
        
        // Process friend requests with given restrictions and print final results
        List<String> results = processFriendRequests(n, restrictions, requests);
        System.out.println("Final Results: " + results); // Output: [approved, denied, approved, denied]
        
        // Additional example
        int[][] restrictions2 = { {0, 2}, {2, 4}, {1, 3} }; // New restrictions
        int[][] requests2 = { {0, 1}, {2, 3}, {3, 4}, {1, 4} }; // New friendship requests
        
        // Process friend requests with the new restrictions and print final results
        List<String> results2 = processFriendRequests(n, restrictions2, requests2);
        System.out.println("Additional Example - Final Results: " + results2); // Expected Output: [approved, approved, denied, approved]
    }
}
