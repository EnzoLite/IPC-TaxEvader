/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Category;
import model.Charge;
/**
 *
 * @author marcos
 */
public class ChargeViewerController implements Initializable {

    Pane animatedPanel;
    @FXML
    private Button addChargeB;
    
    @FXML
    private AnchorPane back;
    
    @FXML
    private GridPane grid;
    
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button goBack;

    @FXML
    private ImageView logOut;

    @FXML
    private Button logOutB;

    @FXML
    private Button userB;
    
    private ChargeViewerController controllerL;
    
    List<Node> listNodes;
    List<ChargeController> listCont;
    List<Charge> listCat;
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        addChargeB.setOnAction(event -> showAnimatedPanel());
    }
    
        private void showAnimatedPanel() {
        if (animatedPanel != null) {
            grid.getChildren().remove(animatedPanel);
        }
        
        //BlockingPane
        Button blockingPane = new Button();
        blockingPane.setOpacity(0.5);
        blockingPane.setScaleY(1);
        blockingPane.setScaleX(1);
        blockingPane.setPrefHeight(back.getHeight());
        blockingPane.setPrefWidth(back.getWidth());
        blockingPane.setStyle("-fx-background-color: white");
        blockingPane.setVisible(true);
        back.getChildren().add(blockingPane);
        
        //AnimatedPanel
        animatedPanel = new VBox();
        animatedPanel.setScaleY(0);
        back.getChildren().add(animatedPanel);
        animatedPanel.setStyle("-fx-background-color: lightgray;");
        
        //Button creation
        Button createCat = new Button("Create new category");
        Button createCharge = new Button("Create new charge");
        Button exits = new Button("Cancel");
        
        //Adding Buttons to pane
        animatedPanel.getChildren().add(createCat);
        animatedPanel.getChildren().add(createCharge);
        animatedPanel.getChildren().add(exits);
        
        //AnimatedPanel Layout
        animatedPanel.setPrefWidth(back.getWidth()/3.0);
        animatedPanel.setPrefHeight(26*3);
        animatedPanel.setMaxWidth(back.getWidth()/3.0);
        animatedPanel.setMaxHeight(26*3);
        animatedPanel.setLayoutX(addChargeB.getLayoutX()+addChargeB.getWidth()-animatedPanel.getPrefWidth());
        animatedPanel.setLayoutY(addChargeB.getLayoutY() + addChargeB.getHeight()-animatedPanel.getPrefHeight());

        //Buttons size
        createCat.setPrefWidth(animatedPanel.getMaxWidth());
        createCharge.setPrefWidth(animatedPanel.getMaxWidth());
        exits.setPrefWidth(animatedPanel.getMaxWidth());
        
        
        TranslateTransition translateTransition = new TranslateTransition((Duration)Duration.millis(250), animatedPanel);
        translateTransition.setFromY((double)addChargeB.getHeight());
        translateTransition.setToY(0);

        ScaleTransition scaleTransition = new ScaleTransition((Duration)Duration.millis(250), animatedPanel);
        scaleTransition.setFromY(0);
        scaleTransition.setToY(1);

        translateTransition.play();
        scaleTransition.play();
        animatedPanel.requestFocus();
        animatedPanel.focusedProperty().addListener((c, oldValue, newValue)->{
            if(!newValue && !exits.isFocused() && !createCat.isFocused() && !createCharge.isFocused())
            {
                animatedPanel.setVisible(false);
                blockingPane.setVisible(false);
            }else{
                animatedPanel.setVisible(true);
                blockingPane.setVisible(true);
            }
        }
        );
        exits.focusedProperty().addListener((c, oldValue, newValue)->{
            
            if(!newValue && !animatedPanel.isFocused() && !createCat.isFocused() && !createCharge.isFocused())
            {
                blockingPane.setVisible(false);
                animatedPanel.setVisible(false);
            }else{
                blockingPane.setVisible(true);
                animatedPanel.setVisible(true);
            }
        });
        createCat.focusedProperty().addListener((c, oldValue, newValue)->{
            if(!newValue && !exits.isFocused() && !animatedPanel.isFocused() && !createCharge.isFocused())
            {
                blockingPane.setVisible(false);
                animatedPanel.setVisible(false);
            }else{
                animatedPanel.setVisible(true);
                blockingPane.setVisible(true);
            }
        }
        );
        createCharge.focusedProperty().addListener((c, oldValue, newValue)->{
            if(!newValue && !exits.isFocused() && !createCat.isFocused() && !animatedPanel.isFocused())
            {
                blockingPane.setVisible(false);
                animatedPanel.setVisible(false);
            }else{
                animatedPanel.setVisible(true);
                blockingPane.setVisible(true);
            }
        });   
        exits.setOnAction((c)->{back.requestFocus();});
        createCharge.setOnAction((c)->{
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AddExpense.fxml"));
                Scene scene = new Scene(loader.load());
                Stage st = new Stage();
                st.initOwner((Stage)back.getScene().getWindow());             //Falta un overlay (gris) sobre la pantalla origin mientras stage está activo
                st.initModality(Modality.WINDOW_MODAL);
                Pane pane = new Pane();
                pane.setOpacity(0.5);
                back.getChildren().add(pane);
                pane.setPrefHeight(back.getHeight());
                pane.setPrefWidth(back.getWidth());
                pane.setStyle("-fx-background-color:white");
                pane.setVisible(true);
                st.setScene(scene);
                st.show();
                st.setHeight(600);
                st.setMinHeight(600);
                st.setWidth(850);
                st.setMinWidth(850);
                st.setResizable(false);
                try{
                    st.showAndWait();
                }catch(IllegalStateException e){}
                pane.setVisible(false);
            }catch(IOException e){ e.printStackTrace();}
            
            
        });
        createCat.setOnAction((c)->{
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AddCategory.fxml"));
                Scene scene = new Scene(loader.load());
                Stage st = new Stage();
                ((AddExpenseController)loader.getController()).setMainController(this.controllerL);
                st.setScene(scene);
                st.show();
                st.setHeight(600);
                st.setWidth(900);
                st.setResizable(false);
                try{
                    st.showAndWait();
                }catch(IllegalStateException e){}
            }catch(IOException e){e.printStackTrace();}
        });
    }
}
