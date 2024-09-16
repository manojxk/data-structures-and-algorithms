package medium.arrays;

/*
 Problem: Sweet and Savory

 You are given two types of dishes: sweet and savory. Each dish has a taste value, which can either be positive for sweet or negative for savory.
 You are also given a target value that represents the desired total taste value of a sweet and savory dish combo.
 The task is to find the sweet and savory dish pair whose total taste value is closest to the target, but not greater than it.

 Example:

 Input:
 sweetAndSavory = [-3, -5, 1, 7, 3, 9]
 target = 8

 Output:
 [-5, 9] (or any pair whose sum is <= 8 and closest to it)

 Explanation:
 The best pair is -5 (savory) and 9 (sweet) because their sum is 4, which is the closest to 8 without exceeding it.
*/

/*
 Solution Steps:

 1. Separate the sweet and savory dishes into two separate lists: sweetDishes (positive) and savoryDishes (negative).
 2. Sort both lists.
 3. Use a two-pointer technique to find the best pair:
    - One pointer starts at the beginning of the savoryDishes, and the other starts at the end of the sweetDishes.
    - At each step, calculate the sum of the pair.
    - If the sum is greater than the target, move the sweet pointer left to reduce the sum.
    - If the sum is less than or equal to the target, check if it's the closest sum so far, and move the savory pointer right to try and find a closer pair.
 4. Return the pair with the closest sum.
*/

import java.util.Arrays;

public class A14SweetAndSavory {

  // Function to find the best sweet and savory combo
  public static int[] findSweetAndSavoryCombo(int[] dishes, int target) {
    // Step 1: Separate sweet and savory dishes
    int[] sweetDishes = Arrays.stream(dishes).filter(dish -> dish > 0).toArray();
    int[] savoryDishes = Arrays.stream(dishes).filter(dish -> dish < 0).toArray();

    // Step 2: Sort both lists
    Arrays.sort(sweetDishes);
    Arrays.sort(savoryDishes);

    // Step 3: Use two pointers to find the best combination
    int closestSum = Integer.MIN_VALUE;
    int[] bestPair = new int[2];

    int i = 0; // Pointer for savoryDishes
    int j = sweetDishes.length - 1; // Pointer for sweetDishes

    while (i < savoryDishes.length && j >= 0) {
      int currentSum = savoryDishes[i] + sweetDishes[j];

      // If the current sum is greater than the target, move the sweet pointer left
      if (currentSum > target) {
        j--;
      } else {
        // If the current sum is less than or equal to the target, check if it's closer to the
        // target
        if (currentSum > closestSum) {
          closestSum = currentSum;
          bestPair[0] = savoryDishes[i];
          bestPair[1] = sweetDishes[j];
        }
        i++; // Move the savory pointer right
      }
    }

    return bestPair;
  }

  public static int[] findBestPairingOptimized(int[] dishes, int target) {
    int[] sweetDishes = Arrays.stream(dishes).filter(d -> d < 0).toArray();
    int[] savoryDishes = Arrays.stream(dishes).filter(d -> d > 0).toArray();

    Arrays.sort(sweetDishes);
    Arrays.sort(savoryDishes);

    int bestSum = Integer.MIN_VALUE;
    int[] bestPair = {0, 0};

    int i = 0;
    int j = savoryDishes.length - 1;

    while (i < sweetDishes.length && j >= 0) {
      int sum = sweetDishes[i] + savoryDishes[j];
      if (sum <= target && sum > bestSum) {
        bestSum = sum;
        bestPair[0] = sweetDishes[i];
        bestPair[1] = savoryDishes[j];
      }
      if (sum > target) {
        j--;
      } else {
        i++;
      }
    }

    return bestPair;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] dishes = {-3, -5, 1, 7, 3, 9};
    int target = 8;

    int[] result = findSweetAndSavoryCombo(dishes, target);
    System.out.println(
        "Best Sweet and Savory combo: " + Arrays.toString(result)); // Output: [-5, 9]
  }

  /*
   Time Complexity:
   - O(n log n), where n is the number of dishes. We sort the sweet and savory lists, which dominates the time complexity.

   Space Complexity:
   - O(n), since we create new arrays for sweetDishes and savoryDishes.
  */
}
