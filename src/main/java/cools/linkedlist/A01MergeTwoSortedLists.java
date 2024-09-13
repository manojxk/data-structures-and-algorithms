package cools.linkedlist;

/*
 Problem: Merge Two Sorted Lists

 You are given the heads of two sorted linked lists list1 and list2.
 Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.

 Return the head of the merged linked list.

 Example 1:
 Input: list1 = [1,2,4], list2 = [1,3,4]
 Output: [1,1,2,3,4,4]

 Example 2:
 Input: list1 = [], list2 = []
 Output: []

 Example 3:
 Input: list1 = [], list2 = [0]
 Output: [0]

 Constraints:
 - The number of nodes in both lists is in the range [0, 50].
 - -100 <= Node.val <= 100
 - Both list1 and list2 are sorted in non-decreasing order.

 Solution Approach:
 1. Use a **two-pointer technique** to traverse both linked lists simultaneously.
 2. Compare the current nodes of both lists and add the smaller node to the merged list.
 3. Continue until all nodes from both lists have been added to the merged list.
*/

public class A01MergeTwoSortedLists {

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

  // Function to merge two sorted linked lists
  public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    // Dummy node to serve as the starting point of the merged list
    ListNode dummy = new ListNode(-1);
    ListNode current = dummy;

    // Traverse both lists and add the smaller node to the merged list
    while (list1 != null && list2 != null) {
      if (list1.val <= list2.val) {
        current.next = list1;
        list1 = list1.next;
      } else {
        current.next = list2;
        list2 = list2.next;
      }
      current = current.next; // Move to the next node in the merged list
    }

    // If any nodes remain in list1, add them to the merged list
    if (list1 != null) {
      current.next = list1;
    }

    // If any nodes remain in list2, add them to the merged list
    if (list2 != null) {
      current.next = list2;
    }

    // Return the merged list, starting from dummy.next
    return dummy.next;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01MergeTwoSortedLists solution = new A01MergeTwoSortedLists();

    // Example 1
    ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
    ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
    ListNode mergedList1 = solution.mergeTwoLists(list1, list2);
    printList(mergedList1); // Output: 1 -> 1 -> 2 -> 3 -> 4 -> 4

    // Example 2
    ListNode list3 = null;
    ListNode list4 = null;
    ListNode mergedList2 = solution.mergeTwoLists(list3, list4);
    printList(mergedList2); // Output: []

    // Example 3
    ListNode list5 = null;
    ListNode list6 = new ListNode(0);
    ListNode mergedList3 = solution.mergeTwoLists(list5, list6);
    printList(mergedList3); // Output: 0
  }

  // Helper function to print a linked list
  public static void printList(ListNode head) {
    while (head != null) {
      System.out.print(head.val + " -> ");
      head = head.next;
    }
    System.out.println("null");
  }

  /*
   Time Complexity:
   - O(n + m), where n and m are the lengths of list1 and list2 respectively. We traverse both lists once.

   Space Complexity:
   - O(1), since we are merging the nodes in-place without using additional space proportional to the input size.
  */
}
