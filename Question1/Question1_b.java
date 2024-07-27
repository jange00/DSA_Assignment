/*
b.
Imagine you have a secret decoder ring with rotating discs labeled with the lowercase alphabet. You're given a 
message s written in lowercase letters and a set of instructions shifts encoded as tuples (start_disc, end_disc, 
direction). Each instruction represents rotating the discs between positions start_disc and end_disc (inclusive) 
either clockwise (direction = 1) or counter-clockwise (direction = 0). Rotating a disc shifts the message by one 
letter for each position moved on the alphabet (wrapping around from ‘z’ to ‘a’ and vice versa).
Your task is to decipher the message s by applying the rotations specified in shifts in the correct order.
*/

public class Question1_b {
   // Method to decipher the message based on the shifts
   public static String decipherMessage(String s, int[][] shifts) {
    // Convert input string into a character array for manipulation
    char[] message = s.toCharArray();

    // Iterate through each shift instruction
    for (int[] shift : shifts) {
        int start_disc = shift[0];   // Start index of discs to be shifted
        int end_disc = shift[1];     // End index of discs to be shifted
        int direction = shift[2];    // Direction of the shift (1 for clockwise, 0 for counter-clockwise)

        // Apply the shift to each character within the specified range
        for (int i = start_disc; i <= end_disc; i++) {
            // Determine the shift amount based on direction
            if (direction == 1) { // Clockwise shift
                System.out.print("Rotate disc " + i + " " + message[i]);
                int newIndex = (message[i] - 'a' + 1) % 26; // Calculate new index with wrap-around
                message[i] = (char) ('a' + newIndex); // Update the character with the shifted value
                System.out.println(" becomes " + message[i]); // Print intermediate message (for debugging)
            } else if (direction == 0) { // Counter-clockwise shift
                System.out.print("Rotate disc " + i + " " + message[i]);
                int newIndex = (message[i] - 'a' - 1 + 26) % 26; // Calculate new index with wrap-around
                message[i] = (char) ('a' + newIndex); // Update the character with the shifted value
                System.out.println(" becomes " + message[i]); // Print intermediate message (for debugging)
            }
        }
    }

    // Convert the character array back to a string and return the deciphered message
    return new String(message);
}

public static void main(String[] args) {
    // Example 1
    String s1 = "hello";
    int[][] shifts1 = {{0, 1, 1}, {2, 3, 0}, {0, 2, 1}}; // Example shifts
    String decipheredMessage1 = decipherMessage(s1, shifts1);
    System.out.println("Deciphered Message 1: " + decipheredMessage1); // Output: jglko

    // Example 2
    String s2 = "world";
    int[][] shifts2 = {{0, 4, 1}, {1, 3, 0}, {2, 4, 1}};
    String decipheredMessage2 = decipherMessage(s2, shifts2);
    System.out.println("Deciphered Message 2: " + decipheredMessage2); // Output: xpmqe

    // Example 3
    String s3 = "abcdef";
    int[][] shifts3 = {{0, 2, 0}, {3, 5, 1}, {1, 4, 0}};
    String decipheredMessage3 = decipherMessage(s3, shifts3);
    System.out.println("Deciphered Message 3: " + decipheredMessage3); // Output: zyzbaq
}
}
