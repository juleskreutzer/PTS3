package hack.attack.server;

import hack.attack.rmi.Defense;
import hack.attack.rmi.Spell;
import hack.attack.rmi.Minion;
import hack.attack.rmi.Module;
import hack.attack.rmi.IClientUpdate;
import hack.attack.rmi.IClientCreate;
import hack.attack.rmi.IClient;
import hack.attack.server.GameEngine.OnExecuteTick;
import hack.attack.server.AppliedEffect.OnEffectExpired;
import hack.attack.rmi.Effect;
import java.awt.Point;
import java.util.ArrayList;
import hack.attack.server.exceptions.*;
import hack.attack.rmi.ITargetable;
import hack.attack.server.enums.LogState;
import hack.attack.server.logger.Log;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class GameEngine extends Thread {

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
    
    // Available interfaces to communicate with client
    private final HashMap<String, IClient> interfacesA;
    private final HashMap<String, IClient> interfacesB;
    
    private boolean gameRunning;
    private Spell selectedSpell;
    private Module selectedModule;
    
    private List<OnCompleteTick> tickCompleteListeners;
    private List<OnCompleteTick> unsubscribedCompleteListeners;
    private List<OnExecuteTick> listeners;
    private List<OnExecuteTick> unsubscribedListeners;
    
    private SpawnTargetImage st = null;
    
    private Session session;

    public GameEngine(Session session, HashMap<String, IClient> interfacesA, HashMap<String, IClient> interfacesB)
    {
        this.interfacesA = interfacesA;
        this.interfacesB = interfacesB;
        this.session = session;
        initialize(session.getPlayerA().getDisplayName(), session.getPlayerA().getUID(), session.getPlayerB().getDisplayName(), session.getPlayerB().getUID());
    }
    
    private void initialize(String playerNameA, int uIDA, String playerNameB, int uIDB){
        // This makes a daemon thread of the gameengine. This means that this thread will not prevent the JVM from shutting down. 
        // This is implemented due previous bugs that kept the gameengine thread running while the game was closed.
        setDaemon(true);
        
        map = Map.getInstance();
        tickCompleteListeners = new ArrayList<>();
        unsubscribedCompleteListeners = new ArrayList<>();
        listeners = new ArrayList<>();
        unsubscribedListeners = new ArrayList<>();
        waveList = new ArrayList<>();
        playerA = new Player(this, 100, playerNameA, 100, new Point(0,50), uIDA);
        playerB = new Player(this, 100, playerNameB, 100, new Point(100,50), uIDB);
        gameRunning = false;
        waveNumber = 0;
        lastWaveStart = GameTime.getElapsedTime();
        preStart();
    }
    
    /**
     * Mostly used to draw the initial components like the bases, roads and event handlers
     */
    private void preStart(){

    }
    
    /**
     * Starts the game. From this point, the initial wave will be created and the game will run from this point on.
     */
    public void startGame(){
        gameRunning = true;        
        
        Wave w = generateNextWave();
        waveList.add(w);
        currentWave = w;
        w.startWave();
        this.start();
    }
    
    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    tick();
                } catch (RemoteException | RuntimeException ex) {
                    HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
                    System.out.println(ex.toString());
                    ex.printStackTrace();
                }
            }
        }, 0, 15);
        
    }
    
    private void tick() throws RemoteException{
        if(gameRunning)
        {
            IClientUpdate iClientUpdateA = (IClientUpdate)interfacesA.get("update");
            IClientUpdate iClientUpdateB = (IClientUpdate)interfacesB.get("update");
            
            // What is the purpose of this code?
            // Why are the order of parameters changed depending on which player died?
            if(playerA.getHealth() <= 0)
            {
               
                iClientUpdateA.updateLabels(waveNumber, playerA.getName(), playerA.getHealth(), playerA.getBitcoins(), playerB.getName(), playerB.getHealth());
                gameRunning = false;
               
                try{
                    Data.updateScore(playerB.getUID(), playerB.getReceivedBitcoins());
                }
                catch(IllegalArgumentException | IOException ex)
                {
                    HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
                } 

            }
            
            if(playerB.getHealth() <= 0)
            {
                
                iClientUpdateB.updateLabels(waveNumber, playerB.getName(), playerB.getHealth(), playerB.getBitcoins(), playerA.getName(), playerA.getHealth());
                gameRunning = false;
                
                try{
                    Data.updateScore(playerA.getUID(), playerA.getReceivedBitcoins());
                }
                catch(IllegalArgumentException | IOException ex)
                {
                    HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
                }
            }

            if(!currentWave.waveActive()){
                Wave w = generateNextWave();
                lastWaveStart = GameTime.getElapsedTime();
                waveList.add(w);
                currentWave = w;
                
                w.startWave();

            }

            processUnsubscribers();
            notifyListeners();
            fillLabels();
            
            List<Module> allmodules = playerA.getModules();
            allmodules.addAll(playerB.getModules());
            List<Minion> allMinions = currentWave.minionsAsList();
            
            //iClientUpdateA.redrawCurrentModules(allmodules, playerA.getUID());
            iClientUpdateA.redrawCurrentMinions(allMinions, playerA.getUID());
            
            //iClientUpdateB.redrawCurrentModules(allmodules, playerB.getUID());
            iClientUpdateB.redrawCurrentMinions(allMinions, playerB.getUID());
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
        HackAttackServer.writeConsole(new Log(LogState.OK, (bytes + " : " + kiloBytes + " : " +megaBytes + " : " +gigaBytes + " : " + teraBytes + " : " + petaBytes))); // for logging purpose
        
        return new Wave(this, waveNumber,1 + 0.1*waveNumber,playerA, playerB, bytes,kiloBytes,megaBytes,gigaBytes,teraBytes,petaBytes);
    }
    
    /**
     * Returns the player object from the player that's currently playing. So no enemy Player is returned.
     * @param uID unique ID of a player
     * @return Current user
     */
    public Player getPlayer(int uID){
        if(playerA.getUID() == uID)
        {
            return playerA;
        }
        else
        {
            return playerB;
        }
    }
    /**
     * Returns the opposite player of the given parameter
     * @param uID
     * @return 
     */
    public Player getOppositePlayer(int uID){
        if(playerA.getUID() == uID)
        {
            return playerB;
        }
        else
        {
            return playerA;
        }
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
     * @param position
     * @param uID
     */
    public void executeSpell(Spell spell, Point position, int uID) throws RemoteException{
        List<ITargetable> minionTargets = new ArrayList<>();
        List<ITargetable> defenseTargets = new ArrayList<>();
        
        Player user;
        
        // Get the correct modules and store them in defenseTargets
        switch(spell.getName())
        {
            case VIRUSSCAN:
                user = playerA.getUID() == uID ? playerA : playerB;
                for(Module m : user.getModules())
                {
                    if(m instanceof Defense)
                    {
                        if(targetInRange(position.getX(), position.getY(), spell.getRange(), (ITargetable) m))
                        {
                            defenseTargets.add((ITargetable) m);
                        }
                    }
                }
                break;
            case DISRUPT:
                user = playerA.getUID() == uID ? playerB : playerA;
                for(Module m : user.getModules())
                {
                    if(m instanceof Defense)
                    {
                        if(targetInRange(position.getX(), position.getY(), spell.getRange(), (ITargetable) m))
                        {
                            defenseTargets.add((ITargetable) m);
                        }
                    }
                }
                break;
        }
        
        for(Minion m : currentWave.minionsAsList()){
            if(targetInRange(position.getX(), position.getY(), spell.getRange(),m)){
                minionTargets.add(m);
            }
        }
        
        IClientCreate createA = (IClientCreate)interfacesA.get("create");
        IClientCreate createB = (IClientCreate)interfacesB.get("create");
        
        switch(spell.getName()){
            case FIREWALL:
                for(ITargetable t : minionTargets){
                    Minion m = (Minion)t;
                    AppliedEffect effect = new AppliedEffect(Effect.SLOWED, spell.getEffectDuration(), new OnEffectExpired(){

                        @Override
                        public void onExpired() {
                            m.removeEffect();
                        }
                        
                    });
                    m.applyEffect(effect);
                    
                    createA.drawNewSpells(Effect.SLOWED, minionTargets, uID);
                    createB.drawNewSpells(Effect.SLOWED, minionTargets, uID);
                }
                break;
            case LOCKDOWN:
                for(ITargetable t : minionTargets)
                {
                    Minion m = (Minion)t;
                    AppliedEffect effect = new AppliedEffect(Effect.STOPPED, spell.getEffectDuration(), new OnEffectExpired() {

                        @Override
                        public void onExpired() {
                            m.removeEffect();
                        }
                        
                    });
                    m.applyEffect(effect);
                    
                    createA.drawNewSpells(Effect.STOPPED, minionTargets, uID);
                    createB.drawNewSpells(Effect.STOPPED, minionTargets, uID);
                }
                break;
            case VIRUSSCAN: 
                for(ITargetable t : defenseTargets)
                {
                    Defense d = (Defense)t;
                    AppliedEffect effect = new AppliedEffect(Effect.BUFFED, spell.getEffectDuration(), new OnEffectExpired(){

                        @Override
                        public void onExpired() {
                            d.removeEffect();
                        }
                        
                    });
                    d.applyEffect(effect);
                    
                    createA.drawNewSpells(Effect.BUFFED, defenseTargets, uID);
                    createB.drawNewSpells(Effect.BUFFED, defenseTargets, uID);
                }
                break;
            case CORRUPT:
                for(ITargetable t : minionTargets)
                {
                    Minion m = (Minion)t;
                    AppliedEffect effect = new AppliedEffect(Effect.SPLASH, spell.getEffectDuration(), new OnEffectExpired(){

                        @Override
                        public void onExpired() {
                            m.removeEffect();
                        }
                        
                    });
                    m.applyEffect(effect);
                    
                    createA.drawNewSpells(Effect.SPLASH, minionTargets, uID);
                    createB.drawNewSpells(Effect.SPLASH, minionTargets, uID);
                }
                break;
            case DISRUPT: 
                for(ITargetable t : defenseTargets)
                {
                    Defense d = (Defense)t;
                    AppliedEffect effect = new AppliedEffect(Effect.SPLASH, spell.getEffectDuration(), new OnEffectExpired(){

                        @Override
                        public void onExpired() {
                            d.removeEffect();
                        }
                        
                    });
                    d.applyEffect(effect);
                    
                    createA.drawNewSpells(Effect.SPLASH, defenseTargets, uID);
                    createB.drawNewSpells(Effect.SPLASH, defenseTargets, uID);
                }
                break;
            case ENCRYPT:
                for(ITargetable t : minionTargets)
                {
                    Minion m = (Minion)t;
                    AppliedEffect effect = new AppliedEffect(Effect.ENCRYPT, spell.getEffectDuration(), new OnEffectExpired(){

                        @Override
                        public void onExpired() {
                            m.removeEffect();
                        }
                        
                    });
                    m.applyEffect(effect);
                    
                    createA.drawNewSpells(Effect.ENCRYPT, minionTargets, uID);
                    createB.drawNewSpells(Effect.ENCRYPT, minionTargets, uID);
                }
                break;
        }
    }
    
    private void fillLabels() throws RemoteException{
        String name = playerA.getName();
        double health = playerA.getHealth();
        double coins = playerA.getBitcoins();
        int wave = currentWave.getWaveNr();
        ((IClientUpdate)interfacesA.get("update")).updateLabels(waveNumber, playerA.getName(), playerA.getHealth(), playerA.getBitcoins(), playerB.getName(), playerB.getHealth());
        ((IClientUpdate)interfacesB.get("update")).updateLabels(waveNumber, playerB.getName(), playerB.getHealth(), playerB.getBitcoins(), playerA.getName(), playerA.getHealth());
        
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
    
    public HashMap<String, IClient> getInterfacesA()
    {
        return this.interfacesA;
    }
    
    public HashMap<String, IClient> getInterfacesB()
    {
        return this.interfacesB;
    }
    
    public Session getSession()
    {
        return this.session;
    }
}
