/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client.interfaces;

import hack.attack.client.*;
import hack.attack.client.templates.ModuleTemplate;
import java.awt.Point;
import java.rmi.Remote;

/**
 *
 * @author juleskreutzer
 */
public interface IServerUpdate extends Remote {
    
    /**
     * The client can call this method to create a new module.
     * 
     * The client can check if the position is available, but we want to check if the client has enough bitcoins on the server.
     * This because the bitcoins amount could be different on the server than on the client for a small amount of time when the client 
     * has a slow internet connection.
     * 
     * This method will not have a return type because:
     *  - When a module can be build, it will be added to the player's module list in the Player Class and will be send to the clien with another interface
     *  - When it is not possible to build a module, an exception will be thrown. This will be send to the client by another interface
     * 
     * @param sessionKey a unique key to identify the session on the server, received after the 'hand shake'
     * @param uID unique ID of the user, received after the login call to the API on the client
     * @param module The module that the client wants to build
     */
    public Module buildModule(String sessionKey, int uID, ModuleTemplate module, Point position, int width, int height);
    
    /**
     * The client can call this method to execute a spell.
     * 
     * The client can check if the position is available, but we want to check if the client has enough bitcoins on the server.
     * This because the bitcoins amount could be different on the server than on the client for a small amount of time when the client 
     * has a slow internet connection.
     * 
     * This method will not have a return type because:
     *  - When a spell can be build, it will be added to the player's active spell list in the Player Class and will be send to the clien with another interface
     *  - When it is not possible to execute a spell, an exception will be thrown. This will be send to the client by another interface
     * 
     * @param sessionKey a unique key to identify the session on the server, received after the 'hand shake'
     * @param uID unique ID of the user, received after the login call to the API on the client
     * @param spell The spell that the client wants to execute
     */
    public Spell executeSpell(String sessionKey, int uID, Spell spell);
    
    /**
     * The client can call this method to upgrade a module.
     * 
     * We want to check if the player has enough bitcoins on the server because the amount could be different on the server than on the client for a small 
     * amount of time when the client has a slow internet connection.
     * 
     * This method will not have a return type because:
     *  - When a module can be upgraded, it will be done in the player's module list in the Player class and will be send to the client by another interface
     *  - When it is not possible to upgrade a module, an exception will be thrown. This will be send to the client by another interface
     * @param sessionKey a unique key to identify the session on the server, received after the 'hand shake'
     * @param uID unique ID of the user, received after the login call to the API on the client
     * @param module Module the client wants to upgrade
     */
    public boolean upgradeModule(String sessionKey, int uID, Module module);
}
