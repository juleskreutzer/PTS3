/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.server.enums.*;
import hack.attack.server.exceptions.*;
import hack.attack.server.interfaces.*;
import hack.attack.server.logger.Log;
import java.lang.reflect.AccessibleObject;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juleskreutzer
 */
public class ServerAdapter extends UnicastRemoteObject implements IServerConnect, IServerUpdate{
    private ExecutorService tPool;
    private List<Account> customGameUsers;
    private List<Account> automaticGameUsers;
    private List<Session> sessions;
    
    public ServerAdapter() throws RemoteException
    {
        customGameUsers = new ArrayList<>();
        automaticGameUsers = new ArrayList<>();
        sessions = new ArrayList<>();
            
    }

    @Override
    public HashMap<String, IServerUpdate> hostCustomGame(Account account, HashMap<String, Remote> interfaces) {
        customGameUsers.add(account);
        
        String key = "";
        String sessionKey = "";
        try{
            Date date = new Date();
            key = "HackAttackServer" + date.toString();
            sessionKey = Data.encrypt(key);
        } catch (Exception ex) {
            Log log = new Log(LogState.ERROR, ex.getMessage());
        }
        
        if(account == null)
        {
            Log log = new Log(LogState.ERROR, "Account object is null");
        }
        
        if(interfaces == null || interfaces.size() != 3)
        {
            Log log = new Log(LogState.ERROR, "No or not enough interfaces provided!");
        }
        
        Session session = new Session(sessionKey, interfaces, account);
        sessions.add(session);
        
        
        HashMap hashMap = new HashMap<>();
        hashMap.put(sessionKey, IServerUpdate.class);
        
        return hashMap;
        
    }

    @Override
    public HashMap<String, IServerUpdate> joinCustomGame(Account account, String targetUsername, HashMap<String, Remote> interfaces) {
        int count = 0;
        
        try{
            for(Account a : customGameUsers)
            {
                if(a.getUsername().equals(targetUsername))
                {
                    count = 1;
                }
            }
        
            if(count == 0)
            {
                throw new NoHostAvailableException("No host found with the provided targetUsername");
            }
            
            // At this point, we found a host
            for(Session s : sessions)
            {
                if(s.getPlayerA().getUsername().equals(targetUsername))
                {
                    s.joinSession(account, interfaces);
                    HashMap hashMap = new HashMap<>();
        
                    hashMap.put(s.getSessionKey(), IServerUpdate.class);
        
                    return hashMap;
                }
            }
        } catch (NoHostAvailableException ex) {
            System.out.print(ex.toString());
            Log log = new Log(LogState.ERROR, ex.getMessage());
        }
        return null;
    }

    @Override
    public HashMap<String, IServerUpdate> findMatch(Account account, HashMap<String, Remote> interfaces) {
        int modulo = automaticGameUsers.size() % 2;
        
        if(modulo == 1)
        {
            // We have enough players to start a automatic game
            int score = account.getUserScore();
            Account a = automaticGameUsers.get(0);
            int aScore = a.getUserScore();
            int distance = Math.abs(aScore - score);
            int idx = 0;
            for(int i = 0; i < automaticGameUsers.size(); i++)
            {
                Account tempAccount = (Account) automaticGameUsers.get(i);
                int tempScore = tempAccount.getUserScore();
                int cdistance = Math.abs(tempScore - score);
                if(cdistance < distance){
                    idx = i;
                    distance = cdistance;
                }
            }
            
            Account secondPlayer = (Account) automaticGameUsers.get(idx);
            for(Session session : sessions)
            {
                if(session.getPlayerA().getUsername().equals(secondPlayer.getUsername()))
                {
                    session.joinSession(account, interfaces);
                    HashMap hashMap = new HashMap<>();
                    hashMap.put(session.getSessionKey(), IServerUpdate.class);
                    return hashMap;
                }
            }
        }
        else
        {
            // No player(s) are available, create a new session
            automaticGameUsers.add(account);
            
            String key = "";
            String sessionKey = "";
            try{
                Date date = new Date();
                key = "HackAttackServer" + date.toString();
                sessionKey = Data.encrypt(key);
            } catch (Exception ex) {
                Log log = new Log(LogState.ERROR, ex.getMessage());
            }
            
            if(account == null)
            {
                Log log = new Log(LogState.ERROR, "Account object is null");
            }
        
            if(interfaces == null || interfaces.size() != 3)
            {
                Log log = new Log(LogState.ERROR, "No or not enough interfaces provided!");
            }
            
            Session session = new Session(sessionKey, interfaces, account);
            sessions.add(session);
            
            HashMap hashMap = new HashMap<>();
            hashMap.put(session.getSessionKey(), IServerUpdate.class);
            return hashMap;
        }
        return null;
    }

