package easy.greedyalgorithm;

/*
 Problem: Class Photo

 A group of students is standing in line to take a class photo. The group is divided into two rows based on their heights.
 The students in the back row must be strictly taller than the students in the front row.
 The problem is to determine if it's possible to take such a photo given the heights of students in two groups.

 Example:

 Input:
   redShirtHeights = [5, 8, 1, 3, 4]
   blueShirtHeights = [6, 9, 2, 4, 5]
 Output:
   true

 Explanation:
 - We can arrange the students in such a way that all students in one row are strictly taller than the students in the other row.
 - For instance, one possible arrangement is to have the red-shirted students in the front row and the blue-shirted students in the back row.

*/

/*
 Solution Steps:

 1. Sort both the red-shirted and blue-shirted students' heights in ascending order.
 2. Determine which group will stand in the front and which group will stand in the back.
 3. For the chosen configuration, check if every student in the back row is strictly taller than the student in front of them.
 4. If the condition holds true for all students, return true. Otherwise, return false.
*/

import java.util.Arrays;

public class A02ClassPhoto {

  // Function to determine if it's possible to take a class photo with the given heights
  public boolean canTakeClassPhoto(int[] redShirtHeights, int[] blueShirtHeights) {
    // Sort both arrays of heights
    Arrays.sort(redShirtHeights);
    Arrays.sort(blueShirtHeights);

    // Determine which group stands in the front
    boolean redInFront = redShirtHeights[0] < blueShirtHeights[0];

    // Check if all students in the back row are taller than the students in the front row
    for (int i = 0; i < redShirtHeights.length; i++) {
      if (redInFront) {
        // If red is in front, check if every red shirt is shorter than the corresponding blue shirt
        if (redShirtHeights[i] >= blueShirtHeights[i]) {
          return false;
        }
      } else {
        // If blue is in front, check if every blue shirt is shorter than the corresponding red
        // shirt
        if (blueShirtHeights[i] >= redShirtHeights[i]) {
          return false;
        }
      }
    }

    // If all conditions are met, it's possible to take the class photo
    return true;
  }

  // Main function to test the Class Photo problem
  public static void main(String[] args) {
    A02ClassPhoto classPhoto = new A02ClassPhoto();

    // Example input
    int[] redShirtHeights = {5, 8, 1, 3, 4};
    int[] blueShirtHeights = {6, 9, 2, 4, 5};

    // Check if it's possible to take the class photo
    boolean result = classPhoto.canTakeClassPhoto(redShirtHeights, blueShirtHeights);
    System.out.println("Can take class photo: " + result); // Output: true
  }

  /*
   Time Complexity:
   - O(n log n), where n is the number of students. Sorting both arrays takes O(n log n) time.

   Space Complexity:
   - O(1), as we only use a constant amount of extra space (no additional data structures other than input arrays).
  */
}
