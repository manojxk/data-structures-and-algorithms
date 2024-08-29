/*
 * Problem Statement:
 * Given two binary trees, write a function that merges them and returns the resulting tree.
 * If two nodes overlap during the merge, the value of the merged node should be the sum of
 * the overlapping nodes' values.
 *
 * You can either mutate the input trees or return a new tree. Each BinaryTree node has an
 * integer value, a left child node, and a right child node. Children nodes can either be
 * BinaryTree nodes themselves or null.
 *
 * Example:
 *
 * tree1 =    1
 *           /   \
 *          3     2
 *        /   \
 *       7     4
 *
 * tree2 =    1
 *           /   \
 *          5     9
 *        /      / \
 *       2      7   6
 *
 * Output:
 *
 * output =   2
 *           /   \
 *          8     11
 *        /  \    /  \
 *       9    4  7    6
 */
/*Brute Force Solution
Approach:
The brute force solution involves traversing both trees simultaneously. If both nodes exist, we sum their values; otherwise, we take the node that exists.

Time Complexity:
O(n): Where n is the total number of nodes in the smaller of the two trees. Each node is visited once.
Space Complexity:
O(h): Where h is the height of the tree. The space complexity is due to the recursion stack*/

/*
Optimized Solution: Iterative Approach
Approach:
An optimized solution could involve using a queue or stack for an iterative approach. We push pairs of nodes from both trees into the stack/queue and process them iteratively. This can avoid the deeper recursion stack if the trees are large.

Time Complexity:
O(n): Similar to the recursive approach, where n is the total number of nodes in the smaller tree.
Space Complexity:
O(h): Space complexity is determined by the stack/queue used in the iterative approach, which will store up to h levels of nodes.*/

package medium;

import java.util.Stack;

public class MergeBinaryTrees {

  // Brute Force Solution: Recursive Approach
  public static BinaryTree mergeTrees(BinaryTree tree1, BinaryTree tree2) {
    if (tree1 == null) return tree2;
    if (tree2 == null) return tree1;

    // Merge the values of the overlapping nodes
    tree1.value += tree2.value;

    // Recursively merge the left and right subtrees
    tree1.left = mergeTrees(tree1.left, tree2.left);
    tree1.right = mergeTrees(tree1.right, tree2.right);

    return tree1; // Return the merged tree
  }

  // Helper class to define the structure of a BinaryTree node
  static class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;

    BinaryTree(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  // Optimized Solution: Iterative Approach using Stack
  public static BinaryTree mergeTreesIterative(BinaryTree tree1, BinaryTree tree2) {
    if (tree1 == null) return tree2;
    if (tree2 == null) return tree1;

    Stack<Pair> stack = new Stack<>();
    stack.push(new Pair(tree1, tree2));

    while (!stack.isEmpty()) {
      Pair current = stack.pop();

      if (current.tree1 == null || current.tree2 == null) continue;

      // Merge the values of the overlapping nodes
      current.tree1.value += current.tree2.value;

      // Process the left child
      if (current.tree1.left == null) {
        current.tree1.left = current.tree2.left;
      } else {
        stack.push(new Pair(current.tree1.left, current.tree2.left));
      }

      // Process the right child
      if (current.tree1.right == null) {
        current.tree1.right = current.tree2.right;
      } else {
        stack.push(new Pair(current.tree1.right, current.tree2.right));
      }
    }

    return tree1; // Return the merged tree
  }

  // Helper class to hold pairs of nodes for the iterative approach
  static class Pair {
    BinaryTree tree1;
    BinaryTree tree2;

    Pair(BinaryTree tree1, BinaryTree tree2) {
      this.tree1 = tree1;
      this.tree2 = tree2;
    }
  }

  public static void main(String[] args) {
    // Constructing tree1
    BinaryTree tree1 = new BinaryTree(1);
    tree1.left = new BinaryTree(3);
    tree1.right = new BinaryTree(2);
    tree1.left.left = new BinaryTree(7);
    tree1.left.right = new BinaryTree(4);

    // Constructing tree2
    BinaryTree tree2 = new BinaryTree(1);
    tree2.left = new BinaryTree(5);
    tree2.right = new BinaryTree(9);
    tree2.left.left = new BinaryTree(2);
    tree2.right.left = new BinaryTree(7);
    tree2.right.right = new BinaryTree(6);

    // Merging the two trees
    BinaryTree mergedTree = mergeTrees(tree1, tree2);

    // Output the root value of the merged tree (just for demonstration)
    System.out.println(mergedTree.value); // Output: 2
  }
}
