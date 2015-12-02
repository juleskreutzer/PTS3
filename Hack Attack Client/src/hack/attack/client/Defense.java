/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import hack.attack.client.enums.DefenseType;
import hack.attack.client.enums.Effect;
import hack.attack.client.enums.ModuleName;
import hack.attack.client.exceptions.*;
import hack.attack.client.interfaces.ITargetable;
import java.awt.Point;
import hack.attack.client.templates.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a module used for the defense of your base. 
 * It fires {@link Bullet } at enemy minions.
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class Defense extends Module implements ITargetable {
    private double damage;
    private int range;
    // The amount of attacks per second
    private int frequency;
    private DefenseType type;
    private Effect effect;
    
    private boolean reloading;
    
    private Minion target;
    
    // The last time this module performed an attack
    private long lastAttack;
    
    
    /**
     * Constructor for the Defense module based on the DefenseTemplate
     * @param template instance of DefenseTemplate created in the data class
     * @param position position of the module on the map
     * @param width width of the module
     * @param height height of the module
     * @throws InvalidModuleEnumException when the
     * 
     * Because we have to check if the given ModuleName is correct, we create an array of ModuleName with all the possible values and check if the given ModuleName is correct
     */
    public Defense(DefenseTemplate template, Point position, int width, int height) throws InvalidModuleEnumException
    {
        super(template.getCost(), position, width, height, template.getModuleName(), template.getLevel(), template.getDescription());
        ModuleName ModuleNameList[] = new ModuleName[] {ModuleName.BOTTLECAP_ANTIVIRUS, ModuleName.MUSCLE_ANTIVIRUS, ModuleName.SCALE_ANTIVIRUS, ModuleName.SNIPER_ANTIVIRUS};
        if(!Arrays.asList(ModuleNameList).contains(template.getModuleName()))
        {
            throw new InvalidModuleEnumException();
        }
        this.type = template.getDefenseType();
        this.effect = template.getEffect();
        this.damage = template.getDamage();
        this.range = template.getRange();
        this.frequency = template.getFrequency();
        reloading = false;
        
    }
    
    /**
     * 
     * @return Type of the object as String
     */
    public String getDefenceTypeString()
    {
        String result;
        
        switch(type)
        {
            case RANGE:
                result = "range";
                break;
            case CHEAP:
                result = "cheap";
                break;
            case BALANCED:
                result = "balanced";
                break;
            case STRONG:
                result = "strong";
                break;
            default:
                result = "";
        }
        return result;
    }
    
    /**
     * 
     * @return return the effect as String
     */
    public String getEffectString()
    {
        String result;
        
        if(effect == null){
            return "";
        }
        switch(effect)
        {
            case SLOWED:
                result = "slow";
                break;
            case POISENED:
                result = "poison";
                break;
            case SPLASH:
                result = "splash";
                break;
            case DECRYPTED:
                result = "decryptor";
                break;
            default:
                result = "";
        }
        return result;
    }
    
    public DefenseType getDefenceType(){
        return type;
    }
    
    public Effect getEffect(){
        return effect;
    }
    
    public int getFrequecy(){
        return frequency;
    }
    
    /**
     * Sets the Defense modules effect.
     * @param e
     */
    public void setEffect(Effect e){
        effect = e;
    }
    
    public double getDamage(){
        return damage;
    }
    
    /**
     * Sets the towers damage.
     * @param d, must be above 0.
     */
    public void setDamage(double d){
        if (d > 0)
        {
            damage = d;
        }
    }
    
    public int getRange(){
        return range;
    }
    
    /**
     * Sets the towers RANGE.
     * @param r, must be above 0.
     */
    public void setRange(int r){
        if (range > 0)
        {
            range = r;
        }
    }
    
    public void setTarget(Minion target){
        this.target = target;
    }
    
    public Minion getTarget(){
        return target;
    }
    
    /**
     * Sets the last time this module attacked
     * @param lastAttack The time in milliseconds at the moment this module fired
     */
    public void setLastAttack(long lastAttack){
        this.lastAttack = lastAttack;
    }
    
    /**
     * The last moment this module attacked in milliseconds
     */
    public long getLastAttack(){
        return lastAttack;
    }
    
    /**
     * Upgrades this module
     * @return Whether the module was successfully upgraded.
     */
    public boolean upgrade() throws NoUpgradeAllowedException{
        DefenseTemplate newDefense;
        if(this.level == 1)
        {
            if(this.moduleName == ModuleName.BOTTLECAP_ANTIVIRUS)
            {
                newDefense = Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_2;
                this.type = newDefense.getDefenseType();
                super.setLevel(newDefense.getLevel());
                this.level = newDefense.getLevel();
                this.effect = newDefense.getEffect();
                this.damage = newDefense.getDamage();
                this.range = newDefense.getRange();
                this.frequency = newDefense.getFrequency();
                
                return true;
            }
            else if(this.moduleName == ModuleName.MUSCLE_ANTIVIRUS)
            {
                newDefense = Data.DEFAULT_MODULE_DEFENSE_MUSCLE_2;
                this.type = newDefense.getDefenseType();
                super.setLevel(newDefense.getLevel());
                this.level = newDefense.getLevel();
                this.effect = newDefense.getEffect();
                this.damage = newDefense.getDamage();
                this.range = newDefense.getRange();
                this.frequency = newDefense.getFrequency();
                
                return true;
            }
            else if(this.moduleName == ModuleName.SCALE_ANTIVIRUS)
            {
                newDefense = Data.DEFAULT_MODULE_DEFENSE_SCALE_2;
                this.type = newDefense.getDefenseType();
                super.setLevel(newDefense.getLevel());
                this.level = newDefense.getLevel();
                this.effect = newDefense.getEffect();
                this.damage = newDefense.getDamage();
                this.range = newDefense.getRange();
                this.frequency = newDefense.getFrequency();
                
                return true;
            }
            else if(this.moduleName == ModuleName.SNIPER_ANTIVIRUS)
            {
                newDefense = Data.DEFAULT_MODULE_DEFENSE_SNIPER_2;
                this.type = newDefense.getDefenseType();
                super.setLevel(newDefense.getLevel());
                this.level = newDefense.getLevel();
                this.effect = newDefense.getEffect();
                this.damage = newDefense.getDamage();
                this.range = newDefense.getRange();
                this.frequency = newDefense.getFrequency();
                
                return true;
            }
            else
            {
                return false;
            }
            
        }
        else if(this.level == 2)
        {
            if(this.moduleName == ModuleName.BOTTLECAP_ANTIVIRUS)
            {
                newDefense = Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_3;
                this.type = newDefense.getDefenseType();
                super.setLevel(newDefense.getLevel());
                this.level = newDefense.getLevel();
                this.effect = newDefense.getEffect();
                this.damage = newDefense.getDamage();
                this.range = newDefense.getRange();
                this.frequency = newDefense.getFrequency();
                
                return true;
            }
            else if(this.moduleName == ModuleName.MUSCLE_ANTIVIRUS)
            {
                newDefense = Data.DEFAULT_MODULE_DEFENSE_MUSCLE_3;
                this.type = newDefense.getDefenseType();
                super.setLevel(newDefense.getLevel());
                this.level = newDefense.getLevel();
                this.effect = newDefense.getEffect();
                this.damage = newDefense.getDamage();
                this.range = newDefense.getRange();
                this.frequency = newDefense.getFrequency();
                
                return true;
            }
            else if(this.moduleName == ModuleName.SCALE_ANTIVIRUS)
            {
                newDefense = Data.DEFAULT_MODULE_DEFENSE_SCALE_3;
                this.type = newDefense.getDefenseType();
                super.setLevel(newDefense.getLevel());
                this.level = newDefense.getLevel();
                this.effect = newDefense.getEffect();
                this.damage = newDefense.getDamage();
                this.range = newDefense.getRange();
                this.frequency = newDefense.getFrequency();
                
                return true;
            }
            else if(this.moduleName == ModuleName.SNIPER_ANTIVIRUS)
            {
                newDefense = Data.DEFAULT_MODULE_DEFENSE_SNIPER_3;
                this.type = newDefense.getDefenseType();
                super.setLevel(newDefense.getLevel());
                this.level = newDefense.getLevel();
                this.effect = newDefense.getEffect();
                this.damage = newDefense.getDamage();
                this.range = newDefense.getRange();
                this.frequency = newDefense.getFrequency();
                
                return true;
            }
            else
            {
                return false;
            }
        }
        else if(this.level == 3)
        {
           throw new NoUpgradeAllowedException(String.format("Your %s has already reached it's maximal level. You can't upgrade it anymore.", this.moduleName.toString()));
        }
        else
        {
            throw new NoUpgradeAllowedException("Something went wrong. We can't upgrade your defense module at this time.");
        }
    }
    
    /**
     * Checks if the given minion is within the range of the module
     * @param m The minion to check on
     * @return True if the given minion is in range.
     */
    public boolean targetInRange(Minion m){
        //return true if the square root of range is bigger than (minion and tower variables used) -> Delta X^2 + Delta Y^2 (Pythagoras).
        long distance = (long)Math.sqrt((position.x-m.getPosition().x)*(position.x-m.getPosition().x) + (position.y-m.getPosition().y)*(position.y-m.getPosition().y));
        return range >= distance;
    }
}
