
| **Time Complexity** | **Max Input Size (n)** | **Explanation**                                   |
|---------------------|------------------------|---------------------------------------------------|
| **O(1)**            | Any                    | Constant time – performance is independent of input size. |
| **O(log n)**        | ~10^18                 | Logarithmic time – can handle very large input sizes efficiently (e.g., binary search). |
| **O(n)**            | ~10^8                  | Linear time – each operation takes a constant time, so can handle input sizes up to ~100 million. |
| **O(n log n)**      | ~10^6                  | Log-linear time – common for efficient sorting algorithms; can handle up to about 1 million elements. |
| **O(n^2)**          | ~10^4                  | Quadratic time – typical for nested loops; handles smaller input sizes, around 10,000. |
| **O(n^3)**          | ~500                   | Cubic time – very expensive; suitable for very small input sizes, around 500. |
| **O(2^n)**          | ~20-25                 | Exponential time – grows extremely fast; can handle input sizes around 20-25. Suitable for brute force solutions like subsets, permutations. |
| **O(n!)**           | ~10-12                 | Factorial time – impractical for larger sizes; usually handles input up to 10-12. Suitable for problems involving permutations or backtracking. |

### Explanation:
- **O(1)** (Constant time): Algorithms with constant time complexity do not depend on the input size and are considered very efficient.

- **O(log n)** (Logarithmic time): Algorithms that reduce the problem size by a fraction in each step (e.g., binary search) can handle extremely large inputs, even up to `10^18`.

- **O(n)** (Linear time): Linear algorithms process each element once, so they can handle input sizes of around `10^8` in 1 second.

- **O(n log n)** (Log-linear time): Commonly seen in efficient sorting algorithms (e.g., merge sort), these can handle inputs up to around `10^6` efficiently.

- **O(n^2)** (Quadratic time): Typically observed in algorithms involving nested loops. For this complexity, handling up to `10^4` inputs is feasible.

- **O(n^3)** (Cubic time): Seen in more complex nested iterations, cubic algorithms struggle with input sizes beyond 500.

- **O(2^n)** (Exponential time): These algorithms double the problem size in each step (e.g., solving problems recursively without optimization), limiting input size to around 20-25 elements.

- **O(n!)** (Factorial time): Used in brute force algorithms for permutations or combinations, the input size must be very small (typically ≤ 12).

---

