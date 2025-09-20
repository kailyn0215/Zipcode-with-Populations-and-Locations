/**
 * File name: ExpandableArray.java
 * Author: Kailyn Brown
 * Date: 9/19/2025
 * Purpose: stores generic objects (Place and subclasses, or other types)
 *          auto resizes as elements are added
 */
public class ExpandableArray<T> {
    private Object[] data;
    private int size;

    public ExpandableArray() {
        this(16);
    }

    public ExpandableArray(int initialCapacity) {
        if (initialCapacity <= 0) initialCapacity = 16;
        data = new Object[initialCapacity];
        size = 0;
    }

    public int size() {
        return size;
    }

    /** Add to the end */
    public void add(T value) {
        ensureCapacity(size + 1);
        data[size++] = value;
    }

    /** Insert at the end (alias for add) */
    public void insert(T value) {
        add(value);
    }

    /** Insert at a specific index */
    public void insert(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
        ensureCapacity(size + 1);
        // shift elements right
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
    if (index >= 0 && index < this.size) {
        return (T) this.data[index]; // ✅ Cast Object → T
    } else {
        return null;
    }
}

    public void set(int index, T value) {
        if (index < 0) return;
        ensureCapacity(index + 1);
        data[index] = value;
        if (index >= size) size = index + 1;
    }

    /** Remove at index and return the element */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
        T removed = (T) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null;
        size--;
        return removed;
    }

    // checks size + creates new if necessary
    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= data.length) return;
        int newCap = data.length * 2;
        if (newCap < minCapacity) newCap = minCapacity;
        Object[] newData = new Object[newCap];
        for (int i = 0; i < size; i++) newData[i] = data[i];
        data = newData;
    }

    // searches thru looking for place w param zip
    public int indexOfZip(String zipcode) {
        if (zipcode == null) return -1;
        for (int i = 0; i < size; i++) {
            Object o = data[i];
            if (o instanceof Place) {
                Place p = (Place) o;
                if (zipcode.equals(p.getZip())) return i;
            }
        }
        return -1;
    }
}
