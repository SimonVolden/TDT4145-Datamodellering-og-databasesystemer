package Prosjekt;

import java.io.IOException;

import SQL.CreateUser;
import SQL.SQLConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller for the signup scene.
 */
public class SignupController {
    @FXML TextField usernameTextField, emailTextField;
    @FXML PasswordField passwordField1, passwordField2;
    @FXML Text passwordNotEqualText;
    @FXML CheckBox instructorCheckbox;
    @FXML Button signupButton, backButton;

    /**Checks first if the email is already in use, if not it creates an user if the two passwordfields match.
     * The user object is injected into the sql database.
     * @throws IOException
     */
    @FXML
    private void handleSignup() throws IOException {

        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField1.getText();
        String password2 = passwordField2.getText();
        boolean instructor = instructorCheckbox.isSelected();
        SQLConnector connector = new SQLConnector();

        if (password.equals(password2)) {
            if (connector.signupCheck(email)) {
                CreateUser user = new CreateUser(username, email, password, instructor);
                passwordNotEqualText.setText("");
                App.setRoot("login");
                handleBack();
            }
            else {
                passwordNotEqualText.setText("An user with this email currently exists");
            }
        }
        else {
            passwordNotEqualText.setText("Passwords do not match");
        }
    }

    /**
     * Sends you back to the login scene if the back button is pressed.
     * @throws IOException
     */
    @FXML
    private void handleBack() throws IOException {
        
        try {
            Stage mainStage = (Stage) this.backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            mainStage.setScene(new Scene(root));

            } catch (IOException e) {
                System.err.println(e);
            }
        
        //App.setRoot("login");
    }
}

