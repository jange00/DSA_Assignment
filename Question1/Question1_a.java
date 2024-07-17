// a)
// Imagine you're a scheduling officer at a university with n classrooms numbered 0 to n-1. Several different courses 
// require classrooms throughout the day, represented by an array of classes classes[i] = [starti, endi], where starti is 
// the start time of the class and endi is the end time (both in whole hours). Your goal is to assign each course to a 
// classroom while minimizing disruption and maximizing classroom utilization. Here are the rules:
// • Priority Scheduling: Classes with earlier start times have priority when assigning classrooms. If multiple 
// classes start at the same time, prioritize the larger class (more students).
// • Dynamic Allocation: If no classroom is available at a class's start time, delay the class until a room 
// becomes free. The delayed class retains its original duration.
// • Room Release: When a class finishes in a room, that room becomes available for the next class with the 
// highest priority (considering start time and size).
// Your task is to determine which classroom held the most classes throughout the day. If multiple classrooms are 
// tied, return the one with the lowest number.


import java.util.Arrays;
import java.util.PriorityQueue;

public class Question1_a {
    public static int mostVisitedRoom(int n, int[][] classes) {
        // Create a priority queue to store the classes
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        for (int[] cl : classes) {
            pq.offer(cl);
        }

        // Create an array to store the end time of each room
        int[] endTime = new int[n];
        Arrays.fill(endTime, -1);

        // Create an array to store the count of classes in each room
        int[] count = new int[n];

        // Process the classes
        while (!pq.isEmpty()) {
            int[] cl = pq.poll();
            int startTime = cl[0];
            int endTimeCl = cl[1];

            // Find the first available room
            int room = -1;
            for (int i = 0; i < n; i++) {
                if (endTime[i] <= startTime) {
                    room = i;
                    break;
                }
            }

            // If no room is available, delay the class
            if (room == -1) {
                int minEndTime = Integer.MAX_VALUE;
                for (int i = 0; i < n; i++) {
                    if (endTime[i] < minEndTime) {
                        minEndTime = endTime[i];
                        room = i;
                    }
                }
                startTime = minEndTime;
            }

            // Update the end time and count of the room
            endTime[room] = endTimeCl;
            count[room]++;

            // Re-add the class to the priority queue if it's delayed
            if (startTime > cl[0]) {
                pq.offer(new int[]{startTime, endTimeCl});
            }
        }

        // Find the room with the maximum count
        int maxCount = Integer.MIN_VALUE;
        int room = -1;
        for (int i = 0; i < n; i++) {
            if (count[i] > maxCount) {
                maxCount = count[i];
                room = i;
            }
        }

        return room;
    }

    public static void main(String[] args) {
        int n = 2;
        int[][] classes = {{0, 10}, {1, 5}, {2, 7}, {3, 4}};
        System.out.println(mostVisitedRoom(n, classes)); // Output: 0 or 1 depending on the logic

        n = 3;
        classes = new int[][]{{1, 20}, {2, 10}, {3, 5}, {4, 9}, {6, 8}};
        System.out.println(mostVisitedRoom(n, classes)); // Output: 0 or 1 depending on the logic
    }
}

