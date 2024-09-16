package medium.linkedlists;

/*
 Problem: Linked List Construction

 Implement a basic doubly linked list with the following operations:
 1. Set the head of the list.
 2. Set the tail of the list.
 3. Insert a node before a given node.
 4. Insert a node after a given node.
 5. Insert a node at a specific position (1-indexed).
 6. Remove a node.
 7. Remove all nodes with a specific value.
 8. Search for a node with a specific value.

 Example:

 - Insert nodes: After inserting 1, 2, 3 at head -> [3, 2, 1]
 - Insert at a specific position: Inserting 4 at position 2 -> [3, 4, 2, 1]
 - Remove node with value: Removing node with value 4 -> [3, 2, 1]
 - Search for node: Search for 2 returns true.
*/

/*
 Solution Steps:

 1. Set the head: If the list is empty, set the head and tail to the node.
 2. Set the tail: If the list is empty, set the node as head. Otherwise, append it to the current tail.
 3. Insert Before: Remove the node from its current position (if it exists), then reassign pointers to insert it before the target node.
 4. Insert After: Remove the node from its current position, then reassign pointers to insert it after the target node.
 5. Insert at Position: Traverse the list to the given position and insert the node there.
 6. Remove Node: Adjust pointers to bypass the node to be removed.
 7. Remove Nodes with Value: Traverse the list and remove all nodes matching the given value.
 8. Search for Node: Traverse the list and return `true` if a node with the given value is found.
*/

public class A01DoublyLinkedList {

  // Definition for a doubly linked list node
  static class Node {
    int value;
    Node prev;
    Node next;

    public Node(int value) {
      this.value = value;
      this.prev = null;
      this.next = null;
    }
  }

  // Head and tail pointers
  private Node head;
  private Node tail;

  // Constructor for an empty list
  public A01DoublyLinkedList() {
    this.head = null;
    this.tail = null;
  }

  // Method to set the head of the linked list
  public void setHead(Node node) {
    if (head == null) {
      head = node;
      tail = node;
    } else {
      insertBefore(head, node);
    }
  }

  // Method to set the tail of the linked list
  public void setTail(Node node) {
    if (tail == null) {
      setHead(node);
    } else {
      insertAfter(tail, node);
    }
  }

  // Method to insert a node before a given node
  public void insertBefore(Node node, Node nodeToInsert) {
    if (nodeToInsert == head && nodeToInsert == tail) return; // Node is already in place
    remove(nodeToInsert); // Remove the node if it already exists in the list
    nodeToInsert.prev = node.prev;
    nodeToInsert.next = node;
    if (node.prev == null) {
      head = nodeToInsert;
    } else {
      node.prev.next = nodeToInsert;
    }
    node.prev = nodeToInsert;
  }

  // Method to insert a node after a given node
  public void insertAfter(Node node, Node nodeToInsert) {
    if (nodeToInsert == head && nodeToInsert == tail) return; // Node is already in place
    remove(nodeToInsert); // Remove the node if it already exists in the list
    nodeToInsert.prev = node;
    nodeToInsert.next = node.next;
    if (node.next == null) {
      tail = nodeToInsert;
    } else {
      node.next.prev = nodeToInsert;
    }
    node.next = nodeToInsert;
  }

  // Method to insert a node at a specific position (1-indexed)
  public void insertAtPosition(int position, Node nodeToInsert) {
    if (position == 1) {
      setHead(nodeToInsert);
      return;
    }
    Node node = head;
    int currentPosition = 1;
    while (node != null && currentPosition++ != position) {
      node = node.next;
    }
    if (node != null) {
      insertBefore(node, nodeToInsert);
    } else {
      setTail(nodeToInsert);
    }
  }

  // Method to remove a node from the list
  public void remove(Node node) {
    if (node == head) head = head.next;
    if (node == tail) tail = tail.prev;
    removeNodeBindings(node);
  }

  // Method to remove all nodes with a specific value
  public void removeNodesWithValue(int value) {
    Node node = head;
    while (node != null) {
      Node nodeToRemove = node;
      node = node.next;
      if (nodeToRemove.value == value) {
        remove(nodeToRemove);
      }
    }
  }

  // Method to search for a node with a specific value
  public boolean containsNodeWithValue(int value) {
    Node node = head;
    while (node != null) {
      if (node.value == value) return true;
      node = node.next;
    }
    return false;
  }

  // Helper method to remove node bindings (disconnect a node)
  private void removeNodeBindings(Node node) {
    if (node.prev != null) node.prev.next = node.next;
    if (node.next != null) node.next.prev = node.prev;
    node.prev = null;
    node.next = null;
  }

  // Helper method to print the linked list (for testing purposes)
  public void printList() {
    Node node = head;
    while (node != null) {
      System.out.print(node.value + " ");
      node = node.next;
    }
    System.out.println();
  }

  // Main function to demonstrate linked list operations
  public static void main(String[] args) {
    A01DoublyLinkedList dll = new A01DoublyLinkedList();

    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);
    Node node5 = new Node(5);

    // Test setting head and tail
    dll.setHead(node1);
    dll.setTail(node5);

    // Test inserting nodes
    dll.insertBefore(node5, node3); // Insert 3 before 5
    dll.insertAfter(node1, node2); // Insert 2 after 1
    dll.insertAtPosition(3, node4); // Insert 4 at position 3

    dll.printList(); // Expected output: 1 2 4 3 5

    // Test removing nodes with a specific value
    dll.removeNodesWithValue(4);
    dll.printList(); // Expected output: 1 2 3 5

    // Test if a node with value exists
    System.out.println(dll.containsNodeWithValue(3)); // Expected output: true
    System.out.println(dll.containsNodeWithValue(10)); // Expected output: false
  }

  /*
   Time Complexity:
   - Setting head/tail: O(1)
   - Insert Before/After: O(1)
   - Insert At Position: O(n), where n is the number of nodes (traverse to the correct position).
   - Remove Node: O(1)
   - Remove Nodes with Value: O(n), where n is the number of nodes.
   - Search for Node: O(n), where n is the number of nodes in the list.

   Space Complexity:
   - O(1), since we are modifying the list in place with no additional space usage.
  */
}
