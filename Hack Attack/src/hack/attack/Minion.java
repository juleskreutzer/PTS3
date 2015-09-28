package hack.attack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bart van Keersop
 */
public class Minion {
    
    public interface MinionHeartbeat{
        void onMinionDeath(Minion minion);
    }
    
    //Fields
    private double hp; //remaining hitpoints of the minion.
    private double atp; //attackpower of the minion.
    private double speed; //the speed of the unit.
    private Status[] statuses; //the statuses of the unit.
    
    
}
