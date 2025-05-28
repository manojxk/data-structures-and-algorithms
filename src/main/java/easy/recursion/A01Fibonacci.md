**Problem Overview**

The **Fibonacci sequence** is defined as:

* **F(1) = 0**
* **F(2) = 1**
* **F(n) = F(n–1) + F(n–2)** for **n > 2**

Your goal is to compute the **nᵗʰ** Fibonacci number. We’ll look at **three** ways to do this:

1. **Brute-force recursion**
2. **Optimized recursion** with **memoization**
3. **Iterative** approach

We’ll explain each from a beginner’s perspective, then show the Java code and discuss time/space complexity.

---

## 1. Brute-Force Recursive Solution

### Intuition

* Directly translate the definition:

  * If **n** is 1 or 2, return the base cases.
  * Otherwise, recursively compute **F(n–1)** and **F(n–2)** and add them.

### Step-by-Step

1. **Check base cases**:

   ```java
   if (n == 1) return 0;
   if (n == 2) return 1;
   ```
2. **Recurse**:

   ```java
   return getNthFib(n–1) + getNthFib(n–2);
   ```

### Drawback

* **Exponential** work: each call spawns two more calls, many repeated computations.

```java
public static int getNthFib(int n) {
  if (n == 1) return 0;
  if (n == 2) return 1;
  return getNthFib(n - 1) + getNthFib(n - 2);
}
```

* **Time Complexity:** O(2ⁿ)
* **Space Complexity:** O(n) (maximum recursion depth)

---

## 2. Optimized Recursive with Memoization

### Intuition

* **Cache** results of **F(k)** the first time you compute them.
* On later requests, return the cached value instead of recomputing.

### Step-by-Step

1. Create a **map** or array to store previously computed Fibonacci values.
2. **Initialize** the base cases in the cache:

   ```java
   memo.put(1, 0);
   memo.put(2, 1);
   ```
3. When computing **F(n)**:

   * If `memo` already contains **n**, return it.
   * Otherwise, compute **F(n–1)** and **F(n–2)** (recursively), add them, store in `memo`, then return.

```java
public static int getNthFibOptimized(int n) {
  Map<Integer,Integer> memo = new HashMap<>();
  memo.put(1, 0);
  memo.put(2, 1);
  return getNthFibOptimized(n, memo);
}

private static int getNthFibOptimized(int n, Map<Integer,Integer> memo) {
  if (memo.containsKey(n)) {
    return memo.get(n);
  }
  int result = getNthFibOptimized(n - 1, memo)
             + getNthFibOptimized(n - 2, memo);
  memo.put(n, result);
  return result;
}
```

* **Time Complexity:** O(n) — each Fibonacci number computed once
* **Space Complexity:** O(n) — for the memo structure and recursion stack

---

## 3. Iterative Solution

### Intuition

* Build up from the bottom:

  * Track the two most recent Fibonacci values and update them in a loop.

### Step-by-Step

1. Handle base cases:

   ```java
   if (n == 1) return 0;
   if (n == 2) return 1;
   ```
2. Initialize:

   ```java
   int prevPrev = 0; // F(1)
   int prev     = 1; // F(2)
   int current  = 0;
   ```
3. Loop **i** from 3 to **n**:

   ```java
   current  = prevPrev + prev; 
   prevPrev = prev;
   prev     = current;
   ```
4. At the end, `current` holds **F(n)**.

```java
public static int getNthFibIter(int n) {
  if (n == 1) return 0;
  if (n == 2) return 1;

  int prevPrev = 0;
  int prev     = 1;
  int current  = 0;

  for (int i = 3; i <= n; i++) {
    current  = prevPrev + prev;
    prevPrev = prev;
    prev     = current;
  }
  return current;
}
```

* **Time Complexity:** O(n) — single loop up to n
* **Space Complexity:** O(1) — only a few integer variables

---

## Complete Java Class

```java
package easy.recursion;

import java.util.HashMap;
import java.util.Map;

public class A01Fibonacci {

  // 1) Brute-force recursive
  public static int getNthFib(int n) {
    if (n == 1) return 0;
    if (n == 2) return 1;
    return getNthFib(n - 1) + getNthFib(n - 2);
  }

  // 2) Optimized recursive with memoization
  public static int getNthFibOptimized(int n) {
    Map<Integer, Integer> memo = new HashMap<>();
    memo.put(1, 0);
    memo.put(2, 1);
    return getNthFibOptimized(n, memo);
  }

  private static int getNthFibOptimized(int n, Map<Integer, Integer> memo) {
    if (memo.containsKey(n)) {
      return memo.get(n);
    }
    int fib = getNthFibOptimized(n - 1, memo)
            + getNthFibOptimized(n - 2, memo);
    memo.put(n, fib);
    return fib;
  }

  // 3) Iterative solution
  public static int getNthFibIter(int n) {
    if (n == 1) return 0;
    if (n == 2) return 1;

    int prevPrev = 0, prev = 1, current = 0;
    for (int i = 3; i <= n; i++) {
      current  = prevPrev + prev;
      prevPrev = prev;
      prev     = current;
    }
    return current;
  }

  public static void main(String[] args) {
    System.out.println(getNthFib(2));           // 1
    System.out.println(getNthFib(6));           // 5
    System.out.println(getNthFibOptimized(6));  // 5
    System.out.println(getNthFibIter(6));       // 5
  }
}
```

---

### Summary of Complexities

| Method                | Time Complexity | Space Complexity |
| --------------------- | --------------- | ---------------- |
| Brute-Force Recursion | O(2ⁿ)           | O(n)             |
| Memoized Recursion    | O(n)            | O(n)             |
| Iterative             | O(n)            | O(1)             |

For practical use, prefer the **iterative** or **memoized** approach—both run in linear time, with the iterative using only constant extra space.
