/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Igor, Jasper Rouwhorst
 */
public class Map {
    public static List<Point[]> path;
    public static List<Point> defenseBuildLocations = new ArrayList<Point>(){{
        add(new Point(50,50));  
        add(new Point(100,100));
    }};
    public static List<Point> baseBuildLocations = new ArrayList<Point>(){{
        add(new Point(50,50));  
        add(new Point(100,100));
    }};;
    
    
}
