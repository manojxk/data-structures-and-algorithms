Below are six classic backtracking problems. For each one, we’ll:

1. **Explain the Problem** in simple terms.
2. **Describe the Approach** step by step—how a beginner can think about constructing all valid solutions.
3. **Analyze Time & Space Complexity**.
4. **Relate to the Provided Java Code**, pointing out exactly how each backtracking step maps to the code.

---

## 1. Subsets (Power Set) – Distinct Integers

### Problem

> Given an array of **distinct** integers, return a list of all possible subsets (the power set).
> The final solution should include the empty subset and should **not** contain any duplicate subsets.

For example, if `nums = [1, 2, 3]`, then all subsets are:

```
[
  [], 
  [1], [2], [3], 
  [1,2], [1,3], [2,3], 
  [1,2,3]
]
```

### Approach (Backtracking)

1. **Start with an empty “current” subset** (call it `subset = []`). At any point, every prefix of a valid subset is a legitimate node in the “decision tree.”
2. **Add the current subset to the powerset** before you try to extend it. This ensures we collect duplicates of all sizes—from size 0 up to size n.
3. **Iterate** through the array starting at index `start`. For each index `i`:

   * **Choose** `nums[i]` by appending it to `subset`.
   * **Recurse** with `start = i+1`, meaning “all future choices must come from positions strictly after `i`.”
   * **Backtrack** by removing the last element from `subset` and continue to the next `i`.

Concretely:

```java
void backtrack(int[] nums, int start) {
    // 1. Add a copy of the partial solution to the results
    powerset.add(new ArrayList<>(subset));

    // 2. Try including each nums[i] for i in [start .. nums.length-1]
    for (int i = start; i < nums.length; i++) {
        subset.add(nums[i]);              // choose nums[i]
        backtrack(nums, i + 1);           // recurse on the “tail” subarray
        subset.remove(subset.size() - 1); // undo the choice
    }
}
```

* When we first call `backtrack(nums, 0)`, it records `[]`.
* Then it “bubbles” up `[1]`, `[1,2]`, `[1,2,3]`, backtracks to `[1]`, tries `[1,3]`, then back to `[]`, tries `[2]`, `[2,3]`, then `[3]`, etc.
* Every time you “enter” a call of `backtrack`, you record the snapshot of `subset` into `powerset`.

### Time and Space Complexity

* **Time Complexity**:
  We generate $2^n$ subsets in total (for $n$ input elements). On each subset‐generation path, we do $O(n)$ work to copy the current `subset` into our final `powerset` list. So the total time is $O(n \times 2^n)$.

* **Space Complexity**:

  * The recursion stack can go as deep as $n$ (if you include one element at each level).
  * We also build up $O(2^n)$ subsets in the `powerset` list, each up to length $n$. That storage is also $O(n \times 2^n)$.
  * Ignoring the output itself, the extra “temporary” space is $O(n)$ for recursion and the current `subset`.

### How the Provided Code Implements This

```java
List<List<Integer>> powerset = new ArrayList<>();
List<Integer> subset = new ArrayList<>();

public List<List<Integer>> subsets(int[] nums) {
  backtrack(nums, 0);
  return powerset;
}

private void backtrack(int[] nums, int start) {
  // (A) Record the current subset (so far) into the final list
  powerset.add(new ArrayList<>(subset));

  // (B) From index = start .. end, try each choice
  for (int i = start; i < nums.length; i++) {
    subset.add(nums[i]);            // Choose nums[i]
    backtrack(nums, i + 1);         // Recurse
    subset.remove(subset.size() - 1); // Backtrack
  }
}
```

* `(A)` copies the current “partial” subset into `powerset`.
* `(B)` loops `i = start ... nums.length-1`, “picks” `nums[i]` then recurses with `start = i+1`.
* The remove‐last‐element line undoes that pick so we can then “try the next i.”

---

## 2. Subsets II (Handling Duplicates)

### Problem

> Given an array of integers **that may contain duplicates**, return all possible subsets (the power set) without containing any duplicate subsets.
> For example, `nums = [1, 2, 2]` should produce:

```
[
  [],
  [1], 
  [1, 2],
  [1, 2, 2],
  [2],
  [2, 2]
]
```

You will *not* output `[2, 1]` or duplicate copies, and you will only keep exactly one `[1,2]`.

