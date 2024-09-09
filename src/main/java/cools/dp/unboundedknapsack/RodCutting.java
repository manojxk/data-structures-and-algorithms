package cools.dp.unboundedknapsack;

/*
Given a rod of length N inches and an array of prices, price[]. price[i] denotes the value of a piece of length i. Determine the maximum value obtainable by cutting up the rod and selling the pieces.

Note: Consider 1-based indexing.

Example 1:

Input:
N = 8
Price[] = {1, 5, 8, 9, 10, 17, 17, 20}
Output:22
Explanation:
The maximum obtainable value is 22 by
cutting in two pieces of lengths 2 and
6, i.e., 5+17=22.

same as Unbounded Knapsack

*/
public class RodCutting {

  public static void main(String[] args) {
    int[] price = {1, 5, 8, 9, 10, 17, 17, 20};
    int N = 8;

    int[] weights = new int[N];
    for (int i = 0; i < N; i++) {
      weights[i] = i + 1;
    }

    System.out.println(
        "Recursive result: "
            + UnboundedKnapsack.knapsackTabulation(weights, price, N)); // Output: 22
  }
}
