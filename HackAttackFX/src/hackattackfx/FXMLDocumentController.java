/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jasper Rouwhorst
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane window;
    
    private GraphicsEngine gEngine;
    private static FXMLDocumentController instance;
    
    // This constructor is public even this class represents a Singleton class.
    // This is because the Scene Builder creates an instance of this class using the constructor.
    // Besides that, this class should be considered as Singleton.
    public FXMLDocumentController(){
        instance = this;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gEngine = GraphicsEngine.getInstance();
    }    
    
    public void addNode(Node node){
        window.getChildren().add(node);
    }
    
    public static FXMLDocumentController getInstance(){
        return instance == null ? new FXMLDocumentController() : instance;
    }
    
    /**
     * Returns a node selected by the given id.
     * @param fxid 
     * @return If found the corresponding Node. If not return null
     */
    public Node getNode(String fxid){
        ObservableList list = window.getChildren();
        for(Object n : list){
            if(n instanceof Node){
                Node node = (Node)n;
                if(node.getId().equals(fxid)){
                    return node;
                }
            }
        }
        return null;
    }
    
    public ObservableList getAllNodes(){
        return window.getChildren();
    }
    
    
}
