package hackattackfx;

import hackattackfx.GameEngine.OnExecuteTick;
import hackattackfx.exceptions.InvalidModuleEnumException;
import hackattackfx.enums.ModuleName;
import java.awt.Point;
import javafx.scene.input.*;
import java.util.ArrayList;
import hackattackfx.exceptions.*;
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
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javax.swing.JOptionPane;

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
    private int waveNumber;
    private Player playerA;
    private Player playerB;
    
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
        graphicsEngine = GraphicsEngine.getInstance();
        map = Map.getInstance();
        tickCompleteListeners = new ArrayList<>();
        unsubscribedCompleteListeners = new ArrayList<>();
        listeners = new ArrayList<>();
        unsubscribedListeners = new ArrayList<>();
        waveList = new ArrayList<>();
        playerA = new Player(100, "Jasper", 100, new Point(0,50));
        playerB = new Player(100, "Jules", 100, new Point(100,50));
        gameRunning = false;
        waveNumber = 0;
        preStart();
        startGame();
    }
    
    
    private boolean isPointInNode(int x, int y, int width, int height)
    {
            ObservableList<Node> nodes = graphicsEngine.getNodes();

            for(Node n : nodes)
            {
                if(n instanceof ModuleImage)
                {
                    Point p = new Point(x,y);
                    ModuleImage mi = (ModuleImage) n;
                    return (mi.getX() > p.getX() && mi.getX() < (p.getX() + width) && mi.getY() > p.getY() && mi.getY() < (p.getY() + height));
                    //return (mi.getX() > p.getX() && mi.getY() < p.getY() && mi.getX() +  mi.getImage().getWidth() > p.getX() && mi.getY() + mi.getImage().getHeight() > p.getY());
                    
                }
                else if(n instanceof PathImage)
                {
                    // Create switch (direction)
                    PathImage pi = (PathImage) n;
                    Path pt = (Path) pi.getReference();
                    switch(pt.getDirection())
                    {
                        case Up:
                            break;
                        case Down:
                            break;
                        case Left:
                            return(pt.getEnd().getX() < x && x < pt.getStart().getX() && pt.getEnd().getY() > y && y < (pt.getEnd().getY() + pi.getImage().getHeight()));
                        case Right:
                            return(pt.getStart().getX() < x && x < pt.getEnd().getX() && pt.getStart().getY() < y && y < (pt.getStart().getY() + pi.getImage().getHeight()));
                        default:
                            throw new IllegalArgumentException("Direction is not recognized");
                    }
                    int length = pt.getLength();
                    if(x > pi.getX() && x < (pi.getX() + length) && (x + width) > pi.getX() && (x+width) < (pi.getX() + length))
                    //return (pi.getX() < p.getX() && pi.getY() < p.getY() && pi.getX() + pi.getImage().getWidth() > p.getX() && pi.getY() + pi.getImage().getHeight() > p.getY());
                    return false;
                }
                
            }
        return false;
    }
    
    /**
     * Mostly used to draw the initial components like the bases and roads
     */
    private void preStart(){
        graphicsEngine.drawRoad(map.getRoad());
        
        
        
        // Initialize all GUI buttons
        ImageView sniperav = (ImageView)graphicsEngine.getNode("buildSniperAV");
        sniperav.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
 
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
                        
                        Point position = new Point((int)event.getSceneX(), (int)event.getSceneY());
                        try {
                            Defense defense = playerA.buildDefense(new Defense(Data.DEFAULT_MODULE_DEFENSE_SNIPER_1, position, 50, 50));
                            
                            try {
                                int x = (int)st.getX();
                                int y = (int)st.getY();
                                
                                
                                if(isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                {
                                    graphicsEngine.spawn(defense);
                                    graphicsEngine.deSpawn(st);
                                }
                                else
                                {
                                    graphicsEngine.showError("You are not allowed to build here.");
                                }
                            } catch (DuplicateSpawnException | InvalidObjectException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            defense.activate();
                            
                        } catch (InvalidModuleEnumException | NotEnoughBitcoinsException ex) {
                            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                
                });
            }
            
        });
        
        ImageView scaleav = (ImageView)graphicsEngine.getNode("buildScaleAV");
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
                        Point position = new Point((int)event.getSceneX(), (int)event.getSceneY());
                        try {
                            Defense defense = playerA.buildDefense(new Defense(Data.DEFAULT_MODULE_DEFENSE_SCALE_1, position, 50, 50));
                            
                            try {
                                int x = (int)st.getX();
                                int y = (int)st.getY();
                                
                                
                                if(!isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                {
                                    graphicsEngine.spawn(defense);
                                    graphicsEngine.deSpawn(st);
                                }
                                else{
                                    graphicsEngine.showError("You are not allowed to build here.");
                                }
                            } catch (DuplicateSpawnException | InvalidObjectException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            defense.activate();
                            
                        } catch (InvalidModuleEnumException | NotEnoughBitcoinsException ex) {
                            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                
                });
            }
            
        });
        
        ImageView bottlecapav = (ImageView)graphicsEngine.getNode("buildBottlecapAV");
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
                        Point position = new Point((int)event.getSceneX(), (int)event.getSceneY());
                        try {
                            Defense defense = playerA.buildDefense(new Defense(Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1, position, 50, 50));
                            
                            try {
                                int x = (int)st.getX();
                                int y = (int)st.getY();
                                
                                
                                if(!isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                {
                                    graphicsEngine.spawn(defense);
                                    graphicsEngine.deSpawn(st);
                                }
                                else
                                {
                                    graphicsEngine.showError("You are not allowed to build here.");
                                }
                            } catch (DuplicateSpawnException | InvalidObjectException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            defense.activate();
                            
                        } catch (InvalidModuleEnumException | NotEnoughBitcoinsException ex) {
                            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                
                });
            }
            
        });
        
        ImageView muscleav = (ImageView)graphicsEngine.getNode("buildMuscleAV");
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
                        Point position = new Point((int)event.getSceneX(), (int)event.getSceneY());
                        try {
                            Defense defense = playerA.buildDefense(new Defense(Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1, position, 50, 50));
                            
                            try {
                                int x = (int)st.getX();
                                int y = (int)st.getY();
                                
                                if(!isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                {
                                    graphicsEngine.spawn(defense);
                                    graphicsEngine.deSpawn(st);
                                }
                                else
                                {
                                    graphicsEngine.showError("You are not allowed to build here.");
                                }
                            } catch (DuplicateSpawnException | InvalidObjectException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            defense.activate();
                            
                        } catch (InvalidModuleEnumException | NotEnoughBitcoinsException ex) {
                            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                
                });
            }
            
        });
    }
    
    /**
     * Starts the game. From this point, the initial wave will be created and the game will run from this point on.
     */
    private void startGame(){
        gameRunning = true;        
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
             Wave w = generateNextWave();
             waveList.add(w);
             currentWave = w;
             w.startWave();
            }
        }, 0, 30000);
        
    }
    
    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                tick();
                System.out.print("use javaFX thread (gameEngine line 157) (animationTimer)");
            }
        }, 0, 15);
        
    }
    
    private void tick(){
        processUnsubscribers();
        notifyListeners();
        fillLabels();
    }
    
    public Wave getActiveWave(){
        return currentWave;
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
        System.err.println(bytes + " : " + kiloBytes + " : " +megaBytes + " : " +gigaBytes + " : " + teraBytes + " : " + petaBytes);
        return new Wave(waveNumber,1 + 0.1*waveNumber,playerA,bytes,kiloBytes,megaBytes,gigaBytes,teraBytes,petaBytes);
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
     */
    public void executeSpell(Spell spell){
        
    }
    
    private void fillLabels(){
        String name = playerA.getName();
        double health = playerA.getHealth();
        double coins = playerA.getBitcoins();
        graphicsEngine.drawLabels(name, health, coins);
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
