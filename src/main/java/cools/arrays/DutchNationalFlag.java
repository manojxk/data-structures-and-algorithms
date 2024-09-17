package cools.arrays;/*
 Problem: Dutch National Flag Problem

 The Dutch National Flag Problem is a popular algorithmic problem that involves sorting an array of 0s, 1s, and 2s.
 The goal is to arrange the array in-place so that all 0s appear first, followed by all 1s, and then all 2s.

 Example:

 Input: [2, 0, 2, 1, 1, 0]
 Output: [0, 0, 1, 1, 2, 2]

 Explanation:
 The array is sorted in a single pass by maintaining three regions:
   - One for 0s (left side of the array)
   - One for 1s (middle of the array)
   - One for 2s (right side of the array)
*/

/*
 Solution Steps:

 1. Use three pointers: `low`, `mid`, and `high`.
 2. `low` will point to the boundary of the region of 0s.
 3. `high` will point to the boundary of the region of 2s.
 4. `mid` will traverse the array and categorize the elements as follows:
     - If the element at `mid` is 0, swap it with the element at `low` and move both `low` and `mid` forward.
     - If the element at `mid` is 2, swap it with the element at `high` and move `high` backward.
     - If the element at `mid` is 1, just move `mid` forward.
 5. The process continues until `mid` surpasses `high`.
*/

public class DutchNationalFlag {

  // Function to perform the Dutch National Flag sorting
  public void dutchNationalFlagSort(int[] arr) {
    int low = 0;
    int mid = 0;
    int high = arr.length - 1;

    // Traverse the array
    while (mid <= high) {
      // Case 0: When the element at `mid` is 0, swap with `low` and move both pointers
      if (arr[mid] == 0) {
        swap(arr, low, mid);
        low++;
        mid++;
      }
      // Case 1: When the element at `mid` is 1, just move the `mid` pointer forward
      else if (arr[mid] == 1) {
        mid++;
      }
      // Case 2: When the element at `mid` is 2, swap with `high` and move the `high` pointer
      else if (arr[mid] == 2) {
        swap(arr, mid, high);
        high--;
      }
    }
  }

  // Helper function to swap two elements in an array
  private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  // Main function to test the Dutch National Flag problem implementation
  public static void main(String[] args) {
    DutchNationalFlag sorter = new DutchNationalFlag();

    // Example array
    int[] arr = {2, 0, 2, 1, 1, 0};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    // Perform Dutch National Flag Sort
    sorter.dutchNationalFlagSort(arr);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }

  /*
   Time Complexity:
   - O(n), where n is the number of elements in the array. We make a single pass through the array, processing each element exactly once.

   Space Complexity:
   - O(1), since the algorithm is in-place and uses only a constant amount of extra space.
  */
}
