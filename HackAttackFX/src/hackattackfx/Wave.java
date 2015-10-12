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
    private int waveNr;
    private boolean waveActive;
    private double waveMultiplier;
    
    private ArrayList<Minion> minionList;
    
    public Wave(int wavenr, double multiplier, Player enemyplayer, 
            int bamount, int kbamount, int mbamount, int gbamount, int tbamount, int pbamount){
        
        waveNr = wavenr;
        waveActive = false;
        waveMultiplier = multiplier;
        minionList = new ArrayList<Minion>();
        Map map = Map.getInstance();
        Point base = map.getBaseLocationA();
        
        for(int i=0; i<bamount; i++){
            Minion minion = new Minion(Data.DEFAULT_BYTE);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        for(int i=0; i<kbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_KILOBYTE);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        for(int i=0; i<mbamount; i++){
           Minion minion = new Minion(Data.DEFAULT_MEGABYTE);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        for(int i=0; i<gbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_GIGABYTE);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        for(int i=0; i<tbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_TERABYTE);
            minion.setPosition(new Point(base.x, base.y));
            minionList.add(minion);
        }
        for(int i=0; i<pbamount; i++){
            Minion minion = new Minion(Data.DEFAULT_PETABYTE);
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
        
        //Initialise a timer that executes the method below at an interval of every 1000 mS.
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
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
                            
                        }); //Exception Handling.
                    }catch(DuplicateSpawnException e){
                        System.out.println(e.toString());
                    }catch(InvalidObjectException e){
                        System.out.println(e.toString());
                    }
                }
            }
        }, 0, 1000); //Set timer to 1000 mS.
        
        
        /*
        GameEngine.getInstance().setOnTickListener(new GameEngine.OnExecuteTick(){
            
            @Override
            public void onTick(long elapsedtime){
                
                
                if(elapsedtime % 3000 <= 3000){
                    Minion m = null;
                    if(minionit.hasNext()){
                        m = minionit.next();
                        try{
                            GraphicsEngine.getInstance().spawn(m);
                        }catch(DuplicateSpawnException e){
                            System.out.println(e.toString());
                        }catch(InvalidObjectException e){
                            System.out.println(e.toString());
                        }
                    }else{
                        // If no more minions are found, remove this wave from the gameengine tick event
                        try {
                            GameEngine.getInstance().unsubscribeListener(listener);
                        } catch (UnsubscribeNonListenerException ex) {
                            Logger.getLogger(Wave.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            
        });*/
    }
    
    public Iterator<Minion> minions(){
        return minionList.iterator();
    }
    
    private boolean removeMinion(Minion minion){
        minionList.remove(minion);
        return true;
    }
    
    
}
