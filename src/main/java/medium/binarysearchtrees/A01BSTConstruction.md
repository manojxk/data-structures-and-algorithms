
```java
package medium.binarysearchtrees;

public class BinarySearchTree {
    // Definition of a tree node
    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    /** Public API: insert a value into the BST */
    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    /** Public API: check if a value exists in the BST */
    public boolean contains(int value) {
        return containsRecursive(root, value);
    }

    /** Public API: remove the first occurrence of a value from the BST */
    public void remove(int value) {
        root = removeRecursive(root, value);
    }

    /** ===== Recursive helpers ===== */

    // Inserts value into subtree rooted at 'node' and returns the (possibly new) root
    private Node insertRecursive(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }
        if (value < node.value) {
            node.left = insertRecursive(node.left, value);
        } else {
            node.right = insertRecursive(node.right, value);
        }
        return node;
    }

    // Returns true if value is found in subtree rooted at 'node'
    private boolean containsRecursive(Node node, int value) {
        if (node == null) {
            return false;
        }
        if (value == node.value) {
            return true;
        } else if (value < node.value) {
            return containsRecursive(node.left, value);
        } else {
            return containsRecursive(node.right, value);
        }
    }

    // Removes the first instance of 'value' in the subtree rooted at 'node'
    // and returns the (possibly new) root of this subtree
    private Node removeRecursive(Node node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = removeRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = removeRecursive(node.right, value);
        } else {
            // Found the node to be removed

            // Case 1: no children
            if (node.left == null && node.right == null) {
                return null;
            }
            // Case 2: one child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            // Case 3: two children – replace with inorder successor
            Node successor = findMin(node.right);
            node.value = successor.value;
            // Delete the successor node from the right subtree
            node.right = removeRecursive(node.right, successor.value);
        }
        return node;
    }

    // Finds the node with the minimum value in the subtree rooted at 'node'
    private Node findMin(Node node) {
        return (node.left == null) ? node : findMin(node.left);
    }

    /** Utility: inorder traversal to print the tree (for testing) */
    public void inorderPrint() {
        inorderRecursive(root);
        System.out.println();
    }

    private void inorderRecursive(Node node) {
        if (node == null) return;
        inorderRecursive(node.left);
        System.out.print(node.value + " ");
        inorderRecursive(node.right);
    }

    /** Simple main to demonstrate usage */
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        // Insert some values
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);

        System.out.print("Inorder after inserts: ");
        tree.inorderPrint(); // 20 30 40 50 60 70 80 

        // Search
        System.out.println("Contains 40? " + tree.contains(40)); // true
        System.out.println("Contains 25? " + tree.contains(25)); // false

        // Remove a leaf
        tree.remove(20);
        System.out.print("Inorder after removing 20: ");
        tree.inorderPrint(); // 30 40 50 60 70 80 

        // Remove a node with one child
        tree.remove(30);
        System.out.print("Inorder after removing 30: ");
        tree.inorderPrint(); // 40 50 60 70 80 

        // Remove a node with two children
        tree.remove(50);
        System.out.print("Inorder after removing 50: ");
        tree.inorderPrint(); // 40 60 70 80 
    }
}
```

**Explanation of key parts:**

1. **Node class** holds an integer `value` and pointers to `left` and `right` children.
2. **insertRecursive**

   * If `node` is `null`, create a new node.
   * Otherwise, recurse into left or right subtree based on comparison.
   * Return the (possibly unchanged) `node` so the parent can re‐link correctly.
3. **containsRecursive**

   * If `node` is `null`, return `false`.
   * If `value == node.value`, return `true`.
   * Otherwise, recurse left or right.
4. **removeRecursive**

   * Recurse left/right until finding the node to delete.
   * If it has no children, return `null`.
   * If it has exactly one child, return that child.
   * If it has two children, find the inorder successor (`findMin` in the right subtree), copy its value into `node`, and then delete that successor recursively.
5. **findMin** simply goes left until it hits a leaf, returning the node with the smallest value in that subtree.
6. **inorderPrint** (optional) helps verify the tree’s contents.

This satisfies a purely recursive implementation of insert, remove (first occurrence), and contains.
