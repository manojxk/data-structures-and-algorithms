**Problem Explanation**
Given an array of integers (e.g. `[1, 2, 3, 4]`), we need to produce a new array of the same length where each element at index *i* is the **product of all numbers in the original array except the one at *i***. In other words, for the example:

```
Input:  [1, 2, 3, 4]
Output: [2·3·4, 1·3·4, 1·2·4, 1·2·3]
      = [24,       12,       8,       6]
```

We cannot simply compute the total product and divide by the element at *i*, because the array might contain zeroes. Instead, we’ll build two auxiliary “running product” arrays—one scanning from the left and one from the right—and then multiply them.

---

## Approach 1: Left and Right Products (O(n) Time, O(n) Space)

1. **Left Products Array**

   * Define an array `leftProducts` of length *n*.
   * `leftProducts[i]` will store the product of all original elements **to the left of index *i***.
   * By definition, `leftProducts[0] = 1`, because no elements lie to the left of index 0.
   * Then for `i` from 1 to \_n–1\`:

     ```
     leftProducts[i] = leftProducts[i – 1] * array[i – 1]
     ```

     That way, at step *i*, you have the product of everything from index 0 up through index (i–1).

2. **Right Products Array**

   * Similarly, define `rightProducts[i]` as the product of all original elements **to the right of index *i***.
   * Here, `rightProducts[n – 1] = 1`, since nothing lies to the right of the last index.
   * For `i` from \_n–2 down to 0\`:

     ```
     rightProducts[i] = rightProducts[i + 1] * array[i + 1]
     ```

     which accumulates the product of everything to the right.

3. **Final Products**

   * The answer at index *i* is simply

     ```
     products[i] = leftProducts[i] * rightProducts[i]
     ```

     because the product of all elements except `array[i]` splits into “everything on the left of *i*” times “everything on the right of *i*”.

**Why it works:**
At position *i*, you want

```
( array[0] · array[1] · … · array[i–1] ) 
× ( array[i+1] · array[i+2] · … · array[n–1] ) 
```

which is exactly `leftProducts[i] · rightProducts[i]`.

### Java Code

```java
package medium.arrays;

import java.util.Arrays;

public class A07ArrayOfProducts {

  /**
   * Returns an array where each index i holds the product of all elements
   * in the input except array[i], without using division.
   *
   * Time:  O(n)  
   * Space: O(n)  (for leftProducts and rightProducts)
   */
  public static int[] arrayOfProducts(int[] array) {
    int n = array.length;

    int[] leftProducts  = new int[n];
    int[] rightProducts = new int[n];
    int[] products      = new int[n];

    // 1) Build leftProducts: product of everything to the left of i
    leftProducts[0] = 1;
    for (int i = 1; i < n; i++) {
      leftProducts[i] = leftProducts[i - 1] * array[i - 1];
    }

    // 2) Build rightProducts: product of everything to the right of i
    rightProducts[n - 1] = 1;
    for (int i = n - 2; i >= 0; i--) {
      rightProducts[i] = rightProducts[i + 1] * array[i + 1];
    }

    // 3) The final product at i is leftProducts[i] * rightProducts[i]
    for (int i = 0; i < n; i++) {
      products[i] = leftProducts[i] * rightProducts[i];
    }

    return products;
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 3, 4};
    int[] result = arrayOfProducts(array);
    System.out.println("Array of Products: " + Arrays.toString(result));
    // Expected: [24, 12,  8, 6]
  }
}
```

* **Time Complexity:** O(n) — three linear passes (building leftProducts, building rightProducts, final multiplication).
* **Space Complexity:** O(n) — we allocate three length-n arrays.

---

## Approach 2: Single-Pass with Running Products (O(n) Time, O(n) Space)

We can compress the same idea into **two passes** over a single `products[]` array:

1. **First pass (left to right):**

   * Maintain a `leftRunningProduct` that starts at 1.
   * As you move from `i = 0` to `n – 1`:

     * Set `products[i] = leftRunningProduct` (at this moment, `leftRunningProduct` is the product of everything to the left of *i*).
     * Update `leftRunningProduct *= array[i]`.

2. **Second pass (right to left):**

   * Maintain a `rightRunningProduct` that starts at 1.
   * Move from `i = n – 1` down to 0:

     * Multiply `products[i] *= rightRunningProduct` (now `products[i]` was previously left-of-i, and `rightRunningProduct` is the product of everything to the right of *i*).
     * Update `rightRunningProduct *= array[i]`.

When you finish, `products[i]` equals `(∏ all left of i) × (∏ all right of i)`, as desired.

### Java Code

```java
package medium.arrays;

import java.util.Arrays;

public class A07ArrayOfProducts {

  /**
   * Returns an array where products[i] = product of all array[j] for j ≠ i.
   *
   * Time:  O(n)
   * Space: O(n)  (for products[])
   */
  public static int[] arrayOfProductsCopilot(int[] array) {
    int n = array.length;
    int[] products = new int[n];

    // 1) First pass: compute products of everything to the left
    int leftRunningProduct = 1;
    for (int i = 0; i < n; i++) {
      products[i] = leftRunningProduct;
      leftRunningProduct *= array[i];
    }

    // 2) Second pass: multiply by products of everything to the right
    int rightRunningProduct = 1;
    for (int i = n - 1; i >= 0; i--) {
      products[i] *= rightRunningProduct;
      rightRunningProduct *= array[i];
    }

    return products;
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 3, 4};
    int[] result = arrayOfProductsCopilot(array);
    System.out.println("Array of Products: " + Arrays.toString(result));
    // Expected: [24, 12,  8, 6]
  }
}
```

* **Time Complexity:** O(n) — two linear passes.
* **Space Complexity:** O(n) — the `products[]` array.

Both approaches handle zeroes correctly (e.g., if one element is zero, that index’s product ends up being the product of all other non-zero elements, and the zero contributes appropriately to nearby indices). They avoid division entirely, making them robust when array elements may be zero.

Choose whichever style you find clearer—both run in linear time and use only linear extra space (not counting the input).
