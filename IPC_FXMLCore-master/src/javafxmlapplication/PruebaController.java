package javafxmlapplication;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class PruebaController{
    
    @FXML
    private Text name;
    @FXML
    private Button buttonMover;
    @FXML
    private Text price;
    @FXML
    private StackPane back;
    
    private int pos;
    private Node node;
    FXMLDocumentController fatherController;
    PruebaController pC;
    @FXML
    private Rectangle rectangle;
    //In order to move the node inside the screen
    private double x, y, xL, yL;
    double getX(){ return this.x; }
    double getY(){ return this.y; }
    void setX( double x ){ this.x = x; }
    void setY( double y ){ this.y = y; }
    double getXL(){ return this.xL; }
    double getYL(){ return this.yL; }
    void setXL( double x ){ this.xL = x; }
    void setYL( double y ){ this.yL = y; }
    String getName(){ return this.name.getText(); }
    
    
    void setFatherController(FXMLDocumentController fatherController, Node node, PruebaController pC, int pos){
        this.fatherController = fatherController;
        this.node = node;
        this.pC = pC;
        this.pos = pos;
    }
            
    StackPane getBack()
    {
        return back;
    }
    @FXML
    void move(MouseEvent event) {
        fatherController.moveCat(node, event, pC);
    }
    @FXML
    void moving(MouseEvent event)
    {
        fatherController.movingCat(node, event, pC);
    }
    @FXML
    void moved(MouseEvent event) {
        fatherController.movedCat(node, event, pC);
    }
    void setName(String text)
    {
        this.name.setText(text);
    }
    void setPrice(String price)
    {
        this.price.setText(price);
    }
    void setRectangle(String color){this.rectangle.setStyle("-fx-fill: "+color);}
}
