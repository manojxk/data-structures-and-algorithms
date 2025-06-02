**Problem Restatement**
You have a stack of integers (the top element is the most recently pushed). You need to sort this stack so that the **smallest** integers end up on top and the **largest** on the bottom, using **only recursion** (no loops for the sorting logic). You may only use the call stack and the stack’s push/pop operations—no additional data structures other than the recursion stack itself.

> **Example 1**
>
> ```
> Input stack (top → bottom): [34, 3, 31, 98, 92, 23]
> Output stack (top → bottom): [3, 23, 31, 34, 92, 98]
> ```
>
> The smallest element (3) ends up on top, then 23, then 31, and so on.

> **Example 2**
>
> ```
> Input: [-3, 14, 18, -5, 30]
> Output: [-5, -3, 14, 18, 30]
> ```

---

## Approach Overview (Recursive)

1. **Recursively “unload” the stack** until it’s empty:

   * Pop the top element, call it `topValue`.
   * Recursively call the same `sortStack` function on the now-smaller stack.
   * After the recursive call returns, the sub-stack below is sorted.
   * Insert `topValue` back into that sorted sub-stack at the correct position so that the entire stack remains sorted.

2. **Insertion helper (`insertInSortedOrder`)**

   * Given a stack that is already sorted (smallest on top) and a new value `val`, you want to insert `val` so that the stack stays sorted.
   * If the stack is empty, or if the current top of the stack is ≤ `val`, just push `val` on top (since it belongs above any smaller or equal element).
   * Otherwise, pop the top element, recursively insert `val` into the smaller stack, then push the popped element back on top.

Because each element is popped exactly once during the “unload” phase, and each element may be popped once again during the insertion phase, the time complexity becomes $O(n^2)$ in the worst case (where $n$ is the number of items in the stack). The space complexity is $O(n)$ due to recursion depth.

---

## Step-by-Step Solution

### 1. `sortStack(stack)`

```java
public static void sortStack(Stack<Integer> stack) {
  // Base case: if the stack is empty, there's nothing to sort
  if (stack.isEmpty()) {
    return;
  }

  // 1) Pop the top element
  int topValue = stack.pop();

  // 2) Recursively sort the remaining stack
  sortStack(stack);

  // 3) Insert the popped value back into the sorted stack
  insertInSortedOrder(stack, topValue);
}
```

* If `stack` is empty, simply return—an empty stack is already sorted.
* Otherwise, pop off the top element (`topValue`) and *remember* it.
* Call `sortStack(...)` on what remains. After that call returns, the remaining stack is guaranteed to be sorted with the smallest element at the top.
* Finally, insert `topValue` into the correct position of that sorted stack by calling `insertInSortedOrder(stack, topValue)`.

### 2. `insertInSortedOrder(stack, value)`

```java
public static void insertInSortedOrder(Stack<Integer> stack, int value) {
  // If the stack is empty, or its top is ≤ value, then 
  // pushing value here preserves ascending order.
  if (stack.isEmpty() || stack.peek() <= value) {
    stack.push(value);
    return;
  }

  // Otherwise, the top element is larger than 'value'.
  // Pop it, insert 'value' further down, then push this popped element back.
  int top = stack.pop();
  insertInSortedOrder(stack, value);
  stack.push(top);
}
```

* **Base case:** If there are no elements left or if the current top is already ≤ `value`, just push `value`.
  That guarantees all smaller or equal numbers remain above `value`.
* **Otherwise:** The top is strictly greater than `value`, so hold it aside by popping it, recursively insert `value` into the now-smaller stack, and then re-push the popped element. This way, `value` will end up beneath any element larger than it, preserving sorted order.

---

## Full Java Code

