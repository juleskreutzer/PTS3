/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;
import hackattackfx.exceptions.FloatingPathException;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This is an implementation that works for a single player game.
 * In the future different methods will be added to ensure multiplayer will be possible.
 * @author Igor, Jasper Rouwhorst
 */
public class Map {
    private static Map instance;
    
    private static Road road; //The road that belongs to the map.
    private static Point baseLocationA; //The base location of player A.
    private static Point baseLocationB; //The base location of player B.
    
    /**
     * Constructor of Map, creates a new instance of Map.
     */
    private Map(){
        
        road = new Road();
        
        baseLocationA = new Point(135,350);
        baseLocationB = new Point(500,500); 
        
        //Create the map's road out of Paths.
        try {
            road.addPath(new Path(new Point(baseLocationA.x, baseLocationA.y),50, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),70, Path.Direction.Up));
            road.addPath(new Path(road.getEnd(),360, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),140, Path.Direction.Down));
            road.addPath(new Path(road.getEnd(),180, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),140, Path.Direction.Up));
            road.addPath(new Path(road.getEnd(),95, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),165, Path.Direction.Up));
            road.addPath(new Path(road.getEnd(),360, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),70, Path.Direction.Down));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Right));
            
        } catch (FloatingPathException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Map getInstance(){
        return instance == null ? new Map() : instance;
    }
    
    public Road getRoad(){
        return road;
    }
    
    public Point getBaseLocationA(){
        return baseLocationA;
    }
    
    public Point getBaseLocationB(){
        return baseLocationB;
    }
}
