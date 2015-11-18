/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    
    private int userID;
    private String username;
    private String displayName;
    private int score;
    
    public FXMLLobbyController(int userID, String username, String displayName, int Score)
    {
        this.userID = userID;
        this.username = username;
        this.displayName = displayName;
        this.score = score;
        
        lblUsername.setText(String.format("Username: %s", this.username));
        lblDisplayName.setText(String.format("Display name: %s", this.displayName));
        lblScore.setText(String.format("Score: %s", this.score));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
