/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jasper Rouwhorst
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane window;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private ImageView errorImage;
    
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
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                window.getChildren().add(node);
            }
            
        });
    }
    
    public void removeNode(Node node){
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                window.getChildren().remove(node);
            }
            
        });
    }
    
    public static FXMLDocumentController getInstance(){
        return instance == null ? new FXMLDocumentController() : instance;
    }
    
    /**
     * Returns a node selected by the given id.
     * @param fxid 
     * @return If found the corresponding Node. If not return null
     */
    public Node getNode(String id){
        ObservableList list = window.getChildren();
        for(Object n : list){
            if(n instanceof Node){
                Node node = (Node)n;
                if(node instanceof Parent){
                    Parent p = (Parent)node;
                    for(Node child : p.getChildrenUnmodifiable()){
                        String s = child.getId();
                        if (child.getId() != null && s.equals(id)){
                            return child;
                        }
                    }
                }
                String s = node.getId();
                if (node.getId() != null && s.equals(id)){
                    return node;
                }
            }
        }
        return null;
    }
    
    public AnchorPane getScene(){
        return window;
    }
    
    public ObservableList getAllNodes(){
        return window.getChildren();
    }
    
    
}
