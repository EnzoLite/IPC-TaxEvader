/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
    
    private boolean correctPass = false, correctNN = true, surname_check = false;
    byte messageE = 0;
    
    int counter = 0;
    String name = "";
    String surname = "";
    String email = "";
    String nickName = "";
    String password = "";
    Image image;
    LocalDate date;
    
    Acount signUp;
    @FXML
    private Label passInfLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        signUpErrorMessage.setFill(Color.RED);
        signUpErrorMessage.setVisible(false);
        passwordTextField.setVisible(false);
        imageOption.setVisible(false);
        passInfLabel.setVisible(false);
        nextButton.setDisable(true);
    } 

    @FXML
    private void onNextButton(ActionEvent event) throws IOException, AcountDAOException {
        counter++;
        if(counter == 1){
            nextButton.setDisable(true);
            name = signUpTextField.getText();
            surname = surnameTextField.getText();
            signUpTextField.setText(nickName);
            signUpErrorMessage.setText("");
            signUpInstance.setText("Nick Name");
            signUpInstance.setX(-5);
            signUpTextField.setPromptText("Nick Name");
            circle2.setFill(Paint.valueOf("#efb810"));
            if(nickName.length() == 0) signUpTextField.clear();
            else nextButton.setDisable(false);
            surnameTextField.clear();
            surnameTextField.setVisible(false);
            signUpInstance.setY(5);
            myAnchorPane.setTopAnchor(signUpTextField, 170.0);
            myAnchorPane.setLeftAnchor(signUpTextField, 225.0);
            
        }
        else if(counter == 2){
            nickName = signUpTextField.getText();
            if(signUp.getInstance().existsLogin(nickName)){
                correctNN = false;
                signUpErrorMessage.setVisible(true);
                signUpErrorMessage.setText("This nickname already exits");
                counter--;
            }
            else{
                signUpTextField.setText(email);
                correctNN = true;
                signUpErrorMessage.setText("");
                signUpInstance.setText("Email");
                signUpInstance.setX(22);
                signUpTextField.setPromptText("Email");
                passwordTextField.setVisible(false);
                signUpTextField.setVisible(true);
                circle3.setFill(Paint.valueOf("#efb810"));
                if(email.length() == 0) signUpTextField.clear();
                else nextButton.setDisable(false);
            }
        }
        else if(counter == 3){
            email = signUpTextField.getText();
            signUpTextField.setText(password);
            signUpErrorMessage.setText("");
            signUpTextField.setVisible(false);
            passInfLabel.setVisible(true);
            signUpInstance.setText("Password");
            signUpInstance.setX(4);
            passwordTextField.setVisible(true);
            passwordTextField.setPromptText("Password");
            circle4.setFill(Paint.valueOf("#efb810"));
            if(password.length() == 0) signUpTextField.clear();
            else nextButton.setDisable(false);
        }
        else if(counter == 4){
            password = passwordTextField.getText();            
            correctPass = true;
            signUpErrorMessage.setText("");
            passInfLabel.setVisible(false);
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
        else if(counter == 5){
            image = imageView.snapshot(null, null);
            date = LocalDate.now();
            
            signUp.getInstance().registerUser(name, surname, email, nickName, password, image, date);
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("Sign up succeeded");
            a.setHeaderText("You have signed up");
            a.setContentText("Please log in and start documenting your expenses now");
            a.showAndWait();
            
            backButton.getScene().getWindow().hide();
            /*FXMLLoader myFXMLLoader = new FXMLLoader(getClass().getResource(""));
            Parent root = myFXMLLoader.load();
        
            Scene scene = new Scene(root, 1000, 800);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("MyExpenses");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();*/
        }
    }

    @FXML
    private void onBackButton(ActionEvent event) {
        if(counter > 0) counter--;
        if(counter == 0){
            nextButton.setDisable(false);
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
            nextButton.setDisable(false);
            signUpErrorMessage.setVisible(false);
            signUpInstance.setText("Nick Name");
            signUpInstance.setX(-5);
            signUpTextField.setPromptText("Nick Name");
            circle3.setFill(Paint.valueOf("#1e90ff"));
            signUpTextField.clear();
            signUpTextField.setText(nickName);
        }
        else if(counter == 2){
            passInfLabel.setVisible(false);
            nextButton.setDisable(false);
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
            nextButton.setDisable(false);
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

    @FXML
    private void onSignUpTextField(KeyEvent event) {
        if(counter == 0){
            String name_listener = signUpTextField.getText();
            if(name_listener.length() == 0 && surname_check) nextButton.setDisable(true);
            else nextButton.setDisable(false);
        }
        else if(counter == 1){
            String nickName_listener = signUpTextField.getText();
            boolean alphaNumerical;
            alphaNumerical = nickName_listener.matches("[a-zA-Z0-9]+");
            if(nickName_listener.length() == 0){
                correctNN = false;
                nextButton.setDisable(true);
                messageE &= 1;
                return;
            }
            if(!alphaNumerical){
                correctNN = false;
                nextButton.setDisable(true);
                signUpErrorMessage.setVisible(true);
                signUpErrorMessage.setText("Only alphanumerical characters are allowed for nickname");
                messageE |= 2;
            }
            else if(correctNN && alphaNumerical){
                messageE &= 1;
                correctNN = true;
                signUpErrorMessage.setText("");
                nextButton.setDisable(false);
            }
            else{
                correctNN = true;
                messageE &= 1;
                signUpErrorMessage.setText("");
                if(messageE == 1) signUpErrorMessage.setText("Spaces are not allowed for the nickname field");
            }
        }
        else if(counter == 2){
            String email_listener = signUpTextField.getText();
            if(email_listener.length() == 0) nextButton.setDisable(true);
            else nextButton.setDisable(false);
        }
    }

    @FXML
    private void onPasswordTextField(KeyEvent event) {
        String password_listener = passwordTextField.getText();
        if(password_listener.contains(" ")){
            correctPass = false;
            messageE |= 1;
            signUpErrorMessage.setVisible(true);
            signUpErrorMessage.setText("Your password cannot have spaces");
            nextButton.setDisable(true);
        }
        else{
            correctPass = true;
            if(correctPass && password_listener.length() > 5) nextButton.setDisable(false);
            else nextButton.setDisable(true);
            if(messageE == 1) signUpErrorMessage.setText("");
            else if(messageE == 3) signUpErrorMessage.setText("Only alphanumerical characters are allowed");
            messageE &= 2;
            
        }
    }
    
    @FXML
    void onImageOption(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Images", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        try{
            imageView.setImage(new Image(selectedFile.toURI().toURL().toExternalForm()));
        }catch(Exception e){
            
        }
    }
    
    @FXML
    void onSurnameTextField(ActionEvent event) {
        String surname_listener = surnameTextField.getText();
        if(surname_listener.length() == 0) surname_check = true;
        else surname_check = false;
    }
}
