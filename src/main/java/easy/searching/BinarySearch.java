package easy.searching;

public class BinarySearch {
  public static int findTargetBruteForce(int[] array, int target) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] == target) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Iterative binary search to find the target integer in a sorted array. Time Complexity: O(log n)
   * Space Complexity: O(1)
   *
   * @param array the sorted array of integers
   * @param target the target integer to find
   * @return the index of the target if found, otherwise -1
   */
  public static int binarySearchIterative(int[] array, int target) {
    int left = 0;
    int right = array.length - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2; // Prevents overflow

      if (array[mid] == target) {
        return mid;
      } else if (array[mid] < target) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return -1;
  }

  /**
   * Recursive binary search to find the target integer in a sorted array. Time Complexity: O(log n)
   * Space Complexity: O(log n) due to recursive call stack
   *
   * @param array the sorted array of integers
   * @param target the target integer to find
   * @param left the left boundary of the search interval
   * @param right the right boundary of the search interval
   * @return the index of the target if found, otherwise -1
   */
  public static int binarySearchRecursive(int[] array, int target, int left, int right) {
    if (left > right) {
      return -1;
    }

    int mid = left + (right - left) / 2;

    if (array[mid] == target) {
      return mid;
    } else if (array[mid] < target) {
      return binarySearchRecursive(array, target, mid + 1, right);
    } else {
      return binarySearchRecursive(array, target, left, mid - 1);
    }
  }

  public static void main(String[] args) {
    int[] array = {0, 1, 21, 33, 45, 45, 61, 71, 72, 73};
    int target = 33;

    int resultIterative = binarySearchIterative(array, target);
    System.out.println("Iterative Result: " + resultIterative); // Output: 3

    int resultRecursive = binarySearchRecursive(array, target, 0, array.length - 1);
    System.out.println("Recursive Result: " + resultRecursive); // Output: 3
  }
}
