import java.util.ArrayList;
import java.util.List;

public class Network {
    private List<Node> nodes;
    private Blockchain blockchain;

    public Network() {
        this.nodes = new ArrayList<>();
        this.blockchain = new Blockchain();
    }

    public void addNode(Node node) {
        nodes.add(node);
        node.setBlockchain(blockchain);
    }

    public void castVote(String voterId, String candidateName) {
        Vote vote = new Vote(voterId, candidateName);
        Block block = new Block(blockchain.getLatestBlock().getHash(), vote);
        
        // Mine the block
        mineBlock(block);

        // Add the block to the blockchain
        blockchain.addBlock(block);

        // Synchronize all nodes
        synchronizeNodes();
    }

    private void mineBlock(Block block) {
        if (!nodes.isEmpty()) {
            nodes.get(0).mineBlock(block);
        } else {
            System.out.println("No nodes available to mine the block.");
        }
    }

    private void synchronizeNodes() {
        List<Block> chain = blockchain.getChain();
        for (Node node : nodes) {
            node.updateBlockchain(chain);
        }
    }

    public String getBlockchainAsString() {
        return blockchain.toString();
    }

    public boolean verifyBlockchain() {
        return blockchain.isChainValid();
    }
}

