
// b)
// Imagine you're on a challenging hiking trail represented by an array nums, where each element represents the 
// altitude at a specific point on the trail. You want to find the longest consecutive stretch of the trail you can hike 
// while staying within a certain elevation gain limit (at most k).
// Constraints:
// You can only go uphill (increasing altitude).
// The maximum allowed elevation gain between two consecutive points is k.
// Goal: Find the longest continuous hike you can take while respecting the elevation gain limit.
// Examples:
// Input:
// Trail: [4, 2, 1, 4, 3, 4, 5, 8, and 15], Elevation gain limit (K): 3
// Output: 5
// Explanation
// Longest hike: [1, 3, 4, 5, 8] (length 5) - You can climb from 1 to 3 (gain 2), then to 4 (gain 1), and so on, all within 
// the limit.
// Invalid hike: [1, 3, 4, 5, 8, 15] - The gain from 8 to 15 (7) exceeds the limit.


package Question5;


public class Question5_b {
    public int longestHike(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) return 0; // If the array is empty, the longest hike length is 0.

        int maxLen = 0; // Initialize maxLen to 0 because we start with no valid hike.
        int start = 0; // Start index of the current valid subsequence.

        for (int i = 1; i < n; i++) {
            // Check if the elevation gain between nums[i-1] and nums[i] is within the limit k and that we're going uphill.
            if (nums[i] > nums[i - 1] && nums[i] - nums[i - 1] <= k) {
                // If within limit, update maxLen to be the maximum of current maxLen or current subsequence length.
                maxLen = Math.max(maxLen, i - start + 1);
            } else {
                // If not within limit or it's not uphill, reset start index to i.
                // Since we're not in a valid segment, move `start` to `i` only if it's a potential start of a new hike.
                start = i;
                // Consider the case where the new hike starts at `i`
                if (i > 0 && nums[i] > nums[i - 1] && nums[i] - nums[i - 1] <= k) {
                    start = i - 1;
                }
            }
        }

        return maxLen; // Return the maximum length of a valid hike found.
    }

    public static void main(String[] args) {
        Question5_b trail = new Question5_b();
        int[] nums = {4, 2, 1, 4, 3, 4, 5, 8, 15};
        int k = 3;
        System.out.println("Longest hike: " + trail.longestHike(nums, k)); // Output should be 5
    }
}