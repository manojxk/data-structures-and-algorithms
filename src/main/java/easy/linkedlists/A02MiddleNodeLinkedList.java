package easy.linkedlists;

/*
 Problem: Find the Middle Node of a Linked List

 Given the head of a singly linked list, return the middle node.
 If there are two middle nodes, return the second middle node.

 Example 1:

 Input: 1 -> 2 -> 3 -> 4 -> 5
 Output: 3

 Example 2:

 Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6
 Output: 4

 Explanation: In Example 1, there are 5 nodes, so the middle node is the 3rd node.
              In Example 2, there are 6 nodes, and the second middle node is the 4th node.
*/

/*
 Solution Steps:

 1. Initialize two pointers: `slow` and `fast`. Both pointers start at the head of the list.
 2. Move `slow` by one node and `fast` by two nodes in each iteration.
 3. When `fast` reaches the end of the list (or there are no more nodes for `fast` to move), `slow` will be at the middle node.
 4. Return the `slow` pointer, which will be pointing at the middle node.
*/

public class A02MiddleNodeLinkedList {

  // Definition for singly-linked list.
  static class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
      this.val = val;
      this.next = null;
    }
  }

  public static ListNode findMiddleNode(ListNode head) {
    // Step 1: Count the number of nodes
    int count = 0;
    ListNode currentNode = head;
    while (currentNode != null) {
      count++;
      currentNode = currentNode.next;
    }

    // Step 2: Find the middle node
    int middleIndex = count / 2; // Second middle if even number of nodes
    currentNode = head;
    for (int i = 0; i < middleIndex; i++) {
      currentNode = currentNode.next;
    }

    // Return the middle node
    return currentNode;
  }

  // Function to find the middle node of the linked list
  public ListNode middleNode(ListNode head) {
    // Initialize two pointers, both starting at the head
    ListNode slow = head;
    ListNode fast = head;

    // Traverse the list: `slow` moves one step, `fast` moves two steps
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }

    // When the loop ends, `slow` is at the middle node
    return slow;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    A02MiddleNodeLinkedList solution = new A02MiddleNodeLinkedList();

    // Example: Create a linked list: 1 -> 2 -> 3 -> 4 -> 5
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);
    head.next.next.next.next = new ListNode(5);

    // Find and print the middle node
    ListNode middle = solution.middleNode(head);
    System.out.println("Middle Node: " + middle.val); // Output: 3

    // Example: Create a linked list with an even number of nodes: 1 -> 2 -> 3 -> 4 -> 5 -> 6
    ListNode head2 = new ListNode(1);
    head2.next = new ListNode(2);
    head2.next.next = new ListNode(3);
    head2.next.next.next = new ListNode(4);
    head2.next.next.next.next = new ListNode(5);
    head2.next.next.next.next.next = new ListNode(6);

    // Find and print the middle node (for even-sized list, it should return the second middle)
    ListNode middle2 = solution.middleNode(head2);
    System.out.println("Middle Node (Even-sized list): " + middle2.val); // Output: 4
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the linked list. We traverse the list with the `fast` pointer at twice the speed of the `slow` pointer.

   Space Complexity:
   - O(1), because we only use a constant amount of extra space for the two pointers (`slow` and `fast`).
  */
}
