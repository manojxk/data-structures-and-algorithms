/*
 * Problem Statement:
 *
 * Implement a DoublyLinkedList class that supports the following operations:
 *
 * - Setting the head and tail of the linked list.
 * - Inserting nodes before and after other nodes, as well as at specified positions.
 * - Removing specific nodes and nodes with specified values.
 * - Searching for nodes with a given value.
 *
 * The linked list will contain nodes with integer values, and each node will have
 * references to both the previous node and the next node. The input nodes provided
 * to methods will be either standalone nodes or nodes already existing in the linked list.
 *
 * Requirements:
 * - The class must manage the head and tail pointers efficiently.
 * - It should handle nodes that might already be in the linked list and reposition them accordingly.
 *
 * Example Usage:
 * - Assume the linked list 1 <-> 2 <-> 3 <-> 4 <-> 5 has already been created.
 * - Assume the following standalone nodes: 3, 3, 6.
 *
 * Operations:
 * 1. setHead(4):      4 <-> 1 <-> 2 <-> 3 <-> 5       (Set node with value 4 as the new head)
 * 2. setTail(6):      4 <-> 1 <-> 2 <-> 3 <-> 5 <-> 6 (Set node with value 6 as the new tail)
 * 3. insertBefore(6, 3): 4 <-> 1 <-> 2 <-> 5 <-> 3 <-> 6 (Move node with value 3 before node with value 6)
 * 4. insertAfter(6, 3): 4 <-> 1 <-> 2 <-> 5 <-> 3 <-> 6 <-> 3 (Insert standalone node with value 3 after node with value 6)
 * 5. insertAtPosition(1, 3): 3 <-> 4 <-> 1 <-> 2 <-> 5 <-> 3 <-> 6 <-> 3 (Insert node with value 3 at position 1)
 * 6. removeNodesWithValue(3): 4 <-> 1 <-> 2 <-> 5 <-> 6 (Remove all nodes with value 3)
 * 7. remove(2): 4 <-> 1 <-> 5 <-> 6 (Remove the node with value 2)
 * 8. containsNodeWithValue(5): true (Check if a node with value 5 exists)
 */

package medium.linkedlists;

public class DoublyLinkedList {
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

  Node head;
  Node tail;

  public DoublyLinkedList() {
    this.head = null;
    this.tail = null;
  }

  // Sets the head of the list
  public void setHead(Node node) {
    if (head == null) {
      head = node;
      tail = node;
      return;
    }
    insertBefore(head, node);
  }

  // Sets the tail of the list
  public void setTail(Node node) {
    if (tail == null) {
      setHead(node);
      return;
    }
    insertAfter(tail, node);
  }

  // Inserts a node before a given node
  public void insertBefore(Node node, Node nodeToInsert) {
    if (nodeToInsert == head && nodeToInsert == tail) return;
    remove(nodeToInsert);
    nodeToInsert.prev = node.prev;
    nodeToInsert.next = node;
    if (node.prev == null) {
      head = nodeToInsert;
    } else {
      node.prev.next = nodeToInsert;
    }
    node.prev = nodeToInsert;
  }

  // Inserts a node after a given node
  public void insertAfter(Node node, Node nodeToInsert) {
    if (nodeToInsert == head && nodeToInsert == tail) return;
    remove(nodeToInsert);
    nodeToInsert.prev = node;
    nodeToInsert.next = node.next;
    if (node.next == null) {
      tail = nodeToInsert;
    } else {
      node.next.prev = nodeToInsert;
    }
    node.next = nodeToInsert;
  }

  // Inserts a node at a specific position (1-based index)
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

  // Removes a specific node from the list
  public void remove(Node node) {
    if (node == head) head = head.next;
    if (node == tail) tail = tail.prev;
    removeNodeBindings(node);
  }

  // Removes all nodes with a specific value
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

  // Checks if a node with a specific value exists in the list
  public boolean containsNodeWithValue(int value) {
    Node node = head;
    while (node != null) {
      if (node.value == value) {
        return true;
      }
      node = node.next;
    }
    return false;
  }

  // Helper method to remove node bindings (internal use)
  private void removeNodeBindings(Node node) {
    if (node.prev != null) {
      node.prev.next = node.next;
    }
    if (node.next != null) {
      node.next.prev = node.prev;
    }
    node.prev = null;
    node.next = null;
  }

  public static void main(String[] args) {
    // Example usage
    DoublyLinkedList dll = new DoublyLinkedList();

    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);
    Node node5 = new Node(5);
    Node node6 = new Node(6);

    dll.setHead(node1);
    dll.insertAfter(node1, node2);
    dll.insertAfter(node2, node3);
    dll.insertAfter(node3, node4);
    dll.insertAfter(node4, node5);

    // Example operations
    dll.setHead(node4); // 4 <-> 1 <-> 2 <-> 3 <-> 5
    dll.setTail(node6); // 4 <-> 1 <-> 2 <-> 3 <-> 5 <-> 6
    dll.insertBefore(node6, node3); // 4 <-> 1 <-> 2 <-> 5 <-> 3 <-> 6
    dll.insertAfter(node6, new Node(3)); // 4 <-> 1 <-> 2 <-> 5 <-> 3 <-> 6 <-> 3
    dll.insertAtPosition(1, new Node(3)); // 3 <-> 4 <-> 1 <-> 2 <-> 5 <-> 3 <-> 6 <-> 3
    dll.removeNodesWithValue(3); // 4 <-> 1 <-> 2 <-> 5 <-> 6
    dll.remove(node2); // 4 <-> 1 <-> 5 <-> 6
    System.out.println(dll.containsNodeWithValue(5)); // true
  }
}

/*
Explanation:
Node Class:

Represents an individual node in the doubly linked list with value, prev, and next pointers.
DoublyLinkedList Class:

head and tail pointers are managed to keep track of the start and end of the list.
setHead and setTail methods set the head and tail of the list.
insertBefore, insertAfter, and insertAtPosition methods allow inserting nodes at specific positions relative to other nodes.
remove and removeNodesWithValue methods allow for removing specific nodes or all nodes with a given value.
containsNodeWithValue method checks if the list contains a node with a specific value.
removeNodeBindings is a helper function to disconnect a node from the list, which is used internally by the remove and removeNodesWithValue methods.

Time Complexity:

Insertion/Removal at head or tail: O(1)
Insertion/Removal at a specific position: O(n) in the worst case (traversing the list)
Searching: O(n) in the worst case
Space Complexity:

The space complexity is O(1) for all operations as we are not using any additional space proportional to the size of the input.*/
