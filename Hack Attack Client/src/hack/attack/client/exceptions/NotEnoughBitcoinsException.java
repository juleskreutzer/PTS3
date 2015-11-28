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
public class NotEnoughBitcoinsException extends Exception {
    
    public NotEnoughBitcoinsException()
    {
        super();
    }
    
    public NotEnoughBitcoinsException(String message)
    {
        super(message);
    }
    
}
