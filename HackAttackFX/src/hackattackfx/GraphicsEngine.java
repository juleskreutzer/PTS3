/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.util.List;
import hackattackfx.exceptions.*;
import java.io.File;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Jasper Rouwhorst
 */
public class GraphicsEngine{
    
    private FXMLDocumentController parent;
    private static GraphicsEngine instance;
    
    private double updateTime;
    
    private GraphicsEngine(){
        instance = this;
        parent = FXMLDocumentController.getInstance();
        
        initialize();
        
    }
    
    public static GraphicsEngine getInstance(){
        return instance == null ? new GraphicsEngine() : instance;
    }
    
    private void initialize(){
        GameEngine.getInstance().setOnTickCompleteListener(new GameEngine.OnCompleteTick() {

            @Override
            public void tickComplete() {
                update();
            }
            
        });
    }
    
    /**
     * Returns a node selected by the given id
     * @param id
     * @return 
     */
    public Node getNode(String id){
        return parent.getNode(id);
    }
    
    /**
     * From the moment an object is spawned, it will be updated every tick.
     * This is the entry point for an object to be updated every tick.
     * @param object 
     * @throws hackattackfx.exceptions.DuplicateSpawnException 
     * @throws hackattackfx.exceptions.InvalidObjectException 
     */
    public void spawn(Object object) throws DuplicateSpawnException, InvalidObjectException{
        // Checks if the object doesn't exists already
        List<Node> list = parent.getAllNodes();
        for(Node node : list){
            if(node instanceof ObjectImage){
                ObjectImage image = (ObjectImage)node;
                if(image.getReference() == object){
                    throw new DuplicateSpawnException("This object already spawned!");
                }
            }
        }
        
        if(object instanceof Minion){
            parent.addNode(new MinionImage((Minion)object));
        }else if(object instanceof Module){
            parent.addNode(new ModuleImage((Module)object));
        }else if(object instanceof Spell){
            parent.addNode(new SpellImage((Spell)object));
        }else{
            throw new InvalidObjectException("The object you tried to spawn doesn't have a corresponding ObjectImage implementation");
        }
    }
    
    public double update(){
        draw();
        return 0;
    }
    
    private void draw(){
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                List<Node> nodes = parent.getAllNodes();

                for(Node n : nodes){
                    if(n instanceof MinionImage){
                        MinionImage mi = (MinionImage)n;
                        Minion m = ((MinionImage)n).getMinion();
                        mi.setX(m.getPosition().x - (mi.getImage().getWidth()/2));
                        mi.setY(m.getPosition().y - (mi.getImage().getHeight()/2));

                    }else if(n instanceof ModuleImage){

                    }else if(n instanceof SpellImage){

                    }
                }
            }
        });
    }
    
    public void drawRoad(Road road){
        for(Path p : road.getPaths()){
            PathImage image = new PathImage(p);
            parent.addNode(image);
        }
    }
    
    /**
     * Draws an holographic version of the module you want to build.
     * @param m The module the holographic version should be showed of
     */
    public void drawModuleSpawnTarget(Module m){
        
    }
    
    public void drawSpellRange(Spell spell){
        
    }
    
}
