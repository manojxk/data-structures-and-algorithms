package medium.linkedlists;

/*
 Problem: Remove Kth Node From End of Linked List

 Given the head of a singly linked list, remove the k-th node from the end of the list and return its head.

 Example 1:

 Input: 1 -> 2 -> 3 -> 4 -> 5, k = 2
 Output: 1 -> 2 -> 3 -> 5

 Explanation: The 2nd node from the end of the list is 4, and it is removed.

 Example 2:

 Input: 1 -> 2, k = 2
 Output: 2

 Explanation: The 2nd node from the end of the list is 1, and it is removed.
*/

/*
 Solution Steps:

 1. Use two pointers, both starting at the head of the list.
 2. Move the first pointer `k` steps ahead in the list.
 3. Then, move both pointers simultaneously until the first pointer reaches the end of the list.
    a. When the first pointer reaches the end, the second pointer will be at the (k+1)th node from the end.
    b. Remove the k-th node by adjusting the `next` pointer of the (k+1)th node.
 4. Handle edge cases, such as when removing the head of the list (if k is equal to the length of the list).
*/

public class A02RemoveKthNodeFromEnd {

  // Definition for singly-linked list node
  static class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
      this.val = val;
      this.next = null;
    }
  }

  // Function to remove the k-th node from the end of the list
  public ListNode removeKthFromEnd(ListNode head, int k) {
    // Initialize two pointers
    ListNode first = head;
    ListNode second = head;

    // Move the first pointer `k` steps ahead
    for (int i = 0; i < k; i++) {
      first = first.next;
    }

    // Edge case: if `first` is null after moving `k` steps, it means we need to remove the head
    // node
    if (first == null) {
      return head.next; // Remove the head node
    }

    // Move both pointers until the first pointer reaches the end of the list
    while (first.next != null) {
      first = first.next;
      second = second.next;
    }

    // Remove the k-th node by adjusting the `next` pointer
    second.next = second.next.next;

    return head;
  }

  // Helper method to print the linked list (for testing)
  public static void printList(ListNode head) {
    ListNode current = head;
    while (current != null) {
      System.out.print(current.val + " ");
      current = current.next;
    }
    System.out.println();
  }

  // Main function to demonstrate removing k-th node from end
  public static void main(String[] args) {
    A02RemoveKthNodeFromEnd solution = new A02RemoveKthNodeFromEnd();

    // Example: Create a linked list: 1 -> 2 -> 3 -> 4 -> 5
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);
    head.next.next.next.next = new ListNode(5);

    System.out.println("Original List:");
    printList(head); // Output: 1 2 3 4 5

    // Remove the 2nd node from the end (4)
    head = solution.removeKthFromEnd(head, 2);
    System.out.println("After removing 2nd node from end:");
    printList(head); // Output: 1 2 3 5
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the list. We traverse the list twice:
     1. First to move the `first` pointer `k` steps ahead.
     2. Then to move both pointers until the `first` pointer reaches the end.

   Space Complexity:
   - O(1), as we are using only a constant amount of extra space for the two pointers.
  */
}
