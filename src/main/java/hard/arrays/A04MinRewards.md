**Problem Restatement**
You have an array `scores[]` of length *n*, representing the exam scores of students sitting in a line. You want to give each student a positive integer reward (at least 1) so that:

1. Every student gets at least one reward.
2. If a student’s score is higher than an **adjacent** student, then that student must receive strictly more rewards than that neighbor.

Return the **minimum total number of rewards** you need to distribute so that these two conditions hold.

---

## Example Walkthrough

1. **Example 1**

   ```
   scores = [8, 4, 2, 1, 3, 6, 7, 9, 5]
   ```

   A valid way to assign rewards is:

   ```
   rewards = [4, 3, 2, 1, 2, 3, 4, 5, 1]
   ```

   * Notice that anywhere a student has a higher score than the one immediately to the left (or right), they indeed get more rewards:

     * 8>4 → 4>3
     * 4>2 → 3>2
     * 2>1 → 2>1
     * 1<3 → 1<2
     * 3<6 → 2<3
     * 6<7 → 3<4
     * 7<9 → 4<5
     * 9>5 → 5>1
   * Summing those gives 4 + 3 + 2 + 1 + 2 + 3 + 4 + 5 + 1 = 25.
   * It can be shown you cannot do it with fewer than 25 total rewards, so the answer is **25**.

2. **Example 2**

   ```
   scores = [1, 0, 2]
   ```

   * One valid assignment is `[2, 1, 2]`:

     * 1>0 → 2>1
     * 0<2 → 1<2
   * Total = 2 + 1 + 2 = 5. You cannot give fewer than 5 while still satisfying the “higher‐score⇒more‐rewards” rule, so the answer is **5**.

---

## Why a Two‐Pass (“Left → Right” & “Right → Left”) Works

A naive approach might try to repeatedly walk the array and fix any adjacent violation until everything is OK, but that can become complicated and still be O(n²). A more direct O(n) approach is:

1. **Initialize every student’s reward to 1.**
   Because every student must get at least one.

2. **Left → Right pass**

   * Traverse from index 1 up to *n−1*.
   * If `scores[i] > scores[i−1]`, then student *i* must get more rewards than student *i−1*. So do

     ```
     rewards[i] = rewards[i−1] + 1
     ```

     Otherwise (if `scores[i] ≤ scores[i−1]`), leave `rewards[i]` as is (which is 1 if it hasn’t been raised yet).
   * After this pass, you have correctly enforced the “strictly increasing from left to right” constraint wherever it occurs.

3. **Right → Left pass**

   * Now traverse from index *n−2* down to 0.
   * If `scores[i] > scores[i+1]`, then student *i* must have more rewards than student *i+1*—but we might already have given `rewards[i]` more than `rewards[i+1]` during the left→right pass.
   * Therefore, set

     ```
     rewards[i] = max(rewards[i], rewards[i+1] + 1)
     ```

     so that you never break the “higher‐score⇒more‐rewards” rule in the right→left direction.
   * If `scores[i] ≤ scores[i+1]`, you do nothing (leave `rewards[i]` alone).

4. **Sum them all**
   The array `rewards[]` now satisfies both conditions in both directions. Sum its entries to get the minimum total.

---

### Why Taking `max(rewards[i], rewards[i+1] + 1)` Is Necessary

* After the left→right pass, “all left‐neighbors” that have lower scores are guaranteed to have fewer rewards.
* But imagine a valley shape, e.g. `scores = [3, 2, 1, 2, 3]`.

  * After left→right you might have `rewards = [1, 1, 1, 2, 3]`. (Whenever you go down or stay equal, you just keep 1.)
  * Now look right→left:

    * At `i=3`, `scores[3] = 2` is bigger than `scores[4] = 3`? No. So you do nothing.
    * At `i=2`, `scores[2] = 1` is not > `scores[3] = 2`, so do nothing → `rewards[2]` stays 1.
    * At `i=1`, `scores[1] = 2` IS > `scores[2] = 1`. You must ensure `rewards[1] > rewards[2]`. But right now `rewards[2] = 1` and `rewards[1] = 1`. So you do `rewards[1] = max(1, 1+1) = 2`.
    * At `i=0`, `scores[0] = 3` is > `scores[1] = 2`. Now `rewards[1] = 2`, so you do `rewards[0] = max(1, 2+1) = 3`.

  Final `rewards = [3, 2, 1, 2, 3]`. You cannot satisfy the rule by leaving `rewards[1]` or `rewards[0]` at 1 or 2; you must raise them on the second pass. Hence you take the maximum at each step.

