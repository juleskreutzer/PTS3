/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server.exceptions;

/**
 *
 * @author Jasper Rouwhorst
 */
public class InvalidObjectException extends Exception {
    
    public InvalidObjectException()
    {
        super();
    }
    
    public InvalidObjectException(String message)
    {
        super(message);
    }
}
