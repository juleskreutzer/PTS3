/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.InvalidMinionTypeException;
import hackattackfx.exceptions.InvalidSpellNameException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author juleskreutzer
 */
public class FXMLLoaderController implements Initializable {
    
    @FXML
    ProgressBar progressBar = new ProgressBar(0.0);  
    
    
    
    public FXMLLoaderController() throws IOException, InvalidMinionTypeException, InvalidSpellNameException
    {   
        Data data = new Data(new Data.UpdateProgress() {

            @Override
            public void update(double value) {
                progressBar.setProgress(value);
            }
        });
        progressBar.setProgress(0.50);
        
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
