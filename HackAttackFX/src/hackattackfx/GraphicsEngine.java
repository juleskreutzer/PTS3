/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.Effect;
import hackattackfx.enums.ModuleName;
import java.util.List;
import hackattackfx.exceptions.*;
import java.awt.Point;
import java.io.File;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    
    private ImageView pauseButton;
    private Image pauseImage;
    private Image playImage;
    private Label lblCurrentWave;
    private Label lblPlayerName;
    private Label lblPlayerHealth;
    private Label lblPlayerBitcoins;
    private Label errorLabel;
    private Label lblStatsName;
    private Label lblStatsDescription;
    private Label lblStatsDamage;
    private Label lblStatsLevel;
    private Label lblStatsROF;
    private Label lblStatsEffect;
    private Label lblStatsRange;
    private Label lblStatsCosts;
    
    private ImageView errorImage;
    
    private GraphicsEngine(){
        instance = this;
        parent = FXMLDocumentController.getInstance();
        pauseButton = (ImageView)parent.getNode("btnPause", null);
        File pause = new File("src/hackattackfx/resources/interface/Icons/PauseButton.png");
        pauseImage = new Image(pause.toURI().toString());
        File play = new File("src/hackattackfx/resources/interface/Icons/PlayButton.png");
        playImage = new Image(play.toURI().toString());
        lblCurrentWave = (Label)parent.getNode("lblCurrentWave",null);
        lblPlayerName = (Label)parent.getNode("lblPlayerName",null);
        lblPlayerHealth = (Label)parent.getNode("lblPlayerHealth",null);
        lblPlayerBitcoins = (Label)parent.getNode("lblPlayerBitcoins",null);
        lblStatsName = (Label)parent.getNode("lblStatsName",null);
        lblStatsDescription = (Label)parent.getNode("lblStatsDescription",null);
        lblStatsDamage = (Label)parent.getNode("lblStatsDamage",null);
        lblStatsLevel = (Label)parent.getNode("lblStatsLevel",null);
        lblStatsROF = (Label)parent.getNode("lblStatsROF",null);
        lblStatsEffect = (Label)parent.getNode("lblStatsEffect",null);
        lblStatsRange = (Label)parent.getNode("lblStatsRange",null);
        lblStatsCosts = (Label)parent.getNode("lblStatsCosts",null);
        errorLabel = (Label)parent.getNode("errorLabel",null);
        errorImage = (ImageView)parent.getNode("errorImage",null);
        File file = new File("src/hackattackfx/resources/error.png");
        Image image = new Image(file.toURI().toString());
        errorImage.setImage(image);
        errorImage.setVisible(false);
        errorLabel.setVisible(false);
        
        createEffect(lblCurrentWave);
        createEffect(lblPlayerName);
        createEffect(lblPlayerHealth);
        createEffect(lblPlayerBitcoins);
        
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
     * @param parent
     * @return 
     */
    public Node getNode(String id, Pane p){
        Node n = parent.getNode(id, p);
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
        
        boolean isMinion = false;
        Point position;
        double reward;
        double damage;
        if(n instanceof MinionImage)
        {
            Minion m = ((MinionImage)n).getMinion();
            isMinion = true;
            position = m.getPosition();
            reward = m.getReward();
            damage = m.getDamage();
            
            //drawEffect(isMinion, position, reward, damage);
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
                            hb.setWidth((mi.getImage().getWidth()/100) * m.getHealthInPercentage());
                            
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
                        
                        if(mi.hovered()){
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
    
    /**
     * Updates the module stats with the right values given by the parameter.
     * @param m The module the stats should be shown of. Can be null. If null empty values will be shown.
     */
    public void drawModuleStats(Module m){
        if(m != null){
            lblStatsName.setText(String.format("Name: %s",m.getName()));
            lblStatsDescription.setText(String.format("Description: %s","No description available"));
            lblStatsLevel.setText(String.format("Level: %d",m.getLevel()));
            /*if(GameEngine.getInstance().getPlayer().getBitcoins() < m.getCost()){
                lblStatsCosts.setStyle("-fx-text-fill: #ff0000;-fx-font-weight: bold");
            }else{
                lblStatsCosts.setStyle("-fx-color: black");
            }*/
            lblStatsCosts.setText(String.format("Costs: %d",Math.round(m.getCost())));
            if(m instanceof Defense){
                Defense d = (Defense)m;
                lblStatsROF.setText(String.format("Rate of fire: %d",d.getFrequecy()));
                lblStatsEffect.setText(String.format("Effect: %s",d.getEffectString()));
                lblStatsRange.setText(String.format("Range: %d",d.getRange()));
                lblStatsDamage.setText(String.format("Damage: %d",Math.round(d.getDamage())));
            }
        }else{
            lblStatsName.setText("Name:");
            lblStatsDescription.setText("Description:");
            lblStatsLevel.setText("Level");
            /*if(GameEngine.getInstance().getPlayer().getBitcoins() < m.getCost()){
                lblStatsCosts.setStyle("-fx-text-fill: #ff0000;-fx-font-weight: bold");
            }else{
                lblStatsCosts.setStyle("-fx-color: black");
            }*/
            lblStatsCosts.setText("Costs:");
            lblStatsROF.setText("Rate of fire:");
            lblStatsEffect.setText("Effect:");
            lblStatsRange.setText("Range:");
            lblStatsDamage.setText("Damage:");
        }
        
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
    
    public Ellipse drawSpellRange(Spell spell){
        Ellipse rangecircle = new Ellipse(spell.getRange(),spell.getRange());
        rangecircle.setStroke(Color.BLUEVIOLET);
        rangecircle.setStrokeWidth(3);
        rangecircle.setFill(Color.BLUE);
        rangecircle.setOpacity(0.7);
        rangecircle.setId("SpellRange");
        parent.addNode(rangecircle);
        return rangecircle;
    }
    
    /**
     * Draws the module of the given {@link ModuleName} and sets the corresponding enabled or disabled image.
     * @param name
     * @param enabled 
     */
    public void drawBaseModule(ModuleName name, boolean enabled){
        switch(name){
            case BITCOIN_MINER:
                File file;
                if(enabled){
                    file = new File("src/hackattackfx/resources/interface/Icons/Base/dark/Bitcoin.png");
                }else{
                    file = new File("src/hackattackfx/resources/interface/Icons/Base/light/Bitcoin.png");
                }
                ImageView miner = (ImageView)parent.getNode("buildBitcoinMiner",null);
                Image image = new Image(file.toURI().toString());
                miner.setImage(image);
                break;
            case CPU_UPGRADE:
                
                break;
            case SOFTWARE_INJECTOR:
                
                break;
        }
    }
    
    public void drawLabels(int wavenr, String name, double health, double bitcoins){
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                lblCurrentWave.setText(String.format("Wave: %d", wavenr));
                lblPlayerName.setText(String.format("Playername: %s", name));
                lblPlayerHealth.setText(String.format("Health: %s", health));
                lblPlayerBitcoins.setText(String.format("Bitcoins: %s", bitcoins));
            }
        });
        
    }
    
    /**
     * Draw a label to let the user know the effect for a minion. 
     * A FadeTransition object will be used to let the label fade away after 3 seconds.
     * @param effect Effect enum. Determines what will be showed for the user
     * @param position Point where the label well be drawn on the screen
     * @param reward Used when a minion is killed to show it's reward in bitcoins.
     * @param damage Used when a player is attacked to show the amount of damage the minion did to the player
     */
    public void drawEffect(Effect effect, Point position, double reward, double damage)
    {
        Label label;
        
        FadeTransition fadeOut = new FadeTransition(Duration.millis(3000));
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        
        switch(effect)
        {
            case DIE:
                // A minion has been killed
                label = new Label();
                label.setLayoutX(position.getX());
                label.setLayoutY(position.getY());
                label.setTextFill(Color.GREEN);
                label.setText(String.format("+ %s ฿", reward));
                parent.addNode(label);
                fadeOut.setNode(label);
                fadeOut.playFromStart();

                fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent event) {
                        parent.removeNode(label);
                    }


                });
                break;
            case REACHED_BASE:
                // The player has received damage
                label = new Label();
                label.setLayoutX(position.getX());
                label.setLayoutY(position.getY());
                label.setTextFill(Color.RED);
                label.setText(String.format("- %s HP", damage));
                parent.addNode(label);
                fadeOut.setNode(label);
                fadeOut.playFromStart();

                fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent event) {
                        parent.removeNode(label);
                    }


                });
                break;
            case SLOWED:
                label = new Label();
                label.setText("SLOWED");
                label.setTextFill(Color.GREEN);
                label.setLayoutX(position.getX());
                label.setLayoutY(position.getY());
                parent.addNode(label);
                fadeOut.setNode(label);
                fadeOut.playFromStart();
                
                fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent event) {
                    parent.removeNode(label);
                }
                    
                });
                
                break;
            case POISENED:
                label = new Label();
                label.setText("POISENED");
                label.setTextFill(Color.RED);
                label.setLayoutX(position.getX());
                label.setLayoutY(position.getY());
                parent.addNode(label);
                fadeOut.setNode(label);
                fadeOut.playFromStart();
                
                fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent event) {
                    parent.removeNode(label);
                }
                    
                });
                
                break;
            case SPLASH:
                label = new Label();
                label.setText("SPLASHED");
                label.setTextFill(Color.PURPLE);
                label.setLayoutX(position.getX());
                label.setLayoutY(position.getY());
                parent.addNode(label);
                fadeOut.setNode(label);
                fadeOut.playFromStart();
                
                fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent event) {
                    parent.removeNode(label);
                }
                    
                });
                
                break;
            case DECRYPTED:
                label = new Label();
                label.setText("DECRYPTED");
                label.setTextFill(Color.PURPLE);
                label.setLayoutX(position.getX());
                label.setLayoutY(position.getY());
                parent.addNode(label);
                fadeOut.setNode(label);
                fadeOut.playFromStart();
                
                fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent event) {
                    parent.removeNode(label);
                }
                    
                });
                
                break;
        }
    }
    
    public void showEndGame(String name)
    {
        Platform.runLater(new Runnable(){

            @Override
            public void run() {

                Text text = new Text();
                text.setText(String.format("Game over, %s", name));
                text.setFill(Color.RED);
                text.setStyle("-fx-font-size: 40");
                double height = parent.getScene().getHeight();
                double width = parent.getScene().getWidth();
                height -= 40;
                width -= 280;
                text.setLayoutX(width/2);
                text.setLayoutY(height/2);
                
                parent.addNode(text);
            }            
        });
    }
    
    public void setPauseButton(boolean running){
        if(running){
                pauseButton.setImage(pauseImage);
            }else{
                pauseButton.setImage(playImage);
            }
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
        
        timer.cancel();
    }
    
    /**
     * Create a text effect for the labels.
     */
    private void createEffect(Label text)
    {
      text.setTextFill(Color.web("#386db2"));
      text.setBlendMode(BlendMode.HARD_LIGHT);
      text.setFont(Font.font(java.awt.Font.DIALOG_INPUT, FontWeight.BOLD, 13));
      final Reflection reflection = new Reflection();
      reflection.setFraction(1.0);
      text.setEffect(reflection);
    }
}
