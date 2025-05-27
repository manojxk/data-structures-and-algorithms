**Problem Explanation**

You have a list of **competitions**, where each competition is a pair of teams:

```java
[String homeTeam, String awayTeam]
```

You also have a parallel array of **results**, where `results[i]` is:

* `1` if the **home team** won competition `i`,
* `0` if the **away team** won competition `i`.

Every win earns the winner **3 points**, and there are no ties. Your goal is to figure out **which team** finishes the tournament with the **highest** total points.

---

## Approach

1. **Use a hash map** (`Map<String,Integer>`) to track each team’s cumulative score.
2. **Iterate once** over all competitions:

   * Determine the winning team for this match.
   * Add **3 points** to that team in the map.
   * Keep track of the team with the **highest score so far**.
3. At the end, the tracked “highest-scoring” team is your tournament winner.

This single-pass strategy runs in **O(n)** time (where n = number of competitions) and uses **O(k)** space (where k = number of distinct teams).

---

## Detailed Steps

1. **Initialize**:

   ```java
   Map<String,Integer> scores = new HashMap<>();
   String  tournamentWinner = "";   // Will hold the current leader
   int     maxScore         = 0;    // Highest score seen so far
   ```
2. **Loop through each competition** (`i` from `0` to `competitions.length-1`):

   * Extract home and away teams:

     ```java
     String home = competitions[i][0];
     String away = competitions[i][1];
     ```
   * Determine the winner based on `results[i]`:

     ```java
     String winner = (results[i] == 1) ? home : away;
     ```
   * **Update** that team’s score in the map (defaulting to 0 if first time):

     ```java
     scores.put(winner, scores.getOrDefault(winner, 0) + 3);
     ```
   * **Check** if this team’s new total exceeds `maxScore`. If yes, update:

     ```java
     int winnerScore = scores.get(winner);
     if (winnerScore > maxScore) {
       maxScore = winnerScore;
       tournamentWinner = winner;
     }
     ```
3. **Return** `tournamentWinner`.

---

## Java Implementation

```java
package easy.arrays;

import java.util.HashMap;
import java.util.Map;

public class TournamentWinner {

  /**
   * Returns the name of the team with the highest total points.
   * Each win = 3 points. No ties.
   *
   * Time:  O(n)     (one pass through competitions)
   * Space: O(k)     (scores map for k distinct teams)
   */
  public static String findTournamentWinner(String[][] competitions, int[] results) {
    Map<String, Integer> scores = new HashMap<>();
    String tournamentWinner = "";
    int maxScore = 0;

    for (int i = 0; i < competitions.length; i++) {
      String home = competitions[i][0];
      String away = competitions[i][1];
      // Determine winner: 1 → home, 0 → away
      String winner = (results[i] == 1) ? home : away;

      // Add 3 points for this win
      scores.put(winner, scores.getOrDefault(winner, 0) + 3);

      // If this team now leads, update our tracker
      int currentScore = scores.get(winner);
      if (currentScore > maxScore) {
        maxScore = currentScore;
        tournamentWinner = winner;
      }
    }

    return tournamentWinner;
  }

  public static void main(String[] args) {
    String[][] competitions = {
      {"HTML", "C#"},
      {"C#", "Python"},
      {"Python", "HTML"}
    };
    int[] results = {0, 0, 1};

    // HTML vs C# → away wins (C#): C# = 3
    // C#  vs Python → away wins (Python): Python = 3
    // Python vs HTML → home wins (Python): Python = 6
    //
    // Final: HTML=0, C#=3, Python=6 → Winner = "Python"
    System.out.println(findTournamentWinner(competitions, results)); 
    // Output: Python
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**
  We process each of the **n** competitions exactly once, doing O(1) work per iteration → **O(n)**.

* **Space Complexity:**
  We store scores for each of the **k** distinct teams in a hash map → **O(k)**.
