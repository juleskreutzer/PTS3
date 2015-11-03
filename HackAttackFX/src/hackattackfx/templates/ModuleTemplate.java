/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx.templates;

import hackattackfx.enums.*;
import hackattackfx.exceptions.*;
import java.awt.Point;
/**
 *
 * @author juleskreutzer
 */
public abstract class ModuleTemplate {
    protected double cost;
    protected int level;
    protected String displayName;
    protected boolean allowBuild = false;
    protected ModuleName moduleName;
    
    public ModuleTemplate(double cost, int level, ModuleName name)
    {
        this.cost = cost;
        this.level = level;
        this.moduleName = name;
        
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
    
    /**
     * Returns whether the module may be build
     * @return true if module may be build, false if not
     */
    public boolean getAllowBuild()
    {
        return this.allowBuild;
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
