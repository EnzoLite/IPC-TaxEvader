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
import javafx.scene.control.Cell;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DateCell;
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
import javafx.stage.Stage;
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
    
    private ChargeViewerController chargeVController;
    private String title, description;
    private double cost;
    private LocalDate costDate;
    private Image Ticket;
    private Category category;
    private Charge charge;
    List<Category> categories;
    @FXML
    private ColorPicker myColorPicker;

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
        try{
            Acount account = Acount.getInstance();
            categories = account.getUserCategories();
            for(int i = 0; i < categories.size(); i++){
                final int o = i;
                MenuItem item = new MenuItem(categories.get(i).getName());
                item.setOnAction(e ->
                {
                    categoryPicker.setText(item.getText());
                    category = categories.get(o);
                 });
                categoryPicker.getItems().addAll(item);
            }
        }catch(Exception e){
            
        }
        datePicker.setDayCellFactory(param -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now())>0);
            }
        });
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
    void onCategoryPicker(ActionEvent event) {
/*
        for(int i = 0; i < categories.size(); i++){
            Category aux = categories.get(i);
            if(categoryPicker.getText() == aux.getName()){
                category = aux;
                break;
            }
        }*/
            
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
    void onTicket(ActionEvent event) {
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
        
        /*try{
            if(signUp.registerCharge(title, description, cost, 2, Ticket, costDate, category)){
                //categories.setPrice(categoryPicker.getText());
                acceptButton.getScene().getWindow().hide();
            }
            else{
                if(title == "") errorMessage.setText("You need to fill the Title field");
                else if(description == "") errorMessage.setText("You need to fill the Description field");
                else if(costDate == null) errorMessage.setText("You need to fill the Date field");
                else if(category == null) errorMessage.setText("You need to fill the Category field");
            }
        }catch(Exception e){
            
        }*/
        
        if((title== null || title.length() == 0 || title.matches(" *")))
        {
            if(title.matches(" *")){System.out.println("I'm gonna kill myself");}
            errorMessage.setText("Name field cannot be empty");
            return;
        }
        if(description == null || description.length() == 0 || description.matches(" *"))
        {
            errorMessage.setText("Description field cannot be empty");
            return;
        }
        if(costTextField.getText() == null || costTextField.getText().length() == 0 || costTextField.getText().matches(" *")){
            errorMessage.setText("Cost field cannot be empty");
        }
        if(costDate == null || costDate.toString().length() == 0 || costDate.toString().matches(" *")){
            errorMessage.setText("Date field cannot be empty");
        }
        if(chargeVController.addCharge(title, description, cost, 1, ticket.getImage(), costDate, category))
        {
            Stage stage = (Stage)titleTextField.getScene().getWindow();
            stage.close();
        }else{
            errorMessage.setText("You already have a charge with that name");
        }
    }

    @FXML
    void onCancelButton(ActionEvent event) {
        ((Stage)cancelButton.getScene().getWindow()).close();
    }

//   @FXML
//    private void onTitleTextField(ActionEvent event) {
//    }
    public void setMainController(ChargeViewerController chargeViewController)
    {
        chargeVController = chargeViewController;
    }
}
