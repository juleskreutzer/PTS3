/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import hack.attack.exceptions.*;

/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class BulletImage extends ObjectImage {
    
    private Bullet bullet;
    
    public BulletImage(Bullet bullet){
        super(bullet);
        this.bullet = bullet;
    }
    
    public Bullet getBullet(){
        return bullet;
    }
}
