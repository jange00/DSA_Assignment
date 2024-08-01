package Question4;
// TreeNode class represents a node in the binary tree
class TreeNode {
    int val;        // Value of the node (magical coin)
    TreeNode left;  // Left child of the node
    TreeNode right; // Right child of the node

    // Constructor to initialize the node with a value
    TreeNode(int x) {
        val = x;
    }
}

// Result class stores the result of each subtree traversal
class Result {
    int maxSum; // Maximum sum of coins in the subtree
    int min;    // Minimum value in the subtree
    int max;    // Maximum value in the subtree

    // Constructor to initialize the result with maxSum, min, and max values
    Result(int maxSum, int min, int max) {
        this.maxSum = maxSum;
        this.min = min;
        this.max = max;
    }
}

// Question4_b class finds the largest magical grove in the binary tree
public class Question4_b {

    // Method to find the largest magical grove sum starting from the root node
    public int findLargestMagicalGrove(TreeNode root) {
        Result result = findLargestMagicalGroveHelper(root); // Call helper method to find the result
        return result.maxSum; // Return the maximum sum found
    }

    // Helper method to recursively find the largest magical grove starting from a given node
    private Result findLargestMagicalGroveHelper(TreeNode node) {
        // Base case: If the node is null (no subtree), return a result with 0 sum, and extreme min and max values
        if (node == null) {
            return new Result(0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        // Recursively find results for the left and right subtrees
        Result left = findLargestMagicalGroveHelper(node.left);
        Result right = findLargestMagicalGroveHelper(node.right);

        // Check if the current node itself can form a valid magical grove
        if (node.val > left.max && node.val < right.min) {
            // Calculate the sum of coins in the subtree rooted at the current node
            int sum = node.val + left.maxSum + right.maxSum;
            // Determine the minimum and maximum values in the subtree rooted at the current node
            int min = Math.min(node.val, left.min);
            int max = Math.max(node.val, right.max);
            // Return a new result with the calculated sum, min, and max values
            return new Result(sum, min, max);
        } else {
            // If the current node does not form a valid grove, return the subtree with the maximum sum found so far
            int maxSum = Math.max(left.maxSum, right.maxSum);
            return new Result(maxSum, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }

    // Main method to demonstrate usage with example binary trees
    public static void main(String[] args) {
        // Example 1: Create an example binary tree
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(4);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(2);
        root1.left.right = new TreeNode(4);
        root1.right.left = new TreeNode(2);
        root1.right.right = new TreeNode(5);
        root1.right.right.left = new TreeNode(4);
        root1.right.right.right = new TreeNode(6);

        // Create an instance of Question4_b
        Question4_b finder = new Question4_b();
        // Find the largest magical grove sum starting from the root
        int largestGroveSum1 = finder.findLargestMagicalGrove(root1);
        // Print the largest magical grove sum found
        System.out.println("Largest Magical Grove Sum (Example 1): " + largestGroveSum1); // Output: 20

        // Example 2: Create another example binary tree
        TreeNode root2 = new TreeNode(10);
        root2.left = new TreeNode(5);
        root2.right = new TreeNode(15);
        root2.left.left = new TreeNode(1);
        root2.left.right = new TreeNode(8);
        root2.right.right = new TreeNode(7);

        // Find the largest magical grove sum starting from the root
        int largestGroveSum2 = finder.findLargestMagicalGrove(root2);
        // Print the largest magical grove sum found
        System.out.println("Largest Magical Grove Sum (Example 2): " + largestGroveSum2); // Output: 24

        // Example 3: Create yet another example binary tree
        TreeNode root3 = new TreeNode(5);
        root3.left = new TreeNode(1);
        root3.right = new TreeNode(10);
        root3.right.left = new TreeNode(7);
        root3.right.right = new TreeNode(15);
        root3.right.left.left = new TreeNode(6);
        root3.right.left.right = new TreeNode(8);

        // Find the largest magical grove sum starting from the root
        int largestGroveSum3 = finder.findLargestMagicalGrove(root3);
        // Print the largest magical grove sum found
        System.out.println("Largest Magical Grove Sum (Example 3): " + largestGroveSum3); // Output: 46
    }
}
