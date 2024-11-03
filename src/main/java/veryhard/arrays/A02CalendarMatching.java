package veryhard.arrays;

/*
 * Problem Statement:
 *
 * You are given two people's daily calendars and their available bounds. Each person's calendar is a list of
 * non-overlapping meetings (each meeting is represented by a tuple of start time and end time).
 * Both calendars are in the same day and the times are given in a 24-hour format (e.g., "09:00", "18:00").
 *
 * You are also given two bounds, which represent the earliest and latest times during which the individuals are available.
 * Write a function that returns a list of available time slots where both people can attend a meeting.
 *
 * Example:
 *
 * Input:
 * calendar1 = [["09:00", "10:30"], ["12:00", "13:00"], ["16:00", "18:00"]],
 * dailyBounds1 = ["09:00", "20:00"]
 *
 * calendar2 = [["10:00", "11:30"], ["12:30", "14:30"], ["14:30", "15:00"], ["16:00", "17:00"]],
 * dailyBounds2 = ["10:00", "18:30"]
 *
 * meetingDuration = 30
 *
 * Output:
 * [["11:30", "12:00"], ["15:00", "16:00"], ["18:00", "18:30"]]
 *
 * Explanation:
 * The available time slots for both individuals that are at least 30 minutes long are:
 * ["11:30", "12:00"], ["15:00", "16:00"], and ["18:00", "18:30"].
 */

/*
 * Solution Approach:
 *
 * 1. Convert the calendars and daily bounds to minutes from "00:00" for easier comparison.
 * 2. Merge the two calendars into one and then flatten overlapping or adjacent meetings.
 * 3. Find the gaps between the flattened meetings where a meeting of the required duration can be scheduled.
 * 4. Return the available time slots as a list of time intervals in the "HH:MM" format.
 */

import java.util.*;

public class A02CalendarMatching {

