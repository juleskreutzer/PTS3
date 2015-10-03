/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import hack.attack.enums.ModuleName;
import java.awt.Point;
import hack.attack.exceptions.*;
/**
 * BitcoinMiner is a module that generates bitcoins for the player.
 * @author juleskreutzer, Jasper Rouwhorst
 */

public class BitcoinMiner extends Module {

    /**
     * This interface is used to let the listener know this module has mined some bitcoins.
     * @param mineValue The amount that's mined.
     */
    public interface OnMineComplete{
        void onMine(double mineValue);
    }
    
    private double valuePerSecond;
    
    /**
     * 
     * @param cost The costs to build this module.
     * @param position The position this module should be builded(upleft corner).
     * @param width The width of this module.
     * @param height The height of this module.
     * @param level The initial level of this module.
     * @param name The {@link ModuleName} of this module.
     * @param valuePerSecond The amount this module generates per second.
     */
    public BitcoinMiner(double cost, Point position, int width, int height, int level)
    {
        super(cost, position, width, height, ModuleName.BITCOIN_MINER, level);
        valuePerSecond = level * 10;
    }
    
    /**
     * Upgrades this module
     * @return Whether the module was successfully upgraded.
     */
    public boolean upgrade(){
        level++;
        valuePerSecond = level * 10;
        return true;
    }
    
}
