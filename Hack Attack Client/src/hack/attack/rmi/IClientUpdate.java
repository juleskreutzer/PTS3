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
public interface IClientUpdate extends IClient {
    
    /**
     * 
     * @param modules 
     * @param uID 
     */
    public void redrawCurrentModules(List<Module> modules, int uID) throws RemoteException;
    
    /**
     * 
     * @param minions 
     * @param uID 
     */
    public void redrawCurrentMinions(List<Minion> minions, int uID) throws RemoteException;
    
    /**
     * 
     * @param spells
     * @param uID
     */
    public void redrawCurrentSpells(List<Spell> spells, int uID) throws RemoteException;
    
    /**
     * This method should be called when an event occurs at the server that has influence on the player's health or bitcoins.
     * 
     * This method is used to update the labels in the top of the screen on the client. These labels are:
     * <ul>
     *  <li>Wave number</li>
     *  <li>Player name</li>
     *  <li>Player health</li>
     *  <li>Player bitcoins amount</li>
     * </ul>
     * 
     * The wave number is the same for both players, so we send that only once. For the other labels, we send a player object so we can
     * update each player instance and update the labels from these instances.
     * @param playerA Instance of player class with fields like displayname, health, bitcoins
     * @param playerB Instance of player class with fields like displayname, health, bitcoins
     * @param waveNumber Number indicating the current wave
     */
    public void updateLabels(int waveNumber, String playernamea, double healthplayera, double bitcoinsplayera, String playernameb, double healthplayerb) throws RemoteException;
    
    public void fire(int moduleid, long minionid) throws RemoteException;
}
