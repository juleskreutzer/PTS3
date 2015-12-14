/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import hack.attack.rmi.Effect;
import hack.attack.server.enums.LogState;
import hack.attack.server.logger.Log;
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
        
        if(duration > 0){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    callback.onExpired();
                }
            }, duration);
        }else{
            callback.onExpired();
            HackAttackServer.writeConsole(new Log(LogState.WARNING, "Duration of spell is smaller then 0"));
        }
    }
    
    public Effect getEffectType(){
        return type;
    }
    
    public int getDuration(){
        return duration;
    }

}