### Approach (Backtracking + Skip Duplicates)

All steps from **Subsets I** apply, **plus**:

1. **Sort the array** first. That groups equal values together.
2. In the for‐loop, whenever you see `nums[i] == nums[i - 1]` (and $i$ is strictly greater than `start`), you must **skip** that iteration. Otherwise you generate duplicate subsets that differ only by choosing the “second” copy of the same value first.

Concretely:

```java
Arrays.sort(nums);
backtrack(nums, 0);

private void backtrack(int[] nums, int start) {
  powerset.add(new ArrayList<>(subset));

  for (int i = start; i < nums.length; i++) {
    // If this value is the same as the previous one and
    // you haven’t moved start forward, skip to avoid duplicate subsets.
    if (i > start && nums[i] == nums[i - 1]) {
      continue;
    }
    subset.add(nums[i]);
    backtrack(nums, i + 1);
    subset.remove(subset.size() - 1);
  }
}
```

* Because `nums` is sorted, all duplicates are adjacent.
* `if (i > start && nums[i] == nums[i - 1]) continue;` means: “If this value is the same as the immediate predecessor, and you’re at the same recursion level (haven’t advanced `start`), skip it.”

### Time and Space Complexity

* **Time**: Again $O(n \times 2^n)$ in the worst case (when all values are distinct). Sorting first takes $O(n \log n)$.
* **Space**: $O(n)$ extra for recursion plus output size $O(n \times 2^n)$.

### Provided Code

```java
List<List<Integer>> powerset = new ArrayList<>();
List<Integer> subset = new ArrayList<>();

public List<List<Integer>> subsets(int[] nums) {
  Arrays.sort(nums);         // Sort first to handle duplicates
  backtrack(nums, 0);
  return powerset;
}

private void backtrack(int[] nums, int start) {
  powerset.add(new ArrayList<>(subset));

  for (int i = start; i < nums.length; i++) {
    // Skip if same as previous at the same recursion level
    if (i > start && nums[i] == nums[i - 1]) continue;
    subset.add(nums[i]);
    backtrack(nums, i + 1);
    subset.remove(subset.size() - 1);
  }
}
```

---

## 3. Combination Sum (Unlimited Repetition)

### Problem

> Given an array of **distinct** integers `candidates` and a target integer `target`, return **all unique combinations** of `candidates` where the sum of the chosen numbers equals `target`.
> You may choose the same number from `candidates` any number of times. Each combination should be a nondecreasing list of numbers. The output need not be in any specific order, but you must not have duplicate combinations.

For example:

* `candidates = [2, 3, 6, 7], target = 7`; valid solutions are `[[7], [2, 2, 3]]`.
* `candidates = [2, 3, 5], target = 8`; valid solutions are `[[2,2,2,2], [2,3,3], [3,5]]`.

### Approach (Backtracking with Reuse)

1. **No sorting or duplicates handling is needed** if `candidates` are guaranteed distinct.
2. Use a “current sum” variable (`currentSum`). Start at 0.
3. Maintain a `start` index so that when you choose `candidates[i]`, you pass `i` (not `i+1`) into the next recursion—because you are allowed to use the same candidate again.
4. At each recursion:

   * If `currentSum == target`, you’ve found a valid combination—“freeze” it by copying the `currentCombination` into `result` and return.
   * Otherwise, iterate `i` from `start` to `candidates.length-1`:

     * If `currentSum + candidates[i] > target`, **skip** (early stop).
     * Otherwise, append `candidates[i]` to `currentCombination`, call `backtrack(candidates, i, target, currentSum + candidates[i])`, then remove last.

```java
void backtrack(int[] candidates, int start, int target, int currentSum) {
  if (currentSum == target) {
    result.add(new ArrayList<>(currentCombination));
    return;
  }

  for (int i = start; i < candidates.length; i++) {
    if (currentSum + candidates[i] > target) continue;  
    currentCombination.add(candidates[i]);
    // pass `i` so we can reuse the same candidate any number of times
    backtrack(candidates, i, target, currentSum + candidates[i]);
    currentCombination.remove(currentCombination.size() - 1);
  }
}
```

### Time and Space Complexity

