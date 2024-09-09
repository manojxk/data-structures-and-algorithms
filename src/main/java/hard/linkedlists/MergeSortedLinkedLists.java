package hard.linkedlists;

/**
 * Problem Statement:
 *
 * <p>Write a function that takes in the heads of two singly linked lists that are in sorted order.
 * The function should merge these two lists in place (i.e., without creating a new list) and return
 * the head of the merged list, which should also be in sorted order.
 *
 * <p>Each LinkedList node has an integer value and a next pointer pointing to the next node in the
 * list or null if it's the tail of the list.
 *
 * <p>You can assume that the input linked lists will always have at least one node (i.e., heads
 * will never be null).
 *
 * <p>Sample Input: headOne = 2 -> 6 -> 7 -> 8 headTwo = 1 -> 3 -> 4 -> 5 -> 9 -> 10
 *
 * <p>Sample Output: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
 *
 * <p>Time Complexity: O(n + m), where `n` and `m` are the lengths of the two linked lists. Space
 * Complexity: O(1), as we are modifying the lists in place.
 */
class LinkedList {
  int value;
  LinkedList next;

  public LinkedList(int value) {
    this.value = value;
    this.next = null;
  }
}

public class MergeSortedLinkedLists {

  // Function to merge two sorted linked lists in place
  public static LinkedList mergeLinkedLists(LinkedList headOne, LinkedList headTwo) {
    // If one of the lists is empty, return the other list
    if (headOne == null) return headTwo;
    if (headTwo == null) return headOne;

    // Identify the new head of the merged list (smaller of the two heads)
    LinkedList mergedHead;
    if (headOne.value < headTwo.value) {
      mergedHead = headOne;
      headOne = headOne.next;
    } else {
      mergedHead = headTwo;
      headTwo = headTwo.next;
    }

    // Track the current node in the merged list
    LinkedList current = mergedHead;

    // Merge the lists by comparing values from both lists
    while (headOne != null && headTwo != null) {
      if (headOne.value < headTwo.value) {
        current.next = headOne;
        headOne = headOne.next;
      } else {
        current.next = headTwo;
        headTwo = headTwo.next;
      }
      current = current.next;
    }

    // If one list is exhausted, append the remaining part of the other list
    if (headOne != null) {
      current.next = headOne;
    } else if (headTwo != null) {
      current.next = headTwo;
    }

    // Return the new head of the merged list
    return mergedHead;
  }

  // Main function to test the mergeLinkedLists function
  public static void main(String[] args) {
    // Creating two linked lists for testing
    LinkedList headOne = new LinkedList(2);
    headOne.next = new LinkedList(6);
    headOne.next.next = new LinkedList(7);
    headOne.next.next.next = new LinkedList(8);

    LinkedList headTwo = new LinkedList(1);
    headTwo.next = new LinkedList(3);
    headTwo.next.next = new LinkedList(4);
    headTwo.next.next.next = new LinkedList(5);
    headTwo.next.next.next.next = new LinkedList(9);
    headTwo.next.next.next.next.next = new LinkedList(10);

    // Merge the two sorted lists
    LinkedList mergedHead = mergeLinkedLists(headOne, headTwo);

    // Print the merged linked list
    LinkedList current = mergedHead;
    System.out.print("Merged List: ");
    while (current != null) {
      System.out.print(current.value + " ");
      current = current.next;
    }
  }
}
