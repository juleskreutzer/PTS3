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
    private ArrayList<Module> modules;
    
    //Methods
    
<<<<<<< HEAD
    /**
    * Initialize a SoftwareInjector object, add the object to the modules field and return a list of spells that became available
    * @return List of Spells that became available
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
=======
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
>>>>>>> origin/master
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
    
<<<<<<< HEAD
    /**
     * Initialize a CpuUpgrade object and add the object to the modules field
     */
    public void buildCpuUpgrade(){
        /***Need to implent the parameters***
        modules.add(new CpuUpgrade());
        */
    }
    
    /**
     * Retrieve the player's private health field
     * @return double from the private health field
     */
=======
    public void buildCPUUpgrade(){
    
    }
    
    public void upgradeCPUUpgrade(){
        
    }
    
>>>>>>> origin/master
    public double getHealth(){
        return health;
    }
    
<<<<<<< HEAD
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
=======
    public void setHealth(double health){
        health = health;
    }
    
    void receiveDamage(double damage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
>>>>>>> origin/master
    public String getName(){
        return name;
    }
    
<<<<<<< HEAD
    /**
     * Set the player's private name field from the Name parameter
     * @param Name
     */
    public void setName(String Name){
        name = Name;
=======
    public void setName(String name){
        name = name;
>>>>>>> origin/master
    }
    
    /**
     * Retrieve the player's private bitcoins field
     * @return double from the private bitcoins field
     */
    public double getBitcoins(){
        return bitcoins;
    }
    
<<<<<<< HEAD
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
=======
    public void setBitcoins(double bitcoins){
        bitcoins = bitcoins;
    }
    
    public void addBitcoins(double amount){
        bitcoins += amount;
    }
    
    public void removeBitcoins(double amount){
        bitcoins -= amount;
    }
    
    public void buildDefense(Defense defence){
        
>>>>>>> origin/master
    }
    
    public void upgradeDefense(Defense defense, Effect effect){
        
    }
    
    public Point getBaseLocation(){
        return baseLocation;
    }
    
    
    
    
    
    
    
}
