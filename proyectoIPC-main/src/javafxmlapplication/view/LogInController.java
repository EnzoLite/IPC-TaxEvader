/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class LogInController implements Initializable {
    
    private Acount account;

    @FXML
    private TextField nickNameInput;
    @FXML
    private Label nickNameErrorText;
    @FXML
    private Label passwordErrorText;
    @FXML
    private Button loginButton;
    @FXML
    private Text signupText;
    @FXML
    private Button signupButton;
    @FXML
    private PasswordField passwordInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            account = Acount.getInstance();
        } catch (AcountDAOException e) {
            System.err.println(e);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        
    }    

    @FXML
    private void onTextTyped(KeyEvent event) {
        String text = nickNameInput.getText();
        if(text.contains(" ")) {
            nickNameErrorText.setText("Nickname cannot contain blankspaces");
        }
    }

    @FXML
    private void onButtonPressed(ActionEvent event) {
        String nickname = nickNameInput.getText();
        String password = passwordInput.getText();
        if(nickname.contains(" ")) {
            nickNameErrorText.setText("Nickname cannot contain blankspaces");
            return;
        }
        if(nickname.length() == 0) {
            nickNameErrorText.setText("Nickname field cannot be empty");
            return;
        }
        if(password.length() <= 6) {
            passwordErrorText.setText("Password must have length greater than 6");
            return;
        }
        try{
            boolean login = account.logInUserByCredentials(nickname, password);
            if(login) {
                //LLevar a nueva pantalla
            } else {
                passwordErrorText.setText("Credentials are incorrect, please try again");
            }
        } catch (AcountDAOException e) {
            passwordErrorText.setText("Error trying to log in");
        }
        
        
    }
    
    
    
}
