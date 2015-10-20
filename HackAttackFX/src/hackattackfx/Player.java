/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hackattackfx;

import hackattackfx.exceptions.*;
import hackattackfx.BitcoinMiner.OnMineComplete;
import hackattackfx.enums.Effect;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    /**
     * Constructor for the player
     * @param h health value for the player
     * @param n name for the player
     * @param b bitcoins value for the player
     * @param l baselocation for the player
     */
    public Player(double h, String n, double b, Point l) {
        health = h;
        name = n;
        bitcoins = b;
        baseLocation = l;
        modules = new ArrayList<Module>();
    }
    
    //Methods
    
    /**
     * Check if the player can build or upgrade the desired module
     * @param cost cost of the module to build or upgrade it
     * @return returns true if the player can build or upgrade, false if not
     */
    private boolean CheckCostToBuildOrUpgrade(double cost)
    {
        if(cost <= this.bitcoins) { return true; } else { return false; }
    }
    
    /**
    * Initialize a SoftwareInjector object, add the object to the modules field and return a list of spells that became available
    * @return The newly created {@link SoftwareInjector}
    */  
    public SoftwareInjector buildSoftwareInjector(SoftwareInjector injector){
        modules.add(injector);
        return injector;
    }
    
    /**
     * Retrieve a SoftwareInjector object from the modules field and call the Upgrade method from inside the class
     * @return
     */
    public boolean upgradeSoftwareInjector(SoftwareInjector injector){
        if(injector.upgrade()) { return true; } else { return false; }
    }
    
    public List<Spell> getSpells(){        
        for (Module module : modules)
            if(module instanceof SoftwareInjector)
                return ((SoftwareInjector)module).getSpells();
        return null;
    }
    
    /**
     * Initialize a BitcoinMiner object and add the object to the modules field
     * @return The newly created {@link BitcoinMiner}
     */
    public BitcoinMiner buildBitcoinMiner(BitcoinMiner miner){
        modules.add(miner);
        try{
            miner.setOnMineListener(new OnMineComplete() {

                @Override
                public void onMine(double mineValue) {

                    addBitcoins(mineValue);

                }
            });
        }
        catch(DuplicateListenerException ex)
        {
            System.out.print(ex.toString());
        }
        
        return miner;
    }
    
    /**
     * Retrieve a BitcoinMiner object from the modules field and call the Upgrade method from inside the class
     * @return boolean if the upgrade was successfully executed 
     */
    public boolean upgradeBitcoinMiner(BitcoinMiner miner) throws NoUpgradeAllowedException{
        if(miner.upgrade()) { return true; } else { return false; }
    }
    
    /**
     * Initialize a CpuUpgrade object and add the object to the modules field
     */
    public CPUUpgrade buildCPUUpgrade(CPUUpgrade cpu){
        modules.add(cpu);
        return cpu;
    }
    
    public boolean upgradeCPUUpgrade(CPUUpgrade cpu) throws NoUpgradeAllowedException{
        // First get a list of all minions
        Iterator<Minion> minions = GameEngine.getInstance().getActiveWave().minions();
        
        // Upgrade the CPU - Lets make it an 12GHz octa-core ;)
        if(cpu.upgrade(minions)) { return true; } else { return false; }
    }
    
    public Defense buildDefense(Defense defense){
        modules.add(defense);
        return defense;
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
    
    public boolean upgradeDefense(Defense defense, Effect effect) throws NoUpgradeAllowedException{
        if(defense.upgrade())
        {
            defense.setEffect(effect);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public Point getBaseLocation(){
        return baseLocation;
    }
}
