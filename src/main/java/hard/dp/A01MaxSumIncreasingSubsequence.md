Below is a step-by-step explanation of how the **“Max Sum Increasing Subsequence”** solution works.  We’ll assume you are fairly new to dynamic programming and walk through everything from first principles.  At the end, we’ll restate the time and space complexity.

---

## 1. Problem Restatement

> **Given**: an integer array `array[]` of length n (elements may be negative or positive).
> **Return**:
>
> 1. The maximum sum we can obtain by selecting a **strictly increasing subsequence** of `array`.
> 2. One actual subsequence (in array order) that achieves that maximum sum.

A **subsequence** means you pick some indices \<i₁ < i₂ < …< iₖ</i> from the array and take those elements.  “Strictly increasing” means each chosen element must be larger than the one before it.  We want both (a) the largest possible sum of such a chosen subsequence, and (b) the actual list of numbers that achieves it.

### Examples

1. Input: `[10, 70, 20, 30, 50, 11, 30]`
   Output: `110` and the subsequence `[10, 20, 30, 50]`.
   Explanation:

   * Among all increasing subsequences, the one that sums to 110 is: 10 → 20 → 30 → 50.
   * Other increasing subsequences might exist (e.g. `10→70` sums to 80), but 110 is tallest.

2. Input: `[8, 12, 2, 3, 15, 5, 7]`
   Output: `35` and subsequence `[8, 12, 15]`.
   Explanation:

   * 8 → 12 → 15 sums to 35, and you cannot do better with a strictly increasing pick.

---

## 2. Why Dynamic Programming (DP)?

A naive approach would be to enumerate *all* increasing subsequences (there are exponentially many) and compute their sums.  That is too slow when `n` might be up to 1000.  Instead, we notice that the “best sum ending exactly at index i” depends in a simple way on the “best sums ending at each index j < i” whenever `array[j] < array[i]`.  That overlap of subproblems is a classic DP pattern.

Concretely, define:

> **DP state**
> Let `S[i]` = the maximum possible sum of an increasing subsequence **that ends at index i**.
> In other words, we force the subsequence to **include** `array[i]` as its last element.

Once we have computed all `S[i]` for i=0..n−1, the overall answer (maximum‐sum over any increasing subsequence) is simply `max(S[0..n−1])`.  Moreover, to reconstruct which elements were chosen, we also keep track of **“previous index”** pointers.

---

## 3. DP Recurrence

1. **Base case**:
   For each `i`, if you pick only the single element `array[i]` and nothing else, the sum is `array[i]`.  So initialize:

   ```
   S[i] = array[i]   (before we look for any j < i)
   previous[i] = −1  (means “nothing came before i”)
   ```

2. **Transition**:
   To compute `S[i]`, look at all earlier indices `j` with `0 ≤ j < i`.  If `array[j] < array[i]` (so that appending `array[i]` after `array[j]` remains strictly increasing), then we can extend an optimal subsequence ending at `j` by adding `array[i]`.  In that case, the new candidate sum would be

   ```
   candidate = S[j] + array[i].
   ```

   We pick whichever such candidate is largest.  In pseudocode:

   ```
   S[i] = array[i]            // starting sum if we take no j
   previous[i] = −1           // means “our subsequence just starts at i”

   For each j from 0 to i−1:
     if array[j] < array[i] and S[j] + array[i] > S[i]:
       S[i] = S[j] + array[i]
       previous[i] = j
   ```

   That way, `S[i]` always becomes “the best sum of an increasing subsequence that ends in i,” and `previous[i]` remembers which index j we came from.

3. **Overall best**:
   After filling every `S[i]`, the global maximum sum is `maxSum = max_{0 ≤ i < n} S[i]`.  The index `bestIndex` at which this maximum occurs satisfies `S[bestIndex] = maxSum`.

4. **Reconstructing the subsequence**:
   Start at `bestIndex`.  Follow `previous[bestIndex] → previous[ previous[bestIndex] ] → …` until you hit −1.  That sequence of indices in reverse order forms the chosen subsequence.

---

## 4. Step‐By‐Step Code Explanation

Below is a cleaned‐up version of the core DP code with inline comments.  Pay attention to two arrays:

* `sums[i]` will store exactly our DP state `S[i]`.
* `prev[i]` will store “which index immediately precedes i in the optimal subsequence.”

