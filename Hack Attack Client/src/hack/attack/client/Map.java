/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;
import hack.attack.client.exceptions.FloatingPathException;
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
    
    private static Road roadA; //The road that belongs to the map.
    private static Road roadB;
    private static Point baseLocationA; //The base location of player A.
    private static Point baseLocationB; //The base location of player B.
    
    /**
     * Constructor of Map, creates a new instance of Map.
     */
    private Map(){
        
        roadA = new Road();
        roadB = new Road();
        
        baseLocationA = new Point(170,90); // base that spaws minions on top map
        baseLocationB = new Point(1200,90); // base that spawns minions on bottom map
        
        //Create the map's road out of Paths.
        try {
            // Drawn bottom 
            roadA.addPath(new Path(new Point(baseLocationB.x, baseLocationB.y),180, Path.Direction.Left));
            roadA.addPath(new Path(roadA.getEnd(),110, Path.Direction.Down));
            roadA.addPath(new Path(roadA.getEnd(),280, Path.Direction.Left));
            roadA.addPath(new Path(roadA.getEnd(),110, Path.Direction.Up));
            roadA.addPath(new Path(roadA.getEnd(),280, Path.Direction.Left));
            roadA.addPath(new Path(roadA.getEnd(),110, Path.Direction.Down));
            roadA.addPath(new Path(roadA.getEnd(),290, Path.Direction.Left));
            
            // Drawn top
            roadB.addPath(new Path(new Point(baseLocationA.x, baseLocationA.y),180, Path.Direction.Right));
            roadB.addPath(new Path(roadB.getEnd(),110, Path.Direction.Down));
            roadB.addPath(new Path(roadB.getEnd(),280, Path.Direction.Right));
            roadB.addPath(new Path(roadB.getEnd(),110, Path.Direction.Up));
            roadB.addPath(new Path(roadB.getEnd(),280, Path.Direction.Right));
            roadB.addPath(new Path(roadB.getEnd(),110, Path.Direction.Down));
            roadB.addPath(new Path(roadB.getEnd(), 290, Path.Direction.Right));
            
        } catch (FloatingPathException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Map getInstance(){
        return instance == null ? new Map() : instance;
    }
    
    public Road getRoadA(){
        return roadA;
    }
    
    public Road getRoadB()
    {
        return roadB;
    }
    
    public Point getBaseLocationA(){
        return baseLocationA;
    }
    
    public Point getBaseLocationB(){
        return baseLocationB;
    }
}
