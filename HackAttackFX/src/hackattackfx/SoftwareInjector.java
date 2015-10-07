/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.ModuleName;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import hackattackfx.exceptions.*;
import hackattackfx.templates.SoftwareInjectorTemplate;

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
    public SoftwareInjector(SoftwareInjectorTemplate softwareInjector, Point position, int width, int height)
    {
        super(softwareInjector.getCost(), position, width, height, softwareInjector.getModuleName(), softwareInjector.getLevel());
        
    }
    
    public boolean upgrade(){
        if(super.getLevel() < 3)
        {
            throw new UnsupportedOperationException("Software Upgrade method not yet implemented");
        }
        return false;
    }
    
    public List<Spell> getSpells(){
        return spells;
    }
}
