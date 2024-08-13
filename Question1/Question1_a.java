/*
 a)
Imagine you're a scheduling officer at a university with n classrooms numbered 0 to n-1. Several different courses 
require classrooms throughout the day, represented by an array of classes classes[i] = [starti, endi], where starti is 
the start time of the class and endi is the end time (both in whole hours). Your goal is to assign each course to a 
classroom while minimizing disruption and maximizing classroom utilization. Here are the rules:
• Priority Scheduling: Classes with earlier start times have priority when assigning classrooms. If multiple 
classes start at the same time, prioritize the larger class (more students).
• Dynamic Allocation: If no classroom is available at a class's start time, delay the class until a room 
becomes free. The delayed class retains its original duration.
• Room Release: When a class finishes in a room, that room becomes available for the next class with the 
highest priority (considering start time and size).
Your task is to determine which classroom held the most classes throughout the day. If multiple classrooms are 
tied, return the one with the lowest number.
 */


 import java.util.Arrays;
 import java.util.PriorityQueue;
 
 public class Question1_a {
     public static int mostVisitedRoom(int n, int[][] classes) {
         // Sort classes based on start time, if start times are equal, sort by class size in descending order
         Arrays.sort(classes, (a, b) -> a[0] == b[0] ? b[1] - b[0] - (a[1] - a[0]) : a[0] - b[0]);
 
         // Priority queue to track the earliest available room (sorted by end time)
         PriorityQueue<int[]> rooms = new PriorityQueue<>((a, b) -> a[1] - b[1]);
 
         // Initialize all rooms as available at time 0
         for (int i = 0; i < n; i++) {
             rooms.offer(new int[]{i, 0});
         }
 
         // Array to count the number of classes held in each room
         int[] count = new int[n];
 
         // Process each class
         for (int[] cl : classes) {
             int startTime = cl[0];
             int endTime = cl[1];
 
             // Get the room that becomes available the earliest
             int[] room = rooms.poll();
 
             // If the room is available by the start time, assign the class
             if (room[1] <= startTime) {
                 room[1] = endTime; // Update the room's end time to this class's end time
             } else {
                 // If the room is not available, delay the class until the room is free
                 startTime = room[1];
                 room[1] = startTime + (endTime - cl[0]); // Adjust the end time accordingly
             }
 
             // Increase the count for this room
             count[room[0]]++;
 
             // Re-add the room to the priority queue with its updated end time
             rooms.offer(room);
         }
 
         // Find the room with the maximum count of classes held
         int maxCount = 0;
         int mostUsedRoom = 0;
         for (int i = 0; i < n; i++) {
             if (count[i] > maxCount) {
                 maxCount = count[i];
                 mostUsedRoom = i;
             }
         }
 
         return mostUsedRoom;
     }
 
     public static void main(String[] args) {
         int n = 2;
         int[][] classes = {{0, 10}, {1, 5}, {2, 7}, {3, 4}};
         System.out.println(mostVisitedRoom(n, classes)); // Output: 0
 
         n = 3;
         classes = new int[][]{{1, 20}, {2, 10}, {3, 5}, {4, 9}, {6, 8}};
         System.out.println(mostVisitedRoom(n, classes)); // Output: 0 or 1 depending on schedule order
     }
 }
 