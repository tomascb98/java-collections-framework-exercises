package com.epam.rd.autocode.queue;

import java.util.*;

import com.epam.rd.autocode.queue.CashBox.State;


public class Shop {

	private int cashBoxCount;

	private List<CashBox> cashBoxes;

	public Shop(int count) {
		this.cashBoxCount = count;
		cashBoxes = new ArrayList<>();
		for(int i = 0 ; i<count ; i++) this.cashBoxes.add(new CashBox(i));
	}

	public int getCashBoxCount() {
		return cashBoxCount;
	}

	private static int getTotalBuyersCount(List<CashBox> cashBoxes) {
		int countBuyers = 0;
		for (CashBox cashBox: cashBoxes) {
			/*for (Buyer buyer:cashBox.getQueue()) {
				countBuyers++;
			}*/
			if(cashBox.inState(State.ENABLED)) countBuyers += cashBox.getQueue().size();
		}
		return countBuyers;
	}

	public void addBuyer(Buyer buyer) {
		Iterator<CashBox> iterator = cashBoxes.iterator();
		CashBox cashBoxToAdd = null;
		while (iterator.hasNext()){
			CashBox cashBoxToCompare = iterator.next();
			if(cashBoxToAdd == null && cashBoxToCompare.inState(State.ENABLED)) cashBoxToAdd = cashBoxToCompare;
			else if(cashBoxToCompare.inState(State.ENABLED) && cashBoxToAdd != null){
				if(cashBoxToCompare.getQueue().size() < cashBoxToAdd.getQueue().size()) cashBoxToAdd = cashBoxToCompare;
			}
		}
        cashBoxToAdd.addLast(buyer); //Can produce NullPointerException
	}

	public void tact() {
		for (CashBox cashBox: cashBoxes) {
			cashBox.serveBuyer();
		}
		balance();
	}

	public void balance(){
		Queue<Buyer> defectorBuyers = new ArrayDeque<>();

		int numCurrentBuyers = getTotalBuyersCount(cashBoxes);
		int availableCashboxes = availableCashboxes();
		int minNumberBuyers = numCurrentBuyers/availableCashboxes;
		int maxNumberAddition = ((numCurrentBuyers % availableCashboxes) == 0) ? 0 : 1;

		for (CashBox cashBox: cashBoxes) {
			if(cashBox.getQueue().size() > minNumberBuyers+maxNumberAddition && cashBox.inState(State.ENABLED)){
				int numberOfDefectorBuyers = cashBox.getQueue().size() - (minNumberBuyers+maxNumberAddition);
				for(int i=0 ; i<numberOfDefectorBuyers; i++) defectorBuyers.add(cashBox.removeLast());
			}
		}

		for (CashBox cashBox: cashBoxes) {
			if(cashBox.getQueue().size() < minNumberBuyers+maxNumberAddition && cashBox.inState(State.ENABLED)){
				int numberOfBuyersToAdd = minNumberBuyers + maxNumberAddition - cashBox.getQueue().size();
				for(int i=0 ; i < numberOfBuyersToAdd ; i++) {
					if(!defectorBuyers.isEmpty()) cashBox.addLast(defectorBuyers.poll());
				}
			}
		}

		for (CashBox cashbox: cashBoxes) {
			if(!isTheMaxDifferenceOne() && cashbox.inState(State.IS_CLOSING)){
				Buyer buyer = cashbox.removeLast();
				addBuyer(buyer);
			}
		}
	}
	private int availableCashboxes (){
		int countOfCashboxes = 0;
		for (CashBox cashbox:cashBoxes){
			if(cashbox.inState(State.ENABLED)) countOfCashboxes++;
		}
		return countOfCashboxes;
	}
	private boolean isTheMaxDifferenceOne(){
		int maxEnabled = 0;
		int maxIsClosing = 0;

		for (CashBox cashBox : cashBoxes) {
			if(cashBox.inState(State.IS_CLOSING) && cashBox.getQueue().size() > maxIsClosing) maxIsClosing = cashBox.getQueue().size();
			else if (cashBox.inState(State.ENABLED) && cashBox.getQueue().size() > maxEnabled) maxEnabled = cashBox.getQueue().size();
		}
		return !(maxIsClosing-maxEnabled >= 1);
	}


	public static int[] getMinMaxSize(List<CashBox> cashBoxes) {
		int maxSize = cashBoxes.get(0).getQueue().size();
		int minSize = cashBoxes.get(0).getQueue().size();
		for (CashBox cashBox : cashBoxes){
			if(cashBox.getQueue().size() < minSize) minSize = cashBox.getQueue().size();
			else if (cashBox.getQueue().size() > maxSize) maxSize = cashBox.getQueue().size();
		}
		return new int[]{minSize, maxSize};
    }

	public void setCashBoxState(int cashBoxNumber, State state) {
		getCashBox(cashBoxNumber).setState(state);
	}

	public CashBox getCashBox(int cashBoxNumber) {
		CashBox cashBoxToReturn = null;
		for (CashBox cashBox: cashBoxes){
			if(cashBox.getNumber() == cashBoxNumber){
				cashBoxToReturn = cashBox;
				break;
			}

		}
		return cashBoxToReturn;
	}
	
	public void print() {
		StringBuilder sb = new StringBuilder(getCashBox(0).toString());
		for(int i = 1 ; i<cashBoxCount ; i++){
			sb.append("\n");
			sb.append(getCashBox(i).toString());
		}
		System.out.println(sb);
	}

}
