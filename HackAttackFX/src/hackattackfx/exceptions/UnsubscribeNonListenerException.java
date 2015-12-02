/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx.exceptions;

/**
 *
 * @author Jasper Rouwhorst
 */
public class UnsubscribeNonListenerException extends Exception{
    
    public UnsubscribeNonListenerException(){
        
    }
    
    public UnsubscribeNonListenerException(String message){
        super(message);
    }
    
}
