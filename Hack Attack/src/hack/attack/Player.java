/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hack.attack;
import hack.attack.enums.Effect;
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
    private List<Module> modules;
    
    public Player(){}
    
    public Player(double h, String n, double b, Point l) {
        health = h;
        name = n;
        bitcoins = b;
        baseLocation = l;
        modules = new ArrayList<Module>();
    }
    
    //Methods
    
    /**
    * Initialize a SoftwareInjector object, add the object to the modules field and return a list of spells that became available
    */  
    public void buildSoftwareInjector(){
        /***Need to implent the parameters***
        SoftwareInjector softwareinjector = new SoftwareInjector();
        modules.add(softwareinjector);
        return softwareinjector.getSpells();
        */
        
    }
    
    /**
     * Retrieve a SoftwareInjector object from the modules field and call the Upgrade method from inside the class
     * @return
     */
    public boolean upgradeSoftwareInjector(){
        for (Module module : modules)
            if(module instanceof SoftwareInjector)
            {
               
               /*NOT FULLY IMPLENTED
                return softwareinjector.upgrade();
               */
            }
        return false;
    }
    
    public List<Spell> getSpells(){        
        for (Module module : modules)
            if(module instanceof SoftwareInjector)
                return ((SoftwareInjector)module).getSpells();
        return null;
    }
    
    /**
     * Initialize a BitcoinMiner object and add the object to the modules field
     */
    public void buildBitcoinMiner(){
        /***Need to implent the parameters***
        modules.add(new BitcoinMiner());
        */
    }
    
    /**
     * Retrieve a BitcoinMiner object from the modules field and call the Upgrade method from inside the class
     * @return boolean if the upgrade was successfully executed 
     */
    public boolean upgradeBitcoinMiner(){
        for (Module module : modules)
            if(module instanceof BitcoinMiner)
            {
                BitcoinMiner bitcoinminer = (BitcoinMiner)module;
                return bitcoinminer.upgrade();
            }
        return false;
    }
    
    /**
     * Initialize a CpuUpgrade object and add the object to the modules field
     */
    public void buildCPUUpgrade(){
        /***Need to implent the parameters***
        modules.add(new CpuUpgrade());
        */
    }
    
    public boolean upgradeCPUUpgrade(){
        return false;
    }
    
    /**
     * Retrieve the player's private health field
     * @return double from the private health field
     */
    public double getHealth(){
        return health;
    }
    
    /**
     * Set the player's private health field from the Health parameter
     * @param Health
     */
    public void setHealth(double Health){
        health = Health;
    }
    
    /**
     * Retrieve the player's private name field
     * @return double from the private name field
     */
    public String getName(){
        return name;
    }
    
    /**
     * Set the player's private name field from the Name parameter
     * @param Name
     */
    public void setName(String Name){
        name = Name;
    }
    
    /**
     * Retrieve the player's private bitcoins field
     * @return double from the private bitcoins field
     */
    public double getBitcoins(){
        return bitcoins;
    }

    /**
     * Set the player's private bitcoin field from the Bitcoin parameter
     * @param Bitcoins
     */
    public void setBitcoins(double Bitcoins){
        bitcoins = Bitcoins;
    }
    
    
    /**
     * Subtract the damage parameter from the health field
     * @param damage
    */
    public void receiveDamage(double damage) {
        health = health - damage;
    }
    
    /**
     * Retrieve the player's private modules field
     * @return List of Module from the private modules field
     */
    public List<Module> getModules(){
        return modules;
    }
    
    public void addBitcoins(double amount){
        bitcoins += amount;
    }
    
    public void removeBitcoins(double amount){
        bitcoins -= amount;
    }
    
    public void buildDefense(Defense defence){
        
    }
    
    public void upgradeDefense(Defense defense, Effect effect){
        
    }
    
    public Point getBaseLocation(){
        return baseLocation;
    }
    
    
    
    
    
    
    
}
