/*
b.
Imagine you're at a movie theater with assigned seating. You have a seating chart represented by an array nums 
where nums[i] represents the seat number at row i. You're looking for two friends to sit together, considering their 
seat preferences and your limitations:
Seating Distance: Your friends prefer to sit close together, with a maximum allowed seat difference of indexDiff. 
Imagine indexDiff = 2, meaning they'd be comfortable within 2 seats of each other (e.g., seats 3 and 5).
Movie Preference: They also want similar movie tastes, requiring a difference in their seat numbers (abs(nums[i] -
nums[j])) to be within valueDiff. Think of valueDiff = 1 as preferring movies with similar ratings (e.g., seats 4 and 
5 for movies rated 4.5 and 5 stars).
Your task is to determine if there are two friends (represented by two indices i and j) who can sit together while 
satisfying both the seating distance and movie preference limitations.

*/

package Question2;

import java.util.*;

public class Question2_b {
    /**
     * Checks if there are two friends who can sit together based on seating distance
     * and movie preference constraints, and prints the pairs that satisfy the conditions.
     * 
     * @param nums An array representing seat numbers.
     * @param indexDiff Maximum allowed seat difference between two friends.
     * @param valueDiff Maximum allowed difference in seat numbers for movie preference.
     * @return true if there exists at least one pair of indices (i, j) such that:
     *         - The absolute difference between i and j is <= indexDiff.
     *         - The absolute difference between nums[i] and nums[j] is <= valueDiff.
     *         false otherwise.
     */
    public static boolean canSitTogether(int[] nums, int indexDiff, int valueDiff) {
        int n = nums.length;
        boolean foundPair = false;

        // Use a TreeMap to store the seat numbers and their indices
        TreeMap<Integer, Integer> seatMap = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            int seat = nums[i];
            int currentIndex = i;
            
            // Remove old seat numbers that are out of the allowable index difference range
            while (!seatMap.isEmpty() && currentIndex - seatMap.firstEntry().getValue() > indexDiff) {
                seatMap.pollFirstEntry();
            }
            
            // Check for valid pairs within the value difference constraint
            Integer lowerBound = seatMap.floorKey(seat + valueDiff);
            Integer upperBound = seatMap.ceilingKey(seat - valueDiff);
            
            if (lowerBound != null && seatMap.get(lowerBound) != currentIndex) {
                System.out.println("Valid pair found: Indices (" + seatMap.get(lowerBound) + ", " + currentIndex + ")");
                System.out.println("  Seats: " + lowerBound + ", " + seat);
                foundPair = true;
            }
            
            if (upperBound != null && seatMap.get(upperBound) != currentIndex) {
                System.out.println("Valid pair found: Indices (" + seatMap.get(upperBound) + ", " + currentIndex + ")");
                System.out.println("  Seats: " + upperBound + ", " + seat);
                foundPair = true;
            }
            
            // Add the current seat to the TreeMap
            seatMap.put(seat, currentIndex);
        }

        return foundPair; // Return whether at least one valid pair was found
    }

    /**
     * Main method to demonstrate the usage of canSitTogether method.
     * 
     * @param args Command-line arguments (not used in this example).
     */
    public static void main(String[] args) {
        // Example 1
        int[] nums1 = {2, 3, 5, 4, 9};
        int indexDiff1 = 2;
        int valueDiff1 = 1;
        boolean result1 = canSitTogether(nums1, indexDiff1, valueDiff1);
        System.out.println("Example 1 - Output: " + result1); // Output: true

        // Example 2
        int[] nums2 = {1, 2, 3, 7, 8, 9};
        int indexDiff2 = 1;
        int valueDiff2 = 1;
        boolean result2 = canSitTogether(nums2, indexDiff2, valueDiff2);
        System.out.println("Example 2 - Output: " + result2); // Output: true

        // Example 3
        int[] nums3 = {10, 20, 30, 40, 50};
        int indexDiff3 = 1;
        int valueDiff3 = 5;
        boolean result3 = canSitTogether(nums3, indexDiff3, valueDiff3);
        System.out.println("Example 3 - Output: " + result3); // Output: false

        // Example 4
        int[] nums4 = {5, 8, 5, 12, 15};
        int indexDiff4 = 3;
        int valueDiff4 = 4;
        boolean result4 = canSitTogether(nums4, indexDiff4, valueDiff4);
        System.out.println("Example 4 - Output: " + result4); // Output: true
    }
}
