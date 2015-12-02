/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server.interfaces;

import hack.attack.server.*;
import java.util.List;

/**
 *
 * @author juleskreutzer
 */
public interface IClientCreate extends IClient {
    
    /**
     * This method is called by the server and is used to display new modules on the client.
     * 
     * When the client sends a request to the server to build a module, the server will create it and will call this method
     * to notify the client to draw the new modules.
     * 
     * When implementing this method on the client, we don't have to check if the player can build a module, because this has already
     * been done by the server.
     * 
     * The server should only call this method when the module-list isn't null, but for some extra safety we could check
     * the module list on the server to see if it is null.
     * @param modules List of modules the client needs to draw
     * @param uID
     */
    public void drawNewModules(List<Module> modules, int uID);
    
    /**
     * This method is called by the server and is used to display new minions on the client.
     * 
     * When the server generates a new wave of minions, this method will be called to draw the minions on the client.
     * 
     * The server shouldn't call this method when the list of minions is null, but we can check it for our client safety.
     * @param minions List of minions the client needs to draw
     * @param uID
     */
    public void drawNewMinions(List<Minion> minions, int uID);
    
    /**
     * This method is called by the server and is used to display the spells a player has executed on the client.
     * 
     * When a player wants to cast a spell, a request is send to the server. We don't have to check if it is possible for 
     * a player to cast a spell, because that will be done on the server.
     * 
     * The server shouldn't call this method when the spell-list is null, but we can check if the list is null for our client safety.
     * @param spells List of spells the client needs to draw
     * @param uID
     */
    public void drawNewSpells(List<Spell> spells, int uID);
    
    /**
     * 
     */
    public void startGame();
    
    /**
     * 
     */
    public void initialize();
    
}
