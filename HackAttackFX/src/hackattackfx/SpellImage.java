/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import javafx.scene.image.ImageView;
/**
 *
 * @author juleskreutzer
 */
public class SpellImage extends ObjectImage{

    private Spell spell;
    
    public SpellImage(Spell s){
        super(s);
        spell = s;
    }
    
    public Spell getSpell(){
        return spell;
    }

}
