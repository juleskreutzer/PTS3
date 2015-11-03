/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 *
 * @author juleskreutzer
 */
public class ModuleImage extends ObjectImage {

    private Module module;
    private boolean hovered;
    
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
                file = new File("src/hackattackfx/resources/interface/module/40x40/sniper_module.png");
                break;
            case BOTTLECAP_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/interface/module/40x40/bottlecap_module.png");
                break;
            case SCALE_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/interface/module/40x40/scale_module.png");
                break;
            case MUSCLE_ANTIVIRUS:
                file = new File("src/hackattackfx/resources/interface/module/40x40/muscle_module.png");
                break;
        }
        
        Image image = new Image(file.toURI().toString());
        setX(module.getPosition().x - (image.getHeight()/2));
        setY(module.getPosition().y - (image.getWidth()/2));
        this.setImage(image);
        
        if(module instanceof Defense){
            Defense defense = (Defense)module;
            this.setOnMouseEntered(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    
                    hovered = true;
                    GraphicsEngine.getInstance().drawModuleStats(module);
                }
            
            });
            this.setOnMouseExited(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    
                    hovered = false;
                    GraphicsEngine.getInstance().drawModuleStats(null);
                }
                
            });
        }
    }
    
    /**
     * Checks if this module is hovered
     * @return
     */
    public boolean hovered(){
            return hovered;
    }
    
}
