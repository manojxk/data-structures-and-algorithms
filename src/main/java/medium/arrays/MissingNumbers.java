/*
 * Problem Statement:
 * Given an unordered list of unique integers 'nums' in the range [1, n], where
 * 'n' represents the length of 'nums + 2', two numbers from this range are missing.
 *
 * Write a function that takes in this list and returns a new list containing
 * the two missing numbers, sorted in ascending order.
 *
 * Example:
 * Input: nums = [1, 4, 3]
 * Output: [2, 5]
 * Explanation: The original range should be [1, 2, 3, 4, 5], and the numbers
 *              2 and 5 are missing.
 */

/*Brute Force Solution
Approach:
Generate the complete list of numbers from 1 to n.
Iterate through this complete list and check if each number is present in nums.
Collect the missing numbers.
Time Complexity:
O(n^2): We iterate through the complete list (O(n)) and for each number, we check if it exists in nums (O(n)), resulting in quadratic time complexity.
Space Complexity:
O(n): Additional space is used to store the complete list from 1 to n.*/
/*Optimized Solution: Using Sum and Product Approach
Approach:
Sum Formula: The sum of all numbers from 1 to n can be calculated using the formula

Sum of Squares Formula: Similarly, the sum of squares of all numbers from 1 to n can be calculated using the formula

Calculate the actual sum and sum of squares from the given array nums.
Using the difference between expected and actual sums and sum of squares, solve the quadratic equation to find the missing numbers.
Time Complexity:
O(n): We perform a constant number of operations on each element of the array.
Space Complexity:
O(1): Only a few variables are used, independent of the input size.*/

package medium.arrays;

import java.util.*;

public class MissingNumbers {

  // Brute Force Solution
  public static List<Integer> findMissingNumbersBruteForce(int[] nums) {
    int n = nums.length + 2;
    List<Integer> missingNumbers = new ArrayList<>();

    for (int i = 1; i <= n; i++) {
      boolean found = false;
      for (int num : nums) {
        if (num == i) {
          found = true;
          break;
        }
      }
      if (!found) {
        missingNumbers.add(i);
      }
    }
    Collections.sort(missingNumbers); // Ensure the missing numbers are sorted
    return missingNumbers;
  }

  // Optimized Solution using Sum and Product approach
  public static List<Integer> findMissingNumbers(int[] nums) {
    int n = nums.length + 2;

    // Calculate the expected sum and sum of squares
    int expectedSum = n * (n + 1) / 2;
    int expectedSumOfSquares = n * (n + 1) * (2 * n + 1) / 6;

    // Calculate the actual sum and sum of squares
    int actualSum = 0;
    int actualSumOfSquares = 0;
    for (int num : nums) {
      actualSum += num;
      actualSumOfSquares += num * num;
    }

    // The sum of the missing numbers
    int sumOfMissingNumbers = expectedSum - actualSum;

    // The sum of squares of the missing numbers
    int sumOfSquaresOfMissingNumbers = expectedSumOfSquares - actualSumOfSquares;

    // Calculate the product of the missing numbers
    int productOfMissingNumbers =
        (sumOfMissingNumbers * sumOfMissingNumbers - sumOfSquaresOfMissingNumbers) / 2;

    // Solve the quadratic equation
    int diffOfMissingNumbers =
        (int) Math.sqrt(sumOfMissingNumbers * sumOfMissingNumbers - 4 * productOfMissingNumbers);
    int missingNumber1 = (sumOfMissingNumbers + diffOfMissingNumbers) / 2;
    int missingNumber2 = (sumOfMissingNumbers - diffOfMissingNumbers) / 2;

    return Arrays.asList(
        Math.min(missingNumber1, missingNumber2), Math.max(missingNumber1, missingNumber2));
  }

  /**
   * This function finds the two missing numbers in the given list.
   *
   * @param nums The input list of unique integers.
   * @return A new list containing the two missing numbers, sorted in ascending order.
   */
  public static int[] findMissingNumbersCopilot(int[] nums) {
    int n = nums.length + 2;
    // Calculate the sum of the numbers in the original range.
    int totalSum = n * (n + 1) / 2;
    // Calculate the sum of the numbers in the input list.
    int numsSum = Arrays.stream(nums).sum();
    // The difference between these two sums is the sum of the two missing numbers.
    int missingSum = totalSum - numsSum;

    // Divide this sum by 2 to find a pivot.
    int pivot = missingSum / 2;

    // Calculate the sum of the numbers less than or equal to the pivot in the original range.
    int totalLeftSum = pivot * (pivot + 1) / 2;
    // Calculate the sum of the numbers less than or equal to the pivot in the input list.
    int numsLeftSum = Arrays.stream(nums).filter(x -> x <= pivot).sum();
    // The difference between these two sums is one of the missing numbers.
    int missingLeft = totalLeftSum - numsLeftSum;

    // The other missing number is the difference between the sum of the two missing numbers and
    // this number.
    int missingRight = missingSum - missingLeft;

    // Return a list containing the two missing numbers, sorted in ascending order.
    return new int[] {missingLeft, missingRight};
  }

  public static void main(String[] args) {
    int[] nums = {1, 4, 3};
    System.out.println(Arrays.toString(findMissingNumbersCopilot(nums))); // Output: [2, 5]
  }

  // O(n) time | O(n) space - where n is the length of the input array
  public int[] missingNumbers(int[] nums) {
    HashSet<Integer> includedNums = new HashSet<Integer>();
    for (int num : nums) {
      includedNums.add(num);
    }

    int[] solution = new int[] {-1, -1};
    for (int num = 1; num < nums.length + 3; num++) {
      if (!includedNums.contains(num)) {
        if (solution[0] == -1) {
          solution[0] = num;
        } else {
          solution[1] = num;
        }
      }
    }

    return solution;
  }
}
