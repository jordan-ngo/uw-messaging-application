package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;

    public MinFourHeapComparable() {
        this.data = (E[]) new Comparable[10]; // represents the heap/array
        this.size = 0; // size of the heap
    }

    public MinFourHeapComparable(int capacity) {
        this.data = (E[])new Comparable[capacity];
        this.size = 0;
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) { //data.length = size of array
        if (this.size == data.length - 1) {
            resize();
        }
        if (size == 0) {
            data[0] = work;

        } else {
            int i = percUp(this.size, work);
            data[i] = work;

            }
        size++;

        }


    @Override
    public E peek() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();

        }
        return this.data[0];

    }

    @Override
    public E next() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        E ans = data[0];
        size--;
        int hole = percDown(0, data[size]);
        data[hole] = data[size];
        return ans;

    }

    @Override
    public int size() {
        return this.size;

    }

    @Override
    public void clear() {
        this.data = (E[])new Comparable[10];
        size = 0;

    }

    public int percUp(int hole, E value) {
        while (hole > 0 && value.compareTo(data[(hole - 1)/4]) < 0) {
            data[hole] = data[(hole - 1)/4];
            hole = (hole - 1)/4;

        }
        return hole;

    }
    public int percDown(int hole, E value) {
        while (4 * hole <= size) {
            int target = 4 * hole + 1;
            if (target > size) {
                break;
            }

            // loop used to find the child that is the smallest
            for (int i = 1; i <= 4; i++) {
                if ((4 * hole) + i < size && data[target].compareTo(data[4 * hole + i]) > 0) {
                    target = 4 * hole + i;

                }
            }

            // if the parent value is less than the child value
            if (data[target].compareTo(value) < 0) {
                data[hole] = data[target];
                hole = target;
            } else {
                break;

            }
        }
        return hole;

    } // used to double the capacity of the array
    public void resize() {
        E[] temp = (E[]) new Comparable[this.size * 2];
        for (int i = 0; i < this.size; i++) {
            temp[i] = data[i];

        }
        data = temp;

    }

}
