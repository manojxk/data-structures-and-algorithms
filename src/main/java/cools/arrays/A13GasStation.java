package cools.arrays;

/*
 Problem: Gas Station

 There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].
 You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station.

 Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1. If there exists a solution, it is guaranteed to be unique.

 Constraints:
 - 1 <= gas.length, cost.length <= 10^5
 - 0 <= gas[i], cost[i] <= 10^4

 Example 1:
 Input: gas = [1, 2, 3, 4, 5], cost = [3, 4, 5, 1, 2]
 Output: 3
 Explanation:
 Start at station 3 (index 3) and travel through all stations successfully with sufficient gas at each stop.

 Example 2:
 Input: gas = [2, 3, 4], cost = [3, 4, 3]
 Output: -1
 Explanation: You can't start at any station and complete the circuit due to insufficient gas at certain points.

 Solution Approach:
 1. The problem can be solved in O(n) using a greedy approach.
 2. Keep track of the total gas and total cost.
 3. If at any point, the gas left in the tank drops below zero, reset the starting point to the next station.
 4. At the end, if the total gas is greater than or equal to the total cost, there is a valid solution; otherwise, return -1.
*/

public class A13GasStation {

  // Function to find the starting gas station index if a valid circuit exists
  public static int canCompleteCircuit(int[] gas, int[] cost) {
    int totalGas = 0; // Track the total gas available
    int totalCost = 0; // Track the total cost required
    int tank = 0; // Track the current gas in the tank
    int startIndex = 0; // The starting index for a valid solution

    // Traverse all gas stations
    for (int i = 0; i < gas.length; i++) {
      totalGas += gas[i];
      totalCost += cost[i];
      tank += gas[i] - cost[i];

      // If at any point the tank is negative, reset the start index and tank
      if (tank < 0) {
        startIndex = i + 1; // Start from the next station
        tank = 0; // Reset the gas in the tank
      }
    }

    // If the total gas is greater than or equal to the total cost, return startIndex
    // Otherwise, it's impossible to complete the circuit
    return totalGas >= totalCost ? startIndex : -1;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A13GasStation solution = new A13GasStation();

    // Example 1
    int[] gas1 = {1, 2, 3, 4, 5};
    int[] cost1 = {3, 4, 5, 1, 2};
    System.out.println("Start Index: " + solution.canCompleteCircuit(gas1, cost1)); // Output: 3

    // Example 2
    int[] gas2 = {2, 3, 4};
    int[] cost2 = {3, 4, 3};
    System.out.println("Start Index: " + solution.canCompleteCircuit(gas2, cost2)); // Output: -1
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the arrays gas and cost. We traverse the arrays once.

   Space Complexity:
   - O(1), since we only use a few extra variables to store the current gas and the total gas and cost.
  */
}
