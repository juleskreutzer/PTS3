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
public class Defense {
    
    private double cost;
    private Point xPos;
    private Point yPos;
    private DefenseType type;
    private DefenseEffect effect;
    
    /**
     * 
     * @return The cost of the object
     */
    public double getCost() {
        return this.cost;
    }
    
    /**
     * 
     * @return xPosition of the object
     */
    public Point getXPos()
    {
        return this.xPos;
    }
    
    /**
     * 
     * @return yPosition of the object
     */
    public Point getYPos()
    {
        return this.yPos;
    }
    
    /**
     * 
     * @return Type of the object as String
     */
    private String getType()
    {
        String result;
        
        switch(type)
        {
            case range:
                result = "range";
                break;
            case cheap:
                result = "cheap";
                break;
            case balanced:
                result = "balanced";
                break;
            case strong:
                result = "strong";
                break;
            default:
                result = "";
        }
        return result;
    }
    
    /**
     * 
     * @return return the effect as String
     */
    private String getEffect()
    {
        String result;
        
        switch(effect)
        {
            case slow:
                result = "slow";
                break;
            case poison:
                result = "poison";
                break;
            case slash:
                result = "slash";
                break;
            case decryptor:
                result = "decryptor";
                break;
            default:
                result = "";
        }
        return result;
    }
    
    @Override
    public String toString()
    {
        return "This tower with position (" + this.xPos + "," + this.yPos + ") is of type " + getType() + " and has the " + getEffect() + " effect";
    }
    
}
