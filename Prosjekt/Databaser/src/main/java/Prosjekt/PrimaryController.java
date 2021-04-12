package Prosjekt;

import java.io.IOException;

import SQL.CreateUser;
import SQL.SQLConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    public TextField emailTextField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button loginButton, signupButton;

    SQLConnector connector = new SQLConnector();

    CreateUser user;

    /**
     * Uses the text from the two textfields and checks if there exists an user with the 
     * corresponding email and password. If so, it logs in the user.
     * @throws IOException
     */
    @FXML
    private void login() throws IOException {

        if (this.connector.loginCheck(emailTextField.getText(), passwordField.getText())) {

            this.user = this.connector.userLoggedIn(emailTextField.getText(), passwordField.getText());
            
            try {
            Stage mainStage = (Stage) this.loginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("piazza.fxml"));
            Parent root = loader.load();

            PiazzaSceneController controller = loader.getController();
            controller.initData(user);

            mainStage.setScene(new Scene(root));

            } catch (IOException e) {
                System.err.println(e);
            }

            //App.setRoot("piazza");
        }
        else {
            System.out.println("Wrong password or email");
        }
        
    }

    /**
     * When you press the sign up button, it sends you to the signup scene
     * @throws IOException
     */
    @FXML
    private void signup() throws IOException {

        try {
            Stage mainStage = (Stage) this.loginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent root = loader.load();

            mainStage.setScene(new Scene(root));

            } catch (IOException e) {
                System.err.println(e);
            }


        //App.setRoot("signup");

    }

}
