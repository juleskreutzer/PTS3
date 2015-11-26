/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server.interfaces;

import hack.attack.server.Account;
import java.util.HashMap;

/**
 *
 * @author juleskreutzer
 */
public interface IServerConnect {
    
    /**
     * When a user wants to host a custom match, this interface method will be called. 
     * 
     * The method needs some values that are required for the server. It will then return a hashMap with a string and IServerUpdate object.
     * @param account Instance of an Account class where uID, username, displayName and userScore is stored in
     * @param interfaces Array of interface that can be used by the server to call the client. Normally those interface are
     * IClientCreate, IClientUpdate and IClientDelete
     * @return Returns a hashMap<String, IServerUpdate> The string will be the sessionKey and IServerUpdate will be an interface the client 
     * can use to communicate with the server while in-game
     * 
     */
    public HashMap<String, IServerUpdate> hostCustomGame(Account account, Object[] interfaces);
    
    /**
     * When a user wants to join a custom match, this interface method needs to be called.
     * 
     * When this method is called, we assume that another player already hosts a custom match to join.
     * 
     * The method needs some values that are required for the server. It will then return a hashMap with a string and IServerUpdate object.
     * @param account Instance of an Account class where uID, username, displayName and userScore is stored in
     * @param targetUsername the username from the player that is the host for the custom Match
     * @param interfaces Array of interfaces that can be used by the server to call the client. Normally those interfaces are
     * IClientCreate, IClientUpdate and IClientDelete
     * @return Returns a hashMap<String, IServerUpdate> The string will be the sessionKey and IServerUpdate will be an interface the client
     * can use to communicate with the server while in-game
     */
    public HashMap<String, IServerUpdate> joinCustomGame(Account account, String targetUsername, Object[] interfaces);
    
    /**
     * When a user wants to start or join a automatic match, this interface method needs to be called.
     * 
     * 
     * @param interfaces array of interfaces used to by the server to communicate with the client. Normally those interfaces are 
     * IClientCreate, IClientUpdate and IClientDelete
     * @return Returns a hashMap<String, IServerUpdate> The string will be the sessionKey and IServerUpdate will be an interface the client 
     * can use to communicate with the server while in-game
     */
    public HashMap<String, IServerUpdate> findMatch(Account account, Object[] interfaces);
}
