**Problem Restatement**
You have $N$ cities arranged in a circle. For each city $i$:

* You can fill up `fuel[i]` gallons at its gas station.
* To drive from city $i$ to city $i+1$, you must travel a distance of `distances[i]` miles.
* Your car’s fuel efficiency is `mpg` miles per gallon, so each gallon lets you go `mpg` miles.

You start with an **empty** tank at some city. Your task is to find the **one and only** index `start` (in $[0 \ldots N-1]$) from which, if you begin with zero fuel, you can drive the entire circular route (visiting every city in order, wrapping around at the end) and return to your starting city without running out of gas. It is guaranteed that the total fuel available across all stations is exactly enough to cover the total distance of the circuit (when multiplied by `mpg`), so a solution exists and is unique.

> **Example**
>
> ```
> distances = [5, 25, 15, 10, 15]
> fuel      = [1,  2,  1,  0,  3]
> mpg       = 10
> ```
>
> Here,
>
> * City 0 gives you 1 gallon (→ 10 miles). You must travel 5 miles to city 1.
> * City 1 gives you 2 gallons (→ 20 miles). You must travel 25 miles to city 2, etc.
>   The correct starting index is **4**. If you start at city 4 (filling 3 gallons → 30 miles), you can complete the loop and end with nonnegative fuel.

---

## Why a Brute‐Force Would Be Inefficient

A naïve approach would try every index $0$ through $N-1$, simulate driving the whole loop, and see if you run out of gas. That takes $O(N)$ time **per** starting index, for a total of $O(N^2)$. Since $N$ can be large (e.g.\ a few thousand or more), we want a linear $O(N)$ solution.

---

## Greedy Approach (One‐Pass, O(N) Time)

The classic greedy insight is:

1. **Convert “fuel in gallons” → “distance you can travel.”**
   Since the car goes `mpg` miles per gallon, if city $i$ has `fuel[i]` gallons, that is effectively `fuel[i] * mpg` miles worth of gas. Let us precompute:

   $$
     \text{fuelMiles}[i] \;=\; \text{fuel}[i] \times \text{mpg}.
   $$

2. **At each city $i$, compute the net gain/loss of miles** when you fill up and then drive to the next city. Define:

   $$
     \text{net}[i] \;=\; \text{fuelMiles}[i] \;-\; \text{distances}[i].
   $$

   * If $\text{net}[i] \ge 0$, you can travel at least as many miles as you just gained at city $i$.
   * If $\text{net}[i] < 0$, you lose more miles in travel to the next city than you gained at $i$.

3. **The “tank” variable tracks how many miles of fuel remain** as you move along. You try to walk the circle in order, maintaining a running `tank` that starts at 0. You also maintain a `startIndex`—the candidate city from which you assume you began with zero fuel.

4. **Iterate from $0$ to $N-1$**, updating `tank` by adding `net[i]`.

   * If at any point `tank` becomes negative, it means that starting at your current `startIndex` can’t even make it to city $i+1$. All the intermediate stops from `startIndex` to $i$ have been tried in that segment, so none of them can work as a viable start (because if one of them could succeed, then the total surplus from that point to $i$ would never have let `tank` go below 0).
   * **Therefore**: when `tank < 0`, set `startIndex = i + 1` (i.e.\ try starting at the very next city), and reset `tank = 0`. Continue scanning forward.

5. **After one full pass**, the `startIndex` left by this process is the unique valid city. We do not need a second pass to check “wrap‐around,” because it is guaranteed that the total sum of all $\text{net}[i]$ across $i = 0 \ldots N-1$ is $\sum \text{fuelMiles}[i] - \sum \text{distances}[i] = 0$. That guarantees that once the local segment that failed is skipped, the remaining part of the circle must succeed.

---

## Time and Space Complexity

* **Time Complexity:** $O(N)$. We traverse the array of length $N$ exactly once, performing $O(1)$ work per city.
* **Space Complexity:** $O(1)$ extra (ignoring the space for the input arrays). We only store a few integers:

  * `startIndex`
  * `tank`
  * `N, mpg`
  * And a small temporary for computing `fuelMiles[i]`.

---

## Step‐by‐Step Java Solution

