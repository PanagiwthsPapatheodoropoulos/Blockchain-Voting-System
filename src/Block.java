import java.util.Date;

public class Block {
    private String hash;
    private String previousHash;
    private Vote vote;
    private long timeStamp;
    private int nonce;

    public Block(String previousHash, Vote vote) {
        this.previousHash = previousHash;
        this.vote = vote;
        this.timeStamp = new Date().getTime();
        this.nonce = 0;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = Node.applySha256(
                previousHash +
                Long.toString(timeStamp) +
                Integer.toString(nonce) +
                vote.toString()
        );
        return calculatedhash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "Block{" +
                "hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", vote=" + vote +
                ", timeStamp=" + timeStamp +
                ", nonce=" + nonce +
                '}';
    }
}

