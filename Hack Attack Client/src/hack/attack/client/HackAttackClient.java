/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author juleskreutzer
 */
public class HackAttackClient extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
        Parent loaderroot = (Parent)loader.load(getClass().getResource("FXMLLoader.fxml").openStream());
        FXMLLoaderController controller = loader.<FXMLLoaderController>getController();
        Scene scene = new Scene(loaderroot);

        stage.setScene(scene);
        stage.show();
        
        controller.initializeData();
        stage.setTitle("Loading Hack Attack");
        
//        FXMLLoader gameloader = new FXMLLoader();
//        Parent mainroot = (Parent)gameloader.load(getClass().getResource("FXMLDocument.fxml").openStream());
//        Stage gamestage = new Stage();
//        scene = new Scene(mainroot);
//        gamestage.setScene(scene);
//        gamestage.show();
//        gamestage.setTitle("Hack Attack");
//        
//        gEngine = GameEngine.getInstance();
//        gEngine.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
