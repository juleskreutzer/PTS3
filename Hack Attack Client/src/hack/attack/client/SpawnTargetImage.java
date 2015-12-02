/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import hack.attack.client.templates.ModuleTemplate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Jasper Rouwhorst
 */
public class SpawnTargetImage extends ImageView{
    
    private ModuleTemplate spawnable;
    private Image unavailable;
    private Image available;
    
    public SpawnTargetImage(ModuleTemplate m, Image unavailable, Image available){
        spawnable = m;
        this.unavailable = unavailable;
        this.available = available;
    }
    
    public Image getUnavailable()
    {
        return unavailable;
    }
    
    public Image getAvailable()
    {
        return available;
    }
    
}
