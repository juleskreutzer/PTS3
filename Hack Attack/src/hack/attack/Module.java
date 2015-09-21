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
public class Module {
    private double cost;
    private Point xPos;
    private Point yPos;
    private ModuleName name;
    
    public Module(double cost, Point xPos, Point yPos, ModuleName name)
    {
        this.cost = cost;
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
    }
    
    /**
     * 
     * @return the cost of the object
     */
    public double getCost()
    {
        return this.cost;
    }
    
    /**
     * 
     * @return the x-Position of the object
     */
    public Point getXPos()
    {
        return this.xPos;
    }
    
    /**
     * 
     * @return the Y-Position of the object
     */
    public Point getYPos()
    {
        return this.yPos;
    }
    
    /**
     * 
     * @return the name of the object as a String
     */
    protected String getName()
    {
        String result;
        
        switch(name)
        {
            case BITCOIN_MINER:
                result = "Bitcoin Miner";
                break;
            case SOFTWARE_INJECTOR:
                result = "Software Injector";
                break;
            case CPU_UPGRADE:
                result = "CPU Upgrade";
                break;
            case SNIPER_ANTIVIRUS:
                result = "Sniper Antivirus";
                break;
            case BOTTLECAP_ANTIVIRUS:
                result = "Bottlecap Antivirus";
                break;
            case SCALE_ANTIVIRUS:
                result = "Scale Antivirus";
                break;
            case MUSCLE_ANTIVIRUS:
                result = "Muscel Antivirus";
                break;
            default:
                result = "Enum not implemented correct in Module class";
        }
        return result;
    }
    
    @Override
    public String toString()
    {
        return "This module with position (" + this.xPos + "," + this.yPos + ") has the name " + getName();
    }
}
