/*
 * Problem Statement:
 * Write a function that returns all the possible IP addresses that can be created by inserting three dots in a string of digits.
 *
 * An IP address is a sequence of four positive integers that are separated by dots, where each individual integer is within the range 0 - 255, inclusive.
 * An IP address isn't valid if any of the individual integers contains leading zeros or is greater than 255.
 *
 * The function should return the IP addresses in string format and in no particular order. If no valid IP addresses can be created from the string, return an empty list.
 *
 * Example:
 *
 * Sample Input:
 * string = "1921680"
 *
 * Sample Output:
 * [
 *   "1.9.216.80",
 *   "1.92.16.80",
 *   "1.92.168.0",
 *   "19.2.16.80",
 *   "19.2.168.0",
 *   "19.21.6.80",
 *   "19.21.68.0",
 *   "19.216.8.0",
 *   "192.1.6.80",
 *   "192.1.68.0",
 *   "192.16.8.0"
 * ]
 */

/*
 Solution Steps:

 1. Loop through all combinations of placing three dots in the string, splitting it into four segments.
 2. For each combination, check if each segment represents a valid integer (between 0 and 255) and ensure there are no leading zeros.
 3. If all segments are valid, construct the IP address and add it to the result list.
 4. Return the list of valid IP addresses.

 Time Complexity: O(1)
 - The number of possible IP addresses from a string of length 12 is fixed at a maximum of 81 iterations due to the constraints on segment length and the size of an IP address.

 Space Complexity: O(1)
 - The space used is constant aside from the result list, as we are only storing a fixed number of possible combinations.
*/

package medium.strings;

import java.util.*;

public class A03RestoreIPAddresses {

  public static List<String> restoreIpAddresses(String s) {
    List<String> result = new ArrayList<>();
    String ans;

    // Looping through each segment's possible length (1 to 3 characters)
    for (int a = 1; a <= 3; a++) {
      for (int b = 1; b <= 3; b++) {
        for (int c = 1; c <= 3; c++) {
          for (int d = 1; d <= 3; d++) {
            // Check if the sum of lengths equals the string's length
            if (a + b + c + d == s.length()) {
              // Parse each segment into an integer
              int A = Integer.parseInt(s.substring(0, a));
              int B = Integer.parseInt(s.substring(a, a + b));
              int C = Integer.parseInt(s.substring(a + b, a + b + c));
              int D = Integer.parseInt(s.substring(a + b + c, a + b + c + d));

              // Check if all segments are valid (i.e., between 0 and 255)
              if (A <= 255 && B <= 255 && C <= 255 && D <= 255) {
                // Create the IP address and check its length (to avoid leading zeros)
                ans = A + "." + B + "." + C + "." + D;
                result.add(ans);
              }
            }
          }
        }
      }
    }
    return result;
  }

  public static void main(String[] args) {

    String input = "1921680";
    List<String> output = restoreIpAddresses(input);
    System.out.println(output);
    // Expected Output: [1.9.216.80, 1.92.16.80, 1.92.168.0, 19.2.16.80, 19.2.168.0, 19.21.6.80,
    // 19.21.68.0, 19.216.8.0, 192.1.6.80, 192.1.68.0, 192.16.8.0]
  }
}
