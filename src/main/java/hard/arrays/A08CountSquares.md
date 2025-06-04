Below is a concise, step-by-step explanation of both the brute-force and optimized approaches for counting all squares whose four corners lie in a given set of points.

---

## 1. Brute-Force Solution (O(n⁴) Time, O(1) Space)

### Idea

* A square is determined by any four distinct corner points.
* If you pick any combination of 4 points from the input, check whether those four form a square.
* Count how many such quadruplets indeed form a square.

### Steps

1. Loop through every quadruplet of indices `(i, j, k, l)` with `0 ≤ i < j < k < l < n`.
2. Collect the four points `p[i], p[j], p[k], p[l]` into a small array `square = {p[i], p[j], p[k], p[l]}`.
3. Call `isSquare(square)`, which:

   * Computes all six pairwise squared distances among those four points.
   * Sorts those six distances.
   * Checks that the four smallest distances (the sides) are equal, the two largest (the diagonals) are equal, and that each diagonal length = 2×(side length).
4. If `isSquare(...)` returns true, increment your counter.
5. Return the counter at the end.

### Code Sketch

```java
public static int countSquaresBruteForce(int[][] points) {
  int n = points.length;
  int count = 0;

  for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
      for (int k = j + 1; k < n; k++) {
        for (int l = k + 1; l < n; l++) {
          int[][] quad = { points[i], points[j], points[k], points[l] };
          if (isSquare(quad)) {
            count++;
          }
        }
      }
    }
  }
  return count;
}

// Helper: returns true if the 4 corners form a square.
private static boolean isSquare(int[][] pts) {
  // Compute all 6 squared distances among pts[0..3].
  int[] dist = new int[6];
  int idx = 0;
  for (int a = 0; a < 4; a++) {
    for (int b = a + 1; b < 4; b++) {
      dist[idx++] = squaredDistance(pts[a], pts[b]);
    }
  }
  Arrays.sort(dist);
  // The first 4 must be equal (sides), and the last 2 (diagonals) equal,
  // and diagonal length = 2 × side length.
  return dist[0] > 0 
      && dist[0] == dist[1] 
      && dist[1] == dist[2] 
      && dist[2] == dist[3] 
      && dist[4] == dist[5] 
      && dist[4] == 2 * dist[0];
}

private static int squaredDistance(int[] p1, int[] p2) {
  int dx = p1[0] - p2[0];
  int dy = p1[1] - p2[1];
  return dx*dx + dy*dy;
}
```

#### Complexity

* We choose 4 out of n points in O(n⁴) time (since there are on the order of n⁴ such quadruplets).
* Checking each quadruplet is O(1) once you compute and sort six distances.
* Total Time: **O(n⁴)**.
* Space: only a few temporary variables, so **O(1)** extra space.

---

## 2. Optimized Solution (O(n²) Time, O(n) Space)

### Key Observation

Any square on the plane has its diagonals crossing at a right angle and each diagonal’s length = √2 times a side’s length. Equivalently, if two points can be opposite corners of a square (a diagonal), then:

* Their x-difference and y-difference must have the same absolute value (so that the diagonal is at 45°).
* The other two corners must be exactly `(x₁, y₂)` and `(x₂, y₁)` if the diagonal corners are `(x₁,y₁)` and `(x₂,y₂)`.

Hence, for each pair of points that meet the diagonal condition, we check if the other two candidate corners exist in our set.

### Steps

1. **Store all points in a hash set** (for O(1) existence checks).

   * Represent each point `(x,y)` as a string key `"x,y"`, or use a custom `Point` class with proper `hashCode`/`equals`.
2. **Loop over every pair** of distinct input points `p1 = (x₁,y₁)` and `p2 = (x₂,y₂)` (so O(n²) pairs).
3. **Check if they can be a square’s diagonal** by verifying `|x₁ − x₂| == |y₁ − y₂|`. If not, skip.

   * If yes, compute the other two candidate corners:

     ```
     p3 = (x₁, y₂)
     p4 = (x₂, y₁)
     ```
4. **If both p3 and p4 exist** in the hash set, you have found one square. Increment a counter.
5. **Since each square’s two diagonals appear twice** when you pick pairs `(p1,p2)` and `(p2,p1)`, you actually count each square twice. In the end, divide the counter by 2.

### Code Sketch

