package javafxmlapplication;

import javafx.stage.Stage;
import model.Acount;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LogInController {
    
    public static Acount account;
    
    @FXML
    private AnchorPane back;

    @FXML
    private AnchorPane front;

    @FXML
    private PasswordField inputPass;

    @FXML
    private TextField inputUs;

    @FXML
    private Button logInSelector;

    @FXML
    private Label notMem;

    @FXML
    private Label ps;

    @FXML
    private Button signInSelector;

    @FXML
    private Label title;

    @FXML
    private Label us;

    @FXML
    private Text messages;

    @FXML
    void correctKeyPass(KeyEvent event) {
        String pressed = event.getText();
        System.out.println(pressed);
        if(pressed.contains(" "))
        {
            messages.setFill(Color.RED);
            messages.setText("Space are not allowed in this field");
        }
    }

    @FXML
    void correctKeyUs(KeyEvent event) {
        String pressed = event.getText();
        System.out.println(pressed);
        if(pressed.length() != 0 && !(Character.isDigit(pressed.charAt(0)) || Character.isLetter(pressed.charAt(0))))
        {
            messages.setFill(Color.RED);
            messages.setText("Only alphanumerical characters are allowed for username");
        }
    }

    @FXML
    void enterApp(ActionEvent event) {
        messages.setFill(Color.RED);
        String pass = inputPass.getText(), username = inputUs.getText();
        if(pass.length() == 0 || username.length() == 0)
        {
            messages.setText("All fields must be filled.");
        }else{
            try{
                account = Acount.getInstance();
                boolean isOk = account.logInUserByCredentials(username,pass);
                if(isOk)
                {
                    Stage stage = (Stage)inputPass.getScene().getWindow();
                    // stage.setScene(app);
                }else if(account.existsLogin(inputUs.getText())){
                    messages.setText("Incorrect password");
                }else{
                    messages.setText("Incorrect username");
                }
            }catch(model.AcountDAOException e){
                throw new RuntimeException("New scene could not be found");
            }catch(java.io.IOException e)
            {
                throw new RuntimeException("Screen could not be shown");
            }
        }
    }

    @FXML
    void enterRegister(ActionEvent event) {

    }

}

