/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import model.User;
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

    @FXML
    private Text title;


    @FXML
    private TextArea descriptionArea;

    private Acount account;


    private Category cat;
    private ChargeViewerController controllerL;
    private RowConstraints rowC = new RowConstraints();
    List<Node> listNodes;
    List<ChargePruebaController> listCont;
    List<Charge> listCharges;
    private double bX, bY;
    private int chargeCounter;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        listNodes = new ArrayList<>();
        listCont = new ArrayList<>();
        addChargeB.setOnAction(event -> showAnimatedPanel());
        bX = 0.88333333333333;
        bY = 0.825;
        chargeCounter = 0;
        rowC.setMinHeight(120);
        rowC.setPrefHeight(120);
        rowC.setMaxHeight(Double.MAX_VALUE);
        try{
            account = Acount.getInstance();
            listCharges = account.getUserCharges();            
        }catch(AcountDAOException | IOException e){}
        logOutB.setOnAction((c)->{
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

    }
    public void setCat(Category cat, ChargeViewerController controller)
    {
        controllerL = controller;
        this.cat = cat;
        this.title.setText(cat.getName());
        this.descriptionArea.setText(cat.getDescription().split("-")[1]);
        loadCharges();
    }
    void loadCharges()
    {
        try{
            List<Charge> listAux = account.getUserCharges();
            for(int i = 0 ; i < listAux.size() ; i++)
            {
                if(listAux.get(i).getCategory().equals(cat))
                {
                    listCharges.add(listAux.get(i));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/chargePrueba.fxml"));
                    Node node =loader.load();
                    ChargePruebaController cc = loader.getController();
                    listCont.add(cc);
                    listNodes.add(node);
                    cc.setFatherController(controllerL,  node, cc);
                    grid.add(node, 0, 2+chargeCounter, 3, 1);
                    if(grid.getRowConstraints().size() <= 2+chargeCounter)
                    {
                        grid.getRowConstraints().add(rowC);
                    }else{
                        grid.getRowConstraints().set(2+chargeCounter, rowC);
                    }
                    grid.setPrefHeight(177+120*((chargeCounter > 2 ? chargeCounter : 2)));
                    if(grid.getHeight() < grid.getScene().getWindow().getHeight())
                    {
                        grid.setPrefHeight(grid.getScene().getWindow().getHeight()-3);
                    }
                    chargeCounter++;
                }
            }
        }catch(AcountDAOException | IOException e){}
        User us = account.getLoggedUser();


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
        Button createCharge = new Button("Create new charge");
        Button exits = new Button("Cancel");

        //Adding Buttons to pane
        animatedPanel.getChildren().add(createCharge);
        animatedPanel.getChildren().add(exits);

        //AnimatedPanel Layout
        animatedPanel.setPrefWidth(back.getWidth()/3.0);
        animatedPanel.setPrefHeight(26*2);
        animatedPanel.setMaxWidth(back.getWidth()/3.0);
        animatedPanel.setMaxHeight(26*2);
        animatedPanel.setLayoutX(addChargeB.getLayoutX()+addChargeB.getWidth()-animatedPanel.getPrefWidth());
        animatedPanel.setLayoutY(addChargeB.getLayoutY() + addChargeB.getHeight()-animatedPanel.getPrefHeight());

        //Buttons size
        createCharge.setPrefWidth(animatedPanel.getMaxWidth());
        exits.setPrefWidth(animatedPanel.getMaxWidth());


        TranslateTransition translateTransition = new TranslateTransition((Duration)Duration.millis(180), animatedPanel);
        translateTransition.setFromY((double)addChargeB.getHeight());
        translateTransition.setToY(0);

        ScaleTransition scaleTransition = new ScaleTransition((Duration)Duration.millis(180), animatedPanel);
        scaleTransition.setFromY(0);
        scaleTransition.setToY(1);

        translateTransition.play();
        scaleTransition.play();
        animatedPanel.requestFocus();
        animatedPanel.focusedProperty().addListener((c, oldValue, newValue)->{
            if(!newValue && !exits.isFocused() && !createCharge.isFocused())
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

            if(!newValue && !animatedPanel.isFocused() && !createCharge.isFocused())
            {
                blockingPane.setVisible(false);
                animatedPanel.setVisible(false);
            }else{
                blockingPane.setVisible(true);
                animatedPanel.setVisible(true);
            }
        });
        createCharge.focusedProperty().addListener((c, oldValue, newValue)->{
            if(!newValue && !exits.isFocused() && !animatedPanel.isFocused())
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
                ((AddExpenseController)loader.getController()).setMainController(controllerL);
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
        addChargeB.setLayoutX((0.0+bX)*back.getWidth());
        goBack.setLayoutX(back.getWidth()-addChargeB.getLayoutX());

    }
    void adjustH(){
        if(grid.getScene() == null) return;
        if(animatedPanel != null)
        {
            animatedPanel.setVisible(false);

        }
        Scene scene = grid.getScene();
        back.setPrefHeight(scene.getHeight());
        scrollPane.setPrefHeight(scene.getHeight());
        grid.setPrefHeight(scene.getHeight());
        back.setMaxHeight(scene.getHeight());
        scrollPane.setMaxHeight(scene.getHeight());
        grid.setMaxHeight(scene.getHeight());
        addChargeB.setLayoutY((bY+0.0)*back.getHeight());
        goBack.setLayoutY((bY+0.0)*back.getHeight());
    }
    public boolean addCharge(String name, String description, double cost, int units, Image scanImage, LocalDate dateCategory, Category cat1)
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/chargePrueba.fxml"));
            Node node = loader.load();
            ChargePruebaController controllerC = loader.getController();
            listNodes.add(node);
            listCont.add(controllerC);
            listCharges = account.getUserCharges();
            account.registerCharge(name, description, cost, units, scanImage, dateCategory, cat);
            controllerC.setFatherController(controllerL, node, controllerC);
            if(2+chargeCounter <= 3)
            {
                grid.add(node, 0, 2+chargeCounter, 3, 1);
                grid.getRowConstraints().set(2+chargeCounter++, rowC);
                grid.setPrefHeight(177+120*2);
            }else{
                grid.add(node, 0, 2+chargeCounter, 3, 1);
                grid.setPrefHeight(grid.getPrefHeight()+120 );
                if(grid.getRowConstraints().size() <= 2+chargeCounter)
                {
                    grid.getRowConstraints().add(rowC);
                }else{
                    grid.getRowConstraints().set(2+chargeCounter, rowC);
                }
                chargeCounter++;
            }
        }catch(IOException | AcountDAOException e){return false;}
        return true;
    }



    void moveCat(Node node, MouseEvent event, ChargePruebaController pC)
    {
        node.toFront();
        pC.setY(event.getSceneY());
        pC.setYL(pC.getY());
    }
    void movingCat(Node node, MouseEvent event, ChargePruebaController pC)
    {
        if(event.getSceneY() > grid.getHeight()){return; }
        node.setTranslateY(node.getTranslateY()+event.getSceneY()-pC.getYL());
        pC.setYL(event.getSceneY());

    }

    void movedCat(Node node, MouseEvent event, ChargePruebaController pC)
    {
        //column == row :)
        double rowIni = GridPane.getRowIndex(node);
        double row =(( scrollPane.getVvalue()*(grid.getHeight()-back.getHeight()) ) +event.getSceneY())/(grid.getHeight() / grid.getRowCount());
        try{
            row = (row >= chargeCounter+2 ? chargeCounter+1 : (row >= 2 ? row : 2));
            Charge cat1 = listCharges.get((int)row-2);
            String[] st1 = cat1.getName().split("-");

            Charge cat2 = listCharges.get((int)rowIni-2);
            String[] st2 = cat2.getName().split("-");
            cat1.setName(st2[0]+"-"+st1[1]);
            cat2.setName(st1[0]+"-"+st2[1]);

            listCharges = account.getUserCharges();
            Collections.swap(listNodes, (int)row-2, (int)rowIni-2);
            Collections.swap(listCont,  (int)row-2, (int)rowIni-2);
            GridPane.setRowIndex(listNodes.get((int)row-2), (int)row);
            GridPane.setRowIndex(listNodes.get((int)rowIni-2), (int)rowIni);

        }catch(AcountDAOException e){}

        node.setTranslateY(0);
        event.consume();
    }
}

