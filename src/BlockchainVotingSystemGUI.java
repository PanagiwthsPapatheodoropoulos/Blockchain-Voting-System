import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BlockchainVotingSystemGUI {
    private Stage stage;
    private Network network;
    private VBox root;
    private TextArea blockchainView;

    public BlockchainVotingSystemGUI(Stage stage) {
        this.stage = stage;
        this.network = new Network();
        createUI();
    }

    private void createUI() {
        root = new VBox(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Blockchain Voting System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
            createAddNodeTab(),
            createCastVoteTab(),
            createViewBlockchainTab()
        );

        root.getChildren().addAll(titleLabel, tabPane);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Blockchain Voting System");
    }

    private Tab createAddNodeTab() {
        Tab tab = new Tab("Add Node");
        tab.setClosable(false);

        VBox content = new VBox(10);
        content.setPadding(new Insets(10));

        TextField nodeNameField = new TextField();
        nodeNameField.setPromptText("Enter node name");

        Button addNodeButton = new Button("Add Node");
        addNodeButton.setOnAction(e -> {
            String nodeName = nodeNameField.getText();
            if (!nodeName.isEmpty()) {
                network.addNode(new Node(nodeName));
                nodeNameField.clear();
                showAlert("Node added successfully: " + nodeName);
            } else {
                showAlert("Please enter a node name");
            }
        });

        content.getChildren().addAll(
            new Label("Node Name:"),
            nodeNameField,
            addNodeButton
        );

        tab.setContent(content);
        return tab;
    }

    private Tab createCastVoteTab() {
        Tab tab = new Tab("Cast Vote");
        tab.setClosable(false);

        VBox content = new VBox(10);
        content.setPadding(new Insets(10));

        TextField voterIdField = new TextField();
        voterIdField.setPromptText("Enter voter ID");

        TextField candidateNameField = new TextField();
        candidateNameField.setPromptText("Enter candidate name");

        Button castVoteButton = new Button("Cast Vote");
        castVoteButton.setOnAction(e -> {
            String voterId = voterIdField.getText();
            String candidateName = candidateNameField.getText();
            if (!voterId.isEmpty() && !candidateName.isEmpty()) {
                network.castVote(voterId, candidateName);
                voterIdField.clear();
                candidateNameField.clear();
                showAlert("Vote cast successfully!");
                updateBlockchainView();
            } else {
                showAlert("Please enter both voter ID and candidate name");
            }
        });

        content.getChildren().addAll(
            new Label("Voter ID:"),
            voterIdField,
            new Label("Candidate Name:"),
            candidateNameField,
            castVoteButton
        );

        tab.setContent(content);
        return tab;
    }

    private Tab createViewBlockchainTab() {
        Tab tab = new Tab("View Blockchain");
        tab.setClosable(false);

        VBox content = new VBox(10);
        content.setPadding(new Insets(10));

        blockchainView = new TextArea();
        blockchainView.setEditable(false);
        blockchainView.setWrapText(true);

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> updateBlockchainView());

        Button verifyButton = new Button("Verify Blockchain");
        verifyButton.setOnAction(e -> {
            boolean isValid = network.verifyBlockchain();
            showAlert("Blockchain is " + (isValid ? "valid" : "invalid"));
        });

        HBox buttonBox = new HBox(10, refreshButton, verifyButton);

        content.getChildren().addAll(blockchainView, buttonBox);

        tab.setContent(content);
        return tab;
    }

    private void updateBlockchainView() {
        blockchainView.setText(network.getBlockchainAsString());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void show() {
        stage.show();
    }
}

