**Problem Restatement**
You have an array of **unique** integers `nums`, each in the range $1, n$, where $n = \text{nums.length} + 2$. Two numbers from 1..n are missing in `nums`. Return those two missing numbers in **ascending order**.

> **Example**
> Input: `nums = [1, 4, 3]`
> Here, `nums.length = 3`, so $n = 3 + 2 = 5$. The full range should be $1, 2, 3, 4, 5$.
> Missing numbers are `2` and `5`.
> Output: `[2, 5]`.

---

## 1. Brute‐Force Approach (O(n²) Time • O(1) Extra Space)

1. Let $n = \text{nums.length} + 2$.
2. For each integer $i$ from 1 to $n$, scan `nums` to see if $i$ is present.
3. If it is not found in `nums`, collect $i$ into a “missing” list.
4. Return that list (it will contain exactly two values).
5. Finally, sort the list before returning (to ensure ascending order).

```java
public static List<Integer> findMissingNumbersBruteForce(int[] nums) {
  int n = nums.length + 2;
  List<Integer> missing = new ArrayList<>();
  for (int i = 1; i <= n; i++) {
    boolean found = false;
    // scan nums for i
    for (int num : nums) {
      if (num == i) {
        found = true;
        break;
      }
    }
    if (!found) {
      missing.add(i);
      if (missing.size() == 2) break; // stop once we have two
    }
  }
  Collections.sort(missing);
  return missing;
}
```

* **Time Complexity:**
  For each of the $n$ numbers in $1..n$, you do an O(n) scan of `nums`, so $O(n \times n) = O(n^2)$.
* **Space Complexity:**
  Aside from the output list of two elements, you only use a few variables → $O(1)$ extra.

---

## 2. Optimized “Sum & Sum-of-Squares” Approach (O(n) Time • O(1) Extra Space)

Because the missing numbers problem boils down to:

1. Two unknowns $x, y$,
2. We know the **sum** of all numbers from 1..n, and the **sum of squares** of all numbers from 1..n,
3. We can subtract the actual sums coming from `nums` to form two equations in $x, y$.

Let

$$
n = \text{nums.length} + 2.
$$

1. **Compute**

   $$
   S = 1 + 2 + \cdots + n \;=\; \frac{n(n+1)}{2},
   \quad
   Q = 1^2 + 2^2 + \cdots + n^2 
       \;=\; \frac{n(n+1)(2n+1)}{6}.
   $$
2. **Compute** the “actual” sums from the given array:

   $$
   S_{\text{actual}} = \sum_{k=0}^{\,\text{nums.length}-1} \text{nums}[k], 
   \quad
   Q_{\text{actual}} 
     = \sum_{k=0}^{\,\text{nums.length}-1} \bigl(\text{nums}[k]\bigr)^2.
   $$
3. Then the two missing integers $x$ and $y$ satisfy:

   $$
   (1)\quad x + y = S - S_{\text{actual}}, 
   \quad 
   (2)\quad x^2 + y^2 = Q - Q_{\text{actual}}.
   $$

   Let

   $$
   A = x + y,\qquad
   B = x^2 + y^2.
   $$

   From these,

   $$
   x^2 + y^2 = (x + y)^2 - 2xy 
   \;\;\Longrightarrow\;\;
   B \;=\; A^2 - 2\,xy
   \;\;\Longrightarrow\;\;
   xy \;=\; \frac{A^2 - B}{2}.
   $$

   So we know:

   $$
   \begin{cases}
     x + y = A, \\
     x\,y  = C \;=\; \dfrac{A^2 - B}{2}.
   \end{cases}
   $$

   Thus $x, y$ are the two **roots** of the quadratic:

   $$
   t^2 \;-\; A\,t \;+\; C \;=\; 0.
   $$

   The two solutions are

   $$
   x = \frac{A + \sqrt{\,A^2 - 4\,C\,}}{2}, 
   \quad 
   y = \frac{A - \sqrt{\,A^2 - 4\,C\,}}{2}.
   $$
4. **Implementation Steps**

   * Compute $n = \text{nums.length} + 2$.
   * Compute $S = \tfrac{n(n+1)}{2}, \; Q = \tfrac{n(n+1)(2n+1)}{6}.$
   * Scan `nums` once to find $S_{\text{actual}},\,Q_{\text{actual}}$.
   * Let
     $\displaystyle A = S - S_{\text{actual}},$
     $\displaystyle B = Q - Q_{\text{actual}},$
     $\displaystyle C = \frac{A^2 - B}{2}.$
   * Compute
     $\displaystyle D = \sqrt{\,A^2 - 4\,C\,}$ (an integer),
     then
     $\displaystyle x_1 = \frac{A + D}{2}, \quad x_2 = \frac{A - D}{2}.$
   * Finally, return `[min(x1, x2), max(x1, x2)]`.

### Java Code

