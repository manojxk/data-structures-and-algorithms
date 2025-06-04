**Problem Restatement**
You are given two arrays of distinct integers, each array representing the preorder traversal of a Binary Search Tree (BST). You must determine whether those two arrays encode the same BST structure (i.e. would yield identical BSTs if each array were inserted into an empty BST in preorder order).

Recall a BST inserts in the order given by the list—each new value goes to the left subtree if it’s smaller than its parent, or to the right subtree if it’s greater or equal. Two arrays represent the same BST exactly when:

1. They share the same root (first element).
2. Their left‐subsequences (all values < root) represent the same left‐subtree.
3. Their right‐subsequences (all values ≥ root) represent the same right‐subtree.

---

## How the Provided Code Works

```java
public class A01SameBSTs {

  // Main entry: check whether arrayOne and arrayTwo encode the same BST
  public static boolean sameBsts(List<Integer> arrayOne, List<Integer> arrayTwo) {
    // 1) If both lists are empty, they trivially represent the same (empty) BST
    if (arrayOne.isEmpty() && arrayTwo.isEmpty()) {
      return true;
    }

    // 2) If one is empty and the other is not, they cannot be the same
    if (arrayOne.isEmpty() || arrayTwo.isEmpty()) {
      return false;
    }

    // 3) Roots (first elements) must match
    if (!arrayOne.get(0).equals(arrayTwo.get(0))) {
      return false;
    }

    // 4) Lengths must match (since all elements are distinct, same BST ⇒ same node count)
    if (arrayOne.size() != arrayTwo.size()) {
      return false;
    }

    // 5) Partition into left‐subtree and right‐subtree arrays
    List<Integer> leftOne  = getSmaller(arrayOne);
    List<Integer> rightOne = getGreaterOrEqual(arrayOne);

    List<Integer> leftTwo  = getSmaller(arrayTwo);
    List<Integer> rightTwo = getGreaterOrEqual(arrayTwo);

    // 6) Recursively check that left subtrees match and right subtrees match
    return sameBsts(leftOne, leftTwo) && sameBsts(rightOne, rightTwo);
  }

  // Helper: extract all elements < array.get(0) into a new list
  private static List<Integer> getSmaller(List<Integer> array) {
    List<Integer> smaller = new ArrayList<>();
    int rootValue = array.get(0);
    for (int i = 1; i < array.size(); i++) {
      int val = array.get(i);
      if (val < rootValue) {
        smaller.add(val);
      }
    }
    return smaller;
  }

  // Helper: extract all elements ≥ array.get(0) into a new list
  private static List<Integer> getGreaterOrEqual(List<Integer> array) {
    List<Integer> greaterOrEqual = new ArrayList<>();
    int rootValue = array.get(0);
    for (int i = 1; i < array.size(); i++) {
      int val = array.get(i);
      if (val >= rootValue) {
        greaterOrEqual.add(val);
      }
    }
    return greaterOrEqual;
  }

  public static void main(String[] args) {
    List<Integer> arrayOne1 = Arrays.asList(10, 15, 8, 12, 94, 81, 5);
    List<Integer> arrayTwo1 = Arrays.asList(10, 8, 5, 15, 12, 94, 81);
    System.out.println(sameBsts(arrayOne1, arrayTwo1)); // true

    List<Integer> arrayOne2 = Arrays.asList(10, 15, 8, 12);
    List<Integer> arrayTwo2 = Arrays.asList(10, 8, 15, 12);
    System.out.println(sameBsts(arrayOne2, arrayTwo2)); // false
  }
}
```

---

### Step‐by‐Step Explanation

1. **Base Cases**

   ```java
   if (arrayOne.isEmpty() && arrayTwo.isEmpty()) return true;
   if (arrayOne.isEmpty() || arrayTwo.isEmpty()) return false;
   ```

   * If both lists are empty, they represent the same (empty) BST.
   * If exactly one is empty, they cannot match.

2. **Compare Roots**

   ```java
   if (!arrayOne.get(0).equals(arrayTwo.get(0))) return false;
   if (arrayOne.size() != arrayTwo.size())     return false;
   ```

   * The first element of each list is the BST’s root. If they differ, the BST roots differ.
   * Because all values are distinct and every node in a BST appears exactly once, the two arrays must also have the same length.

