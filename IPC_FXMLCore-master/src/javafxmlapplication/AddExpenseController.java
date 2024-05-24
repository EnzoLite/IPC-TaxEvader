/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author ATATBAL
 */
public class AddExpenseController implements Initializable {

    @FXML
    private TextField titleTextField;
    @FXML
    private SplitMenuButton categoryPicker;
    @FXML
    private TextField costTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ImageView ticket;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private Text errorMessage;
    
    private String title, description;
    private double cost;
    private LocalDate costDate;
    private Image Ticket;
    private Category category;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        acceptButton.setDisable(true);
        errorMessage.setText("");
        acceptButton.disableProperty().bind(Bindings.or(titleTextField.textProperty().isEmpty(), 
                Bindings.or(descriptionTextField.textProperty().isEmpty(), 
                Bindings.or(costTextField.textProperty().isEmpty(), 
                Bindings.or(categoryPicker.textProperty().isEmpty(), 
                datePicker.getEditor().textProperty().isEmpty())))));
    }    

    @FXML
    void onTitleTextField(KeyEvent event) {
        title = titleTextField.getText();
    }
    
    @FXML
    void onDescriptionTextField(KeyEvent event) {
        description = descriptionTextField.getText();
    }

    @FXML
    private void onCategoryPicker(ActionEvent event) {
        /*List<Category> categories = getUserCategories();
        for(int i = 0; i < categories.size(); i++){
            MenuItem item = new MenuItem(categories.get(i).name);
            categoryPicker.getItems().addAll(item);
            item.setOnAction(e -> categoryPicker.setText(item.getText()));
        }*/
    }

    @FXML
    void onCostTextField(KeyEvent event) {
        String cost_string = costTextField.getText();
        boolean numerical;
        numerical = cost_string.matches("[0-9-.]+");
        if(!numerical){
            if(cost_string.length() == 0) errorMessage.setText("");
            else{
                errorMessage.setText("Only decimal numbers are allowed for the cost");
                //acceptButton.setDisable(true);
            }
        }
        else{
            try{
                cost = Double.parseDouble(cost_string);
                errorMessage.setText("");
            }
            catch(NumberFormatException e){
                errorMessage.setText("Only decimal numbers are allowed for the cost");
                //acceptButton.setDisable(true);
            }
        }
        
    }

    @FXML
    private void onDatePicker(ActionEvent event) {
        costDate = datePicker.getValue();
    }

    @FXML
    private void onTicket(MouseEvent event) {
        Ticket = ticket.getImage();
    }
    
    @FXML
    void onAcceptButton(ActionEvent event) {
        /*if(registerCharge(title, description, cost, 0, Ticket, costDate, category)){
            acceptButton.getScene().getWindow().hide();
        }
        else{
            errorMessage.setText("A field was wrongly introduced");
        }*/
    }

    @FXML
    void onCancelButton(ActionEvent event) {
        cancelButton.getScene().getWindow().hide();
    }
}
