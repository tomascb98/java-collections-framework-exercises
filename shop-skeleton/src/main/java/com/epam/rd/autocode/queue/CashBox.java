package com.epam.rd.autocode.queue;

import java.util.ArrayDeque;
import java.util.Deque;

public class CashBox {
	
	private int number;
	
	private Deque<Buyer> byers;
	
	private State state;
	
	public enum State {
		ENABLED, DISABLED, IS_CLOSING
	}
	
	public CashBox(int number) {
		this.number = number;
		this.byers = new ArrayDeque<>();
		this.state = State.DISABLED;
	}

	public Deque<Buyer> getQueue() {
        return new ArrayDeque<>(byers);
	}

	public Buyer serveBuyer() {
		if(inState(State.ENABLED) || inState(State.IS_CLOSING) && !byers.isEmpty()){
			Buyer buyer = byers.poll();
			if(buyer != null && inState(State.IS_CLOSING) && byers.isEmpty()) state = State.DISABLED;
			return buyer;
		}
		return null;
	}

	public boolean inState(State state) {
		return getState().equals(state);
	}

	public boolean notInState(State state) {
		return !getState().equals(state);
	}

	public int getNumber() {
		return number;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return this.state;
	}

	public void addLast(Buyer byer) {
		byers.offerLast(byer);
	}

	public Buyer removeLast() {
		return byers.pollLast();
	}
	
	@Override
	public String toString() {
		String stateRepresentation = "";
		switch (getState()){
			case ENABLED:
				stateRepresentation = "+";
				break;
			case DISABLED:
				stateRepresentation = "-";
				break;
			case IS_CLOSING:
				stateRepresentation = "|";
				break;
		}
		StringBuilder stringBuilder = new StringBuilder("#");
		stringBuilder.append(this.number)
				.append("[")
				.append(stateRepresentation)
				.append("]")
//				.append(" ")
				;
		for (Buyer byer:this.byers) {
			stringBuilder.append(byer.toString());
		}
		return stringBuilder.toString();
	}

}
