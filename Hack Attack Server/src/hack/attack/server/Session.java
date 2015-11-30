/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.server.enums.LogState;
import hack.attack.server.interfaces.*;
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
    private GameEngine engine;
    
    public Session(String key, HashMap<String, Remote> interfaces, Account PlayerA)
    {
        this.sessionKey = key;
        this.playerA = PlayerA;
    }
    
    public GameEngine getEngine()
    {
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
    
    public void joinSession(Account account, HashMap<String, Remote> interfaces)
    {
        this.playerB = account;
    }
    
    public String getSessionKey()
    {
        return this.sessionKey;
    }
}
