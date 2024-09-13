package cools.linkedlist;

/*
 Problem: Copy List with Random Pointer

 A linked list of length n is given such that each node contains an additional random pointer, which could point to any node in the list, or null.
 The task is to construct a deep copy of the list. Each node in the copied list should be a new node, and both the `next` and `random` pointers
 should point to nodes in the copied list (not the original list).

 Example:
 Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]

 Constraints:
 - 0 <= n <= 1000
 - -10000 <= Node.val <= 10000
 - Node.random is null or points to a node in the linked list.

 Solution Approach:
 1. We will solve the problem in three passes:
    - **First Pass**: Create new nodes and insert each new node right next to its original node. This helps us easily set the random pointers in the second pass.
    - **Second Pass**: Set the random pointers for the copied nodes using the interleaved list structure.
    - **Third Pass**: Separate the original and copied nodes into two distinct lists.
*/

public class A02CopyListWithRandomPointer {
  // Definition for a Node
  static class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
      this.val = val;
      this.next = null;
      this.random = null;
    }
  }

  // Function to copy the linked list with random pointer
  public Node copyRandomList(Node head) {
    if (head == null) {
      return null;
    }

    // Step 1: Create new nodes and insert them after the original nodes
    Node current = head;
    while (current != null) {
      Node newNode = new Node(current.val);
      newNode.next = current.next;
      current.next = newNode;
      current = newNode.next;
    }

    // Step 2: Set the random pointers for the new nodes
    current = head;
    while (current != null) {
      if (current.random != null) {
        current.next.random = current.random.next; // Copy the random pointer
      }
      current = current.next.next; // Move to the next original node
    }

    // Step 3: Separate the original list and the copied list
    Node original = head;
    Node copy = head.next;
    Node copyHead = copy; // This will be the head of the copied list
    while (original != null) {
      original.next = original.next.next;
      if (copy.next != null) {
        copy.next = copy.next.next;
      }
      original = original.next;
      copy = copy.next;
    }

    return copyHead; // Return the head of the copied list
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    // Example linked list creation
    Node head = new Node(7);
    head.next = new Node(13);
    head.next.random = head;
    head.next.next = new Node(11);
    head.next.next.random = head.next;
    head.next.next.next = new Node(10);
    head.next.next.next.random = head.next.next;
    head.next.next.next.next = new Node(1);
    head.next.next.next.next.random = head;

    A02CopyListWithRandomPointer solution = new A02CopyListWithRandomPointer();
    Node copiedList = solution.copyRandomList(head);

    // Output the copied list to verify
    printList(copiedList); // This should print the values and random pointers of the copied list
  }

  // Helper function to print a linked list with random pointers
  public static void printList(Node head) {
    while (head != null) {
      System.out.print(
          "Node val: "
              + head.val
              + ", Random points to: "
              + (head.random != null ? head.random.val : "null")
              + "\n");
      head = head.next;
    }
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the original list. We traverse the list three times.

   Space Complexity:
   - O(1), since we are only using constant extra space for the new nodes (excluding the space for the new list itself).
  */
}
