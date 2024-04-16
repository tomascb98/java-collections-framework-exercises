package com.epam.rd.autocode.hashtableopen816;

public interface HashtableOpen8to16 {
    void insert(int key, Object value);
    Object search(int key);
    void remove (int key);
    int size();
    int[] keys();

    static HashtableOpen8to16 getInstance(){
        return new HashTableOpen8to16Impl();
    }

    class HashTableOpen8to16Impl implements HashtableOpen8to16{

        private HashTableNode[] hashTable;
        private int capacity;


        private HashTableOpen8to16Impl() {
            this.hashTable = new HashTableNode[8];
            this.capacity = 8;
        }

        private static class HashTableNode {
            private final Object element;
            private final int key;

            public HashTableNode(Object element, int key) {
                this.element = element;
                this.key = key;
            }

            public Object getElement() {
                return element;
            }


            public int getKey() {
                return key;
            }

        }
        @Override
        public void insert(int key, Object value) {
            if(size() == capacity && capacity<=8 && checkKey(key) < 0) doubleCapacity();

            HashTableNode hashTableNodeToAdd = new HashTableNode(value, key);
            int indexToAdd = Math.abs(key)%capacity;

            if(size() == 16 && checkKey(key)<0) throw new IllegalStateException();

            if(checkKey(key) >= 0) indexToAdd = checkKey(key);
            else {
                HashTableNode nodeToCompare = hashTable[indexToAdd];
                while (nodeToCompare != null) {
                    if (indexToAdd == capacity - 1){
                        indexToAdd = 0;
                    }
                    else {
                        indexToAdd++;
                    }
                    nodeToCompare = hashTable[indexToAdd];
                }
            }
            hashTable[indexToAdd] = hashTableNodeToAdd;
        }

        public int checkKey(int key){
            int indexToReturn = 0;
            for (HashTableNode hashTableNode : hashTable){
                if(hashTableNode!=null){
                    if(hashTableNode.getKey() == key) return indexToReturn;
                }
                indexToReturn++;
            }
            return -1;
        }
        // TAL VEZ TOCA REHASHEAR DE NUEVO PARA ACOMODAR EN LAS CELDAS, QUE SUPONGO QUE SI
        //PORQUE PARA ENCONTRARLOS ES A RAIZ DE LA KEY, SO TOCA HACERLO

        public void doubleCapacity() {
            int doubleCapacity = capacity * 2;
            if (doubleCapacity <= 16) {
                HashTableNode[] moreCapacityHashTableNodes = new HashTableNode[doubleCapacity];
                reHashing(moreCapacityHashTableNodes, doubleCapacity);
                hashTable = moreCapacityHashTableNodes;
                capacity = doubleCapacity;
            }
        }

        private void reHashing(HashTableNode[] newCapacityHashTable, int newCapacity){
            for (HashTableNode hashTableNode:this.hashTable) {
                if(hashTableNode!=null) {
                    int indexToAdd = Math.abs(hashTableNode.getKey() % newCapacity);
                    int i = 0;
                    do {
                        if(newCapacityHashTable[indexToAdd] == null){
                            newCapacityHashTable[indexToAdd] = hashTableNode;
                            break;
                        }
                        else{
                            if(indexToAdd == newCapacity-1) indexToAdd = 0;
                            else indexToAdd++;
                        }
                        i++;
                    }while (i<=capacity);
                }
            }
        }

        @Override
        public Object search(int key) {
            int indexToSearch = checkKey(key);
            return indexToSearch < 0 ? null : hashTable[indexToSearch].getElement();
        }

        @Override
        public void remove(int key) {
            int indexToRemove = checkKey(key);
            if(indexToRemove >= 0){
                hashTable[indexToRemove] = null;
                checkCapacity();
            }
        }

        private void checkCapacity(){
            if(size()*4 == capacity){
                int newCapacity = capacity/2;
                HashTableNode[] smallerCapacityHashTable = new HashTableNode[newCapacity];
                reHashing(smallerCapacityHashTable, newCapacity);
                hashTable = smallerCapacityHashTable;
                capacity = newCapacity;
            }
        }

        @Override
        public int size() {
            int countNodes = 0;
            for (HashTableNode hashTableNode: hashTable) {
                if(hashTableNode != null) countNodes++;
            }
            return countNodes;
        }

        @Override
        public int[] keys() {
            int[] keysOfHashTable = new int[capacity];
            int i = 0;
            for (HashTableNode hashTableNode:hashTable) {
                keysOfHashTable[i] = hashTableNode==null ? 0 : hashTableNode.getKey();
                i++;
            }
            return keysOfHashTable;
        }
    }
}

