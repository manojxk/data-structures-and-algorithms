/*Implementing a Binary Search Tree (BST) Class in Java
The task is to implement a BST class that supports three primary operations:

Insertion: Insert a new value into the tree.
Removal: Remove a value from the tree, ensuring that only the first instance of the value is removed.
Searching: Check if a value exists in the tree.
BST Class Structure
Node Class: Represents each node in the tree.
BST Class: Contains methods to insert, remove, and search values in the tree.*/

package medium.binarysearchtrees;

class BinarySearchTree {
  Node root;

  public BinarySearchTree() {
    root = null;
  }

  public static void main(String[] args) {
    BinarySearchTree tree = new BinarySearchTree();

    // Inserting elements
    tree.insert(50);
    tree.insert(30);
    tree.insert(20);
    tree.insert(40);
    tree.insert(70);
    tree.insert(60);
    tree.insert(80);

    System.out.println("Inorder traversal:");
    tree.inorder();

    // Deleting elements
    tree.delete(20);
    tree.delete(30);

    System.out.println("Inorder traversal after deletion:");
    tree.inorder();

    // Searching for an element
    int searchKey = 70;
    System.out.println("Is " + searchKey + " present in the tree? " + tree.search(searchKey));

    // Traversals
    System.out.println("Preorder traversal:");
    tree.preorder();

    System.out.println("Postorder traversal:");
    tree.postorder();
  }

  // Insertion operation
  void insert(int key) {
    root = insertRec(root, key);
  }

  Node insertRec(Node root, int key) {
    if (root == null) {
      root = new Node(key);
      return root;
    }

    if (key < root.key) root.left = insertRec(root.left, key);
    else if (key > root.key) root.right = insertRec(root.right, key);

    return root;
  }

  // Deletion operation
  void delete(int key) {
    root = deleteRec(root, key);
  }

  Node deleteRec(Node root, int key) {
    if (root == null) return root;

    if (key < root.key) root.left = deleteRec(root.left, key);
    else if (key > root.key) root.right = deleteRec(root.right, key);
    else {
      if (root.left == null) return root.right;
      else if (root.right == null) return root.left;

      root.key = minValue(root.right);

      root.right = deleteRec(root.right, root.key);
    }

    return root;
  }

  int minValue(Node root) {
    int minv = root.key;
    while (root.left != null) {
      minv = root.left.key;
      root = root.left;
    }
    return minv;
  }

  // Search operation
  boolean search(int key) {
    return searchRec(root, key);
  }

  boolean searchRec(Node root, int key) {
    if (root == null) return false;
    if (root.key == key) return true;
    if (root.key < key) return searchRec(root.right, key);
    return searchRec(root.left, key);
  }

  // Inorder traversal
  void inorder() {
    inorderRec(root);
    System.out.println("\n");
  }

  void inorderRec(Node root) {
    if (root != null) {
      inorderRec(root.left);
      System.out.print(root.key + " ");
      inorderRec(root.right);
    }
  }

  // Preorder traversal
  void preorder() {
    preorderRec(root);
    System.out.println("\n");
  }

  void preorderRec(Node root) {
    if (root != null) {
      System.out.print(root.key + " ");
      preorderRec(root.left);
      preorderRec(root.right);
    }
  }

  // Postorder traversal
  void postorder() {
    postorderRec(root);
    System.out.println("\n");
  }

  void postorderRec(Node root) {
    if (root != null) {
      postorderRec(root.left);
      postorderRec(root.right);
      System.out.print(root.key + " ");
    }
  }

  static class Node {
    int key;
    Node left, right;

    public Node(int item) {
      key = item;
      left = right = null;
    }
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
