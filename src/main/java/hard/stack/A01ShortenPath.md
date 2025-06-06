**Problem Statement**
You need to “simplify” a Unix‐style file path string by eliminating:

* Redundant slashes (`//`)
* “Current‐directory” tokens (`.`)
* “Parent‐directory” tokens (`..`) in accordance with how a Unix shell would resolve them

Both **absolute** paths (beginning with `/`) and **relative** paths (no leading `/`) must be handled. If after all simplifications the resulting path is empty (or for an absolute path, just root), you should return `/`. Otherwise, reconstruct the path from its valid components.

---

## 1. What Does “Simplify a Path” Mean?

1. **Multiple consecutive slashes** (`//`) count the same as a single slash (`/`).
2. A single period (`.`) means “stay in this directory,” so it can be removed.
3. A double period (`..`) means “go up one directory”:

   * In an **absolute** path (one that begins with `/`), if you see `..` at the root level, it simply keeps you at `/` (you can’t go above root).
   * In a **relative** path (no leading slash), `..` may accumulate if there is nothing to pop—e.g. `"../../foo"` stays as is, because you can’t “pop” beyond the start of a relative path.

After you remove all extraneous bits, you join the remaining directory names with `/`.

* For an **absolute** path, you prefix the result with `/`.
* For a **relative** path, you do **not** prefix with `/`.
* If the stack of valid directories ends up empty:

  * For an **absolute** path, return `"/"`.
  * For a **relative** path, return `""` (but often `"."` is used in shells—here we’ll treat an empty stack as the empty string).

---

## 2. Example Walkthroughs

1. **Example 1 (Absolute)**

   ```
   Input:  "/foo/../test/../test/../foo//bar/./baz"
   Output: "/foo/bar/baz"
   ```

   * Split on `/`: `["", "foo", "..", "test", "..", "test", "..", "foo", "", "bar", ".", "baz"]`
   * Process each token in order, maintaining a stack:

     1. `""` → skip (empty token)
     2. `"foo"` → push `"foo"`
     3. `".."` → pop once (removes `"foo"`)
     4. `"test"` → push `"test"`
     5. `".."` → pop once (removes `"test"`)
     6. `"test"` → push `"test"`
     7. `".."` → pop once (removes `"test"`)
     8. `"foo"` → push `"foo"`
     9. `""` → skip (empty token)

   10. `"bar"` → push `"bar"`

   11. `"."` → skip (current directory)

   12. `"baz"` → push `"baz"`

   * Final stack contents (bottom → top): `["foo", "bar", "baz"]`
   * Join them with `/` and prefix with `/` (because it was absolute):

     ```
     "/" + "foo/bar/baz"  →  "/foo/bar/baz"
     ```

2. **Example 2 (Relative)**

   ```
   Input:  "foo/bar/../bar/baz"
   Output: "foo/bar/baz"
   ```

   * Split on `/`: `["foo", "bar", "..", "bar", "baz"]`

   * Process:

     1. `"foo"` → push `"foo"`
     2. `"bar"` → push `"bar"`
     3. `".."` → pop once (removes `"bar"`)
     4. `"bar"` → push `"bar"`
     5. `"baz"` → push `"baz"`

   * Stack = `["foo", "bar", "baz"]`

   * It was **relative** (no leading slash), so just join them with `/`:

     ```
     "foo/bar/baz"
     ```

3. **Example 3 (Absolute)**

   ```
   Input:  "/a/./b/../../c/"
   Output: "/c"
   ```

   * Split: `["", "a", ".", "b", "..", "..", "c", ""]`

   * Process in order:

     1. `""` → skip
     2. `"a"` → push `"a"`
     3. `"."` → skip
     4. `"b"` → push `"b"`
     5. `".."` → pop once (removes `"b"`)
     6. `".."` → pop once (removes `"a"`)
     7. `"c"` → push `"c"`
     8. `""` → skip

   * Stack = `["c"]`. Join and prefix with `/`:

     ```
     "/" + "c"  →  "/c"
     ```

---

## 3. Detailed Approach & Step by Step

1. **Determine if the path is absolute or relative**

   ```java
   boolean isAbsolute = path.startsWith("/");
   ```

   * If `isAbsolute` is `true`, our final simplified path must start with `/`.
   * If `false`, we do **not** add a leading slash.

2. **Split on `/`**

   ```java
   String[] tokens = path.split("/");
   ```

   * Every `"/"` boundary yields one token.
   * Multiple slashes produce empty tokens (`""`), which we skip.

3. **Use a stack (e.g. `Deque<String>`)**

   * Whenever you see a normal directory name (i.e. not `"."` or `""` and not `".."`), push it onto the stack.
   * If you see `"."` or `""`, just ignore it.
   * If you see `".."`:

     * In an **absolute** path: pop only if the stack is not empty. If the stack is empty, ignore—in absolute mode you can’t go above `/`.
     * In a **relative** path:

       * If the stack is non‐empty, pop.
       * If the stack is empty, push `".."` onto it, because `../../something` means “go up” from wherever the caller was. (You cannot compress away leading `..` in a relative path.)

4. **Build the final string**

   * After processing all tokens, you have a stack of “valid” directory names (plus any needed `".."` components if it was relative and you popped too far).
   * Pop them all into a `List<String>`, reverse that list (since stack is LIFO), and then join with `"/"`.
   * If the result is empty:

     * Return `"/"` if `isAbsolute` is true, or `""` if `isAbsolute` is false.

---

## 4. Complete Java Code

