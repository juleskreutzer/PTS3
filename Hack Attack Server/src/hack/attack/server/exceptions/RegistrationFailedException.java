/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server.exceptions;

/**
 *
 * @author juleskreutzer
 */
public class RegistrationFailedException extends Exception {
    public RegistrationFailedException(String message)
    {
        super(message);
    }
    
    public RegistrationFailedException()
    {
        super();
    }
}
