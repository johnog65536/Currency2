package com.johacks;

import java.security.NoSuchAlgorithmException;

public class KeyPair {
	final private String name;
	final private String publicKey;
	final private String privateKey;
	
	public KeyPair(String name) throws NoSuchAlgorithmException {
		this.name=name;
		
		// FIXME - actual keys not strings
		java.security.KeyPair pair = CryptoUtils.getKeyPair();
		publicKey=CryptoUtils.utfToBase64(pair.getPublic().getEncoded());
		privateKey=CryptoUtils.utfToBase64(pair.getPrivate().getEncoded());
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
