/*
 * Problem Statement:
 *
 * An algorithms tournament is taking place where teams compete against each other in a round-robin format.
 * Each team competes against every other team exactly once, with one team designated as the home team and
 * the other as the away team for each competition. The competition results are recorded, with 3 points
 * awarded to the winning team and 0 points to the losing team.
 *
 * Given an array of competitions and an array of results, write a function that returns the name of the
 * team that wins the tournament. The competitions array consists of pairs [homeTeam, awayTeam],
 * and the results array contains integers, where a 1 indicates that the home team won and a 0 indicates
 * that the away team won.
 *
 * Example:
 * Input:
 *   competitions = [
 *     ["HTML", "C#"],
 *     ["C#", "Python"],
 *     ["Python", "HTML"],
 *   ]
 *   results = [0, 0, 1]
 * Output: "Python"
 *
 * Explanation:
 * - C# beats HTML, Python beats C#, and Python beats HTML.
 * - Final scores: HTML - 0 points, C# - 3 points, Python - 6 points
 * - The team with the highest score, "Python", wins the tournament.
 */

package easy.arrays;

import java.util.HashMap;
import java.util.Map;

public class TournamentWinner {

  // Brute Force Solution
  public static String findTournamentWinner(String[][] competitions, int[] results) {
    Map<String, Integer> scores = new HashMap<>();
    String tournamentWinner = "";
    int maxScore = 0;

    for (int i = 0; i < competitions.length; i++) {
      String homeTeam = competitions[i][0];
      String awayTeam = competitions[i][1];
      String winningTeam = results[i] == 1 ? homeTeam : awayTeam;

      scores.put(winningTeam, scores.getOrDefault(winningTeam, 0) + 3);

      if (scores.get(winningTeam) > maxScore) {
        maxScore = scores.get(winningTeam);
        tournamentWinner = winningTeam;
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
    System.out.println(findTournamentWinner(competitions, results)); // Output: "Python"
  }
}

/*
O(n) time | O(k) space.*/
