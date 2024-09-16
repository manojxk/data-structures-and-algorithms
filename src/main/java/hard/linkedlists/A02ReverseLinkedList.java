package hard.linkedlists;

/*
 * Problem: Reverse Linked List
 *
 * You are given the head of a singly linked list, and the task is to reverse the linked list.
 * You should return the new head of the reversed linked list.
 *
 * Each LinkedList node contains an integer value and a next pointer to the next node in the list.
 *
 * Example:
 *
 * Input: 1 -> 2 -> 3 -> 4 -> 5 -> null
 * Output: 5 -> 4 -> 3 -> 2 -> 1 -> null
 *
 * Time Complexity: O(n) where `n` is the number of nodes in the linked list.
 * Space Complexity: O(1), as we are only using constant space for the pointers.
 */

public class A02ReverseLinkedList {

  // Definition for the LinkedList node
  static class ListNode {
    int value;
    ListNode next;

    public ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  /*
   * Approach:
   * 1. Use three pointers: `prev`, `current`, and `next`.
   * 2. Initialize `prev` to null, `current` to the head of the list.
   * 3. Iterate through the list, reversing the direction of the `next` pointers as we go.
   * 4. Update `prev` to `current`, `current` to `next`, and move forward until `current` becomes null.
   * 5. Once the loop ends, the `prev` pointer will be the new head of the reversed list.
   */

  // Function to reverse a singly linked list
  public static ListNode reverseLinkedList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;
    ListNode next = null;

    while (current != null) {
      next = current.next; // Store the next node
      current.next = prev; // Reverse the link
      prev = current; // Move `prev` to `current`
      current = next; // Move `current` to `next`
    }

    return prev; // `prev` will be the new head after the reversal
  }

  /*
   * Recursive Approach:
   * 1. Recursively reverse the rest of the list.
   * 2. Once the recursive call reaches the last node, reverse the links back while unwinding.
   */

  // Recursive function to reverse a singly linked list
  public static ListNode reverseLinkedListRecursive(ListNode head) {
    // Base case: if the list is empty or only has one node, return the head
    if (head == null || head.next == null) {
      return head;
    }

    // Recursive call to reverse the rest of the list
    ListNode newHead = reverseLinkedListRecursive(head.next);

    // Reverse the link for the current node
    head.next.next = head; // Set the next node's next pointer to the current node
    head.next = null; // Set the current node's next pointer to null (it becomes the new tail)

    return newHead; // Return the new head (the last node in the original list)
  }

  // Helper method to print the linked list (for testing purposes)
  public static void printList(ListNode head) {
    ListNode current = head;
    while (current != null) {
      System.out.print(current.value + " ");
      current = current.next;
    }
    System.out.println();
  }

  // Main function to test the reverse linked list function
  public static void main(String[] args) {
    // Create a linked list: 1 -> 2 -> 3 -> 4 -> 5
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);
    head.next.next.next.next = new ListNode(5);

    // Print the original list
    System.out.println("Original List:");
    printList(head); // Output: 1 2 3 4 5

    // Reverse the list
    ListNode reversedHead = reverseLinkedList(head);

    // Print the reversed list
    System.out.println("Reversed List:");
    printList(reversedHead); // Output: 5 4 3 2 1
  }
}

/*
 * Time Complexity:
 * - O(n), where `n` is the number of nodes in the linked list. We traverse the list once to reverse it.
 *
 * Space Complexity:
 * - O(1), as we only use a constant amount of extra space for the pointers (`prev`, `current`, and `next`).
 */
