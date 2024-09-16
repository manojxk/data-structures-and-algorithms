package medium.linkedlists;

/*
 * Problem Statement:
 * You are given two singly linked lists of potentially unequal lengths. These linked lists may intersect at a shared node, and from that node onwards, the lists share the same sequence of nodes.
 * Write a function that returns the intersection node or returns null if there is no intersection.
 *
 * Each LinkedList node has an integer value and a next node pointing to the next node in the list or to null if it's the tail of the list.
 *
 * Note: Your function should return an existing node. It should not modify either of the linked lists, and it should not create any new linked lists.
 *
 * Example:
 * Input: linkedListOne = 2 -> 3 -> 1 -> 4, linkedListTwo = 8 -> 7 -> 1 -> 4
 * Output: 1 -> 4 // The lists intersect at the node with value 1.
 */

/*
 * Brute Force Solution
 *
 * Approach:
 * - For each node in the first linked list, traverse the entire second linked list to check if any node matches.
 * - If a match is found, return that node as the intersection.
 * - If no match is found after checking all nodes, return null.
 *
 * Time Complexity:
 * O(n * m), where n and m are the lengths of the two linked lists. This is because for each node in the first list, we traverse the entire second list.
 *
 * Space Complexity:
 * O(1), as no additional data structures are used.
 */

import java.util.*;

public class A04LinkedListIntersection {

  // Definition of ListNode
  static class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  // Brute Force Solution
  public static ListNode getIntersectionNodeBruteForce(ListNode headA, ListNode headB) {
    ListNode currentA = headA;
    while (currentA != null) {
      ListNode currentB = headB;
      while (currentB != null) {
        if (currentA == currentB) {
          return currentA; // Intersection found
        }
        currentB = currentB.next;
      }
      currentA = currentA.next;
    }
    return null; // No intersection found
  }

  /*
   * Optimized Solution 1: Using HashSet
   *
   * Approach:
   * - Traverse the first linked list and store each node's reference in a HashSet.
   * - Traverse the second linked list, and for each node, check if it exists in the HashSet.
   * - If a node is found in the HashSet, that node is the intersection.
   * - If no node is found in the HashSet, return null.
   *
   * Time Complexity:
   * O(n + m), where n and m are the lengths of the two linked lists. Each list is traversed once.
   *
   * Space Complexity:
   * O(n) or O(m), depending on which list is stored in the HashSet.
   */

  public static ListNode getIntersectionNodeHashSet(ListNode headA, ListNode headB) {
    HashSet<ListNode> nodesSeen = new HashSet<>();

    ListNode currentA = headA;
    while (currentA != null) {
      nodesSeen.add(currentA);
      currentA = currentA.next;
    }

    ListNode currentB = headB;
    while (currentB != null) {
      if (nodesSeen.contains(currentB)) {
        return currentB; // Intersection found
      }
      currentB = currentB.next;
    }

    return null; // No intersection found
  }

  /*
   * Optimized Solution 2: Two Pointer Technique
   *
   * Approach:
   * - Initialize two pointers, one for each linked list.
   * - Traverse the two linked lists simultaneously.
   * - When a pointer reaches the end of a list, redirect it to the head of the other list.
   * - Continue until the two pointers meet at the intersection or both reach null if no intersection exists.
   * - The key insight is that if the lists intersect, the two pointers will meet at the intersection node after at most two traversals of the lists.
   *
   * Time Complexity:
   * O(n + m), where n and m are the lengths of the two linked lists. Each list is traversed at most twice.
   *
   * Space Complexity:
   * O(1), as no additional data structures are used.
   */

  public static ListNode getIntersectionNodeTwoPointers(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) {
      return null;
    }

    ListNode pointerA = headA;
    ListNode pointerB = headB;

    // Traverse both lists simultaneously
    while (pointerA != pointerB) {
      // Move to the next node or to the head of the other list
      pointerA = (pointerA == null) ? headB : pointerA.next;
      pointerB = (pointerB == null) ? headA : pointerB.next;
    }

    // Either they meet at the intersection, or both are null (no intersection)
    return pointerA;
  }

  /*
   * Solution: Optimized by counting lengths
   *
   * Approach:
   * - Calculate the lengths of both linked lists.
   * - Move the pointer of the longer list ahead by the difference in lengths to align both lists.
   * - Traverse both lists simultaneously until an intersection is found or both pointers reach null.
   *
   * Time Complexity:
   * O(n + m), where n and m are the lengths of the two linked lists. We first traverse both lists to calculate their lengths, then align and traverse them simultaneously.
   *
   * Space Complexity:
   * O(1), as we are using only a constant amount of extra space for the two pointers and variables.
   */

  public static ListNode getIntersectionNodeLengthBased(ListNode headA, ListNode headB) {
    int lenA = getLength(headA);
    int lenB = getLength(headB);

    // Align both lists by moving the pointer of the longer list
    if (lenA > lenB) {
      headA = moveForward(headA, lenA - lenB);
    } else {
      headB = moveForward(headB, lenB - lenA);
    }

    // Traverse both lists together to find the intersection
    while (headA != null && headB != null) {
      if (headA == headB) {
        return headA; // Intersection found
      }
      headA = headA.next;
      headB = headB.next;
    }

    return null; // No intersection found
  }

  // Helper function to get the length of a linked list
  private static int getLength(ListNode head) {
    int length = 0;
    while (head != null) {
      length++;
      head = head.next;
    }
    return length;
  }

  // Helper function to move a pointer forward by 'n' steps
  private static ListNode moveForward(ListNode head, int steps) {
    while (steps > 0 && head != null) {
      head = head.next;
      steps--;
    }
    return head;
  }

  // Main function to demonstrate the solutions
  public static void main(String[] args) {
    // Example: Create two intersecting linked lists
    ListNode common = new ListNode(1);
    common.next = new ListNode(4);

    ListNode headA = new ListNode(2);
    headA.next = new ListNode(3);
    headA.next.next = common; // Intersection at node with value 1

    ListNode headB = new ListNode(8);
    headB.next = new ListNode(7);
    headB.next.next = common; // Intersection at node with value 1

    // Brute Force Solution
    System.out.println(
        "Brute Force Intersection Node: " + getIntersectionNodeBruteForce(headA, headB).value);

    // HashSet Solution
    System.out.println(
        "HashSet Intersection Node: " + getIntersectionNodeHashSet(headA, headB).value);

    // Two Pointer Solution
    System.out.println(
        "Two Pointer Intersection Node: " + getIntersectionNodeTwoPointers(headA, headB).value);

    // Optimized Solution by Length
    System.out.println(
        "Length-Based Intersection Node: " + getIntersectionNodeLengthBased(headA, headB).value);
  }
}
