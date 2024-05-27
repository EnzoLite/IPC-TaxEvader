package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;


//grid.add((Node) loader.load(), width, height, row.spane, column.span)

public class FXMLDocumentController implements Initializable{

    static FXMLDocumentController controllerL;
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
    
    private Category othersA;
    private int counterObj;
    
    private VBox animatedPanel;
    private RowConstraints rowC = new RowConstraints();
    List<Node> listNodes;
    List<PruebaController> listCont;
    List<Category> listCat;
    private AnchorPane pane;
    private TextField userName, password;
    //Button adder position ratios
    double bX, bY;
    
    //AnimatedPane
    String name="", description="";
    TextField nameField;
    TextArea descriptionField;
    public Pane blockingPane;
    @FXML
    void updateUser(ActionEvent event) {
        FXMLLoader loaderU = new FXMLLoader(getClass().getResource("../view/updateUser.fxml"));
        try{
            Node nodeU =loaderU.load();
            UpdateUserController updateController = loaderU.getController();
            Stage stU = (Stage)buttonOut.getScene().getWindow();
            stU.setResizable(false);
            blockingPane = new Pane();
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
    void setControllerL(FXMLDocumentController controller,Scene scene) 
    {
        controllerL = controller;
        try{
            loadCreated(scene);
        }catch(IOException e){System.out.println("don't understand anything");}
        Stage stage = ((Stage)scene.getWindow());
        stage.setHeight(600);
        stage.setWidth(600);
        stage.setMinHeight(600);
        stage.setMinWidth(600);
        stage.fullScreenProperty().addListener((observable, oldValue, NewValue)->{
            scrollPane.setPrefHeight(scene.getHeight());
            grid.setPrefHeight(177+120*(grid.getRowCount()-2));
        });
    }
    static FXMLDocumentController getController()
    {
        return controllerL;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        adder.setOnAction(event -> showAnimatedPanel());
        try{
            account = Acount.getInstance();
            try{
                account.registerCategory("!!!!!!!!!!-Others", "#000000-0-Charges without category" );
            }catch(Exception e){}
            /*
            List<Charge> list = account.getUserCharges();
            List<Category> listC = account.getUserCategories();
            for(int i = 0 ; i < list.size() ; i++)
            {
                account.removeCharge(list.get(i));
            }
            for(int i = 0 ; i < listC.size() ; i++)
            {
                account.removeCategory(listC.get(i));
            }*/
            Image image = account.getLoggedUser().getImage();
            if( image == null ){
                photo.setImage(new Image("../../resources/images/fotoDefault.jpg"));
            }else{
                photo.setImage(image);
            }
            //Image image2 = new Image("../../resources/images/fotoLogOut.jpg");
            //logOut.setImage(image2);
            buttonOut.setOnAction((c)->{
                account.logOutUser();
                LogInController contr = LogInController.getLogInController();
                Scene sceneL = contr.getScene();
                ((Stage)grid.getScene().getWindow()).setScene(sceneL);
                contr.comingBack();
                contr.adjustH();
                contr.adjustW();
                ((Stage)sceneL.getWindow()).setFullScreen(true);
                ((Stage)sceneL.getWindow()).setFullScreen(false);
            });
            listCat = account.getUserCategories();
            listNodes = new ArrayList<>(50);
            listCont = new ArrayList<>(50);
            rowC.setMinHeight(120);
            rowC.setPrefHeight(120);
            rowC.setMaxHeight(Double.MAX_VALUE);
        }catch( AcountDAOException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
    }
    void addButtons() throws IOException
    {
        back.setPrefHeight(back.getScene().getWindow().getHeight());
        scrollPane.setPrefHeight(back.getHeight());
        bX = 0.88333333333;
        bY = 0.825;
        Stage stage3 = (Stage)back.getScene().getWindow();
        adder.setLayoutX((0.0+bX)*back.getWidth());
        adder.setLayoutY((bY+0.0)*back.getHeight()); 
    }

    
    void adjustW(){
        if(grid.getScene() == null) return;
        if(animatedPanel != null)
        {
            animatedPanel.setVisible(false);
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
        if(grid.getScene() == null) return;
        if(animatedPanel != null)
        {
            animatedPanel.setVisible(false);
            
        }
        Scene scene = grid.getScene();
        double vars = ((120*(grid.getRowCount()-2)+177) > scene.getHeight() ? (120*(grid.getRowCount()-2)+177) : scene.getHeight() );
        back.setPrefHeight(vars);
        scrollPane.setPrefHeight(scene.getHeight());
        grid.setPrefHeight(vars);
        back.setMaxHeight(scene.getHeight());
        scrollPane.setMaxHeight(scene.getHeight());
        grid.setMaxHeight(vars);
        adder.setLayoutY((bY+0.0)*back.getHeight()); 
    }
    
    
    private void loadCreated(Scene scene) throws IOException
    {
        for (Category listCat1 : listCat) {
            if(listCat1.getName().endsWith("Others"))
            {
                System.out.println("found");
                othersA = listCat1;
                ChargeViewerController.setOthers(listCat1);
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/prueba.fxml"));
            Node obj = loader.load();
            listNodes.add(obj);
            PruebaController pController = loader.getController();
            listCont.add(pController);
            
            String[] a = listCat1.getName().split("-");
            pController.setName(a[1]);
            String[] b = listCat1.getDescription().split("-");
            pController.setPrice(b[1]);
            pController.setFatherController(controllerL, obj, pController,2+counterObj, listCat1);
           pController.setRectangle(b[0]);
            grid.add(obj, 0, 2+counterObj, 3, 1);
            if(grid.getRowConstraints().size() <= 2+counterObj)
            {
                grid.getRowConstraints().add(rowC);
            }else{
                grid.getRowConstraints().set(2+counterObj, rowC);
            }
            grid.setPrefHeight(177+120*((counterObj > 2 ? counterObj : 2)));
            if(grid.getHeight() < scene.getWindow().getHeight())
            {
                grid.setPrefHeight(scene.getWindow().getHeight()-3);
            }
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
        createCat.setStyle("-fx-text-fill: black");
        createCharge.setStyle("-fx-text-fill: black");
        exits.setStyle("-fx-text-fill: black");
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
                AddExpenseController contr = loader.getController();
                contr.setDocumentController(controllerL);
                Stage st = new Stage();
                st.initOwner((Stage)back.getScene().getWindow());             //Falta un overlay (gris) sobre la pantalla origin mientras stage está activo
                FXMLLoader loaders = new FXMLLoader(getClass().getResource("../view/ChargeViewer.fxml"));
                loaders.load();
                ChargeViewerController chargeVController = loaders.getController();
                contr.setMainController(chargeVController);
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
                ((AddCategoryController)loader.getController()).setMainController(this.controllerL);
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
   List<PruebaController> getControllerList()
   {
       return this.listCont;
   }
    void moveCat(Node node, MouseEvent event, PruebaController pC)
    {
        node.toFront();
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
        double row =(( scrollPane.getVvalue()*(grid.getHeight()-back.getHeight()) ) +event.getSceneY())/(grid.getHeight() / grid.getRowCount());
        try{
            row = (row >= counterObj+2 ? counterObj+1 : (row >= 2 ? row : 2));
            Category cat1 = listCat.get((int)row-2);
            String[] st1 = cat1.getName().split("-");
            
            Category cat2 = listCat.get((int)rowIni-2);
            String[] st2 = cat2.getName().split("-");
            cat1.setName(st2[0]+"-"+st1[1]);
            cat2.setName(st1[0]+"-"+st2[1]);

            listCat = account.getUserCategories();
            Collections.swap(listNodes, (int)row-2, (int)rowIni-2);
            Collections.swap(listCont,  (int)row-2, (int)rowIni-2);
            GridPane.setRowIndex(listNodes.get((int)row-2), (int)row);
            GridPane.setRowIndex(listNodes.get((int)rowIni-2), (int)rowIni);
            
        }catch(AcountDAOException e){}
        
        node.setTranslateY(0);
        event.consume();
    }
    
    public boolean addCategory(String name1, String description1, Color color)
        {
            try{
   
                for(int i = 0 ; i < listCont.size() ; i++)
                {
                    if(name1.equals(listCont.get(i).getName())){ return false; }
                }
                StringBuilder pos = new StringBuilder(((Integer)counterObj).toString());
                for(int i = pos.length(); i < 10 ; i++){ pos.append(filler); }
                pos.append("-");
                String colors = color.toString();
                colors = "#"+colors.substring(2);
                System.out.println("Hello");
                boolean added = account.registerCategory(pos.toString()+name1, colors+"-0-"+description1);
                if(added)
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/prueba.fxml"));
                    Node obj = loader.load();
                    PruebaController pController = loader.getController();
                    pController.setName(name1);
                    pController.setPrice("0");
                    pController.setRectangle(colors);
                    listCont.add(pController);
                    listCat = account.getUserCategories();
                    pController.setFatherController(controllerL, obj, pController,2+counterObj, listCat.get(counterObj));
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
                }
                
            }catch(AcountDAOException | IOException e){e.printStackTrace();}
            return true;
        }
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
