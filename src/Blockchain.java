import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        chain = new ArrayList<>();
        // Create the genesis block
        chain.add(new Block("0", new Vote("Genesis", "Genesis")));
    }

    public void addBlock(Block block) {
        chain.add(block);
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Check if the current block's hash is correctly calculated
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Current block hash is invalid");
                return false;
            }

            // Check if the current block's previousHash matches the hash of the previous block
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Previous hash link is broken");
                return false;
            }

            // Check if the hash meets the difficulty requirement (starts with "0000")
            if (!currentBlock.getHash().startsWith("0000")) {
                System.out.println("Block hasn't been mined properly");
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Block block : chain) {
            sb.append(block).append("\n");
        }
        return sb.toString();
    }

    public List<Block> getChain() {
        return new ArrayList<>(chain);
    }
}

