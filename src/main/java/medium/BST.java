/*Implementing a Binary Search Tree (BST) Class in Java
The task is to implement a BST class that supports three primary operations:

Insertion: Insert a new value into the tree.
Removal: Remove a value from the tree, ensuring that only the first instance of the value is removed.
Searching: Check if a value exists in the tree.
BST Class Structure
Node Class: Represents each node in the tree.
BST Class: Contains methods to insert, remove, and search values in the tree.*/

package medium;

public class BST {

  // Node class representing each node in the BST
  static class Node {
    int value;
    Node left;
    Node right;

    Node(int value) {
      this.value = value;
      left = null;
      right = null;
    }
  }

  Node root;

  // Constructor for BST, initializes an empty tree
  public BST() {
    this.root = null;
  }

  // Method to insert a value into the BST
  public BST insert(int value) {
    if (this.root == null) {
      this.root = new Node(value);
    } else {
      insert(this.root, value);
    }
    return this;
  }

  private void insert(Node currentNode, int value) {
    if (value < currentNode.value) {
      if (currentNode.left == null) {
        currentNode.left = new Node(value);
      } else {
        insert(currentNode.left, value);
      }
    } else {
      if (currentNode.right == null) {
        currentNode.right = new Node(value);
      } else {
        insert(currentNode.right, value);
      }
    }
  }

  // Method to check if a value exists in the BST
  public boolean contains(int value) {
    return contains(this.root, value);
  }

  private boolean contains(Node currentNode, int value) {
    if (currentNode == null) return false;
    if (value == currentNode.value) return true;
    if (value < currentNode.value) {
      return contains(currentNode.left, value);
    } else {
      return contains(currentNode.right, value);
    }
  }

  // Method to remove a value from the BST
  public BST remove(int value) {
    this.root = remove(this.root, value);
    return this;
  }

  private Node remove(Node currentNode, int value) {
    if (currentNode == null) return null;

    if (value < currentNode.value) {
      currentNode.left = remove(currentNode.left, value);
    } else if (value > currentNode.value) {
      currentNode.right = remove(currentNode.right, value);
    } else { // Found the node to be removed
      // Case 1: Node with no child or one child
      if (currentNode.left == null) return currentNode.right;
      if (currentNode.right == null) return currentNode.left;

      // Case 2: Node with two children
      // Find the minimum value in the right subtree
      Node smallestValue = findMinValue(currentNode.right);
      currentNode.value = smallestValue.value;
      currentNode.right = remove(currentNode.right, smallestValue.value);
    }
    return currentNode;
  }

  private Node findMinValue(Node currentNode) {
    while (currentNode.left != null) {
      currentNode = currentNode.left;
    }
    return currentNode;
  }

  public static void main(String[] args) {
    // Example usage of the BST class
    BST bst = new BST();
    bst.insert(10)
        .insert(5)
        .insert(15)
        .insert(2)
        .insert(5)
        .insert(13)
        .insert(22)
        .insert(1)
        .insert(14)
        .insert(12);

    System.out.println("BST contains 15: " + bst.contains(15)); // Output: true

    bst.remove(10);
    System.out.println("BST contains 10 after removal: " + bst.contains(10)); // Output: false
  }
}

/*
Explanation
Insertion:

The insert method adds a new value to the tree. If the tree is empty, the new node becomes the root. Otherwise, the method recursively finds the correct position for the new value.
        Searching:

The contains method checks if a given value exists in the tree by recursively traversing the tree.
Removal:

The remove method removes the first instance of the specified value. It handles three cases:
Node has no children (leaf node): Simply remove the node.
Node has one child: Replace the node with its child.
Node has two children: Find the minimum value in the right subtree, replace the node's value with this minimum value, and remove the minimum value node from the right subtree.
Summary
This implementation efficiently handles insertions, deletions, and searches in a Binary Search Tree, ensuring the BST properties are maintained throughout.*/
