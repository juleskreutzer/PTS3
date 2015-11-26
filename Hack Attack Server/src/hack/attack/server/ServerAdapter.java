/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.server.enums.LogState;
import hack.attack.server.exceptions.NoHostAvailableException;
import hack.attack.server.interfaces.IServerConnect;
import hack.attack.server.interfaces.IServerUpdate;
import hack.attack.server.logger.Log;
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
    
    public ServerAdapter() throws RemoteException
    {
        customGameUsers = new ArrayList<>();
        automaticGameUsers = new ArrayList<>();
        sessions = new ArrayList<>();
    }

    @Override
    public HashMap<String, IServerUpdate> hostCustomGame(Account account, Object[] interfaces) {
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
        
        if(interfaces == null || interfaces.length != 3)
        {
            Log log = new Log(LogState.ERROR, "No or not enough interfaces provided!");
        }
        
        Session session = new Session(sessionKey, interfaces);
        session.setPlayerA(account);
        sessions.add(session);
        
        
        HashMap hashMap = new HashMap<>();
        
        hashMap.put(sessionKey, IServerUpdate.class);
        
        return hashMap;
        
    }

    @Override
    public HashMap<String, IServerUpdate> joinCustomGame(Account account, String targetUsername, Object[] interfaces) {
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
                    s.setPlayerB(account);
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
    public HashMap<String, IServerUpdate> findMatch(Account account, Object[] interfaces) {
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
                    session.setPlayerB(account);
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
        
            if(interfaces == null || interfaces.length != 3)
            {
                Log log = new Log(LogState.ERROR, "No or not enough interfaces provided!");
            }
            
            Session session = new Session(sessionKey, interfaces);
            sessions.add(session);
            
            HashMap hashMap = new HashMap<>();
            hashMap.put(session.getSessionKey(), IServerUpdate.class);
            return hashMap;
        }
        return null;
    }

    @Override
    public void buildModule(String sessionKey, int uID, Module module) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void executeSpell(String sessionKey, int uID, Spell spell) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void upgradeModule(String sessionKey, int uID, Module module) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
