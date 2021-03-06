/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.InvalidMinionTypeException;
import hackattackfx.exceptions.InvalidSpellNameException;
import java.awt.Button;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jasper Rouwhorst
 */
public class HackAttackFX extends Application {
    
    GameEngine gEngine;
    
    
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
    
    @Override
    public void stop(){
        // Executes when the javafx application is stopped
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InvalidMinionTypeException, InvalidSpellNameException {
        launch(args);
        
    }
    
}
