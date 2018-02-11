package com.johacks;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Wallet {
	private final HashMap<String,KeyPair> keyPairs = new HashMap<String,KeyPair>();
	
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

	public KeyPair generateKeyPair(String name) throws NoSuchAlgorithmException {
		final KeyPair keypair = new KeyPair(name);
		keyPairs.put(name,keypair);
		return keypair;
	}
	
	@Override
	public String toString() {
		return "Wallet [name=" + name + "]" ;
	}

	public KeyPair getKeyPair(String name) {
		return keyPairs.get(name);
	}
	
	
}
