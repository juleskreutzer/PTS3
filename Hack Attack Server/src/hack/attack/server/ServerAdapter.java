/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.rmi.Defense;
import hack.attack.rmi.DefenseTemplate;
import hack.attack.rmi.ModuleTemplate;
import hack.attack.rmi.SpellTemplate;
import hack.attack.rmi.Account;
import hack.attack.rmi.Spell;
import hack.attack.rmi.Module;
import hack.attack.rmi.IServerConnect;
import hack.attack.rmi.IServerUpdate;
import hack.attack.rmi.IClient;
import hack.attack.rmi.IClientCreate;
import hack.attack.server.enums.*;
import hack.attack.server.exceptions.*;
import hack.attack.server.logger.Log;
import hack.attack.server.templates.*;
import java.awt.Point;
import java.rmi.RemoteException;
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
    
    private static ServerAdapter instance;
    
    private ServerAdapter() throws RemoteException
    {
        instance = this;
        customGameUsers = new ArrayList<>();
        automaticGameUsers = new ArrayList<>();
        sessions = new ArrayList<>();
            
    }
    
    public static ServerAdapter getInstance(){
        try {
            return instance == null ? new ServerAdapter() : instance;
        } catch (RemoteException ex) {
            Logger.getLogger(ServerAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Use this method when client is done with all the loading(including GUI). When both players are fully loaded, the game will be started.
     * @param sessionkey Session key of the match
     * @param account The player to set ready
     * @return Whether this method executed succesfully
     */
    @Override
    public boolean ready(String sessionkey, Account account){
        for(Session session : sessions){
            if(session.getSessionKey().equals(sessionkey)){
                session.setPlayerReady(account);
                
                if(session.isPlayerReady(session.getPlayerA()) && session.isPlayerReady(session.getPlayerB())){
                    session.getEngine().startGame();
                    return true;
                }
                return true;
            }
        }
        HackAttackServer.writeConsole(new Log(LogState.ERROR, "Could not start session.. sessionkey not found"));
        return false;
    }

    @Override
    public HashMap<String, IServerUpdate> hostCustomGame(Account account, HashMap<String, IClient> interfaces) {
        customGameUsers.add(account);
        
        String key = "";
        String sessionKey = "";
        try{
            Date date = new Date();
            key = "HackAttackServer" + date.toString();
            sessionKey = Data.encrypt(key);
        } catch (Exception ex) {
            HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
        }
        
        if(account == null)
        {
            HackAttackServer.writeConsole(new Log(LogState.ERROR, "Account object is null"));
        }
        
        if(interfaces == null || interfaces.size() != 3)
        {
            HackAttackServer.writeConsole(new Log(LogState.ERROR, "No or not enough interfaces provided!"));
        }
        
        Session session = new Session(sessionKey, interfaces, account);
        sessions.add(session);
        
        
        HashMap hashMap = new HashMap<>();
        hashMap.put(sessionKey, this);
        
        return hashMap;
        
    }

    @Override
    public HashMap<String, IServerUpdate> joinCustomGame(Account account, String targetUsername, HashMap<String, IClient> interfaces) {
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
        
                    hashMap.put(s.getSessionKey(), this);
        
                    return hashMap;
                }
            }
        } catch (NoHostAvailableException ex) {
            HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
        }
        return null;
    }

    @Override
    public HashMap<String, IServerUpdate> findMatch(Account account, HashMap<String, IClient> interfaces) {
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
                    hashMap.put(session.getSessionKey(), this);
                    
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
                HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
            }
            
            if(account == null)
            {
                HackAttackServer.writeConsole(new Log(LogState.ERROR, "Account object is null"));
            }
        
            if(interfaces == null || interfaces.size() != 3)
            {
                HackAttackServer.writeConsole(new Log(LogState.ERROR, "No or not enough interfaces provided!"));
            }
            
            Session session = new Session(sessionKey, interfaces, account);
            sessions.add(session);
            
            HashMap hashMap = new HashMap<>();
            hashMap.put(session.getSessionKey(), this);
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
     * @return 
     */
    @Override
    public Module buildModule(String sessionKey, int uID, ModuleTemplate module, Point position, int width, int height) {
        try{
            Session session = null;
        
            for(Session s : sessions)
            {
                if(s.getSessionKey().equals(sessionKey))
                {
                    session = s;
                    break;
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
                    if(module instanceof BitCoinMinerTemplate)
                    {
                        BitcoinMiner miner = new BitcoinMiner((BitCoinMinerTemplate)module, position, width, height);
                        return session.getEngine().getPlayer(uID).buildBitcoinMiner(miner);
                    }
                    else
                    {
                        throw new InvalidObjectException("provided object is not a BitcoinMiner object");
                    }
                case SOFTWARE_INJECTOR:
                    if(module instanceof SoftwareInjectorTemplate)
                    {
                        return session.getEngine().getPlayer(uID).buildSoftwareInjector((SoftwareInjectorTemplate)module, position, width, height);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a SoftwareInjector object");
                    }
                case CPU_UPGRADE:
                    if(module instanceof CPUUpgradeTemplate)
                    {
                        CPUUpgrade cpu = new CPUUpgrade((CPUUpgradeTemplate) module, position, width, height);
                        return session.getEngine().getPlayer(uID).buildCPUUpgrade(cpu);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a CPUUpgrade object");
                    }
                case SNIPER_ANTIVIRUS:
                    if(module instanceof DefenseTemplate)
                    {
                        Defense defense = new Defense((DefenseTemplate) module, position, width, height);
                        return session.getEngine().getPlayer(uID).buildDefense(defense);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a Defense object");
                    }
                case BOTTLECAP_ANTIVIRUS:
                    if(module instanceof DefenseTemplate)
                    {
                        Defense defense = new Defense((DefenseTemplate) module, position, width, height);
                        IClientCreate create;
                        if(uID == session.getPlayerA().getUID()){
                            create = (IClientCreate)session.getInterfacesB().get("create");
                        }else{
                            create = (IClientCreate)session.getInterfacesA().get("create");
                        }
                        List<Module> list = new ArrayList<>();
                        list.add(defense);
                        create.drawNewModules(list, uID);
                        return session.getEngine().getPlayer(uID).buildDefense(defense);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a Defense object");
                    }
                case SCALE_ANTIVIRUS:
                    if(module instanceof DefenseTemplate)
                    {
                        Defense defense = new Defense((DefenseTemplate) module, position, width, height);
                        return session.getEngine().getPlayer(uID).buildDefense(defense);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a Defense object");
                    }
                case MUSCLE_ANTIVIRUS:
                    if(module instanceof DefenseTemplate)
                    {
                        Defense defense = new Defense((DefenseTemplate) module, position, width, height);
                        return session.getEngine().getPlayer(uID).buildDefense(defense);
                    }
                    else
                    {
                        throw new InvalidObjectException("Provided object is not a Defense object");
                    }
                default:
                    // The given module type is not found
                    throw new InvalidModuleEnumException("ModuleName not recognized");
                    
                
            }
        }
        catch(InvalidSessionKeyException | InvalidModuleEnumException | InvalidObjectException  ex)
        {
            HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
        } catch (NotEnoughBitcoinsException ex) {
            HackAttackServer.writeConsole(new Log(LogState.WARNING, ex.getMessage()));
        } catch (RemoteException ex) {
            Logger.getLogger(ServerAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    
    @Override
    public Spell executeSpell(String sessionKey, int uID, SpellTemplate spell, Point position) {
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
                    session.getEngine().executeSpell(new Spell(spell), position, uID);
                    break;
                case ENCRYPT:
                    session.getEngine().executeSpell(new Spell(spell), position, uID);
                    break;
                case DISRUPT:
                    session.getEngine().executeSpell(new Spell(spell), position, uID);
                    break;
                case FIREWALL:
                    session.getEngine().executeSpell(new Spell(spell), position, uID);
                    break;
                case LOCKDOWN:
                    session.getEngine().executeSpell(new Spell(spell), position, uID);
                    break;
                case VIRUSSCAN:
                    session.getEngine().executeSpell(new Spell(spell), position, uID);
                    break;
                default:
                    throw new InvalidObjectException("SpellName not recognized.");
            }
                
            
        }
        catch(InvalidSessionKeyException | InvalidObjectException | RemoteException ex)
        {
            HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
        }
        catch(IllegalArgumentException ex)
        {
            HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
        }
        
        return null;
    }

    @Override
    public boolean upgradeModule(String sessionKey, int uID, Module module) {
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
                        session.getEngine().getPlayer(uID).upgradeBitcoinMiner((BitcoinMiner) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Gived module is not an instance of BitcoinMiner");
                    }
                    break;
                case CPU_UPGRADE:
                    if(module instanceof CPUUpgrade)
                    {
                        session.getEngine().getPlayer(uID).upgradeCPUUpgrade((CPUUpgrade) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of CPUUpgrade");
                    }
                    break;
                case SOFTWARE_INJECTOR:
                    if(module instanceof SoftwareInjector)
                    {
                        session.getEngine().getPlayer(uID).upgradeSoftwareInjector((SoftwareInjector) module);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of SoftwareInjector");
                    }
                    break;
                case SNIPER_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer(uID).upgradeDefense((Defense) module, null);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of Defense");
                    }
                    break;
                case SCALE_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer(uID).upgradeDefense((Defense) module, null);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of Defense");
                    }
                    break;
                case MUSCLE_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer(uID).upgradeDefense((Defense) module, null);
                    }
                    else
                    {
                        throw new InvalidObjectException("Given module is not an instance of Defense");
                    }
                    break;
                case BOTTLECAP_ANTIVIRUS:
                    if(module instanceof Defense)
                    {
                        session.getEngine().getPlayer(uID).upgradeDefense((Defense) module, null);
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
            HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.getMessage()));
        }
        catch(NotEnoughBitcoinsException ex)
        {
            HackAttackServer.writeConsole(new Log(LogState.WARNING, ex.getMessage()));
        }
        
        return false;
    }
}
