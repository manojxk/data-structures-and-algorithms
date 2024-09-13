package cools.linkedlist;

/*
 Problem: Remove Nth Node From End of List

 Given the head of a linked list, remove the nth node from the end of the list and return its head.

 Example 1:
 Input: head = [1,2,3,4,5], n = 2
 Output: [1,2,3,5]
 Explanation: The 2nd node from the end is 4, so we remove it.

 Example 2:
 Input: head = [1], n = 1
 Output: []

 Example 3:
 Input: head = [1,2], n = 1
 Output: [1]

 Constraints:
 - The number of nodes in the list is in the range [1, 30].
 - 0 <= Node.val <= 100
 - 1 <= n <= the length of the list

 Solution Approach:
 1. Use the **two-pointer technique**:
    - First, move the fast pointer `n` steps ahead.
    - Then move both slow and fast pointers together until the fast pointer reaches the end of the list.
    - The slow pointer will now be just before the nth node from the end. Remove the nth node by adjusting the `next` pointer.
 2. Edge cases such as removing the first node or a single-node list are handled using a dummy node.
*/

public class A03RemoveNthNodeFromEnd {

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

  // Function to remove the nth node from the end of the list
  public ListNode removeNthFromEnd(ListNode head, int n) {
    // Dummy node to handle edge cases like removing the first node
    ListNode dummy = new ListNode(0);
    dummy.next = head;

    // Initialize two pointers, both starting at the dummy node
    ListNode slow = dummy;
    ListNode fast = dummy;

    // Move the fast pointer n steps ahead
    for (int i = 0; i < n + 1; i++) {
      fast = fast.next;
    }

    // Move both pointers until the fast pointer reaches the end
    while (fast != null) {
      slow = slow.next;
      fast = fast.next;
    }

    // Remove the nth node from the end
    slow.next = slow.next.next;

    // Return the head of the modified list
    return dummy.next;
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
    A03RemoveNthNodeFromEnd solution = new A03RemoveNthNodeFromEnd();

    // Example 1: Remove the 2nd node from the end of the list
    ListNode head1 =
        new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
    System.out.println("Original List:");
    printList(head1);
    ListNode result1 = solution.removeNthFromEnd(head1, 2);
    System.out.println("List after removing 2nd node from the end:");
    printList(result1); // Output: [1, 2, 3, 5]

    // Example 2: Remove the only node
    ListNode head2 = new ListNode(1);
    System.out.println("Original List:");
    printList(head2);
    ListNode result2 = solution.removeNthFromEnd(head2, 1);
    System.out.println("List after removing the node:");
    printList(result2); // Output: []

    // Example 3: Remove the last node
    ListNode head3 = new ListNode(1, new ListNode(2));
    System.out.println("Original List:");
    printList(head3);
    ListNode result3 = solution.removeNthFromEnd(head3, 1);
    System.out.println("List after removing the last node:");
    printList(result3); // Output: [1]
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the linked list. We traverse the list twice: once to move the fast pointer n steps,
     and once to move both pointers to the end.

   Space Complexity:
   - O(1), since we are only using a constant amount of extra space for the two pointers.
  */
}
