/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.Charge;

/**
 *
 * @author marcos
 */
public class ChargeController {
    
    @FXML
    private Button acceptButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private Label categoryName;

    @FXML
    private TextField costTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ImageView ticket;

    @FXML
    private Button ticketButton;
    
    @FXML
    private Text errorMessage;
    
    Charge charge;
    Acount acount;
    String description;
    double cost;
    LocalDate costDate;
    Image new_ticket;
    
    
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        acceptButton.setDisable(false);
        errorMessage.setText("");
        descriptionTextField.setText(charge.getDescription());
        costTextField.setText("" + charge.getCost());
        datePicker.setValue(charge.getDate());
        ticket.setImage(charge.getImageScan());
        acceptButton.disableProperty().bind(Bindings.or(descriptionTextField.textProperty().isEmpty(), 
                Bindings.or(costTextField.textProperty().isEmpty(),  
                datePicker.getEditor().textProperty().isEmpty())));
        
    }
    
    @FXML
    void onAcceptButton(ActionEvent event) {
        try{
            if(acount.registerCharge(charge.getName(), description, cost, 2, new_ticket, costDate, charge.getCategory())){
                if(acount.removeCharge(charge)) ((Stage)cancelButton.getScene().getWindow()).close();
                else errorMessage.setText("It was not possible to modify the charge");
            }
        }catch(Exception e){
           errorMessage.setText("It was not possible to modify the charge"); 
        }
    }

    @FXML
    void onCancelButton(ActionEvent event) {
        ((Stage)cancelButton.getScene().getWindow()).close();
    }

    @FXML
    private void onCostTextField(KeyEvent event) {
        String cost_string = costTextField.getText();
        boolean numerical;
        numerical = cost_string.matches("[0-9-.]+");
        if(!numerical){
            if(cost_string.length() == 0) errorMessage.setText("");
            else errorMessage.setText("Only decimal numbers are allowed for the cost");
        }
        else{
            try{
                cost = Double.parseDouble(cost_string);
                errorMessage.setText("");
            }
            catch(NumberFormatException e){
                errorMessage.setText("Only decimal numbers are allowed for the cost");
            }
        }
    }

    @FXML
    private void onDatePicker(ActionEvent event) {
        LocalDate today = LocalDate.now();
        if(datePicker.getValue().isAfter(today)){
            errorMessage.setText("You cannot select a future date");
            costDate = null;
        }
        else{
            costDate = datePicker.getValue();
        }
    }

    @FXML
    private void onDescriptionTextField(KeyEvent event) {
        description = descriptionTextField.getText();
    }

    @FXML
    private void onTicket(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        try{
            ticket.setImage(new Image(selectedFile.toURI().toURL().toExternalForm()));
        }catch(Exception e){
            
        }
        new_ticket = ticket.getImage();
    }
}
