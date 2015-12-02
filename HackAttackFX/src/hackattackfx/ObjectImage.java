/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import javafx.scene.image.ImageView;

/**
 *
 * @author Jasper Rouwhorst
 */
public abstract class ObjectImage extends ImageView {
    
    private Object referencedObject;
    
    public ObjectImage(Object reference){
        referencedObject = reference;
    }
    
    /**
     * 
     * @return The referenced object.
     */
    public Object getReference(){
        return referencedObject;
    }
}