```java
package medium.stacks;

import java.util.Stack;

public class A05SortStack {

  /**
   * Sorts the given stack in ascending order (smallest on top) using recursion.
   *
   * Time Complexity:  O(n^2) in the worst case (where n = stack.size()).
   * Space Complexity: O(n) due to the recursion stack.
   */
  public static void sortStack(Stack<Integer> stack) {
    // Base case: empty stack is already sorted
    if (stack.isEmpty()) {
      return;
    }

    // 1) Pop the top element
    int topValue = stack.pop();

    // 2) Recursively sort the remaining stack
    sortStack(stack);

    // 3) Insert the popped element back into the sorted stack
    insertInSortedOrder(stack, topValue);
  }

  /**
   * Inserts 'value' into a stack that is already sorted in ascending order
   * (with smallest element at the top). After insertion, the stack remains sorted.
   */
  public static void insertInSortedOrder(Stack<Integer> stack, int value) {
    // If the stack is empty, or top ≤ value, push value
    if (stack.isEmpty() || stack.peek() <= value) {
      stack.push(value);
      return;
    }

    // Otherwise, the top is > value. Pop it and recurse.
    int top = stack.pop();
    insertInSortedOrder(stack, value);

    // Once 'value' is inserted in the correct place, push the held-aside element back.
    stack.push(top);
  }

  public static void main(String[] args) {
    // Example 1:
    Stack<Integer> stack1 = new Stack<>();
    stack1.push(34);
    stack1.push(3);
    stack1.push(31);
    stack1.push(98);
    stack1.push(92);
    stack1.push(23);
    System.out.println("Original Stack 1: " + stack1);
    sortStack(stack1);
    System.out.println("Sorted Stack 1:   " + stack1);
    // Expected: [3, 23, 31, 34, 92, 98] (top → bottom)

    // Example 2:
    Stack<Integer> stack2 = new Stack<>();
    stack2.push(-3);
    stack2.push(14);
    stack2.push(18);
    stack2.push(-5);
    stack2.push(30);
    System.out.println("Original Stack 2: " + stack2);
    sortStack(stack2);
    System.out.println("Sorted Stack 2:   " + stack2);
    // Expected: [-5, -3, 14, 18, 30]
  }
}
```

---

## Why This Works

1. **“Unload” Phase**

   * Each call to `sortStack` removes the top element and calls itself on a smaller stack. Eventually, you reach the empty stack (base case).
   * By that point, you’ve popped off all elements in a chain of recursive calls.

2. **“Insert” Phase**

   * When the recursion unwinds, you start inserting the values back one by one, always placing each new value into what is already a sorted sub-stack.
   * The helper `insertInSortedOrder` recursively shifts aside any elements larger than `value` so that `value` can slip in beneath them. This keeps the stack sorted at every step up the recursion.

3. **Net Effect**

   * The first element popped (the original top) ends up being inserted last, so it will “bubble” up exactly to its correct sorted position.
   * By the time you finish unwinding the recursion, every element has been re-inserted in ascending order (smallest on top).

---

## Complexity Analysis

* **Time Complexity: $O(n^2)$**

  * In the worst case (e.g., if the stack is in strictly descending order already), each insertion may pop and push almost all remaining elements to find the correct position.
  * Rough estimate:

    * The first popped element is inserted into an empty stack → $O(1)$.
    * The second popped element might have to be compared against 1 element → $O(1)$.
    * …
    * The last element inserted might need to be compared/popped against up to $n-1$ elements → $O(n)$.
  * Summing up these operations over all $n$ elements gives $O(1 + 2 + … + (n-1)) = O(n^2)$.

* **Space Complexity: $O(n)$**

  * The stack itself holds $n$ elements.
  * Additionally, the recursion call stack can go as deep as $n$ if you have to pop all elements before inserting.

Thus, while this method is conceptually elegant, it is quadratic in time. For very large $n$, a different approach (e.g., using an auxiliary stack with repeated passes, or converting the stack to an array and sorting) would be more efficient in practice. However, the recursive solution beautifully demonstrates how you can sort with nothing but the stack’s own operations and recursion.
