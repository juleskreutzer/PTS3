/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import java.awt.Point;

/**
 *
 * @author juleskreutzer
 */
public class BitcoinMiner extends Module {
    private double valuePerSecond;
    
    public BitcoinMiner(double cost, Point position, int level, ModuleName name, double valuePerSecond)
    {
        super(cost, position, name, level);
        this.valuePerSecond = valuePerSecond;
    }
    
}
