/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

/**
 *
 * @author Jasper Rouwhorst
 */
public class GameTime {
    
    private final static long firstLoopTime = System.nanoTime();
    private static long lastLoopTime = System.nanoTime();
    // Elapsed time of the whole game in milliseconds
    private static long elapsedTime;
    public final static int TARGET_FPS = 60;
    public final static long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    
    private GameTime(){
        
    }
    
    public static long getDeltaTime(){
        long now = System.nanoTime();
        long updateLength = now - lastLoopTime;
        //lastLoopTime = now;
        long delta = updateLength / OPTIMAL_TIME;
        return delta;
    }
    
    public static long getLastLoopTime(){
        return lastLoopTime;
    }
    
    /**
     * 
     * @return The elapsed time of the whole game in milliseconds 
     */
    public static long getElapsedTime(){
        boolean bigger = System.nanoTime() > firstLoopTime;
        return (System.nanoTime() - firstLoopTime) / 1000000;
    }
    
}
