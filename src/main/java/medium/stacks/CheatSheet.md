# Java Collections Framework DSA Cheat Sheet

## List Interface (ArrayList, LinkedList, Vector)

| Operation | ArrayList | LinkedList | Time Complexity | Comments |
|-----------|-----------|------------|-----------------|----------|
| **Creation** | `List<Integer> list = new ArrayList<>();` | `List<Integer> list = new LinkedList<>();` | O(1) | ArrayList is most commonly used |
| **Add Element** | `list.add(element)` | `list.add(element)` | O(1) amortized / O(1) | Appends to end |
| **Add at Index** | `list.add(index, element)` | `list.add(index, element)` | O(n) / O(n) | Shifts elements in ArrayList |
| **Get Element** | `list.get(index)` | `list.get(index)` | O(1) / O(n) | Random access vs sequential |
| **Set Element** | `list.set(index, element)` | `list.set(index, element)` | O(1) / O(n) | Updates element at index |
| **Remove by Index** | `list.remove(index)` | `list.remove(index)` | O(n) / O(n) | Returns removed element |
| **Remove by Object** | `list.remove(object)` | `list.remove(object)` | O(n) / O(n) | Removes first occurrence |
| **Size** | `list.size()` | `list.size()` | O(1) / O(1) | Number of elements |
| **Is Empty** | `list.isEmpty()` | `list.isEmpty()` | O(1) / O(1) | Returns boolean |
| **Contains** | `list.contains(object)` | `list.contains(object)` | O(n) / O(n) | Linear search |
| **Clear** | `list.clear()` | `list.clear()` | O(n) / O(1) | Removes all elements |
| **First Element** | `list.get(0)` | `list.getFirst()` | O(1) / O(1) | LinkedList has dedicated method |
| **Last Element** | `list.get(list.size()-1)` | `list.getLast()` | O(1) / O(1) | LinkedList has dedicated method |

## Stack (Using ArrayDeque or Stack Class)

| Operation | Code | Time Complexity | Comments |
|-----------|------|-----------------|----------|
| **Creation** | `Deque<Integer> stack = new ArrayDeque<>();` | O(1) | ArrayDeque preferred over Stack class |
| **Push** | `stack.push(element)` | O(1) | Adds to top |
| **Pop** | `stack.pop()` | O(1) | Removes and returns top element |
| **Peek/Top** | `stack.peek()` | O(1) | Returns top without removing |
| **Is Empty** | `stack.isEmpty()` | O(1) | Check if stack is empty |
| **Size** | `stack.size()` | O(1) | Number of elements |

## Queue (Using ArrayDeque or LinkedList)

| Operation | Code | Time Complexity | Comments |
|-----------|------|-----------------|----------|
| **Creation** | `Queue<Integer> queue = new ArrayDeque<>();` | O(1) | ArrayDeque is most efficient |
| **Enqueue (Add)** | `queue.offer(element)` or `queue.add(element)` | O(1) | Add to rear |
| **Dequeue (Remove)** | `queue.poll()` or `queue.remove()` | O(1) | Remove from front, poll() returns null if empty |
| **Front Element** | `queue.peek()` or `queue.element()` | O(1) | peek() returns null, element() throws exception |
| **Is Empty** | `queue.isEmpty()` | O(1) | Check if queue is empty |
| **Size** | `queue.size()` | O(1) | Number of elements |

## Deque (Double-ended Queue)

| Operation | Code | Time Complexity | Comments |
|-----------|------|-----------------|----------|
| **Creation** | `Deque<Integer> deque = new ArrayDeque<>();` | O(1) | Can act as stack or queue |
| **Add First** | `deque.addFirst(element)` or `deque.offerFirst(element)` | O(1) | Add to front |
| **Add Last** | `deque.addLast(element)` or `deque.offerLast(element)` | O(1) | Add to rear |
| **Remove First** | `deque.removeFirst()` or `deque.pollFirst()` | O(1) | Remove from front |
| **Remove Last** | `deque.removeLast()` or `deque.pollLast()` | O(1) | Remove from rear |
| **Peek First** | `deque.peekFirst()` or `deque.getFirst()` | O(1) | View front element |
| **Peek Last** | `deque.peekLast()` or `deque.getLast()` | O(1) | View rear element |

## Set Interface (HashSet, LinkedHashSet, TreeSet)

