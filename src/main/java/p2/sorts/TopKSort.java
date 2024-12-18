package p2.sorts;

import datastructures.worklists.MinFourHeap;
import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> Heap = new MinFourHeap<>(comparator);

        if (k > array.length) {
            k = array.length;

        }
        for (int i = 0; i < k; i++) {
            Heap.add(array[i]);

            }
        for (int i = k; i < array.length; i ++) {
            if (comparator.compare(array[i], Heap.peek()) > 0) {
                Heap.next();
                Heap.add(array[i]);

            }
            array[i] = null;

        }
        for (int i = 0; i < k; i++) {
            array[i] = Heap.next();

        }
    }
}
