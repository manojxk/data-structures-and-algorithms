package veryhard.linkedlists;

/*
 * Problem: Linked List Palindrome
 *
 * Given the head of a singly linked list, write a function to determine if the linked list is a palindrome.
 *
 * Example 1:
 * Input: 1 -> 2 -> 2 -> 1
 * Output: true
 *
 * Example 2:
 * Input: 1 -> 2 -> 3
 * Output: false
 *
 * The linked list is a palindrome if it reads the same forwards and backwards.
 */

/*
 * Solution Steps:
 *
 * 1. Use a slow and fast pointer to find the middle of the linked list.
 * 2. Reverse the second half of the list.
 * 3. Compare the first half and the reversed second half node by node.
 * 4. If all nodes match, the linked list is a palindrome. Otherwise, it is not.
 * 5. Optionally, restore the list back to its original form by reversing the second half again (if needed).
 */

public class A03LinkedListPalindrome {

  // Definition for singly linked list node
  static class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Function to check if the linked list is a palindrome
  public static boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) return true;

    // Step 1: Find the middle of the linked list using the slow and fast pointers
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }

    // Step 2: Reverse the second half of the list
    ListNode secondHalfHead = reverseList(slow);

    // Step 3: Compare the first half and the reversed second half
    ListNode firstHalfPointer = head;
    ListNode secondHalfPointer = secondHalfHead;
    boolean isPalindrome = true;
    while (firstHalfPointer != null && secondHalfPointer != null) {
      if (firstHalfPointer.value != secondHalfPointer.value) {
        isPalindrome = false;
        break;
      }
      firstHalfPointer = firstHalfPointer.next;
      secondHalfPointer = secondHalfPointer.next;
    }

    // Step 4: Restore the second half (optional, if you need to restore the list)
    reverseList(secondHalfHead);

    return isPalindrome;
  }

  // Helper function to reverse a linked list
  private static ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;
    while (current != null) {
      ListNode nextNode = current.next;
      current.next = prev;
      prev = current;
      current = nextNode;
    }
    return prev;
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

  // Main function to test the palindrome check
  public static void main(String[] args) {
    // Example 1: Create linked list 1 -> 2 -> 2 -> 1
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(2);
    head.next.next.next = new ListNode(1);

    System.out.println("Original List:");
    printList(head); // Output: 1 2 2 1

    // Check if the list is a palindrome
    boolean result = isPalindrome(head);
    System.out.println("Is Palindrome? " + result); // Output: true

    // Example 2: Create linked list 1 -> 2 -> 3
    head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);

    System.out.println("Original List:");
    printList(head); // Output: 1 2 3

    // Check if the list is a palindrome
    result = isPalindrome(head);
    System.out.println("Is Palindrome? " + result); // Output: false
  }

  /*
   * Time Complexity:
   * O(n), where n is the number of nodes in the linked list.
   * - Finding the middle of the list takes O(n).
   * - Reversing the second half takes O(n/2).
   * - Comparing the two halves takes O(n/2).
   *
   * Space Complexity:
   * O(1), because we are modifying the list in place and using only a constant amount of extra space for the pointers.
   */
}
