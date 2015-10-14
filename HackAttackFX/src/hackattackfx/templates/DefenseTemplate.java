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
public class DefenseTemplate extends ModuleTemplate {
    private double damage;
    private int range;
    private DefenseType defenseType;
    private Effect effect;
    private int frequency;
    
    
    public DefenseTemplate(double cost, int level, ModuleName moduleName, double damage, int range, DefenseType type, Effect effect, int frequency ) {
        super(cost, level, moduleName);
        this.damage = damage;
        this.range = range;
        this.defenseType = type;
        this.effect = effect;
        this.frequency = frequency;
    }
    
    public double getDamage()
    {
        return this.damage;
    }
    
    public int getRange()
    {
        return this.range;
    }
    
    public DefenseType getDefenseType()
    {
        return this.defenseType;
    }
    
    public Effect getEffect()
    {
        return this.effect;
    }
    
    public int getFrequency()
    {
        return this.frequency;
    }
}
