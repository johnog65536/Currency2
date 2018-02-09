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
	
    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	logger.info("Working");
    	
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

    	
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();
    	
		String x = gson.toJson(genesysTransaction);
		logger.info(x);
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
