/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import hack.attack.client.interfaces.*;
import java.util.List;

/**
 *
 * @author juleskreutzer
 */
public class ClientAdapter implements IClientCreate, IClientUpdate, IClientDelete {
    
    private String sessionKey;
    private IServerUpdate update;
    private GraphicsEngine engine;
    
    public ClientAdapter(IServerUpdate update, String sessionKey)
    {
        this.update = update;
        this.sessionKey = sessionKey;
        this.engine = GraphicsEngine.getInstance();
    }

    @Override
    public void drawNewModules(List<Module> modules) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawNewMinions(List<Minion> minions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawNewSpells(List<Spell> spells) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void redrawCurrentModules(List<Module> modules) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void redrawCurrentMinions(List<Minion> minions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void redrawCurrentSpells(List<Spell> minions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateLabels(Player playerA, Player playerB) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCurrentModules(List<Module> modules) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCurrentMinions(List<Minion> minions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCurrentSpells(List<Spell> spells) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
