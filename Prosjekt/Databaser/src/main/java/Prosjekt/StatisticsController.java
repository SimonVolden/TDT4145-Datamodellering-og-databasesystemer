package Prosjekt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import SQL.CreateUser;
import SQL.SQLConnector;
import SQL.Statistics;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the statistics scene.
 */
public class StatisticsController implements Initializable {

    @FXML Button backButton, logoutButton;
    @FXML ScrollPane statisticsScrollPane;
    SQLConnector connector = new SQLConnector();

    CreateUser user;

    /**
     * Sends you back to the main scene if the back button is pressed.
     */
    @FXML
    public void back() {
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
     * Displays all statistics for all users. The statistics contain username, number of posts read
     * and number of posts created, sorted by decreasing number of posts read.
     */
    public void showStatistics() {

        ArrayList<Statistics> statistics = connector.getPostsReadStatistics();
        Iterator<Statistics> statisticsIterator = statistics.iterator();
        VBox root = new VBox();

        TextField t1 = new TextField("Username");
        TextField t2 = new TextField("Posts Read");
        TextField t3 = new TextField("Posts Created");
        t1.setAlignment(Pos.CENTER);
        t2.setAlignment(Pos.CENTER);
        t3.setAlignment(Pos.CENTER);

        HBox titleHBox = new HBox();
        titleHBox.getChildren().addAll(t1, t2, t3);
        root.getChildren().add(titleHBox);

        while (statisticsIterator.hasNext()) {
            Statistics tempStats = statisticsIterator.next();
            String tempUsername = tempStats.getUsername();
            int tempPostsRead = tempStats.getPostsRead();
            int tempPostsCreated = tempStats.getPostsCreated();

            TextField usernameTextField = new TextField(tempUsername);
            TextField tempPostsReadTextField = new TextField(Integer.toString(tempPostsRead));
            TextField tempPostsCreatedTextField = new TextField(Integer.toString(tempPostsCreated));

            usernameTextField.setAlignment(Pos.CENTER);
            tempPostsReadTextField.setAlignment(Pos.CENTER);
            tempPostsCreatedTextField.setAlignment(Pos.CENTER);

            //String statsString = "" + tempStats.getUsername() + ":   " + tempStats.getPostsRead() + "    " + tempStats.getPostsCreated();
            //TextField statsTextField = new TextField(statsString);
            HBox innerRoot = new HBox();
            innerRoot.getChildren().addAll(usernameTextField, tempPostsReadTextField, tempPostsCreatedTextField);
            root.getChildren().add(innerRoot);
        }

        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        statisticsScrollPane.setContent(root);
        statisticsScrollPane.setPannable(true);

    } 

    /**
     * Initializing the logged in user. This method is runned by PiazzaSceneController
     * @param user
     */
    public void initData(CreateUser user) {
        this.user = user;
    }

    /**
     * This method is run when the scene is initialized.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        showStatistics();
        
    }
    
}
