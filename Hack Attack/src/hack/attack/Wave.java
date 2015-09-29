/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import java.util.ArrayList;

/**
 *
 * @author Igor, Jasper Rouwhorst
 */
public class Wave {
    private int waveNr;
    private double waveMultiplier;
    
    ArrayList<Minion> minionList;
    
    public Wave(int wavenr, double multiplier, Player enemyplayer, 
            int bamount, int kbamount, int mbamount, int gbamount, int tbamount){
        
        waveNr = wavenr;
        waveMultiplier = multiplier;
        minionList = new ArrayList<Minion>();
        
        for(int i=0; i<bamount; i++){
            minionList.add(new Minion(Data.DEFAULT_BYTE, new Minion.MinionHeartbeat() {

                @Override
                public void onMinionDeath(Minion minion) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }));
        }
        for(int i=0; i<kbamount; i++){
            minionList.add(new Minion(Data.DEFAULT_KILOBYTE, new Minion.MinionHeartbeat() {

                @Override
                public void onMinionDeath(Minion minion) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }));
        }
        for(int i=0; i<mbamount; i++){
            minionList.add(new Minion(Data.DEFAULT_MEGABYTE, new Minion.MinionHeartbeat() {

                @Override
                public void onMinionDeath(Minion minion) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }));
        }
        for(int i=0; i<gbamount; i++){
            minionList.add(new Minion(Data.DEFAULT_GIGABYTE, new Minion.MinionHeartbeat() {

                @Override
                public void onMinionDeath(Minion minion) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }));
        }
        for(int i=0; i<tbamount; i++){
            minionList.add(new Minion(Data.DEFAULT_TERABYTE, new Minion.MinionHeartbeat() {

                @Override
                public void onMinionDeath(Minion minion) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }));
        }
        
    }
    
    
}
