/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.interfaces.ISpawnable;
import javafx.scene.image.ImageView;

/**
 *
 * @author juleskreutzer
 */
public class ModuleImage extends ObjectImage implements ISpawnable {

    private Module module;
    
    public ModuleImage(Module m){
        super(m);
        module = m;
    }

    @Override
    public ImageView getSpawnableImage() {
        return this;
    }
}
