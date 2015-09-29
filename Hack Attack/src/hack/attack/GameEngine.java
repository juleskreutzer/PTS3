package hack.attack;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class GameEngine implements MouseListener {

    public interface OnExecuteTick{
        void onTick();
    }
    
    public interface OnCompleteTick{
        void tickComplete();
    }
    
    private static GameEngine instance;
    
    private GraphicsEngine graphicsEngine;
    private ArrayList<Wave> waveList;
    private Player playerA;
    private Player playerB;
    
    private int elapsedTime;
    private Spell selectedSpell;
    
    private GameEngine(){
        instance = this;
        initialize();
    }
    
    public static GameEngine getInstance(){
        return instance == null ? new GameEngine() : instance;
    }
    
    private void initialize(){
        graphicsEngine = GraphicsEngine.getInstance();
        waveList = new ArrayList<Wave>();
        playerA = new Player(100, "Jasper", 100, new Point(0,50));
        playerB = new Player(100, "Jules", 100, new Point(100,50));
    }
    
    private void tick(){
        
    }
    
    public void setOnTickListener(Object listener, OnExecuteTick callback){
        
    }
    
    public void setOnTickCompleteListener(Object listener, OnCompleteTick callback){
        
    }
    
    public void executeSpell(Spell spell){
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
