/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server.templates;

import hack.attack.rmi.MinionType;
import hack.attack.server.exceptions.*;

/**
 *
 * @author jaspe_000
 */
public class MinionTemplate {
    private MinionType type;
    private double health;
    private double speed;
    private double damage;
    private boolean encrypted;
    private double reward;
    
    public MinionTemplate(MinionType t, double h, double s, double d, boolean e, double r){
        type = t;
        health = h;
        speed = s;
        damage = d;
        encrypted = e;
        reward = r;
    }
    
    public MinionType getMinionType(){
        return type;
    }
    
    public double getHealth(){
        return health;
    }
    
    public double getSpeed(){
        return speed;
    }
    
    public double getDamage(){
        return damage;
    }
    
    public boolean getEncrypted(){
        return encrypted;
    }
    
    public double getReward(){
        return reward;
    }
    
}
