/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hack.attack.exceptions.InvalidModuleEnumException;
import hackattackfx.GameEngine.OnExecuteTick;
import hackattackfx.enums.DefenseType;
import hackattackfx.enums.Effect;
import hackattackfx.enums.ModuleName;
import hackattackfx.exceptions.UnsubscribeNonListenerException;
import java.awt.Point;
import hackattackfx.templates.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a module used for the defense of your base. 
 * It fires {@link Bullet } at enemy minions.
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class Defense extends Module {
    private double damage;
    private int range;
    private DefenseType type;
    private Effect effect;
    
    private Minion target;
    
    // The tickListener is declared when this module is activated
    private OnExecuteTick tickListener;
    
    /**
     * Constructor for the Defense module based on the DefenseTemplate
     * @param template instance of DefenseTemplate created in the data class
     * @param position position of the module on the map
     * @param width width of the module
     * @param height height of the module
     * @throws InvalidModuleEnumException when the
     * 
     * Because we have to check if the given ModuleName is correct, we create an array of ModuleName with all the possible values and check if the given ModuleName is correct
     */
    public Defense(DefenseTemplate template, Point position, int width, int height) throws InvalidModuleEnumException
    {
        super(template.getCost(), position, width, height, template.getModuleName(), template.getLevel());
        ModuleName ModuleNameList[] = new ModuleName[] {ModuleName.BOTTLECAP_ANTIVIRUS, ModuleName.MUSCLE_ANTIVIRUS, ModuleName.SCALE_ANTIVIRUS, ModuleName.SNIPER_ANTIVIRUS};
        if(!Arrays.asList(ModuleNameList).contains(template.getModuleName()))
        {
            throw new InvalidModuleEnumException();
        }
        this.type = template.getDefenseType();
        this.effect = template.getEffect();
        this.damage = template.getDamage();
        this.range = template.getRange();
        
    }
    
    // Activates the module to listen to the {@link GameEngine} ticks
    public void activate(){
        target = null;
        tickListener = new OnExecuteTick() {

            @Override
            public void onTick(long elapsedtime) {
                if(!hasTarget()){
                    target = findTarget();
                }else{
                    if(targetInRange(target)){
                        fire(target);
                    }else{
                        target = null;
                    }
                }
            }
        };
        GameEngine.getInstance().setOnTickListener(tickListener);
    }
    
    public void deactivate(){
        try {
            GameEngine.getInstance().unsubscribeListener(tickListener);
        } catch (UnsubscribeNonListenerException ex) {
            Logger.getLogger(Defense.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @return Type of the object as String
     */
    public String getDefenceTypeString()
    {
        String result;
        
        switch(type)
        {
            case RANGE:
                result = "range";
                break;
            case CHEAP:
                result = "cheap";
                break;
            case BALANCED:
                result = "balanced";
                break;
            case STRONG:
                result = "strong";
                break;
            default:
                result = "";
        }
        return result;
    }
    
    /**
     * 
     * @return return the effect as String
     */
    public String getEffectString()
    {
        String result;
        
        switch(effect)
        {
            case SLOWED:
                result = "slow";
                break;
            case POISENED:
                result = "poison";
                break;
            case SPLASH:
                result = "slash";
                break;
            case DECRYPTED:
                result = "decryptor";
                break;
            default:
                result = "";
        }
        return result;
    }
    
    public DefenseType getDefenceType(){
        return type;
    }
    
    public Effect getEffect(){
        return effect;
    }
    
    /**
     * Sets the Defense modules effect.
     * @param e
     */
    public void setEffect(Effect e){
        effect = e;
    }
    
    public double getDamage(){
        return damage;
    }
    
    /**
     * Sets the towers damage.
     * @param d, must be above 0.
     */
    public void setDamage(double d){
        if (d > 0)
        {
            damage = d;
        }
    }
    
    public int getRange(){
        return range;
    }
    
    /**
     * Sets the towers RANGE.
     * @param r, must be above 0.
     */
    public void setRange(int r){
        if (range > 0)
        {
            range = r;
        }
    }
    
    /**
     * Upgrades this module
     * @return Whether the module was successfully upgraded.
     */
    public boolean upgrade(){
        level++;
        damage = level * 10;
        return true;
    }
    
    /**
     * Look for enemy minions within the RANGE of this module, then randomly select a target.
     * @return The enemy minion that's targeted. If no minion is found, return null
     */
    public Minion findTarget(){
        GameEngine engine = GameEngine.getInstance();
        Iterator<Minion> minions = engine.getActiveWave().minions();
        while(minions.hasNext()){
            Minion m = minions.next();
            if(targetInRange(m)){
                return m;
            }
        }
        return null;
    }
    
    /**
     * Checks if the given minion is within the range of the module
     * @param m The minion to check on
     * @return True if the given minion is in range.
     */
    public boolean targetInRange(Minion m){
        int distance = (int)Math.sqrt((position.x-m.getPosition().x)*(position.x-m.getPosition().x) + (position.y-m.getPosition().y)*(position.y-m.getPosition().y));
        return range >= (int)Math.sqrt((position.x-m.getPosition().x)*(position.x-m.getPosition().x) + (position.y-m.getPosition().y)*(position.y-m.getPosition().y));
    }
    
    /**
     * Checks if this module has an active target
     * @return True is this module is targeting a minion
     */
    public boolean hasTarget(){
        return target != null;
    }
    
    /**
     * Fire a {@link Bullet} at the module's target.
     * @param minion The enemy minion target.
     */
    public void fire(Minion minion){
        System.out.println(this.toString() + " is attacking " + minion.toString());
    }
    
}