```java
public static int countSquaresOptimized(int[][] coords) {
  Set<String> pointSet = new HashSet<>();
  for (int[] c : coords) {
    pointSet.add(c[0] + "," + c[1]);
  }

  int count = 0;
  int n = coords.length;

  for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
      int x1 = coords[i][0], y1 = coords[i][1];
      int x2 = coords[j][0], y2 = coords[j][1];

      // Must be diagonal of a square → abs(x1 - x2) == abs(y1 - y2)
      if (Math.abs(x1 - x2) != Math.abs(y1 - y2)) continue;

      // The other two corners:
      String p3 = x1 + "," + y2;
      String p4 = x2 + "," + y1;

      // If both exist, we found one square.
      if (pointSet.contains(p3) && pointSet.contains(p4)) {
        count++;
      }
    }
  }

  // Each square was counted twice (once per diagonal), so divide by 2.
  return count / 2;
}
```

#### Why It Works

* Picking any two points `(x₁,y₁)` and `(x₂,y₂)` as the diagonal corners of a square requires:

  1. The diagonal must be at 45°, so the horizontal and vertical difference magnitudes match.
  2. The missing two corners of that square are `(x₁, y₂)` and `(x₂, y₁)`.
  3. If those two also appear in our set, then exactly four corners of a square exist.
* By checking all O(n²) pairs and performing two O(1) lookups, we find all possible squares in O(n²) time.
* Each square has two diagonals, so we count it twice; hence `count/2`.

### Complexity

* **Time:** O(n²) (two nested loops over the list of n points, each performing O(1) checks).
* **Space:** O(n) for storing all points in the hash set.

---

## 3. Putting It All Together

```java
import java.util.*;

public class A08CountSquares {

  // Brute‐force O(n⁴) version
  public static int countSquaresBruteForce(int[][] points) {
    int n = points.length;
    int count = 0;

    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        for (int k = j + 1; k < n; k++) {
          for (int l = k + 1; l < n; l++) {
            int[][] quad = {
              points[i],
              points[j],
              points[k],
              points[l]
            };
            if (isSquare(quad)) {
              count++;
            }
          }
        }
      }
    }
    return count;
  }

  // Helper to check whether 4 points form a square
  private static boolean isSquare(int[][] pts) {
    int[] dist = new int[6];
    int idx = 0;
    for (int a = 0; a < 4; a++) {
      for (int b = a + 1; b < 4; b++) {
        dist[idx++] = squaredDistance(pts[a], pts[b]);
      }
    }
    Arrays.sort(dist);
    // Must have four equal small sides > 0, two equal big diagonals, and diag = 2 * side
    return dist[0] > 0
        && dist[0] == dist[1]
        && dist[1] == dist[2]
        && dist[2] == dist[3]
        && dist[4] == dist[5]
        && dist[4] == 2 * dist[0];
  }

  private static int squaredDistance(int[] p1, int[] p2) {
    int dx = p1[0] - p2[0];
    int dy = p1[1] - p2[1];
    return dx*dx + dy*dy;
  }

  // Optimized O(n²) version
  public static int countSquaresOptimized(int[][] coordinates) {
    Set<String> pointSet = new HashSet<>();
    for (int[] c : coordinates) {
      pointSet.add(c[0] + "," + c[1]);
    }

    int count = 0;
    int n = coordinates.length;

    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int x1 = coordinates[i][0], y1 = coordinates[i][1];
        int x2 = coordinates[j][0], y2 = coordinates[j][1];

        // Diagonal must satisfy |dx| == |dy|
        if (Math.abs(x1 - x2) != Math.abs(y1 - y2)) continue;

        // The other two corners are (x1,y2) and (x2,y1)
        String p3 = x1 + "," + y2;
        String p4 = x2 + "," + y1;

        if (pointSet.contains(p3) && pointSet.contains(p4)) {
          count++;
        }
      }
    }

    // Each square was counted twice (once per diagonal), so divide by 2.
    return count / 2;
  }

  public static void main(String[] args) {
    int[][] coords1 = { {0,0}, {1,0}, {0,1}, {1,1} };
    System.out.println("Brute Force: " + countSquaresBruteForce(coords1));   // 1
    System.out.println("Optimized: " + countSquaresOptimized(coords1));     // 1

    int[][] coords2 = {
      {0,0}, {1,0}, {0,1}, {2,0}, {1,1}, {2,1}
    };
    System.out.println("Brute Force: " + countSquaresBruteForce(coords2));   // 2
    System.out.println("Optimized: " + countSquaresOptimized(coords2));     // 2
  }
}
```

**Summary**

* **Brute Force**: Enumerate all 4-point subsets in O(n⁴), check each in O(1).
* **Optimized**: For every pair `(p1,p2)`, treat them as a potential diagonal if `|dx|==|dy|`. Then check the two other corners in O(1) (via a hash set). That counts every square twice (one per diagonal), so return half. Runs in O(n²) time and O(n) space.
