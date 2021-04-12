package Prosjekt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import SQL.CreateFolder;
import SQL.CreatePost;
import SQL.CreateUser;
import SQL.FollowupDiscussion;
import SQL.InstructorAnswer;
import SQL.PostRead;
import SQL.SQLConnector;
import SQL.StudentAnswer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * Controller class for the main scene.
 */

public class PiazzaSceneController implements Initializable {

    SQLConnector connector = new SQLConnector();
    ArrayList<CreatePost> posts = new ArrayList<>();
    int postIndex;
    @FXML
    Button logoutButton, statisticsButton, postButton, createFolderButton, postStudentAnswerButton,
            postInstructorAnswerButton, postFollowUpDiscussionButton;
    @FXML
    TextField searchForPostTextField, createFolderTextField;
    @FXML
    TextArea postTextArea, studentAnswerTextArea, instructorAnswerTextArea, followupdiscussionsTextArea;
    @FXML
    Text postIdText, usernameText, studentAnswerText, instructorAnswerText, loggedInUserText, loggedInInstructorText,
            postTitleText;
    @FXML
    ScrollPane postScrollPane, folderScrollPane, followupdiscussionsScrollPane;
    @FXML
    CheckBox studentAnswerAnonymousCheckBox, followUpDiscussionCheckBox;

    ArrayList<String> selectedFolderIDs = new ArrayList<>();
    CreateUser user;

    /**
     * Changes the scene to the login scene, and logs the user out.
     * 
     * @throws IOException
     */

    @FXML
    private void logout() throws IOException {

        try {
            Stage mainStage = (Stage) this.logoutButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            mainStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.err.println(e);
        }

        // App.setRoot("login");
    }

