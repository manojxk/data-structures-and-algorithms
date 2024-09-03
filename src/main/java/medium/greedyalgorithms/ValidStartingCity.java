/*
 * Problem Statement:
 *
 * You are given a circular route connecting a set of cities. Each city has a gas station that provides a certain amount of fuel (in gallons),
 * and each city is a specific distance away from the next city on the route.
 *
 * You have a car that can travel a certain number of miles per gallon of fuel.
 * The goal is to determine the starting city index such that you can complete the entire circular route
 * and return to the starting city with 0 or more gallons of fuel remaining.
 *
 * Important Points:
 * - The route is circular, meaning after the last city, you loop back to the first city.
 * - There is exactly one valid starting city that allows completing the entire route.
 * - The sum of fuel available at all cities is exactly enough to cover the total distance of the route.
 * - Your car starts with an empty fuel tank.
 * - The input consists of an array `distances`, where `distances[i]` represents the distance from city `i` to city `i+1`.
 * - The input also includes an array `fuel`, where `fuel[i]` represents the fuel available at city `i`.
 * - A positive integer `mpg` is provided, representing how many miles your car can travel per gallon of fuel.
 *
 * Example:
 * Input: distances = [5, 25, 15, 10, 15], fuel = [1, 2, 1, 0, 3], mpg = 10
 * Output: 4  // Starting at city index 4 allows completing the circuit.
 */

/*Brute Force Solution
Approach:
The brute force approach checks each city as a starting point. For each starting city, we simulate the entire trip around the circular route. If we can complete the trip and return to the starting city with non-negative fuel, then that city is the valid starting city.

Time Complexity:
O(n^2): We simulate the trip for each city, resulting in quadratic time complexity.
Space Complexity:
O(1): We only use a constant amount of extra space.*/

package medium.greedyalgorithms;

public class ValidStartingCity {

  // Brute Force Solution
  public static int validStartingCityBruteForce(int[] distances, int[] fuel, int mpg) {
    int numberOfCities = distances.length;

    for (int startCity = 0; startCity < numberOfCities; startCity++) {
      int fuelRemaining = 0;
      boolean canCompleteTrip = true;

      for (int currentCity = startCity; currentCity < startCity + numberOfCities; currentCity++) {
        int cityIndex = currentCity % numberOfCities;
        fuelRemaining += fuel[cityIndex] * mpg - distances[cityIndex];

        if (fuelRemaining < 0) {
          canCompleteTrip = false;
          break;
        }
      }

      if (canCompleteTrip) {
        return startCity;
      }
    }

    return -1; // This line should never be reached as per problem guarantees.
  }

  // Optimized Solution
  public static int validStartingCityOptimized(int[] distances, int[] fuel, int mpg) {
    int numberOfCities = distances.length;
    int fuelRemaining = 0;
    int minFuel = 0;
    int startingCityIndex = 0;

    for (int i = 0; i < numberOfCities; i++) {
      int fuelFromCurrentCity = fuel[i] * mpg - distances[i];
      fuelRemaining += fuelFromCurrentCity;

      // Update starting city if the fuel remaining is less than the minimum seen so far
      if (fuelRemaining < minFuel) {
        minFuel = fuelRemaining;
        startingCityIndex = i + 1;
      }
    }

    return startingCityIndex % numberOfCities;
  }

  public static void main(String[] args) {
    int[] distances = {5, 25, 15, 10, 15};
    int[] fuel = {1, 2, 1, 0, 3};
    int mpg = 10;
    System.out.println(validStartingCityBruteForce(distances, fuel, mpg)); // Output: 4
  }
}

/*Optimized Solution
Approach:
The optimized approach involves tracking the fuel deficit and identifying the city where this deficit is the worst. We simulate the trip in a single pass. If at any point, our fuel drops below zero, that means the previous segment can't be completed if we started from the previous segment. We set our starting city to the next city and continue. The worst point where we run out of fuel is the key to identifying the valid starting city.

Time Complexity:
O(n): We make a single pass through the array, resulting in linear time complexity.
Space Complexity:
O(1): We only use a constant amount of extra space.*/
