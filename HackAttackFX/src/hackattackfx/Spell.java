/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;
import hackattackfx.enums.*;
import hackattackfx.exceptions.*;
import hackattackfx.templates.SpellTemplate;

/**
 *
 * @author juleskreutzer
 */
public class Spell {
    
    private SpellName name;
    private SpellType type;
    private double range;
    private int cooldown;
    private int requiredLevel;
    
    /**
     * Create a new Spell instance based on the template in the data class
     * @param template Instance of the spell base on the template in the data class
     */
    public Spell(SpellTemplate template){
        this.name = template.getName();
        this.type = template.getType();
        this.range = template.getRange();
        this.cooldown = template.getCooldown();
        this.requiredLevel = template.getRequiredLevel();
    }
    
    /**
     * Get the name of the spell
     * @return Returns the name of the spell based on the SpellName enum
     */
    public SpellName getName(){
        return name;
    }
    
    /**
     * Get the cooldown of the spell object
     * @return Returns the cooldown in an int value
     */
    public int getCooldown(){
        return cooldown;
    }
    
    /**
     * Get the required level to use the spell
     * @return Return the level required to use the spell (Int)
     */
    public int getRequiredLevel(){
        return requiredLevel;
    }
    
    public double getRange(){
        return range;
    }
    
    public SpellType getSpellType(){
        return type;
    }
}
