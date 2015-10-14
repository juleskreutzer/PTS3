/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.GameEngine.OnExecuteTick;
import hackattackfx.exceptions.DuplicateSpawnException;
import hackattackfx.exceptions.InvalidObjectException;
import hackattackfx.exceptions.UnsubscribeNonListenerException;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
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
    
    // Initial -3000 is the compensation with the spawning interval so the minions will be spawned the first frame of the game
    private long lastSpawn = -3000;
    
    
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
        Map map = Map.getInstance();
        Point base = map.getBaseLocationA();
        
        //Create the minions for this wave.
        //Bytes
        for(int i=0; i<bamount; i++){
            Minion minion = new Minion(Data.DEFAULT_BYTE, multiplier);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        //KiloBytes
        for(int i=0; i<kbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_KILOBYTE, multiplier);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        //MegaBytes
        for(int i=0; i<mbamount; i++){
           Minion minion = new Minion(Data.DEFAULT_MEGABYTE, multiplier);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        //GigaBytes
        for(int i=0; i<gbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_GIGABYTE, multiplier);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        //TeraBytes
        for(int i=0; i<tbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_TERABYTE, multiplier);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        //PetaBytes
        for(int i=0; i<pbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_PETABYTE, multiplier);
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
        
        //Create an Iterator instance of the minion List.
        Iterator<Minion> minionit = minions();
        
        GameEngine.getInstance().setOnTickListener(new GameEngine.OnExecuteTick(){
            
            @Override
            public void onTick(long elapsedtime){
                if(elapsedtime >= (lastSpawn + 3000)){
                     Minion m = null; //Initialise the minion m.
                    if(minionit.hasNext()){
                        m = minionit.next(); //If the minion Iterator contains another minion, minion m will become that minion.  
                        try{
                            GraphicsEngine.getInstance().spawn(m); //Get an instance of the graphics engine, and spawn the minion with it.
                            m.activate(new Minion.MinionHeartbeat() { //Call upon the activate method of minion, and pass it the minionHeartbeat Interface.

                                @Override //Override this method, remove the passed minion from the current wave with removeMinion.
                                public void onMinionDeath(Minion minion) {
                                    removeMinion(minion);
                                }

                            });
                             //Exception Handling.
                        }catch(DuplicateSpawnException e){
                            System.out.println(e.toString());
                        }catch(InvalidObjectException e){
                            System.out.println(e.toString());
                        }
                    }
                }
            }
            
        });
    }
    
    /**
     * Returns an iterator of the minions in this wave.
     * @return, returns the Iterator of the minionList.
     */
    public Iterator<Minion> minions(){
        return minionList.iterator();
    }
    
    /**
     * If the given minion is in the current wave, it gets removed and true is returned.
     * If the given minion isn't in the current wave, false is returned.
     * @param minion, the minion
     * @return, true if removed successfully, false if not.
     */
    private boolean removeMinion(Minion minion){
        if (minionList.contains(minion)){
        minionList.remove(minion);
        return true;
        } 
        else{
            return false;
        }
    }
    
    
}
