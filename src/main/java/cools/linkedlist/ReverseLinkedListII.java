package cools.linkedlist;

/*
 Problem: Reverse Linked List II

 Given the head of a singly linked list and two integers left and right where left <= right, reverse the nodes of the list
 from position left to position right, and return the reversed list.

 Example 1:
 Input: head = [1,2,3,4,5], left = 2, right = 4
 Output: [1,4,3,2,5]

 Example 2:
 Input: head = [5], left = 1, right = 1
 Output: [5]

 Constraints:
 - The number of nodes in the list is n.
 - 1 <= n <= 500
 - -500 <= Node.val <= 500
 - 1 <= left <= right <= n

 Solution Approach:
 1. Traverse the list to reach the node at position `left`.
 2. Reverse the sublist between positions `left` and `right`.
 3. Reconnect the reversed sublist back to the original list.

*/

public class ReverseLinkedListII {

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

  // Function to reverse the linked list from position left to right
  public ListNode reverseBetween(ListNode head, int left, int right) {
    if (head == null || left == right) {
      return head; // No need to reverse if the list is empty or left == right
    }

    // Dummy node to simplify edge cases (like when left == 1)
    ListNode dummy = new ListNode(0);
    dummy.next = head;

    // Step 1: Reach the node just before the "left" node
    ListNode prev = dummy;
    for (int i = 1; i < left; i++) {
      prev = prev.next;
    }

    // Step 2: Start reversing from the "left" node
    ListNode start = prev.next;
    ListNode then = start.next;

    // Reverse the sublist from left to right
    for (int i = 0; i < right - left; i++) {
      start.next = then.next;
      then.next = prev.next;
      prev.next = then;
      then = start.next;
    }

    return dummy.next; // Return the head of the modified list
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
    ReverseLinkedListII solution = new ReverseLinkedListII();

    // Example 1: Reverse from position 2 to 4
    ListNode head1 =
        new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
    System.out.println("Original List:");
    printList(head1);
    ListNode result1 = solution.reverseBetween(head1, 2, 4);
    System.out.println("Reversed List from position 2 to 4:");
    printList(result1); // Output: [1, 4, 3, 2, 5]

    // Example 2: Single node list
    ListNode head2 = new ListNode(5);
    System.out.println("Original List:");
    printList(head2);
    ListNode result2 = solution.reverseBetween(head2, 1, 1);
    System.out.println("Reversed List:");
    printList(result2); // Output: [5]
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the linked list. We traverse the list to reach the left position and then reverse the sublist in a single pass.

   Space Complexity:
   - O(1), since we only use a few additional pointers to reverse the list and don't require any extra space proportional to the input size.
  */
}
