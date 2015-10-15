/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.ModuleName;
import java.util.List;
import hackattackfx.exceptions.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Jasper Rouwhorst
 */
public class GraphicsEngine{
    
    private FXMLDocumentController parent;
    private static GraphicsEngine instance;
    // The active spawntarget image. This value is null if no spawning is in progres.
    private SpawnTargetImage spawnTarget;
    // The active modulerange ellipse. This value is null if no module is hovered.
    private Ellipse moduleRange;
    private double updateTime;
    
    private Label lblPlayerName;
    private Label lblPlayerHealth;
    private Label lblPlayerBitcoins;
    
    private GraphicsEngine(){
        instance = this;
        parent = FXMLDocumentController.getInstance();
        lblPlayerName = (Label)parent.getNode("lblPlayerName");
        lblPlayerHealth = (Label)parent.getNode("lblPlayerHealth");
        lblPlayerBitcoins = (Label)parent.getNode("lblPlayerBitcoins");
        initialize();
        
    }
    
    public static GraphicsEngine getInstance(){
        return instance == null ? new GraphicsEngine() : instance;
    }
    
    private void initialize(){
        GameEngine.getInstance().setOnTickCompleteListener(new GameEngine.OnCompleteTick() {

            @Override
            public void tickComplete(long elapsedtime) {
                update();
            }
            
        });
    }
    
    /**
     * The Scene is the main window the whole application runs in
     * @return The main AnchorPane
     */
    public AnchorPane getScene(){
        return parent.getScene();
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
    
    /**
     * Check if the give {@link Node} exists then remove it from the UI.
     * @param n The node to despawn
     */
    public void deSpawn(Node n) throws InvalidObjectException{
        if(!parent.getAllNodes().contains(n)){
            throw new InvalidObjectException("The despawned object does not exist");
        }
        parent.removeNode(n);
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
                        ModuleImage mi = (ModuleImage)n;
                        
                        if(mi.showRange()){
                            if(moduleRange == null){
                                drawModuleRange((Module)mi.getReference());
                            }
                        }else{
                            if(moduleRange != null){
                                try {
                                    deSpawn(moduleRange);
                                    moduleRange = null;
                                } catch (InvalidObjectException ex) {
                                    Logger.getLogger(GraphicsEngine.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        
                        
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
    public SpawnTargetImage drawModuleSpawnTarget(ModuleName module){
        
        switch(module){
            case BITCOIN_MINER:
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_BITCOINMINER_1);
                break;
            case SOFTWARE_INJECTOR:
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_SOFTWAREINJECTOR_1);
                break;
            case CPU_UPGRADE:
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_CPUUPGRADE_1);
                break;
            case SNIPER_ANTIVIRUS:
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_SNIPER_1);
                break;
            case BOTTLECAP_ANTIVIRUS:
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1);
                break;
            case SCALE_ANTIVIRUS:
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_SCALE_1);
                break;
            case MUSCLE_ANTIVIRUS:
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1);
                break;
        }
        
        File file = new File("src/hackattackfx/resources/DefenceSpawnTarget.png");
        Image targetimage = new Image(file.toURI().toString());
        spawnTarget.setImage(targetimage);
        parent.addNode(spawnTarget);
        parent.getScene().setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>(){

            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                spawnTarget.setX(event.getSceneX() - (spawnTarget.getImage().getWidth()/2));
                spawnTarget.setY(event.getSceneY() - (spawnTarget.getImage().getHeight()/2));
            }

        });
        
        return spawnTarget;
    }
    
    public Ellipse drawModuleRange(Module module){
        Ellipse rangecircle = null;
        if(module instanceof Defense){
            Defense defense = (Defense)module;
            rangecircle = new Ellipse(defense.getRange(), defense.getRange());
        }
        rangecircle.setCenterX(module.getPosition().x);
        rangecircle.setCenterY(module.getPosition().y);
        rangecircle.setStroke(Color.BLACK);
        rangecircle.setFill(null);
        rangecircle.setStrokeWidth(3);
        parent.addNode(rangecircle);
        moduleRange = rangecircle;
        return rangecircle;
    }
    
    public void drawSpellRange(Spell spell){
        
    }
    
    public void drawLabels(String name, double health, double bitcoins){
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                lblPlayerName.setText(String.format("Playername = %s", name));
                lblPlayerHealth.setText(String.format("Health: %s", health));
                lblPlayerBitcoins.setText(String.format("Bitcoins: %s", bitcoins));
            }
        
        });
        
    }
    
}
