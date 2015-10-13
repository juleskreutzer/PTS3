/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

/**
 *
 * @author juleskreutzer
 */
public class ModuleImage extends ObjectImage {

    private Module module;
    
    public ModuleImage(Module m){
        super(m);
        module = m;
        
        File file = null;
        switch(module.getModuleName()){
            case BITCOIN_MINER:
                file = new File("src/hackattackfx/resources/sniper_av.png");
                break;
            case SOFTWARE_INJECTOR:
                file = new File("src/hackattackfx/resources/sniper_av.png");
                break;
            case CPU_UPGRADE:
                file = new File("src/hackattackfx/resources/sniper_av.png");
                break;
            case SNIPER_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/sniper_av.png");
                break;
            case BOTTLECAP_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/sniper_av.png");
                break;
            case SCALE_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/sniper_av.png");
                break;
            case MUSCLE_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/sniper_av.png");
                break;
        }
        
        Image image = new Image(file.toURI().toString());
        setX(module.getPosition().x - image.getWidth()/2);
        setY(module.getPosition().y - image.getHeight()/2);
        this.setImage(image);
        
    }
    
}