```java
public static List<List<Integer>> maxSumIncreasingSubsequence(int[] array) {
  int n = array.length;

  // 1) Initialize DP tables:
  //    sums[i] = best sum of an increasing subsequence that ends at index i
  //    prev[i] = index of the previous element in that subsequence (or -1 if none)
  int[] sums = array.clone();                     // start by assuming “just take array[i]”
  int[] prev = new int[n];
  Arrays.fill(prev, -1);                          // -1 means “no predecessor”

  // 2) We'll also track which index has the best final sum:
  int maxSumIdx = 0;                              // start by assuming index 0 is best

  // 3) Fill DP in O(n^2) time:
  for (int i = 1; i < n; i++) {
    // sums[i] already = array[i], prev[i] = -1
    for (int j = 0; j < i; j++) {
      // Can we append array[i] after array[j]? Only if array[j] < array[i].
      if (array[j] < array[i]) {
        // If we extend the best subsequence ending at j by array[i], the sum would be:
        int candidate = sums[j] + array[i];
        if (candidate > sums[i]) {
          sums[i] = candidate;
          prev[i] = j;
        }
      }
    }
    // After checking all j < i, maybe sums[i] improved.  Now update maxSumIdx if needed:
    if (sums[i] > sums[maxSumIdx]) {
      maxSumIdx = i;
    }
  }

  // 4) Now sums[maxSumIdx] is the largest sum of any increasing subsequence.
  //    We must reconstruct the actual subsequence by following prev[].
  List<Integer> subsequence = new ArrayList<>();
  int current = maxSumIdx;
  while (current != -1) {
    // Insert array[current] at the front of our subsequence list
    subsequence.add(0, array[current]);
    current = prev[current];  // “go back” to that index
  }

  // 5) Package result: first the maximum sum, then the subsequence
  List<List<Integer>> answer = new ArrayList<>();
  answer.add(Arrays.asList(sums[maxSumIdx]));   // a single‐element list containing the max sum
  answer.add(subsequence);                      // the actual subsequence in correct order

  return answer;
}
```

### Let’s trace this on the first example

```
 array = [10, 70, 20, 30, 50, 11, 30]
```

* Initially:

  ```
  sums = [10, 70, 20, 30, 50, 11, 30]
  prev = [ -1, -1, -1, -1, -1, -1, -1 ]
  maxSumIdx = 0   // because sums[0] = 10 so far
  ```

* **i = 1 (value = 70)**

  * j=0: array\[0]=10 < 70, so candidate = sums\[0] + 70 = 10 + 70 = 80.
    That’s > sums\[1] (which started at 70).  So update:

    ```
    sums[1] = 80
    prev[1] = 0
    ```
  * Done j-loop.  Now `sums[1] = 80`.  Compare to `sums[maxSumIdx] = sums[0]=10`.  80>10, hence `maxSumIdx = 1`.

* **i = 2 (value = 20)**

  * j=0: 10 < 20, candidate = sums\[0] + 20 = 10 + 20 = 30 > sums\[2] (20)? Yes, update:

    ```
    sums[2] = 30
    prev[2] = 0   (subsequence is now [10,20] ending at index 2)
    ```
  * j=1: 70 < 20? No.  (skip)
  * End.  Now `sums[2] = 30`. Compare to current best = `sums[1]=80`. 30 < 80, so `maxSumIdx` stays at 1.

* **i = 3 (value = 30)**

  * j=0: 10 < 30 → candidate = 10+30 = 40 > sums\[3] (30)? yes →

    ```
    sums[3] = 40; prev[3] = 0   // best subsequence so far: [10,30] ending at 3
    ```
  * j=1: 70 < 30? No.
  * j=2: 20 < 30? Yes → candidate = sums\[2] + 30 = 30 + 30 = 60. 60>40? yes →

    ```
    sums[3] = 60; prev[3] = 2   // now best subsequence: [10,20,30] ending at 3
    ```
  * End.  `sums[3] = 60`. Compare to best so far 80? 60 < 80, so `maxSumIdx` unchanged (still 1).

* **i = 4 (value = 50)**

  * j=0: 10 < 50 → candidate = 10+50 = 60 > sums\[4] (50)? yes →
    `sums[4] = 60; prev[4] = 0  // [10,50]`
  * j=1: 70 < 50? No.
  * j=2: 20 < 50 → candidate = sums\[2] + 50 = 30 + 50 = 80 > sums\[4]=60? yes →
    `sums[4] = 80; prev[4] = 2  // [10,20,50]`
  * j=3: 30 < 50 → candidate = sums\[3] + 50 = 60 + 50 = 110 > sums\[4]=80? yes →
    `sums[4] = 110; prev[4] = 3  // [10,20,30,50]`
  * End.  `sums[4] = 110`. Compare to best so far = 80. 110 > 80, so `maxSumIdx = 4`.

* **i = 5 (value = 11)**

  * j=0: 10 < 11 → candidate = 10 + 11 = 21 > sums\[5] (11)? yes →
    `sums[5] = 21; prev[5] = 0  // subsequence [10,11]`
  * j=1: 70 < 11? no
  * j=2: 20 < 11? no
  * j=3: 30 < 11? no
  * j=4: 50 < 11? no
  * End.  `sums[5] = 21`. 21 < 110, so `maxSumIdx` remains 4.