    /**
     * Changes the scene to the post scene, and changes the controller to
     * PostController. Uses the initData function to send the currently logged in
     * user-object to the next controller.
     * 
     * @throws IOException
     */
    @FXML
    private void newPost() throws IOException {
        try {
            Stage mainStage = (Stage) this.postButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("post.fxml"));
            Parent root = loader.load();

            PostController controller = loader.getController();
            controller.initData(this.user);
            mainStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Changes the scene to statistics.fxml, and changes the controller to
     * StatisticsController. Uses the initData function to send the currently logged
     * in user-object to the next controller. This scene shows the number of posts
     * read and posts created for all users.
     */
    @FXML
    public void statistics() {
        try {
            Stage mainStage = (Stage) this.statisticsButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
            Parent root = loader.load();

            StatisticsController controller = loader.getController();
            controller.initData(this.user);
            mainStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Currently a stub method.
     * 
     * @throws IOException
     */
    @FXML
    private void invite() throws IOException {

    }

    /**
     * Creates a folder using the text in the TextField. Must have a title with
     * length > 0. The method also inserts the created folder in the SQL database.
     */
    @FXML
    private void createFolder() {
        // CourseNumber and ParentfolderNumber is hardcoded for the time being.
        if (createFolderTextField.getText().length() > 0) {
            CreateFolder folder = new CreateFolder(createFolderTextField.getText(), 1, 0);
            folder.insert();
            createFolderTextField.setText("");
            showFolders();
        } else {
            createFolderTextField.setPromptText("Must have a name");
        }
    }

    /**
     * Creates or updates the StudentAnswer corresponding to the selected post.
     * After the creation/update it refreshes the textfield incase the
     * anonymousCheckBox has been selected.
     */
    @FXML
    private void postStudentAnswer() {
        StudentAnswer studentAnswer = new StudentAnswer(this.postIndex, this.user.getUserID(),
                studentAnswerTextArea.getText(), studentAnswerAnonymousCheckBox.isSelected());
        studentAnswer.insert();
        showStudentAnswer(this.postIndex);

    }

    /**
     * Creates or updates the InstructorAnswer corresponding to the selected post.
     * After the creation/update it refreshes the textfield.
     */
    @FXML
    private void postInstructorAnswer() {
        InstructorAnswer instructorAnswer = new InstructorAnswer(this.postIndex, this.user.getUserID(),
                instructorAnswerTextArea.getText());
        instructorAnswer.insert();
        showInstructorAnswer(this.postIndex);
    }

    /**
     * Creates a new followupdiscussion, updates the sql database and refreshes the scene.
     */
    @FXML
    private void postFollowUpDiscussion() {
        FollowupDiscussion fud = new FollowupDiscussion(this.postIndex, this.connector.getNextSequenceNumber(this.postIndex), this.user.getUserID(),
        followupdiscussionsTextArea.getText(), followUpDiscussionCheckBox.isSelected(), 0);
        fud.insert();
        followupdiscussionsTextArea.clear();
        showFollowUpDiscussion(this.postIndex);
    }

    /**
     * Shows all followupdiscussions related to the selected post.
     * @param postID
     */
    private void showFollowUpDiscussion(int postID) {
        VBox root = new VBox();
        ArrayList<FollowupDiscussion> fud = this.connector.getFollowupDiscussionsFromPostID(postID);
        Iterator<FollowupDiscussion> fudIterator = fud.iterator();
        while (fudIterator.hasNext()) {
            Text userName = new Text();
            FollowupDiscussion tempfud = fudIterator.next();
            TextField tempTextField = new TextField(tempfud.getContent());
            if (tempfud.getAnonymous()) {
                userName.setText("Anonymous");
            }
            else {
                userName.setText("" + this.connector.getUserNameFromUserID(tempfud.getUserID()));
            }
            tempTextField.setPrefWidth(220);
            userName.setTextAlignment(TextAlignment.CENTER);
            root.getChildren().addAll(tempTextField, userName);
            
        }
        root.setPrefWidth(220);
        root.getChildren().add(followupdiscussionsTextArea);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        followupdiscussionsScrollPane.setContent(root);
        followupdiscussionsScrollPane.setPannable(true);
    }

    /**
     * Shows the StudentAnswer corresponding to the selected postID. If the post
     * currently does not have an StudentAnswer, it sets the TextFields to blank. It
     * also checks if the student wants to be anonymous.
     * 
     * @param postID
     */
    private void showStudentAnswer(int postID) {
        StudentAnswer sa = this.connector.getStudentAnswerFromPostID(postID);
        if (sa != null) {
            studentAnswerTextArea.setText(sa.getContent());
            if (sa.getAnonymous()) {
                studentAnswerText.setText("Anonymous");
            } else {
                studentAnswerText.setText(this.connector.getUserNameFromUserID(sa.getUserID()));
            }
        } else {
            studentAnswerTextArea.setText("");
            studentAnswerText.setText("");
        }
    }

    /**
     * Shows the InstructorAnswer corresponding to the selected postID. If the post
     * currently does not have an InstructorAnswer, it sets the TextFields to blank.
     * 
     * @param postID
     */
    private void showInstructorAnswer(int postID) {
        InstructorAnswer ia = this.connector.getInstructorAnswerFromPostID(postID);
        if (ia != null) {
            instructorAnswerTextArea.setText(ia.getContent());
            instructorAnswerText.setText(this.connector.getUserNameFromUserID(ia.getUserID()));
        } else {
            instructorAnswerTextArea.setText("");
            instructorAnswerText.setText("");
        }
    }

    /**
     * This method all posts that matches with the text in the
     * searchForPostTextField and currently selected folders. If no filter is
     * applied, it gets all posts.
     */
    @FXML
    private void getFilteredPosts() {
        if (selectedFolderIDs.size() == 0) {
            this.posts = connector.getFilteredPosts(searchForPostTextField.getText());
            this.showPosts();
        } else {
            this.posts = connector.getPostsInFolder(selectedFolderIDs, searchForPostTextField.getText());
            this.showPosts();
        }
    }

    /**
     * This method takes all the posts from getFilteredPosts and displays them in
     * the postScrollPane. It also adds a MouseEvent for each TextField in the
     * ScrollPane, so that when the user clicks a TextField, it displays the
     * corresponding content within the selected post.
     */
    private void showPosts() {
        VBox root = new VBox();
        Iterator<CreatePost> postsIterator = this.posts.iterator();
        while (postsIterator.hasNext()) {
            CreatePost post = postsIterator.next();
            String postString = post.titleString();
            TextField postTextField = new TextField(postString);
            postTextField.setEditable(false);

            EventHandler<MouseEvent> postClickedHandler = new EventHandler<MouseEvent>() {
                /**
                 * MouseEvent that is bound to each TextField. It gets the postID from the start
                 * of the string and saves it so that the correct post is displayed when clicked
                 * on.
                 */
                @Override
                public void handle(MouseEvent e) {
                    if (e.getSource() instanceof TextField) {
                        TextField postText = (TextField) e.getSource();
                        int stringIndex = postText.getText().indexOf(":");
                        int postIndex = Integer.parseInt(postText.getText(0, stringIndex));
                        setPostIndex(postIndex);
                        showPost(postIndex);
                        showInstructorAnswer(postIndex);
                        showStudentAnswer(postIndex);
                        showFollowUpDiscussion(postIndex);
                    }
                }
            };

            postTextField.addEventHandler(MouseEvent.MOUSE_CLICKED, postClickedHandler);
            postTextField.setPrefWidth(200);
            root.getChildren().add(postTextField);
        }
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        postScrollPane.setContent(root);
        postScrollPane.setPannable(true);

    }

    /**
     * Method used for indexing posts within the postClickedHandler, so that the
     * correct post can be displayed.
     * 
     * @param postIndex
     */
    private void setPostIndex(int postIndex) {
        this.postIndex = postIndex;
    }

    /**
     * This method is used for displaying selected posts. Uses the postIndex from
     * the MouseEvent bound to the TextFields in postScrollPane and iterates through
     * the ArrayList of posts to find the one with the corresponding postID. It then
     * displays the selected post. It also updates the PostRead table, for the
     * currently logged in user.
     * 
     * @param postIndex
     */
    private void showPost(int postIndex) {
        PostRead pr = new PostRead(this.user.getUserID(), this.postIndex);
        pr.insert();
        CreatePost post = null;
        Iterator<CreatePost> postIterator = this.posts.iterator();
        while (postIterator.hasNext()) {
            CreatePost tempPost = postIterator.next();
            if (tempPost.getPostID() == postIndex) {
                post = tempPost;
            }
        }
        if (post != null) {
            postTitleText.setText(post.getTitle());
            postTextArea.setText(post.getContent());
            postIdText.setText((Integer.toString(post.getPostID())));
            if (post.getAnonymous()) {
                usernameText.setText("Anonymous");
            } else {
                usernameText.setText(connector.getUserNameFromUserID(post.getUserID()));
            }
        }
    }

    /**
     * Method used for displaying folders.
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

            EventHandler<MouseEvent> folderClickedHandler = new EventHandler<MouseEvent>() {
                /**
                 * Handler used to get the folderID from the folder when clicked. Uses the
                 * methods selectFolder and folderIsSelected to keep track of which folders are
                 * selected. In this controller this is used to filter posts based on which
                 * folders they are in. If a folder is selected, the corresponding TextField is
                 * coloured blue, and if not it's coloured white.
                 */
                @Override
                public void handle(MouseEvent e) {
                    if (e.getSource() instanceof TextField) {
                        TextField folderTextField = (TextField) e.getSource();
                        int folderIndex = folderTextField.getText().indexOf(":");
                        String folderID = folderTextField.getText(0, folderIndex);
                        selectFolder(folderID);
                        if (folderIsSelected(folderID)) {
                            folderTextField.setStyle("-fx-control-inner-background: #00FFFF");
                        } else {
                            folderTextField.setStyle("-fx-control-inner-background: #FFFFFF");
                        }
                        getFilteredPosts();
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
     * Method to add/remove folderIDs from the ArrayList selectedFolderIDs, to keep
     * track of which folders are selected. Used in the EventHandler in showFolders.
     * 
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
     * Checks if the clicked folder has already been selected.
     * 
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
     * Initializes the controller by displaying all posts and folders.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.posts = connector.getPosts();
        showPosts();
        showFolders();

    }

    /**
     * Method used for sending the currently logged in user from the login scene.
     * Hides or displays different buttons depending on if the user is an instructor
     * or a student.
     * 
     * @param user
     */
    public void initData(CreateUser user) {
        this.user = user;
        loggedInUserText.setText(this.user.getName());
        boolean instructor = this.user.getInstructor();
        if (this.user.getInstructor()) {
            loggedInInstructorText.setText("Instructor");
        } else {
            loggedInInstructorText.setText("Student");
        }
        statisticsButton.setVisible(instructor);
        statisticsButton.setDisable(!instructor);
        studentAnswerTextArea.setEditable(!instructor);
        instructorAnswerTextArea.setEditable(instructor);
        createFolderButton.setVisible(instructor);
        createFolderTextField.setVisible(instructor);
        postInstructorAnswerButton.setVisible(instructor);
        postStudentAnswerButton.setVisible(!instructor);
        studentAnswerAnonymousCheckBox.setVisible(!instructor);
        followUpDiscussionCheckBox.setVisible(!instructor);
    }
}