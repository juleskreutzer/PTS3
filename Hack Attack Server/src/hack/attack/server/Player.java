/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hack.attack.server;

import hack.attack.rmi.Defense;
import hack.attack.rmi.Spell;
import hack.attack.rmi.Minion;
import hack.attack.rmi.Module;
import hack.attack.server.exceptions.*;
import hack.attack.server.BitcoinMiner.OnMineComplete;
import hack.attack.rmi.Effect;
import hack.attack.server.enums.LogState;
import hack.attack.server.logger.Log;
import hack.attack.server.templates.*;
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
    private GameEngine engine;
    
    private double health; //amount of player's health
    private String name; //the player's name
    private double bitcoins; //amount of player's currency in bitcoins
    private Point baseLocation;
    private List<Module> modules;
    private int uID;
    
    public Player(){}
    /**
     * Constructor for the player
     * @param h health value for the player
     * @param n name for the player
     * @param b bitcoins value for the player
     * @param l baselocation for the player
     */
    public Player(GameEngine engine, double h, String n, double b, Point l, int uID) {
        this.engine = engine;
        health = h;
        name = n;
        bitcoins = b;
        baseLocation = l;
        this.uID = uID;
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
        return cost <= this.bitcoins;
    }
    
    /**
    * Initialize a SoftwareInjector object, add the object to the modules field, lower the player bitcoins and return a list of spells that became available
    * @return The newly created {@link SoftwareInjector}
    */  
    public SoftwareInjector buildSoftwareInjector(SoftwareInjectorTemplate template, Point position, int width, int height){
        try {
            SoftwareInjector injector;
                injector = new SoftwareInjector(template, position, width, height);
            this.removeBitcoins(injector.getCost());
            modules.add(injector);
            return injector;
        } catch (NotEnoughBitcoinsException | InvalidModuleEnumException ex) {
            new Log(LogState.ERROR, ex.getMessage());
        }
        return null;
    }
    
    /**
     * Retrieve a SoftwareInjector object from the modules field and call the Upgrade method from inside the class
     * @param injector
     * @return
     */
    public boolean upgradeSoftwareInjector(SoftwareInjector injector) {
        if(injector.getCost() <= this.getBitcoins())
        {
            try {
                if(injector.upgrade()) 
                { 

                    this.removeBitcoins(injector.getCost()); 
                    return true;

                }
            }
            catch (NotEnoughBitcoinsException | NoUpgradeAllowedException ex) {
                new Log(LogState.ERROR, ex.getMessage());
            }
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
     * Initialize a BitcoinMiner object, lower the player bitcoins and add the object to the modules field
     * @return The newly created {@link BitcoinMiner}
     */
    public BitcoinMiner buildBitcoinMiner(BitcoinMiner miner) throws NotEnoughBitcoinsException{
        try{
            miner.setOnMineListener(new OnMineComplete() {

                @Override
                public void onMine(double mineValue) {

                    addBitcoins(mineValue);

                }
            });
            this.removeBitcoins(miner.getCost());
            miner.activate(engine);
            modules.add(miner);
        }
        catch(DuplicateListenerException ex)
        {
            HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.toString()));
        }
        
        return miner;
    }
    
    /**
     * Retrieve a BitcoinMiner object from the modules field, lower the player bitcoins and call the Upgrade method from inside the class
     * @return boolean if the upgrade was successfully executed 
     */

    public boolean upgradeBitcoinMiner(BitcoinMiner miner) throws NotEnoughBitcoinsException{
        try {
            this.removeBitcoins(miner.getCost());
            return miner.upgrade();
        } catch (NoUpgradeAllowedException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Initialize a CpuUpgrade object and add the object to the modules field
     */
    public CPUUpgrade buildCPUUpgrade(CPUUpgrade cpu) throws NotEnoughBitcoinsException{
        this.removeBitcoins(cpu.getCost());
        modules.add(cpu);
        return cpu;
    }
    
    public boolean upgradeCPUUpgrade(CPUUpgrade cpu) throws NotEnoughBitcoinsException{
        try {
            // First get a list of all minions
            Iterator<Minion> minions = engine.getActiveWave().minions();
            this.removeBitcoins(cpu.getCost());
            return cpu.upgrade(minions);
        } catch (NoUpgradeAllowedException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
	}
    
    public Defense buildDefense(Defense defense) throws NotEnoughBitcoinsException{
        this.removeBitcoins(defense.getCost());
        modules.add(defense);
        defense.activate(engine);
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
    
    /**
     * Increase the amount of bitcoins for the player with the given amount value
     * @param amount The amout of bitcoins that will be increased.
     */
    public void addBitcoins(double amount){
        if(amount < 0) throw new IllegalArgumentException("Amount may not be less than 0");
        bitcoins += amount;
    }
    
    /**
     * Remove the amount from the player's bitcoins.
     * First check if the amount > 0, than check if the amount is not greater than the amount of bitcoins the player has.
     * @param amount The amount that the players bitcoins will be lowered with
     * @throws NotEnoughBitcoinsException Throws when the amount > bitcoins of the player.
     */
    public void removeBitcoins(double amount) throws NotEnoughBitcoinsException{
        if (amount < 0) throw new IllegalArgumentException("Amount may not be less than 0");
        

        if (!this.CheckCostToBuildOrUpgrade(amount)) throw new NotEnoughBitcoinsException("You do not have enough Bitcoins to build this module.");

        if (amount > this.getBitcoins())
        {
            throw new NotEnoughBitcoinsException ("Not enough bitcoins");
        } 

        
        this.bitcoins -= amount;
    }
    

    public boolean upgradeDefense(Defense defense, Effect effect) throws NotEnoughBitcoinsException{
        try {
            if(defense.upgrade())
            {
                defense.setEffect(effect);
                this.removeBitcoins(defense.getCost());
                return true;
            }
            else
            {
                return false;
            }
        } catch (NoUpgradeAllowedException | NotEnoughBitcoinsException ex) {
            new Log(LogState.WARNING, ex.getMessage());
	}
        
        return false;
    }		
    
    public Point getBaseLocation(){
        return baseLocation;
    }
    
    public int getUID()
    {
        return this.uID;
    }
}
