/*
 * Problem Statement:
 * A company has N interns and N teams. Each intern and each team has ranked their preferences for the other.
 * You need to assign one intern to each team such that:
 * 1. The assignment is stable (no unmatched intern-team pair would both prefer to be matched with each other).
 * 2. The solution is optimal for the interns (each intern is matched with the best possible team according to their preferences).
 *
 * Input:
 * - interns: A 2D list where interns[i] contains the preferences of intern i.
 * - teams: A 2D list where teams[j] contains the preferences of team j.
 *
 * Output:
 * - A 2D list where each sublist [internIndex, teamIndex] represents a matching.
 *
 * Example:
 * interns = [
 *   [0, 1, 2],
 *   [1, 0, 2],
 *   [1, 2, 0]
 * ]
 * teams = [
 *   [2, 1, 0],
 *   [1, 2, 0],
 *   [0, 2, 1]
 * ]
 *
 * Output:
 * [
 *   [0, 0],
 *   [1, 1],
 *   [2, 2]
 * ]
 *
 * This is the most optimal solution for interns.
 */
/*Gale-Shapley Algorithm (Optimized Solution)
Approach:
The Gale-Shapley algorithm (Deferred Acceptance Algorithm) is a well-known solution for the Stable Marriage Problem. It works as follows:

Each intern proposes to their most preferred team that has not yet rejected them.
Each team evaluates the proposals and tentatively accepts the best proposal (according to their preferences), rejecting the others.
Rejected interns propose to their next preferred team in the next round.
This process repeats until all interns are matched.

Time Complexity:
O(N^2): Each intern may propose to all teams, and each team may evaluate all proposals.
Space Complexity:
O(N): For storing preferences and maintaining proposal lists.*/
package medium;

import java.util.*;

public class StableInternships {

  // Gale-Shapley Algorithm (Deferred Acceptance Algorithm)
  public static List<int[]> stableMatching(int[][] interns, int[][] teams) {
    int N = interns.length;

    // Create a map to store the team preferences in a more accessible format
    int[][] teamPrefs = new int[N][N];
    for (int team = 0; team < N; team++) {
      for (int rank = 0; rank < N; rank++) {
        teamPrefs[team][teams[team][rank]] = rank;
      }
    }

    // Keep track of the current proposal index for each intern
    int[] proposalIndex = new int[N];
    // Maintain the matched pair for each team (initialized as -1)
    int[] teamMatch = new int[N];
    Arrays.fill(teamMatch, -1);
    // Track if an intern is free or not
    boolean[] internFree = new boolean[N];
    Arrays.fill(internFree, true);

    // Queue of free interns
    Queue<Integer> freeInterns = new LinkedList<>();
    for (int intern = 0; intern < N; intern++) {
      freeInterns.add(intern);
    }

    while (!freeInterns.isEmpty()) {
      int intern = freeInterns.poll();
      int team = interns[intern][proposalIndex[intern]];

      // If the team is free, match the intern with the team
      if (teamMatch[team] == -1) {
        teamMatch[team] = intern;
      } else {
        int otherIntern = teamMatch[team];
        // If the team prefers the new intern over the currently matched intern
        if (teamPrefs[team][intern] < teamPrefs[team][otherIntern]) {
          teamMatch[team] = intern;
          // The rejected intern becomes free
          freeInterns.add(otherIntern);
        } else {
          // The team prefers the current intern, so the proposing intern remains free
          freeInterns.add(intern);
        }
      }
      // Move to the next proposal
      proposalIndex[intern]++;
    }

    // Prepare the output format
    List<int[]> result = new ArrayList<>();
    for (int team = 0; team < N; team++) {
      result.add(new int[] {teamMatch[team], team});
    }
    return result;
  }

  public static void main(String[] args) {
    int[][] interns = {
      {0, 1, 2},
      {1, 0, 2},
      {1, 2, 0}
    };
    int[][] teams = {
      {2, 1, 0},
      {1, 2, 0},
      {0, 2, 1}
    };

    List<int[]> result = stableMatching(interns, teams);

    // Output the result
    for (int[] pair : result) {
      System.out.println(Arrays.toString(pair));
    }
  }
}
