/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author Jasper Rouwhorst
 */
public class FXMLDocumentController implements Initializable {
    
    GraphicsEngine gEngine;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gEngine = GraphicsEngine.getInstance();
        gEngine.initialize(url, rb);
    }    
    
    
}
