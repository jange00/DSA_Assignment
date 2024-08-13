// a) Implement travelling a salesman problem using hill climbing algorithm.


package Question5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Question5_a {
    // Class to represent a city with x and y coordinates
    static class City {
        int x, y;

        public City(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Method to calculate the Euclidean distance between this city and another city
        public double distance(City other) {
            return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
        }
    }

    // Main method to execute the hill climbing algorithm and print the results
    public static void main(String[] args) {
        // Created a list of cities with their coordinates
        List<City> cities = new ArrayList<>();
        cities.add(new City(0, 0));
        cities.add(new City(10, 0));
        cities.add(new City(5, 5));
        cities.add(new City(0, 10));
        cities.add(new City(10, 10));
        cities.add(new City(7, 7));

        // Find the best route using hill climbing algorithm with 1000 iterations
        List<City> bestRoute = hillClimbing(cities, 1000);
        // Calculate the total distance of the best route found
        double bestDistance = totalDistance(bestRoute);

        // Print the best route
        System.out.println("Best Route:");
        for (City city : bestRoute) {
            System.out.print("(" + city.x + ", " + city.y + ") -> ");
        }
        // Print the return to the starting city
        System.out.println("(" + bestRoute.get(0).x + ", " + bestRoute.get(0).y + ")");
        // Print the total distance of the best route
        System.out.println("Best Distance: " + bestDistance);
    }

    // Hill climbing algorithm to find a better route
    public static List<City> hillClimbing(List<City> cities, int maxIterations) {
        // Ensure there are at least two cities to visit
        if (cities == null || cities.size() < 2) {
            throw new IllegalArgumentException("There should be at least two cities.");
        }

        // Initialize the current route and calculate its total distance
        List<City> currentRoute = new ArrayList<>(cities);
        double currentDistance = totalDistance(currentRoute);

        // Create a Random object for reproducibility
        Random rand = new Random();

        // Perform the hill climbing algorithm for a specified number of iterations
        for (int i = 0; i < maxIterations; i++) {
            // Create a neighbor route by swapping two random cities
            List<City> neighborRoute = new ArrayList<>(currentRoute);
            int i1, i2;
            do {
                i1 = rand.nextInt(currentRoute.size());
                i2 = rand.nextInt(currentRoute.size());
            } while (i1 == i2); // Ensure the indices are different

            Collections.swap(neighborRoute, i1, i2); // Swap the cities
            double neighborDistance = totalDistance(neighborRoute); // Calculate the distance of the neighbor route

            // If the neighbor route is shorter, update the current route
            if (neighborDistance < currentDistance) {
                currentRoute = neighborRoute;
                currentDistance = neighborDistance;
            }
        }

        // Return the best route found
        return currentRoute;
    }

    // Method to calculate the total distance of a given route
    public static double totalDistance(List<City> route) {
        double distance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            distance += route.get(i).distance(route.get(i + 1)); // Sum the distances between consecutive cities
        }
        // Add the distance from the last city back to the first city
        distance += route.get(route.size() - 1).distance(route.get(0));
        return distance;
    }
}