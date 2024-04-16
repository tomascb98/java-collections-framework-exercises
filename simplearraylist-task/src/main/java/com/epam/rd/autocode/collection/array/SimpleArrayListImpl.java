package com.epam.rd.autocode.collection.array;


import java.util.Optional;

public class SimpleArrayListImpl implements SimpleArrayList {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int FACTOR_MULTIPLIER = 2;
    private static final double INCREASE_LOAD_FACTOR = 0.75;
    private static final double DECREASE_LOAD_FACTOR = 0.4;

    private Object[] elements;
    private int size;

    /**
     * Creates a list with the default capacity = 10.
     */
    public SimpleArrayListImpl() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean add(Object element) {
        if(element==null) throw new NullPointerException();
        increaseCapacityArray();
        elements[size()] = element;
        size++;
        return true;
    }

    private void increaseCapacityArray (){
        if(size()+1 >= capacity()*INCREASE_LOAD_FACTOR){
            int newCapacity = (int) (capacity()*FACTOR_MULTIPLIER*INCREASE_LOAD_FACTOR);
            Object[] newArray = new Object[newCapacity];
            System.arraycopy(elements, 0, newArray, 0, elements.length);
            elements = newArray;
        }
    }



    @Override
    public int capacity() {
        // place your code here
        return elements.length;
    }

    @Override
    public boolean decreaseCapacity() {
        if(size() <= capacity()*DECREASE_LOAD_FACTOR){
            int newCapacity = (size()*FACTOR_MULTIPLIER);
            Object[] newArray = new Object[newCapacity];
            System.arraycopy(elements, 0, newArray, 0, newCapacity);
            elements = newArray;
            return true;
        }
        return false;
    }

    @Override
    public Object get(int index) {
        // place your code here
        if(index < 0 || index > size()-1) throw new IndexOutOfBoundsException();
        return elements[index];
    }

    @Override
    public Optional<Object> remove(Object el) {
        // place your code here
        if(el==null) throw new NullPointerException();
        int index = 0;
        for (Object element: elements) {
            if(el.equals(element)){
                moveToLeft(index);
                size--;
                return Optional.of(element);
            }
            index++;
        }
        return Optional.empty();
    }

    private void moveToLeft (int indexRemoved){
        //System.arraycopy(this.elements,indexRemoved+1,this.elements,indexRemoved, lengthToMove);
        for(int i=indexRemoved; i<elements.length-1; i++){
            elements[i] = elements[i+1];
        }
    }

    @Override
    public int size() {
        // place your code here
//        int countElements = 0;
//        for (Object element: elements) {
//            if (element != null) countElements++;
//            else if(element == null) break;
//        }
        return size;
    }

    @Override
    public String toString() {
        // place your code here
        StringBuilder stringBuilder = new StringBuilder("[");
        if(size()!=0) stringBuilder.append(elements[0].toString());
        for(int i=1; i<size(); i++){
            stringBuilder.append(", ").append(elements[i].toString());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
