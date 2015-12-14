/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.rmi.Minion;
import hack.attack.server.GameEngine.OnCompleteTick;
import hack.attack.server.MinionEffect.OnEffectExpired;
import hack.attack.rmi.Effect;
import hack.attack.rmi.IClientCreate;
import hack.attack.server.enums.LogState;
import hack.attack.server.exceptions.DuplicateSpawnException;
import hack.attack.server.exceptions.InvalidObjectException;
import hack.attack.server.exceptions.UnsubscribeNonListenerException;
import hack.attack.server.logger.Log;
import java.awt.Point;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Igor, Jasper Rouwhorst
 */
public class Wave {
    
    private GameEngine engine;
    private int waveNr; //The number of the wave.
    private boolean waveActive; //If the wave is active or not.
    private double waveMultiplier; //The multiplier of the wave.
    
    private ArrayList<Minion> minionList;
    private static int spawnedMinions;
    private ArrayList<Minion> killedMinions;
    
    /**
     * Each minion will be created 2 (two) times, the first time the enemy = enemy, second time enemy = playerA.
     */
    private Player playerA; 
    private Player playerB;
    
    // Initial -1000 is the compensation with the spawning interval so the minions will be spawned the first frame of the game
    private long lastSpawn = -1000;
    
    
    //TODO Add a WaveTemplate
    /**
     * The constructor of the wave.
     * @param wavenr, the number of the wave, must be above 0.
     * @param multiplier, the multiplier number, must be above 1.
     * @param playerB, the enemy player, cannot be null.
     * @param bamount, the amount of byte minions, must be a positive number or zero.
     * @param kbamount, the amount of kilobyte minions, must be a positive number or zero.
     * @param mbamount, the amount of megabyte minions, must be a positive number or zero.
     * @param gbamount, the amount of gigabyte minions, must be a positive number or zero.
     * @param tbamount, the amount of terabyte minions, must be a positive number or zero.
     * @param pbamount , the amount of petabyte minions, must be a positive number or zero.
     */
    public Wave(GameEngine engine, int wavenr, double multiplier, Player playerA, Player playerB, 
            int bamount, int kbamount, int mbamount, int gbamount, int tbamount, int pbamount){
        
        //Set fields and initialise.
        this.engine = engine;
        waveNr = wavenr;
        waveActive = false;
        waveMultiplier = multiplier;
        minionList = new ArrayList<>();
        killedMinions = new ArrayList<>();
        this.playerA = playerA;
        this.playerB = playerB;
        Map map = Map.getInstance();
        Point baseA = map.getBaseLocationA();
        Point baseB = map.getBaseLocationB();
        
        //Create the minions for this wave.
        //Bytes
        for(int i=0; i<bamount; i++){
            Minion minion = new Minion(Data.DEFAULT_BYTE, multiplier, playerA.getUID());
            Minion minion2 = new Minion(Data.DEFAULT_BYTE, multiplier, playerB.getUID());
            minion.setEnemy(playerB);
            minion2.setEnemy(playerA);
            minion.setPosition(new Point(baseA.x, baseA.y));
            minion.setPosition(new Point(baseB.x, baseB.y));
            minionList.add(minion);
            minionList.add(minion2);
        }
        //KiloBytes
        for(int i=0; i<kbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_KILOBYTE, multiplier, playerA.getUID());
            Minion minion2 = new Minion(Data.DEFAULT_KILOBYTE, multiplier, playerB.getUID());
            minion.setEnemy(playerB);
            minion2.setEnemy(playerA);
            minion.setPosition(new Point(baseA.x, baseA.y));
            minion2.setPosition(new Point(baseB.x, baseB.y));
            minionList.add(minion);
            minionList.add(minion2);
        }
        //MegaBytes
        for(int i=0; i<mbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_MEGABYTE, multiplier, playerA.getUID());
            Minion minion2 = new Minion(Data.DEFAULT_MEGABYTE, multiplier, playerB.getUID());
            minion.setEnemy(playerB);
            minion2.setEnemy(playerA);
            minion.setPosition(new Point(baseA.x, baseA.y));
            minion2.setPosition(new Point(baseB.x, baseB.y));
            minionList.add(minion);
            minionList.add(minion2);
        }
        //GigaBytes
        for(int i=0; i<gbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_GIGABYTE, multiplier, playerA.getUID());
            Minion minion2 = new Minion(Data.DEFAULT_GIGABYTE, multiplier, playerB.getUID());
            minion.setEnemy(playerB);
            minion2.setEnemy(playerA);
            minion.setPosition(new Point(baseA.x, baseA.y));
            minion2.setPosition(new Point(baseB.x, baseB.y));
            minionList.add(minion);
            minionList.add(minion2);
        }
        //TeraBytes
        for(int i=0; i<tbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_TERABYTE, multiplier, playerA.getUID());
            Minion minion2 = new Minion(Data.DEFAULT_TERABYTE, multiplier, playerB.getUID());
            minion.setEnemy(playerB);
            minion2.setEnemy(playerA);
            minion.setPosition(new Point(baseA.x, baseA.y));
            minion2.setPosition(new Point(baseB.x, baseB.y));
            minionList.add(minion);
            minionList.add(minion2);
        }
        //PetaBytes
        for(int i=0; i<pbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_PETABYTE, multiplier, playerA.getUID());
            Minion minion2 = new Minion(Data.DEFAULT_PETABYTE, multiplier, playerB.getUID());
            minion.setEnemy(playerB);
            minion2.setEnemy(playerA);
            minion.setPosition(new Point(baseA.x, baseA.y));
            minion2.setPosition(new Point(baseB.x, baseB.y));
            minionList.add(minion);
            minionList.add(minion2);
        }
        
        
    }
    
    /**
     * Start the actual wave and spawn the containing minions.
     */
    public void startWave(){
        //Set to true so the wave becomes active.
        waveActive = true;
        OnCompleteTick listener = new GameEngine.OnCompleteTick(){
            
            @Override
            public void tickComplete(long elapsedtime){
                if(elapsedtime >= (lastSpawn + 1000)){
                    if(spawnedMinions < minionList.size()){
                        Minion m = minionList.get(spawnedMinions++);
                        IClientCreate createA = (IClientCreate)engine.getInterfacesA().get("create");
                        IClientCreate createB = (IClientCreate)engine.getInterfacesB().get("create");
                        
                        try {
                            createA.drawNewMinions(m, m.getOwnerID());
                            createB.drawNewMinions(m, m.getOwnerID());
                        } catch (RemoteException ex) {
                            HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
                        }
                        m.activate(engine, new Minion.MinionHeartbeat() { //Call upon the activate method of minion, and pass it the minionHeartbeat Interface.

                            @Override //Override this method, remove the passed minion from the current wave with removeMinion.
                            public void onMinionDeath(Minion minion, Boolean reachedBase) {
                                removeMinion(minion, reachedBase);

                                if(reachedBase)
                                {
                                    MinionEffect effect = new MinionEffect(Effect.REACHED_BASE, 0, new OnEffectExpired(){

                                        @Override
                                        public void onExpired() {
                                        }

                                    });
                                    m.applyEffect(effect);
                                }
                                else
                                {
                                    MinionEffect effect = new MinionEffect(Effect.DIE, 0, new OnEffectExpired(){

                                        @Override
                                        public void onExpired() {
                                        }

                                    });
                                    m.applyEffect(effect);
                                }
                            }

                        });
                        lastSpawn = elapsedtime;
                         //Exception Handling.
                    }
                }else{
                    try {
                        engine.unsubscribeListener(this);
                    } catch (UnsubscribeNonListenerException e) {
                        HackAttackServer.writeConsole(new Log(LogState.ERROR, e.toString()));
                    }
                }
            }
        };
        
        engine.setOnTickCompleteListener(listener);
    }
    
    /**
     * Returns an iterator of the minions in this wave.
     * @return, returns the Iterator of the minionList.
     */
    public Iterator<Minion> minions(){
        return minionList.iterator();
    }
    
    public int getWaveNr(){
        return waveNr;
    }
    
    public ArrayList<Minion> minionsAsList(){
        return minionList;
    }
    
    public boolean waveActive(){
        return (minionList.size() > 0);
    }
    
    /**
     * If the given minion is in the current wave, it gets removed and true is returned.
     * If the given minion isn't in the current wave, false is returned.
     * @param minion, the minion
     * @return true if removed successfully, false if not.
     */
    private boolean removeMinion(Minion minion, Boolean reachedBase){
        if (minionList.contains(minion)){
            killedMinions.add(minion);
            minionList.remove(minion);
            spawnedMinions--;
            minion.deactivate();
            if (reachedBase) {
                if(minion.getOwnerID() == playerA.getUID())
                {
                    HackAttackServer.writeConsole(new Log(LogState.INFO, "Minion with playerA ID " + playerA.getUID() + " has reached enemy base (enemy user ID: " + playerB.getUID() + ")"));
                    playerB.receiveDamage(minion.getDamage());
                }
                else if(minion.getOwnerID() == playerB.getUID())
                {
                    playerA.receiveDamage(minion.getDamage());
                    HackAttackServer.writeConsole(new Log(LogState.INFO, "Minion with playerA ID " + playerB.getUID() + " has reached enemy base (enemy user ID: " + playerA.getUID() + ")"));
                }
            }
            else{
                if(minion.getOwnerID() == playerA.getUID())
                {
                    playerB.addBitcoins(minion.getReward());
                    HackAttackServer.writeConsole(new Log(LogState.INFO, "Minion with playerA ID " + playerA.getUID() + " has been killed. " + minion.getReward() + " bitcoins have been added to " + playerB.getUID()));
                }
                else if(minion.getOwnerID() == playerB.getUID())
                {
                    playerA.addBitcoins(minion.getReward());
                    HackAttackServer.writeConsole(new Log(LogState.INFO, "Minion with playerA ID " + playerB.getUID() + " has been killed. " + minion.getReward() + " bitcoins have been added to " + playerA.getUID()));
                }
            }
            return true;
        } 
        else{
            return false;
        }
    }
}