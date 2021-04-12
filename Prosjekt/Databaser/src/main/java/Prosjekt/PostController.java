package Prosjekt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import SQL.CreateFolder;
import SQL.CreatePost;
import SQL.CreatePostInFolder;
import SQL.CreateUser;
import SQL.SQLConnector;
import SQL.TagInPost;
import SQL.Tags;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller for the post scene. Here you can create posts.
 */
public class PostController {
    CreateUser user;
    SQLConnector connector = new SQLConnector();

    @FXML
    Button backButton, logoutButton;
    @FXML
    ScrollPane folderScrollPane, tagScrollPane;
    @FXML
    TextField titleTextField;
    @FXML
    TextArea contentTextArea;
    @FXML
    Text successfullyAddedText;
    @FXML
    CheckBox anonymousCheckBox;

    ArrayList<String> selectedFolderIDs = new ArrayList<>();
    ArrayList<String> selectedTagIDs = new ArrayList<>();

    /**
     * Sends you back to the main scene if the back button is pressed.
     */
    @FXML
    public void handleBack() {
        try {
            Stage mainStage = (Stage) this.backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("piazza.fxml"));
            Parent root = loader.load();

            PiazzaSceneController controller = loader.getController();
            controller.initData(this.user);

            mainStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Creates a post with the information given in the textfields. It also adds the post in the
     * selected folders, and adds selected tags to the post. PostID is given incrementally, and UserID
     * comes from the logged in user.
     */
    @FXML
    public void post() {
        String title = titleTextField.getText();
        String content = contentTextArea.getText();
        boolean anonymous = anonymousCheckBox.isSelected();

        CreatePost post = new CreatePost(this.user.getUserID(), title, content, anonymous);
        post.insert();
        successfullyAddedText.setText("Post has been added successfully");

        Iterator<String> folderIterator = selectedFolderIDs.iterator();
        while (folderIterator.hasNext()) {
            int folderID = Integer.parseInt(folderIterator.next());
            CreatePostInFolder pif = new CreatePostInFolder(folderID, post.getPostID());
            pif.insert();
        }

        Iterator<String> tagIterator = selectedTagIDs.iterator();
        while (tagIterator.hasNext()) {
            int tagId = Integer.parseInt(tagIterator.next());
            TagInPost tip = new TagInPost(tagId, post.getPostID());
            tip.insert();
        }
    }

    /**
     * Logs out the user and sends you back to the login scene.
     */
    @FXML
    public void logout() {
        try {
            Stage mainStage = (Stage) this.logoutButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            mainStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Displays all tags and gives each tag an eventhandler used for indexing
     * so that it is possible to select tags.
     */
    private void showTags() {
        HBox tagsHBox = new HBox();
        tagsHBox.getChildren().clear();

        ArrayList<Tags> tags = this.connector.getTags();
        Iterator<Tags> tagsIterator = tags.iterator();

        while (tagsIterator.hasNext()) {
            Tags tag = tagsIterator.next();
            String tagString = tag.getTagID() + ": " + tag.getTag();
            TextField tempText = new TextField(tagString);
            tempText.setAlignment(Pos.CENTER);
            tempText.setEditable(false);
            tempText.setPrefWidth(100);
            // tempText.prefColumnCountProperty().bind(tempText.textProperty().length());

            EventHandler<MouseEvent> tagsClickedHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getSource() instanceof TextField) {
                        TextField tagTextField = (TextField) e.getSource();
                        int tagIndex = tagTextField.getText().indexOf(":");
                        String tagID = tagTextField.getText(0, tagIndex);
                        selectTag(tagID);
                        System.out.println(selectedTagIDs);
                        if (tagIsSelected(tagID)) {
                            tagTextField.setStyle("-fx-control-inner-background: #00FFFF");
                        } else {
                            tagTextField.setStyle("-fx-control-inner-background: #FFFFFF");
                        }
                    }
                }
            };

            tempText.addEventHandler(MouseEvent.MOUSE_CLICKED, tagsClickedHandler);
            tagsHBox.getChildren().add(tempText);
        }

        tagsHBox.setSpacing(5);
        tagsHBox.setPadding(new Insets(5));
        tagScrollPane.setContent(tagsHBox);
    }

    /**
     * A check if a tag is already selected. Used to display a blue colour if selected.
     * @param tagID
     * @return
     */
    private boolean tagIsSelected(String tagID) {
        if (this.selectedTagIDs.size() > 0) {
            if (this.selectedTagIDs.contains(tagID)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Method called when the user clicks on a tag.
     * @param tagID
     */
    private void selectTag(String tagID) {
        if (this.selectedTagIDs.contains(tagID)) {
            this.selectedTagIDs.remove(tagID);
        } else {
            this.selectedTagIDs.add(tagID);
        }
    }
    /**
     * Displays all folders and gives each folder an eventhandler used for indexing
     * so that it is possible to select folders.
     */
    private void showFolders() {
        HBox folderHBox = new HBox();
        folderHBox.getChildren().clear();

        ArrayList<CreateFolder> folders = this.connector.getFolders();
        Iterator<CreateFolder> folderIterator = folders.iterator();
        folderIterator.next();

        while (folderIterator.hasNext()) {
            CreateFolder folder = folderIterator.next();
            String folderString = folder.getFolderID() + ": " + folder.getTitle();
            TextField tempText = new TextField(folderString);
            tempText.setAlignment(Pos.CENTER);
            tempText.setEditable(false);
            tempText.setPrefWidth(75);
            // tempText.prefColumnCountProperty().bind(tempText.textProperty().length());

            EventHandler<MouseEvent> folderClickedHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getSource() instanceof TextField) {
                        TextField folderTextField = (TextField) e.getSource();
                        int folderIndex = folderTextField.getText().indexOf(":");
                        String folderID = folderTextField.getText(0, folderIndex);
                        selectFolder(folderID);
                        System.out.println(selectedFolderIDs);
                        if (folderIsSelected(folderID)) {
                            folderTextField.setStyle("-fx-control-inner-background: #00FFFF");
                        } else {
                            folderTextField.setStyle("-fx-control-inner-background: #FFFFFF");
                        }

                    }
                }
            };

            tempText.addEventHandler(MouseEvent.MOUSE_CLICKED, folderClickedHandler);
            folderHBox.getChildren().add(tempText);
        }

        folderHBox.setSpacing(5);
        folderHBox.setPadding(new Insets(5));
        folderScrollPane.setContent(folderHBox);
    }

    /**
     * A method called when user clicks on a folder.
     * @param folderID
     */
    private void selectFolder(String folderID) {
        if (this.selectedFolderIDs.contains(folderID)) {
            this.selectedFolderIDs.remove(folderID);
        } else {
            this.selectedFolderIDs.add(folderID);
        }
    }

    /**
     * Checks if a folder is already selected.
     * @param folderID
     * @return
     */
    private boolean folderIsSelected(String folderID) {
        if (this.selectedFolderIDs.size() > 0) {
            if (this.selectedFolderIDs.contains(folderID)) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }
    /**
     * Initializing the scene with information from the main scene.
     * @param user
     */
    public void initData(CreateUser user) {
        this.user = user;
        this.anonymousCheckBox.setVisible(!this.user.getInstructor());
        showFolders();
        showTags();
    }
}
