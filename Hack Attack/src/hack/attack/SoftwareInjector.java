/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import hack.attack.enums.ModuleName;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import hack.attack.exceptions.*;

/**
 *
 * @author juleskreutzer
 */
public class SoftwareInjector extends Module {
    
    private List<Spell> spells;
    private ArrayList<Spell> spellList;
    
    public SoftwareInjector(double cost, Point position, int width, int height, int level, ModuleName name)
    {
        super(cost, position, width, height, name, level);
        
    }
    
    public boolean upgrade(){
        return false;
    }
    
    public List<Spell> getSpells(){
        return spells;
    }
}
