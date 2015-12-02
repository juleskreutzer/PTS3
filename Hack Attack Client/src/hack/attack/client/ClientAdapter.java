/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import hack.attack.client.enums.Effect;
import hack.attack.client.exceptions.DuplicateListenerException;
import hack.attack.client.exceptions.NoUpgradeAllowedException;
import hack.attack.client.exceptions.NotEnoughBitcoinsException;
import hack.attack.client.interfaces.*;
import hack.attack.client.templates.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juleskreutzer
 */
public class ClientAdapter implements IClientCreate, IClientUpdate, IClientDelete {
    
    private String sessionKey;
    private IServerUpdate update;
    private GraphicsEngine engine;
    private final IClientCreate clientCreate;
    private final IClientUpdate clientUpdate;
    private final IClientDelete clientDelete;
    
    private Account account;
    
    private static ClientAdapter instance;
    
    private ClientAdapter()
    {
        this.engine = GraphicsEngine.getInstance();
        this.clientCreate = this;
        this.clientDelete = this;
        this.clientUpdate = this;
    }
    
    public static ClientAdapter getInstance()
    {
        return instance == null ? new ClientAdapter() : instance;
    }
    
    /**
     * This method sets the sessionKey than is needed to communicate with the server
     * @param sessionKey encrypted sessionKey string
     */
    public void setSessionKey(String sessionKey)
    {
        this.sessionKey = sessionKey;
    }
    
    /**
     * This method sets the IServerUpdate interface instance for the client so it can communicate with the server
     * @param update IServerUpdate instance
     */
    public void setIServerUpdate(IServerUpdate update)
    {
        this.update = update;
    }
    
    /**
     * This method sets the account that is needed to build, cast or upgrade modules
     * @param account Instance of an account class for the current player
     */
    public void setAccount(Account account)
    {
        this.account = account;
    }
    
    /**
     * This method will return a HashMap that is needed to call the IServerConnect methods
     * @return HashMap<String, IClient> containing the following interfaces:
     * <ul>
     *  <li>Key: <b>create</b> -> <b>IClientCreate</b></li>
     *  <li>Key: <b>upgrade</b> -> <b>IClientUpgrade</b></li>
     *  <li>Key: <b>delete</b> -> <b>IClientDelete</b></li>
     * </ul>
     */
    public HashMap<String, IClient> getInterfaces()
    {
        HashMap<String, IClient> interfaces = new HashMap<>();
        interfaces.put("create", this.clientCreate);
        interfaces.put("update", this.clientUpdate);
        interfaces.put("delete", this.clientDelete);
        
        return interfaces;
    }

    @Override
    public void drawNewModules(List<Module> modules) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawNewMinions(List<Minion> minions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawNewSpells(List<Spell> spells) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void redrawCurrentModules(List<Module> modules) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void redrawCurrentMinions(List<Minion> minions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void redrawCurrentSpells(List<Spell> minions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCurrentModules(List<Module> modules) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCurrentMinions(List<Minion> minions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCurrentSpells(List<Spell> spells) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateLabels(Player playerA, Player playerB, int waveNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
    * Initialize a SoftwareInjector object, add the object to the modules field, lower the player bitcoins and return a list of spells that became available
     * @param injector The object the player want's to build
    * @return The object that the player has build
     * @throws NotEnoughBitcoinsException when the player doesn't have enough bitcoins to build the module
    */  
    public SoftwareInjector buildSoftwareInjector(SoftwareInjectorTemplate injector) throws NotEnoughBitcoinsException{
        update.buildModule(sessionKey, account.getUID(), injector);
        
    }
    
    /**
     * Retrieve a SoftwareInjector object from the modules field and call the Upgrade method from inside the class
     * @param injector The object the player want's to upgrade
     * @return A Boolean indicating if the upgrade was successful. True: Success, False: Not success
     * @throws NotEnoughBitcoinsException when the player doesn't have enough bitcoins to upgrade the object
     * @throws NoUpgradeAllowedException when the given parameter is already at it's highest level.
     */
    public boolean upgradeSoftwareInjector(SoftwareInjector injector) throws NotEnoughBitcoinsException, NoUpgradeAllowedException
    {
        update.upgradeModule(sessionKey, account.getUID(), injector);
        
    }
    
    /**
     * Initialize a BitcoinMiner object, lower the player bitcoins and add the object to the modules field
     * @param miner The object the client want's to build
     * @return an BitcoinMiner object that the player has build.
     * @throws NotEnoughBitcoinsException when the player doesn't have enough bitcoins to build the BitcoinMiner
     */
    public BitcoinMiner buildBitcoinMiner(BitCoinMinerTemplate miner) throws NotEnoughBitcoinsException{
        update.buildModule(sessionKey, account.getUID(), miner);
    }
    
    /**
     * Retrieve a BitcoinMiner object from the modules field, lower the player bitcoins and call the Upgrade method from inside the class
     * @param miner The object the client want's to upgrade
     * @return Boolean indicating if the upgrade was successful. True: Success, False: Not success
     * @throws NotEnoughBitcoinsException when the player doesn't have enough bitcoins to upgrade the object
     * @throws NoUpgradeAllowedException when the given parameter is already at it's highest level.
     */
    public boolean upgradeBitcoinMiner(BitcoinMiner miner) throws NotEnoughBitcoinsException, NoUpgradeAllowedException {
        update.upgradeModule(sessionKey, account.getUID(), miner);
    }
    
    /**
     * Initialize a CpuUpgrade object and add the object to the modules field
     * @param cpu The object the client want's to build
     * @return an CPUUpgrade object that the player has build.
     * @throws NotEnoughBitcoinsException when the player hasn't enough bitcoins to build the CPUUpgrade object
     */
    public CPUUpgrade buildCPUUpgrade(CPUUpgradeTemplate cpu) throws NotEnoughBitcoinsException{
       update.buildModule(sessionKey, account.getUID(), cpu);
    }
    
    /**
     * Upgrade the CPUUpgrade instance to a higher level.
     * @param cpu The object the client want's to upgrade
     * @return A boolean indicating if the upgrade was successful. True : Succes, False : Not succes
     * @throws NotEnoughBitcoinsException when the client doesn't have enough bitcoins to upgrade the module
     * @throws NoUpgradeAllowedException when the given parameter is already at it's highest level.
     */
    public boolean upgradeCPUUpgrade(CPUUpgrade cpu) throws NotEnoughBitcoinsException, NoUpgradeAllowedException {
        update.upgradeModule(sessionKey, account.getUID(), cpu);
    }
    
    /**
     * Build a new Defense module
     * @param defense The module the client want's to build
     * @return An defense object that the player has build.
     * @throws NotEnoughBitcoinsException when the client doesn't have enough bitcoins to build the module
     */
    public Defense buildDefense(DefenseTemplate defense) throws NotEnoughBitcoinsException{
       update.buildModule(sessionKey, account.getUID(), defense);
    }
    
    /**
     * Upgrade the Defense instance to a higher level
     * @param defense The Defence object the client want's to upgrade
     * @param effect An effect the player want's to give to the module when it's been upgraded.
     * @return A boolean indicating if the upgrade was successful. True: Succes, False: Not success.
     * @throws NotEnoughBitcoinsException when the client doesn't have enough bitcoins to upgrade the defense object
     * @throws NoUpgradeAllowedException when the given parameter is already at it's highest level.
     */
    public boolean upgradeDefense(Defense defense, Effect effect) throws NotEnoughBitcoinsException, NoUpgradeAllowedException{
        update.upgradeModule(sessionKey, account.getUID(), defense);
    }	
    
}