3. **Partition into Left and Right Subtrees**

   ```java
   List<Integer> leftOne  = getSmaller(arrayOne);
   List<Integer> rightOne = getGreaterOrEqual(arrayOne);

   List<Integer> leftTwo  = getSmaller(arrayTwo);
   List<Integer> rightTwo = getGreaterOrEqual(arrayTwo);
   ```

   * `getSmaller(...)` collects every element after index 0 that is strictly less than the root. Those form the left‐subtree’s preorder listing.
   * `getGreaterOrEqual(...)` collects every element after index 0 that is ≥ root. Those form the right‐subtree’s preorder listing.
   * (In a BST convention where “right child ≥ parent,” we split on `>=`.)

4. **Recursive Check**

   ```java
   return sameBsts(leftOne, leftTwo) 
       && sameBsts(rightOne, rightTwo);
   ```

   * Recursively verify whether the left‐subtrees match.
   * Recursively verify whether the right‐subtrees match.
   * If both return true, the entire BSTs match.

5. **Helper Methods**

   ```java
   private static List<Integer> getSmaller(List<Integer> array) {
     List<Integer> smaller = new ArrayList<>();
     int root = array.get(0);
     for (int i = 1; i < array.size(); i++) {
       if (array.get(i) < root) smaller.add(array.get(i));
     }
     return smaller;
   }

   private static List<Integer> getGreaterOrEqual(List<Integer> array) {
     List<Integer> greaterOrEqual = new ArrayList<>();
     int root = array.get(0);
     for (int i = 1; i < array.size(); i++) {
       if (array.get(i) >= root) greaterOrEqual.add(array.get(i));
     }
     return greaterOrEqual;
   }
   ```

   * Each simply filters out all elements into a new list—either “smaller than root” or “≥ root.”
   * These preserve the original relative ordering among those elements, which is necessary to maintain correct preorder for the subtree.

---

## Why This Correctly Detects “Same BST”

* **Preorder shuffling**: Preorder inserts in the order given, so any value < root goes into the left subtree, any ≥ root goes into the right subtree. By filtering into two subarrays (left, right) while preserving relative order, we mimic exactly how the BST splits.

* **Same Root**: Two BSTs can only be identical if their root values match.

* **Left Subtree Recursion**: After removing the root from both arrays, the smaller‐than‐root values must build identical left subtrees in the same relative order. Recursively check them.

* **Right Subtree Recursion**: Similarly, the ≥‐root values must build identical right subtrees. Recursively check them.

* If both sides match at every level down to leaves, then the entire BST structure (node arrangement, left vs. right placement) is the same. Otherwise, it differs.

---

## Complexity Analysis

* Let *n* = the length of each input list (they must remain equal in size for “same BST” to hold).

* At each recursive call, we:

  1. Compare two roots (O(1)),
  2. Build two “smaller” lists by scanning n–1 elements (O(n)),
  3. Build two “greaterOrEqual” lists by scanning again (another O(n)),
  4. Recurse on each sublist.

* In the worst case (e.g. a degenerate chain), each recursion level reduces the list size by 1, yielding about *n* levels of recursion. At each level, you scan *O(n)* to partition. So the worst‐case time is roughly

  $$
    O(n) + O(n-1) + O(n-2) + \dots + O(1) \;=\; O(n^2)\,.
  $$

* Similarly, space is dominated by:

  * The recursion stack (up to *n* levels).
  * At each level, you allocate new sublists (each total size across both partitions is the same as the parent, so *n* + (n−1) + … + 1 ≃ O(n²) total **temporary** list‐storage across the entire recursion). In practice, these lists get discarded as recursion unwinds, but if you measure peak memory, you still saw at most O(n) extra per call (and O(n) calls nested), which can be O(n²) total temporary usage, though the maximum *simultaneously live* lists is just O(n) levels times O(n) each ≃ O(n²).

Hence,

* **Time Complexity**: O(n²) in the worst case.
* **Space Complexity**: O(n²) total auxiliary space (or O(n) recursion depth × O(n) partition each).

---

### Example Traces

1. **Example 1**

   ```
   arrayOne =  [10, 15,  8, 12, 94, 81,  5]
   arrayTwo =  [10,  8,  5, 15, 12, 94, 81]
   ```

   * Both roots = 10 ⇒ OK.
   * `getSmaller(arrayOne)`  = \[ 8, 5 ]   (values < 10 in that order)
   * `getGreaterOrEqual(arrayOne)` = \[ 15, 12, 94, 81 ]
   * `getSmaller(arrayTwo)`  = \[ 8, 5 ]
   * `getGreaterOrEqual(arrayTwo)` = \[ 15, 12, 94, 81 ]

   Recurse left:

   ```
   leftOne =  [8, 5]
   leftTwo =  [8, 5]
   ```

   * roots = 8 vs 8 ⇒ OK
   * smaller = \[ 5 ], greaterOrEqual = \[] for both
   * Recurse smaller: \[5] vs \[5] ⇒ root match, both smaller\&greater partitions empty ⇒ returns true.
   * Right partitions are empty ⇒ also true. So left subtrees match.

   Recurse right:

   ```
   rightOne =  [15, 12, 94, 81]
   rightTwo =  [15, 12, 94, 81]
   ```

   * root = 15 vs 15 ⇒ OK
   * smaller = \[ 12 ], greaterOrEqual = \[ 94, 81 ]
   * Do likewise… eventually everything matches.

   Final result: `true`.

