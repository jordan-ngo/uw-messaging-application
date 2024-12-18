package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> Comparator) {
        sort(array, Comparator, 0, array.length);

    }
    public static <E> void sort(E[] array, Comparator<E> Comparator, int low, int high) {
        if (high - low >= 2) {
            E pivot = array[high - 1];
            int partition = low - 1;
            for (int i = low; i < high; i++) {
                if (Comparator.compare(array[i], pivot) <= 0 ) {
                    partition++;
                    E temp = array[partition];
                    array[partition] = array[i];
                    array[i] = temp;

                }
            }
            sort(array, Comparator, low, partition);
            sort(array, Comparator, partition + 1, high);
        }
    }
}
