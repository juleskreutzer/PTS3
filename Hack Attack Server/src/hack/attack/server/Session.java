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
    
    public Session(String key, IClientCreate create, IClientUpdate update, IClientDelete delete)
    {
        this.sessionKey = key;
        engine = GameEngine.getInstance();
        
        if(create == null)
        {
            Log log = new Log(LogState.ERROR, "IClientCreate not provided!");
        }
        
        if(update == null)
        {
            Log log = new Log(LogState.ERROR, "IClientUpdate not provided!");
        }
        
        if(delete == null)
        {
            Log log = new Log(LogState.ERROR, "IClientDelete not provided!");
        }
        
        // Create an object array to send to startGame
        Object[] interfaces = {create, update, delete};
        
        engine.startGame(interfaces);
        
    }
    
    public GameEngine getEngine()
    {
        return engine;
    }
}
