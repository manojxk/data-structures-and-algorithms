package hard.linkedlists;

/**
 * Problem Statement:
 *
 * <p>Write a function that takes in the head of a singly linked list and an integer `k`, and shifts
 * the list by `k` positions in place (without creating a new list). The function should return the
 * new head of the shifted list.
 *
 * <p>Shifting a linked list means moving its nodes forward or backward. Positive `k` shifts the
 * list forward (toward the tail), and negative `k` shifts the list backward (toward the head).
 * After shifting, the list wraps around where necessary. For example, shifting forward by 1 means
 * the tail becomes the new head.
 *
 * <p>Sample Input: head = 0 -> 1 -> 2 -> 3 -> 4 -> 5 k = 2
 *
 * <p>Sample Output: 4 -> 5 -> 0 -> 1 -> 2 -> 3
 *
 * <p>Time Complexity: O(n), where `n` is the number of nodes in the list. Space Complexity: O(1),
 * as we are shifting the list in place.
 */
public class ShiftLinkedList {
  static class LinkedList {
    int value;
    LinkedList next;

    public LinkedList(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Function to shift the linked list by `k` positions
  public static LinkedList shiftLinkedList(LinkedList head, int k) {
    if (head == null || head.next == null || k == 0) {
      return head;
    }

    // Step 1: Find the length of the list and connect the tail to the head to form a circular list
    LinkedList tail = head;
    int listLength = 1;
    while (tail.next != null) {
      tail = tail.next;
      listLength++;
    }
    tail.next = head; // Connect the tail to the head, making it circular

    // Step 2: Calculate the effective shift (k % listLength)
    int effectiveK = k % listLength;
    if (effectiveK < 0) {
      effectiveK += listLength; // Convert negative shifts to positive equivalents
    }

    // Step 3: Find the new tail (listLength - effectiveK - 1 steps from the original head)
    int newTailPosition = listLength - effectiveK - 1;
    LinkedList newTail = head;
    for (int i = 0; i < newTailPosition; i++) {
      newTail = newTail.next;
    }

    // Step 4: Break the circular link to form the new list
    LinkedList newHead = newTail.next;
    newTail.next = null; // Break the link

    // Return the new head
    return newHead;
  }

  // Main function to test the shiftLinkedList function
  public static void main(String[] args) {
    // Create a linked list 0 -> 1 -> 2 -> 3 -> 4 -> 5
    LinkedList head = new LinkedList(0);
    head.next = new LinkedList(1);
    head.next.next = new LinkedList(2);
    head.next.next.next = new LinkedList(3);
    head.next.next.next.next = new LinkedList(4);
    head.next.next.next.next.next = new LinkedList(5);

    // Shift the linked list by 2 positions
    LinkedList newHead = shiftLinkedList(head, 2);

    // Print the shifted list
    LinkedList current = newHead;
    System.out.print("Shifted List: ");
    while (current != null) {
      System.out.print(current.value + " ");
      current = current.next;
    }
  }
}
