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
public class InvalidSpellNameException extends Exception {
    
    public InvalidSpellNameException(String message)
    {
        super(message);
    }
    
    public InvalidSpellNameException()
    {
        super();
    }
}
