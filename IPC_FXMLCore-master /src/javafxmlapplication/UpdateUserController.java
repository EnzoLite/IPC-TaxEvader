/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
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
import model.Acount;
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
    
    private String firstName, lastName, nickName, email, password;
    private Image photos;
    private Acount account;
    
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try{
            account = Acount.getInstance();
        }catch(AcountDAOException | IOException e){}
        User us = account.getLoggedUser();
        firstName = us.getName();
        lastName = us.getSurname();
        nickName = us.getNickName();
        email = us.getEmail();
        password = us.getPassword();
        photo.setImage(us.getImage());
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
        
    }

    @FXML
    void updateFields(ActionEvent event) {
        firstName = (nameF.getText().isEmpty() ? firstName : nameF.getText());
        lastName = (!lastF.getText().isEmpty() ? lastF.getText() : lastName);
        nickName = (nicknameF.getText().isEmpty() ? nickName : nicknameF.getText());
        email = (emailF.getText().isEmpty() ? email : emailF.getText());
        if( password1.getText().equals(password2.getText()))
        {
            System.out.println(1);
        }
        System.out.println(1);
    }
}
