package veryhard.linkedlists;

/*
 * Problem: Rearrange Linked List
 *
 * You are given a singly linked list and an integer k. Write a function that rearranges the linked list such that all nodes less than k come before nodes equal to or greater than k.
 * The relative ordering of nodes within each group should remain the same.
 *
 * Example 1:
 * Input: head = 3 -> 0 -> 5 -> 2 -> 1 -> 4, k = 3
 * Output: 0 -> 2 -> 1 -> 3 -> 5 -> 4
 *
 * Example 2:
 * Input: head = 2 -> 1 -> 7 -> 8 -> 5, k = 5
 * Output: 2 -> 1 -> 5 -> 7 -> 8
 */

/*
 * Solution Steps:
 * 1. Initialize three linked lists:
 *    a. One for nodes less than k.
 *    b. One for nodes equal to or greater than k.
 * 2. Traverse the input linked list and place each node into one of the two lists based on the comparison with k.
 * 3. Combine the two linked lists:
 *    a. Append the "greater or equal to" list after the "less than" list.
 * 4. Return the rearranged list.
 */

public class A02RearrangeLinkedList {

  // Definition for singly linked list node
  static class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Function to rearrange the linked list based on the value k
  public static ListNode rearrangeLinkedList(ListNode head, int k) {
    // Pointers for the "less than k" and "greater than or equal to k" lists
    ListNode lessHead = null, lessTail = null;
    ListNode greaterEqualHead = null, greaterEqualTail = null;

    // Traverse the input linked list
    ListNode current = head;
    while (current != null) {
      if (current.value < k) {
        // Add node to the "less than k" list
        if (lessHead == null) {
          lessHead = lessTail = current;
        } else {
          lessTail.next = current;
          lessTail = current;
        }
      } else {
        // Add node to the "greater than or equal to k" list
        if (greaterEqualHead == null) {
          greaterEqualHead = greaterEqualTail = current;
        } else {
          greaterEqualTail.next = current;
          greaterEqualTail = current;
        }
      }
      current = current.next;
    }

    // If there are no nodes less than k, return the greaterEqual list
    if (lessHead == null) {
      return greaterEqualHead;
    }

    // Connect the two lists
    lessTail.next = greaterEqualHead;

    // Ensure the new tail node's next pointer is null
    if (greaterEqualTail != null) {
      greaterEqualTail.next = null;
    }

    return lessHead;
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

  // Main function to test the rearrangement of the linked list
  public static void main(String[] args) {
    // Example 1: Create linked list 3 -> 0 -> 5 -> 2 -> 1 -> 4
    ListNode head = new ListNode(3);
    head.next = new ListNode(0);
    head.next.next = new ListNode(5);
    head.next.next.next = new ListNode(2);
    head.next.next.next.next = new ListNode(1);
    head.next.next.next.next.next = new ListNode(4);

    System.out.println("Original List:");
    printList(head); // Output: 3 0 5 2 1 4

    // Rearrange the list around k = 3
    ListNode result = rearrangeLinkedList(head, 3);
    System.out.println("Rearranged List around k = 3:");
    printList(result); // Output: 0 2 1 3 5 4

    // Example 2: Create linked list 2 -> 1 -> 7 -> 8 -> 5
    head = new ListNode(2);
    head.next = new ListNode(1);
    head.next.next = new ListNode(7);
    head.next.next.next = new ListNode(8);
    head.next.next.next.next = new ListNode(5);

    System.out.println("Original List:");
    printList(head); // Output: 2 1 7 8 5

    // Rearrange the list around k = 5
    result = rearrangeLinkedList(head, 5);
    System.out.println("Rearranged List around k = 5:");
    printList(result); // Output: 2 1 5 7 8
  }

  /*
   * Time Complexity:
   * O(n), where n is the number of nodes in the linked list.
   * - We traverse the linked list once, classifying nodes into the "less than" or "greater than or equal to" lists.
   *
   * Space Complexity:
   * O(1), since we're only using a few pointers to rearrange the nodes in place, without using extra memory for new nodes.
   */
}
