/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.ModuleName;
import java.awt.Point;
import hack.attack.exceptions.*;
import hackattackfx.templates.*;
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
     * @param cost The costs to build this module (must be > 0).
     * @param position The position this module should be builded(upleft corner), must fall within the X and Y values of the singleton class Map.
     * @param width The width of this module.
     * @param height The height of this module.
     * @param level The initial level of this module (must be > 0).
     * @param name The {@link ModuleName} of this module.
     * @param valuePerSecond The amount this module generates per second. (must be above 0).
     */
    public BitcoinMiner(double cost, Point position, int width, int height, int level)
    {
        //TO DO
        //Check if position falls within the X and Y values of the map singleton.
        //Check if level is above 0.
        //Check if cost is above 0.
        //Check if valuePerSecond is above 0.
        
        super(cost, position, width, height, ModuleName.BITCOIN_MINER, level);
        valuePerSecond = level * 10;
        
    }
    
    /**
     * Constructor for the BitcoinMiner based on the BitCoinMinerTemplate
     * @param template Instance of BitCoinMinerTemplate created in data class
     * @param position position of the module on the map
     * @param width width of the module
     * @param height height of the module
     * @throws InvalidModuleEnumException 
     */
    public BitcoinMiner(BitCoinMinerTemplate template, Point position, int width, int height) throws InvalidModuleEnumException
    {
        super(template.getCost(), position, width, height, template.getModuleName(), template.getLevel());
        if(template.getModuleName() != ModuleName.BITCOIN_MINER)
        {
            // ModuleName is incorrect
            throw new InvalidModuleEnumException();
        }
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
