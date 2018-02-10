package com.johacks;

import java.util.ArrayList;
import java.util.Date;

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
	
	private final static String REQUIRED_HASH_PREFIX = "000";
	
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
		// TODO check it has an input
		// TODO check it has an output
		// TODO check the input signature
		// TODO check the input is exactly spent
		// TODO confirmation fees for this transaction
	}
	
	public void confirmWipTransactions() {
		final Block newBlock = new Block();
		for (Transaction txn: wipTransactions) {
			newBlock.addTransaction(txn);
			final String txnAsJSON = gson.toJson(txn);
			logger.info("WIP txn Confirmed: "+txnAsJSON);
		}
		
		// TODO fees for creating a block
		// TODO abort mining if receive a new block from different miner (threads!)
		boolean mined=false;
		int nonce=-1;
		String hash="";
		newBlock.setMinedBy(minerKey.getPublicKey());
	    String blockAsJSON;;
		while (! mined) {
			nonce++;
			newBlock.setNonce(nonce);
			newBlock.setConfirmationTime(new Date());
			blockAsJSON = gson.toJson(newBlock);
			hash = CryptoUtils.calcHash(blockAsJSON);
			mined= hash.startsWith(REQUIRED_HASH_PREFIX);
		}
		
		newBlock.setHash(hash);
		
		final String minedBlockAsJSON = gson.toJson(newBlock);
		logger.info("Confirmed new block:"+minedBlockAsJSON);
		
		blockChain.addBlock(newBlock);
		
		// clear out WIP
		wipTransactions=new ArrayList<Transaction>();
	}
}
