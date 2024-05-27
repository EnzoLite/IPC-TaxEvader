package javafxmlapplication;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Category;
public class PruebaController{
    
    @FXML
    private Text name;
    @FXML
    private Button buttonMover;
    @FXML
    private Text price;
    @FXML
    private StackPane back;
    @FXML
    private Button bigButton;
    
    private int pos;
    private Node node;
    FXMLDocumentController fatherController;
    Category cat;
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
    
    
    void setFatherController(FXMLDocumentController fatherController, Node node, PruebaController pC, int pos, Category cat){
        this.fatherController = fatherController;
        this.node = node;
        this.pC = pC;
        this.pos = pos;
        this.cat = cat;
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
    @FXML
    void enterCat(ActionEvent actionEvent)
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ChargeViewer.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage1 = (Stage) node.getScene().getWindow();
            ChargeViewerController controller = loader.getController();
            stage1.setScene(scene);
            stage1.setFullScreen(true);
            scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            controller.adjustW();
            });

            scene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                controller.adjustH();
            });
            stage1.fullScreenProperty().addListener((observable, oldValue, NewValue)->{
                controller.adjustW();
                controller.adjustH();
            });
            controller.setCat(this.cat, controller, scene, pC);
            stage1.setX(stage1.getX()+1);
            stage1.setX(stage1.getX()-1);
            stage1.setY(stage1.getY()+1);
            stage1.setY(stage1.getY()-1);
            controller.adjustW();
            controller.adjustH();
        }catch(IOException e)
        {
            
        }
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
