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

package medium.greedyalgorithms;

import cools.arrays.A13GasStation;
import java.util.Arrays;

public class A02ValidStartingCity {

  public static int canCompleteCircuit(int[] distances, int[] fuel, int mpg) {
    fuel = Arrays.stream(fuel).map(n -> n * mpg).toArray();
    return A13GasStation.canCompleteCircuit(fuel, distances);
  }

  public static void main(String[] args) {
    int[] distances = {5, 25, 15, 10, 15};
    int[] fuel = {1, 2, 1, 0, 3};
    int mpg = 10;
    System.out.println(canCompleteCircuit(distances, fuel, mpg)); // Output: 4
  }
}


