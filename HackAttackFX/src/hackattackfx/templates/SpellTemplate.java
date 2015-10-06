/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx.templates;

/**
 *
 * @author juleskreutzer
 */
public class SpellTemplate {
    private String name;
    private double range;
    private String type;
    private int cooldown;
    private int requiredLevel;
    
    public SpellTemplate(String name, double range, String type, int cooldown, int requiredLevel)
    {
        this.name = name;
        this.range = range;
        this.type = type;
        this.cooldown = cooldown;
        this.requiredLevel = requiredLevel;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public double getRange()
    {
        return this.range;
    }
    
    public String getType()
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
}
