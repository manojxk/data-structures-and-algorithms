

```java
package medium.arrays;

/*
 Problem: Best Seat

 Problem Restatement:
 You have an array seats[] of length n where:
 - seats[i] == 0 means seat i is empty, and
 - seats[i] == 1 means seat i is occupied.

 You want to choose an empty seat so that the distance to the closest occupied seat is as large as possible.
 In other words, for each empty index i, compute:

     dist(i) = min{|i – j| : seats[j] == 1}

 Pick the empty index i that maximizes dist(i). If there are multiple such indices, return the smallest one.
 If no empty seats exist, return –1.

 Example:
 Input: [1, 0, 0, 0, 1, 0, 1]
 Output: 2

 Explanation:
 The seat at index 2 is the best because it maximizes the distance to the nearest person.
*/

public class A10BestSeat {

  // ✅ Solution 1: Scan for sequences of 0s, pick the middle seat
  public static int bestSeat(int[] seats) {
    int maxDistance = 0;
    int bestSeatIdx = -1;
    int currentStart = -1;

    for (int i = 0; i < seats.length; i++) {
      if (seats[i] == 0) {
        if (currentStart == -1) {
          currentStart = i;
        }
      } else {
        if (currentStart != -1) {
          int length = i - currentStart;
          int middleSeat = currentStart + length / 2;
          if (length > maxDistance) {
            maxDistance = length;
            bestSeatIdx = middleSeat;
          }
          currentStart = -1;
        }
      }
    }

    // Handle trailing segment of empty seats
    if (currentStart != -1) {
      int length = seats.length - currentStart;
      int middleSeat = currentStart + length / 2;
      if (length > maxDistance) {
        bestSeatIdx = middleSeat;
      }
    }

    return bestSeatIdx;
  }

  // ✅ Solution 2: AlgoExpert-style two-pointer approach
  public static int bestSeatTwoPointer(int[] seats) {
    int bestSeat = -1;
    int maxSpace = 0;

    int left = 0;
    while (left < seats.length) {
      int right = left + 1;

      while (right < seats.length && seats[right] == 0) {
        right++;
      }

      int availableSpace = right - left - 1;
      if (availableSpace > maxSpace) {
        bestSeat = (left + right) / 2;
        maxSpace = availableSpace;
      }

      left = right;
    }

    return bestSeat;
  }

  // ✅ Main method to test both solutions
  public static void main(String[] args) {
    int[] seats1 = {1, 0, 0, 0, 1, 0, 1};
    System.out.println("Best seat index (Solution 1): " + bestSeat(seats1));         // Output: 2
    System.out.println("Best seat index (Solution 2): " + bestSeatTwoPointer(seats1)); // Output: 2

    int[] seats2 = {1, 0, 0, 1, 0, 0, 1};
    System.out.println("Best seat index (Solution 1): " + bestSeat(seats2));         // Output: 1
    System.out.println("Best seat index (Solution 2): " + bestSeatTwoPointer(seats2)); // Output: 1

    int[] seats3 = {1, 1, 1};
    System.out.println("Best seat index (Solution 1): " + bestSeat(seats3));         // Output: -1
    System.out.println("Best seat index (Solution 2): " + bestSeatTwoPointer(seats3)); // Output: -1
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the seats array.

   Space Complexity:
   - O(1), only constant extra space used.
  */
}
```

---

