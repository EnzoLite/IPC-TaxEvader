/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class JavaFXMLApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("../view/logIn.fxml"));
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        stage.setTitle("start PROJECT - IPC:");
        stage.setMinHeight(550);
        stage.setMinWidth(364);
        stage.show();
        LogInController logInController = loader.getController();
        logInController.arrInizialiter();
        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            logInController.adjustW();
        });
        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            logInController.adjustH();
        });
        stage.fullScreenProperty().addListener((observable, oldValue, NewValue)->{
            System.out.println(stage.getWidth());
            logInController.adjustW();
            logInController.adjustH();
        });
        /*
        stage.show();
        FXMLDocumentController controller = loader.getController();
        controller.trueInit();
        */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }


    
}
