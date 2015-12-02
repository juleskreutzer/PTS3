/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.server.enums.LogState;
import hack.attack.server.interfaces.IServerConnect;
import hack.attack.server.interfaces.IServerUpdate;
import hack.attack.server.logger.Log;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author juleskreutzer
 */
public class HackAttackServer extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        try{
            IServerConnect connect = ServerAdapter.getInstance();
            IServerUpdate update = ServerAdapter.getInstance();

            Registry registry = LocateRegistry.createRegistry(7611);

            Naming.rebind("rmi://localhost:7611/HackAttackServerConnect", connect);
            Naming.rebind("rmi://localhost:7611/HackAttackServerUpdate", update);
        
        
        } catch(RemoteException | MalformedURLException ex) {
            Log log = new Log(LogState.ERROR, ex.getMessage());
        }
    }
    
}
