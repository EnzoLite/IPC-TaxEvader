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
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    
    @FXML
    private ImageView photo;
    
    @FXML
    private ImageView logOut;
    
    @FXML
    private Button buttonOut;
    
    private int counterObj;
    
    private VBox animatedPanel;
    private RowConstraints rowC = new RowConstraints();
    List<Node> listNodes;
    List<PruebaController> listCont;
    List<Category> listCat;
    List<Charge> listChar;
    private AnchorPane pane;
    private TextField userName, password;
    //Button adder position ratios
    double bX, bY;
    
    //AnimatedPane
    String name="", description="";
    TextField nameField;
    TextArea descriptionField;
    
        @FXML
    void updateUser(ActionEvent event) {
        FXMLLoader loaderU = new FXMLLoader(getClass().getResource("../view/updateUser.fxml"));
        try{
            Node nodeU =loaderU.load();
            UpdateUserController updateController = loaderU.getController();
            Stage stU = (Stage)buttonOut.getScene().getWindow();
            stU.setResizable(false);
            Pane blockingPane = new Pane();
            back.getChildren().add(blockingPane);
            blockingPane.setPrefHeight(back.getHeight());
            blockingPane.setPrefWidth(back.getWidth());
            blockingPane.setStyle("-fx-background-color: white");
            blockingPane.setOpacity(0.5);
            blockingPane.setVisible(true);
            back.getChildren().add(nodeU);
            nodeU.setLayoutY((back.getHeight()-600)/2);
            nodeU.setLayoutX((back.getWidth()-500)/2);
            nodeU.setVisible(true);            
        }catch(IOException e){}
        
    }
    
    String filler = "!"; // '!' < 'a' && '!' < '1'
    void setControllerL(FXMLDocumentController controller) 
    {
        this.controllerL = controller;
        try{
            loadCreated();
        }catch(IOException e){System.out.println("don't understand anything");}
        Stage stage = ((Stage)this.back.getScene().getWindow());
        stage.setHeight(600);
        stage.setWidth(600);
        stage.setMinHeight(600);
        stage.setMinWidth(600);
        stage.fullScreenProperty().addListener((observable, oldValue, NewValue)->{
            scrollPane.setPrefHeight(grid.getScene().getHeight());
            grid.setPrefHeight(177+120*(grid.getRowCount()-2));
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        adder.setOnAction(event -> showAnimatedPanel());
        try{
            account = Acount.getInstance();
            //account.registerUser("M", "M", "M", "M", "M", null, LocalDate.now());
            account.logInUserByCredentials("M","M");
            Image image = account.getLoggedUser().getImage();
            if( image == null ){
                photo.setImage(new Image("../../resources/images/fotoDefault.jpg"));
            }else{
                photo.setImage(image);
            }
            //Image image2 = new Image("../../resources/images/fotoLogOut.jpg");
            //logOut.setImage(image2);
            List<Category> cats = account.getUserCategories();
            for( int i = 0 ; i < cats.size() ; i++)
            {
                account.removeCategory(cats.get(i));
            }
            buttonOut.setOnAction((c)->{
                account.logOutUser();
                LogInController contr = LogInController.getLogInController();
                contr.emptyFields();
                Scene sceneL = contr.getScene();
                ((Stage)grid.getScene().getWindow()).setScene(sceneL);
            });
            
            listCat = account.getUserCategories();
            listNodes = new ArrayList<Node>(50);
            listCont = new ArrayList<PruebaController>(50);
            rowC.setMinHeight(120);
            rowC.setPrefHeight(120);
            rowC.setMaxHeight(Double.MAX_VALUE);
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
            listNodes.add(obj);
            PruebaController pController = loader.getController();
            listCont.add(pController);
            
            String[] a = listCat1.getName().split("-");
            pController.setName(a[1]);
            
            pController.setPrice(listCat1.getDescription());
            pController.setFatherController(controllerL, obj, pController,2+counterObj);
            
            grid.add(obj, 0, 2+counterObj, 3, 1);
            if(grid.getRowConstraints().size() <= 2+counterObj)
            {
                grid.getRowConstraints().add(rowC);
            }else{
                grid.getRowConstraints().set(2+counterObj, rowC);
            }
            grid.setPrefHeight(177+120*((counterObj > 2 ? counterObj : 2)));
            counterObj++;
        }
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
        
        Text title = new Text("Create category");
        nameField = new TextField();
        descriptionField = new TextArea();
        Button accept = new Button("Create");
        accept.setOnAction((c)->{
        });
        nameField.setPromptText("Enter name");
        descriptionField.setPromptText("Enter name");
        
        //Adding Buttons to pane
        animatedPanel.getChildren().add(createCat);
        animatedPanel.getChildren().add(createCharge);
        animatedPanel.getChildren().add(exits);
        
        //AnimatedPanel Layout
        animatedPanel.setPrefWidth(back.getWidth()/3.0);
        animatedPanel.setPrefHeight(26*3);
        animatedPanel.setMaxWidth(back.getWidth()/3.0);
        animatedPanel.setMaxHeight(26*3);
        animatedPanel.setLayoutX(adder.getLayoutX()+adder.getWidth()-animatedPanel.getPrefWidth());
        animatedPanel.setLayoutY(adder.getLayoutY() + adder.getHeight()-animatedPanel.getPrefHeight());

        //Buttons size
        createCat.setPrefWidth(animatedPanel.getMaxWidth());
        createCharge.setPrefWidth(animatedPanel.getMaxWidth());
        exits.setPrefWidth(animatedPanel.getMaxWidth());
        
        
        TranslateTransition translateTransition = new TranslateTransition((Duration)Duration.millis(250), animatedPanel);
        translateTransition.setFromY((double)adder.getHeight());
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
                name =nameField.getText();
                description = descriptionField.getText();
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
                name =nameField.getText();
                description = descriptionField.getText();
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
                name =nameField.getText();
                description = descriptionField.getText();
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
                name =nameField.getText();
                description = descriptionField.getText();
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
                try{
                    st.showAndWait();
                }catch(IllegalStateException e){}
                pane.setVisible(false);
            }catch(IOException e){System.out.println("Hey MG");}
            
            
        });
        createCat.setOnAction((c)->{
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addCategory.fxml"));
                Scene scene = new Scene(loader.load());
                Stage st = new Stage();
                st.setScene(scene);
                st.show();
                st.setHeight(600);
                st.setWidth(900);
                try{
                    st.showAndWait();
                }catch(IllegalStateException e){}
            }catch(IOException e){}
        });
    }
    
    void moveCat(Node node, MouseEvent event, PruebaController pC)
    {
        pC.setY(event.getSceneY());
        pC.setYL(pC.getY());
    }
    void movingCat(Node node, MouseEvent event, PruebaController pC)
    {
        if(event.getSceneY() > grid.getHeight()){return; }
        node.setTranslateY(node.getTranslateY()+event.getSceneY()-pC.getYL());
        pC.setYL(event.getSceneY());
        
    }
    void movedCat(Node node, MouseEvent event, PruebaController pC)
    {
        //column == row :)
        double rowIni = GridPane.getRowIndex(node);
        double row =(( scrollPane.getVvalue()* (grid.getHeight()-back.getHeight()) ) +event.getSceneY())/(grid.getHeight() / grid.getRowCount());
        try{
            row = (row >= counterObj+2 ? counterObj : (row >= 2 ? row : 2));
            Category cat1 = listCat.get((int)row-2);
            String[] st1 = cat1.getName().split("-");
            Category cat2 = listCat.get((int)rowIni-2);
            String[] st2 = cat2.getName().split("-");
            account.removeCategory(cat1);
            account.removeCategory(cat2);
            cat1.setName(st2[0]+"-"+st1[1]);
            cat2.setName(st1[0]+"-"+st2[1]);
            account.registerCategory(cat1.getName(),cat1.getDescription());
            account.registerCategory(cat2.getName(), cat2.getDescription());
            listCat = account.getUserCategories();
            for(int i = 0 ; i < listCat.size() ; i++)
            {
                System.out.println(listCat.get(i).getName());
            }
            listNodes.set((int)rowIni-2, listNodes.get((int)row-2));
            listNodes.set((int)row-2, node);
            listCont.set((int)rowIni-2, listCont.get((int)row-2));
            listCont.set((int)row-2, pC);
            GridPane.setRowIndex(listNodes.get((int)row-2), (int)row);
            GridPane.setRowIndex(listNodes.get((int)rowIni-2), (int)rowIni);
            
        }catch(AcountDAOException e){}
        
        node.setTranslateY(0);
        event.consume();
    }
    /*
            try{
                String name1 = nameField.getText();
                String description1 = descriptionField.getText();
                if(name1.length() == 0 || description1.length() == 0)
                {
                    return;
                }
                for(int i = 0 ; i < listCont.size() ; i++)
                {
                    if(name1.equals(listCont.get(i).getName())){ return; }
                }
                StringBuilder pos = new StringBuilder(((Integer)counterObj).toString());
                for(int i = pos.length(); i < 10 ; i++){ pos.append(filler); }
                pos.append("-");
                boolean added = account.registerCategory(pos.toString()+nameField.getText(), descriptionField.getText());
                if(added)
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("prueba.fxml"));
                    Node obj = loader.load();
                    PruebaController pController = loader.getController();
                    pController.setName(nameField.getText());
                    pController.setPrice(descriptionField.getText());
                    pController.setFatherController(controllerL, obj, pController,2+counterObj);
                    listCont.add(pController);
                    listCat = account.getUserCategories();
                    listNodes.add(obj);
                    if(2+counterObj <= 3)
                    {
                        grid.add(obj, 0, 2+counterObj, 3, 1);
                        grid.getRowConstraints().set(2+counterObj++, rowC);
                        grid.setPrefHeight(177+120*2);
                    }else{
                        grid.add(obj, 0, 2+counterObj, 3, 1);
                        grid.setPrefHeight(grid.getPrefHeight()+120 );
                        if(grid.getRowConstraints().size() <= 2+counterObj)
                        {
                            grid.getRowConstraints().add(rowC);
                        }else{
                            grid.getRowConstraints().set(2+counterObj, rowC);
                        }
                        counterObj++;
                    }
                    pController.getBack().focusedProperty().addListener((d, oldV, newV)->{
                        if(newV){ System.out.println("Hello");}
                    });
                    blockingPane.setVisible(false);
                    animatedPanel.setVisible(false);
                }
                
            }catch(AcountDAOException e){}
            catch(IOException b){}
    */
}