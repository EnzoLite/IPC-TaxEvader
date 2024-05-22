/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author ATATBAL
 */
public class SignUpNameController implements Initializable {

    @FXML
    private Button nextButton;
    @FXML
    private Button backButton;
    @FXML
    private Text signUpInstance;
    @FXML
    private Circle circle4;
    @FXML
    private Circle circle2;
    @FXML
    private Circle circle3;
    @FXML
    private Circle circle1;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField signUpTextField;
    @FXML
    private Text signUpErrorMessage;
    @FXML
    private TextField surnameTextField;
    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    private Circle circle5;
    @FXML
    private Button imageOption;
    @FXML
    private ImageView imageView;
    
    int counter = 0;
    String name;
    String surname;
    String email;
    String nickName;
    String password;
    Image image;
    LocalDate date;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        signUpErrorMessage.setFill(Color.RED);
        signUpErrorMessage.setVisible(false);
        passwordTextField.setVisible(false);
        imageOption.setVisible(false);
    } 

    @FXML
    private void onNextButton(ActionEvent event) throws IOException, AcountDAOException {
        counter++;
        if(counter == 1){
            name = signUpTextField.getText();
            surname = surnameTextField.getText();
            if(name.length() == 0 || surname.length() == 0){
                signUpErrorMessage.setVisible(true);
                signUpErrorMessage.setText("All fields must be filled");
                counter--;
            }else{
                signUpErrorMessage.setVisible(false);
                signUpInstance.setText("Nick Name");
                signUpInstance.setX(-5);
                signUpTextField.setPromptText("Nick Name");
                circle2.setFill(Paint.valueOf("#efb810"));
                signUpTextField.clear();
                surnameTextField.clear();
                surnameTextField.setVisible(false);
                signUpInstance.setY(5);
                myAnchorPane.setTopAnchor(signUpTextField, 170.0);
                myAnchorPane.setLeftAnchor(signUpTextField, 225.0);
            }
        }
        else if(counter == 2){
            nickName = signUpTextField.getText();
            if(nickName.length() == 0){
                signUpErrorMessage.setVisible(true);
                signUpErrorMessage.setText("All fields must be filled");
                counter--;
            }
            else if(nickName.contains(" ")){
                signUpErrorMessage.setVisible(true);
                signUpErrorMessage.setText("Your nickname cannot have spaces");
                counter--;
            }
            else{
                signUpErrorMessage.setVisible(false);
                signUpInstance.setText("Email");
                signUpInstance.setX(22);
                signUpTextField.setPromptText("Email");
                passwordTextField.setVisible(false);
                signUpTextField.setVisible(true);
                circle3.setFill(Paint.valueOf("#efb810"));
                signUpTextField.clear();
            }
        }
        else if(counter == 3){
            email = signUpTextField.getText();
            if(email.length() == 0){
                signUpErrorMessage.setVisible(true);
                signUpErrorMessage.setText("All fields must be filled");
                counter--;
            }
            else{
                signUpErrorMessage.setVisible(false);
                signUpTextField.setVisible(false);
                signUpInstance.setText("Password");
                signUpInstance.setX(4);
                passwordTextField.setVisible(true);
                passwordTextField.setPromptText("Password");
                circle4.setFill(Paint.valueOf("#efb810"));
                signUpTextField.clear();
            }
        }
        else if(counter == 4){
            password = passwordTextField.getText();
            if(password.length() == 0){
                signUpErrorMessage.setVisible(true);
                signUpErrorMessage.setText("All fields must be filled");
                counter--;
            }
            else if(password.contains(" ")){
                signUpErrorMessage.setVisible(true);
                signUpErrorMessage.setText("Your password cannot have spaces");
                counter--;
            }
            else if(password.length() <= 6){
                signUpErrorMessage.setVisible(true);
                signUpErrorMessage.setText("Your password must have more than 6 characters");
                counter--;
            }
            else{
                signUpErrorMessage.setVisible(false);
                signUpTextField.clear();
                signUpInstance.setText("Image(Optional)");
                signUpInstance.setX(-40);
                signUpInstance.setY(-5);
                passwordTextField.setVisible(false);
                imageOption.setVisible(true);
                circle5.setFill(Paint.valueOf("#efb810"));
                nextButton.setTranslateY(30);
                backButton.setTranslateY(30);
            }
        }
        else if(counter == 5){
            image = imageView.snapshot(null, null);
            date = LocalDate.now();
            
            boolean isOk = Acount.getInstance().registerUser(name, surname, email, nickName, password, image, date);
            
            FXMLLoader myFXMLLoader = new FXMLLoader(getClass().getResource(""));
            Parent root = myFXMLLoader.load();
        
            Scene scene = new Scene(root, 1000, 800);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("MyExpenses");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    @FXML
    private void onBackButton(ActionEvent event) {
        if(counter > 0) counter--;
        if(counter == 0){
            signUpErrorMessage.setVisible(false);
            signUpInstance.setText("Full Name");
            signUpInstance.setX(4);
            signUpTextField.setPromptText("Name");
            circle2.setFill(Paint.valueOf("#1e90ff"));
            signUpTextField.clear();
            signUpTextField.setText(name);
            surnameTextField.setText(surname);
            surnameTextField.setVisible(true);
            signUpInstance.setY(0);
            myAnchorPane.setTopAnchor(signUpTextField, 150.0);
            myAnchorPane.setLeftAnchor(signUpTextField, 225.0);
        }
        else if(counter == 1){
            signUpErrorMessage.setVisible(false);
            signUpInstance.setText("Nick Name");
            signUpInstance.setX(-5);
            signUpTextField.setPromptText("Nick Name");
            circle3.setFill(Paint.valueOf("#1e90ff"));
            signUpTextField.clear();
            signUpTextField.setText(nickName);
        }
        else if(counter == 2){
            signUpErrorMessage.setVisible(false);
            signUpInstance.setText("Email");
            signUpInstance.setX(22);
            passwordTextField.clear();
            signUpTextField.setText(email);
            passwordTextField.setVisible(false);
            signUpTextField.setVisible(true);
            circle4.setFill(Paint.valueOf("#1e90ff"));
        }
        else if(counter == 3){
            signUpErrorMessage.setVisible(false);
            signUpInstance.setText("Password");
            signUpInstance.setX(4);
            signUpTextField.setPromptText("Password");
            circle5.setFill(Paint.valueOf("#1e90ff"));
            imageOption.setVisible(false);
            passwordTextField.setVisible(true);
            signUpInstance.setX(4);
            signUpInstance.setY(5);
            nextButton.setTranslateY(5);
            backButton.setTranslateY(5);
        }
    }
    
    
    
}