    /**
     * Build a new module for the player that calls the remote method.
     * 
     * First we check if the given sessionKey belongs to a session stored on the server.
     * When the session is not null, we loop over the ModuleName enum of the module to see what type of module it should be.
     * 
     * When we know that module, for example, is a bitcoin miner, we check if module is an instance of BitcoinMiner.
     * 
     * If all those checks pass, we request the gameEngine from the session, request the player from the gameEngine and than call
     * the build method for the specific module.
     * @param sessionKey Unique key to identify the session on the server
     * @param uID Unique identifier for the user
     * @param module Module that player would like to build
     */
    @Override
    public void buildModule(String sessionKey, int uID, Module module) {
        try{
            Session session = null;
        
            for(Session s : sessions)
            {
                if(s.getSessionKey().equals(sessionKey))
                {
                    session = s;
                }
            }
        
        
            if(session == null)
            {
                throw new InvalidSessionKeyException("Provided sessionKey not found on server");
            }
            
            // At this point, we have the session that we need. Now we have to check which module the player
            // would like to build.
            switch(module.getModuleName())
            {
                case BITCOIN_MINER:
                    if(module instanceof BitcoinMiner)
                    {
                        session.getEngine().getPlayer().buildBitcoinMiner((BitcoinMiner) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("provided object is not a BitcoinMiner object");
                    }
                    break;
                case SOFTWARE_INJECTOR:
                    if(module instanceof SoftwareInjector)
                    {
                        session.getEngine().getPlayer().buildSoftwareInjector((SoftwareInjector) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a SoftwareInjector object");
                    }
                    break;
                case CPU_UPGRADE:
                    if(module instanceof CPUUpgrade)
                    {
                        session.getEngine().getPlayer().buildCPUUpgrade((CPUUpgrade) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a CPUUpgrade object");
                    }
                    break;
                case SNIPER_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer().buildDefense((Defense) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a Defense object");
                    }
                    break;
                case BOTTLECAP_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer().buildDefense((Defense) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a Defense object");
                    }
                    break;
                case SCALE_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer().buildDefense((Defense) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a Defense object");
                    }
                    break;
                case MUSCLE_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer().buildDefense((Defense) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a Defense object");
                    }
                    break;
                default:
                    // The given module type is not found
                    throw new InvalidModuleEnumException("ModuleName not recognized");
                    
                
            }
        }
        catch(InvalidSessionKeyException | InvalidModuleEnumException | InvalidObjectException ex)
        {
            Log log = new Log(LogState.ERROR, ex.getMessage());
        }
        catch(NotEnoughBitcoinsException ex)
        {
            Log log = new Log(LogState.WARNING, ex.getMessage());
        }
    }

    
    @Override
    public void executeSpell(String sessionKey, int uID, Spell spell) {
        try{
            Session session = null;
            for(Session s : sessions)
            {
                if(s.getSessionKey().equals(sessionKey))
                {
                    session = s;
                }
            }
            
            if(session == null)
            {
                throw new InvalidSessionKeyException("The provided sessionKey doesn't belong to any session on the server.");
            }
            
            if(spell == null)
            {
                throw new InvalidObjectException("Spell object cannot be null");
            }
            
            switch(spell.getName())
            {
                case CORRUPT:
                    throw new IllegalArgumentException("IServerUpdate executeSpell method isn't implemented correctly");
                case ENCRYPT:
                    throw new IllegalArgumentException("IServerUpdate executeSpell method isn't implemented correctly");
                case DISRUPT:
                    throw new IllegalArgumentException("IServerUpdate executeSpell method isn't implemented correctly");
                case FIREWALL:
                    throw new IllegalArgumentException("IServerUpdate executeSpell method isn't implemented correctly");
                case LOCKDOWN:
                    throw new IllegalArgumentException("IServerUpdate executeSpell method isn't implemented correctly");
                case VIRUSSCAN:
                    throw new IllegalArgumentException("IServerUpdate executeSpell method isn't implemented correctly");
                default:
                    throw new InvalidObjectException("SpellName not recognized.");
            }
                
            
        }
        catch(InvalidSessionKeyException | InvalidObjectException ex)
        {
            Log log = new Log(LogState.ERROR, ex.getMessage());
        }
        catch(IllegalArgumentException ex)
        {
            Log log = new Log(LogState.WARNING, ex.getMessage());
        }
    }

    @Override
    public void upgradeModule(String sessionKey, int uID, Module module) {
        try{
            Session session = null;
            for(Session s : sessions)
            {
                if(s.getSessionKey().equals(sessionKey))
                {
                    session = s;
                }
            }
            
            if(session == null)
            {
                throw new InvalidSessionKeyException("Provided session key not found on the server");
            }
            
            switch(module.getModuleName())
            {
                case BITCOIN_MINER:
                    if(module instanceof BitcoinMiner)
                    {
                        session.getEngine().getPlayer().upgradeBitcoinMiner((BitcoinMiner) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Gived module is not an instance of BitcoinMiner");
                    }
                    break;
                case CPU_UPGRADE:
                    if(module instanceof CPUUpgrade)
                    {
                        session.getEngine().getPlayer().upgradeCPUUpgrade((CPUUpgrade) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of CPUUpgrade");
                    }
                    break;
                case SOFTWARE_INJECTOR:
                    if(module instanceof SoftwareInjector)
                    {
                        session.getEngine().getPlayer().upgradeSoftwareInjector((SoftwareInjector) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of SoftwareInjector");
                    }
                    break;
                case SNIPER_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer().upgradeDefense((Defense) module, null);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of Defense");
                    }
                    break;
                case SCALE_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer().upgradeDefense((Defense) module, null);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of Defense");
                    }
                    break;
                case MUSCLE_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer().upgradeDefense((Defense) module, null);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of Defense");
                    }
                    break;
                case BOTTLECAP_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer().upgradeDefense((Defense) module, null);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of Defense");
                    }
                    break;
                default:
                    throw new InvalidObjectException("ModuleName not recognized");
            }
        }
        catch(InvalidSessionKeyException | InvalidObjectException ex)
        {
            Log log = new Log(LogState.ERROR, ex.getMessage());
        }
        catch(NotEnoughBitcoinsException | NoUpgradeAllowedException ex)
        {
            Log log = new Log(LogState.WARNING, ex.getMessage());
        }
    }
}
