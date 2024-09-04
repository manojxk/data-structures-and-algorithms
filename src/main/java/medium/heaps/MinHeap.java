package medium.heaps;

public class MinHeap {
  private int[] heap;
  private int size;
  private int capacity;

  public MinHeap(int capacity) {
    this.capacity = capacity;
    this.size = 0;
    this.heap = new int[capacity];
  }

  private int parent(int index) {
    return (index - 1) / 2;
  }

  private int leftChild(int index) {
    return 2 * index + 1;
  }

  private int rightChild(int index) {
    return 2 * index + 2;
  }

  private void swap(int index1, int index2) {
    int temp = heap[index1];
    heap[index1] = heap[index2];
    heap[index2] = temp;
  }

  private void heapifyUp(int index) {
    while (index > 0 && heap[parent(index)] > heap[index]) {
      swap(index, parent(index));
      index = parent(index);
    }
  }

  private void heapifyDown(int index) {
    int smallest = index;
    int left = leftChild(index);
    int right = rightChild(index);

    if (left < size && heap[left] < heap[smallest]) {
      smallest = left;
    }

    if (right < size && heap[right] < heap[smallest]) {
      smallest = right;
    }

    if (smallest != index) {
      swap(index, smallest);
      heapifyDown(smallest);
    }
  }

  public void insert(int value) {
    if (size == capacity) {
      throw new IllegalStateException("Heap is full");
    }

    heap[size] = value;
    size++;
    heapifyUp(size - 1);
  }

  public int extractMin() {
    if (size == 0) {
      throw new IllegalStateException("Heap is empty");
    }

    int root = heap[0];
    heap[0] = heap[size - 1];
    size--;
    heapifyDown(0);

    return root;
  }

  public int getMin() {
    if (size == 0) {
      throw new IllegalStateException("Heap is empty");
    }

    return heap[0];
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public static void main(String[] args) {
    MinHeap minHeap = new MinHeap(10);

    minHeap.insert(3);
    minHeap.insert(2);
    minHeap.insert(15);
    minHeap.insert(5);
    minHeap.insert(4);
    minHeap.insert(45);

    System.out.println("Extracted Min: " + minHeap.extractMin()); // Output: 2
    System.out.println("Current Min: " + minHeap.getMin()); // Output: 3
    System.out.println("Heap Size: " + minHeap.size()); // Output: 5
  }
}
