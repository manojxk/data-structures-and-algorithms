package veryhard.linkedlists;

/*
 * Problem: Zip Linked List
 *
 * Given the head of a singly linked list, rearrange the list in a "zip" pattern. The first half of the list should be interleaved with the second half of the list in reverse order.
 *
 * The structure should follow this pattern:
 * L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → ...
 *
 * Example 1:
 * Input: 1 -> 2 -> 3 -> 4 -> 5
 * Output: 1 -> 5 -> 2 -> 4 -> 3
 *
 * Example 2:
 * Input: 1 -> 2 -> 3 -> 4
 * Output: 1 -> 4 -> 2 -> 3
 */

/*
 * Solution Steps:
 *
 * 1. Use slow and fast pointers to find the middle of the linked list.
 * 2. Split the list into two halves.
 * 3. Reverse the second half of the list.
 * 4. Merge the first half and the reversed second half in an alternating pattern (zip the list).
 * 5. Return the rearranged list.
 */

public class A04ZipLinkedList {

  // Definition for singly linked list node
  static class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Function to zip the linked list
  public static ListNode zipLinkedList(ListNode head) {
    if (head == null || head.next == null) {
      return head; // No zipping needed for empty or single-node lists
    }

    // Step 1: Use slow and fast pointers to find the middle of the list
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }

    // Step 2: Split the list into two halves
    ListNode firstHalf = head;
    ListNode secondHalf = slow.next;
    slow.next = null; // Break the list into two halves

    // Step 3: Reverse the second half of the list
    secondHalf = reverseList(secondHalf);

    // Step 4: Merge (zip) the two halves
    return mergeLists(firstHalf, secondHalf);
  }

  // Helper function to reverse a linked list
  private static ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;
    while (current != null) {
      ListNode nextNode = current.next;
      current.next = prev;
      prev = current;
      current = nextNode;
    }
    return prev;
  }

  // Helper function to merge two lists in a zip pattern
  private static ListNode mergeLists(ListNode first, ListNode second) {
    ListNode mergedHead = first;
    ListNode temp1, temp2;

    // Zip the two lists together
    while (first != null && second != null) {
      temp1 = first.next;
      temp2 = second.next;

      first.next = second; // Link from the first list
      if (temp1 == null) break; // If the first list has no more nodes, stop
      second.next = temp1; // Link from the second list

      first = temp1;
      second = temp2;
    }

    return mergedHead;
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

  // Main function to test the zip linked list operation
  public static void main(String[] args) {
    // Example 1: Create linked list 1 -> 2 -> 3 -> 4 -> 5
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);
    head.next.next.next.next = new ListNode(5);

    System.out.println("Original List:");
    printList(head); // Output: 1 2 3 4 5

    // Zip the list
    ListNode result = zipLinkedList(head);
    System.out.println("Zipped List:");
    printList(result); // Output: 1 5 2 4 3

    // Example 2: Create linked list 1 -> 2 -> 3 -> 4
    head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);

    System.out.println("Original List:");
    printList(head); // Output: 1 2 3 4

    // Zip the list
    result = zipLinkedList(head);
    System.out.println("Zipped List:");
    printList(result); // Output: 1 4 2 3
  }

  /*
   * Time Complexity:
   * O(n), where n is the number of nodes in the linked list.
   * - Finding the middle of the list takes O(n).
   * - Reversing the second half takes O(n/2).
   * - Merging the two halves takes O(n).
   *
   * Space Complexity:
   * O(1), because we are modifying the list in place and using only a constant amount of extra space for the pointers.
   */
}
