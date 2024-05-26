/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAO;
import model.AcountDAOException;
import model.User;

/**
 *
 * @author marcos
 */
public class UpdateUserController implements Initializable{
    
    @FXML
    private TextField emailF;

    @FXML
    private TextField lastF;

    @FXML
    private TextField nameF;

    @FXML
    private TextField nicknameF;

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

    @FXML
    private ImageView photo;
    
    @FXML
    private Button updateB;
    
    @FXML
    private AnchorPane back;

    @FXML
    private Button cancelB;
    
    
    @FXML
    private Button buttonImage;
    
    private String firstName, lastName, nickName, email, password;
    private Image photos;
    private Acount account;
    private User us; 
    private FXMLDocumentController fatherController;
    
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try{
            account = Acount.getInstance();
        }catch(AcountDAOException | IOException e){}
        back.setPrefWidth(500);
        back.setPrefHeight(600);
        us = account.getLoggedUser();
        firstName = us.getName();
        lastName = us.getSurname();
        nickName = us.getNickName();
        email = us.getEmail();
        password = us.getPassword();
        photo.setImage(us.getImage());
        back.setStyle("-fx-background-radius: 20");
    }
        

    @FXML
    void exitS(ActionEvent event) {
        nameF.setText("");
        lastF.setText("");
        nicknameF.setText("");
        emailF.setText("");
        password1.setText("");
        password2.setText("");
        Node a = back.getParent();
        ((Pane)a).getChildren().remove(back);
        FXMLDocumentController docController = FXMLDocumentController.getController();
        ((Stage)docController.blockingPane.getScene().getWindow()).setResizable(true);
        docController.blockingPane.setVisible(false);
        
    }

    @FXML
    void updateFields(ActionEvent event) {
        firstName = (nameF.getText().isEmpty() ? firstName : nameF.getText());
        lastName = (!lastF.getText().isEmpty() ? lastF.getText() : lastName);
        email = (emailF.getText().isEmpty() ? email : emailF.getText());
        String p = password1.getText();
        if( ((p == null || p.length() == 0) && (password2.getText() == null || password2.getText().length() == 0)) || p.equals(password2.getText()))
        {
            if(!(p == null || p.length() == 0))
            {
                password = p;
            }else{
                //Show error message
                return;
            }
        }
        //Actualizar usuario
        //Esto crea un nuevo usuario pero no borra al anterior jaja me cago en mis muertos
        us = account.getLoggedUser();
        us.setEmail(email);
        us.setName(firstName);
        us.setSurname(lastName);
        us.setPassword(password);
        us.setImage(photo.getImage());
    }
    @FXML
    void setPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        try{
            photo.setImage(new Image(selectedFile.toURI().toURL().toExternalForm()));
        }catch(Exception e){
        }
    }
}
