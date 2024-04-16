package com.epam.rd.autocode.collection.tree;

import java.util.Optional;

/**
 * Binary Search Tree.<br>
 * This class uses the natural ordering to compare elements.<br>
 * This implementation does not provide any balancing.
 *
 * @author D. Kolesnikov, Y. Mishcheriakov
 */
public class BinaryTree {

    private static final String INDENT = "-~-";
    private static final String EOL = System.lineSeparator();

    private Node root;
    private int size;

    private static final class Node {
        Integer e;
        Node left;
        Node right;

        Node(Integer e) {
            this.e = e;
        }

        public String toString() {
            return "Node[" + e + "]";
        }
    }

    /**
     * Creates an empty binary tree.
     */
    public BinaryTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Creates an empty binary tree and adds all distinct elements.
     *
     * @param elements the elements to add.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} elements.
     */
    public BinaryTree(Integer... elements) {
        this.root = null;
        this.size = 0;
        if(elements == null) throw new NullPointerException();
        addAll(elements);
    }

    /**
     * Adds an element to the tree.
     * If there is an element in the tree that,
     * using the compareTo method, is equal to the
     * element being added, then the addition does
     * not occur and the method returns false,
     * otherwise the element is included in the tree
     * and the method returns true.
     *
     * @param element the element to add.
     * @return {@code true} if the element was added (@code false} otherwise.
     * @throws NullPointerException if the parameter is {@code null}.
     */
    public boolean add(Integer element) {
        if(element == null) throw new NullPointerException();

        Node nodeToAdd = new Node(element);
        if(this.root == null){
            this.root = nodeToAdd;
            size++;
            return true;
        }else{
            Node currentNode = this.root;
            do{
                if(nodeToAdd.e.compareTo(currentNode.e) == 0) return false;
                else if(nodeToAdd.e.compareTo(currentNode.e) > 0){
                    if(currentNode.right == null) {
                        currentNode.right = nodeToAdd;
                        size++;
                        return true;
                    }
                    currentNode = currentNode.right;
                }
                else {
                    if(currentNode.left == null) {
                        currentNode.left = nodeToAdd;
                        size++;
                        return true;
                    }
                    currentNode = currentNode.left;
                }
            }while(true);
        }
    }


    /**
     * Adds all non-existing in the tree elements to this tree.
     *
     * @param ar the elements to add.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} elements.
     */
    public void addAll(Integer... ar) {
        for (Integer element: ar) {
            add(element);
        }
    }

    /**
     * @return a string representation of the tree
     * that composed using natural ordering.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        inOrderTraversalAndSort(this.root, sb);
        sb.append("]");
        return sb.toString();
    }

    private void inOrderTraversalAndSort(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderTraversalAndSort(node.left, sb);

            if (sb.length() > 1) {
                sb.append(", ");
            }

            sb.append(node.e);

            inOrderTraversalAndSort(node.right, sb);
        }
    }
    
//    public String toString() {
//
//        Integer[] binaryTree = nodeExtraction(new Integer[size()], this.root, 0);
//        sortingArray(binaryTree);
//        StringBuilder sb = new StringBuilder("[");
//        if (binaryTree.length > 0) sb.append(binaryTree[0]);
//        for (int i = 1; i<binaryTree.length ; i++) {
//            sb.append(", ").append(binaryTree[i]);
//        }
//        return sb.append("]").toString();
//    }
//
//    private Integer[] nodeExtraction(Integer[] arrayToAdd, Node node, int i){
//        if ((node.e != null)) {
//            arrayToAdd[i] = node.e;
//            if ((node.left != null)) nodeExtraction(arrayToAdd, node.left, indexToAdd(arrayToAdd));
//            if ((node.right != null)) nodeExtraction(arrayToAdd, node.right, indexToAdd(arrayToAdd));
//        }
//        return arrayToAdd;
//    }
//
//    private int indexToAdd (Integer[] arrayToAdd){
//        for(int i = 0; i<arrayToAdd.length ; i++){
//            if(arrayToAdd[i] == null) return i;
//        }
//        return 0;
//    }
//
//    private void sortingArray(Integer[] arrayToSort){
//        int n = arrayToSort.length;
//        boolean swapped;
//        for (int i = 0; i < n - 1; i++) {
//            swapped = false;
//            for (int j = 0; j < n - 1 - i; j++) {
//                if (arrayToSort[j] > arrayToSort[j + 1]) {
//                    // Swap array[j] and array[j+1]
//                    int temp = arrayToSort[j];
//                    arrayToSort[j] = arrayToSort[j + 1];
//                    arrayToSort[j + 1] = temp;
//                    swapped = true;
//                }
//            }
//            if (!swapped) {
//                break;
//            }
//        }
//    }


    /**
     * Removes the specified element from this tree if
     * it exists in this tree.
     *
     * @param element an element to remove.
     * @return {@code true} if the element was removed, otherwise {@code false}.
     * @throws NullPointerException if the parameter is {@code null}.
     */
    public Optional<Integer> remove(Integer element) {
        if(element == null) throw new NullPointerException();

        if(this.root == null && size() == 0) return Optional.empty();
        Optional<Integer> optionalToReturn;
        Node nodeToRemove = null;
        Node superNode = null;
        Node currentNode = this.root;
        Boolean isRigthNode = null;

        while(nodeToRemove==null){
            if(element.compareTo(currentNode.e) == 0){
                nodeToRemove = currentNode;
            }
            else if(element.compareTo(currentNode.e) > 0){
                if(currentNode.right == null) return Optional.empty();
                else{
                    superNode = currentNode;
                    currentNode = currentNode.right;
                    isRigthNode = true;
                }
            }
            else {
                if(currentNode.left == null) return Optional.empty();
                else {
                    superNode = currentNode;
                    currentNode = currentNode.left;
                    isRigthNode = false;
                }
            }
        }

        if(nodeToRemove.right == null && nodeToRemove.left == null){
            if(nodeToRemove.e.compareTo(this.root.e) == 0){
                this.root = null;
                size--;
                return Optional.of(nodeToRemove.e);
            }
            if(isRigthNode) superNode.right = null;
            else superNode.left = null;
            optionalToReturn = Optional.of(nodeToRemove.e);
        } else if ((nodeToRemove.right == null && nodeToRemove.left != null) || (nodeToRemove.right != null && nodeToRemove.left == null)){
            if(nodeToRemove.e.compareTo(this.root.e) == 0){
                if(this.root.right == null){
                    this.root = this.root.left;
                }
                else {
                    this.root = this.root.right;
                }
                size--;
                return Optional.of(nodeToRemove.e);
            }
            if(nodeToRemove.right == null){
                if(isRigthNode)superNode.right = nodeToRemove.left;
                else superNode.left = nodeToRemove.left;
            } else {
                if(isRigthNode)superNode.right = nodeToRemove.right;
                else superNode.left = nodeToRemove.right;
            }
            optionalToReturn = Optional.of(nodeToRemove.e);
        } else {
            Node startRightNode = nodeToRemove.right;
            Node leftMost = startRightNode.left == null ? startRightNode : startRightNode.left;
            Node superLeftMost = leftMost == startRightNode ? nodeToRemove : startRightNode;

            while(leftMost.left != null){
                superLeftMost = leftMost;
                leftMost = leftMost.left;
            }

            if(superLeftMost.e.compareTo(nodeToRemove.e) == 0){
                if(nodeToRemove.e.compareTo(this.root.e) == 0){
                    leftMost.left = this.root.left;
                    this.root = leftMost;
                }else if(isRigthNode){
                    superNode.right = leftMost;
                    leftMost.left = nodeToRemove.left;
                } else {
                    superNode.left = leftMost;
                    leftMost.left = nodeToRemove.left;
                }
                optionalToReturn = Optional.of(nodeToRemove.e);
            } else {
                superLeftMost.left = leftMost.right;
                optionalToReturn = Optional.of(nodeToRemove.e);
                nodeToRemove.e = leftMost.e;
            }

        
        }
        size--;
        return optionalToReturn;
    }

    /**
     * Returns the size of the tree.
     *
     * @return the number of elements in the tree.
     */
    public int size() {
        // place your code here
        return this.size;
    }

    /**
     * The helper method for you.<br>
     * Creates a 'tree' string representation of the tree.<br>
     * If the sequence of elements `[3, 1, 2, 5, 6, 4, 0]`,
     * in the specified order, was added to the tree,
     * then the following representation is expected:
     * <pre>
     *      7
     *   6
     *     5
     * 4
     *     2
     *   1
     *     0
     * </pre>
     * '4' is the root of this tree, '0' is the most left leaf,
     * and '7' is the most right leaf of the tree.
     *
     * @return a 'tree' string representation of the tree.
     */
    String asTreeString() {
        StringBuilder sb = new StringBuilder();
        asTreeString(sb, root, 0);
        return sb.toString();
    }

    private void asTreeString(StringBuilder sb, Node node, int k) {
        if (node == null) {
            return;
        }
        asTreeString(sb, node.right, k + 1);
        sb.append(INDENT.repeat(k));
        sb.append(String.format("%3s", node.e)).append(EOL);
        asTreeString(sb, node.left, k + 1);
    }
}
