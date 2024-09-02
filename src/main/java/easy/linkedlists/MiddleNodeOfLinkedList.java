/*
 * Problem Statement:
 *
 * You are given a Singly Linked List with at least one node. Write a function
 * that returns the middle node of the Linked List. If the Linked List has an even
 * number of nodes, your function should return the second of the two middle nodes.
 *
 * Example:
 * Input:
 * linkedList = 2 -> 7 -> 3 -> 5  // Head node with value 2
 *
 * Output:
 * 3 -> 5  // The middle could be 7 or 3, we return the second middle node
 */

package easy.linkedlists;

public class MiddleNodeOfLinkedList {

  // Definition for singly-linked list node
  static class LinkedList {
    int value;
    LinkedList next;

    LinkedList(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Function to find the middle node of the linked list
  public static LinkedList findMiddleNode(LinkedList head) {
    // Step 1: Count the number of nodes
    int count = 0;
    LinkedList currentNode = head;
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

  // Optimized function to find the middle node of the linked list using two pointers
  public static LinkedList findMiddleNodeOptimized(LinkedList head) {
    LinkedList slow = head;
    LinkedList fast = head;

    // Move fast pointer two steps and slow pointer one step
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }

    // When fast reaches the end, slow is at the middle
    return slow;
  }

  public static void main(String[] args) {
    // Creating the linked list from the sample input
    LinkedList head = new LinkedList(2);
    head.next = new LinkedList(7);
    head.next.next = new LinkedList(3);
    head.next.next.next = new LinkedList(5);

    // Finding the middle node
    LinkedList middleNode = findMiddleNode(head);

    // Printing the middle node and the rest of the list
    printLinkedList(middleNode); // Expected output: 3 -> 5
  }

  // Helper function to print the linked list from a given node
  public static void printLinkedList(LinkedList node) {
    while (node != null) {
      System.out.print(node.value);
      node = node.next;
      if (node != null) {
        System.out.print(" -> ");
      }
    }
    System.out.println();
  }
}

/*
Brute Force Approach:

Time Complexity: O(n)
Space Complexity: O(1)
Uses two passes through the list.
Optimized Two-Pointer Approach:

Time Complexity: O(n)
Space Complexity: O(1)
Uses a single pass with two pointers to find the middle efficiently.*/
