import java.lang.IndexOutOfBoundsException;


public class ExpandableArray<E> {

    private E[] data;
    private int size;
    private int capacity;

    // Constructor with custom capacity
    public ExpandableArray(int capacity) {

        @SuppressWarnings("unchecked")
        E[] temp = (E[]) new Object[capacity];
        data = temp;
        this.capacity = capacity;
        this.size = 0;
    }

    // Default constructor with capacity 10
    public ExpandableArray() {
        this(10);
    }

    // Resize method to expand the internal array when full
    private void resize() {
        capacity *= 2;
        @SuppressWarnings("unchecked")
        E[] newData = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    // Insert element at the beginning
    public void insert(E elem) {
        if (size >= capacity) {
            resize();
        }
        for (int n = size; n > 0; n--) {

            data[n] = data[n - 1];

        }
        data[0] = elem;
        size++;
    }

    // Insert element at a specific location
    public void insert(E elem, int location) throws IndexOutOfBoundsException {
        if (location < 0 || location > size) {
            throw new IndexOutOfBoundsException("Invalid insert location: " + location);
        }

        if (size >= capacity) {
            resize();
        }

        for (int n = size; n > location; n--) {
            data[n] = data[n - 1];
        }

        data[location] = elem;
        size++;
    }

    // Set element at a specific index (replaces current value)
    public void set(E elem, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid set index: " + index);
        }
        this.data[index] = elem;
    }

    // Get element at a specific index
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid get index: " + index);
        }
        return this.data[index];
    }

    // Remove element at a specific index
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        E removedElement = data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        data[size - 1] = null; // Clear the last element's reference
        size--;
        return removedElement;
    }

    // Return string 
    public String toString() {
        if (size == 0) {
            return "";  // Return empty string instead of "[]"
        }

        StringBuilder s = new StringBuilder();

        for (int i = 0; i < size; i++) {
            s.append(data[i]);
            if (i < size - 1) {
                s.append(", ");
            }
        }
        return s.toString();
    }

    // Return current number of elements
    public int size() {
        return size;
    }

    // return current capacity (for debugging)
    public int capacity() {
        return capacity;
    }
    
}
