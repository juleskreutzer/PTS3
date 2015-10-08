package hackattackfx;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import hackattackfx.exceptions.*;
import java.util.Timer;
import java.util.TimerTask;
import static jdk.nashorn.internal.objects.NativeRegExp.test;

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
        void tickComplete();
    }
    
    private static GameEngine instance;
    
    private GraphicsEngine graphicsEngine;
    private Map map;
    private ArrayList<Wave> waveList;
    private Wave currentWave;
    private Player playerA;
    private Player playerB;
    
    private boolean gameRunning;
    private Spell selectedSpell;
    
    private ArrayList<OnCompleteTick> tickCompleteListeners;
    private ArrayList<OnExecuteTick> listeners;
    private ArrayList<OnExecuteTick> unsubscribed;
    
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
        tickCompleteListeners = new ArrayList<OnCompleteTick>();
        listeners = new ArrayList<OnExecuteTick>();
        unsubscribed = new ArrayList<OnExecuteTick>();
        waveList = new ArrayList<Wave>();
        playerA = new Player(100, "Jasper", 100, new Point(0,50));
        playerB = new Player(100, "Jules", 100, new Point(100,50));
        gameRunning = false;
        preStart();
        startGame();
    }
    
    /**
     * Mostly used to draw the initial components like the bases and roads
     */
    private void preStart(){
        graphicsEngine.drawRoad(map.getRoad());
    }
    
    /**
     * Starts the game. From this point, the initial wave will be created and the game will run from this point on.
     */
    private void startGame(){
        gameRunning = true;
        Wave wave = new Wave(1,1,playerB,10,0,0,0,0,0);
        waveList.add(wave);
        
        wave.startWave();
        
    }
    
    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                tick();
            }
        }, 0, 15);
                
        
//        while(gameRunning){
//            while(GameTime.getDeltaTime() < GameTime.OPTIMAL_TIME){
//                
//            }
//        }
    }
    
    private void tick(){
        processUnsubscribers();
        notifyListeners();
        
        
    }
    
    public void setOnTickListener(OnExecuteTick callback){
        listeners.add(callback);
    }
    
    public void setOnTickCompleteListener(OnCompleteTick callback){
        tickCompleteListeners.add(callback);
    }
    /**
     * Removes the unsubscribers from the listeners list.
     * This method should not be called during a tick!
     */
    private void processUnsubscribers(){
        listeners.removeAll(unsubscribed);
        unsubscribed.clear();
    }
    
    public boolean unsubscribeListener(OnExecuteTick callback) throws UnsubscribeNonListenerException{
        if(!listeners.contains(callback)){
            throw new UnsubscribeNonListenerException("You try to unsubscribe a listener that is not subscribed as a listener");
        }
        unsubscribed.add(callback);
        return true;
    }
    
    /**
     * Notifies every listening object that a tick occured.
     */
    private void notifyListeners(){
        for(OnExecuteTick l : listeners){
            l.onTick(GameTime.getElapsedTime());
        }
        for(OnCompleteTick l : tickCompleteListeners){
            l.tickComplete();
        }
    }
    
    /**
     * Adds the effect of the spell to every targeted minion in the range of this spell.
     * @param spell The spell to perform.
     */
    public void executeSpell(Spell spell){
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
