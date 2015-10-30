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
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

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
    private Label errorLabel;
    private Label lostLabel;
    
    private ImageView errorImage;
    
    private GraphicsEngine(){
        instance = this;
        parent = FXMLDocumentController.getInstance();
        lblPlayerName = (Label)parent.getNode("lblPlayerName");
        lblPlayerHealth = (Label)parent.getNode("lblPlayerHealth");
        lblPlayerBitcoins = (Label)parent.getNode("lblPlayerBitcoins");
        errorLabel = (Label)parent.getNode("errorLabel");
        lostLabel = (Label)parent.getNode("lostLabel");
        errorImage = (ImageView)parent.getNode("errorImage");
        File file = new File("src/hackattackfx/resources/error.png");
        Image image = new Image(file.toURI().toString());
        errorImage.setImage(image);
        errorImage.setVisible(false);
        errorLabel.setVisible(false);
        lostLabel.setVisible(false);
        
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
        Node n = parent.getNode(id);
        return n;
    }
    
    public ObservableList getNodes()
    {
        return parent.getAllNodes();
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
            Minion m = (Minion)object;
            MinionImage mi = new MinionImage(m);
            parent.addNode(mi);
            parent.addNode(mi.getHealthBar());
        }else if(object instanceof Module){
            Module m = (Module) object;
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
                        Rectangle hb = mi.getHealthBar();
                        Minion m = ((MinionImage)n).getMinion();
                        
                        if (m.getHealth() > 0){
                            mi.setX(m.getPosition().x - (mi.getImage().getWidth()/2));
                            mi.setY(m.getPosition().y - (mi.getImage().getHeight()/2));
                            hb.setX(mi.getX());
                            hb.setY(mi.getY()+mi.getImage().getHeight());
                            hb.setWidth((mi.getImage().getWidth()/100) * m.getHealth());
                            
                        }
                        else{
                            try {
                                deSpawn(n);
                                deSpawn(hb);
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(GraphicsEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }else if(n instanceof ModuleImage){
                        ModuleImage mi = (ModuleImage)n;
                        
                        if(mi.showRange()){
                            if(moduleRange == null){
                                drawModuleRange((Module)mi.getReference());
                            }
                        }else{
                                try {
                                    // loops through all nodes, if it is an ModuleRange node, remove it.
                                        Iterator i = parent.getAllNodes().iterator();
                                        while (i.hasNext())
                                        {
                                            Node node = (Node)i.next();
                                            if ("ModuleRange".equals(node.getId())) {
                                                deSpawn(node);
                                            }
                                        }
                                    moduleRange = null;
                                } catch (InvalidObjectException ex) {
                                    Logger.getLogger(GraphicsEngine.class.getName()).log(Level.SEVERE, null, ex);
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
        
        File file = null;
        
        File unavailableFile = new File("src/hackattackfx/resources/unavailable.png");
        Image unavailableTargetImage = new Image(unavailableFile.toURI().toString());
        Image availableTargetImage;
        
        switch(module){
            case BITCOIN_MINER:
                file = new File("src/hackattackfx/resources/DefenceSpawnTarget.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_BITCOINMINER_1, unavailableTargetImage, availableTargetImage);
                break;
            case SOFTWARE_INJECTOR:
                file = new File("src/hackattackfx/resources/DefenceSpawnTarget.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_SOFTWAREINJECTOR_1, unavailableTargetImage, availableTargetImage);
                break;
            case CPU_UPGRADE:
                file = new File("src/hackattackfx/resources/DefenceSpawnTarget.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_CPUUPGRADE_1, unavailableTargetImage, availableTargetImage);
                break;
            case SNIPER_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/interface/module/40x40/sniper_module.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_SNIPER_1, unavailableTargetImage, availableTargetImage);
                break;
            case BOTTLECAP_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/interface/module/40x40/bottlecap_module.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1, unavailableTargetImage, availableTargetImage);
                break;
            case SCALE_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/interface/module/40x40/scale_module.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_SCALE_1, unavailableTargetImage, availableTargetImage);
                break;
            case MUSCLE_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/interface/module/40x40/muscle_module.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1, unavailableTargetImage, availableTargetImage);
                break;
        }
        
        Image targetimage = new Image(file.toURI().toString());
        spawnTarget.setImage(targetimage);
        spawnTarget.setOpacity(0.5);
        parent.addNode(spawnTarget);
        
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
        rangecircle.setId("ModuleRange");
        moduleRange = rangecircle;
        parent.addNode(rangecircle);
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
    
    public void showEndGame(String name)
    {
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                lostLabel.setText(String.format("You lost, %s", name));
                lostLabel.setVisible(true);
            }
            
        });
    }

    
    /**
     * Display an error message for the user. First show the error image and error label, than, after 5 seconds, make them magicaly disappear
     * @param message Error message to be shown
     */
    public void showError(String message)
    {
        Timer timer = new Timer();
        
        
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                errorImage.setVisible(true);
                errorLabel.setText(String.format("%s", message));
                errorLabel.setVisible(true);
            }
        });
        
        timer.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run() {
                errorImage.setVisible(false);
                errorLabel.setVisible(false);
            }
            
        }, 0 , 5000);
    }
}
