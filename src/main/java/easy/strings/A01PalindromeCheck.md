**Problem Restatement**

You need to determine whether a given **non‐empty** string reads the **same forwards and backwards**. Such a string is called a **palindrome**. Even a single character (like `"a"`) is trivially a palindrome.

**Example:**

* `"abcdcba"` → **true**
* `"racecar"`  → **true**
* `"hello"`    → **false**

---

## 1. String Concatenation Approach (Inefficient)

### Idea

* Build a **reversed** copy of the input by concatenating characters from end to start.
* Compare the reversed string to the original.

### Steps

1. Initialize `String reversed = ""`.
2. Loop `i` from `string.length() - 1` down to `0`:

   ```java
   reversed += string.charAt(i);
   ```
3. After the loop, check:

   ```java
   return string.equals(reversed);
   ```

### Code

```java
public static boolean isPalindrome(String string) {
  String reversed = "";
  for (int i = string.length() - 1; i >= 0; i--) {
    reversed += string.charAt(i);  // each += creates a new string
  }
  return string.equals(reversed);
}
```

### Complexity

* **Time:** O(n²) because each `+` creates a new string of growing length.
* **Space:** O(n) for the reversed string.

---

## 2. StringBuilder Approach

### Idea

* Use `StringBuilder`, which supports efficient **in‐place** reversal.

### Steps

1. Create `StringBuilder sb = new StringBuilder(string)`.
2. Call `sb.reverse()` to reverse it in O(n) time.
3. Convert back to `String` and compare:

   ```java
   return string.equals(sb.reverse().toString());
   ```

### Code

```java
public static boolean isPalindrome2(String string) {
  StringBuilder sb = new StringBuilder(string);
  String reversed = sb.reverse().toString();
  return string.equals(reversed);
}
```

### Complexity

* **Time:** O(n)
* **Space:** O(n) for the `StringBuilder` and reversed string.

---

## 3. Recursive Two-Pointer Approach

### Idea

* Use recursion to compare the **first** and **last** characters.
* If they match, recurse on the **substring** excluding those two characters.

### Steps

1. Write a helper `boolean check(String s, int left, int right)`:

   * If `left >= right`, all characters have matched → return `true`.
   * If `s.charAt(left) != s.charAt(right)`, mismatch → return `false`.
   * Otherwise, return `check(s, left+1, right-1)`.
2. Call `check(string, 0, string.length()-1)`.

### Code

```java
public static boolean isPalindrome3(String string) {
  return check(string, 0, string.length() - 1);
}

private static boolean check(String s, int left, int right) {
  if (left >= right) return true;               // met or crossed pointers
  if (s.charAt(left) != s.charAt(right)) return false; 
  return check(s, left + 1, right - 1);
}
```

### Complexity

* **Time:** O(n)
* **Space:** O(n) for the recursion stack in the worst case.

---

## 4. Iterative Two-Pointer Technique (Best)

### Idea

* Maintain two indices, `left` at the **start** and `right` at the **end**.
* Move them **inwards**, comparing characters at each step.

### Steps

1. Initialize `int left = 0, right = string.length() - 1;`.
2. While `left < right`:

   * If `string.charAt(left) != string.charAt(right)`, return `false`.
   * Else do `left++`, `right--`.
3. If you finish the loop, return `true`.

### Code

```java
public static boolean isPalindrome4(String string) {
  int left  = 0;
  int right = string.length() - 1;

  while (left < right) {
    if (string.charAt(left) != string.charAt(right)) {
      return false;
    }
    left++;
    right--;
  }
  return true;
}
```

### Complexity

* **Time:** O(n) — one pass from both ends.
* **Space:** O(1) — only two integer pointers.

---

## Which Method to Use?

* For **simplicity** and **best performance**, use the **two-pointer iterative** method (`isPalindrome4`).
* If you prefer a very concise solution and don’t mind O(n) extra space, `StringBuilder` (`isPalindrome2`) is fine.
* Avoid the naive string-concatenation approach in real code because of its **O(n²)** time.

All methods correctly identify palindromes; choose based on your time/space needs and code clarity.
