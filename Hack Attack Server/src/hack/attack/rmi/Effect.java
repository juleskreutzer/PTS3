/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.rmi;

import java.io.Serializable;

/**
 *
 * @author Igor
 */
public enum Effect implements Serializable {
    SLOWED,
    POISENED,
    SPLASH,
    DECRYPTED,
    DIE,
    REACHED_BASE,
    BUFFED,
    STOPPED,
    ENCRYPT
    
}
