package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] bucket;
    private int[] prime;
    private int pIndex;


    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        bucket = new Dictionary[13];
        pIndex = 0;
        prime = new int[]{31, 61, 113, 317, 997, 1109, 3049, 6827, 9433, 15061, 33049, 75931, 344497};
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();

        }
        double loadFactor = 0.75;

        if (((double) size / (double) bucket.length) >= loadFactor) {
            resize();

        }
        int hashIndex = acquireHashIndex(key, bucket.length);
        if (bucket[hashIndex] == null) {
            bucket[hashIndex] = newChain.get();

        }
        Dictionary<K, V> curr = bucket[hashIndex];
        int previous = curr.size();
        V result = curr.insert(key, value);

        if (previous != curr.size()) {
            this.size++;

        }
        return result;

    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();

        }
        if (bucket[acquireHashIndex(key, bucket.length)] == null) {
            return null;

        } else {
            return this.bucket[acquireHashIndex(key, bucket.length)].find(key);

        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        Iterator iterator = new Iterator() {
            int buckCount = -1;
            Iterator<Item<K, V>> iteratorBuckets;

            public boolean hasNext() {
                if (buckCount == -1 || !iteratorBuckets.hasNext()) {
                    helper();

                }
                if (iteratorBuckets == null) {
                    return false;

                }
                return iteratorBuckets.hasNext();

            }

            private void helper() {
                buckCount++;
                while (buckCount < bucket.length && (bucket[buckCount] == null || bucket[buckCount].isEmpty())) {
                    buckCount++;

                }
                if (buckCount >= bucket.length) {
                    iteratorBuckets = null;
                    return;

                }
                if (buckCount >= bucket.length) {
                    iteratorBuckets = null;
                    return;

                }
                iteratorBuckets = bucket[buckCount].iterator();

            }

            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();

                }
                return iteratorBuckets.next();

            }
        };
        return iterator;
    }

    private void resize() {
        Dictionary<K, V>[] temp;

        if (pIndex < prime.length) {
            pIndex++;
            temp = new Dictionary[prime[pIndex]];
        } else {
            temp = new Dictionary[2 * bucket.length];

        }
        Iterator<Item<K, V>> iterator = iterator();
        while (iterator.hasNext()) {
            Item<K, V> curr = iterator.next();
            int hIndex = acquireHashIndex(curr.key, temp.length);

            if (temp[hIndex] == null) {
                temp[hIndex] = newChain.get();

            }
            Dictionary<K, V> chain = temp[hIndex];
            chain.insert(curr.key, curr.value);

        }
        bucket = temp;

    }

    private int acquireHashIndex(K key, int size) {
        int result = 31 * key.hashCode();
        if (result < 0) {
            return Math.abs(result) % size;
        }
        return result % size;
    }
}
