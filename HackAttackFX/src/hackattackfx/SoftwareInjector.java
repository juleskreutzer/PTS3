/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hack.attack.exceptions.InvalidModuleEnumException;
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
    
    /**
     * Constructor for the SoftwareInjector based on the SoftwareInjectorTemplate
     * @param softwareInjector SoftwareInjectorTemplate that has been created by the data class
     * @param position Position on the map for the module
     * @param width Width for the module
     * @param height Height for the module
     */
    public SoftwareInjector(SoftwareInjectorTemplate softwareInjector, Point position, int width, int height) throws InvalidModuleEnumException
    {
        super(softwareInjector.getCost(), position, width, height, softwareInjector.getModuleName(), softwareInjector.getLevel());
        if( softwareInjector.getModuleName() != ModuleName.SOFTWARE_INJECTOR)
        {
            throw new InvalidModuleEnumException();
        }
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
