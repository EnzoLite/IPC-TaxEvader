/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Category;

/**
 *
 * @author jsoler
 */
public class FXMLDocumentController implements Initializable {
    //========================================================
    // objects defined into FXML file with fx:id 
    @FXML
    private AnchorPane back;

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private AnchorPane leftMenu;

    @FXML
    private ListView<Category> listV;

    @FXML
    private Label menuButton1;

    @FXML
    private Label orderB;

    @FXML
    private Text title;

    @FXML
    private Label menuButton2;

    @FXML
    private Label menuButton3;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private HBox whole;
    private Stage stage;
    
    @FXML
    void enteredM(MouseEvent event)
    {
        menuButton1.setVisible(true);
        menuButton2.setVisible(true);
        menuButton3.setVisible(true);
        whole.setPrefWidth(stage.getWidth());
        scrollPane.setPrefWidth(stage.getWidth()*0.7);
        back.setPrefWidth(stage.getWidth()*0.7);
        leftMenu.setPrefWidth(stage.getWidth()*0.3);
        
    }
    @FXML
    void exitedM(MouseEvent event)
    {
            menuButton1.setVisible(false);
            menuButton2.setVisible(false);
            menuButton3.setVisible(false);
            leftMenu.setPrefWidth(10);
            whole.setPrefWidth(stage.getWidth());
        scrollPane.setPrefWidth(stage.getWidth()-10);
        back.setPrefWidth(stage.getWidth()-10);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        menuButton1.setVisible(false);
        menuButton2.setVisible(false);
        menuButton3.setVisible(false);

        leftMenu.onMouseEnteredProperty().addListener((c)->{
                    // TODO
                    System.out.println("Hello");
            menuButton1.setVisible(true);
            menuButton2.setVisible(true);
            menuButton3.setVisible(true);
        });
        leftMenu.onMouseClickedProperty().addListener((c)->{
            System.out.println("Hello");
            menuButton1.setVisible(true);
            menuButton2.setVisible(true);
            menuButton3.setVisible(true);
        });
        leftMenu.onMouseExitedProperty().addListener((c)->{
                    // TODO
            menuButton1.setVisible(false);
            menuButton2.setVisible(false);
            menuButton3.setVisible(false);
        });
    }    
    public void trueInit()
    {
        stage = (Stage) leftMenu.getScene().getWindow();
        leftMenu.setPrefWidth(10);
        scrollPane.setPrefWidth(stage.getWidth()-10);
    }
    
}

