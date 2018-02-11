package com.johacks;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JsonTester extends TestCase {

	private static final Logger logger = LogManager.getLogger("JsonLogger");
	private final GsonBuilder builder = new GsonBuilder();
	private final Gson gson = builder.create();
	
	private final static String GENESYS_KEY_PAIR_LABEL = "genesysKeyPair";
    /**
     * Rigourous Test :-)
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     * @throws UnsupportedEncodingException 
     * @throws SignatureException 
     * @throws InvalidKeyException 
     */
    public void testApp() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException, InvalidKeySpecException
    {
    	logger.info("Working");
    	
    	final Wallet utilityWallet = new Wallet("Utility Wallet");    	
    	final KeyPair genesysKey = utilityWallet.generateKeyPair(GENESYS_KEY_PAIR_LABEL);
    	final KeyPair minerKey = utilityWallet.generateKeyPair("minerKeyPair");
    	
    	final Wallet fromWallet = new Wallet("My Source Wallet");
    	final KeyPair fromKey = fromWallet.generateKeyPair("myFromKeyPair");
    	
    	final Wallet toWallet = new Wallet("My Destination Wallet");    	
    	final KeyPair toKey0 = toWallet.generateKeyPair("myToKeyPair 0");
    	final KeyPair toKey1 = toWallet.generateKeyPair("myToKeyPair 1");
    	    	
    	final Transaction genesysTransaction = new Transaction();
    	//todo supply proof
    	final TransactionOutput genesysOutput = new TransactionOutput(0,1000,genesysKey.getPublicKey(),"");
    	genesysTransaction.addOutput(genesysOutput);
    	
    	final Miner miner = new Miner(genesysTransaction,minerKey);
		
		final Transaction firstTransaction = new Transaction();
    	final TransactionInput input0= new TransactionInput(0,genesysTransaction.getOutput(0));
    	firstTransaction.addInput(input0);
    	
    	//FIXME supply proof - proof supplied, needs validation
    	//FIXME should hang off input transaction properly, meaning input transaction needs to be stamped with block IDs
    	//needs JSON work or different classes for WIP and Confirmed transactions
    	//that comes after validating the signature
    	final double value0=60;
    	final double value1=40;
    	final String outputProofString0 = getProofString(0,0,0,toKey0.getPublicKey(),utilityWallet,GENESYS_KEY_PAIR_LABEL,value0);
    	final String outputProofString1 = getProofString(0,0,0,toKey1.getPublicKey(),utilityWallet,GENESYS_KEY_PAIR_LABEL,value1);
    	final TransactionOutput output0 = new TransactionOutput(0,value0,toKey0.getPublicKey(),outputProofString0);
    	final TransactionOutput output1 = new TransactionOutput(1,value1,toKey1.getPublicKey(),outputProofString1);
    	
    	firstTransaction.addOutput(output0);
    	firstTransaction.addOutput(output0);		
    	miner.addTransaction(firstTransaction);
    	miner.confirmWipTransactions();

    	moveMoney(utilityWallet,genesysTransaction,toKey0,toKey1,miner);
    	moveMoney(utilityWallet,genesysTransaction,toKey0,toKey1,miner);
    	moveMoney(utilityWallet,firstTransaction,toKey0,toKey1,miner);

    	miner.confirmWipTransactions();

    	logger.info("completed second set of mining");	
    }

    private String getProofString(int blockId, int txnId, int outputId, String destPublic, Wallet wallet, String keyname, double value) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, InvalidKeySpecException {
    	final Proof outputProof=new Proof(blockId,txnId,outputId,destPublic,value);
    	final String destPrivate = wallet.getKeyPair(keyname).getPrivateKey();
    	final String jsonProof = gson.toJson(outputProof);
    	final String outputProofString = CryptoUtils.signMessage(jsonProof,destPrivate);
    	
    	// FIXME - validate signature - needs porting to the miner next and confirm what has been signed (is output or input)
    	final String pubKeyAsString=wallet.getKeyPair(keyname).getPublicKey();
    	final PublicKey pubKey=CryptoUtils.generatePubKey(pubKeyAsString);
    	final boolean validSignature = CryptoUtils.verifySignature(jsonProof, outputProofString, pubKey);
    	logger.info("Signature Validation:"+validSignature);
    	
    	logger.info("Proof: JSON=" + jsonProof + "    signature="+ outputProofString);
    	return outputProofString;
    }
    
    private void moveMoney(Wallet srcWallet, Transaction genesysTransaction,KeyPair toKey0,KeyPair toKey1,Miner miner) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, InvalidKeySpecException {
    	final Transaction myTransaction = new Transaction();
    	
    	final TransactionInput input0= new TransactionInput(0,genesysTransaction.getOutput(0));
    	myTransaction.addInput(input0);
    	
    	double value0=15;
    	double value1=25;
    	
    	//todo supply proof
    	final String outputProofString0 = getProofString(0,0,0,toKey0.getPublicKey(),srcWallet,GENESYS_KEY_PAIR_LABEL,value0);
    	final String outputProofString1 = getProofString(0,0,1,toKey0.getPublicKey(),srcWallet,GENESYS_KEY_PAIR_LABEL,value1);
        
    	final TransactionOutput output0 = new TransactionOutput(0,10,toKey0.getPublicKey(),outputProofString0);
    	final TransactionOutput output1 = new TransactionOutput(1,10,toKey1.getPublicKey(),outputProofString1);
    	myTransaction.addOutput(output0);
    	myTransaction.addOutput(output0);

    	miner.addTransaction(myTransaction);
	
    }
    
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JsonTester( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( JsonTester.class );
    }
    
}
