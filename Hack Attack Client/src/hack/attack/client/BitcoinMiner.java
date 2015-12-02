/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import hack.attack.client.enums.ModuleName;
import java.awt.Point;
import hack.attack.client.exceptions.*;
import hack.attack.client.templates.*;
import java.util.ArrayList;
import java.util.List;
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
    private List<OnMineComplete> listeners;
    private long lastMine;
    
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
        super(template.getCost(), position, width, height, template.getModuleName(), template.getLevel(), template.getDescription());
        if(template.getModuleName() != ModuleName.BITCOIN_MINER)
        {
            // ModuleName is incorrect
            throw new InvalidModuleEnumException();
        }
        this.valuePerSecond = template.getValuePerSecond();
        listeners = new ArrayList<>();
        lastMine = 0;
    }
    
    public double getValuePerSecond(){
        return valuePerSecond;
    }
    /**
     * Upgrades this module
     * @return Whether the module was successfully upgraded.
     */
    public boolean upgrade() throws NoUpgradeAllowedException{
        BitCoinMinerTemplate newBCM;

        if(this.level == 1)
        {
            newBCM = Data.DEFAULT_MODULE_BITCOINMINER_2;
            this.level = newBCM.getLevel();
            super.setLevel(newBCM.getLevel());
            this.valuePerSecond = newBCM.getValuePerSecond();
            return true;
        }
        else if(this.level == 2)
        {
            newBCM = Data.DEFAULT_MODULE_BITCOINMINER_3;
            this.level = newBCM.getLevel();
            super.setLevel(newBCM.getLevel());
            this.valuePerSecond = newBCM.getValuePerSecond();
            return true;
        }
        else if(this.level == 3)
        {
            throw new NoUpgradeAllowedException("You already reached the maximum level for this Bitcoin Miner. Upgrade is not available.");
        }
        else
        {
            throw new NoUpgradeAllowedException("Something went wrong, you can't upgrade the Bitcoin Miner at this time.");
        }
    }
}
