/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
//import java.util.Locale.Category;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.Category;
import model.Charge;
import model.Acount;
import model.AcountDAOException;

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
    private Charge charge;
    Acount signUp;
    List<Category> categories;
    @FXML
    private BorderPane back;
    @FXML
    private Label Title;
    @FXML
    private Label cost_css;
    @FXML
    private Label date_css;
    @FXML
    private Label title_name;
    @FXML
    private Label category_css;
    @FXML
    private Label image_css;
    @FXML
    private Label description_css;
    @FXML
    private Button ticketButton;

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
        try{
            categories = signUp.getUserCategories();
            for(int i = 0; i < categories.size(); i++){
                MenuItem item = new MenuItem(category.getName());
                categoryPicker.getItems().addAll(item);
                item.setOnAction(e -> categoryPicker.setText(item.getText()));
            }
        }catch(Exception e){
            errorMessage.setText("The category selected does not exist");
        }
    }

    @FXML
    void onCostTextField(KeyEvent event) {
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
        costDate = datePicker.getValue();
    }

    @FXML
    private void onTicket(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        try{
            ticket.setImage(new Image(selectedFile.toURI().toURL().toExternalForm()));
        }catch(Exception e){
            
        }
        Ticket = ticket.getImage();
    }
    
    @FXML
    void onAcceptButton(ActionEvent event){
        
        try{
            if(signUp.registerCharge(title, description, cost, 0, Ticket, costDate, category)){
                //categories.setPrice(categoryPicker.getText());
                acceptButton.getScene().getWindow().hide();
            }
            else{
                errorMessage.setText("A field was wrongly introduced");
            }
        }catch(Exception e){
            errorMessage.setText("A field was wrongly introduced");
        }
    }

    @FXML
    void onCancelButton(ActionEvent event) {
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    private void onTitleTextField(ActionEvent event) {
    }
}
