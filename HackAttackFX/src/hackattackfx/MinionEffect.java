/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.Effect;
import java.util.Date;
import hackattackfx.exceptions.*;

/**
 *
 * @author juleskreutzer
 */
public class MinionEffect {
    
    /**
     * 
     */
    public interface OnEffectExpired{
        void onExpired();
    }
    
    private Date endTime; //The time at which the minions effect will expire.
    private Effect type; //The effects type, this will determine the consequences that it inflicts upon a minion.
    
    
    public MinionEffect(OnEffectExpired callback, Effect type, Minion m)
    {
        if(type == Effect.DIE)
        {
            // 
            GraphicsEngine.getInstance().drawEffect(false, m.getPosition(), m.getReward(), m.getDamage());
        }
        else if(type == Effect.REACHED_BASE)
        {
            GraphicsEngine.getInstance().drawEffect(true, m.getPosition(), m.getReward(), m.getDamage());
        }
    }
    
}