* **i = 6 (value = 30)**

  * j=0: 10 < 30 → candidate = 10 + 30 = 40 > sums? yes →
    `sums[6] = 40; prev[6] = 0  // [10,30]`
  * j=1: 70 < 30? no
  * j=2: 20 < 30 → candidate = 30 + 30 = 60 > 40? yes →
    `sums[6] = 60; prev[6] = 2  // [10,20,30]`
  * j=3: 30 < 30? no (strictly increasing, so skip “30 == 30”)
  * j=4: 50 < 30? no
  * j=5: 11 < 30 → candidate = sums\[5] + 30 = 21 + 30 = 51. But 51 < 60 (current sums\[6]), so skip.
  * End.  `sums[6] = 60`. Still < 110, so `maxSumIdx` remains 4.

At the end of the double loop, we have:

```
sums = [ 10,  80,  30,  60, 110, 21, 60 ]
prev = [-1,   0,   0,   2,  3,  0,  2 ]
maxSumIdx = 4    // because sums[4] = 110 is the global maximum
```

### Reconstructing the subsequence

* Start `current = maxSumIdx = 4`.  That means the final element is `array[4] = 50`.
* `prev[4] = 3`, so before 50 came `array[3] = 30`.  Subsequence so far: `[30, 50]`.
* Move `current = 3`.  `prev[3] = 2`, so before 30 came `array[2] = 20`.  Subsequence: `[20, 30, 50]`.
* Move `current = 2`.  `prev[2] = 0`, so before 20 came `array[0] = 10`.  Subsequence: `[10, 20, 30, 50]`.
* Move `current = 0`.  `prev[0] = −1`.  When we see −1, we stop.  Final subsequence (in correct order) is `[10, 20, 30, 50]`.

Hence the function returns:

```
[ [110],  [10, 20, 30, 50] ]
```

where `[110]` is the maximum sum, and `[10, 20, 30, 50]` is one valid subsequence achieving that sum.

---

## 5. Full Code Listing

Putting it all together:

```java
import java.util.*;

public class A01MaxSumIncreasingSubsequence {

  public static List<List<Integer>> maxSumIncreasingSubsequence(int[] array) {
    int n = array.length;
    // 1) sums[i] = max sum of an increasing subsequence ending exactly at i
    int[] sums = array.clone();
    // 2) prev[i] = index of the previous element in the best subsequence ending at i
    int[] prev = new int[n];
    Arrays.fill(prev, -1);

    // Track which index yields the maximum sum
    int maxSumIdx = 0;

    // 3) Fill the DP tables in O(n^2)
    for (int i = 1; i < n; i++) {
      for (int j = 0; j < i; j++) {
        if (array[j] < array[i]) {
          int candidate = sums[j] + array[i];
          if (candidate > sums[i]) {
            sums[i] = candidate;
            prev[i] = j;
          }
        }
      }
      if (sums[i] > sums[maxSumIdx]) {
        maxSumIdx = i;
      }
    }

    // 4) Reconstruct the subsequence by walking backward from maxSumIdx
    List<Integer> subsequence = new ArrayList<>();
    int current = maxSumIdx;
    while (current != -1) {
      subsequence.add(0, array[current]);  // insert at front
      current = prev[current];
    }

    // 5) Return [ [maximum sum], [the subsequence] ]
    List<List<Integer>> answer = new ArrayList<>();
    answer.add(Arrays.asList(sums[maxSumIdx])); // wrap sum in a single‐element list
    answer.add(subsequence);
    return answer;
  }

  public static void main(String[] args) {
    int[] array1 = {10, 70, 20, 30, 50, 11, 30};
    List<List<Integer>> result1 = maxSumIncreasingSubsequence(array1);
    System.out.println("Max sum: " + result1.get(0).get(0)); 
      // Output: 110
    System.out.println("Subsequence: " + result1.get(1)); 
      // Output: [10, 20, 30, 50]

    int[] array2 = {8, 12, 2, 3, 15, 5, 7};
    List<List<Integer>> result2 = maxSumIncreasingSubsequence(array2);
    System.out.println("Max sum: " + result2.get(0).get(0)); 
      // Output: 35
    System.out.println("Subsequence: " + result2.get(1)); 
      // Output: [8, 12, 15]
  }

  /*
   Time Complexity:
   - We use two nested loops (i from 1..n−1, j from 0..i−1), so that is O(n^2).
     n can be up to 1000, so O(n^2) is acceptable.

   Space Complexity:
   - We allocate two arrays of length n: `sums[]` and `prev[]`.  That is O(n).  
   - We also build an ArrayList for the final subsequence (at most n elements).  
   - So total extra space is O(n).
  */
}
```

---

## 6. Final Complexity Summary

* **Time** = O(n²) (where n = `array.length`), because for each `i` we potentially examine all `j < i`.
* **Space** = O(n), since we store two length-n arrays (`sums[]` and `prev[]`) plus the output subsequence (which in the worst case could be length n).

That completes the detailed beginner-friendly explanation of how to find (1) the maximum sum of any strictly increasing subsequence, and (2) one actual subsequence that achieves that sum, all in Java.
