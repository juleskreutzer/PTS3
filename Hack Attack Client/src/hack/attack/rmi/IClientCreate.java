/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.rmi;

import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author juleskreutzer
 */
public interface IClientCreate extends IClient {
    
    /**
     * This method is called by the server and is used to display new minions on the client.
     * 
     * When the server generates a new wave of minions, this method will be called to draw the minions on the client.
     * 
     * The server shouldn't call this method when the list of minions is null, but we can check it for our client safety.
     * @param minions List of minions the client needs to draw
     * @param uID
     */
    public void drawNewMinions(List<Minion> minions, int uID) throws RemoteException;
    
    /**
     * This method is called by the server and is used to display the spells a player has executed on the client.
     * 
     * When a player wants to cast a spell, a request is send to the server. We don't have to check if it is possible for 
     * a player to cast a spell, because that will be done on the server.
     * 
     * The server shouldn't call this method when the spell-list is null, but we can check if the list is null for our client safety.
     * @param effect
     * @param targets
     * @param uID
     */
    public void drawNewSpells(Effect effect, List<ITargetable> targets, int uID) throws RemoteException;
    
    /**
     * 
     */
    public void startGame() throws RemoteException;
    
}
