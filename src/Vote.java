public class Vote {
    private String voterId;
    private String candidateName;

    public Vote(String voterId, String candidateName) {
        this.voterId = voterId;
        this.candidateName = candidateName;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voterId='" + voterId + '\'' +
                ", candidateName='" + candidateName + '\'' +
                '}';
    }
}

