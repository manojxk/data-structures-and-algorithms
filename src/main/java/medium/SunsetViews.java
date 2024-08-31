/*
 * Problem Statement:
 * Given an array of buildings and a direction that all of the buildings face,
 * return an array of the indices of the buildings that can see the sunset.
 *
 * A building can see the sunset if it is strictly taller than all of the
 * buildings that come after it in the direction that it faces.
 *
 * The input array named `buildings` contains positive, non-zero
 * integers representing the heights of the buildings. A building at index
 * `i` has a height denoted by `buildings[i]`. All of the
 * buildings face the same direction, and this direction is either east or
 * west, denoted by the input string `direction`, which will
 * always be equal to either "EAST" or "WEST". In
 * relation to the input array, you can interpret these directions as right for
 * east and left for west.
 *
 * Important Note:
 * The indices in the output array should be sorted in ascending order.
 *
 * Example:
 *
 * Sample Input #1:
 * buildings = [3, 5, 4, 4, 3, 1, 3, 2]
 * direction = "EAST"
 *
 * Sample Output #1:
 * [1, 3, 6, 7]
 *
 * Sample Input #2:
 * buildings = [3, 5, 4, 4, 3, 1, 3, 2]
 * direction = "WEST"
 *
 * Sample Output #2:
 * [0, 1]
 */

package medium; /*
                Approach:
                Identify Direction:
                If the buildings face "EAST," check from left to right.
                If the buildings face "WEST," check from right to left.
                Iterate Through Buildings:
                Keep track of the maximum height seen so far.
                If the current building is taller than all previously seen buildings in the given direction, it can see the sunset, so record its index.
                Sort the Result: Ensure the output indices are in ascending order (if needed).
                Time Complexity:
                O(n): Where n is the number of buildings.
                Space Complexity:
                O(n): Space used to store the result.*/

import java.util.*;

public class SunsetViews {

  // Brute Force Solution
  public static List<Integer> sunsetViews(int[] buildings, String direction) {
    List<Integer> result = new ArrayList<>();
    int maxHeight = 0;

    if (direction.equals("EAST")) {
      // Iterate from left to right
      for (int i = buildings.length - 1; i >= 0; i--) {
        if (buildings[i] > maxHeight) {
          result.add(i);
          maxHeight = buildings[i];
        }
      }
    } else {
      // Iterate from right to left
      for (int i = 0; i < buildings.length; i++) {
        if (buildings[i] > maxHeight) {
          result.add(i);
          maxHeight = buildings[i];
        }
      }
    }

    // Since we add indices in reverse order for "EAST," reverse the list before returning
    if (direction.equals("EAST")) {
      Collections.reverse(result);
    }

    return result;
  }

  public static void main(String[] args) {
    int[] buildings = {3, 5, 4, 4, 3, 1, 3, 2};
    String direction = "EAST";
    List<Integer> result = sunsetViews(buildings, direction);
    System.out.println(result); // Output: [1, 3, 6, 7]

    direction = "WEST";
    result = sunsetViews(buildings, direction);
    System.out.println(result); // Output: [0, 1]
  }
}
