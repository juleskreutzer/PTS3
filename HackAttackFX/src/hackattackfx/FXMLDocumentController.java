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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
    private SoundEngine sEngine;
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
        sEngine = SoundEngine.getInstance();
        sEngine.playThemeSong();
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
     * This method is used to find a node in the scene. 
     * @param id The id of the node that's given runtime or whithin the scene builder.
     * @param parent The parent container the node will be searched in. If this is null, the main scene will be used as parent.
     * @return The node with the corresponding id.
     */
    public Node getNode(String id, Pane parent){
        ObservableList<Node> list;
        if(parent == null){
            list = window.getChildren();
        }else{
            list = parent.getChildren();
        }
        for(Node n : list){
            if(n instanceof AnchorPane){
                AnchorPane p = (AnchorPane)n;
                Node node = getNode(id, p);
                if(node != null){
                    return node;
                }
            }
            String s = n.getId();
            if (n.getId() != null && s.equals(id)){
                return n;
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
