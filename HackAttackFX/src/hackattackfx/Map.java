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
 *
 * @author Igor, Jasper Rouwhorst
 */
public class Map {
    private static Map instance;
    
    private static Road road;
    private static List<Point> defenseBuildLocations;
    private static List<Point> baseBuildLocations;
    
    public Map(){
        
        
        road = new Road();
        defenseBuildLocations = new ArrayList<Point>();
        baseBuildLocations = new ArrayList<Point>();
        
        baseBuildLocations.add(new Point(200,200)); 
        baseBuildLocations.add(new Point(500,500)); 
        
        Point base = baseBuildLocations.get(0);
        try {
            road.addPath(new Path(new Point(base.x, base.y),50, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Down));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Down));
            road.addPath(new Path(road.getEnd(),200, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Up));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Down));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Down));
            road.addPath(new Path(road.getEnd(),200, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Up));
        } catch (FloatingPathException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Map getInstance(){
        return instance == null ? new Map() : instance;
    }
    
    public List<Point> getDefenseBuildLocations(){
        return defenseBuildLocations;
    }
    
    public List<Point> getBaseBuildLocations(){
        return baseBuildLocations;
    }
    
    public Road getRoad(){
        return road;
    }
    
    
}
