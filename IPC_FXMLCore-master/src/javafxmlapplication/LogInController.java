package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import model.Acount;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import model.AcountDAOException;

public class LogInController implements Initializable{
    
    public static Acount account;
    
    @FXML
    private AnchorPane back;

    @FXML
    private AnchorPane front;

    @FXML
    private Button signUpSelector;
        
    @FXML
    private PasswordField inputPass;

    @FXML
    private TextField inputUs;

    @FXML
    private Button logInSelector;

    @FXML
    private Label notMem;

    @FXML
    private Label ps;

    @FXML
    private Label title;

    @FXML
    private Label us;

    @FXML
    private Text messages;
    
    private static LogInController controllerLogIn;
    
    Pane notUsable;
    
    byte messageE = 0;
    private Node a[] = new Node[9];
    private Double[] ratiosX = new Double[9];
    private Double[] ratiosY = new Double[9];
    private final double ogWindowX = 364.0, ogWindowY = 518.0;
    private boolean correctUs = false, correctPass = false;
    
    public static void setLogInController(LogInController controller)
    {
        controllerLogIn = controller;
    }
    public static LogInController getLogInController()
    {
        return controllerLogIn;
    }
    @Override
    public void initialize(URL url,  ResourceBundle rb)
    {
        /*Stage stage = new Stage();
        stage.setResizable(false);*/
        notUsable = new Pane();
        back.getChildren().add(notUsable);
        notUsable.setStyle("-fx-background-color: gray;");
        notUsable.setVisible(false);
        messages.setFill(Color.RED);
        signUpSelector.setPrefSize(80.0,42.0);
        fixItems(signUpSelector);
        logInSelector.setPrefSize(80.0,42.0);
        logInSelector.setDisable(true);
        fixItems(logInSelector);
        fixItems(signUpSelector);
        fixItems(notMem);
        fixItems(ps);
        fixItems(title);
        fixItems(us);
        fixItems(messages);
        fixItems(inputUs);
        fixItems(inputPass);
       //signUpSelector.setDisable(true);
       /*
        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            System.out.println("Height changed: " + newHeight);
        });*/
    }
    
    public void arrInizialiter()
    {
        a[0] = title;
        a[1] = us;
        a[2] = ps;
        a[3] = inputUs;
        a[4] = inputPass;
        a[5] = signUpSelector;
        a[6] = logInSelector;
        a[7] = notMem;
        a[8] = messages;
        double y;
        for(int i = 0 ; i < 9 ; i++)
        {
             y = this.a[i].localToScene(this.a[i].getBoundsInLocal()).getMinY();
             ratiosX[i] = 0.5;
             ratiosY[i] = y/title.getScene().getWindow().getHeight();
        }

    }
    public static void fixItems(Node children)
    {
        double x = children.localToScene(children.getBoundsInLocal()).getMinX();
        double y = children.localToScene(children.getBoundsInLocal()).getMinY();;
        AnchorPane.setTopAnchor(children, y);
        AnchorPane.setLeftAnchor(children, x);
    }
    
    @FXML
    void changeScene(ActionEvent event) {
        try{
            Stage origin = (Stage)back.getScene().getWindow();
            Stage registerStage = new Stage();
            registerStage.setResizable(false);
            // Ajustar tamaño
            registerStage.initOwner(origin);             //Falta un overlay (gris) sobre la pantalla origin mientras stage está activo
            registerStage.initModality(Modality.WINDOW_MODAL);
            FXMLLoader loader= new  FXMLLoader(getClass().getResource("../view/SignUpName.fxml"));
            Parent root = loader.load();
            
            //======================================================================
            // 2- creación de la escena con el nodo raiz del grafo de escena
            Scene scene = new Scene(root);
            //======================================================================
            // 3- asiganación de la escena al Stage que recibe el metodo 
            //     - configuracion del stage
            //     - se muestra el stage de manera no modal mediante el metodo show()
            registerStage.setScene(scene);
            registerStage.setTitle("Register--MyExpenses");
            notUsable.setPrefSize(back.getWidth(), back.getHeight());
            notUsable.setVisible(true);
            notUsable.setOpacity(0.5);
            registerStage.showAndWait();
        }catch(Exception e)
        {
            messages.setText("Register scene could not be found");
        }finally{ notUsable.setVisible(false); }
    }
    