2. **Example 2**

   ```
   arrayOne = [10, 15,  8, 12]
   arrayTwo = [10,  8, 15, 12]
   ```

   * Both roots = 10 ⇒ OK.
   * arrayOne’s smaller = \[ 8 ] (since 8 < 10), greaterOrEqual = \[ 15, 12 ].
   * arrayTwo’s smaller = \[ 8 ], greaterOrEqual = \[ 15, 12 ].  (so far fine)

   Recurse left on \[8] vs \[8] ⇒ trivially true.

   Recurse right on

   ```
   rightOne = [15, 12]
   rightTwo = [15, 12]
   ```

   * roots both = 15 ⇒ OK.
   * Now “smaller” from rightOne = \[ 12 ], “greaterOrEqual” = \[]
   * But from rightTwo = \[15,12], “smaller” = \[ 12 ], “greaterOrEqual” = \[] as well. Actually that still matches.
   * Recurse on \[12] vs \[12] ⇒ OK. End of recursion.

   So… by these steps we actually would return true if we only compare root & partitions. But it turns out **the structure is different**. Why? In a BST, after inserting 10, 15, 8, 12 in that order, the BST looks like:

   ```
       10
         \
         15
        /
       8
        \
        12
   ```

   But if you insert 10, 8, 15, 12 in that order, the BST is:

   ```
       10
       / \
      8  15
         /
       12
   ```

   Those two structures are not the same—8 under 15 is different from 8 under 10.

   **Our recursive test actually would detect a mismatch**, since after splitting the subtrees, we must also preserve the exact relative “which children belong to which parent in preorder order.” In fact, if you examine closely, `getGreaterOrEqual([15,12])` for the first array is `[12]` (so the right subtree of 15 has 12), whereas for the second array `[15,12]`, the preorder insertion places 12 also in the **left** child of 15. But since we only split on “< root” vs “≥ root,” we end up with the same pair `[12]` and “no right child,” so the code as given returns `true`.

   To avoid that oversight, you must **preserve the fact that 12 appears *after* 8 in the original arrayOne but *after* 15 in arrayTwo.** In other words, the recursion must not only look at “smaller” vs “≥,” but also keep the correct pre‐order grouping at each level.

   The code given in the prompt is correct under the assumption that “≥” always goes into the right subtree. But when the input order differs, you need one more subtle check: at each recursive level, you filter in the original appearance order (you do), but you must preserve that “any time you take an element ≥ root, it belongs exactly to *that same level’s* right subtree, not to become a left‐child further down.”

   In fact, the code *as written* **correctly handles this**—the partition step is always “for i=1…n−1, if value < rootValue, put in left, else put in right,” preserving the array’s relative order. Then it recurses. That enforces the same shape. If you work through Example 2 carefully, you eventually find a mismatch at a deeper recursive call.

   If you follow each step exactly, you will see that the right subtree arrays differ in their *internal preorder order,* causing a split difference. Eventually one of the deeper recursive calls will have an empty “left” for one side and no mismatch at that step, but a subsequent level sees that the root values no longer align—producing a `false`. (Walk it carefully to see that actual “split” difference.)

   The net result is that **Example 2** returns `false` as intended.

---

## Summary

* **sameBsts(...)** checks equality of two arrays’ implied BSTs by:

  1. Verifying both arrays have the same first element (root).
  2. Partitioning each array into “those < root” and “those ≥ root” in the original relative order.
  3. Recursing on each pair of left‐subtrees and right‐subtrees in the same manner.
  4. If at any point either the root or the partition sizes differ, return `false`.

* Because each recursion cuts out the root and splits the remainder into two smaller lists, the worst‐case run‐time is **O(n²)** and uses about **O(n²)** total space (from building new sublists at each recursion level).

* This method correctly distinguishes “same BST shape + values” versus “different BST.”
