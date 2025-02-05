import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Node {
    private String name;
    private Blockchain blockchain;

    public Node(String name) {
        this.name = name;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void updateBlockchain(List<Block> chain) {
        this.blockchain = new Blockchain();
        for (Block block : chain) {
            this.blockchain.addBlock(block);
        }
    }

    public void mineBlock(Block block) {
        int nonce = 0;
        String hash = block.calculateHash();
        while (!hash.startsWith("0000")) {
            nonce++;
            block.setNonce(nonce);
            hash = block.calculateHash();
        }
        block.setHash(hash);
        System.out.println("Block mined by " + name + ": " + hash);
    }

    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