* **Time Complexity**: In the worst case, the number of valid combinations can be quite large (exponential in the size of `target / (smallest candidate)`), so backtracking enumerates all possibilities. A rough upper bound: $O(N^{\,\frac{T}{\min A}})$ where $N=$ number of candidates and $\min A=$ smallest candidate. In simpler terms, it’s **exponential** in the size of the input.
* **Space Complexity**: $O(\text{target} / \min A)$ for the maximum depth of recursion (the largest possible chain of repeated small candidates), plus the space to store all valid results.

### Provided Code

```java
List<List<Integer>> result = new ArrayList<>();
List<Integer> currentCombination = new ArrayList<>();

public List<List<Integer>> combinationSum(int[] candidates, int target) {
  backtrack(candidates, 0, target, 0);
  return result;
}

private void backtrack(
    int[] candidates, int start, int target, int currentSum) {
  
  // (A) If we’ve hit the target exactly, record a copy
  if (currentSum == target) {
    result.add(new ArrayList<>(currentCombination));
    return;
  }

  // (B) Try each candidate i >= start
  for (int i = start; i < candidates.length; i++) {
    if (currentSum + candidates[i] > target) {
      continue;  // skip any that would overshoot
    }
    currentCombination.add(candidates[i]); // choose it
    // pass `i` again so we can pick this candidate repeatedly
    backtrack(candidates, i, target, currentSum + candidates[i]);
    currentCombination.remove(currentCombination.size() - 1); // backtrack
  }
}
```

---

## 4. Combination Sum II (Each Candidate Used Once, With Duplicates Allowed)

### Problem

> Given an array `candidates` (which may contain duplicates) and a target integer `target`, return all **unique** combinations where the chosen numbers sum to `target`.
> However, each number in `candidates` **may only be used once** in each combination. Also, the solution set must not contain duplicate combinations.

Example:

* `candidates = [10,1,2,7,6,1,5]`, target = 8 → valid unique results:

  ```
  [
    [1,1,6], 
    [1,2,5], 
    [1,7], 
    [2,6]
  ]
  ```
* Notice that although there are two distinct “1”s in the input, `[1,1,6]` uses both once, and we never output `[1,6,1]` or any other ordering; we keep them sorted by the original array’s order.

### Approach (Backtracking + Skip Duplicates + No Reuse)

1. **Sort** `candidates`. This groups identical values together, enabling the skip‐duplicate check.
2. Backtracking parameters: `backtrack(candidates, start, target, currentSum)`, plus a `currentCombination` list.
3. At each recursion:

   * If `currentSum == target`, record the copy of `currentCombination` and return.
   * Otherwise, loop `i` from `start` to `candidates.length - 1`:

     * **Skip duplicates**: If `i > start` AND `candidates[i] == candidates[i-1]`, do `continue`.
     * If `currentSum + candidates[i] > target`, do `continue` or break (since sorted, no future i will work).
     * Otherwise, add `candidates[i]` to `currentCombination`, then call `backtrack(candidates, i+1, target, currentSum + candidates[i])`. (Notice we pass `i+1` because **we cannot reuse** the same value again in this combination.)
     * Finally, `remove` the last element to backtrack.

```java
Arrays.sort(candidates);
backtrack(candidates, 0, target, 0);

private void backtrack(int[] candidates, int start, int target, int currentSum) {
  if (currentSum == target) {
    result.add(new ArrayList<>(currentCombination));
    return;
  }
  for (int i = start; i < candidates.length; i++) {
    if (i > start && candidates[i] == candidates[i - 1]) {
      continue;                  // skip duplicate values at the same level
    }
    if (currentSum + candidates[i] > target) {
      break;                     // no point continuing if this overshoots (array is sorted)
    }
    currentCombination.add(candidates[i]);
    backtrack(candidates, i + 1, target, currentSum + candidates[i]);
    currentCombination.remove(currentCombination.size() - 1);
  }
}
```

### Time and Space Complexity

* **Time**: Still exponential in the worst case, because you must explore all subsets that sum up to `target`. Sorting takes $O(n \log n)$. The backtracking is at worst $O(2^n)$.
* **Space**: Recursion stack up to $O(n)$, plus the output list which can also be large.

