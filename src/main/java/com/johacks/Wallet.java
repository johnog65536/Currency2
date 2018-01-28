package com.johacks;

public class Wallet {

	private String name;
	
	public Wallet(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Wallet [name=" + name + "]" ;
	}
	
	
}
