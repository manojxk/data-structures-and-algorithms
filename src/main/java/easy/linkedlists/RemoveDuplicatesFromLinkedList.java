/*
 * Problem Statement:
 *
 * You are given the head of a Singly Linked List where the nodes are sorted
 * in ascending order based on their values. Write a function that returns a
 * modified version of the Linked List such that all duplicate values are removed.
 * The linked list should be modified in place, meaning you should not create
 * a new list. The resulting Linked List should maintain the sorted order of
 * its nodes.
 *
 * Example:
 * Input:
 * linkedList = 1 -> 1 -> 3 -> 4 -> 4 -> 4 -> 5 -> 6 -> 6  // Head node with value 1
 *
 * Output:
 * 1 -> 3 -> 4 -> 5 -> 6  // Head node with value 1
 */

package easy.linkedlists;

public class RemoveDuplicatesFromLinkedList {

  // Definition for singly-linked list node
  static class LinkedList {
    int value;
    LinkedList next;

    LinkedList(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Function to remove duplicates from the sorted linked list
  public static LinkedList removeDuplicates(LinkedList head) {
    // Initialize the current node as the head
    LinkedList currentNode = head;
    // Traverse the linked list
    while (currentNode != null && currentNode.next != null) {
      // If the current node's value is the same as the next node's value,
      // skip the next node (remove it from the list)
      if (currentNode.value == currentNode.next.value) {
        currentNode.next = currentNode.next.next;
      } else {
        // Otherwise, move to the next node
        currentNode = currentNode.next;
      }
    }
    // Return the head of the modified list
    return head;
  }

  public static void main(String[] args) {
    // Creating the linked list from the sample input
    LinkedList head = new LinkedList(1);
    head.next = new LinkedList(1);
    head.next.next = new LinkedList(3);
    head.next.next.next = new LinkedList(4);
    head.next.next.next.next = new LinkedList(4);
    head.next.next.next.next.next = new LinkedList(4);
    head.next.next.next.next.next.next = new LinkedList(5);
    head.next.next.next.next.next.next.next = new LinkedList(6);
    head.next.next.next.next.next.next.next.next = new LinkedList(6);

    // Removing duplicates
    LinkedList result = removeDuplicates(head);

    // Printing the modified list
    printLinkedList(result); // Expected output: 1 -> 3 -> 4 -> 5 -> 6
  }

  // Helper function to print the linked list
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

/*Traverse and Compare: O(n) time | O(1) space.
In-Place Modification: The linked list is modified without using additional memory.*/
