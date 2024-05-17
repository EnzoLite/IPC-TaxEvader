package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import model.Acount;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
    
    byte messageE = 0;
    private Node a[] = new Node[9];
    private Double[] ratiosX = new Double[9];
    private Double[] ratiosY = new Double[9];
    private final double ogWindowX = 364.0, ogWindowY = 518.0;
    private boolean correctUs = false, correctPass = false;
    
    @Override
    public void initialize(URL url,  ResourceBundle rb)
    {
        /*Stage stage = new Stage();
        stage.setResizable(false);*/
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
            Stage registerStage = new Stage();
            FXMLLoader loader= new  FXMLLoader(getClass().getResource("SignUpName.fxml"));
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
            registerStage.show();
        }catch(Exception e)
        {
            messages.setText("Register scene could not be found");
        }
    }
    
    @FXML
    void correctKeyPass(KeyEvent event) {
        String password = inputPass.getText();
        if(password.contains(" "))
        {
            correctPass = false;
            logInSelector.setDisable(true);
            messageE |= 1;
            messages.setText("Spaces are not allowed in this field");
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
            if((messageE) == 1){ messages.setText("Spaces are not allowed in this field");}
        }
    }

    @FXML
    void enterApp(ActionEvent event) {
        String pass = inputPass.getText(), username = inputUs.getText();
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
        //AnchorPane.setLeftAnchor(this.a[7], ((TextField)a[4]).getLayoutX() );
    }
    public void adjustH() 
    {
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
    }
    @FXML
    void forgotPass(MouseEvent event) {
        try{
            Stage stage = new Stage();
            FXMLLoader loader= new  FXMLLoader(getClass().getResource("SignUpName.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Register--MyExpenses");
            stage.setResizable(false);
            stage.setWidth(600);
            stage.setHeight(300);

            stage.show();
            
            
        }catch(IOException e)
        {
            System.out.println("Email not found");
        }

    }
}