/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import hack.attack.enums.DefenseType;
import hack.attack.enums.ModuleName;
import java.awt.Point;

/**
 *
 * @author juleskreutzer
 */
public class Defense extends Module {
    private double damage;
    private int range;
    private DefenseType type;
    private DefenseEffect effect;
    
    public Defense(double cost, Point position, ModuleName name, DefenseType type, DefenseEffect effect, int level, double damage, int range)
    {
        super(cost, position, name, level);
        
        this.type = type;
        this.effect = effect;
        this.damage = damage;
        this.range = range;
    }
    
    
    
    
    
    
    /**
     * 
     * @return Type of the object as String
     */
    private String getType()
    {
        String result;
        
        switch(type)
        {
            case range:
                result = "range";
                break;
            case cheap:
                result = "cheap";
                break;
            case balanced:
                result = "balanced";
                break;
            case strong:
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
    private String getEffect()
    {
        String result;
        
        switch(effect)
        {
            case slow:
                result = "slow";
                break;
            case poison:
                result = "poison";
                break;
            case slash:
                result = "slash";
                break;
            case decryptor:
                result = "decryptor";
                break;
            default:
                result = "";
        }
        return result;
    }
    
}
