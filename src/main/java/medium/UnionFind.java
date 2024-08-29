package medium;

import java.util.HashMap;
import java.util.Map;

public class UnionFind {
  // Map to store the parent of each node
  private Map<Integer, Integer> parent;
  // Map to store the rank (depth) of each tree
  private Map<Integer, Integer> rank;

  public UnionFind() {
    parent = new HashMap<>();
    rank = new HashMap<>();
  }

  // Creates a new set with the given value
  public void createSet(int value) {
    if (!parent.containsKey(value)) {
      parent.put(value, value);
      rank.put(value, 0);
    }
  }

  // Finds the representative of the set containing the value
  public Integer find(int value) {
    if (!parent.containsKey(value)) {
      return null;
    }
    // Path compression
    if (parent.get(value) != value) {
      parent.put(value, find(parent.get(value)));
    }
    return parent.get(value);
  }

  // Unites the sets containing valueOne and valueTwo
  public void union(int valueOne, int valueTwo) {
    Integer rootOne = find(valueOne);
    Integer rootTwo = find(valueTwo);

    if (rootOne == null || rootTwo == null || rootOne.equals(rootTwo)) {
      return;
    }

    // Union by rank
    if (rank.get(rootOne) > rank.get(rootTwo)) {
      parent.put(rootTwo, rootOne);
    } else if (rank.get(rootOne) < rank.get(rootTwo)) {
      parent.put(rootOne, rootTwo);
    } else {
      parent.put(rootTwo, rootOne);
      rank.put(rootOne, rank.get(rootOne) + 1);
    }
  }

  public static void main(String[] args) {
    UnionFind uf = new UnionFind();

    uf.createSet(5);
    uf.createSet(10);
    System.out.println(uf.find(5)); // Output: 5
    System.out.println(uf.find(10)); // Output: 10

    uf.union(5, 10);
    System.out.println(uf.find(5)); // Output: 5 (or 10 depending on implementation)
    System.out.println(uf.find(10)); // Output: 5 (or 10 depending on implementation)

    uf.createSet(20);
    System.out.println(uf.find(20)); // Output: 20

    uf.union(20, 10);
    System.out.println(uf.find(5)); // Output: 5
    System.out.println(uf.find(10)); // Output: 5
    System.out.println(uf.find(20)); // Output: 5
  }
}

/*Explanation
createSet(value):

Initializes value as its own parent and sets its rank to 0. This means value is its own representative in a new set.
find(value):

Uses path compression to ensure that all nodes in a set directly point to the representative. This reduces the time complexity of future operations.
union(valueOne, valueTwo):

Finds the representatives (roots) of the sets containing valueOne and valueTwo.
Uses union by rank to merge the sets. The root of the tree with higher rank becomes the parent of the other root. If the ranks are equal, one root becomes the parent of the other and its rank is incremented.
        Time Complexity
CreateSet: O(1)
Find: O(α(n)) where α is the inverse Ackermann function, very slow-growing and effectively constant for all practical purposes.
        Union: O(α(n))
Space Complexity
O(n): We maintain parent and rank maps, each requiring space proportional to the number of unique values.*/
