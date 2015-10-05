/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

/**
 *
 * @author Jasper Rouwhorst
 */
public class GameTime {
    
    private final static double firstLoopTime = System.nanoTime();
    private static double lastLoopTime = System.nanoTime();
    // Elapsed time of the whole game in milliseconds
    private static double elapsedTime;
    public final static int TARGET_FPS = 60;
    public final static double OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    
    private GameTime(){
        
    }
    
    public static double getDeltaTime(){
        double now = System.nanoTime();
        double updateLength = now - lastLoopTime;
        lastLoopTime = now;
        double delta = updateLength / OPTIMAL_TIME;
        return delta;
    }
    
    public static double getLastLoopTime(){
        return lastLoopTime;
    }
    
    /**
     * 
     * @return The elapsed time of the whole game in milliseconds 
     */
    public static double getElapsedTime(){
        return (lastLoopTime - firstLoopTime) / 1000000;
    }
    
}
