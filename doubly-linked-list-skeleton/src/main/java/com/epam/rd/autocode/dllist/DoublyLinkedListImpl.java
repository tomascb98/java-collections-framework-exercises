package com.epam.rd.autocode.dllist;

import java.util.Optional;

public class DoublyLinkedListImpl implements DoublyLinkedList {

	private Node head;
	
	private Node tail;

	private static class Node {

		Object element;

		Node next;

		Node prev;

		Node(Object obj, Node prev, Node next) {
			this.element = obj;
			this.next = next;
			this.prev = prev;
		}

	}
	
	@Override
	public boolean addFirst(Object element) {
		if(element == null) return false;
		Node nodeToAdd = new Node(element, null, null);
		if(head==null){
			head = nodeToAdd;
			tail = nodeToAdd;
			return true;
		}
		if(head.next == null){
			nodeToAdd.next = tail;
			head = nodeToAdd;
			tail.prev = head;
			return true;
		}
		nodeToAdd.next=head;
		head = nodeToAdd;
		head.next.prev = head;
		return true;
	}

	@Override
	public boolean addLast(Object element) {
		if(element == null) return false;
		Node nodeToAdd = new Node(element, null, null);
		if(tail==null){
			head = nodeToAdd;
			tail = nodeToAdd;
			return true;
		}
		if(tail.prev == null){
			nodeToAdd.prev = head;
			tail=nodeToAdd;
			head.next=tail;
			return true;
		}

		nodeToAdd.prev = tail;
		tail = nodeToAdd;
		tail.prev.next=tail;
		return true;
	}

	@Override
	public void delete(int index) {
		if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
		Node currentNode = head;
		if(size()==1){
			head=null;
			tail=null;
		}else if(index==0 && size()>1){
			head = currentNode.next;
			head.prev = null;
		}else if(index==size()-1){
			tail = tail.prev;
			tail.next = null;
		}else{
			for(int i=1; i<=index; i++){
				currentNode = currentNode.next;
			}
			currentNode.next.prev = currentNode.prev;
			currentNode.prev.next = currentNode.next;
		}
	}
	
	@Override
	public Optional<Object> remove(Object element) {
		Node currentNode = head;
		for(int i=0; i<size(); i++){
			if(currentNode.element.equals(element)){
				delete(i);
				return Optional.of(currentNode.element);
			}
			currentNode = currentNode.next;
		}
		return Optional.empty();
	}

	@Override
	public boolean set(int index, Object element) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
		if(element == null) return false;
		Node currentNode = head;

		for(int i=1; i<=index; i++){
			currentNode = currentNode.next;
		}
		Node nodeToSet = new Node(element,currentNode.prev==null ? null : currentNode.prev,currentNode.next==null ? null : currentNode.next);
		if(nodeToSet.prev == null && nodeToSet.next == null){
			head=nodeToSet;
			tail=nodeToSet;
			return true;
		} else {
			currentNode.next.prev = nodeToSet;
			currentNode.prev.next = nodeToSet;
		}
		return true;
	}

	@Override
	public int size() {
		Node currentNode = head;
		int countNodes = 0;
		while(currentNode!=null){
			countNodes++;
			currentNode = currentNode.next;
		}
		return countNodes;
	}

	@Override
	public Object[] toArray() {
		Object[] arrayToReturn = new Object[size()];
		Node currentNode = head;
		for(int i=0; i<arrayToReturn.length; i++){
			arrayToReturn[i]=currentNode.element;
			currentNode = currentNode.next;
		}
		return arrayToReturn;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(head.element.toString());
		if(size()==1) return stringBuilder.toString();
		Node currentNode = head.next;
		for(int i=1 ; i<size(); i++){
			stringBuilder.append(" ").append(currentNode.element.toString());
			currentNode=currentNode.next;
		}
		return stringBuilder.toString();
	}
	
}
