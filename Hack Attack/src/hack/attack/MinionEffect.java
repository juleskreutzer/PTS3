/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import hack.attack.enums.Effect;
import java.util.Date;
import hack.attack.exceptions.*;

/**
 *
 * @author juleskreutzer
 */
public class MinionEffect {
    
    public interface OnEffectExpired{
        void onExpired();
    }
    
    private Date endTime;
    private Effect type;
    
    public MinionEffect(OnEffectExpired callback)
    {
        
    }
    
}
