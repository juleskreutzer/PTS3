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
