/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import hack.attack.rmi.Account;
import hack.attack.rmi.ClientAdapter;
import hack.attack.rmi.IServerConnect;
import hack.attack.rmi.IServerUpdate;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    private Button btnJoinCustom;
    @FXML
    private Button btnHostCustom;
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
        
            //System.setProperty("java.rmi.server.hostname","localhost");
            //Registry registry = LocateRegistry.getRegistry("127.0.0.1",7611);
            Registry registry = LocateRegistry.getRegistry("10.0.1.41", 7611); 
            //Registry registry = LocateRegistry.getRegistry("145.93.56.144", 7611);
//            Registry registry = LocateRegistry.getRegistry("145.93.104.222", 7611);
            connect = (IServerConnect)registry.lookup("HackAttackServerConnect");
        } catch (NotBoundException | RemoteException ex) {
            Logger.getLogger(FXMLLobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void hostCustomMatch() throws IOException
    {
       
        ClientAdapter adapter = ClientAdapter.getInstance();
        adapter.setAccount(account);
        HashMap<String, IServerUpdate> result = connect.hostCustomGame(account, adapter.getInterfaces());
        
        FXMLLoader gameloader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent mainroot = (Parent)gameloader.load();
        FXMLDocumentController controller = gameloader.getController();
        Stage stage  = (Stage)pane.getScene().getWindow();
        stage.close();
        Stage gamestage = new Stage();
        Scene scene = new Scene(mainroot);
        gamestage.setScene(scene);
        gamestage.setTitle("Hack Attack");
        gamestage.show();
        adapter.initialize(controller);
        
        for(String key : result.keySet())
        {
            adapter.setSessionKey(key);
            IServerUpdate update = (IServerUpdate)result.get(key);
            adapter.setIServerUpdate(update);
            update.ready(key, account);
            
        }
    }
    
    public void joinCustomMatch() throws IOException
    {
        ClientAdapter adapter = ClientAdapter.getInstance();
        adapter.setAccount(account);
        HashMap<String, IServerUpdate> result = connect.joinCustomGame(account, txtUsername.getText(), adapter.getInterfaces());
        if (result != null) {
            FXMLLoader gameloader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent mainroot = (Parent)gameloader.load();
            FXMLDocumentController controller = gameloader.getController();
            Stage stage  = (Stage)pane.getScene().getWindow();
            stage.close();
            Stage gamestage = new Stage();
            Scene scene = new Scene(mainroot);
            gamestage.setScene(scene);
            gamestage.setTitle("Hack Attack");
            gamestage.show();
            adapter.initialize(controller);

            for(String key : result.keySet())
            {
                adapter.setSessionKey(key);
                IServerUpdate update = (IServerUpdate)result.get(key);
                adapter.setIServerUpdate(update);
                update.ready(key, account);

            }
        }
        else {
            System.err.println("No player found with this displayName");
        }
    }
    
    public void automaticMatch() throws IOException
    {
        ClientAdapter adapter = ClientAdapter.getInstance();
        adapter.setAccount(account);
        HashMap<String, IServerUpdate> result = connect.findMatch(account, adapter.getInterfaces());
        
        
        
        
        FXMLLoader gameloader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent mainroot = (Parent)gameloader.load();
        FXMLDocumentController controller = gameloader.getController();
        Stage stage  = (Stage)pane.getScene().getWindow();
        stage.close();
        Stage gamestage = new Stage();
        Scene scene = new Scene(mainroot);
        gamestage.setScene(scene);
        gamestage.setTitle("Hack Attack");
        gamestage.show();
        adapter.initialize(controller);
        
        for(String key : result.keySet())
        {
            adapter.setSessionKey(key);
            IServerUpdate update = (IServerUpdate)result.get(key);
            adapter.setIServerUpdate(update);
            update.ready(key, account);
            
        }
        //GameEngine engine = GameEngine.getInstance();
        //engine.start();
    }
}
