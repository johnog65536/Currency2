package com.johacks;

public class KeyPair {
	final private String name;
	final private String publicKey;
	final private String privateKey;
	
	public KeyPair(String name) {
		this.name=name;
		
		// todo - actual keys not strings
		publicKey="Public:"+name;
		privateKey="Private:"+name;
	}

	public String getName() {
		return name;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}
	
	public String toString() {
		return "KeyPair: " + getPublicKey(); 
	}
}
