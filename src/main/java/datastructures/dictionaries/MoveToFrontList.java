package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    public Node head;
    public Node tail;

    public MoveToFrontList() {
        this.head = null;
        this.tail = null;
        this.size = 0;

    }

    public static class Node<K, V> {
        public final K key;
        public V value;
        public Node next;
        public Node previous;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.previous = null;

        }
    }

    @Override
    public V insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();

        } else {
            V temp = this.find(key);
            if (temp == null) {
                Node currNode = new Node(key, value);
                V result = null;
                if (this.size != 0) {
                    result = (V) this.head.value;
                    this.head.previous = currNode;
                    currNode.next = this.head;

                    this.head = currNode;
                    this.size++;

                    return result;
                } else {
                    this.head = currNode;
                    this.tail = currNode;

                    this.size++;

                    return value;

                }
            } else {
                this.head.value = value;
                return temp;

            }
        }
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();

        } else {
            Node currNode = this.head;
            while (currNode != null) {
                if (currNode.key.equals(key) && currNode.previous == null) {
                    return (V) currNode.value;

                } else if
                (currNode.key.equals(key) && currNode.previous != null && currNode.next != null) {
                    currNode.previous.next = currNode.next;
                    currNode.next.previous = currNode.previous;
                    this.tail = this.tail.previous;

                    currNode.next = this.head;
                    currNode.previous = null;

                    this.head.previous = currNode;
                    this.head = currNode;

                    return (V) currNode.value;

                } else if
                (currNode.key.equals(key) && currNode.next == null) {
                    currNode.previous.next = null;
                    this.tail = this.tail.previous;
                    currNode.next = this.head;
                    currNode.previous = null;

                    this.head.previous = currNode;
                    this.head = currNode;

                    return (V) currNode.value;

                } else {
                    currNode = currNode.next;

                }
            }
            return null;

        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ListIterator();

    }

    private class ListIterator implements Iterator<Item<K, V>> {
        private Node currNode;

        public ListIterator() {
            currNode = MoveToFrontList.this.head;

        }

        @Override
        public boolean hasNext() {
            return currNode != null;

        }

        public Item<K, V> next() {
            if (hasNext()) {
                Item<K, V> temp = new Item<>((K) this.currNode.key, (V) this.currNode.value);
                this.currNode = this.currNode.next;
                return temp;

            } else {
                return null;

            }
        }
    }
}

