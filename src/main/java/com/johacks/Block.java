package com.johacks;

import java.util.ArrayList;

public class Block {
	private final ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	public final void addTransaction(Transaction txn) {
		transactions.add(txn);
	}
}
