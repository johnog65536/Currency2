package com.johacks;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AppTest  extends TestCase
{
	private static final Logger logger = LogManager.getLogger("TestLogger");

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	logger.info("Started testing");
    	final Wallet genesysWallet = new Wallet("Genesys Wallet");
    	final Wallet fromWallet = new Wallet("My Source Wallet");
    	final Wallet toWallet = new Wallet("My Destination Wallet");    	
    	logger.info("wallets created: "); 

    	final KeyPair genesysKey = genesysWallet.generateKeyPair("genesysKeyPair");
    	final KeyPair fromKey = fromWallet.generateKeyPair("myFromKeyPair");
    	final KeyPair toKey0 = toWallet.generateKeyPair("myToKeyPair 0");
    	final KeyPair toKey1 = toWallet.generateKeyPair("myToKeyPair 1");
    	
    	logger.info("Keys created");
    	
    	final Transaction genesysTransaction = new Transaction();
    	TransactionOutput genesysOutput = new TransactionOutput(0,1000,genesysKey.getPublicKey());
    	genesysTransaction.addOutput(genesysOutput);
    	
    	final Miner miner = new Miner();
    	miner.initialise(genesysTransaction);
    	
    	logger.info("miner intialised with genesys transaction");
    	
    	final Transaction firstTransaction = new Transaction();
    	final TransactionInput input0= new TransactionInput(0,genesysTransaction.getOutput(0));
    	firstTransaction.addInput(input0);
    	
    	final TransactionOutput output0 = new TransactionOutput(0,50,toKey0.getPublicKey());
    	final TransactionOutput output1 = new TransactionOutput(1,50,toKey1.getPublicKey());
    	firstTransaction.addOutput(output0);
    	firstTransaction.addOutput(output0);
    	
    	logger.info("first transaction created");
    	
    	miner.addTransaction(firstTransaction);
    	miner.mine();

    	logger.info("completed first set of mining");
    	
    	moveMoney(genesysTransaction,toKey0,toKey1,miner);
    	moveMoney(genesysTransaction,toKey0,toKey1,miner);
    	moveMoney(firstTransaction,toKey0,toKey1,miner);

    	miner.mine();

    	
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
    	
    	logger.info("moneyMoved");
    }
    
    
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
}
