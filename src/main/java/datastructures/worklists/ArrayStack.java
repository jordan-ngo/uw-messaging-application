package datastructures.worklists;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private int Top;
    private E[] Array;

    public ArrayStack() {
        Array = (E[]) new Object[10];
        Top = -1;

    }

    //Adds work to the WorkList
    @Override
    public void add(E work) {
        if (Array.length == Top + 1) {
            E[] Copy = (E[]) new Object[Array.length * 2];

            for (int i = 0; i < Array.length; i++) {
                Copy[i] = Array[i];

            }
            Array = Copy;

        }
        Top++;
        Array[Top] = work;

    }

    @Override
    public E peek() {
        if (hasWork()) {
            return Array[Top];
        } else {
            throw new NoSuchElementException();

        }
    }

    //
    @Override
    public E next() {
        if (Top == -1) {
            throw new NoSuchElementException();

        } else {
            Top--;
            return Array[Top + 1];

        }
    }

    //Return the size of the WorkList
    @Override
    public int size() {
        return Top + 1;

    }


    //Clears the WorkList
    @Override
    public void clear() {
        Array = (E[])new Object[10];
        Top = -1;

    }
}
