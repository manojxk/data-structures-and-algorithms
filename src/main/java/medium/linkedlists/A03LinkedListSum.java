/*
 * Problem Statement:
 * You are given two linked lists of potentially unequal length. Each linked list represents a non-negative integer, where each node in the linked list is a digit of that integer. The first node in each linked list always represents the least significant digit of the integer.
 * Write a function that returns the head of a new linked list that represents the sum of the integers represented by the two input linked lists.
 *
 * Each LinkedList node has an integer value as well as a next node pointing to the next node in the list or to null if it's the tail of the list. The value of each LinkedList node is always in the range of 0 - 9.
 *
 * Note: Your function must create and return a new linked list. You are not allowed to modify either of the input linked lists.
 *
 * Example:
 * Input: linkedListOne = 2 -> 4 -> 7 -> 1, linkedListTwo = 9 -> 4 -> 5
 * Output: 1 -> 9 -> 2 -> 2
 * Explanation: linkedListOne represents 1742, linkedListTwo represents 549, and their sum is 2291.
 */

package medium.linkedlists;

public class A03LinkedListSum {
  // Definition for singly-linked list.
  static class ListNode {
    int value;
    ListNode next;

    ListNode(int x) {
      value = x;
    }
  }

  /*
   * Approach:
   * 1. Initialize a dummy node to simplify handling the result linked list.
   * 2. Use two pointers to traverse both linked lists and sum corresponding digits along with any carry from the previous operation.
   * 3. Create a new node in the result linked list based on the sum (sum % 10), and calculate the carry (sum / 10).
   * 4. Continue until both linked lists are fully traversed and any carry is added.
   * 5. Return the new linked list, starting from the node after the dummy head.
   */

  // Function to add two linked lists
  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummyHead = new ListNode(0); // Dummy node to simplify code
    ListNode current = dummyHead;
    int carry = 0;

    // Traverse both linked lists
    while (l1 != null || l2 != null || carry != 0) {
      int x = (l1 != null) ? l1.value : 0; // Get value from l1, or 0 if null
      int y = (l2 != null) ? l2.value : 0; // Get value from l2, or 0 if null
      int sum = carry + x + y; // Sum of values and carry

      carry = sum / 10; // Calculate carry
      current.next = new ListNode(sum % 10); // Create new node for the result linked list
      current = current.next; // Move current pointer

      // Move to the next nodes in l1 and l2 if they exist
      if (l1 != null) l1 = l1.next;
      if (l2 != null) l2 = l2.next;
    }

    return dummyHead.next; // The result linked list is next to the dummy node
  }

  // Main function to test the sum of two linked lists
  public static void main(String[] args) {
    // Example usage:
    // LinkedList 1: 2 -> 4 -> 7 -> 1 (represents 1742)
    ListNode l1 = new ListNode(2);
    l1.next = new ListNode(4);
    l1.next.next = new ListNode(7);
    l1.next.next.next = new ListNode(1);

    // LinkedList 2: 9 -> 4 -> 5 (represents 549)
    ListNode l2 = new ListNode(9);
    l2.next = new ListNode(4);
    l2.next.next = new ListNode(5);

    // Get the sum of two linked lists
    ListNode result = addTwoNumbers(l1, l2);

    // Print result: 1 -> 9 -> 2 -> 2 (represents 2291)
    while (result != null) {
      System.out.print(result.value + " ");
      result = result.next;
    }
  }
}

/*
 * Time Complexity:
 * O(max(n, m)), where n and m are the lengths of the two linked lists.
 * We traverse both linked lists simultaneously, and the length of the result list is at most max(n, m) + 1 (due to the carry).
 *
 * Space Complexity:
 * O(max(n, m)), for the result linked list, which is proportional to the length of the longer list, plus one extra node if there is a carry.
 */
