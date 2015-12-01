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
    * @return The newly created {@link SoftwareInjector}
    */  
    public SoftwareInjector buildSoftwareInjector(SoftwareInjectorTemplate injector) throws NotEnoughBitcoinsException{
        update.buildModule(sessionKey, uID, injector);
    }
    
    /**
     * Retrieve a SoftwareInjector object from the modules field and call the Upgrade method from inside the class
     * @return
     */
    public boolean upgradeSoftwareInjector(SoftwareInjector injector) throws NotEnoughBitcoinsException, NoUpgradeAllowedException
    {
        
    }
    
    public List<Spell> getSpells(){        
        
    }
    
    /**
     * Initialize a BitcoinMiner object, lower the player bitcoins and add the object to the modules field
     * @return The newly created {@link BitcoinMiner}
     */
    public BitcoinMiner buildBitcoinMiner(BitcoinMinerTemplate miner) throws NotEnoughBitcoinsException{
        
    }
    
    /**
     * Retrieve a BitcoinMiner object from the modules field, lower the player bitcoins and call the Upgrade method from inside the class
     * @return boolean if the upgrade was successfully executed 
     */

    public boolean upgradeBitcoinMiner(BitcoinMiner miner) throws NotEnoughBitcoinsException{
        
    }
    
    /**
     * Initialize a CpuUpgrade object and add the object to the modules field
     */
    public CPUUpgrade buildCPUUpgrade(CPUUpgradeTemplate cpu) throws NotEnoughBitcoinsException{
       
    }
    
    public boolean upgradeCPUUpgrade(CPUUpgrade cpu) throws NotEnoughBitcoinsException{
    
    }
    
    public Defense buildDefense(DefenseTemplate defense) throws NotEnoughBitcoinsException{
        
    }
    
    public boolean upgradeDefense(Defense defense, Effect effect) throws NotEnoughBitcoinsException{
      
    }	
    
}
