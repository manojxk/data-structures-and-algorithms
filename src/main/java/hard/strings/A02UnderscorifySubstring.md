Great work! Let's break this down like a beginner-friendly coding lesson.

---

## ✅ Problem Statement: *Underscorify Substring*

You are given:

* A **main string** (e.g. `"testthis is a testtest to see if testestest it works"`)
* A **substring** (e.g. `"test"`)

Your job is:

* Add **underscores (`_`)** *before and after* every occurrence of the substring in the main string.
* If **occurrences overlap**, wrap them as **a single group**.

### 👀 Example:

**Input:**

```java
String = "testthis is a testtest to see if testestest it works"
Substring = "test"
```

**Output:**

```java
"_test_this is a _testtest_ to see if _testestest_ it works"
```

---

## ✅ Steps to Solve:

### Step 1: **Find all occurrences of the substring**

Use `indexOf()` to scan the string for every occurrence (including overlapping).

### Step 2: **Merge overlapping or adjacent intervals**

If one occurrence ends at index 6 and the next starts at 5, they are overlapping and should be combined.

### Step 3: **Insert underscores**

Use the merged ranges to insert underscores `_` around the correct parts of the string.

---

## ✅ Java Code with Comments and Breakdown:

```java
package hard.strings;

import java.util.ArrayList;
import java.util.List;

public class A02UnderscorifySubstring {

  // MAIN FUNCTION
  public static String underscorifySubstring(String str, String substring) {
    List<int[]> locations = getLocations(str, substring);       // Step 1
    List<int[]> mergedLocations = mergeLocations(locations);    // Step 2
    return addUnderscores(str, mergedLocations);                // Step 3
  }

  // STEP 1: Find all starting and ending indices of substring
  private static List<int[]> getLocations(String str, String substring) {
    List<int[]> locations = new ArrayList<>();
    int startIdx = 0;

    while (startIdx < str.length()) {
      int nextIdx = str.indexOf(substring, startIdx); // next match
      if (nextIdx == -1) break;

      locations.add(new int[] {nextIdx, nextIdx + substring.length()});
      startIdx = nextIdx + 1; // allow overlapping matches
    }

    return locations;
  }

  // STEP 2: Merge overlapping or touching intervals
  private static List<int[]> mergeLocations(List<int[]> locations) {
    if (locations.isEmpty()) return locations;

    List<int[]> merged = new ArrayList<>();
    int[] previous = locations.get(0);

    for (int i = 1; i < locations.size(); i++) {
      int[] current = locations.get(i);

      if (current[0] <= previous[1]) { // Overlapping or touching
        previous[1] = Math.max(previous[1], current[1]);
      } else {
        merged.add(previous);
        previous = current;
      }
    }

    merged.add(previous); // Add the last one
    return merged;
  }

  // STEP 3: Add underscores to the string
  private static String addUnderscores(String str, List<int[]> locations) {
    StringBuilder result = new StringBuilder();
    int stringIdx = 0, locationIdx = 0;

    while (stringIdx < str.length() && locationIdx < locations.size()) {
      int[] location = locations.get(locationIdx);

      if (stringIdx < location[0]) {
        result.append(str.substring(stringIdx, location[0]));
      }

      result.append("_");
      result.append(str.substring(location[0], location[1]));
      result.append("_");

      stringIdx = location[1]; // jump to after this match
      locationIdx++;
    }

    // Append leftover characters (after last match)
    if (stringIdx < str.length()) {
      result.append(str.substring(stringIdx));
    }

    return result.toString();
  }

  // MAIN TEST CASE
  public static void main(String[] args) {
    String str = "testthis is a testtest to see if testestest it works";
    String substring = "test";

    String result = underscorifySubstring(str, substring);
    System.out.println("Underscorified String: " + result);
    // Expected: "_test_this is a _testtest_ to see if _testestest_ it works"
  }
}
```

---

## ✅ Time and Space Complexity:

### ⏱ Time Complexity:

* **Step 1 (`getLocations`)**: O(n) — `indexOf()` is linear per call and you call it once per match (with overlap allowed).
* **Step 2 (`mergeLocations`)**: O(m) — where m is the number of matches found.
* **Step 3 (`addUnderscores`)**: O(n + m) — traversing the string and inserting underscores.

👉 Overall: **O(n + m)**
(n = length of original string, m = number of matches)

### 💾 Space Complexity:

* `locations` and `mergedLocations`: O(m)
* Final output string: O(n)

👉 **O(n + m)** space

---

## 🧠 Summary:

| Step | Description                    | Time     | Space    |
| ---- | ------------------------------ | -------- | -------- |
| 1    | Find all matches               | O(n)     | O(m)     |
| 2    | Merge overlapping ranges       | O(m)     | O(m)     |
| 3    | Build final underscored string | O(n)     | O(n)     |
|      | **Total**                      | O(n + m) | O(n + m) |

---

Would you like me to optimize the underscore insertion step to be even more efficient with a marker array?
