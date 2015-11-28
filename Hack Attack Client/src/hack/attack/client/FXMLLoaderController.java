/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import hack.attack.client.exceptions.InvalidDefenseTypeException;
import hack.attack.client.exceptions.InvalidEffectException;
import hack.attack.client.exceptions.InvalidMinionTypeException;
import hack.attack.client.exceptions.InvalidSpellNameException;
import hack.attack.client.exceptions.LoginFailedException;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.security.Key;
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
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;
import org.json.*;

/**
 * FXML Controller class
 *
 * @author Jasper Rouwhorst
 */
public class FXMLLoaderController implements Initializable {

    private ProgressBar progressBar;
    private Button btnPlayGame;
    
    @FXML
    private Pane pane;
    @FXML
    private Label errorlabel;
    @FXML
    private Button playButton;
    @FXML
    private Button btnRegister;
    @FXML
    private TextField txtPlayerName;
    @FXML
    private Label lblPassword;
    @FXML
    private PasswordField txtPassword;
    
    Data data;
    
    private static final String algorithm = "AES";
    private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' }; 
    
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
    
    /**
     * Encrypt a value using the ecryptionKey.
     * @param Data Text that will be encrypted
     * @return Returns plaintext encrypted in a byte array
     */
    public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }
    
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, algorithm);
        return key;
    }
    
    private Object[] handleLogin(String username, String password) throws IOException, LoginFailedException
    {
        /**
        * At this point, we have all values we need so we can verify the data with the server.
        * We can construct the URL that we will send to the server. The URL should use the following scheme:
        * [POST]/login/{username}/{password}
        */
        String url = String.format("https://api.nujules.nl/login/%s/%s", username, password);
        JSONArray result = Data.sendPost(url);
        
        for(int i = 0; i < result.length(); i++)
        {
            JSONObject obj = result.getJSONObject(i);
            
            String status = "";
            
                status = obj.getString("status");
                if(status.equals("1"))
                {
                    int id = obj.getInt("id");
                    String displayName = obj.getString("displayName");
                    int score = obj.getInt("score");
                    String uName = obj.getString("username");
                    
                    Object[] array = {id, displayName, score, uName};
                    return array;
                }
                else
                {
                    throw new LoginFailedException("Username or password is incorrect.");
                }
            
        }
        return null;
    }
    
    public void playButtonClicked() throws FileNotFoundException
    {
        String username = txtPlayerName.getText();
        String password = txtPassword.getText();

        FXMLLoader gameloader = new FXMLLoader();
        Parent mainroot;
        try {
            // First we send the username and password to our API to check if it is correct.
            String encryptedPassword = encrypt(password);
            Object[] result = handleLogin(username, encryptedPassword);
            int id = (int)result[0];
            String displayName = (String)result[1];
            int score = (int)result[2];
            String uName = (String)result[3];

            Data.userIDPlayerA = id;
            Data.playerAName = username;
            Data.displayNameA = displayName;
            Data.scorePlayerA = score;
            
            mainroot = (Parent)gameloader.load(getClass().getResource("FXMLLobby.fxml").openStream());
            Stage stage  = (Stage)pane.getScene().getWindow();
            stage.close();
            Stage gamestage = new Stage();
            Scene scene = new Scene(mainroot);
            gamestage.setScene(scene);
            gamestage.setTitle("Hack Attack - Lobby");
            gamestage.show();

        } catch (IOException ex) {
            Logger.getLogger(FXMLLoaderController.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch(LoginFailedException ex) {
            errorlabel.setText("Username or password is incorrect.");
            errorlabel.setVisible(true);
        }
        catch (Exception ex) {
            Logger.getLogger(FXMLLoaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void registrationButtonClicked()
    {
        try{
            FXMLLoader registrationLoader = new FXMLLoader(getClass().getResource("FXMLRegistration.fxml"));
            Parent mainroot = (Parent)registrationLoader.load();                            
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
}