    @FXML
    void correctKeyPass(KeyEvent event) {
        String password = inputPass.getText();
        if(password.length() == 0){ correctPass = false; logInSelector.setDisable(true); return; }
        if(password.contains(" "))
        {
            correctPass = false;
            logInSelector.setDisable(true);
            messageE |= 1;
            messages.setText("Spaces are not allowed for the password field");
        }else{
            
            correctPass = true;
            if(correctUs && password.length() > 5){ logInSelector.setDisable(false); }
            else{ logInSelector.setDisable(true); }
            if(messageE == 1)
            {
                messages.setText("");
            }else if(messageE == 3)
            {
                messages.setText("Only alphanumerical characters are allowed for username");
            }
            messageE &= 2;
        }
    }

    @FXML
    void correctKeyUs(KeyEvent event) {
        String pressed = inputUs.getText();
        boolean alphaNumerical;
        alphaNumerical = pressed.matches("[a-zA-Z0-9]+");
        if(pressed.length() == 0)
        {
            correctUs = false;
            logInSelector.setDisable(true);
            messageE&=1;
            return; 
        }
        if( !alphaNumerical )
        {
            correctUs = false;
            logInSelector.setDisable(true);
            messages.setText("Only alphanumerical characters are allowed for username");
            messageE |= 2;
        }else if(correctPass && alphaNumerical)
        {
            messageE &= 1;
            correctUs = true;
            messages.setText("");
            logInSelector.setDisable(false);    
        }else{ 
            correctUs = true; 
            messageE &= 1;
            messages.setText("");
            if((messageE) == 1){ messages.setText("Spaces are not allowed for the password field");}
        }
    }

    @FXML
    void enterApp(ActionEvent event) {
        String pass = inputPass.getText(), username = inputUs.getText();
        Stage stage1 = (Stage)inputPass.getScene().getWindow();
        
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainScene.fxml"));
            Scene scene = new Scene(loader.load()); // Load the FXML file and create the scene
            stage1.setScene(scene);
            FXMLDocumentController controller = loader.getController(); // Now the controller is initialized
            controller.setControllerL(controller);
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
            controller.addButtons(); 
            return; 
        }catch(IOException e){ e.printStackTrace(); }
        
        
        
