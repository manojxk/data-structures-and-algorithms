Below is a concise explanation of both problems, their greedy solutions, and why they run in O(n) time and O(1) extra space.

---

## 1. Jump Game

**Problem Statement**

> You have an array `nums[]`, where each element `nums[i]` is the maximum jump length from index `i`.
> Starting at index 0, determine whether you can reach the last index.

**Examples**

* `nums = [2,3,1,1,4]` → `true`

  * Explanation: From index 0 (value 2), you can jump to index 1. From index 1 (value 3), you can jump directly to the last index.
* `nums = [3,2,1,0,4]` → `false`

  * Explanation: No matter how you jump, you always land on index 3 (value 0), and cannot proceed to index 4.

**Greedy Approach**

1. Maintain a variable `maxReach`, initialized to 0. It represents “the farthest index we can reach so far.”

2. Traverse from `i = 0` up to `i < nums.length`:

   * If `i > maxReach`, we have stepped beyond what was reachable—return `false`.
   * Otherwise, update

     ```
     maxReach = max(maxReach, i + nums[i]);
     ```

     (i.e. “from index i, we could jump as far as `i + nums[i]`; see if that exceeds our previous farthest.”)
   * If at any point `maxReach >= lastIndex`, return `true`.

3. If the loop finishes without ever getting stuck or reaching the end, return `false`.

**Code**

```java
public class JumpGame {
  public boolean canJump(int[] nums) {
    int maxReach = 0;
    int lastIndex = nums.length - 1;

    for (int i = 0; i < nums.length; i++) {
      if (i > maxReach) {
        // We can’t even reach index i
        return false;
      }
      maxReach = Math.max(maxReach, i + nums[i]);
      if (maxReach >= lastIndex) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    JumpGame sol = new JumpGame();

    System.out.println(sol.canJump(new int[]{2,3,1,1,4})); // true
    System.out.println(sol.canJump(new int[]{3,2,1,0,4})); // false
  }
}
```

**Why It Works**

* At each index `i`, you know the farthest you could have jumped to so far (`maxReach`).
* If `i` itself exceeds `maxReach`, you’ve “fallen off” a gap and can’t proceed.
* Otherwise, you update how far you could go if you jumped from `i`.
* The moment `maxReach` reaches or passes the last index, you have a path.

**Complexities**

* **Time:** O(n), since we do a single pass through `nums`.
* **Space:** O(1), only a few integer variables (`maxReach`, `i`).

---

## 2. Jump Game II

**Problem Statement**

> Given a 0‐indexed array `nums[]`, each element `nums[i]` is the maximum jump length from index `i`. You start at index 0 and want to reach the last index in as few jumps as possible.
> Return that minimum number of jumps.

**Examples**

* `nums = [2,3,1,1,4]` → `2`

  * Explanation: Jump from index 0→1, then from index 1→4. Two jumps total.
* `nums = [2,3,0,1,4]` → `2`

  * Explanation: Jump from index 0→1, then index 1→4.

**Greedy “Current Reach vs Farthest Reach”**

1. Keep three variables:

   * `jumps` = 0, the count of jumps taken so far.
   * `currentEnd` = 0, the farthest index you can reach *within* the current number of jumps.
   * `farthest` = 0, the farthest index you could reach *with one additional jump* from any index up to `currentEnd`.

2. Traverse `i` from 0 to `nums.length−2` (we don’t need to check the last index itself):

   * Update `farthest = max(farthest, i + nums[i])`. That means “if we jump once more from any position up to `i`, we could reach at least `farthest`.
   * If `i` has reached `currentEnd`, that means we have “exhausted” all positions we could reach with the current number of jumps.

     * So we must now take a new jump:

       ```
       jumps++;
       currentEnd = farthest;
       ```
     * If that new `currentEnd` ≥ last index, we can return `jumps` early.

3. At the end, `jumps` holds the minimum number required.

**Code**

```java
public class JumpGameII {
  public int jump(int[] nums) {
    if (nums.length == 1) return 0;  // Already at the end—no jumps needed

    int jumps = 0;
    int currentEnd = 0;
    int farthest = 0;
    int lastIndex = nums.length - 1;

    // We only need to go up to the second‐to‐last index
    for (int i = 0; i < lastIndex; i++) {
      farthest = Math.max(farthest, i + nums[i]);

      // When we reach the end of the range covered by the current jump:
      if (i == currentEnd) {
        jumps++;
        currentEnd = farthest;
        if (currentEnd >= lastIndex) {
          break;
        }
      }
    }
    return jumps;
  }

  public static void main(String[] args) {
    JumpGameII sol = new JumpGameII();

    System.out.println(sol.jump(new int[]{2,3,1,1,4})); // 2
    System.out.println(sol.jump(new int[]{2,3,0,1,4})); // 2
  }
}
```

**Why It Works**

* Think of “levels” of BFS on an implicit graph where each node `i` can jump to any `j` in `[i+1, i+nums[i]]`. In BFS, all nodes reachable in `k` jumps come from nodes reachable in `k−1` jumps.
* `currentEnd` marks the rightmost index you can reach with exactly `jumps` jumps.
* As you iterate `i` from 0 upward, you track `farthest`, the rightmost index any node up to `i` can reach with one more jump.
* The moment you pass `currentEnd`, you have finished exploring everything you could with `jumps` jumps, so you increment `jumps` and set `currentEnd = farthest`, meaning “now we will explore everything reachable with one more jump.”
* You keep doing this until `currentEnd` reaches or passes the last index.

**Complexities**

* **Time:** O(n), one pass through the array.
* **Space:** O(1), only a handful of integers.

---

### Summary

* **Jump Game (I):** Keep a running `maxReach`. If at any point your index exceeds `maxReach`, you’re stuck → `false`. If `maxReach` ever reaches the last index, return `true`.
* **Jump Game II:** Use a greedy layering approach: for each “layer” of reachable indices under the current jump count, update how far you can go next (`farthest`). When you exhaust the current layer, increment `jumps` and begin a new layer ending at `farthest`.

Both run in **O(n)** time and **O(1)** extra space.