  // Helper function to convert time from "HH:MM" to minutes
  public static int timeToMinutes(String time) {
    String[] timeParts = time.split(":");
    return Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);
  }

  // Helper function to convert time from minutes to "HH:MM" format
  public static String minutesToTime(int minutes) {
    int hours = minutes / 60;
    int mins = minutes % 60;
    return String.format("%02d:%02d", hours, mins);
  }

  // Helper function to merge two calendars and flatten overlapping intervals
  public static List<int[]> mergeCalendars(List<int[]> calendar1, List<int[]> calendar2) {
    // Step 1: Sort intervals by their start time
    List<int[]> intervals = new ArrayList<>(calendar1);
    intervals.addAll(calendar2);
    intervals.sort(Comparator.comparingInt(a -> a[0]));

    List<int[]> ans = new ArrayList<>();

    int n = intervals.size();
    if (n <= 1) {
      // If there is 1 or no intervals, return as is
      return intervals;
    }

    // Step 2: Initialize the answer list with the first interval
    ans.add(intervals.get(0));

    // Step 3: Traverse through the sorted intervals and merge them
    for (int i = 1; i < n; i++) {
      // If the current interval overlaps with the last interval in the answer list
      if (ans.get(ans.size() - 1)[1] >= intervals.get(i)[0]) {
        // Merge the intervals by updating the end time
        ans.get(ans.size() - 1)[1] = Math.max(ans.get(ans.size() - 1)[1], intervals.get(i)[1]);
      } else {
        // If no overlap, add the current interval to the answer list
        ans.add(intervals.get(i));
      }
    }

    return ans;
  }

  // Function to find the available time slots where a meeting can be scheduled
  public static List<String[]> findAvailableSlots(
      List<int[]> mergedCalendar, int start, int end, int meetingDuration) {
    List<String[]> availableSlots = new ArrayList<>();

    // Check the gap between start of the day and the first meeting
    if (start < mergedCalendar.get(0)[0]) {
      addAvailableSlot(availableSlots, start, mergedCalendar.get(0)[0], meetingDuration);
    }

    // Check gaps between meetings
    for (int i = 1; i < mergedCalendar.size(); i++) {
      int endCurrentMeeting = mergedCalendar.get(i - 1)[1];
      int startNextMeeting = mergedCalendar.get(i)[0];
      addAvailableSlot(availableSlots, endCurrentMeeting, startNextMeeting, meetingDuration);
    }

    // Check the gap between the last meeting and the end of the day
    if (mergedCalendar.get(mergedCalendar.size() - 1)[1] < end) {
      addAvailableSlot(
          availableSlots, mergedCalendar.get(mergedCalendar.size() - 1)[1], end, meetingDuration);
    }

    return availableSlots;
  }

  // Helper function to add a valid available slot
  private static void addAvailableSlot(
      List<String[]> availableSlots, int start, int end, int meetingDuration) {
    if (end - start >= meetingDuration) {
      availableSlots.add(new String[] {minutesToTime(start), minutesToTime(end)});
    }
  }

  // Main function to match calendars and find available meeting slots
  public static List<String[]> calendarMatching(
      List<String[]> calendar1,
      String[] dailyBounds1,
      List<String[]> calendar2,
      String[] dailyBounds2,
      int meetingDuration) {

    // Convert daily bounds to minutes
    int startBound1 = timeToMinutes(dailyBounds1[0]);
    int endBound1 = timeToMinutes(dailyBounds1[1]);
    int startBound2 = timeToMinutes(dailyBounds2[0]);
    int endBound2 = timeToMinutes(dailyBounds2[1]);

    // Merge daily bounds for both people
    int start = Math.max(startBound1, startBound2);
    int end = Math.min(endBound1, endBound2);

    // Convert calendars to minutes and flatten overlapping meetings
    List<int[]> calendar1InMinutes = convertCalendarToMinutes(calendar1);
    List<int[]> calendar2InMinutes = convertCalendarToMinutes(calendar2);
    List<int[]> mergedCalendar = mergeCalendars(calendar1InMinutes, calendar2InMinutes);

    // Find available slots between meetings
    return findAvailableSlots(mergedCalendar, start, end, meetingDuration);
  }

  // Helper function to convert the calendar times from "HH:MM" to minutes
  public static List<int[]> convertCalendarToMinutes(List<String[]> calendar) {
    List<int[]> calendarInMinutes = new ArrayList<>();
    for (String[] meeting : calendar) {
      calendarInMinutes.add(new int[] {timeToMinutes(meeting[0]), timeToMinutes(meeting[1])});
    }
    return calendarInMinutes;
  }

  // Main function to test the Calendar Matching implementation
  public static void main(String[] args) {
    List<String[]> calendar1 =
        Arrays.asList(
            new String[] {"09:00", "10:30"},
            new String[] {"12:00", "13:00"},
            new String[] {"16:00", "18:00"});
    String[] dailyBounds1 = {"09:00", "20:00"};

    List<String[]> calendar2 =
        Arrays.asList(
            new String[] {"10:00", "11:30"},
            new String[] {"12:30", "14:30"},
            new String[] {"14:30", "15:00"},
            new String[] {"16:00", "17:00"});
    String[] dailyBounds2 = {"10:00", "18:30"};

    int meetingDuration = 30;

    // Output: [["11:30", "12:00"], ["15:00", "16:00"], ["18:00", "18:30"]]
    List<String[]> result =
        calendarMatching(calendar1, dailyBounds1, calendar2, dailyBounds2, meetingDuration);
    System.out.println("Available slots: ");
    for (String[] slot : result) {
      System.out.println(Arrays.toString(slot));
    }
  }

  /*
   * Time Complexity:
   * O(n + m), where n is the number of meetings in calendar1 and m is the number of meetings in calendar2.
   * This is because we merge and sort both calendars and then iterate over the merged calendar to find available slots.
   *
   * Space Complexity:
   * O(n + m), where n is the number of meetings in calendar1 and m is the number of meetings in calendar2.
   * This is for storing the converted calendars and the merged calendar.
   */
}
