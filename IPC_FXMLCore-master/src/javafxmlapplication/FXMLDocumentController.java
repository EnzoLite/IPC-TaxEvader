/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Category;

/**
 *
 * @author jsoler
 */
public class FXMLDocumentController implements Initializable {
    //========================================================
    // objects defined into FXML file with fx:id 
    @FXML
    private GridPane back;

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private AnchorPane leftMenu;

    @FXML
    private ListView<String> listV;

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
    
    private ObservableList<String> listObjects;
    /*
        Cuestión, hago un objeto intermedio para facilitar la cosa y no hacer un cellFactory??
    */
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
        listV.setPrefWidth(stage.getWidth()*0.7);
        back.setPrefWidth(stage.getWidth()*0.7);
        scrollPane.setPrefWidth(stage.getWidth()*0.7);
        leftMenu.setPrefWidth(stage.getWidth()*0.3);
        
    }
    @FXML
    void exitedM(MouseEvent event)
    {
        menuButton1.setVisible(false);
        menuButton2.setVisible(false);
        menuButton3.setVisible(false);
        
        leftMenu.setPrefWidth(30);
        whole.setPrefWidth(stage.getWidth());
        scrollPane.setPrefWidth(stage.getWidth()-32);
        back.setPrefWidth(stage.getWidth()-32);
        listV.setPrefWidth(back.getPrefWidth());
        
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
        this.stage = (Stage)leftMenu.getScene().getWindow();
        menuButton1.setVisible(false);
        menuButton2.setVisible(false);
        menuButton3.setVisible(false);
        listObjects = FXCollections.observableArrayList();
        leftMenu.setPrefWidth(30);
        whole.setPrefWidth(stage.getWidth());
        scrollPane.setPrefWidth(stage.getWidth()-32);
        back.setPrefWidth(stage.getWidth()-32);
        listV.setPrefWidth(back.getPrefWidth());
        try{
            Node node = (Node) new FXMLLoader().load(getClass().getResource("prueba.fxml"));
            back.getChildren().add(node);
        }catch(IOException e){}
        
        
        
    }
    
}

/*listV.getItems().addAll("Item 1", "Item 2", "Item 3");
        listV.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            try {
                                // Load the FXML file
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("prueba.fxml"));
                                setGraphic(loader.load());

                                // Get the controller and set the text
                                PruebaController controller = loader.getController();
                                controller.setItemText(item);
                            } catch (IOException e) {
                                e.printStackTrace();
                                setText("Error loading item");
                            }
                        }
                    }
                };
            }
        });*/