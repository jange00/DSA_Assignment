/*
 Imagine you are managing a bus service with passengers boarding at various stops along the route. Your task is to 
optimize the boarding process by rearranging the order of passengers at intervals of k stops, where k is a positive 
integer. If the total number of passengers is not a multiple of k, then the remaining passengers at the end of the route 
should maintain their original order.
 */

package Quesstion3;

import java.util.LinkedList;
import java.util.List;

public class Question3_b {
     // Function to optimize the boarding process by reversing passengers in groups of size k
     public static List<Integer> optimizeBoarding(List<Integer> passengers, int k) {
        int n = passengers.size(); // Total number of passengers

        // Loop through the list of passengers in increments of k
        for (int i = 0; i < n; i += k) {
            int left = i; // Start index of the current sublist
            int right = Math.min(i + k - 1, n - 1); // End index of the current sublist, ensuring it doesn't exceed the list bounds

            // Check if the current sublist ends before the last element to ensure we only reverse complete sublists
            if ((n - 1) != right) {
                // Reverse the elements in the current sublist
                while (left < right) {
                    int temp = passengers.get(left); // Temporarily store the element at the left index
                    passengers.set(left, passengers.get(right)); // Set the element at the left index to the element at the right index
                    passengers.set(right, temp); // Set the element at the right index to the temporarily stored element
                    left++; // Move the left index one step to the right
                    right--; // Move the right index one step to the left
                }
            }
        }

        return passengers; // Return the modified list of passengers
    }

    public static void main(String[] args) {
        // Test case 1
        List<Integer> passengers1 = new LinkedList<>(List.of(1, 2, 3, 4, 5)); // Create a list of passengers
        int k1 = 2; // Set the group size to 2
        System.out.println("Input: " + passengers1 + ", k = " + k1); // Print the input list and group size
        System.out.println("Output: " + optimizeBoarding(passengers1, k1)); // Print the output list after optimization
        
        // Test case 2
        List<Integer> passengers2 = new LinkedList<>(List.of(1, 2, 3, 4, 5)); // Create another list of passengers
        int k2 = 3; // Set the group size to 3
        System.out.println("Input: " + passengers2 + ", k = " + k2); // Print the input list and group size
        System.out.println("Output: " + optimizeBoarding(passengers2, k2)); // Print the output list after optimization

        // Additional test case 3
        List<Integer> passengers3 = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6)); // Create a list of passengers
        int k3 = 4; // Set the group size to 4
        System.out.println("Input: " + passengers3 + ", k = " + k3); // Print the input list and group size
        System.out.println("Output: " + optimizeBoarding(passengers3, k3)); // Print the output list after optimization
        
        // Additional test case 4
        List<Integer> passengers4 = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6, 7)); // Create a list of passengers
        int k4 = 3; // Set the group size to 3
        System.out.println("Input: " + passengers4 + ", k = " + k4); // Print the input list and group size
        System.out.println("Output: " + optimizeBoarding(passengers4, k4)); // Print the output list after optimization
        
        // Additional test case 5
        List<Integer> passengers5 = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8)); // Create a list of passengers
        int k5 = 5; // Set the group size to 5
        System.out.println("Input: " + passengers5 + ", k = " + k5); // Print the input list and group size
        System.out.println("Output: " + optimizeBoarding(passengers5, k5)); // Print the output list after optimization
    }
}
