package com.johacks;

public class Proof {
	private final int blockID;
	private final int transactionID;
	private final int outputID;
	private final String pubKey;
	private final double value;
	
	public Proof(int blockID, int transactionID, int outputID,String pubKey,double value) {
		super();
		this.blockID = blockID;
		this.transactionID = transactionID;
		this.outputID = outputID;
		this.pubKey = pubKey;
		this.value=value;
	}

	public int getBlockID() {
		return blockID;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public int getOutputID() {
		return outputID;
	}
	
	public String getPubKey() {
		return pubKey;
	}
	
	public double getValue() {
		return value;
	}
}
