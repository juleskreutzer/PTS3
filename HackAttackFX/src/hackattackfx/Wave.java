/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.GameEngine.OnCompleteTick;
import hackattackfx.exceptions.DuplicateSpawnException;
import hackattackfx.exceptions.InvalidObjectException;
import hackattackfx.exceptions.UnsubscribeNonListenerException;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Igor, Jasper Rouwhorst
 */
public class Wave {
    private int waveNr; //The number of the wave.
    private boolean waveActive; //If the wave is active or not.
    private double waveMultiplier; //The multiplier of the wave.
    
    private ArrayList<Minion> minionList;
    private static int spawnedMinions;
    private ArrayList<Minion> killedMinions;
    private Player enemyPlayer;
    
    // Initial -1000 is the compensation with the spawning interval so the minions will be spawned the first frame of the game
    private long lastSpawn = -1000;
    
    
    //TODO Add a WaveTemplate
    /**
     * The constructor of the wave.
     * @param wavenr, the number of the wave, must be above 0.
     * @param multiplier, the multiplier number, must be above 1.
     * @param enemyplayer, the enemy player, cannot be null.
     * @param bamount, the amount of byte minions, must be a positive number or zero.
     * @param kbamount, the amount of kilobyte minions, must be a positive number or zero.
     * @param mbamount, the amount of megabyte minions, must be a positive number or zero.
     * @param gbamount, the amount of gigabyte minions, must be a positive number or zero.
     * @param tbamount, the amount of terabyte minions, must be a positive number or zero.
     * @param pbamount , the amount of petabyte minions, must be a positive number or zero.
     */
    public Wave(int wavenr, double multiplier, Player enemyplayer, 
            int bamount, int kbamount, int mbamount, int gbamount, int tbamount, int pbamount){
        
        //Set fields and initialise.
        waveNr = wavenr;
        waveActive = false;
        waveMultiplier = multiplier;
        minionList = new ArrayList<>();
        killedMinions = new ArrayList<>();
        this.enemyPlayer = enemyplayer;
        Map map = Map.getInstance();
        Point base = map.getBaseLocationA();
        
        //Create the minions for this wave.
        //Bytes
        for(int i=0; i<bamount; i++){
            Minion minion = new Minion(Data.DEFAULT_BYTE, multiplier, enemyPlayer);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        //KiloBytes
        for(int i=0; i<kbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_KILOBYTE, multiplier, enemyPlayer);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        //MegaBytes
        for(int i=0; i<mbamount; i++){
           Minion minion = new Minion(Data.DEFAULT_MEGABYTE, multiplier, enemyPlayer);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        //GigaBytes
        for(int i=0; i<gbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_GIGABYTE, multiplier, enemyPlayer);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        //TeraBytes
        for(int i=0; i<tbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_TERABYTE, multiplier, enemyPlayer);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        //PetaBytes
        for(int i=0; i<pbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_PETABYTE, multiplier, enemyPlayer);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
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
                        try{
                            GraphicsEngine.getInstance().spawn(m); //Get an instance of the graphics engine, and spawn the minion with it.
                            m.activate(new Minion.MinionHeartbeat() { //Call upon the activate method of minion, and pass it the minionHeartbeat Interface.

                                @Override //Override this method, remove the passed minion from the current wave with removeMinion.
                                public void onMinionDeath(Minion minion, Boolean reachedBase) {
                                    removeMinion(minion, reachedBase);
                                }

                            });
                            lastSpawn = elapsedtime;
                             //Exception Handling.
                        }catch(DuplicateSpawnException e){
                            System.out.println(e.toString());
                        }catch(InvalidObjectException e){
                            System.out.println(e.toString());
                        }
                    }else{
                        try {
                            GameEngine.getInstance().unsubscribeListener(this);
                        } catch (UnsubscribeNonListenerException e) {
                            System.out.println(e.toString());
                        }
                    }
                }
            }
            
        };
        
        GameEngine.getInstance().setOnTickCompleteListener(listener);
    }
    
    /**
     * Returns an iterator of the minions in this wave.
     * @return, returns the Iterator of the minionList.
     */
    public Iterator<Minion> minions(){
        return minionList.iterator();
    }
    
    public ArrayList<Minion> minionsAsList(){
        return minionList;
    }
    
    public boolean waveActive(){
        return minionList.size() > 0 ? true : false;
    }
    
    /**
     * If the given minion is in the current wave, it gets removed and true is returned.
     * If the given minion isn't in the current wave, false is returned.
     * @param minion, the minion
     * @return, true if removed successfully, false if not.
     */
    private boolean removeMinion(Minion minion, Boolean reachedBase){
        if (minionList.contains(minion)){
            killedMinions.add(minion);
            minionList.remove(minion);
            spawnedMinions--;
            minion.deactivate();
            if (reachedBase) {
                enemyPlayer.receiveDamage(minion.getDamage());
            }
            else{
            enemyPlayer.addBitcoins(minion.getReward());
        }
            return true;
        } 
        else{
            return false;
        }
    }
}