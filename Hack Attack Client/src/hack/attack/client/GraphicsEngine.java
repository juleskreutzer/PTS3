/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import hack.attack.rmi.Defense;
import hack.attack.client.FXMLDocumentController.Window;
import hack.attack.rmi.ClientAdapter;
import hack.attack.rmi.Spell;
import hack.attack.rmi.Minion;
import hack.attack.rmi.Module;
import hack.attack.rmi.Effect;
import hack.attack.rmi.ModuleName;
import java.util.List;
import hack.attack.client.exceptions.*;
import hack.attack.rmi.ITargetable;
import hack.attack.rmi.SpellTemplate;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
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
    
    private Label lblCurrentWave;
    private Label lblPlayerAName;
    private Label lblPlayerAHealth;
    private Label lblPlayerABitcoins;
    private Label lblPlayerBName;
    private Label lblPlayerBHealth;
    private Label errorLabel;
    private Label lblStatsName;
    private Label lblStatsDescription;
    private Label lblStatsDamage;
    private Label lblStatsLevel;
    private Label lblStatsROF;
    private Label lblStatsEffect;
    private Label lblStatsRange;
    private Label lblStatsCosts;
    
    private Rectangle highlight;
    
    private ImageView errorImage;
    
    private ClientAdapter adapter;
    private ImageView imageViewUpgrade;
    private ImageView imageViewSell;
    
    /* Fields for spell cooldown */
    private long SpellCorrupt = 0;
    private long SpellEncrypt = 0;
    private long SpellDisrupt = 0;
    private long SpellLockdown = 0;
    private long SpellVirusscan = 0;
    private long SpellFirewall = 0;
    
    private GraphicsEngine(){
        instance = this;
        
        
        adapter = ClientAdapter.getInstance();
        
    }
    
    public GraphicsEngine initialize(FXMLDocumentController controller){
        parent = controller;
        
        lblCurrentWave = (Label)parent.getNode("lblCurrentWave",null);
        lblPlayerAName = (Label)parent.getNode("lblPlayerAName",null);
        lblPlayerAHealth = (Label)parent.getNode("lblPlayerAHealth",null);
        lblPlayerABitcoins = (Label)parent.getNode("lblPlayerABitcoins",null);
        
        lblPlayerBName = (Label)parent.getNode("lblPlayerBName",null);
        lblPlayerBHealth = (Label)parent.getNode("lblPlayerBHealth",null);
        
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
        File file = new File("src/hack/attack/client/resources/error.png");
        Image image = new Image(file.toURI().toString());
        errorImage.setImage(image);
        errorImage.setVisible(false);
        errorLabel.setVisible(false);
      
        createEffect(lblCurrentWave);
        createEffect(lblPlayerAName);
        createEffect(lblPlayerAHealth);
        createEffect(lblPlayerABitcoins);
        createEffect(lblPlayerBName);
        createEffect(lblPlayerBHealth);
        
        Map map = Map.getInstance();
        drawRoad(map.getRoadA(), map.getRoadB());
        return this;
    }
    
    public static GraphicsEngine getInstance(){
        return instance == null ? new GraphicsEngine() : instance;
    }
    
    /**
     * The Scene is the main window the whole application runs in
     * @return The main AnchorPane
     */
    public Pane getScene(Window window){
        return parent.getScene(window);
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
    
    public ArrayList<Node> getAllNodes()
    {
        ObservableList nodes1 =  parent.getAllNodes(Window.TOP);
        ObservableList nodes2 =  parent.getAllNodes(Window.DOWN);
        
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.addAll(nodes1);
        nodes.addAll(nodes2);
        return nodes;
        
    }
    
    
    /**
     * From the moment an object is spawned, it will be updated every tick.
     * This is the entry point for an object to be updated every tick.
     * @param object 
     * @throws hack/attack/client.exceptions.DuplicateSpawnException 
     * @throws hack/attack/client.exceptions.InvalidObjectException 
     */
    public void spawn(Object object, int uID) throws DuplicateSpawnException, InvalidObjectException{
        // Checks if the object doesn't exists already
        
        int currentID = ClientAdapter.getInstance().getCurrentUserID();
        FXMLDocumentController.Window window = uID == currentID ? FXMLDocumentController.Window.DOWN : FXMLDocumentController.Window.TOP;
        
        List<Node> list = parent.getAllNodes(window);
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
            FXMLDocumentController.Window w = uID == currentID? FXMLDocumentController.Window.TOP : FXMLDocumentController.Window.DOWN;
            if(w == Window.TOP){
                m.setPosition(new Point(1366 - m.getPosition().x, m.getPosition().y));
            }
            parent.addNode(mi, w);
            parent.addNode(mi.getHealthBar(), w);
        }else if(object instanceof Module){
            Module m = (Module) object;
            FXMLDocumentController.Window w = uID == currentID? FXMLDocumentController.Window.TOP : FXMLDocumentController.Window.DOWN;
            if(w == Window.DOWN){
                m.setPosition(new Point(1366 - m.getPosition().x, m.getPosition().y));
                parent.addNode(new ModuleImage((Module)object, false), window);
            }
            else{
                parent.addNode(new ModuleImage((Module)object, true), window);
            }
        }else if(object instanceof Spell){
            //parent.addNode(new SpellImage((Spell)object), window);
        }else{
            throw new InvalidObjectException("The object you tried to spawn doesn't have a corresponding ObjectImage implementation");
        }
    }
    
    /**
     * Check if the give {@link Node} exists then remove it from the UI.
     * @param n The node to despawn
     */
    public void deSpawn(Object object, int uID) throws InvalidObjectException{
        
        int currentID = ClientAdapter.getInstance().getCurrentUserID();
        FXMLDocumentController.Window window = uID == currentID ? FXMLDocumentController.Window.DOWN : FXMLDocumentController.Window.TOP;
        
        ArrayList<Node> nodes = this.getAllNodes();
        
        for(Node n : nodes)
        {
            if(object instanceof Minion && n instanceof MinionImage)
            {
                FXMLDocumentController.Window w = uID == currentID? FXMLDocumentController.Window.TOP : FXMLDocumentController.Window.DOWN;
                Minion minion = (Minion)object;
                Minion m = ((MinionImage)n).getMinion();
                Rectangle hb = ((MinionImage)n).getHealthBar();
                
                if(minion.getMinionID() == m.getMinionID()){
                    if(m.reachedBase()){
                        drawEffect(Effect.REACHED_BASE, m, w);
                    }else{
                        drawEffect(Effect.DIE, m, w);
                    }
                    parent.removeNode(hb, w);
                    parent.removeNode(n, w);
                    break;
                }
            }
            
            if(object instanceof SpellTemplate && n instanceof Ellipse)
            {
                parent.removeNode(n, FXMLDocumentController.Window.DOWN);
                
            }
//            if(n instanceof ObjectImage){
//                ObjectImage image = (ObjectImage)n;
//                if(object == image.getReference()){
//                    parent.removeNode(n, window);
//                }
//            }
            if(object instanceof SpawnTargetImage && n instanceof SpawnTargetImage){
                parent.removeNode(n, window);
                break;
            }
        }
    }
    
    
    
    public double update(int uID){
        draw();
        return 0;
    }
    
    private void draw(){
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                List<Node> nodes1 = parent.getAllNodes(FXMLDocumentController.Window.DOWN);
                List<Node> nodes2 = parent.getAllNodes(FXMLDocumentController.Window.TOP);
                
                List<Node> nodes = new ArrayList<>();
                nodes.addAll(nodes1);
                nodes.addAll(nodes2);
                
                for(Node n : nodes){
                    if(n instanceof MinionImage){
                        MinionImage mi = (MinionImage)n;
                        Rectangle hb = mi.getHealthBar();
                        Minion m = ((MinionImage)n).getMinion();
                        
                        mi.setX(m.getPosition().x - (mi.getImage().getWidth()/2));
                        mi.setY(m.getPosition().y - (mi.getImage().getHeight()/2));
                        hb.setX(mi.getX());
                        hb.setY(mi.getY()+mi.getImage().getHeight());
                        hb.setWidth((mi.getImage().getWidth()/100) * m.getHealthInPercentage());

                    }else if(n instanceof ModuleImage){
                        ModuleImage mi = (ModuleImage)n;
                      
                        if(mi.hovered()){
                            if(moduleRange == null){
                                drawModuleRange((Module)mi.getReference());
                            }
                        }else{
                            // loops through all nodes, if it is an ModuleRange node, remove it.
                            Iterator i = nodes.iterator();
                            while (i.hasNext())
                            {
                                Node node = (Node)i.next();
                                if ("ModuleRange".equals(node.getId())) {
                                    parent.removeNode(node, FXMLDocumentController.Window.DOWN);
                                }
                            }
                            moduleRange = null;
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
    
    public void drawRoad(Road roadA, Road roadB){
        System.out.print("Drawing road A...");
        for(Path p : roadA.getPaths()){
            PathImage image = new PathImage(p);
            parent.addNode(image, FXMLDocumentController.Window.DOWN);
        }
        
        System.out.print("Drawing road B...");
        for(Path p : roadB.getPaths()){
            PathImage image = new PathImage(p);
            parent.addNode(image, FXMLDocumentController.Window.TOP);
        }
    }
    
    /**
     * Draws an holographic version of the module you want to build.
     * @param m The module the holographic version should be showed of
     */
    public SpawnTargetImage drawModuleSpawnTarget(ModuleName module){
        
        File file = null;
        
        File unavailableFile = new File("src/hack/attack/client/resources/unavailable.png");
        Image unavailableTargetImage = new Image(unavailableFile.toURI().toString());
        Image availableTargetImage;
        
        switch(module){
            case BITCOIN_MINER:
                file = new File("src/hack/attack/client/resources/DefenceSpawnTarget.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_BITCOINMINER_1, unavailableTargetImage, availableTargetImage);
                break;
            case SOFTWARE_INJECTOR:
                file = new File("src/hack/attack/client/resources/DefenceSpawnTarget.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_SOFTWAREINJECTOR_1, unavailableTargetImage, availableTargetImage);
                break;
            case CPU_UPGRADE:
                file = new File("src/hack/attack/client/resources/DefenceSpawnTarget.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_CPUUPGRADE_1, unavailableTargetImage, availableTargetImage);
                break;
            case SNIPER_ANTIVIRUS:
                file = new File("src/hack/attack/client/resources/interface/module/40x40/sniper_module.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_SNIPER_1, unavailableTargetImage, availableTargetImage);
                break;
            case BOTTLECAP_ANTIVIRUS:
                file = new File("src/hack/attack/client/resources/interface/module/40x40/bottlecap_module.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1, unavailableTargetImage, availableTargetImage);
                break;
            case SCALE_ANTIVIRUS:
                file = new File("src/hack/attack/client/resources/interface/module/40x40/scale_module.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_SCALE_1, unavailableTargetImage, availableTargetImage);
                break;
            case MUSCLE_ANTIVIRUS:
                file = new File("src/hack/attack/client/resources/interface/module/40x40/muscle_module.png");
                availableTargetImage = new Image(file.toURI().toString());
                spawnTarget = new SpawnTargetImage(Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1, unavailableTargetImage, availableTargetImage);
                break;
        }
        
        Image targetimage = new Image(file.toURI().toString());
        spawnTarget.setImage(targetimage);
        spawnTarget.setOpacity(0.5);
        parent.addNode(spawnTarget, FXMLDocumentController.Window.DOWN);
        
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
        parent.addNode(rangecircle, FXMLDocumentController.Window.DOWN);
        return rangecircle;
    }
    
    public Ellipse drawSpellRange(Spell spell){
        Ellipse rangecircle = new Ellipse(spell.getRange(),spell.getRange());
        rangecircle.setStroke(Color.BLUEVIOLET);
        rangecircle.setStrokeWidth(3);
        rangecircle.setFill(Color.BLUE);
        rangecircle.setOpacity(0.7);
        rangecircle.setId("SpellRange");
        parent.addNode(rangecircle, FXMLDocumentController.Window.DOWN);
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
                    file = new File("src/hack/attack/client/resources/interface/Icons/Base/dark/Bitcoin.png");
                }else{
                    file = new File("src/hack/attack/client/resources/interface/Icons/Base/light/Bitcoin.png");
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
    
    public void drawLabels(int wavenr, String namea, double healtha, double bitcoinsa, String nameb, double healthb){
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                lblCurrentWave.setText(String.format("Wave: %d", wavenr));
                lblPlayerAName.setText(String.format("Playername: %s", namea));
                lblPlayerAHealth.setText(String.format("Health: %s", healtha));
                lblPlayerABitcoins.setText(String.format("Bitcoins: %s", bitcoinsa));
                lblPlayerBName.setText(String.format("Enemy name: %s", nameb));
                lblPlayerBHealth.setText(String.format("Enemy health: %s", healthb));
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
    public void drawEffect(Effect effect, ITargetable target, FXMLDocumentController.Window window)
    {
        Label label;
        
        FadeTransition fadeOut = new FadeTransition(Duration.millis(3000));
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        
        if(target instanceof Minion)
        {
            Minion m = (Minion)target;
            Point position = m.getPosition();
            double reward = m.getReward();
            double damage = m.getDamage();
            
            switch(effect)
            {
                case DIE:
                    // A minion has been killed
                    label = new Label();
                    label.setLayoutX(position.getX());
                    label.setLayoutY(position.getY());
                    label.setTextFill(Color.GREEN);
                    label.setText(String.format("+ %s ฿", reward));
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                            parent.removeNode(label, window);
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
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                            parent.removeNode(label, window);
                        }


                    });
                    break;
                case SLOWED:
                    label = new Label();
                    label.setText("SLOWED");
                    label.setTextFill(Color.GREEN);
                    label.setLayoutX(position.getX());
                    label.setLayoutY(position.getY());
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                        parent.removeNode(label, window);
                    }

                    });

                    break;
                case POISENED:
                    label = new Label();
                    label.setText("POISENED");
                    label.setTextFill(Color.RED);
                    label.setLayoutX(position.getX());
                    label.setLayoutY(position.getY());
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                        parent.removeNode(label, window);
                    }

                    });

                    break;
                case SPLASH:
                    label = new Label();
                    label.setText("SPLASHED");
                    label.setTextFill(Color.PURPLE);
                    label.setLayoutX(position.getX());
                    label.setLayoutY(position.getY());
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                        parent.removeNode(label, window);
                    }

                    });

                    break;
                case DECRYPTED:
                    label = new Label();
                    label.setText("DECRYPTED");
                    label.setTextFill(Color.PURPLE);
                    label.setLayoutX(position.getX());
                    label.setLayoutY(position.getY());
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                        parent.removeNode(label, window);
                    }

                    });

                    break;
                case BUFFED:
                    label = new Label();
                    label.setText("BUFFED");
                    label.setTextFill(Color.PURPLE);
                    label.setLayoutX(position.getX());
                    label.setLayoutY(position.getY());
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                        parent.removeNode(label, window);
                    }

                    });
                    break;
                case STOPPED:
                    label = new Label();
                    label.setText("STOPPED");
                    label.setTextFill(Color.PURPLE);
                    label.setLayoutX(position.getX());
                    label.setLayoutY(position.getY());
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                        parent.removeNode(label, window);
                    }

                    });
                    break;
                case ENCRYPT:
                    label = new Label();
                    label.setText("ENCRYPTED");
                    label.setTextFill(Color.PURPLE);
                    label.setLayoutX(position.getX());
                    label.setLayoutY(position.getY());
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                        parent.removeNode(label, window);
                    }

                    });
                    break;
            }
        }
        else if(target instanceof Defense)
        {
            fadeOut = new FadeTransition(Duration.millis(10000));
            Defense d = (Defense)target;
            Point position = d.getPosition();
            
            switch(effect)
            {
                case BUFFED:
                    label = new Label();
                    label.setText("RANGE INCREASED\nDAMAGE INCREASED");
                    label.setTextFill(Color.PURPLE);
                    label.setLayoutX(position.getX());
                    label.setLayoutY(position.getY());
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                        parent.removeNode(label, window);
                    }

                    });
                    break;
                case SPLASH:
                    label = new Label();
                    label.setText("DISABLED");
                    label.setTextFill(Color.PURPLE);
                    label.setLayoutX(position.getX());
                    label.setLayoutY(position.getY());
                    parent.addNode(label, window);
                    fadeOut.setNode(label);
                    fadeOut.playFromStart();

                    fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

                        @Override
                        public void handle(ActionEvent event) {
                        parent.removeNode(label, window);
                    }

                    });
                    break;
            }
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
                double height = parent.getScene(Window.MAIN).getHeight();
                double width = parent.getScene(Window.MAIN).getWidth();
                height -= 40;
                width -= 280;
                text.setLayoutX(width/2);
                text.setLayoutY(height/2);
                
                parent.addNode(text, FXMLDocumentController.Window.MAIN);
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
    
    public void moduleClicked(Module module){
        //Create a highlight rectangle
        createHighlight(module);
        //Update the status fields and update upgrade button
        try{
            createUpgradeButtons(module);
        }
        catch(InvalidObjectException ex)
        {
            
        }
    }
    
    public void createHighlight(Module module){
        parent.removeNode(highlight, Window.DOWN);
        highlight = new Rectangle(module.getPosition().x - (module.getWidth()/2), module.getPosition().y - (module.getHeight()/2), module.getHeight(), module.getWidth());
        highlight.setStroke(Color.BLUE);
        highlight.setFill(Color.BLUE);
        highlight.setOpacity(0.50f);
        highlight.setStrokeWidth(1);
        highlight.setId("highlight");
        parent.addNode(highlight, Window.DOWN);
    }
    
     /**
     * Calculates if there is an existing node in the given square
     * @param x the x-location of the left upper corner
     * @param y the y-location of the left upper corner
     * @param width the width of the square
     * @param height the height of the square
     * @return whether or not the given square is overlapping an existing node
     */
    public boolean isPointInNode(int x, int y, int width, int height) {
            ObservableList<Node> nodes = parent.getAllNodes(FXMLDocumentController.Window.DOWN);
            for(Node n : nodes)
            {
                if(n instanceof ModuleImage || n instanceof PathImage)
                {
                    Point2D p1 = new Point2D(x, y); // left upper corner
                    Point2D p2 = new Point2D(x, y + height); // left bottom corner
                    Point2D p3 = new Point2D(x + width, y); // right upper corner
                    Point2D p4 = new Point2D(x + width, y + height); // right bottom corner
                    Point2D p5 = new Point2D(x + 0.5*width, y + 0.5*height); // the middle
                    boolean b = n.contains(p1) || n.contains(p2) || n.contains(p3) || n.contains(p4) || n.contains(p5);
                    if (b) return true;
                }
            }
            return false;
    }
    
    /**
     * Check if a user can cast the given spell.
     * This method will check if the user can cast the spell given as param. 
     * 
     * The method will first check if the spell has been used before, if not, it returns true.
     * If the spell has been used before, lastSpellTime will be set. The method than checks if lastSpellTime + spell cooldown
     * is smaller than the current System.nanoTime();
     * @param spell Spell-object the user wants to cast
     * @return returns <b>true</b> if the spell can be cast, <b>false</b> if the spell can't be cast.
     */
    public boolean spellAvailable(SpellTemplate spell)
    {
        long lastSpellTime = 0;
        
        switch(spell.getName())
        {
            case CORRUPT:
                if(this.SpellCorrupt == 0) { this.SpellCorrupt = System.nanoTime(); return true; } else { lastSpellTime = this.SpellCorrupt; }
                break;
            case ENCRYPT:
                if(this.SpellEncrypt == 0) { this.SpellEncrypt = System.nanoTime(); return true; } else { lastSpellTime = this.SpellEncrypt; }
                break;
            case DISRUPT:
                if(this.SpellDisrupt == 0) { this.SpellDisrupt = System.nanoTime(); return true; } else { lastSpellTime = this.SpellDisrupt; }
                break;
            case LOCKDOWN:
                if(this.SpellLockdown == 0) { this.SpellLockdown = System.nanoTime(); return true; } else { lastSpellTime = this.SpellLockdown; }
                break;
            case VIRUSSCAN:
                if(this.SpellVirusscan == 0) { this.SpellVirusscan = System.nanoTime(); return true; } else { lastSpellTime = this.SpellVirusscan; }
                break;
            case FIREWALL:
                if(this.SpellFirewall == 0) { this.SpellFirewall = System.nanoTime(); return true; } else { lastSpellTime = this.SpellFirewall; }
                break;
        }
        
        // We now know if the spell has been used before
        long cooldown = spell.getCooldown()*1000000000;
        lastSpellTime = lastSpellTime + cooldown;
        
        if(System.nanoTime() <= lastSpellTime)
        {
            // Not allowed to cast spell yet
            this.showError("You are not allowed to cast this spell yet.");
            return false;
        }
        else { return true; }
    }
    
    public void createUpgradeButtons(Module module) throws InvalidObjectException{
        parent.removeNode(imageViewUpgrade, Window.DOWN);
        parent.removeNode(imageViewSell, Window.DOWN);

        imageViewUpgrade = new ImageView();
        imageViewSell = new ImageView();

        if (module.getLevel() < 3) {
        File file = new File("src/hack/attack/client/resources/interface/module/40x40/Upgrade-Module.png");
        Image image = new Image(file.toURI().toString());
        imageViewUpgrade.setImage(image);
        imageViewUpgrade.setId("upgrade");
        imageViewUpgrade.setX(module.getPosition().x - (module.getWidth()));
        imageViewUpgrade.setY(module.getPosition().y - (module.getHeight()));
        parent.addNode(imageViewUpgrade, Window.DOWN);
        /*RMI
        GameEngine.getInstance().addUpgradeClickEvent(imageViewUpgrade, module);
        */
        }

        File file2 = new File("src/hack/attack/client/resources/interface/module/40x40/Sell-Module.png");
        Image imageSell = new Image(file2.toURI().toString());
        imageViewSell.setImage(imageSell);
        imageViewSell.setId("sell");
        imageViewSell.setX(module.getPosition().x - (module.getWidth()));
        imageViewSell.setY(module.getPosition().y - (module.getHeight()) + 40);
        parent.addNode(imageViewSell, Window.DOWN);
        /*RMI
        GameEngine.getInstance().addSellClickEvent(imageViewSell, module);
        */
    }
    
      /**
     * This method adds an event that upgrades the given module.
     * @param imageView
     * @param module 
     */
    public void addUpgradeClickEvent(ImageView imageView, Module module){
         imageView.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               //Logic to upgrade button
               Defense def = (Defense) module;
                try {
                    def.upgrade();
                    drawUpgraded();
                } catch (NoUpgradeAllowedException ex) {
                    //Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void addSellClickEvent(ImageView imageView, Module module){
         imageView.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                //Logic to upgrade button
               Defense def = (Defense) module;
               /*
               //Deactivate module
               def.deactivate();
               //Add bitcoins
               playerA.addBitcoins(module.getCost() * (0.75));
               //Remove module from players list
               playerA.removeModule(module);
               //Remove module from screen
               graphicsEngine.removeModule(module);
               //Show that the module is sold
               graphicsEngine.drawSold();
               */
               List<Module> modulelist = new ArrayList();
               modulelist.add(def);
               ClientAdapter.getInstance().deleteCurrentModules(modulelist, ClientAdapter.getInstance().getCurrentUserID());
            }
        });
    }
      
    public void drawUpgraded(){
        FadeTransition fadeOut = new FadeTransition(Duration.millis(3000));
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        Label label = new Label();
        label.setLayoutX(highlight.getX() - 15);
        label.setLayoutY(highlight.getY() - 15);
        label.setTextFill(Color.BLUE);
        label.setText(String.format("Upgrade complete!"));
        parent.addNode(label, Window.DOWN);
        fadeOut.setNode(label);
        fadeOut.playFromStart();
        
        removeSelected();
        
        fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                parent.removeNode(label, Window.DOWN);
            }
        });
    }
    
     public void drawSold(){
        FadeTransition fadeOut = new FadeTransition(Duration.millis(3000));
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        Label label = new Label();
        label.setLayoutX(highlight.getX() - 15);
        label.setLayoutY(highlight.getY() - 15);
        label.setTextFill(Color.BLUE);
        label.setText(String.format("Module sold!"));
        parent.addNode(label, Window.DOWN);
        fadeOut.setNode(label);
        fadeOut.playFromStart();
        
        removeSelected();
        
        
        
        fadeOut.setOnFinished(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                parent.removeNode(label, Window.DOWN);
            }
        });
    }
    
    public void removeSelected(){
        parent.removeNode(highlight, Window.DOWN);
        parent.removeNode(imageViewUpgrade, Window.DOWN);
        parent.removeNode(imageViewSell, Window.DOWN);
    }
    
    public void removeModule(Module mod){
        ArrayList<Node> nodeListOb = this.getAllNodes();
        for(Node nod: nodeListOb)
        {
            if(nod instanceof ObjectImage)
            {
                ObjectImage ob = (ObjectImage) nod;
                
                    if(ob.getReference() == mod)
                    {
                    try {
                        this.deSpawn(nod, ClientAdapter.getInstance().getCurrentUserID());
                    } catch (InvalidObjectException ex) {
                        //Logger.getLogger(GraphicsEngine.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
            }
        }
    }
    
    public void drawAttackLine(Defense defense, Minion minion){
        Line attackLine = new Line(defense.getPosition().x, defense.getPosition().y, minion.getPosition().x, minion.getPosition().y);
        int currentID = ClientAdapter.getInstance().getCurrentUserID();
        Window window = minion.getOwnerID() == currentID? FXMLDocumentController.Window.TOP : FXMLDocumentController.Window.DOWN;
        
        if (defense.getModuleName() == ModuleName.BOTTLECAP_ANTIVIRUS)
        {
            attackLine.setStroke(Color.GREEN);
            attackLine.setStrokeLineCap(StrokeLineCap.ROUND);
            attackLine.setOpacity(0.75);
            attackLine.setStrokeWidth(3);
            parent.addNode(attackLine, window);
        }
        
        switch(defense.getModuleName())
        {
            case BOTTLECAP_ANTIVIRUS:
                attackLine.setStroke(Color.GREEN);
                attackLine.setStrokeLineCap(StrokeLineCap.ROUND);
                attackLine.setOpacity(0.75);
                attackLine.setStrokeWidth(3);
                parent.addNode(attackLine, window);
                break;
            case MUSCLE_ANTIVIRUS:
                attackLine.setStroke(Color.BLUE);
                attackLine.setStrokeLineCap(StrokeLineCap.ROUND);
                attackLine.setOpacity(0.75);
                attackLine.setStrokeWidth(3);
                parent.addNode(attackLine, window);
                break;
            case SCALE_ANTIVIRUS:
                attackLine.setStroke(Color.PURPLE);
                attackLine.setStrokeLineCap(StrokeLineCap.ROUND);
                attackLine.setOpacity(0.75);
                attackLine.setStrokeWidth(3);
                parent.addNode(attackLine, window);
                break;
            case SNIPER_ANTIVIRUS:
                attackLine.setStroke(Color.RED);
                attackLine.setStrokeLineCap(StrokeLineCap.ROUND);
                attackLine.setOpacity(0.75);
                attackLine.setStrokeWidth(3);
                parent.addNode(attackLine, window);
                break;
        }
        //Initialize timer outisde of method and add tasks?
        //Now we create multiple timers.
        //Timer timer = new Timer();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
        @Override
        public void run() {
        parent.removeNode(attackLine, window);
        }
        },200);
    }
   
}
