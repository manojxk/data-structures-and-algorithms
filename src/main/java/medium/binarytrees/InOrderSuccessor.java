/*To find the in-order successor of a given node in a binary tree where each node has a pointer to its parent, we can leverage the properties of in-order traversal. In-order traversal visits nodes in the following order:

Left subtree
Current node
Right subtree
Key Observations:
If the node has a right child, then the successor is the leftmost node in the node's right subtree.
If the node doesn't have a right child, the successor is one of the node's ancestors. Specifically, it's the first ancestor whose left child is also an ancestor of the node.
Steps:
Right Child Exists: If the node has a right child, go to the right child and then keep traversing left until you reach the leftmost node. This node is the successor.
No Right Child: If the node does not have a right child, move up to the parent. Continue moving up until you move from a left child to its parent. That parent will be the successor.
No Successor: If neither of the above conditions is met, the node has no in-order successor (it’s the last node in the in-order traversal).
Time Complexity:
O(h), where h is the height of the tree. In the worst case, you may have to traverse up the tree from the node to the root, which takes O(h) time.
Space Complexity:
O(1): No extra space is required beyond the input.*/

package medium.binarytrees;

public class InOrderSuccessor {
  static class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;
    BinaryTree parent;

    BinaryTree(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
      this.parent = null;
    }
  }

  public static BinaryTree findSuccessor(BinaryTree node) {
    // Case 1: Node has a right child
    if (node.right != null) {
      return getLeftmostChild(node.right);
    }

    // Case 2: Node has no right child, look at the parent
    return getRightAncestor(node);
  }

  private static BinaryTree getLeftmostChild(BinaryTree node) {
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

  private static BinaryTree getRightAncestor(BinaryTree node) {
    BinaryTree current = node;
    BinaryTree parent = node.parent;

    // Move up the tree until we find a node that is a left child of its parent
    while (parent != null && parent.right == current) {
      current = parent;
      parent = parent.parent;
    }

    return parent;
  }

  public static void main(String[] args) {
    BinaryTree root = new BinaryTree(1);
    BinaryTree node2 = new BinaryTree(2);
    BinaryTree node3 = new BinaryTree(3);
    BinaryTree node4 = new BinaryTree(4);
    BinaryTree node5 = new BinaryTree(5);
    BinaryTree node6 = new BinaryTree(6);

    root.left = node2;
    root.right = node3;
    node2.left = node4;
    node2.right = node5;
    node4.left = node6;

    node2.parent = root;
    node3.parent = root;
    node4.parent = node2;
    node5.parent = node2;
    node6.parent = node4;

    BinaryTree successor = findSuccessor(node5);
    if (successor != null) {
      System.out.println(successor.value); // Output should be 1
    } else {
      System.out.println("No successor");
    }
  }
}

/*
Explanation:
findSuccessor Function:
Case 1: If the node has a right subtree, we find its in-order successor by getting the leftmost node of that subtree.
Case 2: If there is no right subtree, we trace the parent pointers until we find a node that is the left child of its parent, which will be our successor.
getLeftmostChild Function: This function finds the leftmost child of a node, which is useful for finding the successor in a right subtree.
getRightAncestor Function: This function traverses up the tree to find the first ancestor that is the parent of a left child, which will be the successor.
This approach efficiently finds the in-order successor by considering both cases (with and without a right child) and handles edge cases gracefully.*/
