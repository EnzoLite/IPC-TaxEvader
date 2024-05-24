package javafxmlapplication;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
public class PruebaController{
    
    @FXML
    private Text name;
    @FXML
    private Button buttonMover;
    @FXML
    private Text price;
    @FXML
    private StackPane back;
    private Node node;
    
    FXMLDocumentController fatherController;
    void setFatherController(FXMLDocumentController fatherController, Node node){
        this.fatherController = fatherController;
        this.node = node;
    }
            
    StackPane getBack()
    {
        return back;
    }
    @FXML
    void move(MouseEvent event) {
        fatherController.moveCat(node, event);
    }
    @FXML
    void moving(MouseEvent event)
    {
        fatherController.movingCat(node, event);
    }
    @FXML
    void moved(MouseEvent event) {
        fatherController.movedCat(node, event);
    }
    void setName(String text)
    {
        this.name.setText(text);
    }
    void setPrice(String price)
    {
        this.price.setText(price);
    }
}
