/*
Problem Description
You are given three nodes from an ancestral tree:

topAncestor - the top node of the tree.
descendantOne - one of the descendant nodes.
        descendantTwo - another descendant node.
The goal is to find the youngest common ancestor of descendantOne and descendantTwo. The AncestralTree class has an ancestor property pointing to its parent in the tree. Each node can be its own ancestor.

        Approach
Find Depth of Nodes:

Determine the depth of both descendants (descendantOne and descendantTwo) relative to the topAncestor.
Equalize Depths:

If one node is deeper (i.e., further from the top ancestor), move it up the tree until both nodes are at the same depth.
Find Youngest Common Ancestor:

Move both nodes up simultaneously until they meet. The meeting point is the youngest common ancestor.
*/

package medium.graphs;

public class YoungestCommonAncestor {

  static class AncestralTree {
    public AncestralTree ancestor;
  }

  /**
   * Finds the youngest common ancestor of two descendants.
   *
   * @param topAncestor The top ancestor of the tree.
   * @param descendantOne The first descendant.
   * @param descendantTwo The second descendant.
   * @return The youngest common ancestor of descendantOne and descendantTwo.
   */
  public static AncestralTree getYoungestCommonAncestor(
      AncestralTree topAncestor, AncestralTree descendantOne, AncestralTree descendantTwo) {
    int depthOne = getDepth(descendantOne, topAncestor);
    int depthTwo = getDepth(descendantTwo, topAncestor);

    // Equalize the depth of both nodes
    if (depthOne > depthTwo) {
      descendantOne = moveUpToEqualDepth(descendantOne, depthOne - depthTwo);
    } else if (depthTwo > depthOne) {
      descendantTwo = moveUpToEqualDepth(descendantTwo, depthTwo - depthOne);
    }

    // Move both nodes up until they meet
    while (descendantOne != descendantTwo) {
      descendantOne = descendantOne.ancestor;
      descendantTwo = descendantTwo.ancestor;
    }

    return descendantOne; // Both are now the same
  }

  /**
   * Gets the depth of a node relative to the top ancestor.
   *
   * @param node The node whose depth is to be found.
   * @param topAncestor The top ancestor of the tree.
   * @return The depth of the node.
   */
  private static int getDepth(AncestralTree node, AncestralTree topAncestor) {
    int depth = 0;
    while (node != topAncestor) {
      node = node.ancestor;
      depth++;
    }
    return depth;
  }

  /**
   * Moves a node up to a specified depth.
   *
   * @param node The node to be moved up.
   * @param depth The number of levels to move up.
   * @return The node after moving up.
   */
  private static AncestralTree moveUpToEqualDepth(AncestralTree node, int depth) {
    while (depth > 0) {
      node = node.ancestor;
      depth--;
    }
    return node;
  }

  public static void main(String[] args) {
    // Example setup for testing
    AncestralTree topAncestor = new AncestralTree();
    AncestralTree A = new AncestralTree();
    AncestralTree B = new AncestralTree();
    AncestralTree C = new AncestralTree();
    AncestralTree D = new AncestralTree();
    AncestralTree E = new AncestralTree();
    AncestralTree F = new AncestralTree();
    AncestralTree G = new AncestralTree();
    AncestralTree H = new AncestralTree();
    AncestralTree I = new AncestralTree();

    topAncestor.ancestor = null;
    A.ancestor = topAncestor;
    B.ancestor = A;
    C.ancestor = A;
    D.ancestor = B;
    E.ancestor = B;
    F.ancestor = C;
    G.ancestor = C;
    H.ancestor = D;
    I.ancestor = D;

    // Test the function
    AncestralTree result = getYoungestCommonAncestor(topAncestor, E, I);
    System.out.println(result == B); // Output: true
  }
}

/*
Time and Space Complexity
Time Complexity:O(d)
Where d is the depth of the deeper node (in the worst case, both nodes could be at the maximum depth of the tree).
Space Complexity:O(1)

The space usage is constant as only a few extra variables are used.*/

/*
// Definition for a binary tree node
class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;

  // Constructor to initialize the node
  TreeNode(int x) {
    val = x;
    left = null;
    right = null;
  }
}

class Solution {
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    // Base case: if the current node is null, or if it matches either p or q
    if (root == null || root.val == p.val || root.val == q.val) {
      return root;
    }

    // Recursively find the LCA in the left and right subtrees
    TreeNode leftLca = lowestCommonAncestor(root.left, p, q);
    TreeNode rightLca = lowestCommonAncestor(root.right, p, q);

    // If both leftLca and rightLca are not null, it means p and q are found in
    // different subtrees, hence the current node is their LCA
    if (leftLca != null && rightLca != null) {
      return root;
    }

    // If only one of the subtrees contains p or q, return that subtree's LCA
    return (leftLca != null) ? leftLca : rightLca;
  }
  public static void main(String[] args) {
        // Construct a binary tree
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);

        // Find the LCA of nodes with values 5 and 1
        Solution solution = new Solution();
        TreeNode lca = solution.lowestCommonAncestor(root, root.left, root.right);

        System.out.println("LCA of 5 and 1 is: " + lca.val); // Output should be 3
    }
}*/
