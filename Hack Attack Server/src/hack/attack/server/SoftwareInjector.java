/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.rmi.Spell;
import hack.attack.rmi.Module;
import hack.attack.rmi.ModuleName;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import hack.attack.server.exceptions.*;
import hack.attack.server.templates.SoftwareInjectorTemplate;
import hack.attack.rmi.SpellTemplate;

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
        super(softwareInjector.getCost(), position, width, height, softwareInjector.getModuleName(), softwareInjector.getLevel(), softwareInjector.getDescription());
        if( softwareInjector.getModuleName() != ModuleName.SOFTWARE_INJECTOR)
        {
            throw new InvalidModuleEnumException();
        }
        spells = new ArrayList<>();
    }
    
    /**
     * Upgrade the current Software Injector to the next level and new Spells will become available. 
     * Because we have a total of 6 spells, each upgrade will unlock 3 spells. These spells are created using the template for them in the Data class.
     * The spells that become available are hard coded into the upgrade method. It can be changed.
     * @return Returns true if upgrade is successfull, false if not
     * @throws NoUpgradeAllowedException when the current Software Injector is level 3 or when something weird happened.
     */
    public boolean upgrade() throws NoUpgradeAllowedException{
        SoftwareInjectorTemplate sit;
        if(this.level == 1)
        {
            sit = Data.DEFAULT_MODULE_SOFTWAREINJECTOR_2;
            this.cost = sit.getCost();
            this.level = sit.getLevel();
            this.moduleName = sit.getModuleName();
            
            // At the first upgrade, make 2 defense and 1 attack spell available
           
            spells.add(new Spell(Data.DEFAULT_SPELL_CORRUPT));
            spells.add(new Spell(Data.DEFAULT_SPELL_LOCKDOWN));
            spells.add(new Spell(Data.DEFAULT_SPELL_FIREWALL));

            return true;
        }
        else if(this.level == 2)
        {
            sit = Data.DEFAULT_MODULE_SOFTWAREINJECTOR_3;
            this.cost = sit.getCost();
            this.level = sit.getLevel();
            this.moduleName = sit.getModuleName();
            
            // At the second upgrade, make the remaining spells available (1 defense & 2 attack)
            spells.add(new Spell(Data.DEFAULT_SPELL_DISRUPT));
            spells.add(new Spell(Data.DEFAULT_SPELL_ENCRYPT));
            spells.add(new Spell(Data.DEFAULT_SPELL_VIRUSSCAN));
            
            return true;
        }
        else if(this.level == 3)
        {
            throw new NoUpgradeAllowedException("Your Software Injector already reached it's maximal level.");
        }
        else
        {
            throw new NoUpgradeAllowedException("Something went wrong when trying to upgrade your Software Injector. Please try again.");
        }
    }
    
    /**
     * Get a list of all spells available at the current Software Injector level
     * @return A list<Spell> of spells available at the current Software Injector level
     */
    public List<Spell> getSpells(){
        return spells;
    }
}
