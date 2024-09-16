package medium.arrays;

/*
 Problem: Best Seat

 You are given an array representing a row of seats where:
 - 0 means the seat is empty, and
 - 1 means the seat is occupied.

 You want to find the index of the best seat to sit in. The best seat is the one that maximizes the distance to the closest person.
 If multiple seats are equally good, return the first one. If no seats are available, return -1.

 Example:

 Input: [1, 0, 0, 0, 1, 0, 1]
 Output: 2

 Explanation:
 The seat at index 2 is the best because it maximizes the distance to the nearest person.
*/

/*
 Solution Steps:

 1. Iterate through the array and identify all sequences of consecutive empty seats (0s).
 2. For each sequence of empty seats, calculate the middle seat as the best seat.
 3. Track the seat that maximizes the distance from the nearest occupied seat (1).
 4. Return the index of the best seat.
*/

public class A10BestSeat {

  // Function to find the index of the best seat
  public static int bestSeat(int[] seats) {
    int maxDistance = 0; // Variable to store the maximum distance to the nearest person
    int bestSeatIdx = -1; // Variable to store the index of the best seat
    int currentStart = -1; // Track the start of the current sequence of empty seats

    // Step 1: Traverse the array to find the best seat
    for (int i = 0; i < seats.length; i++) {
      if (seats[i] == 0) {
        // Start of a sequence of empty seats
        if (currentStart == -1) {
          currentStart = i;
        }
      } else {
        // End of a sequence of empty seats
        if (currentStart != -1) {
          int length = i - currentStart;
          int middleSeat = currentStart + length / 2; // Choose the middle seat of the sequence
          if (length > maxDistance) {
            maxDistance = length;
            bestSeatIdx = middleSeat;
          }
          currentStart = -1; // Reset the sequence tracker
        }
      }
    }

    // Handle the case where the last seat is part of the longest sequence
    if (currentStart != -1) {
      int length = seats.length - currentStart;
      int middleSeat = currentStart + length / 2;
      if (length > maxDistance) {
        bestSeatIdx = middleSeat;
      }
    }

    return bestSeatIdx;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] seats = {1, 0, 0, 0, 1, 0, 1};
    System.out.println("Best seat index: " + bestSeat(seats)); // Output: 2
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the seats array. We traverse the array once to find the best seat.

   Space Complexity:
   - O(1), since we only use a few extra variables to track the seat index and maximum distance.
  */
}
