/*
 * Problem Statement:
 * Write a function that takes in the head of a Singly Linked List and an integer k,
 * and removes the kth node from the end of the list.
 *
 * The removal should be done in place, meaning that the original data structure
 * should be mutated (no new structure should be created).
 *
 * The input head of the linked list should remain the head of the linked list after
 * the removal is done, even if the head is the node that's supposed to be removed.
 *
 * The function doesn't need to return anything.
 *
 * Assumptions:
 * - The input linked list will always have at least two nodes and, specifically, at least k nodes.
 *
 * Example:
 * Input: head = 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9, k = 4
 * After Removal: 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 7 -> 8 -> 9
 * // The 4th node from the end of the list (the node with value 6) is removed.
 */
/*Brute Force Solution
Approach:
The brute force approach involves two passes through the linked list. In the first pass, determine the length of the list. In the second pass, find and remove the kth node from the end.

Time Complexity:
O(n): We iterate through the list twice—once to calculate the length and once to remove the kth node.
Space Complexity:
O(1): We only use a constant amount of extra space for variables.*/
package medium;

public class RemoveKthNodeFromEnd {
  static class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Brute Force Solution
  public static void removeKthNodeFromEndBruteForce(ListNode head, int k) {
    int length = 0;
    ListNode current = head;

    // Calculate the length of the linked list
    while (current != null) {
      length++;
      current = current.next;
    }

    // Determine the position to remove from the start
    int positionFromStart = length - k;

    // If the head needs to be removed
    if (positionFromStart == 0) {
      head.value = head.next.value;
      head.next = head.next.next;
      return;
    }

    // Traverse to the node just before the one to remove
    current = head;
    for (int i = 1; i < positionFromStart; i++) {
      current = current.next;
    }

    // Remove the kth node from the end
    current.next = current.next.next;
  }

  // Optimized Solution using Two-Pointer Technique
  public static void removeKthNodeFromEnd(ListNode head, int k) {
    ListNode first = head;
    ListNode second = head;
    int counter = 0;

    // Move first pointer k steps ahead
    while (counter < k) {
      first = first.next;
      counter++;
    }

    // If we need to remove the head node
    if (first == null) {
      head.value = head.next.value;
      head.next = head.next.next;
      return;
    }

    // Move both pointers together until first reaches the end
    while (first.next != null) {
      first = first.next;
      second = second.next;
    }

    // Remove the kth node from the end
    second.next = second.next.next;
  }

  public static void main(String[] args) {
    ListNode head = new ListNode(0);
    head.next = new ListNode(1);
    head.next.next = new ListNode(2);
    head.next.next.next = new ListNode(3);
    head.next.next.next.next = new ListNode(4);
    head.next.next.next.next.next = new ListNode(5);
    head.next.next.next.next.next.next = new ListNode(6);
    head.next.next.next.next.next.next.next = new ListNode(7);
    head.next.next.next.next.next.next.next.next = new ListNode(8);
    head.next.next.next.next.next.next.next.next.next = new ListNode(9);

    int k = 4;
    removeKthNodeFromEndBruteForce(head, k);

    printList(head); // Expected output: 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 7 -> 8 -> 9
  }

  // Helper function to print the linked list
  public static void printList(ListNode head) {
    ListNode current = head;
    while (current != null) {
      System.out.print(current.value);
      if (current.next != null) {
        System.out.print(" -> ");
      }
      current = current.next;
    }
    System.out.println();
  }
}
/*
Optimized Solution: Two-Pointer Technique
Approach:
The optimized solution uses a two-pointer technique. The first pointer advances k nodes ahead, and then both pointers move together until the first pointer reaches the end. The second pointer will then be at the node just before the one to remove.

Time Complexity:
O(n): We only traverse the list once, making this a linear time solution.
Space Complexity:
O(1): The extra space used is constant.*/