### Provided Code

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A04CombinationSumII {
  List<List<Integer>> result = new ArrayList<>();
  List<Integer> currentCombination = new ArrayList<>();

  public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    Arrays.sort(candidates);
    backtrack(candidates, 0, target, 0);
    return result;
  }

  private void backtrack(
      int[] candidates, int start, int target, int currentSum) {
    if (currentSum == target) {
      result.add(new ArrayList<>(currentCombination));
      return;
    }

    for (int i = start; i < candidates.length; i++) {
      // Skip duplicates at the same recursion depth
      if (i > start && candidates[i] == candidates[i - 1]) continue;
      // Early break if overshooting
      if (currentSum + candidates[i] > target) break;

      currentCombination.add(candidates[i]);
      // Move forward to i+1, so each value is used at most once
      backtrack(candidates, i + 1, target, currentSum + candidates[i]);
      currentCombination.remove(currentCombination.size() - 1);
    }
  }
}
```

---

## 5. Permutations (All Orders) – Distinct Integers

### Problem

> Given an array of **distinct** integers, return a list of all possible permutations (all orderings). You can return the permutations in any order.
> For example, if `nums = [1,2,3]`, then the permutations are:

```
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]
```

### Approach (Backtracking with a “Used” Array)

1. Maintain a boolean array `used[]` of the same length, initialized to all `false`. This tells you whether a given `nums[i]` has already appeared in the **current permutation**.
2. Maintain a `currentPermutation` list that you build one element at a time.
3. Whenever `currentPermutation.size() == nums.length`, you’ve used every element exactly once. Record it in `result` (copy it). Return.
4. Otherwise, loop `i` from `0` to `nums.length-1`. If `used[i]` is false:

   * Mark `used[i] = true` and append `nums[i]` to `currentPermutation`.
   * Recurse.
   * On return, remove `nums[i]` from `currentPermutation`, and set `used[i] = false` to backtrack.

```java
boolean[] used = new boolean[nums.length];
void backtrack(int[] nums) {
  if (currentPermutation.size() == nums.length) {
    result.add(new ArrayList<>(currentPermutation));
    return;
  }
  for (int i = 0; i < nums.length; i++) {
    if (used[i]) continue;
    // Choose nums[i]
    used[i] = true;
    currentPermutation.add(nums[i]);
    backtrack(nums);
    // Backtrack:
    currentPermutation.remove(currentPermutation.size() - 1);
    used[i] = false;
  }
}
```

### Time and Space Complexity

* **Time**: $O(n! \times n)$. There are $n!$ permutations, and each time you “record” one you do an $O(n)$ copy.
* **Space**: $O(n)$ for the recursion stack and a bit more for the `used[]` array. The output size itself holds $n!$ permutations, each of length $n$.

### Provided Code

```java
import java.util.ArrayList;
import java.util.List;

public class A05Permutations {

  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> currentPermutation = new ArrayList<>();
    boolean[] used = new boolean[nums.length];

    backtrack(nums, result, currentPermutation, used);
    return result;
  }

  private void backtrack(
      int[] nums, List<List<Integer>> result,
      List<Integer> currentPermutation, boolean[] used) {
    if (currentPermutation.size() == nums.length) {
      result.add(new ArrayList<>(currentPermutation));
      return;
    }

    for (int i = 0; i < nums.length; i++) {
      if (used[i]) continue;    // skip if already in current perm
      used[i] = true;           // choose nums[i]
      currentPermutation.add(nums[i]);

      backtrack(nums, result, currentPermutation, used);

      // Backtrack
      currentPermutation.remove(currentPermutation.size() - 1);
      used[i] = false;
    }
  }
}
```

---

## 6. Permutations II (Distinct Permutations with Duplicates in Input)

### Problem

> Given an array `nums` that **may contain duplicates**, return **all unique permutations**. Two permutations are considered the same if they have the same value‐sequence. You should not return duplicates.

For example, `nums = [1,1,2]` should produce:

```
[
  [1, 1, 2],
  [1, 2, 1],
  [2, 1, 1]
]
```

Notice we do not output the permutation `[1,1,2]` more than once, even though there are two “1”s in the input.

### Approach (Backtracking + Skip Duplicates)

1. **Sort** `nums`. That ensures equal values are adjacent, which will let us detect duplicates easily.
2. Use a boolean `used[]` array (just like Permutations I) to prevent selecting the same index twice in a single permutation.
3. In the main loop of backtracking, when you consider `nums[i]`, if `used[i] == true`, you skip it. Also, if `i > 0` and `nums[i] == nums[i-1]` **and** `used[i-1] == false`, you must skip `nums[i]`. That second condition means “the previous identical value at `i-1` is not used in this position, so picking `nums[i]` now would create a duplicate ordering.”
4. Each time you pick `nums[i]`, set `used[i] = true`, append to the current permutation, and recurse. Then backtrack.

```java
Arrays.sort(nums);
boolean[] used = new boolean[nums.length];
backtrack(nums, result, currentPermutation, used);

