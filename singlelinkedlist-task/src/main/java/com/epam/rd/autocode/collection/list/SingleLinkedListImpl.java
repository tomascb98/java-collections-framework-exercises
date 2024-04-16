package com.epam.rd.autocode.collection.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class SingleLinkedListImpl implements List {

    private Node head;

    private static class Node {
        Object data;
        Node next;

        Node(Object data) {
            this.data = data;
        }

        Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "[" + data + ']';
        }
    }

    public SingleLinkedListImpl() {
        head = new Node(0, null);

    }

    @Override
    public void clear() {
        head.next = null;
        head.data = 0;
    }

    @Override
    public int size() {
        return (int) head.data;
    }

    /**
     * Inserts the specified element at the <b>front</b> of this list
     *
     * @param el the element to add
     * @return {@code true} if this list was changed {@code false} otherwise
     */
    @Override
    public boolean add(Object el) {
        if (el == null) throw new NullPointerException();
        Node nodeToAdd = new Node(el);
        nodeToAdd.next = head.next;
        head.next = nodeToAdd;
        head.data = (Integer) head.data + 1;
        return true;
    }

    @Override
    public Optional<Object> remove(Object el) {
        if (el == null) throw new NullPointerException();
        Node previousNode = head;
        Node actualNode = previousNode.next;
        for (int i = 0; i < size(); i++) {
            if (actualNode.data.equals(el)) {
                previousNode.next = actualNode.next;
                head.data = (Integer) head.data - 1;
                return Optional.of(actualNode.data);
            }
            previousNode = actualNode;
            actualNode = actualNode.next;
        }
        return Optional.empty();
    }
    public Optional<Object> remove(Object el, int index) {
        if (el == null) throw new NullPointerException();
        Node previousNode = head;
        Node actualNode = previousNode.next;
        for (int i = 0; i <= index; i++) {
            if (actualNode.data.equals(el) && i == index) {
                previousNode.next = actualNode.next;
                head.data = (Integer) head.data - 1;
                return Optional.of(actualNode.data);
            }
            previousNode = actualNode;
            actualNode = actualNode.next;
        }
        return Optional.empty();
    }

    @Override
    public Object get(int index) {
        if(index<0 || index>size()) throw new IndexOutOfBoundsException();
        Node previousNode = head;
        Node actualNode = previousNode.next;
        Object objectToReturn = null;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                objectToReturn = actualNode.data;
                break;
            }
            actualNode = actualNode.next;
        }
        return objectToReturn;
    }

    /**
     * Makes a string representation of this list.
     * The elements ordering must be coordinated with the
     * {@code Iterator} of this list.
     *
     * @return a string representation of this list.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        if(size()==0) return stringBuilder.append("]").toString();
        Node currentNode = head.next;
        stringBuilder.append(currentNode.data.toString());
        currentNode = currentNode.next;
        for(int i=2 ; i<=size(); i++){
            stringBuilder.append(", ").append(currentNode.data.toString());
            currentNode=currentNode.next;
        }
        return stringBuilder.append("]").toString();
    }

    /**
     * Returns an iterator over elements.
     * Iterator must implement the remove method.
     * <pre>
     * list.add("A");
     * list.add("B");
     * list.add("C");
     * for(Object obj : list) { System.out.print(obj) } // prints: CBA
     * </pre>
     *
     * @return an iterator
     */
    @Override
    public Iterator<Object> iterator() {
        return new Iterator<>() {
            Node currentNode = head;
            int currentIndex = 0;
            boolean flagIllegalState = true;

            @Override
            public boolean hasNext() {
                return currentNode.next != null;
            }

            @Override
            public Object next() {
                if(!hasNext()) throw new NoSuchElementException();
                currentNode = currentNode.next;
                flagIllegalState=false;
                currentIndex++;
                return currentNode.data;
            }

            @Override
            public void remove() {
                if(flagIllegalState){
                    throw new IllegalStateException();
                }
                SingleLinkedListImpl.this.remove(currentNode.data, currentIndex-1);
                currentIndex--;
                flagIllegalState = true;
            }
        };
    }
}


