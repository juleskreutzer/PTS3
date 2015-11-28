/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    
    private int userID;
    private String username;
    private String displayName;
    private int score;
    
    public FXMLLobbyController()
    {
//        this.userID = userID;
//        this.username = username;
//        this.displayName = displayName;
//        this.score = score;
//        
//        lblUsername.setText(String.format("Username: %s", this.username));
//        lblDisplayName.setText(String.format("Display name: %s", this.displayName));
//        lblScore.setText(String.format("Score: %s", this.score));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userID = Data.userIDPlayerA;
        this.username = Data.playerAName;
        this.displayName = Data.displayNameA;
        this.score = Data.scorePlayerA;
        
        Platform.runLater(new Runnable() { 

            @Override
            public void run() {
                lblUsername.setText(String.format("Username: %s", username));
                lblDisplayName.setText(String.format("Display name: %s", displayName));
                lblScore.setText(String.format("Score: %s", score));
            }
            
        });
    }
    
    public void customMatch() throws IOException
    {
        automaticMatch();
    }
    
    public void automaticMatch() throws IOException
    {
        FXMLLoader gameloader = new FXMLLoader();
        Parent mainroot = (Parent)gameloader.load(getClass().getResource("FXMLDocument.fxml").openStream());
        Stage stage  = (Stage)pane.getScene().getWindow();
        stage.close();
        Stage gamestage = new Stage();
        Scene scene = new Scene(mainroot);
        gamestage.setScene(scene);
        gamestage.setTitle("Hack Attack");
        gamestage.show();

        GameEngine engine = GameEngine.getInstance();
        engine.start();
    }
    
}
