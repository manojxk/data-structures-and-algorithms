**Solution Explanation**

We need to find a **stable matching** between **N interns** and **N teams**, using each group’s ranked preferences. Furthermore, among all stable matchings, we must return the one that is **optimal for the interns** (i.e., each intern gets their best‐possible team under any stable matching). This is exactly the classic **Gale–Shapley** algorithm, with interns playing the role of “proposers.”

---

### Gale–Shapley (Intern‐Optimal) Algorithm

1. **Initialize**

   * Every intern is initially “free” (unmatched).
   * No team is matched to any intern yet.

2. **Precompute team rankings**
   For each team `j`, build a map `rank[j][i]` = the position (0‐based) of intern `i` in `teams[j]`’s preference list. This lets us answer “Does team j prefer intern a over intern b?” in O(1).

3. **Each intern keeps a pointer** `nextToPropose[i]` = index in their preference list, telling which team to propose to next. Initially `nextToPropose[i] = 0` for all interns.

4. **While** there is any **free intern** `i` who has not yet proposed to **all** teams:

   * Let `team = interns[i][ nextToPropose[i] ]`.
   * Increment `nextToPropose[i]++` so that next time `i` will propose to the next team on their list.
   * If `team` is **unmatched**, match `i`↔`team`.
   * Else `team` is currently matched to some other intern `i'`. Compare:

     * If `team`’s ranking says it **prefers** `i` over `i'` (i.e. `rank[team][i] < rank[team][i']`), then:

       * Unmatch `(i', team)` and make `i'` “free” again.
       * Match `(i, team)`.
     * Otherwise, `team` rejects `i`, so `i` remains free and will propose to the next team on their list in the next iteration.

5. Once every intern is matched, the algorithm terminates. The result is guaranteed to be a stable, intern‐optimal matching.

---

## Implementation

```java
import java.util.*;

public class StableInternships {
  /**
   * Returns a stable, intern-optimal matching between interns and teams.
   *
   * @param interns A list of N lists, where interns[i] is the preference list of intern i
   *                (teams in descending order of preference).
   * @param teams   A list of N lists, where teams[j] is the preference list of team j
   *                (interns in descending order of preference).
   * @return        A list of pairs [internIndex, teamIndex], one for each intern,
   *                representing the final assignments (order does not matter).
   */
  public static List<int[]> stableMatching(int[][] interns, int[][] teams) {
    int N = interns.length;
    // Build ranking for each team: rank[j][i] = position of intern i in teams[j]’s list
    int[][] rank = new int[N][N];
    for (int j = 0; j < N; j++) {
      for (int position = 0; position < N; position++) {
        int internId = teams[j][position];
        rank[j][internId] = position;
      }
    }

    // nextToPropose[i] = index in interns[i]’s list of the next team to propose to
    int[] nextToPropose = new int[N];
    // matchedIntern[j] = which intern is currently matched to team j (–1 if none)
    int[] matchedIntern = new int[N];
    Arrays.fill(matchedIntern, -1);
    // matchedTeam[i] = which team intern i is currently matched with (–1 if free)
    int[] matchedTeam = new int[N];
    Arrays.fill(matchedTeam, -1);

    // Use a queue (or stack) of free interns who still have teams left to propose to
    Deque<Integer> freeInterns = new ArrayDeque<>();
    for (int i = 0; i < N; i++) {
      freeInterns.add(i);
    }

    while (!freeInterns.isEmpty()) {
      int i = freeInterns.pollFirst();  // a free intern
      // He must propose to next team on his list
      int team = interns[i][ nextToPropose[i] ];
      nextToPropose[i]++; // Next time he’ll propose to the next team

      if (matchedIntern[team] == -1) {
        // Team is free → match them
        matchedIntern[team] = i;
        matchedTeam[i] = team;
      } else {
        int iPrime = matchedIntern[team];
        // Does team prefer i over iPrime?
        if (rank[team][i] < rank[team][iPrime]) {
          // Team prefers i → “unmatch” iPrime, match i
          matchedIntern[team] = i;
          matchedTeam[i] = team;
          // iPrime becomes free again
          matchedTeam[iPrime] = -1;
          // If iPrime still has teams left to propose to, put back in queue
          if (nextToPropose[iPrime] < N) {
            freeInterns.addLast(iPrime);
          }
        } else {
          // Team rejects i → i stays free, propose next round if possible
          if (nextToPropose[i] < N) {
            freeInterns.addLast(i);
          }
        }
      }
    }

    // Build output: list of [intern, team] pairs
    List<int[]> output = new ArrayList<>();
    for (int intern = 0; intern < N; intern++) {
      output.add(new int[]{ intern, matchedTeam[intern] });
    }
    return output;
  }

  // Example usage with the sample input
  public static void main(String[] args) {
    int[][] interns = {
      {0, 1, 2}, // Intern 0 prefers team 0, then 1, then 2
      {1, 0, 2}, // Intern 1 prefers team 1, then 0, then 2
      {1, 2, 0}  // Intern 2 prefers team 1, then 2, then 0
    };
    int[][] teams = {
      {2, 1, 0}, // Team 0 prefers intern 2, then 1, then 0
      {1, 2, 0}, // Team 1 prefers intern 1, then 2, then 0
      {0, 2, 1}  // Team 2 prefers intern 0, then 2, then 1
    };

    List<int[]> matching = stableMatching(interns, teams);
    // Print results:
    // Intern-optimal stable matching is [[0,0],[1,1],[2,2]] (in any order)
    for (int[] pair : matching) {
      System.out.println("Intern " + pair[0] + " → Team " + pair[1]);
    }
  }
}
```

---

### Why This Is Intern‐Optimal and Stable

* **Intern‐optimal**: Because interns are the “proposers” in Gale–Shapley, each intern ends up with the **best possible team** they could ever get in *any* stable matching.
* **Stability**: By construction, there is no unmatched pair $(i, j)$ where intern *i* prefers team *j* over their assigned team and team *j* prefers *i* over its assigned intern. If such a pair existed, that team *j* would have “held out” for *i* when *i* proposed (or vice versa), but the algorithm ensures that can’t happen.

The time complexity is $O(N^2)$ in the worst case, because each intern may propose to every team once, and each proposal takes $O(1)$ time to compare rankings. The space complexity is $O(N^2)$ only if you count the input preference lists; the additional data structures (arrays of length $N$ for matches, plus an $N \times N$ “rank” table) use $O(N^2)$ space as well.
