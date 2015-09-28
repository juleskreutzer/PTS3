/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import java.awt.Point;
import java.util.List;

/**
 *
 * @author juleskreutzer
 */
public class Player {
    
    private double health;
    private String name;
    private double bitCoints;
    private Point[] baseLocation;
    private List<Module> modules;
    
    /**
     * Construct the player instance based on the data from the DATA-class
     * @param name Name of the player
     */
    public Player(String name)
    {
        
    }
    
    /**
     * Build a softwareinjector and return the spells that became available
     * @return List of Spells that became available
     */
    public List<Spell> buildSoftwareInjector()
    {
        return null;
    }
    
    
    void receiveDamage(double damage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