```java
package medium.arrays;

import java.util.*;

public class A12MissingNumbers {

  /**
   * Returns the two missing numbers in ascending order, given that
   * nums contains unique integers from 1..n with exactly two missing.
   *
   * Time Complexity:  O(n)      (one pass to compute sums, plus O(1) work)
   * Space Complexity: O(1)      (just a handful of variables)
   */
  public static List<Integer> findMissingNumbers(int[] nums) {
    int n = nums.length + 2;

    // 1) Compute expected sum and expected sum of squares for 1..n
    long expectedSum = (long) n * (n + 1) / 2;               // S = n(n+1)/2
    long expectedSquares = (long) n * (n + 1) * (2 * n + 1) / 6; // Q = n(n+1)(2n+1)/6

    // 2) Compute actual sum and actual sum of squares from the array
    long actualSum = 0, actualSquares = 0;
    for (int num : nums) {
      actualSum += num;
      actualSquares += (long) num * num;
    }

    // 3) Let A = x + y = missingSum,  B = x^2 + y^2 = missingSquares
    long A = expectedSum - actualSum;         // x + y
    long B = expectedSquares - actualSquares; // x^2 + y^2

    // 4) xy = (A^2 - B) / 2
    long productXY = (A * A - B) / 2;

    // 5) Solve t^2 - A t + productXY = 0
    //    Discriminant D = A^2 - 4·productXY
    long discriminant = A * A - 4 * productXY;
    long D = (long) Math.sqrt(discriminant);

    // 6) The two missing are (A ± D)/2
    int missing1 = (int) ((A + D) / 2);
    int missing2 = (int) ((A - D) / 2);

    // Return in ascending order
    if (missing1 < missing2) {
      return Arrays.asList(missing1, missing2);
    } else {
      return Arrays.asList(missing2, missing1);
    }
  }

  public static void main(String[] args) {
    // Example:
    int[] nums = {1, 4, 3};
    // n = 3 + 2 = 5, full range = [1,2,3,4,5]
    // Missing are 2 and 5
    System.out.println(findMissingNumbers(nums));
    // Expected output: [2, 5]
  }
}
```

* **Time Complexity:** $O(n)$ because we do a single pass through `nums` to compute sums and squares, and then constant‐time arithmetic/√.
* **Space Complexity:** $O(1)$ extra space, since we only allocate a few `long`/`int` variables and the result list of size 2.

---

## 3. Alternative “Pivot” Approach (Also O(n) Time • O(1) Space)

A slightly different O(n) approach avoids squares by dividing the missing‐sum partition:

1. Compute

   $$
   \text{totalSum} = \sum_{i=1}^n i, 
   \quad 
   \text{numsSum} = \sum_{x \in nums} x, 
   \quad 
   \text{missingSum} = \text{totalSum} - \text{numsSum}.
   $$

   So $x + y = \text{missingSum}.$

2. Let $\text{pivot} = \lfloor \text{missingSum} / 2 \rfloor.$

   * Now, we know exactly one of the missing numbers is $\le$ pivot, and the other is $>$ pivot, because if both were $\le$ pivot or both $>$ pivot, their total would not match `missingSum`.
   * Compute

     $$
     \text{leftSum} = \sum_{i=1}^{\text{pivot}} i, 
     \quad 
     \text{numsLeftSum} = \sum_{x \in nums,\; x \,\le\, \text{pivot}} x.
     $$

     Then the number in $\{1,\dots,\text{pivot}\}$ that is missing is

     $$
     missingLeft = \text{leftSum} - \text{numsLeftSum}.
     $$
   * The other missing number is

     $$
     missingRight = \text{missingSum} - missingLeft.
     $$

3. Return `[missingLeft, missingRight]` sorted.

```java
public static int[] findMissingNumbersPivot(int[] nums) {
  int n = nums.length + 2; 
  // Step 1: sum of 1..n
  int totalSum = n * (n + 1) / 2;
  int numsSum = 0;
  for (int x : nums) numsSum += x;
  int missingSum = totalSum - numsSum; // x + y

  // Step 2: pick a “pivot” = floor(missingSum/2)
  int pivot = missingSum / 2;

  // Compute sum of 1..pivot
  int totalLeftSum = pivot * (pivot + 1) / 2;
  // Compute sum of all nums ≤ pivot
  int numsLeftSum = 0;
  for (int x : nums) {
    if (x <= pivot) numsLeftSum += x;
  }
  int missingLeft = totalLeftSum - numsLeftSum;
  int missingRight = missingSum - missingLeft;

  if (missingLeft < missingRight) {
    return new int[]{ missingLeft, missingRight };
  } else {
    return new int[]{ missingRight, missingLeft };
  }
}
```

* **Time Complexity:** O(n).
* **Space Complexity:** O(1).

Either the **sum‐and‐square** method or the **pivot** method finds the two missing numbers in linear time with constant extra space. Both exploit the fact that every original value lies in $1..n$.
