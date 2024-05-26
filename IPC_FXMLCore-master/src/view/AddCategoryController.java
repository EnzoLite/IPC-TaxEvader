/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onTitleTextField(ActionEvent event) {
    }

    @FXML
    private void onTitleTextField(KeyEvent event) {
    }

    @FXML
    private void onAcceptButton(ActionEvent event) {
    }

    @FXML
    private void onCancelButton(ActionEvent event) {
    }
    
}