private void backtrack(
    int[] nums,
    List<List<Integer>> result,
    List<Integer> currentPermutation,
    boolean[] used) {
  if (currentPermutation.size() == nums.length) {
    result.add(new ArrayList<>(currentPermutation));
    return;
  }
  for (int i = 0; i < nums.length; i++) {
    // (1) If this element is already used in this permutation, skip.
    if (used[i]) continue;
    // (2) If this value equals the previous one, and the previous one is not used at this level, skip.
    if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) {
      continue;
    }
    // Choose nums[i]
    used[i] = true;
    currentPermutation.add(nums[i]);
    backtrack(nums, result, currentPermutation, used);
    // Backtrack
    currentPermutation.remove(currentPermutation.size() - 1);
    used[i] = false;
  }
}
```

### Why the “Skip” Condition Works

* Because the array is sorted, all identical numbers cluster together.
* When you’re at index `i` and see `nums[i] == nums[i-1]`, if you have **not** already used `nums[i-1]` in this partial permutation, then picking `nums[i]` now would generate exactly the same permutation that would have been generated by picking `nums[i-1]` in that same position. We avoid that by skipping `i`.
* If `nums[i-1]` **is** already used in this partial permutation, it means we are somewhere “deeper” than the branch where `nums[i-1]` was chosen, so it’s safe to pick `nums[i]` now (since it will yield a different ordering).

### Time & Space Complexity

* **Time**: Up to $O\bigl(\frac{n!}{\prod (\text{count of each duplicate}!)}\bigr)$, because duplicates reduce the total number of unique permutations. Still, in the worst case (all distinct), it’s $O(n! \times n)$.
* **Space**: $O(n)$ for recursion + `used[]`, plus output storage.

### Provided Code

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A06PermutationsII {
  public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> currentPermutation = new ArrayList<>();
    boolean[] used = new boolean[nums.length];

    Arrays.sort(nums); // Sort to make duplicate detection easy
    backtrack(nums, result, currentPermutation, used);
    return result;
  }

  private void backtrack(
      int[] nums, List<List<Integer>> result,
      List<Integer> currentPermutation, boolean[] used) {

    if (currentPermutation.size() == nums.length) {
      result.add(new ArrayList<>(currentPermutation));
      return;
    }

    for (int i = 0; i < nums.length; i++) {
      // Skip if already used in this permutation
      if (used[i]) continue;

      // Skip duplicates: if nums[i] == nums[i-1] but nums[i-1] is NOT used in this branch,
      // then using nums[i] now would recreate the same permutation that started with nums[i-1].
      if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
        continue;
      }

      // Choose nums[i]
      used[i] = true;
      currentPermutation.add(nums[i]);

      backtrack(nums, result, currentPermutation, used);

      // Backtrack
      currentPermutation.remove(currentPermutation.size() - 1);
      used[i] = false;
    }
  }
}
```

---

### In Summary

* **Subsets I** enumerates every subset by choosing to include or exclude each element in a depth‐first manner.
* **Subsets II** is exactly the same, but you **sort** first and skip adjacent duplicates at the same recursion level.
* **Combination Sum I** (unlimited repetition) lets you reuse the same candidate by calling `backtrack(…, i, …)` so that future levels can pick the same index again.
* **Combination Sum II** (each candidate once) uses `i + 1` to move forward, plus a duplicate‐skip rule (`if (i>start && candidates[i]==candidates[i-1]) continue;`) to avoid the same combination multiple times.
* **Permutations I** (distinct input) uses a `used[]` array; at each position you can pick any unused element.
* **Permutations II** (input may have duplicates) also uses `used[]`, but you first sort and then skip any index `i` where `(i>0 && nums[i]==nums[i-1] && !used[i-1])` to prevent generating the same permutation twice.

All six examples share the same backtracking scaffold—“choose an element, recurse, un‐choose it,” and only differ in how they handle duplicates or repetition. Reviewing these patterns is a great way to get comfortable with the backtracking template!
