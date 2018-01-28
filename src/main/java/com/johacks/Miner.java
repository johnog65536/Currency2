package com.johacks;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Miner {
	private static final Logger logger = LogManager.getLogger("TestLogger");
	private BlockChain blockChain;
	
	private Transaction genesysTransaction =null;
	
	private ArrayList<Transaction> wipTransactions=null;
	
	public void initialise(Transaction gen) {
		blockChain=new BlockChain();
		genesysTransaction=gen;
		
		final Block firstBlock = new Block();
		firstBlock.addTransaction(genesysTransaction);
		blockChain.addBlock(firstBlock);
		logger.info("added first block to the chain");
		
		wipTransactions=new ArrayList<Transaction>();
	}
	
	public void addTransaction (Transaction txn) {
		validateTransaction(txn);
		wipTransactions.add(txn);
	}
	
	public void validateTransaction(Transaction txn) {
		// check it has an input
		// check it has an output
		// check the input signature
		// check the input is exactly spent
		// todo miner fees
	}
	
	public void mine() {
		final Block newBlock = new Block();
		for (Transaction txn: wipTransactions) {
			newBlock.addTransaction(txn);
			logger.info("mined: "+txn);
		}
		
		//todo miner fees
		blockChain.addBlock(newBlock);
		
		logger.info("added new block to the chain");
		
		wipTransactions=new ArrayList<Transaction>();
	}
}
