package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;
import java.util.NoSuchElementException;


/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    //Inner node class
    private class NodeList<E> {
        NodeList<E> next;
        E data;

        NodeList() {
            this(null, null);

        }
        NodeList(E data) {
            this(data, null);

        }
        NodeList(E data, NodeList<E> next) {
            this.data = data;
            this.next= next;

        }
    }
    private NodeList<E> front;
    private NodeList<E> back;
    private int size;


    public ListFIFOQueue() {
        this.front = front;
        this.back = back;
        this.size = size;

    }
    @Override
    public void add(E work) {
        // If the list is not empty
        if (hasWork()) {
            back.next = new NodeList<E> (work);
            back = back.next;

        } else {
            front = new NodeList<E>(work);
            back = front;

        }
        size++;

    }

    //
    @Override
    public E peek() {
        if (hasWork()) {
            return front.data;

        }
        throw new NoSuchElementException();

    }

    //Return the next element
    @Override
    public E next() {
        if (hasWork()) {
            E temp = front.data;
            front = front.next;
            size--;
            return temp;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override

    //Return the size
    public int size() {
        return size;
    }

    //Clear the WorkList
    @Override
    public void clear() {
        front = null;
        back = null;
        size = 0;
    }
}