| Operation | HashSet | LinkedHashSet | TreeSet | Time Complexity | Comments |
|-----------|---------|---------------|---------|-----------------|----------|
| **Creation** | `Set<Integer> set = new HashSet<>();` | `Set<Integer> set = new LinkedHashSet<>();` | `Set<Integer> set = new TreeSet<>();` | O(1) | TreeSet maintains sorted order |
| **Add** | `set.add(element)` | `set.add(element)` | `set.add(element)` | O(1) / O(1) / O(log n) | Returns false if already exists |
| **Remove** | `set.remove(element)` | `set.remove(element)` | `set.remove(element)` | O(1) / O(1) / O(log n) | Returns boolean |
| **Contains** | `set.contains(element)` | `set.contains(element)` | `set.contains(element)` | O(1) / O(1) / O(log n) | Check membership |
| **Size** | `set.size()` | `set.size()` | `set.size()` | O(1) | Number of elements |
| **Is Empty** | `set.isEmpty()` | `set.isEmpty()` | `set.isEmpty()` | O(1) | Check if empty |
| **Clear** | `set.clear()` | `set.clear()` | `set.clear()` | O(n) | Remove all elements |
| **First** | N/A | N/A | `set.first()` | N/A / N/A / O(log n) | TreeSet only - smallest element |
| **Last** | N/A | N/A | `set.last()` | N/A / N/A / O(log n) | TreeSet only - largest element |

## Map Interface (HashMap, LinkedHashMap, TreeMap)

| Operation | HashMap | TreeMap | Time Complexity | Comments |
|-----------|---------|---------|-----------------|----------|
| **Creation** | `Map<String, Integer> map = new HashMap<>();` | `Map<String, Integer> map = new TreeMap<>();` | O(1) | TreeMap keeps keys sorted |
| **Put** | `map.put(key, value)` | `map.put(key, value)` | O(1) / O(log n) | Returns previous value or null |
| **Get** | `map.get(key)` | `map.get(key)` | O(1) / O(log n) | Returns value or null |
| **Remove** | `map.remove(key)` | `map.remove(key)` | O(1) / O(log n) | Returns removed value |
| **Contains Key** | `map.containsKey(key)` | `map.containsKey(key)` | O(1) / O(log n) | Check if key exists |
| **Contains Value** | `map.containsValue(value)` | `map.containsValue(value)` | O(n) / O(n) | Linear search for value |
| **Size** | `map.size()` | `map.size()` | O(1) / O(1) | Number of key-value pairs |
| **Is Empty** | `map.isEmpty()` | `map.isEmpty()` | O(1) / O(1) | Check if empty |
| **Key Set** | `map.keySet()` | `map.keySet()` | O(1) / O(1) | Returns Set view of keys |
| **Values** | `map.values()` | `map.values()` | O(1) / O(1) | Returns Collection view of values |
| **Entry Set** | `map.entrySet()` | `map.entrySet()` | O(1) / O(1) | Returns Set view of entries |

## Priority Queue (Min Heap by default)

| Operation | Code | Time Complexity | Comments |
|-----------|------|-----------------|----------|
| **Creation (Min Heap)** | `PriorityQueue<Integer> pq = new PriorityQueue<>();` | O(1) | Natural ordering (ascending) |
| **Creation (Max Heap)** | `PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());` | O(1) | Reverse ordering (descending) |
| **Add/Offer** | `pq.offer(element)` or `pq.add(element)` | O(log n) | Insert element |
| **Poll/Remove** | `pq.poll()` or `pq.remove()` | O(log n) | Remove and return min/max element |
| **Peek** | `pq.peek()` or `pq.element()` | O(1) | View min/max element without removing |
| **Size** | `pq.size()` | O(1) | Number of elements |
| **Is Empty** | `pq.isEmpty()` | O(1) | Check if empty |
| **Contains** | `pq.contains(element)` | O(n) | Linear search |
| **Clear** | `pq.clear()` | O(n) | Remove all elements |

## Common Iteration Patterns

| Collection Type | For-Each Loop | Iterator | Index-based (if applicable) |
|-----------------|---------------|----------|----------------------------|
| **List** | `for(int x : list)` | `Iterator<Integer> it = list.iterator(); while(it.hasNext())` | `for(int i=0; i<list.size(); i++)` |
| **Set** | `for(int x : set)` | `Iterator<Integer> it = set.iterator(); while(it.hasNext())` | N/A |
| **Map Keys** | `for(String key : map.keySet())` | `Iterator<String> it = map.keySet().iterator()` | N/A |
| **Map Entries** | `for(Map.Entry<String,Integer> entry : map.entrySet())` | `Iterator<Map.Entry<String,Integer>> it = map.entrySet().iterator()` | N/A |

