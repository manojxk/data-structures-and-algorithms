package veryhard.linkedlists;

/*
 * Problem: Node Swap (Swap Pairs in a Linked List)
 *
 * Given the head of a singly linked list, write a function that swaps every two adjacent nodes and returns the head of the modified list.
 *
 * Example 1:
 * Input: 1 -> 2 -> 3 -> 4
 * Output: 2 -> 1 -> 4 -> 3
 *
 * Example 2:
 * Input: 1 -> 2 -> 3
 * Output: 2 -> 1 -> 3
 *
 * The function should swap every pair of adjacent nodes. If there is an odd number of nodes, the last node should remain in its position.
 */

/*
 * Solution Steps:
 *
 * 1. Create a dummy node before the head to simplify edge cases.
 * 2. Traverse the list in pairs, swapping each pair of adjacent nodes.
 * 3. Adjust the pointers such that each pair is reversed, and the nodes are correctly linked.
 * 4. Continue until all pairs are swapped, or the end of the list is reached.
 * 5. Return the new head of the modified list.
 */

public class A05NodeSwap {

  // Definition for singly linked list node
  static class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Function to swap every two adjacent nodes in the linked list
  public static ListNode swapPairs(ListNode head) {
    // Step 1: Create a dummy node before the head to handle edge cases
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prevNode = dummy;

    // Step 2: Traverse the list in pairs and swap them
    while (head != null && head.next != null) {
      // Nodes to be swapped
      ListNode firstNode = head;
      ListNode secondNode = head.next;

      // Step 3: Perform the swap
      prevNode.next = secondNode;
      firstNode.next = secondNode.next;
      secondNode.next = firstNode;

      // Step 4: Move pointers forward to the next pair
      prevNode = firstNode;
      head = firstNode.next;
    }

    // Return the new head of the swapped list
    return dummy.next;
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

  // Main function to test the node swap operation
  public static void main(String[] args) {
    // Example 1: Create linked list 1 -> 2 -> 3 -> 4
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);

    System.out.println("Original List:");
    printList(head); // Output: 1 2 3 4

    // Swap the nodes in pairs
    ListNode result = swapPairs(head);
    System.out.println("Swapped List:");
    printList(result); // Output: 2 1 4 3

    // Example 2: Create linked list 1 -> 2 -> 3
    head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);

    System.out.println("Original List:");
    printList(head); // Output: 1 2 3

    // Swap the nodes in pairs
    result = swapPairs(head);
    System.out.println("Swapped List:");
    printList(result); // Output: 2 1 3
  }

  /*
   * Time Complexity:
   * O(n), where n is the number of nodes in the linked list. Each node is visited once.
   *
   * Space Complexity:
   * O(1), because we are swapping the nodes in place and using only a constant amount of extra space.
   */
}
