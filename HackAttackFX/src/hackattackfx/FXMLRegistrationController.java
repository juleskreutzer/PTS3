/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author juleskreutzer
 */
public class FXMLRegistrationController implements Initializable {

    @FXML
    private Pane pane;
    private TextField txtDisplayName;
    private TextField txtUsername;
    private TextField txtEmail;
    private PasswordField txtPassword;
    private TextFlow txtInfoBox;
    private Button btnRegister;
    private Button btnLogin;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Node> nodes = pane.getChildren();
        for(Node n : nodes)
        {
           if(n instanceof TextField)
           {
               txtDisplayName = (TextField)n;
               txtUsername = (TextField)n;
               txtEmail = (TextField)n;
           }
           else if(n instanceof PasswordField)
           {
               txtPassword = (PasswordField)n;
           }
           else if(n instanceof TextFlow)
           {
               txtInfoBox = (TextFlow)n;
               Text text = new Text("Create your own Hack Attack account and start playing. Your username and email will not be visible for other players. Your displayname will be shown ingame");
               txtInfoBox = new TextFlow(text);
           }
           else if(n instanceof Button)
           {
               btnRegister = (Button)n;
               btnLogin = (Button)n;
               
               btnRegister.setOnMouseClicked(new EventHandler(){

                   @Override
                   public void handle(Event event) {
                       /**
                        * Close the current form and show the Loader form.
                        * We don't have to load the loader form again, because we haven't closed it.
                        */
                       Stage stage = (Stage)pane.getScene().getWindow();
                       stage.close();
                   }
                   
               });
           }
        }
    }
    
    public void Login()
    {
        
    }
    
}
