/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hack.attack;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Igor
 */
public class Player {
    
    //Fields
    private double health; //amount of player's health
    private String name; //the player's name
    private double bitcoins; //amount of player's currency in bitcoins
    private Point baseLocation;
    private ArrayList<Module> modules;
    
    //Methods
    
    public Player(double h, String n, double b, Point l){
        health = h;
        name = n;
        bitcoins = b;
        baseLocation = l;
        modules = new ArrayList<Module>();
    }
    
    /**
    * Build a softwareinjector and return the spells that became available
    * @return List of Spells that became available
    */
    public List<Spell> buildSoftwareInjector()
    {
        return null;
    }
    
     public List<Spell> upgradeSoftwareInjector(){
        return null;
    }
    
    public void buildBitcoinMiner(){
    
    }
    
    public void upgradeBitcoinMiner(){
    
    }
    
    public void buildCpuUpgrade(){
    
    }
    
    public double getHealth(){
    return health;
    }
    
    public void setHealth(double Health){
    health = Health;
    }
    
    
    public String getName(){
    return name;
    }
    
    public void setName(String Name){
    name = Name;
    }
    
    public double getBitcoins(){
    return bitcoins;
    }
    
    public void setBitcoins(double Bitcoins){
    bitcoins = Bitcoins;
    }
    
    
    
    /**
     *
     * 
    */
    void receiveDamage(double damage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
