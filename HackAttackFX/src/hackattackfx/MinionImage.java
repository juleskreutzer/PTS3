/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.MinionType;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import hackattackfx.exceptions.*;

/**
 *
 * @author juleskreutzer
 */
public class MinionImage extends ObjectImage {

    private Minion minion;
    
    public MinionImage(Minion m){
        super(m);
        minion = m;
    }
    
    public Minion getMinion(){
        return minion;
    }
    
}