## Utility Methods (Collections Class)

| Operation | Code | Time Complexity | Comments |
|-----------|------|-----------------|----------|
| **Sort List** | `Collections.sort(list)` | O(n log n) | Natural ordering |
| **Sort with Comparator** | `Collections.sort(list, comparator)` | O(n log n) | Custom ordering |
| **Reverse** | `Collections.reverse(list)` | O(n) | Reverse the list |
| **Shuffle** | `Collections.shuffle(list)` | O(n) | Random permutation |
| **Binary Search** | `Collections.binarySearch(list, key)` | O(log n) | List must be sorted |
| **Min Element** | `Collections.min(collection)` | O(n) | Find minimum |
| **Max Element** | `Collections.max(collection)` | O(n) | Find maximum |
| **Frequency** | `Collections.frequency(collection, object)` | O(n) | Count occurrences |

## Arrays Utility Methods

| Operation | Code | Time Complexity | Comments |
|-----------|------|-----------------|----------|
| **Sort Array** | `Arrays.sort(array)` | O(n log n) | In-place sorting |
| **Binary Search** | `Arrays.binarySearch(array, key)` | O(log n) | Array must be sorted |
| **Fill Array** | `Arrays.fill(array, value)` | O(n) | Fill with specific value |
| **Copy Array** | `Arrays.copyOf(array, newLength)` | O(n) | Create copy with new length |
| **Array to List** | `Arrays.asList(array)` | O(1) | Fixed-size list backed by array |
| **Compare Arrays** | `Arrays.equals(array1, array2)` | O(n) | Element-wise comparison |
| **Array to String** | `Arrays.toString(array)` | O(n) | For debugging |

## Common DSA Use Cases

| Data Structure | Best Use Cases |
|----------------|----------------|
| **ArrayList** | Random access, iteration, when size is relatively stable |
| **LinkedList** | Frequent insertion/deletion at beginning/middle, implementing stack/queue |
| **ArrayDeque** | Stack and queue operations, better than Stack class |
| **HashSet** | Fast membership testing, removing duplicates |
| **TreeSet** | Maintaining sorted unique elements, range queries |
| **HashMap** | Fast key-value lookups, counting frequency |
| **TreeMap** | Sorted key-value pairs, range queries on keys |
| **PriorityQueue** | Heap operations, finding min/max efficiently, graph algorithms |

## Important Notes for DSA

1. **ArrayList vs LinkedList**: Use ArrayList for random access, LinkedList for frequent insertions/deletions
2. **HashSet vs TreeSet**: HashSet for O(1) operations, TreeSet for sorted order
3. **HashMap vs TreeMap**: HashMap for O(1) operations, TreeMap for sorted keys
4. **ArrayDeque**: Preferred over Stack class and LinkedList for stack/queue operations
5. **PriorityQueue**: Implements min-heap by default, use `Collections.reverseOrder()` for max-heap
6. **Null handling**: HashMap allows one null key, HashSet allows one null value, TreeMap/TreeSet don't allow nulls


Here’s a concise cheat-sheet covering the Java Collection Framework’s most common operations—organized by interface, with code snippets and inline comments for quick DSA reference.

#### Common `Collection<E>` Methods

| Method                       | Description           | Code Example                                         |
| ---------------------------- | --------------------- | ---------------------------------------------------- |
| `int size()`                 | Number of elements    | `int n = col.size();         // get element count`   |
| `boolean isEmpty()`          | True if no elements   | `if (col.isEmpty()) …       // no elements present`  |
| `boolean contains(Object o)` | Tests for presence    | `if (col.contains(x)) …     // contains x?`          |
| `void clear()`               | Remove *all* elements | `col.clear();               // empty the collection` |
| `Iterator<E> iterator()`     | Sequential access     | `Iterator<E> it = col.iterator();`                   |

---

#### `List<E>` (e.g. `ArrayList`, `LinkedList`)

