package hard.linkedlists;

/**
 * Problem Statement:
 *
 * <p>You are given the head of a singly linked list that contains a loop. The tail node of the
 * linked list points to some other node within the list, creating a cycle. The task is to return
 * the node (not just its value) where the loop begins. The solution should use constant space (O(1)
 * space complexity).
 *
 * <p>The singly linked list contains nodes with an integer value and a next pointer to the next
 * node in the list.
 *
 * <p>Sample Input: head = 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 ^ v 9 <- 8 <- 7
 *
 * <p>Sample Output: Node with value 4 (the start of the loop)
 *
 * <p>Time Complexity: O(n) where `n` is the number of nodes in the linked list. Space Complexity:
 * O(1) because we use only two pointers.
 */
public class LoopDetection {
  static class LinkedList {
    int value;
    LinkedList next;

    public LinkedList(int value) {
      this.value = value;
      this.next = null;
    }
  }

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

    // Step 2: If no loop exists (i.e., fast pointer reached null), return null
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

/*Explanation:
Floyd’s Cycle Detection Algorithm (Tortoise and Hare):

Step 1 (Cycle Detection): Use two pointers, slow and fast. The slow pointer moves one step at a time, and the fast pointer moves two steps at a time. If there's a cycle, the slow and fast pointers will eventually meet.
Step 2 (Find the Start of the Loop): Once a cycle is detected (when slow and fast meet), reset the slow pointer to the head of the list. Move both slow and fast one step at a time. The node where they meet is the start of the loop.
Time Complexity:

O(n): Each pointer traverses the list once. The maximum number of operations is proportional to the length of the list.
Space Complexity:

O(1): Only two pointers (slow and fast) are used, so the space complexity is constant.*/
