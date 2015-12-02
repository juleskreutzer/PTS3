/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client.exceptions;

/**
 *
 * @author Jasper Rouwhorst
 */
public class DuplicateSpawnException extends Exception {
    
    public DuplicateSpawnException()
    {
        super();
    }
    
    public DuplicateSpawnException(String message)
    {
        super(message);
    }
}
