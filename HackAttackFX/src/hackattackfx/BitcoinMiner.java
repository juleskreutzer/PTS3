/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.GameEngine.OnExecuteTick;
import hackattackfx.enums.ModuleName;
import java.awt.Point;
import hackattackfx.exceptions.*;
import hackattackfx.templates.*;
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
    private OnExecuteTick tick;
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
        super(template.getCost(), position, width, height, template.getModuleName(), template.getLevel());
        if(template.getModuleName() != ModuleName.BITCOIN_MINER)
        {
            // ModuleName is incorrect
            throw new InvalidModuleEnumException();
        }
        
        listeners = new ArrayList<OnMineComplete>();
        lastMine = 0;
    }
    
    /**
     * Upgrades this module
     * @return Whether the module was successfully upgraded.
     */
    public boolean upgrade(){
        level++;
        super.setLevel(level);
        valuePerSecond = level * 10;
        return true;
    }
    
    /**
     * Activate the bitcoin miner so the player gets their bitcoins
     */
    public void activate()
    {
        
        tick = new OnExecuteTick() {

            @Override
            public void onTick(long elapsedtime) {
                if(elapsedtime >= (lastMine + 3000))
                {
                    lastMine = elapsedtime;
                    for(OnMineComplete listen : listeners)
                    {
                        listen.onMine(valuePerSecond);
                    }
                }
                
            }
        };
    }
    
    public void setOnMineListener(OnMineComplete callback) throws DuplicateListenerException
    {
        if(!listeners.contains(callback))
        {
        listeners.add(callback);
        }
        else
        {
            throw new DuplicateListenerException();
        }
    }
    
}
