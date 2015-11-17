/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx.interfaces;

import java.awt.Point;

/**
 * This is an empty interface. It's main purpose is to tag an object as targetable by a spell.
 * A spell need objects that implement this interface to define if the spell can execute on the target.
 * @author Jasper Rouwhorst
 */


public interface ITargetable {
    
    public Point getPosition();
    
}
