package com.johacks;

public class TransactionOutput {
	private final int index;
	private final double value;
	private final String publicKey;
	
	private final String proof;
	
	
	public TransactionOutput(int index, double value, String publicKey, String proof) {
		this.index = index;
		this.value = value;
		this.publicKey = publicKey;
		this.proof = proof;
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
	public String getProof() {
		return proof;
	}
	
}
