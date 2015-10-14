/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx.exceptions;

/**
 * Voor het aanmaken van een eigen exception, zie: http://stackoverflow.com/questions/1070590/how-can-i-write-custom-exceptions
 * LET OP: er is een verschil tussen Checked & Unchecked exception: http://www.javapractices.com/topic/TopicAction.do?Id=129
 */
/**
 *
 * @author juleskreutzer
 */
public class InvalidModuleEnumException extends Exception {
    
    public InvalidModuleEnumException()
    {
        super();
    }
    
    public InvalidModuleEnumException(String message)
    {
        super(message);
    }
}
