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
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

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
    private Button btnRegister;
    @FXML
    private Button btnLogin;
    @FXML 
    private Label errorLabel;
    
    private static String encryptionKey = "ba278855787efa5173a90c6ac8721e14";
//    private static String IV = "AAAAAAAAAAAAAAAA";
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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
     * @param text Text that will be encrypted
     * @param secretKey Key that will be used for encryption
     * @return Returns plaintext encrypted in a byte array
     */
    private static String symmetricEncrypt(String text, String secretKey) {
        byte[] raw;
        String encryptedString;
        SecretKeySpec skeySpec;
        byte[] encryptText = text.getBytes();
        Cipher cipher;
        try {
            raw = Base64.decodeBase64(secretKey);
            skeySpec = new SecretKeySpec(raw, "AES");
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encryptedString = Base64.encodeBase64String(cipher.doFinal(encryptText));
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        
        return encryptedString;
    }
    
    public void Register()
    {
        try{
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String passwordConfirm = txtPasswordConfirm.getText();
            String displayName = txtDisplayName.getText();
            String email = txtEmail.getText();
            String encryptedPassword;
            
            // Check for empty fields
            if(username.equals("") || password.equals("") || passwordConfirm.equals("") || displayName.equals("") || email.equals(""))
            {
                throw new RegistrationFailedException("Please fill in all fields");
            }
            
            /**
             * Check if password and passwordConfirm are the same, if not, throw an error, else encrypt the password
             */
            if(!password.equals(passwordConfirm))
                throw new IncorrectPasswordException("Both passwords do not match.");
            else
                encryptedPassword = this.symmetricEncrypt(password, encryptionKey);
            
            /**
             * At this point, we have all values we need to create a new account.
             * We can construct the URL that we will send to the server. The URL should use the following scheme:
             * [POST]/register/{username}/{password}/{displayname}/{email}
             */
            
            System.out.print(encryptedPassword);
            String url = String.format("https://{0}", "test");
            System.out.print(url);
        } 
        catch(RegistrationFailedException | IncorrectPasswordException ex)
        {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText(ex.getMessage());
        }
        catch(Exception ex)
        {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Something went wrong, please try again");
            System.out.print(ex.toString());
        }
        
    }
    
}
