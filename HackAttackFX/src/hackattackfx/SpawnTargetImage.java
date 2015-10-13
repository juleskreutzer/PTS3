/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.templates.ModuleTemplate;
import javafx.scene.image.ImageView;

/**
 *
 * @author Jasper Rouwhorst
 */
public class SpawnTargetImage extends ImageView{
    
    private ModuleTemplate spawnable;
    
    public SpawnTargetImage(ModuleTemplate m){
        spawnable = m;
    }
    
}
