/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.DuplicateSpawnException;
import java.util.ArrayList;
import java.util.Iterator;

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
            int bamount, int kbamount, int mbamount, int gbamount, int tbamount){
        
        waveNr = wavenr;
        waveActive = false;
        waveMultiplier = multiplier;
        minionList = new ArrayList<Minion>();
        
        for(int i=0; i<bamount; i++){
            minionList.add(new Minion(Data.DEFAULT_BYTE, new Minion.MinionHeartbeat() {

                @Override
                public void onMinionDeath(Minion minion) {
                    removeMinion(minion);
                }
            }));
        }
        for(int i=0; i<kbamount; i++){
            minionList.add(new Minion(Data.DEFAULT_KILOBYTE, new Minion.MinionHeartbeat() {

                @Override
                public void onMinionDeath(Minion minion) {
                    removeMinion(minion);
                }
            }));
        }
        for(int i=0; i<mbamount; i++){
            minionList.add(new Minion(Data.DEFAULT_MEGABYTE, new Minion.MinionHeartbeat() {

                @Override
                public void onMinionDeath(Minion minion) {
                    removeMinion(minion);
                }
            }));
        }
        for(int i=0; i<gbamount; i++){
            minionList.add(new Minion(Data.DEFAULT_GIGABYTE, new Minion.MinionHeartbeat() {

                @Override
                public void onMinionDeath(Minion minion) {
                    removeMinion(minion);
                }
            }));
        }
        for(int i=0; i<tbamount; i++){
            minionList.add(new Minion(Data.DEFAULT_TERABYTE, new Minion.MinionHeartbeat() {

                @Override
                public void onMinionDeath(Minion minion) {
                    removeMinion(minion);
                }
            }));
        }
        
    }
    
    /**
     * Start the actual wave and spawn the containing minions
     */
    public void startWave(){
        waveActive = true;
        GameEngine.getInstance().setOnTickListener(new GameEngine.OnExecuteTick(){
            
            @Override
            public void onTick(int elapsedtime){
                if(elapsedtime % GameEngine.FPS == 0){
                    Minion m = minions().next();
                    try{
                        GraphicsEngine.getInstance().spawn(m);
                    }catch(DuplicateSpawnException e){
                        System.out.println(e.toString());
                    }
                }
            }
            
        });
    }
    
    public Iterator<Minion> minions(){
        return minionList.iterator();
    }
    
    private boolean removeMinion(Minion minion){
        minionList.remove(minion);
        return true;
    }
    
    
}