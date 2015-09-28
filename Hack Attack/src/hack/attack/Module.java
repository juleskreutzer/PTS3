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
    private Point position;
    private double cost;
    private int level;
    private String displayName;
    private boolean allowBuild = false;
    private ModuleName moduleName;
    
    public Module(double cost, Point position, ModuleName name, int level)
    {
        this.cost = cost;
        this.position = position;
        this.moduleName = name;
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
     * @return the position of the module
     */
    
    /**
     * 
     * @return the name of the object as a String
     */
    protected void getName()
    {        
        switch(moduleName)
        {
            case BITCOIN_MINER:
                this.displayName = "Bitcoin Miner";
                break;
            case SOFTWARE_INJECTOR:
                this.displayName = "Software Injector";
                break;
            case CPU_UPGRADE:
                this.displayName = "CPU Upgrade";
                break;
            case SNIPER_ANTIVIRUS:
                this.displayName = "Sniper Antivirus";
                break;
            case BOTTLECAP_ANTIVIRUS:
                this.displayName = "Bottlecap Antivirus";
                break;
            case SCALE_ANTIVIRUS:
                this.displayName = "Scale Antivirus";
                break;
            case MUSCLE_ANTIVIRUS:
                this.displayName = "Muscel Antivirus";
                break;
            default:
                this.displayName = "";
        }
    }
    
    @Override
    public String toString()
    {
        return "This module with position (" + this.xPos + "," + this.yPos + ") has the name " + getName();
    }
}
