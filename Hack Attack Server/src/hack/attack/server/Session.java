/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.rmi.Account;
import hack.attack.rmi.IClient;
import hack.attack.server.enums.LogState;
import hack.attack.server.logger.Log;
import java.rmi.Remote;
import java.util.HashMap;

/**
 *
 * @author juleskreutzer
 */
public class Session {
    private String sessionKey;
    private Account playerA;
    private Account playerB;
    private final HashMap<String, IClient> interfacesA;
    private HashMap<String, IClient> interfacesB;
    
    private GameEngine engine;
    
    public Session(String key, HashMap<String, IClient> interfaces, Account PlayerA)
    {
        this.sessionKey = key;
        this.playerA = PlayerA;
        interfacesA = interfaces;
    }
    
    public GameEngine getEngine()
    {
        if(engine == null){
            engine = new GameEngine(this, interfacesA, interfacesB);
        }
        return engine;
    }
    
    public Account getPlayerA()
    {
        return this.playerA;
    }
    
    public Account getPlayerB()
    {
        return this.playerB;
    }
    
    public void joinSession(Account account, HashMap<String, IClient> interfaces)
    {
        this.playerB = account;
        interfacesB = interfaces;
    }
    
    public String getSessionKey()
    {
        return this.sessionKey;
    }
}
