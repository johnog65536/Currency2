package com.johacks;

import java.util.Collection;
import java.util.HashMap;

public class Transaction {
	private final HashMap<Integer,TransactionOutput> outputs = new HashMap<Integer,TransactionOutput>();
	private final HashMap<Integer,TransactionInput> inputs = new HashMap<Integer,TransactionInput>();
	
	public void addOutput(TransactionOutput output) {
		outputs.put(new Integer(output.getIndex()), output);
	}

	public void addInput (TransactionInput input) {
		inputs.put(new Integer(input.getIndex()), input);
	}
	
	public TransactionOutput getOutput(int index) {
		return outputs.get(new Integer(index));
	}
	public TransactionInput getInput(int index) {
		return inputs.get(new Integer(index));
	}
	
	public Collection<TransactionOutput> retreiveOutputs(){
		return outputs.values();
	}
}
