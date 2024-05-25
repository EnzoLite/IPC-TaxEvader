/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
/**
 * FXML Controller class
 *
 * @author ENSABIBA
 */
public class PrintExpenseRecordController implements Initializable {


    @FXML
    private Button buttonDown;
    @FXML
    private Button buttonPrint;
    @FXML
    private Button backButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void download(ActionEvent event) {
        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setTitle("Download my record");
        a.setHeaderText("Do you want to save myexpenserecord.pdf in your device?");
        ButtonType accept = new ButtonType("Yes");
        ButtonType deny = new ButtonType("No", ButtonData.CANCEL_CLOSE);
        a.getButtonTypes().setAll(accept, deny);
        
        Optional<ButtonType> result = a.showAndWait();
        if(result.isPresent() && result.get() == accept ){
            Alert b = new Alert(AlertType.INFORMATION);
            b.setTitle("Download completed");
            b.setHeaderText("myexpenserecord.pdf has been downloaded");
            b.showAndWait();
        }
       
    }

    @FXML
    private void printTry(ActionEvent event) {
        Alert a = new Alert(AlertType.ERROR);
        a.setTitle("ERROR 264");
        a.setHeaderText("It seems there has been an error (264)");
        a.setContentText("Unable to detect a printer, please try again later");
        a.showAndWait();
    }

    @FXML
    private void goBack(ActionEvent event) {
        //no fucking idea on how to do this 
    }

}
