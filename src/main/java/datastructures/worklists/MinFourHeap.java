package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private Comparator<E> Comparator;

    public MinFourHeap(Comparator<E> c) {
        this.data = (E[]) new Object[10];
        this.size = 0;
        this.Comparator = c;

    }

    @Override
    public boolean hasWork() {
        return super.hasWork();

    }

    @Override
    public void add(E work) {
        if (size == data.length - 1) {
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
        E answer = data[0];
        size--;

        int hole = percDown(0, data[size]);
        data[hole] = data[size];

        return answer;
    }

    @Override
    public int size() {
        return this.size;

    }

    @Override
    public void clear() {
        this.data = (E[]) new Object[10];
        this.size = 0;

    }
    public int percUp(int hole, E value) {
        while (hole > 0 && Comparator.compare(value, data[(hole - 1)/4]) < 0) {
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
                if ((4 * hole) + i < size && Comparator.compare(data[target], data[4 * hole + i]) > 0) {
                    target = 4 * hole + i;

                }
            }

            // if the parent value is less than the child value
            if (Comparator.compare(data[target], value) < 0) {
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

