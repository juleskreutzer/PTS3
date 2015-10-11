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
    
    private List<Path> paths;
    
    public Road(){
        paths = new ArrayList<>();
    }
    
    public Point getBegin(){
        return paths.get(0).getStart();
    }
    
    public Point getEnd(){
        Path end = paths.get(paths.size() - 1);
        return end.getEnd();
    }
    
    public List<Path> disect(){
        return paths;
    }
    
    public void addPath(Path p) throws FloatingPathException{
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
