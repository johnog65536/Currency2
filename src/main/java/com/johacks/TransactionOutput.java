package com.johacks;

public class TransactionOutput {
	private final int index;
	private final double value;
	private final String publicKey;
	
	
	
	public TransactionOutput(int index, double value, String publicKey) {
		this.index = index;
		this.value = value;
		this.publicKey = publicKey;
	}
	public int getIndex() {
		return index;
	}
	public double getValue() {
		return value;
	}
	public String getPublicKey() {
		return publicKey;
	}
	
	
}
