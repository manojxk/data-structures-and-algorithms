package cools.linkedlist;

/*
 Problem: Remove Duplicates from Sorted List II

 Given the head of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
 Return the linked list sorted as well.

 Example 1:
 Input: head = [1,2,3,3,4,4,5]
 Output: [1,2,5]

 Example 2:
 Input: head = [1,1,1,2,3]
 Output: [2,3]

 Constraints:
 - The number of nodes in the list is in the range [0, 300].
 - -100 <= Node.val <= 100
 - The list is guaranteed to be sorted in ascending order.

 Solution Approach:
 1. We use a dummy node to handle edge cases easily (such as when the head itself is a duplicate).
 2. Traverse the list and for each node, check if it is a duplicate (i.e., if it has the same value as the next node).
 3. Skip all nodes that are duplicates and adjust the pointers to keep only the distinct nodes.
 4. Finally, return the modified list starting from dummy.next.
*/

public class A04RemoveDuplicatesSortedListII {

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

  // Function to remove duplicates from a sorted list
  public ListNode deleteDuplicates(ListNode head) {
    // Dummy node to handle edge cases
    ListNode dummy = new ListNode(0);
    dummy.next = head;

    // Previous pointer to track the node before the current duplicate sequence
    ListNode prev = dummy;

    // Traverse the linked list
    while (head != null) {
      // Check if current node is a start of duplicates
      if (head.next != null && head.val == head.next.val) {
        // Skip all nodes with the same value
        while (head.next != null && head.val == head.next.val) {
          head = head.next;
        }
        // Connect previous node to the node after the duplicates
        prev.next = head.next;
      } else {
        // No duplicates, move prev pointer
        prev = prev.next;
      }
      // Move head pointer
      head = head.next;
    }

    return dummy.next; // Return the modified list
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
    A04RemoveDuplicatesSortedListII solution = new A04RemoveDuplicatesSortedListII();

    // Example 1: [1,2,3,3,4,4,5]
    ListNode head1 =
        new ListNode(
            1,
            new ListNode(
                2,
                new ListNode(
                    3, new ListNode(3, new ListNode(4, new ListNode(4, new ListNode(5)))))));
    System.out.println("Original List:");
    printList(head1);
    ListNode result1 = solution.deleteDuplicates(head1);
    System.out.println("List after removing duplicates:");
    printList(result1); // Output: [1, 2, 5]

    // Example 2: [1,1,1,2,3]
    ListNode head2 =
        new ListNode(1, new ListNode(1, new ListNode(1, new ListNode(2, new ListNode(3)))));
    System.out.println("Original List:");
    printList(head2);
    ListNode result2 = solution.deleteDuplicates(head2);
    System.out.println("List after removing duplicates:");
    printList(result2); // Output: [2, 3]
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the linked list. We traverse the list once.

   Space Complexity:
   - O(1), since we are only using a constant amount of extra space (ignoring the space used for the output list).
  */
}
