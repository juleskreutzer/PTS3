/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx.exceptions;

/**
 *
 * @author juleskreutzer
 */
public class LocationUnavailableException extends Exception {
 
    public LocationUnavailableException()
    {
        super();
    }
    
    public LocationUnavailableException(String message)
    {
        super(message);
    }
}
