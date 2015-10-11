/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.FloatingPathException;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jasper Rouwhorst
 */
public class Road {
    
    private List<Path> paths; //The collection of paths the road is made out of.
    
    /**
     * Creates a new Road.
     */
    public Road(){
        paths = new ArrayList<>();
    }
    
    /**
     * Get the begin point of the road.
     * @return, returns the starting point of the first path in paths List.
     */
    public Point getBegin(){
        return paths.get(0).getStart();
    }
    
    /**
     * Get the end point of the road.
     * @return, returns the ending point of the last path in paths List.
     */
    public Point getEnd(){
        Path end = paths.get(paths.size() - 1);
        return end.getEnd();
    }
    
    /**
     * Returns the paths the Road is made of.
     * @return, the paths List.
     */
    public List<Path> getPaths(){
        return paths;
    }
    
    /**
     * 
     * @param p
     * @throws FloatingPathException, throw this exception when 
     */
    public void addPath(Path p) throws FloatingPathException{
        //TODO add comments.
        if(paths.size() > 0){
                Point roadstart = getBegin();
                Point roadend = getEnd();
                Point pstart = p.getStart();
                Point pend = p.getEnd();
                    if(pstart.x == roadend.x && pstart.y == roadend.y){
                        paths.add(p);
                    }else if(pend.x == roadstart.x && pend.y == roadstart.y){
                        paths.add(0, p);
                    }else{
                        throw new FloatingPathException("This path doesn't connect the roads start or end position!");
                    }
            }else{
                paths.add(p);
            }
    }
    
}
