/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.FloatingPathException;
import hackattackfx.exceptions.InvalidPathException;
import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Path is a straight horizontal/vertical line and could be a part of a road by adding it to a list of Path objects
 * It is yet only possible to add a path to the beginning or end of the road.
 * @author Jasper Rouwhorst
 */
public class Path {
    
    public static enum Direction{
        Up,Right,Down,Left
    }
    
    private Point start;
    private Point end;
    private Direction direction;

    public Path(Point s, Point e){
        start = s;
        end = e;
        try {
            initialize();
        } catch (FloatingPathException ex) {
            Logger.getLogger(Path.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidPathException ex) {
            Logger.getLogger(Path.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Path(Point s, int lenght, Direction direction){
        start = s;
        switch(direction){
            case Up:
                end = new Point(s.x, s.y - lenght);
                break;
            case Right:
                end = new Point(s.x + lenght, s.y);
                break;
            case Down:
                end = new Point(s.x, s.y + lenght);
                break;
            case Left:
                end = new Point(s.x - lenght, s.y);
                break;
        }
        this.direction = direction;
        try {
            initialize();
        } catch (FloatingPathException ex) {
            Logger.getLogger(Path.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidPathException ex) {
            Logger.getLogger(Path.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initialize() throws FloatingPathException, InvalidPathException{
        // Check if the Path is a straight horizontal or vertical line
        if(!((start.x != end.x && start.y == end.y) || (start.y != end.y && start.x == end.x))){
            throw new InvalidPathException("The path is not a straight horizontal/vertical line");
        }
    }
    
    public Point getStart(){
        return start;
    }

    public Point getEnd(){
        return end;
    }
    
}
