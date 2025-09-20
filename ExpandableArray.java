/**
 * File name: ExpandableArray.java
 * Author: Kailyn Brown
 * Date: 9/19/2025
 * Purpose: stores Place objects (and subclasses)
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

    public void add(T value) {
        ensureCapacity(size + 1);
        data[size++] = value;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T val = (T) data[index];
        return val;
    }

    public void set(int index, T value) {
        if (index < 0) return;
        ensureCapacity(index + 1);
        data[index] = value;
        if (index >= size) size = index + 1;
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
                if (zipcode.equals(p.getZipcode())) return i;
            }
        }
        return -1;
    }
}
