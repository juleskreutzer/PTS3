/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.server.enums.LogState;
import hack.attack.server.interfaces.*;
import hack.attack.server.logger.Log;

/**
 *
 * @author juleskreutzer
 */
public class Session {
    private String sessionKey;
    private Account playerA;
    private Account playerB;
    private GameEngine engine;
    
    public Session(String key, Object[] interfaces)
    {
        this.sessionKey = key;
        engine = GameEngine.getInstance();
        
        engine.startGame(interfaces);
        
    }
    
    public GameEngine getEngine()
    {
        return engine;
    }
    
    public Account getPlayerA()
    {
        return this.playerA;
    }
    
    public void setPlayerA(Account playerA)
    {
        this.playerA = playerA;
    }
    
    public Account getPlayerB()
    {
        return this.playerB;
    }
    
    public void setPlayerB(Account playerB)
    {
        this.playerB = playerB;
    }
    
    public String getSessionKey()
    {
        return this.sessionKey;
    }
}
