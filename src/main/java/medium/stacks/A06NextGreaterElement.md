**Problem Restatement**
Given an integer array `arr[]`, for each element, find the **next greater element** to its right—that is, the first element strictly larger than `arr[i]` among indices `j > i`. If no such element exists, record `-1` for that position.

> **Example 1**
>
> ```
> Input:  [4, 5, 2, 10]
> Output: [5, 10, 10, -1]
> Explanation:
>  - For 4, the next greater to its right is 5.
>  - For 5, the next greater is 10.
>  - For 2, the next greater is 10.
>  - For 10, there is no larger element on its right ⇒ -1.
> ```

> **Example 2**
>
> ```
> Input:  [3, 2, 1]
> Output: [-1, -1, -1]
> Explanation: No element has a larger number to its right, so all are -1.
> ```

---

## Approach: Monotonic Stack (O(n) Time • O(n) Space)

1. **Idea**
   We will scan the array from **right to left** while maintaining a stack of candidates for “next greater elements.” This stack will be kept in strictly decreasing order (top is the smallest of the stack), so that when we process a new element `arr[i]`, all stack elements ≤ `arr[i]` cannot serve as its next greater—they are “useless” for any element further to the left. We pop them off, leaving only strictly greater elements on the stack. The top of the stack (if any remain) is then the next greater element for `arr[i]`. Finally, push `arr[i]` onto the stack as a candidate for elements even further left.

2. **Step-by-Step**

   * Let `n = arr.length`.
   * Create an output list `res` of length `n`, initialized to `-1` everywhere.
   * Initialize an empty stack `stk`. We will store **values** (not indices).
   * Loop `i` from `n−1` down to `0`:

     1. While `stk` is not empty **and** `stk.peek() ≤ arr[i]`, pop from `stk`.

        * Reason: any value ≤ `arr[i]` cannot be “next greater” for `arr[i]` or for any element to the left of `i`, because `arr[i]` itself would be a larger “skip-over” candidate.
     2. If `stk` is not empty after that, then `stk.peek()` is the **first** element to the right of `i` that was strictly greater; set `res[i] = stk.peek()`. Otherwise leave `res[i] = -1`.
     3. Push `arr[i]` onto `stk` as a future candidate for elements to its left.
   * After the loop, `res` contains the next greater element for each index.

3. **Why It’s O(n)**

   * Each array element is pushed onto and popped from the stack **at most once**.
   * Therefore, there are at most $2n$ push/pop operations combined.
   * The loop itself is one pass of $n$ iterations.
   * Overall time is $O(n)$.

4. **Space Complexity**

   * We use a stack that in the worst case can hold all $n$ elements (if the array is strictly decreasing).
   * We also allocate the output array of size $n$.
   * Thus, extra space is $O(n)$.

---

## Java Implementation

```java
package medium.stacks;

import java.util.ArrayList;
import java.util.Stack;

public class A05NextGreaterElement {

    /**
     * Returns a list where res[i] is the next greater element of arr[i] to its right.
     * If no greater element exists, res[i] = -1.
     *
     * Time Complexity:  O(n)
     * Space Complexity: O(n)
     */
    public static ArrayList<Integer> nextLargerElement(int[] arr) {
        int n = arr.length;
        // Initialize result list with -1 for every index
        ArrayList<Integer> res = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            res.add(-1);
        }

        // Stack will store values for which we need to find the next greater
        Stack<Integer> stk = new Stack<>();

        // Traverse from rightmost to leftmost
        for (int i = n - 1; i >= 0; i--) {
            // Discard elements ≤ current, because they can't be next greater for i or anyone to the left
            while (!stk.isEmpty() && stk.peek() <= arr[i]) {
                stk.pop();
            }

            // After removing smaller/equal, if stack still has elements, top is the next greater
            if (!stk.isEmpty()) {
                res.set(i, stk.peek());
            }
            // Push current element as a future “candidate” for elements to the left
            stk.push(arr[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        int[] arr1 = {4, 5, 2, 10};
        System.out.println(nextLargerElement(arr1));
        // Expected output: [5, 10, 10, -1]

        int[] arr2 = {3, 2, 1};
        System.out.println(nextLargerElement(arr2));
        // Expected output: [-1, -1, -1]

        int[] arr3 = {6, 8, 0, 1, 3};
        System.out.println(nextLargerElement(arr3));
        // Expected output: [8, -1, 1, 3, -1]
    }
}
```

---

### How It Works, Illustrated

Take `arr = [4, 5, 2, 10]`. We will build `res` from right to left:

* **Initialize**
  `res = [-1, -1, -1, -1]`
  `stk = []`

* **i = 3**, `arr[3] = 10`

  * Pop none (stack is empty).
  * `stk` is empty → `res[3] = -1`.
  * Push `10`.  → `stk = [10]`.

* **i = 2**, `arr[2] = 2`

  * While `(stk.peek() ≤ 2)?` → `10 ≤ 2`? No → skip pops.
  * `stk` not empty → `res[2] = stk.peek() = 10`.
  * Push `2`. → `stk = [10, 2]`.

* **i = 1**, `arr[1] = 5`

  * Pop while `stk.peek() ≤ 5`:

    * `stk.peek() = 2` → `2 ≤ 5` → pop → `stk = [10]`.
    * Now `stk.peek() = 10` → `10 ≤ 5`? No → stop.
  * `stk` not empty → `res[1] = 10`.
  * Push `5`. → `stk = [10, 5]`.

* **i = 0**, `arr[0] = 4`

  * Pop while `stk.peek() ≤ 4`:

    * `stk.peek() = 5` → `5 ≤ 4`? No → skip pops.
  * `stk` not empty → `res[0] = 5`.
  * Push `4`. → `stk = [10, 5, 4]`.

Final `res = [5, 10, 10, -1]`, as desired.

---

## Complexity Recap

* **Time Complexity:** O(n)

  * Each element goes onto the stack exactly once, and may be popped at most once.
  * Hence, the total number of stack operations is $≤ 2n$.
  * The single loop over `n` elements, plus those pushes/pops, is linear time.

* **Space Complexity:** O(n)

  * The stack can hold up to n elements in the worst case (strictly decreasing input).
  * The result array also uses O(n).

This completes the next‐greater‐element solution using a monotonic stack in one pass.
