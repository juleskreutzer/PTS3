/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client.templates;

import hack.attack.client.enums.ModuleName;

/**
 *
 * @author juleskreutzer
 */
public class BitCoinMinerTemplate extends ModuleTemplate {

    private int valuePerSecond;
    public BitCoinMinerTemplate(double cost, int level, ModuleName name, int value, String desc) {
        super(cost, level, name, desc);
        this.valuePerSecond = value;
    }
    
    public int getValuePerSecond()
    {
        return this.valuePerSecond;
    }
    
    
}