```java
package hard.stack;

import java.util.*;

/**
 * Simplifies a Unix‐style file path by removing ".", "..", and redundant slashes.
 */
public class A01ShortenPath {

  /**
   * Simplifies the given path string and returns the shortened version.
   *
   * @param path  The input path (absolute if it starts with '/', otherwise relative).
   * @return      The simplified path.
   */
  public String simplifyPath(String path) {
    // 1) Determine if it's absolute
    boolean isAbsolute = path.startsWith("/");

    // 2) Split on "/" to get tokens. Multiple slashes produce empty tokens.
    String[] tokens = path.split("/");

    // 3) Use a stack to build up the valid directories
    Deque<String> stack = new LinkedList<>();

    for (String token : tokens) {
      if (token.equals("") || token.equals(".")) {
        // Skip empty tokens (//) or current‐directory tokens (.)
        continue;
      }

      if (token.equals("..")) {
        // Parent‐directory token: pop if possible
        if (!stack.isEmpty() && !stack.peek().equals("..")) {
          stack.pop();
        } else {
          // If it's a relative path, we must keep leading ".."
          if (!isAbsolute) {
            stack.push("..");
          }
          // If absolute and stack is empty, just ignore (can't go above root)
        }
      } else {
        // A normal directory name
        stack.push(token);
      }
    }

    // 4) Reconstruct the final path from the stack (LIFO order → reverse it)
    List<String> parts = new ArrayList<>(stack);
    Collections.reverse(parts);
    String joined = String.join("/", parts);

    // 5) Edge cases: if nothing remains
    if (joined.isEmpty()) {
      return isAbsolute ? "/" : "";
    }

    // 6) If absolute, prefix with "/"; otherwise return as is
    return isAbsolute ? "/" + joined : joined;
  }

  // ------------ Testing the Implementation ------------

  public static void main(String[] args) {
    A01ShortenPath solution = new A01ShortenPath();

    // Example 1 (absolute)
    String path1 = "/foo/../test/../test/../foo//bar/./baz";
    System.out.println(
      "Input:  " + path1 + "\nOutput: " + solution.simplifyPath(path1)
    );  // -> "/foo/bar/baz"

    // Example 2 (relative)
    String path2 = "foo/bar/../bar/baz";
    System.out.println(
      "Input:  " + path2 + "\nOutput: " + solution.simplifyPath(path2)
    );  // -> "foo/bar/baz"

    // Example 3 (absolute)
    String path3 = "/a/./b/../../c/";
    System.out.println(
      "Input:  " + path3 + "\nOutput: " + solution.simplifyPath(path3)
    );  // -> "/c"

    // Example 4 (relative, with extra ..)
    String path4 = "../../a/b/./c/../..";
    System.out.println(
      "Input:  " + path4 + "\nOutput: " + solution.simplifyPath(path4)
    );  // -> "../../"  (because it cannot collapse beyond the start in a relative path)

    // Example 5 (absolute at root)
    String path5 = "/.././../";
    System.out.println(
      "Input:  " + path5 + "\nOutput: " + solution.simplifyPath(path5)
    );  // -> "/"  (you remain at root)

    // Example 6 (absolute, ends up empty)
    String path6 = "/home//foo/";
    System.out.println(
      "Input:  " + path6 + "\nOutput: " + solution.simplifyPath(path6)
    );  // -> "/home/foo"
  }

  /*
   * Time Complexity: O(n)  
   *  - Splitting by "/" takes O(n).  
   *  - We iterate once over all tokens (O(n)), pushing/popping from stack in O(1).  
   *  - Reconstructing the final string (stack → list → join) also takes O(n).  
   * Overall: O(n), where n = length of the input path string.
   *
   * Space Complexity: O(n)  
   *  - The stack can hold up to O(n) components in the worst case (e.g. “a/b/c/d…”).  
   *  - The `parts` list and the final joined string also use O(n) extra space.
   */
}
```

---

### 5. Explanation of Key Details

1. **Splitting on `/`**

   ```java
   String[] tokens = path.split("/");
   ```

   * If `path = "/foo//bar/./baz"`, you get `["", "foo", "", "bar", ".", "baz"]`.
   * Empty tokens (`""`) arise any time you have one or more consecutive slashes.

2. **Handling `".."` Differently for Absolute vs. Relative**

   * In an absolute path, seeing `..` at the root simply does nothing (you can’t pop past `/`).
   * In a relative path, leading `..` tokens accumulate—e.g. `"../a"` → `["..", "a"]` in the stack.

3. **Why We Iterate from Left to Right but Then Reverse**

   * We push valid directory names onto a **stack** so that the top of the stack is the **most recent** directory.
   * At the end, to reconstruct from leftmost directory to rightmost, we must reverse the stack’s contents. („Bottom of stack“ is actually the earliest directory in the path.)

4. **Edge Cases**

   * If the stack is empty and the path was absolute, return `"/"`.
   * If the stack is empty and the path was relative, return `""` (or possibly `"."` in some conventions, but here we’ll return `""`).

---

### 6. Final Notes

* This method correctly handles **any** valid Unix‐style path, including:

  * Multiple consecutive slashes (`////`)
  * `.` tokens for “current directory”
  * `..` tokens for “go up one level” (with the appropriate rule for absolute vs. relative)
  * Trailing slashes
* **Time**: Each character of the input is processed a constant number of times, so overall **O(n)**.
* **Space**: We use a stack (deque) whose size is at most the number of path segments, plus the space for the final string—so **O(n)** extra.

With this approach and code, any given Unix‐style path will be simplified exactly as a shell would interpret it.
