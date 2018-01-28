package com.johacks;

public class TransactionInput {
	private final int index;
	private final TransactionOutput output;
	
	
	public TransactionInput(int index, TransactionOutput output) {
		super();
		this.index = index;
		this.output = output;
	}
	
	public int getIndex() {
		return index;
	}
	public TransactionOutput getOutput() {
		return output;
	}

}
