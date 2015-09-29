/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import hack.attack.enums.ModuleName;
import java.awt.Point;

/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class BitcoinMiner extends Module {

    public interface OnMineComplete{
        void onMine(double mineValue);
    }
    
    private double valuePerSecond;
    
    public BitcoinMiner(double cost, Point position, int width, int height, int level, ModuleName name, double valuePerSecond)
    {
        super(cost, position, width, height, name, level);
        this.valuePerSecond = valuePerSecond;
    }
    
    public boolean upgrade(){
        return false;
    }
    
}
