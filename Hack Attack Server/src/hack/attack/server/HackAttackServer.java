/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.server.enums.LogState;
import hack.attack.rmi.IServerUpdate;
import hack.attack.server.exceptions.InvalidDefenseTypeException;
import hack.attack.server.exceptions.InvalidEffectException;
import hack.attack.server.exceptions.InvalidMinionTypeException;
import hack.attack.server.exceptions.InvalidSpellNameException;
import hack.attack.server.logger.Log;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author juleskreutzer
 */
public class HackAttackServer extends Application {
    
    private static TextArea console;
    
    @Override
    public void start(Stage primaryStage) {
        
        console = new TextArea();
        console.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight());
        console.setStyle("-fx-text-fill: #FF0000");
        
        StackPane root = new StackPane();
        root.getChildren().add(console);
        
        Scene scene = new Scene(root, 1024, 768);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try{
            ServerAdapter connect = ServerAdapter.getInstance();
            IServerUpdate update = ServerAdapter.getInstance();

            //System.setProperty( "java.rmi.server.hostname", "127.0.0.1" ) ;
            System.setProperty("java.rmi.server.hostname", "145.93.64.208");
            //System.setProperty("java.rmi.server.hostname", "145.93.56.144");
//            System.setProperty("java.rmi.server.hostname", "145.93.104.222");
            Registry registry = LocateRegistry.createRegistry(7611);
            registry.rebind("HackAttackServerConnect", connect);
        
            Data data = new Data(new Data.UpdateProgress() {
                    
                    @Override
                    public void update(double value) {
                        
                    }
                });
        
        } catch(RemoteException ex) {
            HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
        } catch (IOException | InvalidMinionTypeException | InvalidSpellNameException | InvalidDefenseTypeException | InvalidEffectException ex) {
            Logger.getLogger(HackAttackServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        launch(args);
    }
    
    public static void writeConsole(Log log){
        
        javafx.application.Platform.runLater( () -> console.appendText(log.toString()) );
    }
    
}
