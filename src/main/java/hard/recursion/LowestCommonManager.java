package hard.recursion;

import java.util.*;

/**
 * Problem Statement:
 *
 * <p>You are given three inputs, all of which are instances of the OrgChart class. The first input
 * is the top manager in an organizational chart, the second and third inputs are reports within the
 * chart. The top manager has a directReports property that points to the direct reports. The two
 * reports are guaranteed to be distinct.
 *
 * <p>Your task is to write a function that returns the lowest common manager (LCM) of the two
 * reports. The lowest common manager is defined as the deepest manager in the hierarchy who is a
 * manager for both reports.
 *
 * <p>Sample Input: OrgChart topManager = Node A; OrgChart reportOne = Node E; OrgChart reportTwo =
 * Node I;
 *
 * <p>A / \ B C / \ / \ D E F G / \ H I
 *
 * <p>Sample Output: Node B (as Node B is the lowest common manager for both E and I).
 */

// Class definition for OrgChart
// Class definition for OrgChart
class OrgChart {
  public char name; // The name (or identifier) of the manager (e.g., 'A', 'B', 'C', etc.)
  public List<OrgChart>
      directReports; // List of OrgChart objects representing direct reports of this manager

  // Constructor to initialize the OrgChart node with a name and empty list of direct reports
  public OrgChart(char name) {
    this.name = name;
    this.directReports = new ArrayList<>();
  }
}

public class LowestCommonManager {

  // Optimized Solution:
  // Time Complexity: O(n), where n is the total number of nodes in the OrgChart.
  // Space Complexity: O(d), where d is the depth of the OrgChart (recursion stack).
  public static OrgChart getLowestCommonManager(
      OrgChart topManager, OrgChart reportOne, OrgChart reportTwo) {
    return getOrgInfo(topManager, reportOne, reportTwo).lowestCommonManager;
  }

  // Helper class to store information during the recursion
  static class OrgInfo {
    public OrgChart lowestCommonManager; // The LCM found so far
    public int
        numImportantReports; // The number of important reports found so far (either 0, 1, or 2)

    public OrgInfo(OrgChart lowestCommonManager, int numImportantReports) {
      this.lowestCommonManager = lowestCommonManager;
      this.numImportantReports = numImportantReports;
    }
  }

  // Recursive function to find the lowest common manager
  private static OrgInfo getOrgInfo(OrgChart manager, OrgChart reportOne, OrgChart reportTwo) {
    int numImportantReports =
        0; // Count of how many important reports (reportOne, reportTwo) are found

    // Check each direct report of the current manager
    for (OrgChart directReport : manager.directReports) {
      // Recursively call the function on each direct report
      OrgInfo orgInfo = getOrgInfo(directReport, reportOne, reportTwo);

      // If a lowest common manager is found in any subtree, return it immediately (early exit)
      if (orgInfo.lowestCommonManager != null) {
        return orgInfo;
      }

      // Increment numImportantReports by how many important reports are found in the subtree
      numImportantReports += orgInfo.numImportantReports;
    }

    // Check if the current manager is one of the reports we're looking for
    if (manager == reportOne || manager == reportTwo) {
      numImportantReports++; // Increment because the current manager is an important report
    }

    // If both reports are found in the subtree, the current manager is the LCM
    OrgChart lowestCommonManager = (numImportantReports == 2) ? manager : null;

    // Return OrgInfo object with the LCM (if found) and the count of important reports
    return new OrgInfo(lowestCommonManager, numImportantReports);
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Sample input
    OrgChart A = new OrgChart('A');
    OrgChart B = new OrgChart('B');
    OrgChart C = new OrgChart('C');
    OrgChart D = new OrgChart('D');
    OrgChart E = new OrgChart('E');
    OrgChart F = new OrgChart('F');
    OrgChart G = new OrgChart('G');
    OrgChart H = new OrgChart('H');
    OrgChart I = new OrgChart('I');

    // Constructing the org chart
    A.directReports.add(B);
    A.directReports.add(C);
    B.directReports.add(D);
    B.directReports.add(E);
    C.directReports.add(F);
    C.directReports.add(G);
    D.directReports.add(H);
    D.directReports.add(I);

    // Test Case: Finding the lowest common manager for E and I
    OrgChart lcm = getLowestCommonManager(A, E, I);
    System.out.println("Lowest Common Manager: " + lcm.name); // Expected Output: B
  }
}
