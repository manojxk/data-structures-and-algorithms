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

// Definition for singly-linked list.
class ListNode {
  int value;
  ListNode next;

  ListNode(int x) {
    value = x;
  }
}

public class LinkedListSum {

  // Function to add two linked lists
  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummyHead = new ListNode(0); // Dummy node to simplify code
    ListNode p = l1, q = l2, current = dummyHead;
    int carry = 0;

    while (p != null || q != null || carry != 0) {
      int x = (p != null) ? p.value : 0;
      int y = (q != null) ? q.value : 0;
      int sum = carry + x + y;

      carry = sum / 10;
      current.next = new ListNode(sum % 10);
      current = current.next;

      if (p != null) p = p.next;
      if (q != null) q = q.next;
    }

    return dummyHead.next; // The result linked list is next to the dummy node
  }

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

    ListNode result = addTwoNumbers(l1, l2);
    // Print result: 1 -> 9 -> 2 -> 2 (represents 2291)
    while (result != null) {
      System.out.print(result.value + " ");
      result = result.next;
    }
  }
}
/*
Brute Force Solution
Approach:
Traverse both linked lists simultaneously.
Add corresponding digits along with any carry from the previous addition.
        Create new nodes for the result linked list based on the sum and the carry.
Continue until both linked lists are fully traversed.
If there’s a carry left after the final addition, add it as a new node.
Time Complexity:
O(max(n, m)), where n and m are the lengths of the two linked lists. This is because each node is processed exactly once.
Space Complexity:
O(max(n, m)) for the result linked list, which is proportional to the length of the longer list plus one extra node if there's a carry.
*/
