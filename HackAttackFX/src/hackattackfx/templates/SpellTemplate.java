/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx.templates;
import hackattackfx.enums.*;

/**
 *
 * @author juleskreutzer
 */
public class SpellTemplate {
    private SpellName name;
    private double range;
    private SpellType type;
    private int cooldown;
    private int requiredLevel;
    private int duration;
    
    public SpellTemplate(SpellName name, double range, SpellType type, int cooldown, int requiredLevel, int duration)
    {
        this.name = name;
        this.range = range;
        this.type = type;
        this.cooldown = cooldown;
        this.requiredLevel = requiredLevel;
        this.duration = duration;
    }
    
    public SpellName getName()
    {
        return this.name;
    }
    
    public double getRange()
    {
        return this.range;
    }
    
    public SpellType getType()
    {
        return this.type;
    }
    
    public int getCooldown()
    {
        return this.cooldown;
    }
    
    public int getRequiredLevel()
    {
        return this.requiredLevel;
    }
    
    public int getDuration()
    {
        return this.duration;
    }
}
