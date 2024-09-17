package veryhard.searching;

/*
 * Problem: Allocate Minimum Pages
 *
 * You are given an array of integers `books[]` where each element represents the number of pages in a book.
 * There are `m` students, and you need to allocate all the books to the students.
 * Each student must be assigned at least one book, and the task is to minimize the maximum number of pages assigned to a student.
 *
 * You need to allocate books in contiguous order to students.
 *
 * Example 1:
 * Input: books = [12, 34, 67, 90], students = 2
 * Output: 113
 * Explanation: Allocate [12, 34, 67] to one student and [90] to another. The maximum number of pages assigned to a student is 113.
 *
 * Example 2:
 * Input: books = [10, 20, 30, 40], students = 2
 * Output: 60
 * Explanation: Allocate [10, 20, 30] to one student and [40] to another. The maximum number of pages assigned is 60.
 */

/*
 * Solution Approach:
 *
 * 1. Use binary search to find the minimum maximum pages a student can be allocated.
 * 2. Define a helper function to check if a given number of pages can be allocated such that no student gets more than the given limit.
 * 3. The binary search range will be between the largest single book (as the minimum value) and the sum of all book pages (as the maximum value).
 * 4. Use binary search to minimize the maximum number of pages by repeatedly adjusting the upper and lower bounds based on feasibility checks.
 */

public class A02AllocateMinimumPages {

  // Function to check if it is possible to allocate books such that no student gets more than
  // `maxPages`
  private static boolean isPossible(int[] books, int students, int maxPages) {
    int allocatedStudents = 1; // Start with one student
    int currentPageSum = 0;

    for (int pages : books) {
      // If adding this book exceeds the limit, allocate to a new student
      if (currentPageSum + pages > maxPages) {
        allocatedStudents++;
        currentPageSum = pages;

        // If students exceed the given number of students, return false
        if (allocatedStudents > students) {
          return false;
        }
      } else {
        // Otherwise, accumulate the pages for the current student
        currentPageSum += pages;
      }
    }

    return true;
  }

  // Function to find the minimum possible maximum pages that can be allocated
  public static int findMinPages(int[] books, int students) {
    // If there are fewer books than students, we cannot allocate
    if (books.length < students) {
      return -1;
    }

    int low = Integer.MIN_VALUE;
    int high = 0;

    // Calculate the range for binary search:
    // low is the maximum number of pages in a single book,
    // high is the sum of all the pages
    for (int pages : books) {
      low = Math.max(low, pages);
      high += pages;
    }

    // Binary search over the range [low, high]
    int result = high;
    while (low <= high) {
      int mid = low + (high - low) / 2;

      // Check if it's possible to allocate books with this mid value as the max limit
      if (isPossible(books, students, mid)) {
        result = mid; // Update result as we try to minimize the maximum pages
        high = mid - 1; // Try for a smaller maximum
      } else {
        low = mid + 1; // Increase the minimum threshold
      }
    }

    return result;
  }

  public static void main(String[] args) {
    // Example 1
    int[] books1 = {12, 34, 67, 90};
    int students1 = 2;
    System.out.println(
        "Minimum pages allocated: " + findMinPages(books1, students1)); // Output: 113

    // Example 2
    int[] books2 = {10, 20, 30, 40};
    int students2 = 2;
    System.out.println("Minimum pages allocated: " + findMinPages(books2, students2)); // Output: 60

    // Example 3
    int[] books3 = {5, 17, 100, 11};
    int students3 = 4;
    System.out.println(
        "Minimum pages allocated: " + findMinPages(books3, students3)); // Output: 100
  }

  /*
   * Time Complexity:
   * O(n * log(sum)), where n is the number of books, and `sum` is the total sum of pages in the array.
   * The binary search runs in O(log(sum)), and for each iteration, we check if the allocation is possible in O(n).
   *
   * Space Complexity:
   * O(1), since we are only using a few variables to store the bounds and current page sum.
   */
}
