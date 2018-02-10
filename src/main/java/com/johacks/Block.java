package com.johacks;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author jogden
 *
 */
public class Block {
	private final ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	private int nonce;
	private String hash;
	private String minedBy;
	private int index;
	private int prevIndex;
	private Date confirmationTime;
	
	public final void addTransaction(Transaction txn) {
		transactions.add(txn);
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public int getNonce() {
		return nonce;
	}

	public String getHash() {
		return hash;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getMinedBy() {
		return minedBy;
	}

	public void setMinedBy(String minedBy) {
		this.minedBy = minedBy;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getPrevIndex() {
		return prevIndex;
	}

	public void setPrevIndex(int prevIndex) {
		this.prevIndex = prevIndex;
	}

	public Date getConfirmationTime() {
		return confirmationTime;
	}

	public void setConfirmationTime(Date confirmationTime) {
		this.confirmationTime = confirmationTime;
	}
	
	
	
}
