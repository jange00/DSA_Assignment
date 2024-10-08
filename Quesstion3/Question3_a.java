/*
a.
 Imagine a small community with n houses, numbered 0 to n-1. Some houses have restrictions against becoming 
friends, represented by pairs in the restrictions list. For example, if [0, 1] is in the list, houses 0 and 1 cannot be 
directly or indirectly friends (through common friends).
Residents send friend requests to each other, represented by pairs in the requests list. Your task is to determine if 
each friend request can be accepted based on the current friendship network and the existing restrictions.
 */

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
                rank[i] = 1;    // Initialize rank of each element as 1
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
            
            // Temporarily union houseA and houseB
            UnionFind tempUf = new UnionFind(n);
            for (int i = 0; i < n; i++) {
                tempUf.parent[i] = uf.parent[i];
                tempUf.rank[i] = uf.rank[i];
            }
            tempUf.union(houseA, houseB); // Temporarily union
            
            boolean canBeFriends = true; // Assume the request can be approved initially
            
            // Check each restriction to see if it would be violated by this request
            for (int[] restriction : restrictions) {
                int restrictedA = restriction[0]; // First restricted house
                int restrictedB = restriction[1]; // Second restricted house
                
                // Check if adding this request violates the restriction
                if (tempUf.find(restrictedA) == tempUf.find(restrictedB)) {
                    canBeFriends = false; // If violated, mark request as denied
                    break; // No need to check further restrictions
                }
            }
            
            // If no restrictions were violated, approve the request and union houseA and houseB
            if (canBeFriends) {
                uf.union(houseA, houseB); // Union houseA and houseB in the main Union-Find structure
                results.add("approved"); // Add "approved" to results
            } else {
                results.add("denied"); // Add "denied" to results
            }
        }
        
        return results; // Return the list of approval results
    }
    
    public static void main(String[] args) {
       // Example 1
    int n1 = 5; // Number of houses
    int[][] restrictions1 = { {0, 1}, {1, 2}, {2, 3} }; // Restrictions between houses
    int[][] requests1 = { {0, 4}, {1, 2}, {3, 1}, {3, 4} }; // Friendship requests
    List<String> results1 = processFriendRequests(n1, restrictions1, requests1);
    System.out.println("Example 1 - Final Results: " + results1); // Output: [approved, denied, approved, denied]

    // Example 2
    int n2 = 6; // Number of houses
    int[][] restrictions2 = { {0, 1}, {2, 3}, {4, 5} }; // Restrictions between houses
    int[][] requests2 = { {0, 2}, {1, 5}, {3, 4}, {2, 5} }; // Friendship requests
    List<String> results2 = processFriendRequests(n2, restrictions2, requests2);
    System.out.println("Example 2 - Final Results: " + results2); // Output: [approved, approved, approved, denied]

    // Example 3
    int n3 = 4; // Number of houses
    int[][] restrictions3 = { {0, 1}, {1, 2}, {2, 3}, {0, 3} }; // Restrictions between houses
    int[][] requests3 = { {0, 2}, {1, 3}, {0, 3}, {2, 3} }; // Friendship requests
    List<String> results3 = processFriendRequests(n3, restrictions3, requests3);
    System.out.println("Example 3 - Final Results: " + results3); // Output: [approved, approved, denied, denied]

    // Example 4
    int n4 = 7; // Number of houses
    int[][] restrictions4 = { {0, 1}, {2, 3}, {4, 5}, {6, 0}, {1, 4} }; // Restrictions between houses
    int[][] requests4 = { {0, 2}, {1, 6}, {3, 5}, {2, 5}, {6, 4} }; // Friendship requests
    List<String> results4 = processFriendRequests(n4, restrictions4, requests4);
    System.out.println("Example 4 - Final Results: " + results4); // Output: [approved, approved, approved, denied, denied]
    }
}