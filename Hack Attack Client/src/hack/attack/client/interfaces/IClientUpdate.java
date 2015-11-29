/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client.interfaces;

import hack.attack.client.*;
import java.util.List;

/**
 *
 * @author juleskreutzer
 */
public interface IClientUpdate {
    
    public void redrawCurrentModules(List<Module> modules);
    
    public void redrawCurrentMinions(List<Minion> minions);
    
    public void redrawCurrentSpells(List<Spell> minions);
    
    public void updateLabels(Player playerA, Player playerB);
}
