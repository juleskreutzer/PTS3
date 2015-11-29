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
public interface IClientDelete {
    
    public void deleteCurrentModules(List<Module> modules);
    
    public void deleteCurrentMinions(List<Minion> minions);
    
    public void deleteCurrentSpells(List<Spell> spells);
    
}
