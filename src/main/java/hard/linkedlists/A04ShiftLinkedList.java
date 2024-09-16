package hard.linkedlists;/*
 * Problem: Shift Linked List
 *
 * You are given a singly linked list and an integer k. Write a function that shifts (rotates) the list by k positions to the right.
 * When k is positive, every node in the linked list is shifted to the right by k positions.
 * When k is negative, every node is shifted to the left by |k| positions.
 *
 * Example 1:
 * Input: head = 0 -> 1 -> 2 -> 3 -> 4 -> 5, k = 2
 * Output: 4 -> 5 -> 0 -> 1 -> 2 -> 3
 *
 * Example 2:
 * Input: head = 0 -> 1 -> 2 -> 3 -> 4 -> 5, k = -1
 * Output: 1 -> 2 -> 3 -> 4 -> 5 -> 0
 *
 * Explanation:
 * - When k = 2, each node is shifted 2 positions to the right.
 * - When k = -1, each node is shifted 1 position to the left.
 */

/*
 * Solution Steps:
 *
 * 1. Find the length of the linked list.
 * 2. Normalize the value of k to avoid unnecessary rotations. This can be done by using `k % length` to get the effective shift.
 * 3. Handle the case where k is negative, converting it to an equivalent positive shift (shifting left by `k` is the same as shifting right by `length - k`).
 * 4. If `k` is zero or a multiple of the length of the list, the list remains unchanged.
 * 5. Identify the new head and tail of the rotated list, and update the pointers accordingly.
 * 6. Return the new head of the rotated list.
 */

public class A04ShiftLinkedList {

  // Definition for singly linked list node
  static class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Function to shift the linked list by k positions
  public static ListNode shiftLinkedList(ListNode head, int k) {
    if (head == null || head.next == null || k == 0) return head;

    // Step 1: Find the length of the linked list and connect the tail to the head to form a circular list
    int length = 1;
    ListNode tail = head;
    while (tail.next != null) {
      tail = tail.next;
      length++;
    }
    tail.next = head; // Circular connection

    // Step 2: Normalize the value of k
    k = k % length;
    if (k < 0) k = k + length; // Handle negative k by converting it to equivalent positive shift

    // Step 3: Find the new tail and new head
    int newTailPosition = length - k;
    ListNode newTail = head;
    for (int i = 1; i < newTailPosition; i++) {
      newTail = newTail.next;
    }

    ListNode newHead = newTail.next;

    // Step 4: Break the circular connection
    newTail.next = null;

    return newHead;
  }

  // Helper method to print the linked list
  public static void printList(ListNode head) {
    ListNode current = head;
    while (current != null) {
      System.out.print(current.value + " ");
      current = current.next;
    }
    System.out.println();
  }

  // Main function to test the shift linked list operation
  public static void main(String[] args) {
    // Example 1: Create linked list 0 -> 1 -> 2 -> 3 -> 4 -> 5
    ListNode head = new ListNode(0);
    head.next = new ListNode(1);
    head.next.next = new ListNode(2);
    head.next.next.next = new ListNode(3);
    head.next.next.next.next = new ListNode(4);
    head.next.next.next.next.next = new ListNode(5);

    System.out.println("Original List:");
    printList(head);  // Output: 0 1 2 3 4 5

    // Shift the list by k = 2 (right shift by 2 positions)
    ListNode result = shiftLinkedList(head, 2);
    System.out.println("Shifted List by k = 2:");
    printList(result);  // Output: 4 5 0 1 2 3

    // Reset the list and try shifting left by k = -1 (equivalent to right shift by length-1)
    head = new ListNode(0);
    head.next = new ListNode(1);
    head.next.next = new ListNode(2);
    head.next.next.next = new ListNode(3);
    head.next.next.next.next = new ListNode(4);
    head.next.next.next.next.next = new ListNode(5);

    result = shiftLinkedList(head, -1);
    System.out.println("Shifted List by k = -1 (left shift by 1):");
    printList(result);  // Output: 1 2 3 4 5 0
  }

  /*
   * Time Complexity:
   * O(n), where n is the number of nodes in the linked list.
   * - Finding the length of the linked list takes O(n).
   * - Normalizing the shift and finding the new tail takes O(n).
   * - Breaking the circular connection also takes O(1).
   *
   * Space Complexity:
   * O(1), because we only use a constant amount of extra space (for pointers like tail, newTail, and newHead).
   */
}
