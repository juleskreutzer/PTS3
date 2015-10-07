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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author jaspe_000
 */
public class FXMLLoaderController implements Initializable {

    private ProgressBar progressBar;
    
    @FXML
    private Pane pane;
    
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
        
       try {
           Data data = new Data(new Data.UpdateProgress() {
               
               @Override
               public void update(double value) {
                   progressBar.setProgress(value);
               }
           });
       } catch (IOException ex) {
           Logger.getLogger(FXMLLoaderController.class.getName()).log(Level.SEVERE, null, ex);
       } catch (InvalidMinionTypeException ex) {
           Logger.getLogger(FXMLLoaderController.class.getName()).log(Level.SEVERE, null, ex);
       } catch (InvalidSpellNameException ex) {
           Logger.getLogger(FXMLLoaderController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
}
