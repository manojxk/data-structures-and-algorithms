package hard.linkedlists;

/*
 * Problem Statement:
 *
 * You are given the head of a singly linked list that contains a loop. The tail node of the
 * linked list points to some other node within the list, creating a cycle. The task is to return
 * the node (not just its value) where the loop begins. The solution should use constant space (O(1)
 * space complexity).
 *
 * The singly linked list contains nodes with an integer value and a next pointer to the next
 * node in the list.
 *
 * Example:
 * Input: head = 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6
 *                      ^                     v
 *                      9 <- 8 <- 7
 *
 * Output: Node with value 4 (the start of the loop)
 *
 * Time Complexity: O(n) where `n` is the number of nodes in the linked list.
 * Space Complexity: O(1) because we use only two pointers (slow and fast).
 */

public class A01LoopDetection {

  // Definition for the LinkedList node
  static class LinkedList {
    int value;
    LinkedList next;

    public LinkedList(int value) {
      this.value = value;
      this.next = null;
    }
  }

  /*
   * Approach:
   * 1. Detect if a cycle exists using Floyd's Cycle Detection Algorithm (Tortoise and Hare approach).
   * 2. If a cycle is detected, find the starting node of the loop.
   * 3. The solution works in O(n) time complexity and uses O(1) extra space.
   */

  // Function to find the node where the loop starts
  public static LinkedList findLoopOrigin(LinkedList head) {
    LinkedList slow = head; // Tortoise
    LinkedList fast = head; // Hare

    // Step 1: Detect if a cycle exists using Tortoise and Hare approach
    while (fast != null && fast.next != null) {
      slow = slow.next; // Move by 1 step
      fast = fast.next.next; // Move by 2 steps

      if (slow == fast) { // Loop detected
        break;
      }
    }

    // Step 2: If no loop exists, return null
    if (fast == null || fast.next == null) {
      return null; // No cycle in the linked list
    }

    // Step 3: Find the start of the loop
    slow = head; // Reset slow to head
    while (slow != fast) {
      slow = slow.next; // Move both pointers by 1 step
      fast = fast.next;
    }

    // The node where they meet is the start of the loop
    return slow;
  }

  // Main function to test the implementation
  public static void main(String[] args) {
    // Creating a linked list with a loop for testing
    LinkedList head = new LinkedList(0);
    head.next = new LinkedList(1);
    head.next.next = new LinkedList(2);
    head.next.next.next = new LinkedList(3);
    head.next.next.next.next = new LinkedList(4);
    head.next.next.next.next.next = new LinkedList(5);
    head.next.next.next.next.next.next = new LinkedList(6);
    head.next.next.next.next.next.next.next = new LinkedList(7);
    head.next.next.next.next.next.next.next.next = new LinkedList(8);
    head.next.next.next.next.next.next.next.next.next = new LinkedList(9);

    // Creating the loop by pointing the next of node 9 to node 4
    head.next.next.next.next.next.next.next.next.next.next = head.next.next.next.next;

    // Detect and print the node where the loop starts
    LinkedList loopStart = findLoopOrigin(head);
    if (loopStart != null) {
      System.out.println("Loop starts at node with value: " + loopStart.value);
    } else {
      System.out.println("No loop detected.");
    }
  }
}

/*
 * Explanation of Floyd’s Cycle Detection Algorithm (Tortoise and Hare):
 *
 * Step 1 (Cycle Detection):
 * - Use two pointers: a slow pointer (Tortoise) that moves one step at a time, and a fast pointer (Hare) that moves two steps at a time.
 * - If the list contains a cycle, the slow and fast pointers will eventually meet within the loop.
 *
 * Step 2 (Finding the Start of the Loop):
 * - Once the cycle is detected (i.e., slow and fast pointers meet), reset the slow pointer to the head of the list.
 * - Move both slow and fast pointers one step at a time. The node where they meet is the starting point of the loop.
 *
 * Time Complexity:
 * - O(n), where `n` is the number of nodes in the linked list. Both pointers traverse the list a maximum of `n` steps.
 *
 * Space Complexity:
 * - O(1), as we are using only two pointers (slow and fast) without additional data structures.
 */
