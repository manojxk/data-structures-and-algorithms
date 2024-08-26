/*
 * Problem Statement:
 *
 * It's photo day at a school, and you are the photographer. The class that
 * you will be photographing has an even number of students, and each student
 * is wearing either a red or a blue shirt. Exactly half of the class is wearing
 * red shirts, and the other half is wearing blue shirts.
 *
 * You need to arrange the students in two rows of equal length before taking
 * the photo. The arrangement must satisfy the following conditions:
 *
 * 1. All students wearing red shirts must be in the same row.
 * 2. All students wearing blue shirts must be in the same row.
 * 3. Each student in the back row must be strictly taller than the student
 *    directly in front of them in the front row.
 *
 * Given two input arrays: one containing the heights of all the students wearing
 * red shirts and another containing the heights of all the students wearing blue
 * shirts, determine if it is possible to take the class photo while satisfying the
 * above conditions.
 *
 * Note: You can assume that each class has at least 2 students.
 *
 * Example:
 * Input:
 * redShirtHeights = [5, 8, 1, 3, 4]
 * blueShirtHeights = [6, 9, 2, 4, 5]
 *
 * Output:
 * true  // Place all students with blue shirts in the back row.
 */

package easy;

import java.util.Arrays;

public class ClassPhoto {

  // Function to determine if a valid class photo can be taken
  public static boolean canTakeClassPhoto(int[] redShirtHeights, int[] blueShirtHeights) {
    // Sort both arrays in ascending order
    Arrays.sort(redShirtHeights);
    Arrays.sort(blueShirtHeights);

    // Determine which group is in the front row
    boolean redInFront = redShirtHeights[0] < blueShirtHeights[0];

    // Compare the heights
    for (int i = 0; i < redShirtHeights.length; i++) {
      if (redInFront) {
        // Red shirts in front, blue shirts in the back
        if (redShirtHeights[i] >= blueShirtHeights[i]) {
          return false;
        }
      } else {
        // Blue shirts in front, red shirts in the back
        if (blueShirtHeights[i] >= redShirtHeights[i]) {
          return false;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    int[] redShirtHeights = {5, 8, 1, 3, 4};
    int[] blueShirtHeights = {6, 9, 2, 4, 5};

    boolean result = canTakeClassPhoto(redShirtHeights, blueShirtHeights);
    System.out.println(result); // Output: true
  }
}

/*Time Complexity:
O(n log n): Sorting the arrays.
O(n): Checking each element pair.
Space Complexity:
O(1): No additional data structures are required.*/
