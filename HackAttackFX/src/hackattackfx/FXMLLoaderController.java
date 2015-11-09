/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.InvalidDefenseTypeException;
import hackattackfx.exceptions.InvalidEffectException;
import hackattackfx.exceptions.InvalidMinionTypeException;
import hackattackfx.exceptions.InvalidSpellNameException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Jasper Rouwhorst
 */
public class FXMLLoaderController implements Initializable {

    private ProgressBar progressBar;
    private TextField txtPlayerName;
    private Button btnPlayGame;
    
    @FXML
    private Pane pane;
    private Label errorlabel;
    private Button playButton;
    private Button btnRegister;
    private TextField playerName;
    private Label lblPassword;
    private PasswordField txtPassword;
    Data data;
    
    
    public FXMLLoaderController() throws IOException, InvalidMinionTypeException, InvalidSpellNameException
    {  
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Node> list = pane.getChildren();
        for(Node node : list){
            if(node instanceof ProgressBar){
                progressBar = (ProgressBar)node;
            }
            if(node instanceof Button)
            {
                btnPlayGame = (Button)node;
                btnPlayGame.setOnMouseClicked(new EventHandler(){

                    @Override
                    public void handle(Event event) {
                        if(txtPlayerName.getText().length() > 0){
                            String name = txtPlayerName.getText();
                            Stage stage  = (Stage)pane.getScene().getWindow();
                            stage.close();

                            FXMLLoader gameloader = new FXMLLoader();
                            Parent mainroot;
                            try {
                                Data.playerAName = name;
                                mainroot = (Parent)gameloader.load(getClass().getResource("FXMLDocument.fxml").openStream());
                                Stage gamestage = new Stage();
                                Scene scene = new Scene(mainroot);
                                gamestage.setScene(scene);
                                gamestage.setTitle("Hack Attack");
                                gamestage.show();

                                GameEngine engine = GameEngine.getInstance();
                                engine.start();
                                
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLLoaderController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else
                        {
                            errorlabel.setVisible(true);
                            errorlabel.setText("Please enter a playername.");
                        }
                        
                    }
                    
                });
                btnRegister = (Button)node;
                btnRegister.setOnMouseClicked(new EventHandler(){

                    @Override
                    public void handle(Event event) {
                        Parent mainroot;
                        FXMLLoader registrationLoader = new FXMLLoader();
                        try{
                            mainroot = (Parent)registrationLoader.load(getClass().getResource("FXMLRegistration.fxml").openStream());                            
                            Stage registrationStage = new Stage();
                            Scene scene = new Scene(mainroot);
                            registrationStage.setScene(scene);
                            registrationStage.setTitle("Register your Hack Attack account");
                            registrationStage.show();
                        } catch(IOException ex)
                        {
                            System.out.print(ex.getMessage());
                        }
                    }
                });
            }
            if(node instanceof TextField)
            {
                txtPlayerName = (TextField)node;
            }
        }
    }
    
    public void initializeData(){
        try {
            try {
                data = new Data(new Data.UpdateProgress() {
                    
                    @Override
                    public void update(double value) {
                        progressBar.setProgress(value);
                        if(value >= 0.99){
                            progressBar.setProgress(1);
                            btnPlayGame.setDisable(false);
                            //Stage stage  = (Stage)pane.getScene().getWindow();
                            //stage.close();
                        }
                    }
                });
            } catch (InvalidDefenseTypeException ex) {
                Logger.getLogger(FXMLLoaderController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidEffectException ex) {
                Logger.getLogger(FXMLLoaderController.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (UnknownHostException to)
       {
//           Alert alert = new Alert(Alert.AlertType.ERROR, "Oops.. we can't connect to our service. Is your internet connection OK?\n" + to.toString());
//           alert.initModality(Modality.APPLICATION_MODAL);
//           alert.show();
           errorlabel.setVisible(true);
           errorlabel.setText("Oops.. we can't connect to our service. is your internet connection OK? \n" + to.toString());
           
       } catch (IOException ex) {
           errorlabel.setVisible(true);
           errorlabel.setText("Oops.. Something went wrong.\n" + ex.toString());
       } catch (InvalidMinionTypeException ex) {
           errorlabel.setVisible(true);
           errorlabel.setText("The miniontype is not recognized.");
       } catch (InvalidSpellNameException ex) {
           errorlabel.setVisible(true);
           errorlabel.setText("The spellname is not recognized.");
       }
    }
    
}
