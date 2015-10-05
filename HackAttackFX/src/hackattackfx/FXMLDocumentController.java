/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jasper Rouwhorst
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private AnchorPane window;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("User dir: "+System.getProperty("user.dir"));
        File minion = new File("Byte.png");
        Image image = new Image(minion.toURI().toString());
        ImageView iv = new ImageView();
        iv.setX(100);
        iv.setY(100);
        iv.setImage(image);
        System.out.println("Window childer: "+window.getChildren().toString());
        window.getChildren().add(iv);
    }    
    
}