| Method                               | Description                      | Code Example                                       |
| ------------------------------------ | -------------------------------- | -------------------------------------------------- |
| `boolean add(E e)`                   | Append to end                    | `list.add(42);           // add 42 at end`         |
| `E get(int index)`                   | Retrieve by index                | `int x = list.get(0);    // first element`         |
| `E remove(int index)`                | Remove by index                  | `list.remove(0);         // drop first element`    |
| `boolean remove(Object o)`           | Remove first occurrence of value | `list.remove((Integer)42); // remove value 42`     |
| `void add(int idx, E e)`             | Insert at position               | `list.add(1, 99);        // insert 99 at idx 1`    |
| `List<E> subList(int from, int to)`  | View a slice `[from, to)`        | `List<E> slice = list.subList(2,5); // idx 2–4`    |
| `void sort(Comparator<? super E> c)` | Sort with comparator             | `Collections.sort(list); // natural order`         |
| `int indexOf(Object o)`              | First index of value             | `list.indexOf(99);       // where 99 sits (or –1)` |

---

#### `Set<E>` (e.g. `HashSet`, `TreeSet`)

| Method                            | Description          | Code Example                                 |
| --------------------------------- | -------------------- | -------------------------------------------- |
| `boolean add(E e)`                | Insert if absent     | `set.add(7);            // add 7 (no dupes)` |
| `boolean remove(Object o)`        | Remove element       | `set.remove(7);         // drop 7`           |
| `boolean contains(Object o)`      | Membership test      | `set.contains(7);       // true/false`       |
| `void clear()`                    | Remove all           | `set.clear();           // empty the set`    |
| `SortedSet<E> subSet(E lo, E hi)` | (TreeSet) view range | `tree.subSet(5,10);     // [5,10)`           |

---

#### `Map<K,V>` (e.g. `HashMap`, `TreeMap`)

| Method                            | Description         | Code Example                                                 |
| --------------------------------- | ------------------- | ------------------------------------------------------------ |
| `V put(K key, V value)`           | Insert or update    | `map.put("a",1);         // map “a”→1`                       |
| `V get(Object key)`               | Retrieve by key     | `int v = map.get("a");   // returns 1 (or null)`             |
| `V remove(Object key)`            | Remove entry        | `map.remove("a");       // delete mapping for “a”`           |
| `boolean containsKey(Object key)` | Key presence        | `map.containsKey("a");  // true/false`                       |
| `Set<K> keySet()`                 | View of all keys    | `for (K k : map.keySet()) …`                                 |
| `Collection<V> values()`          | View of all values  | `for (V val : map.values()) …`                               |
| `Set<Map.Entry<K,V>> entrySet()`  | View of all entries | `for (var e : map.entrySet()) { e.getKey(); e.getValue(); }` |

---

#### `Queue<E>` (e.g. `LinkedList`, `ArrayDeque`)

| Method               | Description                            | Code Example                                       |
| -------------------- | -------------------------------------- | -------------------------------------------------- |
| `boolean offer(E e)` | Insert at tail                         | `queue.offer(5);        // enqueue 5`              |
| `E poll()`           | Remove & return head (or null)         | `int h = queue.poll();  // dequeue, null if empty` |
| `E peek()`           | Return head without removing (or null) | `queue.peek();         // view next element`       |
| `boolean isEmpty()`  | True if no elements                    | `queue.isEmpty();      // empty?`                  |

---

#### `Deque<E>` as Stack/Double-ended Queue (`ArrayDeque`)

| Method               | Description          | Code Example                                |
| -------------------- | -------------------- | ------------------------------------------- |
| `void push(E e)`     | Stack-push at front  | `stack.push(10);        // push onto stack` |
| `E pop()`            | Stack-pop from front | `int top = stack.pop(); // pop and return`  |
| `void addFirst(E e)` | Insert at front      | `deque.addFirst(1);     // head ← 1`        |
| `void addLast(E e)`  | Insert at tail       | `deque.addLast(3);      // tail → 3`        |
| `E removeFirst()`    | Remove front         | `deque.removeFirst();   // drop head`       |
| `E removeLast()`     | Remove tail          | `deque.removeLast();    // drop tail`       |

---

#### Iteration Patterns

```java
// 1) For-each loop
for (E e : collection) {
    // process e
}

// 2) Iterator
Iterator<E> it = collection.iterator();
while (it.hasNext()) {
    E e = it.next();  // advance and fetch
    // process e
}

// 3) Java 8+ forEach with lambda
collection.forEach(e -> {
    // process e
});
```

Use this sheet as your one-stop reference when implementing or analyzing algorithms with Java’s built-in data structures. Good luck in your DSA preparations!
