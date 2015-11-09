/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import hackattackfx.exceptions.*;
import javafx.scene.paint.Color;

/**
 *
 * @author juleskreutzer
 */
public class FXMLRegistrationController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private TextField txtDisplayName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtPasswordConfirm;
    @FXML
    private TextFlow txtInfoBox;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnLogin;
    @FXML 
    private Label errorLabel;
    
    private String encryptionKey = "HAckATtaCKenCRypTIonKeY!@#%$";
    private String IV = "AAAAAAAAAAAAAAAA";
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Node> nodes = pane.getChildren();
        for(Node n : nodes)
        {
           if(n instanceof TextFlow)
           {
               txtInfoBox = (TextFlow)n;
               Text text = new Text("Create your own Hack Attack account and start playing. Your username and email will not be visible for other players. Your displayname will be shown ingame");
               txtInfoBox = new TextFlow(text);
           }
        }
    }
    
    /**
     * Show the login window again
     * We do not hava to initialize it, because we haven't closed it.
     */
    public void Login()
    {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Encrypt a value using the ecryptionKey.
     * https://gist.github.com/bricef/2436364
     * @param plainText Text that will be encrypted
     * @param encryptionKey Key that will be used for encryption
     * @return Returns plaintext encrypted in a byte array
     */
    private byte[] encrypt(String plainText, String encryptionKey)
    {
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
            SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
            return cipher.doFinal(plainText.getBytes("UTF-8"));
            
        } catch(NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex)
        {
            System.out.print(ex.toString());
        }
        return null;
    }
    
    public void Register()
    {
        try{
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String passwordConfirm = txtPasswordConfirm.getText();
            String displayName = txtDisplayName.getText();
            String email = txtEmail.getText();
            byte[] encryptedPassword;
            
            /**
             * Check if password and passwordConfirm are the same, if not, throw an error, else encrypt the password
             */
            if(!password.equals(passwordConfirm))
                throw new IncorrectPasswordException("Both passwords do not match.");
            else
                encryptedPassword = this.encrypt(password, encryptionKey);
            
            String pass = new String(encryptedPassword);
        } 
        catch(IncorrectPasswordException ex)
        {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText(ex.getMessage());
        }
        catch(Exception ex)
        {
            System.out.print(ex.getMessage());
        }
        
    }
    
}
