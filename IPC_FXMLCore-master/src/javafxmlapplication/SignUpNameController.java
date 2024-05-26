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
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;
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
    Color done = Color.web("#ffd700");
    Color notDone = Color.web("#13305e");
    ScaleTransition s1 = new ScaleTransition();
    ScaleTransition s2 = new ScaleTransition();
    ScaleTransition s3 = new ScaleTransition();
    ScaleTransition s4 = new ScaleTransition();
    ScaleTransition s5 = new ScaleTransition();
    
    
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
        circle1.setFill(done);circle1.setStroke(done);
        circle2.setFill(Color.TRANSPARENT);circle2.setStroke(done);
        circle3.setFill(Color.TRANSPARENT);circle3.setStroke(done);
        circle4.setFill(Color.TRANSPARENT);circle4.setStroke(done);
        circle5.setFill(Color.TRANSPARENT);circle5.setStroke(done);
        s1.setNode(circle1); s1.setDuration(Duration.millis(750)); s1.setCycleCount(TranslateTransition.INDEFINITE); s1.setByX(0.25); s1.setByY(0.25); s1.setAutoReverse(true);
        s2.setNode(circle2); s2.setDuration(Duration.millis(750)); s2.setCycleCount(TranslateTransition.INDEFINITE); s2.setByX(0.25); s2.setByY(0.25); s2.setAutoReverse(true);
        s3.setNode(circle3); s3.setDuration(Duration.millis(750)); s3.setCycleCount(TranslateTransition.INDEFINITE); s3.setByX(0.25); s3.setByY(0.25); s3.setAutoReverse(true);
        s4.setNode(circle4); s4.setDuration(Duration.millis(750)); s4.setCycleCount(TranslateTransition.INDEFINITE); s4.setByX(0.25); s4.setByY(0.25); s4.setAutoReverse(true);
        s5.setNode(circle5); s5.setDuration(Duration.millis(750)); s5.setCycleCount(TranslateTransition.INDEFINITE); s5.setByX(0.25); s5.setByY(0.25); s5.setAutoReverse(true);
        s1.play();
        
        
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
            circle2.setFill(done);
            s1.stop(); circle1.setScaleX(1); circle1.setScaleY(1);
            s2.play(); s1.play();
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
                circle3.setFill(done);
                

                s1.stop();s2.stop();
                circle1.setScaleX(1); circle1.setScaleY(1);circle2.setScaleX(1); circle2.setScaleY(1); 
                s3.play();s2.play(); s1.play();
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
            circle4.setFill(done);
            
            s1.stop();s2.stop();s3.stop();
            circle1.setScaleX(1); circle1.setScaleY(1);circle2.setScaleX(1); circle2.setScaleY(1);circle3.setScaleX(1); circle3.setScaleY(1);
            s4.play();s3.play();s2.play(); s1.play();
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
            circle5.setFill(done);
            
            s1.stop();s2.stop();s3.stop();s4.stop();
            circle1.setScaleX(1); circle1.setScaleY(1);circle2.setScaleX(1); circle2.setScaleY(1);circle3.setScaleX(1); circle3.setScaleY(1); circle4.setScaleX(1);circle4.setScaleY(1);
            s5.play();s4.play();s3.play();s2.play(); s1.play();
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
            circle2.setFill(notDone);
            s2.stop();
            circle2.setScaleX(1); circle2.setScaleY(1);
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
            circle3.setFill(notDone);
            s3.stop();
            circle3.setScaleX(1); circle3.setScaleY(1);
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
            circle4.setFill(notDone);
            s4.stop();
            circle4.setScaleX(1); circle4.setScaleY(1);
        }
        else if(counter == 3){
            nextButton.setDisable(false);
            signUpErrorMessage.setVisible(false);
            signUpInstance.setText("Password");
            signUpInstance.setX(4);
            signUpTextField.setPromptText("Password");
            circle5.setFill(notDone);
            s5.stop();
            circle5.setScaleX(1); circle5.setScaleY(1);
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
