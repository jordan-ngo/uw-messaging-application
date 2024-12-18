package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
    private int size;
    private int head;
    private int tail;
    private E[] array;
    private int capacity;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.capacity = capacity;
        this.array = (E[]) new Comparable[capacity];
        this.size = 0;
        this.head = 0;
        this.tail = 0;

    }

    @Override
    public void add(E work) {
        if (isFull()) {
            throw new IllegalArgumentException();

        }
        array[tail] = work;
        tail = (tail + 1) % capacity;
        size++;

    }

    @Override
    public E peek() {
        if (hasWork()) {
            return array[head];

        } else {
            throw new NoSuchElementException();

        }
    }

    @Override
    public E peek(int i) {
        if (hasWork()) {
            if (i < 0 || i >= size()) {
                throw new IndexOutOfBoundsException();

            }
            return array[(head + i) % capacity];
        } else {
            throw new NoSuchElementException();

        }
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();

        }
        E temp = array[head];
        head = (head + 1) % capacity;
        size--;
        return temp;

    }

    @Override
    public void update(int i, E value) {
        if (!hasWork()) {
            throw new NoSuchElementException();

        }
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();

        }
        array[(head + i) % capacity] = value;

    }

    @Override
    public int size() {
        return this.size;

    }

    @Override
    public void clear() {
        this.tail = head;
        this.size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        if (this.equals(other) || (this.size == 0 && other.size() == 0)) {
        return 0;

        } else if
        (this.size() > other.size()) {
                for (int i = 0; i < other.size(); i++) {
                    if (this.peek(i).hashCode() < other.peek(i).hashCode()) {
                        return -1;

                    } else if
                    (this.peek(i).hashCode() > other.peek(i).hashCode()) {
                        return 1;

                    }
                }
                    return 1;

            } else {
            for (int i = 0; i < this.size(); i++) {
                if (this.peek(i).hashCode() < other.peek(i).hashCode()) {
                    return -1;

                } else if
                (this.peek(i).hashCode() > other.peek(i).hashCode()) {
                    return 1;

                }
            }
            if (this.size() == other.size()) {
                return 0;
            }
            return -1;

        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;

        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;

        } else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here
            if (this.size() != other.size()) {
                return false;

            }
            for (int i = 0; i < this.size; i++) {
                if (this.peek(i) != other.peek(i)) {
                    return false;

                }
            }
            return true;

        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int res = 1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                res = 31 * res + 0;

            } else {
                res = 31 * res + array[i].hashCode();

            }
        }
        return res;
    }
}
