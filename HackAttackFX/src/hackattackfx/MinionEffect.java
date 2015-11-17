/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.Effect;
import java.util.Timer;
import java.util.TimerTask;

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
    
    private OnEffectExpired callback;
    private int duration; //The duration in milliseconds
    private Effect type; //The effects type, this will determine the consequences that it inflicts upon a minion.
    
    
    public MinionEffect(Effect type, int duration, OnEffectExpired callback)
    {
        this.type = type;
        this.duration = duration;
        this.callback = callback;
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                callback.onExpired();
            }
        }, 0, duration);
    }
    
    public Effect getEffectType(){
        return type;
    }
    
}
