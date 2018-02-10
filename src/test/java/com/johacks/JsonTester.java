package com.johacks;

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
	
    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	logger.info("Working");
    	
    	final Wallet utilityWallet = new Wallet("Utility Wallet");
    	final KeyPair genesysKey = utilityWallet.generateKeyPair("genesysKeyPair");
    	final KeyPair minerKey = utilityWallet.generateKeyPair("minerKeyPair");
    	
    	final Wallet fromWallet = new Wallet("My Source Wallet");
    	final KeyPair fromKey = fromWallet.generateKeyPair("myFromKeyPair");
    	
    	final Wallet toWallet = new Wallet("My Destination Wallet");    	
    	final KeyPair toKey0 = toWallet.generateKeyPair("myToKeyPair 0");
    	final KeyPair toKey1 = toWallet.generateKeyPair("myToKeyPair 1");
    	    	
    	final Transaction genesysTransaction = new Transaction();
    	TransactionOutput genesysOutput = new TransactionOutput(0,1000,genesysKey.getPublicKey());
    	genesysTransaction.addOutput(genesysOutput);
    	
    	final Miner miner = new Miner(genesysTransaction,minerKey);
  
		final String genesysJson = gson.toJson(genesysTransaction);
		
		final Transaction firstTransaction = new Transaction();
    	final TransactionInput input0= new TransactionInput(0,genesysTransaction.getOutput(0));
    	firstTransaction.addInput(input0);
    	
    	final TransactionOutput output0 = new TransactionOutput(0,50,toKey0.getPublicKey());
    	final TransactionOutput output1 = new TransactionOutput(1,50,toKey1.getPublicKey());
    	firstTransaction.addOutput(output0);
    	firstTransaction.addOutput(output0);
    	
		final String firstTxnJson = gson.toJson(genesysTransaction);
		logger.info(firstTxnJson);
		
    	miner.addTransaction(firstTransaction);
    	miner.confirmWipTransactions();

    	moveMoney(genesysTransaction,toKey0,toKey1,miner);
    	moveMoney(genesysTransaction,toKey0,toKey1,miner);
    	moveMoney(firstTransaction,toKey0,toKey1,miner);

    	miner.confirmWipTransactions();

    	logger.info("completed second set of mining");

		
    }
    
    
    
        
    private void moveMoney(Transaction genesysTransaction,KeyPair toKey0,KeyPair toKey1,Miner miner) {
    	final Transaction myTransaction = new Transaction();
    	
    	final TransactionInput input0= new TransactionInput(0,genesysTransaction.getOutput(0));
    	myTransaction.addInput(input0);
    	
    	final TransactionOutput output0 = new TransactionOutput(0,10,toKey0.getPublicKey());
    	final TransactionOutput output1 = new TransactionOutput(1,10,toKey1.getPublicKey());
    	myTransaction.addOutput(output0);
    	myTransaction.addOutput(output0);

    	miner.addTransaction(myTransaction);
    	
 
    	final String txnJson = gson.toJson(myTransaction);
		logger.info(txnJson);
	
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
