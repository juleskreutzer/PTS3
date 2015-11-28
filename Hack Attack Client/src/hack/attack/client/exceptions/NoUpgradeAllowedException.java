/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client.exceptions;

/**
 *
 * @author juleskreutzer
 */
public class NoUpgradeAllowedException extends Exception {
    
    public NoUpgradeAllowedException()
    {
        super();
    }
    
    public NoUpgradeAllowedException(String message)
    {
        super(message);
    }
    
}
