/*
 * Problem Statement:
 * Write a function that takes in an n x m two-dimensional array (which can be square-shaped when n == m)
 * and returns a one-dimensional array containing all the array's elements in zigzag order.
 *
 * Zigzag order starts at the top left corner of the two-dimensional array, goes down by one element,
 * and proceeds in a zigzag pattern all the way to the bottom right corner.
 *
 * Example:
 * Input: array = [
 *   [1,  3,  4, 10],
 *   [2,  5,  9, 11],
 *   [6,  8, 12, 15],
 *   [7, 13, 14, 16],
 * ]
 * Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]
 */
/*Zigzag Order Approach
        Approach:
        To traverse the matrix in zigzag order, we alternate between moving diagonally down-right and diagonally up-left. We need to handle edge cases when the traversal reaches the boundaries of the matrix.

        Time Complexity:
        O(n * m): We visit each element of the matrix exactly once.
        Space Complexity:
        O(n * m): We store all the elements of the matrix in the result array.*/

package hard.arrays;

import java.util.ArrayList;
import java.util.List;

public class ZigzagTraversal {

    // Zigzag traversal of a 2D array
    public static List<Integer> zigzagTraverse(int[][] array) {
        int height = array.length;
        int width = array[0].length;
        List<Integer> result = new ArrayList<>();

        int row = 0, col = 0;
        boolean goingDown = true;

        while (row < height && col < width) {
            result.add(array[row][col]);

            if (goingDown) {
                if (col == 0 || row == height - 1) {
                    goingDown = false;
                    if (row == height - 1) {
                        col++;
                    } else {
                        row++;
                    }
                } else {
                    row++;
                    col--;
                }
            } else {
                if (row == 0 || col == width - 1) {
                    goingDown = true;
                    if (col == width - 1) {
                        row++;
                    } else {
                        col++;
                    }
                } else {
                    row--;
                    col++;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] array = {
                {1,  3,  4, 10},
                {2,  5,  9, 11},
                {6,  8, 12, 15},
                {7, 13, 14, 16},
        };
        System.out.println(zigzagTraverse(array));
        // Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]
    }
}
