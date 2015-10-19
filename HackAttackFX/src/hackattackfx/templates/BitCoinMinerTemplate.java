/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx.templates;

import hackattackfx.enums.ModuleName;

/**
 *
 * @author juleskreutzer
 */
public class BitCoinMinerTemplate extends ModuleTemplate {

    private int valuePerSecond;
    public BitCoinMinerTemplate(double cost, int level, ModuleName name, int value) {
        super(cost, level, name);
        this.valuePerSecond = value;
    }
    
    public int getValuePerSecond()
    {
        return this.valuePerSecond;
    }
    
    
}
