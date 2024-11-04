/*
 * Problem: Validate Subsequence
 * Given two non-empty arrays of integers, write a function that determines if the second array is a subsequence of the first one.
 * A subsequence of an array is a set of numbers that aren't necessarily adjacent in the array but that are in the same order as they appear in the array.
 * For instance, the numbers `[1, 3, 4]` form a subsequence of the array `[1, 2, 3, 4]`, and so do the numbers `[2, 4]`.
 * Note that a single number in an array and the array itself are both valid subsequences of the array.
 *
 * Example:
 * array = [5, 1, 22, 25, 6, -1, 8, 10]
 * sequence = [1, 6, -1, 10]
 * Output: true
 */

public class ValidateSubsequence {

    // Solution 1: Iterative Approach
    // Time Complexity: O(n) | Space Complexity: O(1)
    public static boolean isValidSubsequence(int[] array, int[] sequence) {
        // Initialize pointers for array and sequence
        int arrayIndex = 0;
        int sequenceIndex = 0;

        // Iterate through the array until we reach the end of either array or sequence
        while (arrayIndex < array.length && sequenceIndex < sequence.length) {
            // If elements match, move the sequence pointer forward
            if (array[arrayIndex] == sequence[sequenceIndex]) {
                sequenceIndex++;
            }
            // Always move the array pointer forward
            arrayIndex++;
        }

        // If sequenceIndex reached the end of the sequence, it means the sequence is valid
        return sequenceIndex == sequence.length;
    }

    public static void main(String[] args) {
        int[] array = {5, 1, 22, 25, 6, -1, 8, 10};
        int[] sequence = {1, 6, -1, 10};

        // Testing the iterative approach
        System.out.println("Validate Subsequence: " + isValidSubsequence(array, sequence));
    }
}