---

## Step‐by‐Step Code Explanation

```java
import java.util.Arrays;

public class A04MinRewards {

  public static int minRewards(int[] scores) {
    if (scores == null || scores.length == 0) {
      return 0;      // No students ⇒ no rewards needed
    }

    int n = scores.length;
    int[] rewards = new int[n];
    Arrays.fill(rewards, 1);   // Step 1: Give everyone 1 reward initially

    // Step 2: Left → Right pass
    for (int i = 1; i < n; i++) {
      // If current student scored higher than the left neighbor,
      // give one more reward than that neighbor.
      if (scores[i] > scores[i - 1]) {
        rewards[i] = rewards[i - 1] + 1;
      }
      // Otherwise leave rewards[i] = 1
    }

    // Step 3: Right → Left pass
    for (int i = n - 2; i >= 0; i--) {
      // If current student scored higher than the right neighbor,
      // ensure this student has at least (right neighbor’s rewards + 1).
      if (scores[i] > scores[i + 1]) {
        rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
      }
      // If not, leave rewards[i] as whatever it was from the first pass
    }

    // Step 4: Sum up all rewards
    int totalRewards = 0;
    for (int r : rewards) {
      totalRewards += r;
    }

    return totalRewards;
  }

  public static void main(String[] args) {
    // Example 1
    int[] scores1 = {8, 4, 2, 1, 3, 6, 7, 9, 5};
    System.out.println(minRewards(scores1)); // 25

    // Example 2
    int[] scores2 = {1, 0, 2};
    System.out.println(minRewards(scores2)); // 5
  }
}
```

1. **Initialize**

   ```java
   int[] rewards = new int[n];
   Arrays.fill(rewards, 1);
   ```

   Every student must get at least one reward, so we start everyone at 1.

2. **Left → Right**

   ```java
   for (int i = 1; i < n; i++) {
     if (scores[i] > scores[i - 1]) {
       rewards[i] = rewards[i - 1] + 1;
     }
   }
   ```

   * You compare each student `i` to the student at `i−1`.
   * If `scores[i]` is larger, make `rewards[i]` one more than `rewards[i−1]`.
   * Otherwise, keep `rewards[i] = 1`.

3. **Right → Left**

   ```java
   for (int i = n - 2; i >= 0; i--) {
     if (scores[i] > scores[i + 1]) {
       rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
     }
   }
   ```

   * Now check each student `i` against `i+1`. If `scores[i]` is larger, you want `rewards[i] > rewards[i+1]`.
   * But remember, after the first pass, `rewards[i]` may already be bigger than 1. You take whichever is **larger**:

     * The existing `rewards[i]` from the left→right pass, or
     * `rewards[i+1] + 1` to fix a right‐neighbor violation.

4. **Compute the Sum**
   Finally, sum up `rewards[]`. That total is the minimum number of reward units required.

---

## Time and Space Complexity

* **Time Complexity: O(n)**

  * We do two simple linear passes over the array (left→right and right→left), each taking O(n).
  * Summing up takes another O(n).
  * Overall ∼ O(n).

* **Space Complexity: O(n)**

  * We allocate the `int[] rewards` of size n.
  * Apart from that, only a few constant‐size variables are used.

---

### Why This Is Correct and Minimal

* After the **left→right** pass, all strictly ascending runs from the left are handled.
* After the **right→left** pass, all strictly ascending runs from the right are handled.
* Because you combined them by taking the maximum at each index, you guarantee both conditions in both directions hold simultaneously.
* There is no way to give anybody fewer than the final `rewards[i]` at index *i*, because if you tried to lower any student’s reward, you would break a “higher score ⇒ higher reward” rule either from the left side or the right side.
* Thus the sum you obtain is provably the minimum total.

---

## Final Notes

* If the array has length 1, you must still give that single student 1 reward (so the answer is 1). The code above handles that automatically (it will fill `rewards[0]=1` and sum to 1).
* If all scores are strictly descending, then after the left→right pass everyone still has 1. But the right→left pass will enforce `rewards[0]=2, rewards[1]=3, rewards[2]=4, …`, etc., giving a strictly increasing reward sequence from right to left to satisfy “higher‐score⇒more‐rewards.”
* If all scores are strictly ascending, the left→right pass will give `rewards = [1,2,3,…]`, and the right→left pass won’t change anything (since there are no right‐neighbor violations).

That completes the full, detailed explanation of the two‐pass approach for **Min Rewards**, along with its Java implementation.
