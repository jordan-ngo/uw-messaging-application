package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    // TODO: Implement me!
    public class AVLNode extends BSTNode {
        private int height;
        public AVLNode(K key, V value) {
            super (key, value);
            this.height = 0;

        }
    }

    public AVLTree() {
        super();
        this.size = 0;

    }
    public int acquireHeight(AVLNode n ) {
        if (n == null) {
            return -1;

        }
        return n.height;

    }

    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V result = this.find(key);
        AVLNode temp = new AVLNode(key, value);
        if (result == null) {
            this.size++;
            result = value;

        }
        this.root = treeCreate((AVLNode) this.root, temp);
        return result;

    }

private AVLNode rotationLeft(AVLNode temp) {
        AVLNode root = (AVLNode) temp.children[0];
        temp.children[0] = root.children[1];
        root.children[1] = temp;
        temp.height = Math.max(acquireHeight((AVLNode) temp.children[0]),
                acquireHeight((AVLNode) temp.children[1])) + 1;
        root.height = Math.max(acquireHeight((AVLNode) root.children[0]),
                temp.height) + 1;
        return root;
}
private AVLNode rotationRight(AVLNode root) {
        AVLNode temp = (AVLNode) root.children[1];
        root.children[1] = temp.children[0];
        temp.children[0] = root;
        root.height = Math.max(acquireHeight((AVLNode) root.children[0]),
                acquireHeight((AVLNode) root.children[1])) + 1;
        temp.height = Math.max(acquireHeight((AVLNode) temp.children[1]),
                root.height) + 1;
        return temp;

}
private AVLNode doubleRotationLeft(AVLNode temp) {
        temp.children[0] = rotationRight((AVLNode) temp.children[0]);
        return rotationLeft(temp);

}

private AVLNode doubleRotationRight(AVLNode temp) {
        temp.children[1] = rotationLeft((AVLNode) temp.children[1]);
        return rotationRight(temp);

}

    private AVLNode checkBalance(AVLNode node) {
        if (node == null) {
            return node;

        }
        if (acquireHeight((AVLNode) node.children[0]) - acquireHeight((AVLNode) node.children[1]) > 1) {
            if (acquireHeight((AVLNode) node.children[0].children[0]) >=
                    (acquireHeight((AVLNode) node.children[0].children[1]))) {
                node = rotationLeft(node);

            } else {
                node = doubleRotationLeft(node);

            }
        } else {
            if (acquireHeight((AVLNode) node.children[1]) - acquireHeight((AVLNode) node.children[0]) > 1) {
                if (acquireHeight((AVLNode) node.children[1].children[1]) >=
                        (acquireHeight((AVLNode) node.children[1].children[0]))) {
                    node = rotationRight(node);

                } else {
                    node = doubleRotationRight(node);

                }
            }
        }
        node.height = Math.max(acquireHeight((AVLNode) node.children[0]), acquireHeight((AVLNode) node.children[1])) + 1;
        return node;

    }

public AVLNode treeCreate(AVLNode root, AVLNode temp) {
        if (root == null) {
            return temp;

        }

        if (root.key.compareTo(temp.key) < 0) {
            root.children[1] = treeCreate((AVLNode) root.children[1], temp);
        } else if
        (root.key.compareTo(temp.key) > 0) {
            root.children[0] = treeCreate((AVLNode) root.children[0], temp);
        } else {
            root.value = temp.value;

        }
        return checkBalance(root);

}
}
