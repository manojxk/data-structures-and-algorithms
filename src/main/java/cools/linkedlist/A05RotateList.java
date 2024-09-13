package cools.linkedlist;

/*
 Problem: Rotate List

 Given the head of a linked list, rotate the list to the right by k places.

 Example 1:
 Input: head = [1,2,3,4,5], k = 2
 Output: [4,5,1,2,3]
 Explanation: Rotate the list by 2 places to the right.

 Example 2:
 Input: head = [0,1,2], k = 4
 Output: [2,0,1]
 Explanation: Rotate the list by 4 places to the right (which is equivalent to rotating by 1 place).

 Constraints:
 - The number of nodes in the list is in the range [0, 500].
 - -100 <= Node.val <= 100
 - 0 <= k <= 2 * 10^9

 Solution Approach:
 1. First, calculate the length of the list.
 2. If k is greater than the length of the list, use `k = k % length` to adjust it.
 3. Use two pointers to find the new tail (i.e., the node before the new head).
 4. Adjust the pointers to rotate the list and set the new head and tail.
*/

public class A05RotateList {

  // Definition for singly-linked list.
  static class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

  // Function to rotate the linked list to the right by k places
  public ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null || k == 0) {
      return head; // No rotation needed for empty or single-node list or k == 0
    }

    // Step 1: Calculate the length of the list and get the tail node
    ListNode current = head;
    int length = 1; // Length of the list
    while (current.next != null) {
      current = current.next;
      length++;
    }

    // The list becomes a circular list by connecting the tail to the head
    current.next = head;

    // Step 2: Calculate the effective number of rotations
    k = k % length; // If k >= length, reduce k to within the list length
    int stepsToNewHead = length - k;

    // Step 3: Find the new tail (node just before the new head)
    ListNode newTail = head;
    for (int i = 1; i < stepsToNewHead; i++) {
      newTail = newTail.next;
    }

    // Step 4: Break the circular list to form the new rotated list
    ListNode newHead = newTail.next;
    newTail.next = null; // Break the circular link

    return newHead; // Return the new head of the rotated list
  }

  // Helper function to print the linked list
  public static void printList(ListNode head) {
    while (head != null) {
      System.out.print(head.val + " -> ");
      head = head.next;
    }
    System.out.println("null");
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A05RotateList solution = new A05RotateList();

    // Example 1: Rotate by 2 places
    ListNode head1 =
        new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
    System.out.println("Original List:");
    printList(head1);
    ListNode result1 = solution.rotateRight(head1, 2);
    System.out.println("Rotated List by 2 places:");
    printList(result1); // Output: [4, 5, 1, 2, 3]

    // Example 2: Rotate by 4 places
    ListNode head2 = new ListNode(0, new ListNode(1, new ListNode(2)));
    System.out.println("Original List:");
    printList(head2);
    ListNode result2 = solution.rotateRight(head2, 4);
    System.out.println("Rotated List by 4 places:");
    printList(result2); // Output: [2, 0, 1]
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the linked list. We traverse the list twice: once to calculate the length and once to find the new head.

   Space Complexity:
   - O(1), since we are using a constant amount of extra space (ignoring the space used for the input and output).
  */
}
