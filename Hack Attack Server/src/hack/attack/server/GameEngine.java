package hack.attack.server;

import hack.attack.server.GameEngine.OnExecuteTick;
import hack.attack.server.MinionEffect.OnEffectExpired;
import hack.attack.server.enums.Effect;
import hack.attack.server.exceptions.InvalidModuleEnumException;
import hack.attack.server.enums.ModuleName;
import hack.attack.server.enums.SpellName;
import hack.attack.server.enums.SpellType;
import java.awt.Point;
import javafx.scene.input.*;
import java.util.ArrayList;
import hack.attack.server.exceptions.*;
import hack.attack.server.interfaces.ITargetable;
import hack.attack.server.templates.SpellTemplate;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This is the main engine that executes the ticks. Every object that wants to be notified when a tick occurs,
 * should register to this object's {@link GameEngine.OnExecuteTick} interface.
 * 
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class GameEngine extends Thread implements MouseListener {

    /**
     * This interface is used to notify every listening object when a tick has occured.
     */
    public interface OnExecuteTick{
        /**
         * Executed when a tick occured
         * @param elapsedtime The elapsed time of the running game in milliseconds
         */
        void onTick(long elapsedtime);
    }
    
    /**
     * This interface is used to notify every listening object when a tick has been ended.
     */
    public interface OnCompleteTick{
        void tickComplete(long elapsedtime);
    }
    
    private static GameEngine instance;
    
    private GraphicsEngine graphicsEngine;
    private Map map;
    private ArrayList<Wave> waveList;
    private Wave currentWave;
    // The elapsed game time a wave has started
    private long lastWaveStart;
    // The elapsed game time a wave has ended
    private long lastWaveEnd;
    private int waveNumber;
    private Player playerA;
    private Player playerB;
    private String playerAName;
    private String playerBName;
    
    private boolean gameRunning;
    private Spell selectedSpell;
    private Module selectedModule;
    
    private List<OnCompleteTick> tickCompleteListeners;
    private List<OnCompleteTick> unsubscribedCompleteListeners;
    private List<OnExecuteTick> listeners;
    private List<OnExecuteTick> unsubscribedListeners;
    
    private SpawnTargetImage st = null;

    private GameEngine(){
        instance = this;
        
        initialize();
    }
    
    public static GameEngine getInstance(){
        return instance == null ? new GameEngine() : instance;
    }
    
    private void initialize(){
        // This makes a daemon thread of the gameengine. This means that this thread will not prevent the JVM from shutting down. 
        // This is implemented due previous bugs that kept the gameengine thread running while the game was closed.
        setDaemon(true);
        
        graphicsEngine = GraphicsEngine.getInstance();
        map = Map.getInstance();
        tickCompleteListeners = new ArrayList<>();
        unsubscribedCompleteListeners = new ArrayList<>();
        listeners = new ArrayList<>();
        unsubscribedListeners = new ArrayList<>();
        waveList = new ArrayList<>();
        playerA = new Player(100, Data.playerAName, 100, new Point(0,50));
        playerB = new Player(100, Data.playerBName, 100, new Point(100,50));
        gameRunning = false;
        waveNumber = 0;
        lastWaveStart = GameTime.getElapsedTime();
        preStart();
        startGame();
    }
    
    /**
     * Mostly used to draw the initial components like the bases, roads and event handlers
     */
    private void preStart(){
        graphicsEngine.drawRoad(map.getRoad());
        
        // Initialize all GUI buttons
        ImageView sniperav = (ImageView)graphicsEngine.getNode("buildSniperAV",null);
        sniperav.setOnMouseClicked(new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                st = graphicsEngine.drawModuleSpawnTarget(ModuleName.SNIPER_ANTIVIRUS);
                
                graphicsEngine.getScene().setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>(){

                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        st.setX(event.getSceneX() - (st.getImage().getWidth()/2));
                        st.setY(event.getSceneY() - (st.getImage().getHeight()/2));
                    }
                });
                // Set a listener for a second click to occur
                st.setOnMouseClicked(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        
                        MouseButton m = event.getButton();
                        if (m == MouseButton.PRIMARY){
                            Point position = new Point((int)event.getSceneX(), (int)event.getSceneY());
                            try {
                                try {
                                    int x = (int)st.getX();
                                    int y = (int)st.getY();


                                    if(!isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                    {   
                                        Defense defense = playerA.buildDefense(new Defense(Data.DEFAULT_MODULE_DEFENSE_SNIPER_1, position, 50, 50));
                                        graphicsEngine.spawn(defense);
                                        graphicsEngine.deSpawn(st);
                                        defense.activate();
                                    }
                                    else
                                    {
                                        graphicsEngine.showError("You are not allowed to build here.");
                                    }
                                } catch (DuplicateSpawnException | InvalidObjectException ex) {
                                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (NotEnoughBitcoinsException ex) {
                                    graphicsEngine.showError(ex.getMessage());
                                } 
                            } catch (InvalidModuleEnumException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            System.out.println("dd");
                            try {
                                graphicsEngine.deSpawn(st);
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
            }
            
        });
        sniperav.setOnMouseEntered(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                try {
                    Module module = new Defense(Data.DEFAULT_MODULE_DEFENSE_SNIPER_1, null, 0,0);
                    graphicsEngine.drawModuleStats(module);
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        sniperav.setOnMouseExited(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                graphicsEngine.drawModuleStats(null);
            }
        });
        
        ImageView scaleav = (ImageView)graphicsEngine.getNode("buildScaleAV",null);
        scaleav.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
 
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                st = graphicsEngine.drawModuleSpawnTarget(ModuleName.SCALE_ANTIVIRUS);
                graphicsEngine.getScene().setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>(){

                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        st.setX(event.getSceneX() - (st.getImage().getWidth()/2));
                        st.setY(event.getSceneY() - (st.getImage().getHeight()/2));

                    }
                });
                // Set a listener for a second click to occur
                st.setOnMouseClicked(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton m = event.getButton();
                        if (m == MouseButton.PRIMARY){
                            Point position = new Point((int)event.getSceneX(), (int)event.getSceneY());
                            try {

                                try {
                                    int x = (int)st.getX();
                                    int y = (int)st.getY();


                                    if(!isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                    {
                                        Defense defense = playerA.buildDefense(new Defense(Data.DEFAULT_MODULE_DEFENSE_SCALE_1, position, 50, 50));
                                        graphicsEngine.spawn(defense);
                                        graphicsEngine.deSpawn(st);
                                        defense.activate();
                                    }
                                    else{
                                        graphicsEngine.showError("You are not allowed to build here.");
                                    }
                                } catch (DuplicateSpawnException | InvalidObjectException ex) {
                                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                                } 


                            } catch (InvalidModuleEnumException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NotEnoughBitcoinsException ex) {
                                graphicsEngine.showError(ex.getMessage());
                            }
                        } else {
                            System.out.println("dd");
                            try {
                                graphicsEngine.deSpawn(st);
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                
                });
            }
            
        });
        scaleav.setOnMouseEntered(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                try {
                    Module module = new Defense(Data.DEFAULT_MODULE_DEFENSE_SCALE_1, null, 0,0);
                    graphicsEngine.drawModuleStats(module);
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        scaleav.setOnMouseExited(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                graphicsEngine.drawModuleStats(null);
            }
        });
        
        ImageView bottlecapav = (ImageView)graphicsEngine.getNode("buildBottlecapAV",null);
        bottlecapav.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
 
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                st = graphicsEngine.drawModuleSpawnTarget(ModuleName.BOTTLECAP_ANTIVIRUS);
                graphicsEngine.getScene().setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>(){

                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        st.setX(event.getSceneX() - (st.getImage().getWidth()/2));
                        st.setY(event.getSceneY() - (st.getImage().getHeight()/2));

                    }
                });
                // Set a listener for a second click to occur
                st.setOnMouseClicked(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton m = event.getButton();
                        if (m == MouseButton.PRIMARY){
                            Point position = new Point((int)event.getSceneX(), (int)event.getSceneY());
                            try {

                                try {
                                    int x = (int)st.getX();
                                    int y = (int)st.getY();


                                    if(!isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                    {
                                        Defense defense = playerA.buildDefense(new Defense(Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1, position, 50, 50));
                                        graphicsEngine.spawn(defense);
                                        graphicsEngine.deSpawn(st);
                                        defense.activate();
                                    }
                                    else
                                    {
                                        graphicsEngine.showError("You are not allowed to build here.");
                                    }
                                } catch (DuplicateSpawnException | InvalidObjectException ex) {
                                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                                }


                            } catch (InvalidModuleEnumException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NotEnoughBitcoinsException ex)
                            {
                                graphicsEngine.showError(ex.getMessage());
                            }
                        } else {
                            System.out.println("dd");
                            try {
                                graphicsEngine.deSpawn(st);
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                
                });
            }
            
        });
        bottlecapav.setOnMouseEntered(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                try {
                    Module module = new Defense(Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1, null, 0,0);
                    graphicsEngine.drawModuleStats(module);
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bottlecapav.setOnMouseExited(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                graphicsEngine.drawModuleStats(null);
            }
        });
        
        ImageView muscleav = (ImageView)graphicsEngine.getNode("buildMuscleAV",null);
        muscleav.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
 
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                st = graphicsEngine.drawModuleSpawnTarget(ModuleName.MUSCLE_ANTIVIRUS);
                graphicsEngine.getScene().setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>(){

                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        st.setX(event.getSceneX() - (st.getImage().getWidth()/2));
                        st.setY(event.getSceneY() - (st.getImage().getHeight()/2));

                    }
                });
                // Set a listener for a second click to occur
                st.setOnMouseClicked(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton m = event.getButton();
                        if (m == MouseButton.PRIMARY){
                            Point position = new Point((int)event.getSceneX(), (int)event.getSceneY());
                            try {

                                try {
                                    int x = (int)st.getX();
                                    int y = (int)st.getY();

                                    if(!isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                    {
                                        Defense defense = playerA.buildDefense(new Defense(Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1, position, 50, 50));
                                        graphicsEngine.spawn(defense);
                                        graphicsEngine.deSpawn(st);
                                        defense.activate();
                                    }
                                    else
                                    {
                                        graphicsEngine.showError("You are not allowed to build here.");
                                    }
                                } catch (DuplicateSpawnException | InvalidObjectException ex) {
                                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                                }


                            } catch (InvalidModuleEnumException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NotEnoughBitcoinsException ex)
                            {
                                graphicsEngine.showError(ex.getMessage());
                            }
                        } else {
                            System.out.println("dd");
                            try {
                                graphicsEngine.deSpawn(st);
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                
                });
            }
            
        });
        muscleav.setOnMouseEntered(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                try {
                    Module module = new Defense(Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1, null, 0,0);
                    graphicsEngine.drawModuleStats(module);
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        muscleav.setOnMouseExited(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                graphicsEngine.drawModuleStats(null);
            }
        });
        
        ImageView bitcoinminer = (ImageView)graphicsEngine.getNode("buildBitcoinMiner",null);
        bitcoinminer.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                Image i = bitcoinminer.getImage();
                Point p = new Point((int)bitcoinminer.getX(),(int)bitcoinminer.getY());
                try {
                    BitcoinMiner miner = new BitcoinMiner(Data.DEFAULT_MODULE_BITCOINMINER_1,p,(int)i.getWidth(),(int)i.getHeight());
                    playerA.buildBitcoinMiner(miner);
                    graphicsEngine.drawBaseModule(ModuleName.BITCOIN_MINER, true);
                    miner.activate();
                } catch (NotEnoughBitcoinsException ex) {
                    graphicsEngine.showError("Not enough bitcoins!");
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        bitcoinminer.setOnMouseEntered(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                try {
                    BitcoinMiner module = new BitcoinMiner(Data.DEFAULT_MODULE_BITCOINMINER_1,null,0,0);
                    graphicsEngine.drawModuleStats(module);
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bitcoinminer.setOnMouseExited(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                graphicsEngine.drawModuleStats(null);
            }
        });
        
        ImageView pausebutton = (ImageView)graphicsEngine.getNode("btnPause",null);
        pausebutton.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                gameRunning = !gameRunning;
                graphicsEngine.setPauseButton(gameRunning);
            }
        
        });
        
        ImageView spellFirewall = (ImageView)graphicsEngine.getNode("spellFirewall",null);
        spellFirewall.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                Spell spell = new Spell(Data.DEFAULT_SPELL_FIREWALL);
                Ellipse range = graphicsEngine.drawSpellRange(spell);
                graphicsEngine.getScene().setOnMouseMoved(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        range.setCenterX(event.getSceneX());
                        range.setCenterY(event.getSceneY());
                    }
                
                });
                range.setOnMouseClicked(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        ArrayList<ITargetable> targets = new ArrayList<ITargetable>();
                        for(Minion m : currentWave.minionsAsList()){
                            if(targetInRange(range.getCenterX(), range.getCenterY(), spell.getRange(),m)){
                                targets.add(m);
                            }
                        }
                        executeSpell(spell, targets);
                    }
                    
                });
            }
            
        });
        
        ImageView spellLockdown = (ImageView)graphicsEngine.getNode("spellLockdown",null);
        
        
        ImageView spellVirusscan = (ImageView)graphicsEngine.getNode("spellVirusscan",null);
        
        
        ImageView spellCorrup = (ImageView)graphicsEngine.getNode("spellCorrupt",null);
        
        
        ImageView spellDisrupt = (ImageView)graphicsEngine.getNode("spellDisrupt",null);
        
        
        ImageView spellEncrypt = (ImageView)graphicsEngine.getNode("spellEncrypt",null);
        
    }
    
    /**
     * Starts the game. From this point, the initial wave will be created and the game will run from this point on.
     */
    private void startGame(){
        gameRunning = true;        
        
        Wave w = generateNextWave();
        waveList.add(w);
        currentWave = w;
        w.startWave();
        
    }
    
    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                tick();
                //System.out.print("use javaFX thread (gameEngine line 157) (animationTimer)");
            }
        }, 0, 15);
        
    }
    
    private void tick(){
        if(gameRunning)
        {
            if(playerA.getHealth() <= 0)
            {
               graphicsEngine.showEndGame(playerA.getName());
               gameRunning = false;
               
            }

            if(!currentWave.waveActive() || GameTime.getElapsedTime() >= (lastWaveStart + 30000)){
                Wave w = generateNextWave();
                lastWaveStart = GameTime.getElapsedTime();
                waveList.add(w);
                currentWave = w;
                w.startWave();

            }

            processUnsubscribers();
            notifyListeners();
            fillLabels();
        }
    }
    
    public Wave getActiveWave(){
        return currentWave;
    }
    
    public ArrayList<Wave> getActiveWaves() {
        ArrayList<Wave> result = new ArrayList<Wave>();
        for (Wave w : this.waveList) {
            if (w.waveActive()) result.add(w);
        }
        return result;
    }
    
    private Wave generateNextWave(){
        // calculates how strong this wave should be and ups  waveNumber by one
        int waveStrongness = 5 + (int)(0.5 * ++waveNumber * waveNumber);
        int bytes = 0;
        int kiloBytes = 0;
        int megaBytes = 0;
        int gigaBytes = 0;
        int teraBytes = 0;
        int petaBytes = 0;
        Random r = new Random();
        while(waveStrongness  > 100) {
            if (r.nextDouble() > 0.5) {
                ++petaBytes;
                waveStrongness -= 32;
            } else {
                ++teraBytes;
                waveStrongness -= 16;
            }
        }
        while(waveStrongness  > 50) {
            if (r.nextDouble() > 0.5) {
                ++teraBytes;
                waveStrongness -= 16;
            } else {
                ++gigaBytes;
                waveStrongness -= 8;
            }
        }
        while(waveStrongness  > 25) {
            if (r.nextDouble() > 0.5) {
                ++gigaBytes;
                waveStrongness -= 8;
            } else {
                ++megaBytes;
                waveStrongness -= 4;
            }
        }
        while(waveStrongness  > 10) {
            if (r.nextDouble() > 0.25) {
                ++megaBytes;
                waveStrongness -= 4;
            } else {
                ++kiloBytes;
                waveStrongness -= 2;
            }
        }
        while(waveStrongness  > 5) {
                ++kiloBytes;
                waveStrongness -= 2;
        }
        while (waveStrongness > 0) {
            ++bytes;
            waveStrongness -= 1;
        }
        System.out.println(bytes + " : " + kiloBytes + " : " +megaBytes + " : " +gigaBytes + " : " + teraBytes + " : " + petaBytes); // for logging purpose
        
        return new Wave(waveNumber,1 + 0.1*waveNumber,playerA,bytes,kiloBytes,megaBytes,gigaBytes,teraBytes,petaBytes);
    }
    
    /**
     * Returns the player object from the player that's currently playing. So no enemy Player is returned.
     * @return Current user
     */
    public Player getPlayer(){
        return playerA;
    }
    
    public void setOnTickListener(OnExecuteTick callback){
        listeners.add(callback);
    }
    
    public void setOnTickCompleteListener(OnCompleteTick callback){
        tickCompleteListeners.add(callback);
    }
    /**
     * Removes the unsubscribers from the listeners list.
     * This method should not be called during a tick therefor only after a tick!
     */
    private void processUnsubscribers(){
        if(unsubscribedListeners.size() > 0 ){
            listeners.removeAll(unsubscribedListeners);
            unsubscribedListeners.clear();
        }
        if(unsubscribedCompleteListeners.size() > 0){
            tickCompleteListeners.removeAll(unsubscribedCompleteListeners);
            unsubscribedCompleteListeners.clear();
        }
    }
    
    public boolean unsubscribeListener(OnExecuteTick callback) throws UnsubscribeNonListenerException{
        if(!listeners.contains(callback)){
            throw new UnsubscribeNonListenerException("You try to unsubscribe a listener that is not subscribed as a listener");
        }
        unsubscribedListeners.add(callback);
        return true;
    }
    
    public boolean unsubscribeListener(OnCompleteTick callback) throws UnsubscribeNonListenerException{
        if(!tickCompleteListeners.contains(callback)){
            throw new UnsubscribeNonListenerException("You try to unsubscribe a listener that is not subscribed as a listener");
        }
        unsubscribedCompleteListeners.add(callback);
        return true;
    }
    
    /**
     * Notifies every listening object that a tick occured.
     */
    private void notifyListeners(){
        Iterator<OnExecuteTick> listit = listeners.iterator();
        while(listit.hasNext()){
            OnExecuteTick l = listit.next();
            l.onTick(GameTime.getElapsedTime());
        }
        Iterator<OnCompleteTick> compit = tickCompleteListeners.iterator();
        while(compit.hasNext()){
            OnCompleteTick cl = compit.next();
            cl.tickComplete(GameTime.getElapsedTime());
        }
    }
    
    /**
     * Adds the effect of the spell to every targeted minion in the range of this spell.
     * @param spell The spell to perform.
     * @param targets The targets the spell should execute on.. Most likely minions or defense modules.
     */
    public void executeSpell(Spell spell, ArrayList<ITargetable> targets){
        switch(spell.getName()){
            case FIREWALL:
                for(ITargetable t : targets){
                    Minion m = (Minion)t;
                    MinionEffect effect = new MinionEffect(Effect.SLOWED, spell.getEffectDuration(), new OnEffectExpired(){

                        @Override
                        public void onExpired() {
                            m.removeEffect();
                        }
                        
                    });
                    m.applyEffect(effect);
                    graphicsEngine.drawEffect(Effect.SLOWED, m);
                }
                break;
            case LOCKDOWN:
                
                break;
            case VIRUSSCAN: 
                
                break;
            case CORRUPT:
                
                break;
            case DISRUPT: 
                
                break;
            case ENCRYPT:
                
                break;
        }
    }
    
    private void fillLabels(){
        String name = playerA.getName();
        double health = playerA.getHealth();
        double coins = playerA.getBitcoins();
        int wave = currentWave.getWaveNr();
        graphicsEngine.drawLabels(wave, name, health, coins);
    }
    
    /**
     * Calculates if there is an existing node in the given square
     * @param x the x-location of the left upper corner
     * @param y the y-location of the left upper corner
     * @param width the width of the square
     * @param height the height of the square
     * @return whether or not the given square is overlapping an existing node
     */
    private boolean isPointInNode(int x, int y, int width, int height) {
            ObservableList<Node> nodes = graphicsEngine.getNodes();
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
     * Checks if the given ITargetable is within the range of the module
     * @param sourcex The x position of the source to measure from
     * @param sourcey The y position of the source to measure from
     * @param range The range is the 
     * @param t An ITargetable object where the position will be checked
     * @return True if the given ITargetable is in range.
     */
    public boolean targetInRange(double sourcex, double sourcey, double range, ITargetable t){
        //return true if the square root of range is bigger than (minion and tower variables used) -> Delta X^2 + Delta Y^2 (Pythagoras).
        long distance = (long)Math.sqrt((sourcex-t.getPosition().x)*(sourcex-t.getPosition().x) + (sourcey-t.getPosition().y)*(sourcey-t.getPosition().y));
        return range >= distance;
    }
    
    public boolean gameRunning(){
        return gameRunning;
    }
    
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
