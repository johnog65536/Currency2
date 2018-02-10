package com.johacks;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Miner {
	private static final Logger logger = LogManager.getLogger("MinerLogger");
	private final GsonBuilder builder = new GsonBuilder();
	private final Gson gson = builder.create();

	private final BlockChain blockChain;
	private final KeyPair minerKey;	
	private ArrayList<Transaction> wipTransactions;
	
	/** Can create a miner either from an existing BlockChain or from new
	 *  Either way, needs the keypair for miner fees
	 * @param blockChain
	 * @param minerKey
	 */
	public Miner(BlockChain blockChain,KeyPair minerKey) {
		this.blockChain=blockChain;
		this.minerKey=minerKey;
		wipTransactions=new ArrayList<Transaction>();

	}

	/** Can create a miner either from an existing BlockChain or from new
	 *  Either way, needs the keypair for miner fees
	 * @param genesysTransaction
	 * @param minerKey
	 */
	public Miner(Transaction genesysTransaction, KeyPair minerKey) {
		this.blockChain=new BlockChain();
		this.minerKey=minerKey;
		wipTransactions=new ArrayList<Transaction>();
		
		// bypasses validation for genesys
		wipTransactions.add(genesysTransaction);
		
		// get block 0 onto the chain
		confirmWipTransactions();
	}
	
	public void addTransaction (Transaction txn) {
		validateTransaction(txn);
		wipTransactions.add(txn);
	}
	
	public void validateTransaction(Transaction txn) {
		// todo check it has an input
		// todo check it has an output
		// todo check the input signature
		// todo check the input is exactly spent
		// todo confirmation fees for this transaction
	}
	
	public void confirmWipTransactions() {
		final Block newBlock = new Block();
		for (Transaction txn: wipTransactions) {
			newBlock.addTransaction(txn);
			final String txnAsJSON = gson.toJson(txn);
			logger.info("WIP txn Confirmed: "+txnAsJSON);
		}
		
		// todo fees for creating a block
		// todo generate a hash with enough zeros via nonce incrementing

		final String blockAsJSON = gson.toJson(newBlock);
		logger.info("Confirmed new block:"+blockAsJSON);
		
		blockChain.addBlock(newBlock);
		
		// clear out WIP
		wipTransactions=new ArrayList<Transaction>();
	}
}
