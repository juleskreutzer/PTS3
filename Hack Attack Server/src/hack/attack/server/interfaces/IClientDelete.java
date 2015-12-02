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
public interface IClientDelete extends IClient {
    
    /**
     * The server will call this method to delete modules a client has drawn.
     * 
     * The server will send a list of module instances that will have a reference to the instances of a module class
     * on the client. We have to check for each item in the list if it is available on the client for extra safety.
     * @param modules List of modules that need to be deleted from the client
     */
    public void deleteCurrentModules(List<Module> modules);
    
    /**
     * The server will call this method to delete minions a client has drawn.
     * 
     * Normally the server will call this method with a list of minions that have died. We have to check each item in the list
     * to make sure it is available on the client and if not we have to handle the the exception so the user doesn't lose any experience.
     * @param minions List of minions that need to be deleted from the client
     */
    public void deleteCurrentMinions(List<Minion> minions);
    
    /**
     * The server will call this method to delete spells a client has drawn.
     * 
     * This method will usually be called when a spell is over. We still have to check if the spell object exists on the client and
     * handle the exception that could be thrown so the user doesn't lose any experience in player Hack Attack.
     * @param spells List of spells the need to be deleted from the client
     */
    public void deleteCurrentSpells(List<Spell> spells);
    
}
