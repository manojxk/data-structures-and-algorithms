package easy.linkedlists;

/*
 Problem: Remove Duplicates from Linked List

 Given the head of a sorted linked list, remove all duplicates such that each element appears only once.
 Return the linked list with no duplicates.

 Example:

 Input: 1 -> 1 -> 2 -> 3 -> 3
 Output: 1 -> 2 -> 3

 Explanation: The duplicate nodes with value 1 and 3 are removed, leaving a linked list with distinct elements.
*/

/*
 Solution Steps:

 1. If the linked list is empty or has only one node, return the head.
 2. Traverse the linked list, starting from the head.
 3. For each node, check if the next node has the same value as the current node.
    a. If so, skip the next node by changing the `next` pointer of the current node to the node after the next node.
    b. Otherwise, move to the next node.
 4. Continue this process until the end of the linked list is reached.
 5. Return the modified linked list starting from the head.
*/

public class A01RemoveDuplicatesLinkedList {

  // Definition for singly-linked list.
  static class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
      this.val = val;
      this.next = null;
    }
  }

  // Function to remove duplicates from a sorted linked list
  public ListNode deleteDuplicates(ListNode head) {
    // Base case: if the list is empty or has only one node, return the head
    if (head == null || head.next == null) return head;

    // Pointer to traverse the linked list
    ListNode current = head;

    // Traverse the list
    while (current != null && current.next != null) {
      // If the next node has the same value, skip it
      if (current.val == current.next.val) {
        current.next = current.next.next;
      } else {
        // Otherwise, move to the next node
        current = current.next;
      }
    }

    // Return the modified list
    return head;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    A01RemoveDuplicatesLinkedList solution = new A01RemoveDuplicatesLinkedList();

    // Example: Create a sorted linked list: 1 -> 1 -> 2 -> 3 -> 3
    ListNode head = new ListNode(1);
    head.next = new ListNode(1);
    head.next.next = new ListNode(2);
    head.next.next.next = new ListNode(3);
    head.next.next.next.next = new ListNode(3);

    // Remove duplicates and print the resulting linked list
    ListNode result = solution.deleteDuplicates(head);

    // Print the result: 1 -> 2 -> 3
    while (result != null) {
      System.out.print(result.val + " ");
      result = result.next;
    }
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the linked list. We traverse the list once to remove duplicates.

   Space Complexity:
   - O(1), because we are modifying the linked list in place and using a constant amount of extra space.
  */
}