        if(pass.length() == 0 || username.length() == 0)
        {
            messages.setText("All fields must be filled.");
        }else{
            try{
                account = Acount.getInstance();
                boolean isOk = account.logInUserByCredentials(username,pass);
                if(isOk)
                {
                    Stage stage = (Stage)inputPass.getScene().getWindow();
                    // stage.setScene(app);
                }else if(account.existsLogin(inputUs.getText())){
                    messages.setText("Incorrect password");
                }else{
                    messages.setText("Incorrect username");
                }
            }catch(model.AcountDAOException e){
                throw new RuntimeException("New scene could not be found");
            }catch(java.io.IOException e)
            {
                throw new RuntimeException("Screen could not be shown");
            }
        }
    }
    public void adjustW()//Sacar el ratio de posicion para ajustarlo desde el fix item hasta llegar a cierto tamaño y cambiar la distribucion de escena
    {
        try{
            Stage stage = (Stage)back.getScene().getWindow();
            back.setLayoutX(0);
            back.setPrefWidth(stage.getWidth());
            front.setLayoutX(0);
            front.setPrefWidth(back.getWidth());
            double var = (back.getWidth() > 800 ? ogWindowX : back.getWidth());
            for(int i = 0 ; i < 9 ; i++)
            {
                double x = ratiosX[i]*var;
                if( a[i] instanceof Label ){
                    x -= (((Label)a[i]).getWidth()/2.0);
                    /* El else es en caso de que sea la etiqueta de forgot password, esta depende del password textfield */
                }else if( a[i] instanceof TextField ){
                    x -= (((TextField)a[i]).getWidth()/2.0);
                }else if( a[i] instanceof Button ){
                    x -= (((Button)a[i]).getWidth()/2.0);
                }else if( a[i] instanceof Text)
                {
                    x+= ((Button)a[6]).getWidth()/2.0+10;
                   messages.setWrappingWidth(var-x-10);
                }
                AnchorPane.setLeftAnchor(this.a[i], x);
            }
            if(back.getWidth() > 800)
            {
                front.setLayoutX((back.getWidth()-ogWindowX)/2);
                front.setPrefWidth(ogWindowX);
            }else{
                front.setLayoutX(0);
                front.setPrefWidth(back.getWidth());
            }
        }catch(NullPointerException e){}
        
        //AnchorPane.setLeftAnchor(this.a[7], ((TextField)a[4]).getLayoutX() );
    }
    public void adjustH() 
    {
        try{
            Stage stage = (Stage)back.getScene().getWindow();
            back.setPrefHeight(stage.getHeight());
            front.setLayoutY(0);
            front.setPrefHeight(back.getHeight());
            Double var = (back.getHeight() > 600 ? ogWindowY : back.getHeight());
            for(int i = 0 ; i < 9 ; i++)
            {   
                double y = ratiosY[i]*var;
                AnchorPane.setTopAnchor(this.a[i], y);
            }
            //AnchorPane.setLeftAnchor(this.a[7], ((TextField)a[4]).getLayoutX() );
            if(back.getHeight() > 600)
            {
                front.setLayoutY( (back.getHeight()-front.getHeight())/2 );
                front.setPrefHeight( ogWindowY );
            }else{
                front.setLayoutY(0);
                front.setPrefHeight( back.getHeight() );
            }
        }catch(NullPointerException e){}

    }
    @FXML
    void forgotPass(MouseEvent event) {
        
        notUsable.setPrefSize(back.getWidth(), back.getHeight());
        notUsable.setVisible(true);
        notUsable.setOpacity(0.5);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Recovery password");
        dialog.setHeaderText("An email to change the password will be sent to the email associated");
        dialog.setContentText("Input your username:");
        Optional<String> output = dialog.showAndWait();
        try{
            account = Acount.getInstance();
        }catch(IOException e){} catch (AcountDAOException ex) {}
        
        if( output.isPresent() && account.existsLogin(output.get()) )
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Email sent");
            alert.setHeaderText(null);
            alert.setContentText("An email to recover the account associated with the username "+output.get()+" has been sent.\nCheck your email");
            alert.showAndWait();
        }else if(output.isPresent()){
            ButtonType button1 = new ButtonType("Retry");
            ButtonType button2 = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(AlertType.ERROR, 
                    "You have to write an existing username, check that you have written it correctly");
            alert.getButtonTypes().setAll(button1, button2);
            /*alert.onCloseRequestProperty().addListener((c)->{
                alert.close();
            });*/
            alert.setTitle("Email could not be sent");
            alert.setHeaderText(null);
            Optional<ButtonType> ans = alert.showAndWait();
            if(ans.isPresent())
            {
                boolean retry = true;
                while(ans.get() == button1 && retry)
                {
                    output = dialog.showAndWait();
                    if(output.isPresent())
                    {
                       if(!account.existsLogin(output.get()))
                       {
                            ans = alert.showAndWait();
                       }else{ retry = false; } 
                    }else{
                        retry = false;
                    }
                }
                if(ans.get() == button1)
                {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Email sent");
                    alert.setHeaderText(null);
                    alert.setContentText("An email to recover the account associated with the username "+output.get()+" has been sent.\nCheck your email");
                    alert.showAndWait();                    
                }

            }
        }
        notUsable.setVisible(false);

    }
    public Scene getScene()
    {
        return back.getScene();
    }
    public void emptyFields()
    {
        inputUs.setText("");
        inputPass.setText("");
        logInSelector.setDisable(true);
        correctUs = false;
        correctPass = false;
    }
}