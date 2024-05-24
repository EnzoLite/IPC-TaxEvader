package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;


//grid.add((Node) loader.load(), width, height, row.spane, column.span)

public class FXMLDocumentController implements Initializable{

    FXMLDocumentController controllerL;
    Acount account;
    @FXML
    private Button adder;
    
    @FXML
    private GridPane grid;
    
    @FXML
    private AnchorPane back;
    
    @FXML
    private ScrollPane scrollPane;
    
    private int counterObj;
    
    private Pane animatedPanel;
    
    ObservableList<Category> list;
    List<PruebaController> listCont;
    List<Category> listCat;
    List<Charge> listChar;
    
    //Button adder position ratios
    double bX, bY;
    
    //AnimatedPane
    String name="", description="";
    TextField nameField;
    TextArea descriptionField;
    void setControllerL(FXMLDocumentController controller)
    {
        this.controllerL = controller;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        adder.setOnAction(event -> showAnimatedPanel());
        try{
            account = Acount.getInstance();
            account.logInUserByCredentials("M","M");
            List<Category> cats = account.getUserCategories();
            for( int i = 0 ; i < cats.size() ; i++)
            {
                account.removeCategory(cats.get(i));
            }
            listCat = account.getUserCategories();
            loadCreated();
        }catch( AcountDAOException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
        
    }
    void addButtons() throws IOException
    {
        bX = adder.getLayoutX()/(back.getWidth()+0.0);
        System.out.println(bX);
        bY = adder.getLayoutY()/(back.getHeight()+0.0);
        System.out.println(bY);
    }

    
    void adjustW(){
        
        if(animatedPanel != null)
        {
            animatedPanel.setVisible(false);
            name =nameField.getText();
            description = descriptionField.getText();
        }
        Scene scene = grid.getScene();
        back.setPrefWidth(scene.getWidth());
        scrollPane.setPrefWidth(scene.getWidth());
        grid.setPrefWidth(scene.getWidth());
        back.setMaxWidth(scene.getWidth());
        scrollPane.setMaxWidth(scene.getWidth());
        grid.setMaxWidth(scene.getWidth());
        adder.setLayoutX((0.0+bX)*back.getWidth());
    }
    void adjustH(){
        
        if(animatedPanel != null)
        {
            animatedPanel.setVisible(false);
            name =nameField.getText();
            description = descriptionField.getText();
            
        }
        Scene scene = grid.getScene();
        back.setPrefHeight(scene.getHeight());
        scrollPane.setPrefHeight(scene.getHeight());
        grid.setPrefHeight(scene.getHeight());
        back.setMaxHeight(scene.getHeight());
        scrollPane.setMaxHeight(scene.getHeight());
        grid.setMaxHeight(scene.getHeight());
        adder.setLayoutY((bY+0.0)*back.getHeight()); 
    }
    
    private void loadCreated() throws IOException
    {
        for (Category listCat1 : listCat) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("prueba.fxml"));
            Node obj = loader.load();
            PruebaController pController = loader.getController();
            pController.setName(listCat1.getName());
            pController.setPrice(listCat1.getDescription());
            grid.add(obj, 0, 2+counterObj++, 3, 1);
        }
    }
    private void showAnimatedPanel() {
        if (animatedPanel != null) {
            grid.getChildren().remove(animatedPanel);
        }
        animatedPanel = new Pane();
        animatedPanel.setScaleY(0);
        back.getChildren().add(animatedPanel);
        animatedPanel.setStyle("-fx-background-color: lightblue;");

        Text title = new Text("Create category");
        nameField = new TextField();
        descriptionField = new TextArea();
        Button accept = new Button("Create");
        accept.setOnAction((c)->{
            try{
                boolean added = account.registerCategory(nameField.getText(), descriptionField.getText());
                if(added)
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("prueba.fxml"));
                    Node obj = loader.load();
                    PruebaController pController = loader.getController();
                    pController.setName(nameField.getText());
                    pController.setPrice(descriptionField.getText());
                    pController.setFatherController(controllerL, obj);
                    if(2+counterObj <= 4)
                    {
                        grid.add(obj, 0, 2+counterObj++, 3, 1);
                    }else{
                        grid.add(obj, 0, 2+counterObj++, 3, 1);
                        grid.setPrefHeight(grid.getHeight()+obj.maxHeight(bX) );
                    }
                    pController.getBack().focusedProperty().addListener((d, oldV, newV)->{
                        if(newV){ System.out.println("Hello");}
                    });

                    animatedPanel.setVisible(false);
                }
                
            }catch(AcountDAOException e){}
            catch(IOException b){}
            
            
        });
        nameField.setPromptText("Enter name");
        descriptionField.setPromptText("Enter name");
        animatedPanel.getChildren().add(nameField);
        animatedPanel.getChildren().add(descriptionField);
        animatedPanel.getChildren().add(accept);
        animatedPanel.getChildren().add(title);
        animatedPanel.setPrefWidth(back.getWidth()/3.0);
        animatedPanel.setPrefHeight(back.getHeight()/3.0);
        
        animatedPanel.setMaxWidth(back.getWidth()/3.0);
        animatedPanel.setMaxHeight(back.getHeight()/3.0);
        animatedPanel.setLayoutX(adder.getLayoutX()+adder.getWidth()-animatedPanel.getPrefWidth());
        animatedPanel.setLayoutY(adder.getLayoutY() + adder.getHeight()-animatedPanel.getPrefHeight());
        
        title.setLayoutY(20);
        nameField.setLayoutX(10);
        nameField.setLayoutY(30);
        nameField.setPrefWidth(animatedPanel.getMaxWidth()-20);
        descriptionField.setLayoutY(animatedPanel.getMaxHeight()*0.5-20);
        descriptionField.setLayoutX(10);
        descriptionField.setPrefWidth(animatedPanel.getMaxWidth()-20);
        descriptionField.setPrefHeight(animatedPanel.getMaxHeight()*0.5-10);
        nameField.setText(name);
        descriptionField.setText(description);
        accept.setLayoutY(animatedPanel.getMaxHeight()-25);
        accept.setLayoutX((animatedPanel.getWidth()-accept.getWidth())/2);
        
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(250), animatedPanel);
        translateTransition.setFromY(adder.getHeight());
        translateTransition.setToY(0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), animatedPanel);
        scaleTransition.setFromY(0);
        scaleTransition.setToY(1);

        translateTransition.play();
        scaleTransition.play();
        animatedPanel.requestFocus();
        animatedPanel.focusedProperty().addListener((c, oldValue, newValue)->{
            if(!newValue)
            {
                animatedPanel.setVisible(false);
            }else{
                animatedPanel.setVisible(true);
                name =nameField.getText();
                description = descriptionField.getText();
            }
        }
        );
        descriptionField.focusedProperty().addListener((c, oldValue, newValue)->{
            if(!newValue)
            {
                animatedPanel.setVisible(false);
            }else{
                animatedPanel.setVisible(true);
                name =nameField.getText();
                description = descriptionField.getText();
            }
        });
        nameField.focusedProperty().addListener((c, oldValue, newValue)->{
            if(!newValue)
            {
                animatedPanel.setVisible(false);
            }else{
                animatedPanel.setVisible(true);
                name =nameField.getText();
                description = descriptionField.getText();
            }
        }
        );
        accept.focusedProperty().addListener((c, oldValue, newValue)->{
            if(!newValue)
            {
                animatedPanel.setVisible(false);
            }else{
                animatedPanel.setVisible(true);
                name =nameField.getText();
                description = descriptionField.getText();
            }
        });   
    }
    
    void moveCat(Node node, MouseEvent event)
    {
        
    }
    void movingCat(Node node, MouseEvent event)
    {
        
    }
    void movedCat(Node node, MouseEvent event)
    {
        
    }

}