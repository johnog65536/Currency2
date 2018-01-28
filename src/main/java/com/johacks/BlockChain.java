package com.johacks;

import java.util.ArrayList;

public class BlockChain {
	private final ArrayList<Block> blocks = new ArrayList<Block>();
	
	public void addBlock(Block block) {
		blocks.add(block);
	}
}
