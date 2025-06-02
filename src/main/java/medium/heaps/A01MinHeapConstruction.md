**Problem Restatement**
Implement a **Min‐Heap** data structure supporting:

1. **Insert** a new integer (maintaining the heap property).
2. **Remove the minimum** (the root) and re‐heapify.

In a Min‐Heap:

* The heap is a complete binary tree (all levels filled except possibly the last, filled from left to right).
* Every node’s value is ≤ its children’s values. Thus, the root is always the minimum element.

---

## Key Operations and Their Implementation

We store the heap in a dynamic array (`ArrayList<Integer> heap`) so that for any index `i`:

* **Parent** index = `(i – 1) / 2`
* **Left child** index = `2*i + 1`
* **Right child** index = `2*i + 2`

### 1. Inserting a Value (`insert(int value)`)

1. **Append** the new value to the end of `heap`. This preserves the “complete tree” shape.
2. **Bubble Up** (also called “heapify up”): Compare the newly inserted node with its parent.

   * While the new node’s value is **less** than its parent’s value, **swap** them.
   * Update its index to the parent’s index, and repeat until either you reach the root or the parent is ≤ the new node.

Because each swap moves the node one level up, and the height of a complete binary tree with $n$ elements is $\lfloor \log_2 n \rfloor$, the **time complexity** of `insert` is $O(\log n)$.

```java
public void insert(int value) {
  // 1) Add at the end
  heap.add(value);
  // 2) Bubble it up to its correct position
  bubbleUp(heap.size() - 1);
}

// Recursively (or iteratively) swap until heap property holds
private void bubbleUp(int i) {
  int parent = (i - 1) / 2;
  while (i > 0 && heap.get(i) < heap.get(parent)) {
    swap(i, parent);
    i = parent;
    parent = (i - 1) / 2;
  }
}
```

### 2. Removing the Minimum (`removeMin()`)

1. **Root = Minimum**: Save `heap.get(0)` as `min`.
2. **Replace the Root** with the last element in the array. Then **remove** that last element (to keep the tree complete).
3. **Sift Down** (also called “heapify down”): Starting from index `0`, compare the current node with its two children.

   * If either child is smaller, swap with the smaller child.
   * Continue moving down until both children (if they exist) are ≥ the current node, or you reach a leaf.

Again, each swap moves one level down, so `removeMin` is also $O(\log n)$.

```java
public int removeMin() {
  if (heap.isEmpty()) {
    throw new IllegalStateException("Heap is empty");
  }
  int min = heap.get(0);                    // 1) Root
  int lastValue = heap.remove(heap.size() - 1); // 2) Remove last

  if (!heap.isEmpty()) {
    heap.set(0, lastValue); // Move last value to root
    siftDown(0);            // 3) Restore min-heap property
  }

  return min;
}

private void siftDown(int i) {
  int left  = 2 * i + 1;
  int right = 2 * i + 2;
  int smallest = i;

  // Compare with left child
  if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
    smallest = left;
  }
  // Compare with right child
  if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
    smallest = right;
  }

  // If a child is smaller, swap and continue down
  if (smallest != i) {
    swap(i, smallest);
    siftDown(smallest);
  }
}
```

### 3. Swapping Helper

Simple method to swap two elements in the `ArrayList`:

```java
private void swap(int i, int j) {
  int temp = heap.get(i);
  heap.set(i, heap.get(j));
  heap.set(j, temp);
}
```

---

## Putting It All Together

```java
import java.util.ArrayList;

public class MinHeap {
  private ArrayList<Integer> heap;

  public MinHeap() {
    heap = new ArrayList<>();
  }

  // INSERT
  public void insert(int value) {
    heap.add(value);
    bubbleUp(heap.size() - 1);
  }

  private void bubbleUp(int i) {
    int parent = (i - 1) / 2;
    while (i > 0 && heap.get(i) < heap.get(parent)) {
      swap(i, parent);
      i = parent;
      parent = (i - 1) / 2;
    }
  }

  // REMOVE MIN (pop root)
  public int removeMin() {
    if (heap.isEmpty()) {
      throw new IllegalStateException("Heap is empty");
    }
    int min = heap.get(0);
    int lastValue = heap.remove(heap.size() - 1);
    if (!heap.isEmpty()) {
      heap.set(0, lastValue);
      siftDown(0);
    }
    return min;
  }

  private void siftDown(int i) {
    int left  = 2 * i + 1;
    int right = 2 * i + 2;
    int smallest = i;

    if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
      smallest = left;
    }
    if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
      smallest = right;
    }
    if (smallest != i) {
      swap(i, smallest);
      siftDown(smallest);
    }
  }

  // SWAP HELPER
  private void swap(int i, int j) {
    int temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }

  // DISPLAY FOR DEBUGGING
  public void displayHeap() {
    System.out.println(heap);
  }

  // MAIN FOR DEMONSTRATION
  public static void main(String[] args) {
    MinHeap minHeap = new MinHeap();

    // Insert a series of numbers
    minHeap.insert(3);
    minHeap.insert(1);
    minHeap.insert(6);
    minHeap.insert(5);
    minHeap.insert(2);
    minHeap.insert(4);

    // After inserts, the internal array (heap) should look like [1, 2, 4, 5, 3, 6]
    System.out.println("Heap after inserts:");
    minHeap.displayHeap();

    // Remove the minimum (1), then the heap reorders
    System.out.println("Removed min element: " + minHeap.removeMin()); // 1
    minHeap.displayHeap(); // [2, 3, 4, 5, 6]

    System.out.println("Removed min element: " + minHeap.removeMin()); // 2
    minHeap.displayHeap(); // [3, 5, 4, 6]
  }
}
```

---

## Complexity Analysis

* **Insert**: $O(\log n)$ because `bubbleUp` moves up at most the height of the tree.
* **Remove Min**: $O(\log n)$ because `siftDown` moves down at most the height of the tree.
* **Space**: $O(n)$ to store the heap elements in the dynamic array.

This completes a fully functional Min‐Heap implementation with explanatory comments. You can now insert arbitrary integers and repeatedly remove the minimum, always preserving the heap property in $O(\log n)$ time per operation.
