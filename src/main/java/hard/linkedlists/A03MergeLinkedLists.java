package hard.linkedlists;

/*
 * Problem: Merging Linked Lists
 *
 * You are given two sorted linked lists. Write a function that merges the two linked lists into a single, sorted linked list.
 * The merged linked list should be returned as a new list.
 *
 * Each LinkedList node has an integer value as well as a next node pointing to the next node in the list or to null if it's the tail of the list.
 *
 * Example:
 *
 * Input:
 * linkedListOne = 1 -> 3 -> 5 -> 7
 * linkedListTwo = 2 -> 4 -> 6 -> 8
 *
 * Output:
 * 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8
 *
 * Example 2:
 *
 * Input:
 * linkedListOne = 5 -> 6 -> 7
 * linkedListTwo = 1 -> 2 -> 3
 *
 * Output:
 * 1 -> 2 -> 3 -> 5 -> 6 -> 7
 */

/*
Solution Steps:
1. Initialize a dummy node to help build the merged list easily.
2. Use two pointers to iterate through both linked lists (`l1` and `l2`).
3. Compare the values at the current nodes of both lists:
   - If the value in `l1` is smaller, append it to the result and move the `l1` pointer forward.
   - If the value in `l2` is smaller, append it to the result and move the `l2` pointer forward.
4. When one of the lists is fully traversed, append the remaining nodes of the other list to the result.
5. Return the merged list, which starts after the dummy node.
*/

public class A03MergeLinkedLists {

  // Definition for singly-linked list node
  static class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Function to merge two sorted linked lists
  public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    // Dummy node to act as the head of the merged list
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;

    // Traverse both lists and merge them
    while (l1 != null && l2 != null) {
      if (l1.value <= l2.value) {
        current.next = l1; // Add l1 node to the result
        l1 = l1.next; // Move l1 pointer forward
      } else {
        current.next = l2; // Add l2 node to the result
        l2 = l2.next; // Move l2 pointer forward
      }
      current = current.next; // Move the result pointer forward
    }

    // If one of the lists is fully traversed, append the other list
    if (l1 != null) {
      current.next = l1;
    } else if (l2 != null) {
      current.next = l2;
    }

    // Return the merged list, starting after the dummy node
    return dummy.next;
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

  // Main function to demonstrate merging two linked lists
  public static void main(String[] args) {
    // Example 1: Create two sorted linked lists
    ListNode l1 = new ListNode(1);
    l1.next = new ListNode(3);
    l1.next.next = new ListNode(5);
    l1.next.next.next = new ListNode(7);

    ListNode l2 = new ListNode(2);
    l2.next = new ListNode(4);
    l2.next.next = new ListNode(6);
    l2.next.next.next = new ListNode(8);

    // Merge the two lists
    ListNode mergedList = mergeTwoLists(l1, l2);

    // Print the merged list
    System.out.println("Merged List:");
    printList(mergedList); // Output: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8
  }

  /*
   * Time Complexity:
   * O(n + m), where n and m are the lengths of the two input linked lists.
   * This is because we iterate through both linked lists once, comparing nodes.
   *
   * Space Complexity:
   * O(1), because we are merging the linked lists in place and only using constant extra space for pointers.
   */
}
