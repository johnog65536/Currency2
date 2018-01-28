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
    	final Wallet wallet = new Wallet("MyTestWallet");
    	
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
