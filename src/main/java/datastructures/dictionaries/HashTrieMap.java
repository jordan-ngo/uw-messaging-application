package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;


import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<A, HashTrieNode>(MoveToFrontList::new);
            this.value = value;

        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            Iterator<Item<A, HashTrieNode>> chainingIterator = pointers.iterator();
            Iterator<Entry<A, HashTrieNode>> resultIterator = new Iterator<Entry<A, HashTrieNode>>() {

                public boolean hasNext() {
                    return chainingIterator.hasNext();

                }
                public Entry<A, HashTrieNode> next() {
                    Item<A, HashTrieNode> returnAnItem = chainingIterator.next();
                    Entry<A, HashTrieNode> entry = new AbstractMap.SimpleEntry<>(returnAnItem.key, returnAnItem.value);
                    return entry;

                }
            };
            return resultIterator;

        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();

    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();

        }
        HashTrieNode Node = (HashTrieMap<A, K, V>.HashTrieNode) root;

        for (A temp : key) {
            if (Node.pointers.find(temp) == null) {
                Node.pointers.insert(temp, new HashTrieNode());
                Node = Node.pointers.find(temp);

            } else {
                Node = Node.pointers.find(temp);

            }
        }
            V result = Node.value;
            if (result == null) {
                size++;

            }
            Node.value = value;
            return result;

        }


    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();

        }
        HashTrieNode Node = (HashTrieNode) this.root;

        for (A temp: key) {
            if (Node.pointers.find(temp) != null) {
                Node = Node.pointers.find(temp);

            } else {
                return null;

            }
        }
        return Node.value;

    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();

        } else {
            HashTrieNode Node = (HashTrieMap<A, K, V>.HashTrieNode) this.root;
            boolean result = true;
            for (A temp : key) {
                if (Node.pointers.find(temp) != null) {
                    Node = Node.pointers.find(temp);

                } else {
                    result = false;

                }
            }
            return result;

        }
    }


    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();

    }
}
