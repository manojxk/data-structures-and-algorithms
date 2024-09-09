package hard.linkedlists;

/**
 * Problem Statement:
 *
 * <p>Write a function that takes in the head of a Singly Linked List, reverses the list in place
 * (i.e., doesn't create a new list), and returns its new head. Each LinkedList node contains an
 * integer value and a next pointer pointing to the next node or null if it's the tail of the list.
 *
 * <p>You can assume that the input linked list always has at least one node (i.e., head will never
 * be null).
 *
 * <p>Sample Input: head = 0 -> 1 -> 2 -> 3 -> 4 -> 5
 *
 * <p>Sample Output: 5 -> 4 -> 3 -> 2 -> 1 -> 0
 *
 * <p>Time Complexity: O(n) where `n` is the number of nodes in the list. Space Complexity: O(1)
 * because the reversal is done in place with no extra space.
 */
public class ReverseLinkedList {
  static class LinkedList {
    int value;
    LinkedList next;

    public LinkedList(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Function to reverse the linked list in place
  public static LinkedList reverseLinkedList(LinkedList head) {
    LinkedList prev = null; // To hold the previous node during reversal
    LinkedList current = head; // The current node we are processing

    while (current != null) {
      LinkedList nextNode = current.next; // Save the next node
      current.next = prev; // Reverse the current node's pointer to the previous node
      prev = current; // Move prev one step forward
      current = nextNode; // Move current one step forward
    }

    // prev will point to the new head of the reversed list
    return prev;
  }

  // Main function to test the reverseLinkedList function
  public static void main(String[] args) {
    // Create a linked list 0 -> 1 -> 2 -> 3 -> 4 -> 5
    LinkedList head = new LinkedList(0);
    head.next = new LinkedList(1);
    head.next.next = new LinkedList(2);
    head.next.next.next = new LinkedList(3);
    head.next.next.next.next = new LinkedList(4);
    head.next.next.next.next.next = new LinkedList(5);

    // Reverse the linked list and get the new head
    LinkedList newHead = reverseLinkedList(head);

    // Print the reversed list
    LinkedList current = newHead;
    System.out.print("Reversed List: ");
    while (current != null) {
      System.out.print(current.value + " ");
      current = current.next;
    }
  }
}