```java
package medium.greedyalgorithms;

public class A02ValidStartingCity {

  /**
   * Returns the index of the one valid city from which you can start with an empty tank
   * and complete the entire circular route without ever running out of fuel.
   *
   * @param distances Distance from city i to city i+1 (last city wraps to city 0).
   * @param fuel      Fuel available at each city, in gallons.
   * @param mpg       Miles per gallon of your car.
   * @return The unique starting city index, or -1 if none exists (though problem guarantees exactly one).
   */
  public static int canCompleteCircuit(int[] distances, int[] fuel, int mpg) {
    int N = distances.length;

    // 1) Convert gallons to miles at each city: fuelMiles[i] = fuel[i] * mpg
    //    Then compute net[i] = fuelMiles[i] - distances[i].
    //    Instead of building a separate array, we’ll compute on the fly.

    int startIndex = 0;   // Candidate starting city
    int tank = 0;         // Running “miles remainder” in our tank
    int totalSurplus = 0; // To verify that the total net of all cities is >= 0

    for (int i = 0; i < N; i++) {
      int fuelMiles = fuel[i] * mpg;
      int net        = fuelMiles - distances[i];
      tank += net;
      totalSurplus += net;

      // 2) If tank < 0, we cannot reach city (i+1) from startIndex.
      //    So no city in [startIndex..i] can be the valid start. Move start to i+1.
      if (tank < 0) {
        startIndex = i + 1;
        tank = 0;
      }
    }

    // 3) If totalSurplus < 0, even summing all net[] is negative, no solution;
    //    but problem guarantees there is exactly one. We include the check for completeness.
    if (totalSurplus < 0) {
      return -1; 
    }

    // 4) Otherwise, startIndex is the unique valid starting city.
    return startIndex % N;
  }

  // Example usage and test
  public static void main(String[] args) {
    int[] distances = {5, 25, 15, 10, 15};
    int[] fuel      = {1,  2,  1,  0,  3};
    int   mpg       = 10;
    int start = canCompleteCircuit(distances, fuel, mpg);
    System.out.println(start);  // Expected output: 4
  }
}
```

---

## How This Code Works (Line by Line)

1. **Convert Fuel to Miles**

   ```java
   int fuelMiles = fuel[i] * mpg;
   int net       = fuelMiles - distances[i];
   ```

   * At city `i`, you pick up `fuel[i]` gallons, which is `fuel[i] * mpg` miles worth of fuel.
   * You then must spend `distances[i]` miles to drive to the next city.
   * The **net** effect on your tank (in miles) is `net = fuelMiles - distances[i]`.

2. **Maintain a Running Tank**

   ```java
   tank += net;
   ```

   * `tank` tracks how many miles you can still drive with the fuel you have so far.
   * If `tank` ever becomes negative, it means the route from your current `startIndex` cannot reach city `i + 1`.

3. **Reset Start When Tank Falls Below Zero**

   ```java
   if (tank < 0) {
     startIndex = i + 1;
     tank = 0;
   }
   ```

   * By the time you reach city `i` and your `tank` < 0, you’ve discovered that starting from `startIndex` fails somewhere between `startIndex` and `i`.
   * Moreover, **no** city between `startIndex` and `i` can succeed either, because if they tried, they’d have even less tank at the failure point (shifting the starting city forward in that segment reduces the amount of net fuel you would have picked up).
   * Therefore, we move `startIndex = i + 1` and reset `tank = 0` to simulate “starting fresh” from the very next city.

4. **Check Total Surplus**

   ```java
   totalSurplus += net;
   ```

   * We also track the sum of `net` over all $i$. Because the problem guarantees that the **total fuel** exactly matches the **total distance**, the sum of all `net` values will be $\ge 0$.
   * If it were negative, we would return `-1`, but per problem statement, there is always exactly one solution.

5. **Return `startIndex`**

   ```java
   return totalSurplus < 0 ? -1 : startIndex % N;
   ```

   * In a valid input, `totalSurplus >= 0`, so we return `startIndex`. Taking `% N` is just to guard against the rare case `startIndex == N` (which can happen if the last city fails), but normally `startIndex` lands in $[0..N-1]$.

---

## Complexity Analysis

* **Time Complexity:**
  We loop once from $i = 0$ to $N-1$, performing $O(1)$ operations each. Hence **$O(N)$**.

* **Space Complexity:**
  We only store a few integer variables (`tank`, `totalSurplus`, `startIndex`, `fuelMiles`, etc.). Therefore **$O(1)$** extra space.

This one‐pass greedy method efficiently finds the one valid city index from which you can complete the circuit with a zero or positive tank at every step.
