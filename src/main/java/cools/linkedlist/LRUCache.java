package cools.linkedlist;

import java.util.*;

/*
 Problem: LRU Cache

 Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

 Implement the LRUCache class:

 - LRUCache(int capacity): Initializes the LRU cache with positive size capacity.
 - int get(int key): Returns the value of the key if the key exists, otherwise returns -1.
 - void put(int key, int value): Updates the value of the key if the key exists. Otherwise, adds the key-value pair to the cache.
    If the number of keys exceeds the capacity from this operation, evict the least recently used key.

 The functions `get` and `put` must each run in O(1) time complexity.

 Solution Approach:
 1. Use a `HashMap` for O(1) access to cache elements based on their key.
 2. Use a doubly linked list to track the order of usage, so we can remove the least recently used (LRU) element in O(1) time.
 3. The head of the list will represent the most recently used (MRU) element, and the tail will represent the least recently used (LRU).
*/

public class LRUCache {
  private final int capacity; // The maximum capacity of the cache

  // Stores key-value pairs
  private final Map<Integer, Integer> cacheMap;

  // Stores keys in the order of access (most recent at the front)
  private final LinkedList<Integer> lruList;

  // Constructor to initialize the cache with a given capacity
  LRUCache(int capacity) {
    this.capacity = capacity;
    this.cacheMap = new HashMap<>();
    this.lruList = new LinkedList<>();
  }

  // Function to get the value for a given key
  public int get(int key) {
    if (!cacheMap.containsKey(key)) {
      return -1; // Key doesn't exist in the cache, return -1
    }

    // Move the accessed key to the front (most recently used position)
    lruList.remove(Integer.valueOf(key));
    lruList.addFirst(key); // Add the key to the front

    return cacheMap.get(key); // Return the value associated with the key
  }

  // Function to put a key-value pair into the cache
  public void put(int key, int value) {
    if (cacheMap.containsKey(key)) {
      lruList.remove(Integer.valueOf(key));
    } else {
      // If the cache is at capacity, remove the least recently used item
      if (cacheMap.size() >= capacity) {
        int leastUsedKey = lruList.removeLast(); // Remove the least recently used key
        cacheMap.remove(leastUsedKey); // Remove it from the map as well
      }
    }
    cacheMap.put(key, value);
    lruList.addFirst(key); // Add the key to the front (most recently used)
  }

  public static void main(String[] args) {
    LRUCache cache = new LRUCache(2);

    // Test case 1: Adding and retrieving values
    cache.put(1, 1); // Cache: {1=1}
    cache.put(2, 2); // Cache: {1=1, 2=2}
    System.out.println(cache.get(1)); // Output: 1 (key 1 is in the cache)

    // Test case 2: Exceeding capacity
    cache.put(3, 3); // LRU key was 2, cache: {1=1, 3=3}
    System.out.println(cache.get(2)); // Output: -1 (key 2 was evicted)

    // Test case 3: Evicting least recently used (LRU) key
    cache.put(4, 4); // LRU key was 1, cache: {3=3, 4=4}
    System.out.println(cache.get(1)); // Output: -1 (key 1 was evicted)
    System.out.println(cache.get(3)); // Output: 3 (key 3 is in the cache)
    System.out.println(cache.get(4)); // Output: 4 (key 4 is in the cache)
  }

  /*
   Time Complexity:
   - get(key): O(1) on average. HashMap provides O(1) time complexity for accessing elements. Removing and adding to the linked list is O(1).
   - put(key, value): O(1) on average. HashMap operations (put and remove) are O(1), and so are linked list operations (addFirst and removeLast).

   Space Complexity:
   - O(n), where n is the capacity of the cache. We use space for the HashMap to store key-value pairs and the LinkedList to track access order.
  */
}
