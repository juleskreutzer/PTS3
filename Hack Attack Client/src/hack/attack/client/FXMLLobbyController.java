/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import hack.attack.client.interfaces.IClient;
import hack.attack.client.interfaces.IClientCreate;
import hack.attack.client.interfaces.IClientDelete;
import hack.attack.client.interfaces.IClientUpdate;
import hack.attack.client.interfaces.IServerConnect;
import hack.attack.client.interfaces.IServerUpdate;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author juleskreutzer
 */
public class FXMLLobbyController implements Initializable {

    @FXML
    private Button btnAutomatic;
    @FXML
    private Button btnCustom;
    @FXML
    private TextField txtUsername;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblDisplayName;
    @FXML
    private Label lblScore;
    @FXML
    private AnchorPane pane;
    
    Account account;
    IServerConnect connect;
    
    public FXMLLobbyController()
    {
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            account = new Account(Data.userIDPlayerA, Data.playerAName, Data.displayNameA, Data.scorePlayerA);
        
            Platform.runLater(new Runnable() { 

                @Override
                public void run() {
                    lblUsername.setText(String.format("Username: %s", account.getUsername()));
                    lblDisplayName.setText(String.format("Display name: %s", account.getDisplayName()));
                    lblScore.setText(String.format("Score: %s", account.getUserScore()));
                }
            });
        
            System.setProperty("java.rmi.server.hostname","localhost");
            connect = (IServerConnect)Naming.lookup("rmi://localhost:7611/HackAttackServerConnect");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(FXMLLobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void customMatch() throws IOException
    {
       
    }
    
    public void automaticMatch() throws IOException
    {
        ClientAdapter adapter = ClientAdapter.getInstance();
        HashMap<String, IServerUpdate> result = connect.findMatch(account, adapter.getInterfaces());
        
        for(String key : result.keySet())
        {
            adapter.setSessionKey(key);
            adapter.setIServerUpdate((IServerUpdate)result.get(key));
        }
        
        FXMLLoader gameloader = new FXMLLoader();
        Parent mainroot = (Parent)gameloader.load(getClass().getResource("FXMLDocument.fxml").openStream());
        Stage stage  = (Stage)pane.getScene().getWindow();
        stage.close();
        Stage gamestage = new Stage();
        Scene scene = new Scene(mainroot);
        gamestage.setScene(scene);
        gamestage.setTitle("Hack Attack");
        gamestage.show();

        //GameEngine engine = GameEngine.getInstance();
        //engine.start();
    }
}
