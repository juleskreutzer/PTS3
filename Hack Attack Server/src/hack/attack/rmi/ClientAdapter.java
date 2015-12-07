/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.rmi;
import hack.attack.server.SpawnTargetImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author juleskreutzer
 */
public class ClientAdapter extends UnicastRemoteObject implements IClientCreate, IClientUpdate, IClientDelete {
    
    private static final long serialVersionUID = 999000L;
    
    private static ClientAdapter instance;
    
    SpawnTargetImage st = null;
    
    private ClientAdapter() throws RemoteException
    {
    }
    
    public static ClientAdapter getInstance()
    {
        try{
            return instance == null ? new ClientAdapter() : instance;
        }catch(RemoteException ex){
            System.out.println(ex.toString());
        }
        return null;
    }
    

    @Override
    public void drawNewModules(List<Module> modules, int uID) {
    }

    @Override
    public void drawNewMinions(List<Minion> minions, int uID) {
    }

    @Override
    public void drawNewSpells(Effect effect, List<ITargetable> targets, int uID) {
    }

    @Override
    public void redrawCurrentModules(List<Module> modules, int uID) {
    }

    @Override
    public void redrawCurrentMinions(List<Minion> minions, int uID) {
    }

    @Override
    public void redrawCurrentSpells(List<Spell> spells, int uID) {
    }

    @Override
    public void deleteCurrentModules(List<Module> modules, int uID) {
    }

    @Override
    public void deleteCurrentMinions(List<Minion> minions, int uID) {
    }

    @Override
    public void deleteCurrentSpells(List<Spell> spells, int uID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateLabels(int waveNumber, String playernamea, String healthplayera, String bitcoinsplayera, String playernameb, String healthplayerb) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void startGame() {
        
    }

    @Override
    public void initialize() {
        
    }
    
}
