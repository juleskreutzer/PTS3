/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;
import hack.attack.exceptions.*;

/**
 *
 * @author juleskreutzer
 */
public class Spell {
    
    private String name;
    private int cooldown;
    private int requiredLevel;
    
    public Spell(String n, int c, int rl){
        name = n;
        cooldown = c;
        requiredLevel = rl;
    }
    
    public String getName(){
        return name;
    }
    
    public int getCooldown(){
        return cooldown;
    }
    
    public int getRequiredLevel(){
        return requiredLevel;
    }
    
}
