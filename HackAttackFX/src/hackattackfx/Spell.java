/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;
import hackattackfx.enums.SpellName;
import hackattackfx.exceptions.*;

/**
 *
 * @author juleskreutzer
 */
public class Spell {
    
    private SpellName name;
    private int cooldown;
    private int requiredLevel;
    
    public Spell(SpellName n, int c, int rl){
        name = n;
        cooldown = c;
        requiredLevel = rl;
    }
    
    public SpellName getName(){
        return name;
    }
    
    public int getCooldown(){
        return cooldown;
    }
    
    public int getRequiredLevel(){
        return requiredLevel;
    }
}
