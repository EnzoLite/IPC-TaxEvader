/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafxmlapplication.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author ENSABIBA
 */
public class AddCategoryController implements Initializable {

    @FXML
    private TextField titleTextField;
    
    @FXML
    private Button acceptButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Text errorMessage;
    
    @FXML
    private ColorPicker myColorPicker;
    
    @FXML
    private TextField descriptionField;
    
    @FXML
    private Text alertMessage;
    
    boolean title = false;
    private FXMLDocumentController controllerMain;
    private String titleS, descriptionS;
    private Color color;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /*@FXML
    private void onTitleTextField(ActionEvent event) {
    }*/

    @FXML
    private void onTitleTextField(KeyEvent event) {
    }

    @FXML
    private void onAcceptButton(ActionEvent event) {
        titleS = titleTextField.getText();
        descriptionS = descriptionField.getText();
        color = myColorPicker.getValue();
        if((titleS== null || titleS.length() == 0 || titleS.matches(" *")))
        {
            alertMessage.setText("Name field cannot be empty");
            return;
        }
        if(descriptionS == null || descriptionS.length() == 0 || descriptionS.matches(" *"))
        {
            alertMessage.setText("Description field cannot be empty");
            return;
        }
        if(controllerMain.addCategory(titleS, descriptionS, color))
        {
            Stage stage = (Stage)titleTextField.getScene().getWindow();
            stage.close();
        }else{
            alertMessage.setText("You already have a category with that name");
        }
    }

    @FXML
    private void onCancelButton(ActionEvent event) {
        Stage stage = (Stage)titleTextField.getScene().getWindow();
        stage.close();
    }
    
    public void setMainController(FXMLDocumentController controllerMain)
    {
        this.controllerMain = controllerMain;
    }
}
