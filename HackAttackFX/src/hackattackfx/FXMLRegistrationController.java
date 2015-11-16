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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import hackattackfx.exceptions.*;
import java.security.Key;
import javafx.scene.paint.Color;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;
import org.json.*;
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
    
    private static final String algorithm = "AES";
    private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' }; 
    
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
                encryptedPassword = this.encrypt(password);
            
            /**
             * At this point, we have all values we need to create a new account.
             * We can construct the URL that we will send to the server. The URL should use the following scheme:
             * [POST]/register/{username}/{password}/{displayname}/{email}
             */
            
            System.out.print(encryptedPassword);
            String url = String.format("https://api.nujules.nl/register/%s/%s/%s/%s", username, encryptedPassword, displayName, email);
            JSONArray result = Data.sendPost(url);
            finishRegistration(result);
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
    
    private void finishRegistration(JSONArray data)
    {
        for(int i = 0; i < data.length(); i++)
        {
            JSONObject obj = data.getJSONObject(i);
            
            String type = "";
            try{
                // Something went wrong with the registration.
                // The Error key contains the error message we need to display.
                type = obj.getString("Error");
                errorLabel.setText(type);
                errorLabel.setTextFill(Color.RED);
                
            }
            catch(Exception ex)
            {
                System.out.print(ex.toString());
            }
            
            try{
                // The registration should be successfull when we arrive at this point.
                // The Success key contains the message we need to display.
                type = obj.getString("Success");
                errorLabel.setText(type);
                errorLabel.setTextFill(Color.GREEN);
            }
            catch(Exception ex)
            {
                System.out.print(ex.toString());
            }
        }
    }
    
}
