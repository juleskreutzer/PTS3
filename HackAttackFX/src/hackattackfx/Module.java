/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.ModuleName;
import java.awt.Point;
import hackattackfx.exceptions.*;

/**
 *
 * @author juleskreutzer
 */
abstract public class Module {
    protected Point position;
    protected int width;
    protected int height;
    protected double cost;
    protected int level;
    protected String displayName;
    protected boolean allowBuild = false;
    protected ModuleName moduleName;
    
    public Module(double cost, Point position, int width, int height, ModuleName name, int level)
    {
        this.width = width;
        this.height = height;
        this.cost = cost;
        this.position = position;
        this.moduleName = name;
        
        getName();
    }
    
    /**
     * 
     * @return the display name of the object
     */
    public String getDisplayName()
    {
        return this.displayName;
    }
     
    /**
     * Get the position for a module
     * @return an array with the x,y coordinate of the module
     */
    public Point getPosition()
    {
        return this.position;
    }
    
    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
    
    /**
     * 
     * @return the cost of the object
     */
    public double getCost()
    {
        return this.cost;
    }
    
    public int getLevel(){
        return level;
    }
    
    public void setLevel(int l){
        level = l;
    }
    
    /**
     * Returns whether the module may be build
     * @return true if module may be build, false if not
     */
    public boolean getAllowBuild()
    {
        return this.allowBuild;
    }
    
    /**
     * Change if the module may be build
     * @param allowBuild true if module may be build, false if not
     */
    public void setAllowBuild(boolean allowBuild)
    {
        this.allowBuild = allowBuild;
    }
    
    /**
     * Get the moduleName enum for this module
     * @return the moduleName enum for the module
     */
    public ModuleName getModuleName()
    {
        return this.moduleName;
    }
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
                this.displayName = "Muscle Antivirus";
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}