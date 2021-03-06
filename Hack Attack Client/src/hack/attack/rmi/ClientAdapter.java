/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.rmi;

import hack.attack.client.BitcoinMiner;
import hack.attack.client.CPUUpgrade;
import hack.attack.client.Data;
import hack.attack.client.FXMLDocumentController;
import hack.attack.client.FXMLDocumentController.Window;
import hack.attack.client.GraphicsEngine;
import hack.attack.client.MinionImage;
import hack.attack.client.ModuleImage;
import hack.attack.client.SoftwareInjector;
import hack.attack.client.SpawnTargetImage;
import hack.attack.client.exceptions.DuplicateSpawnException;
import hack.attack.client.exceptions.InvalidModuleEnumException;
import hack.attack.client.exceptions.InvalidObjectException;
import hack.attack.client.exceptions.NoUpgradeAllowedException;
import hack.attack.client.exceptions.NotEnoughBitcoinsException;
import hack.attack.client.templates.*;
import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author juleskreutzer
 */
public class ClientAdapter extends UnicastRemoteObject implements IClientCreate, IClientUpdate, IClientDelete {
    
    private static final long serialVersionUID = 999000L;
    
    private String sessionKey;
    private IServerUpdate update;
    private GraphicsEngine engine;
    private final IClientCreate clientCreate;
    private final IClientUpdate clientUpdate;
    private final IClientDelete clientDelete;
    
    private Account account;
    
    private static ClientAdapter instance;
    
    SpawnTargetImage st = null;
    SpellTemplate targetSpell = null; // Because st can't be used for a spell to despawn it
    
    private ClientAdapter() throws RemoteException
    {
        instance = this;
        this.clientCreate = (IClientCreate)instance;
        this.clientDelete = (IClientDelete)instance;
        this.clientUpdate = (IClientUpdate)instance;
        
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
    
    /**
     * This method sets the sessionKey than is needed to communicate with the server
     * @param sessionKey encrypted sessionKey string
     */
    public void setSessionKey(String sessionKey)
    {
        this.sessionKey = sessionKey;
    }
    
    /**
     * This method sets the IServerUpdate interface instance for the client so it can communicate with the server
     * @param update IServerUpdate instance
     */
    public void setIServerUpdate(IServerUpdate update)
    {
        this.update = update;
    }
    
    /**
     * This method sets the account that is needed to build, cast or upgrade modules
     * @param account Instance of an account class for the current player
     */
    public void setAccount(Account account)
    {
        this.account = account;
    }
    
    /**
     * This method will return the ID of this client
     * @return uID of the current user
     */
    public int getCurrentUserID()
    {
        return account.getUID();
    }
    /**
     * This method will return a HashMap that is needed to call the IServerConnect methods
     * @return HashMap<String, IClient> containing the following interfaces:
     * <ul>
     *  <li>Key: <b>create</b> -> <b>IClientCreate</b></li>
     *  <li>Key: <b>upgrade</b> -> <b>IClientUpgrade</b></li>
     *  <li>Key: <b>delete</b> -> <b>IClientDelete</b></li>
     * </ul>
     */
    public HashMap<String, IClient> getInterfaces()
    {
        HashMap<String, IClient> interfaces = new HashMap<>();
        interfaces.put("create", clientCreate);
        interfaces.put("update", clientUpdate);
        interfaces.put("delete", clientDelete);
        
        return interfaces;
    }
    
    @Override
    public void drawNewModules(List<Module> modules, int uID){
        try{
            // Check if minions isn't empty
            if(modules == null)
            {
                throw new IllegalArgumentException("Nothing to draw because modules are empty");
            }
            
            // Minions isn't empty, draw them
            for(Module m : modules)
            {
                m.setPosition(new Point(1366 - m.getPosition().x, m.getPosition().y));
                engine.spawn(m, uID);
            }
        }
        catch(IllegalArgumentException ex)
        {
            engine.showEndGame(ex.getMessage());
        } catch (DuplicateSpawnException | InvalidObjectException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @Override
    public void drawNewMinions(Minion m, int uID) {
        try{
            // Check if minions isn't empty
            if(m == null)
            {
                throw new IllegalArgumentException("Nothing to draw because modules are empty");
            }
            else{
                // Minions isn't empty, draw them
                if(m.getOwnerID() == uID && m.getHealth() > 0){
                    m.setPosition(new Point(1366 - m.getPosition().x, m.getPosition().y));
                }else{
                    m.setPosition(m.getPosition());
                }
                engine.spawn(m, uID);
            }
            
        }
        catch(IllegalArgumentException ex)
        {
            engine.showEndGame(ex.getMessage());
        } catch (DuplicateSpawnException | InvalidObjectException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @Override
    public void drawNewSpells(Effect effect, List<ITargetable> targets, int uID) {
        try{
            // Check if spells isn't empty
            if(targets == null || targets.size() < 1)
            {
                throw new IllegalArgumentException("Nothing to draw because modules are empty");
            }
            
            // Modules isn't empty, draw them
            for(ITargetable t : targets)
            {
                Minion m = (Minion)t;
                int currentID = account.getUID();
                FXMLDocumentController.Window window = uID == currentID ? FXMLDocumentController.Window.DOWN : FXMLDocumentController.Window.TOP;
                engine.drawEffect(effect, t, window);
            }
                
        }
        catch(IllegalArgumentException ex)
        {
            engine.showEndGame(ex.getMessage());
        }
    }

    @Override
    public void redrawCurrentModules(List<Module> modules, int uID) {
        try{
            // Check if modules isn't empty
            if(modules == null)
            {
                throw new IllegalArgumentException("Nothing to draw because modules are empty");
            }
            
            // Modules isn't empty, draw them
            for(Module m : modules)
            {
                engine.update(uID);
            }
        }
        catch(IllegalArgumentException ex)
        {
            engine.showEndGame(ex.getMessage());
        }
    }

    @Override
    public void redrawCurrentMinions(List<Minion> minions, int uID) {
        try{
            // Check if modules isn't empty
            if(minions == null)
            {
                throw new IllegalArgumentException("Nothing to draw because modules are empty");
            }
            
            // Modules isn't empty, draw them
            ArrayList<Node> nodes = engine.getAllNodes();
            for(Node n : nodes)
            {
                if(n instanceof MinionImage)
                {
                    MinionImage mi = (MinionImage)n;
                    Minion minion = mi.getMinion();
                    for(Minion m : minions)
                    {
                        if(minion.getMinionID() == m.getMinionID())
                        {
                            if(minion.getOwnerID() == uID && minion.getHealth() > 0){
                                minion.setPosition(new Point(1366 - m.getPosition().x, m.getPosition().y));
                                minion.setHealth(m.getHealth());
                            }else{
                                minion.setPosition(m.getPosition());
                                minion.setHealth(m.getHealth());
                            }
                        }
                    }
                }
            }
            engine.update(uID);
        }
        catch(IllegalArgumentException ex)
        {
            engine.showEndGame(ex.getMessage());
        }
    }

    @Override
    public void redrawCurrentSpells(List<Spell> spells, int uID) {
        try{
            // Check if spells isn't empty
            if(spells == null)
            {
                throw new IllegalArgumentException("Nothing to draw because modules are empty");
            }
            
            // Spells isn't empty, draw them
            for(Spell m : spells)
            {
                engine.spawn(m, uID);
            }
        }
        catch(IllegalArgumentException ex)
        {
            engine.showEndGame(ex.getMessage());
        } catch (DuplicateSpawnException | InvalidObjectException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @Override
    public void deleteCurrentModules(List<Module> modules, int uID) {
        try{
            if(modules == null)
            {
                throw new IllegalArgumentException("Nothing to despawn. (modules)");
            }
            
            for(Module m : modules)
            {
                engine.deSpawn(m, uID);
            }
            
        }catch(IllegalArgumentException | InvalidObjectException ex)
        {
            System.out.print(ex.getMessage());
        }
    }

    @Override
    public void deleteMinion(Minion minion, int uID) {
        try{
            if(minion == null)
            {
                throw new IllegalArgumentException("Nothing to despawn. (minions)");
            }else{
                engine.deSpawn(minion, uID);
            }
        }
        catch(IllegalArgumentException ex)
        {
            System.out.print(ex.getMessage());
        } catch (InvalidObjectException ex) {
            Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    public void deleteCurrentSpells(List<Spell> spells, int uID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateLabels(int wavenumber, String playernamea, double healthplayera, double bitcoinsplayera, String playernameb, double healthplayerb) {
        engine.drawLabels(wavenumber, playernamea, healthplayera, bitcoinsplayera, playernameb, healthplayerb);
    }
    
    /**
    * Initialize a SoftwareInjector object, add the object to the modules field, lower the player bitcoins and return a list of spells that became available
     * @param injector The object the player want's to build
    * @return The object that the player has build
     * @throws NotEnoughBitcoinsException when the player doesn't have enough bitcoins to build the module
    */  
    public SoftwareInjector buildSoftwareInjector(SoftwareInjectorTemplate injector, Point position, int width, int height) throws NotEnoughBitcoinsException, InvalidModuleEnumException, RemoteException{
        return (SoftwareInjector)update.buildModule(sessionKey, account.getUID(), injector, position, width, height);
        
        
    }
    
    /**
     * Retrieve a SoftwareInjector object from the modules field and call the Upgrade method from inside the class
     * @param injector The object the player want's to upgrade
     * @throws NotEnoughBitcoinsException when the player doesn't have enough bitcoins to upgrade the object
     * @throws NoUpgradeAllowedException when the given parameter is already at it's highest level.
     */
    public void upgradeSoftwareInjector(SoftwareInjector injector) throws NotEnoughBitcoinsException, NoUpgradeAllowedException, RemoteException
    {
        update.upgradeModule(sessionKey, account.getUID(), injector);
        
    }
    
    /**
     * Initialize a BitcoinMiner object, lower the player bitcoins and add the object to the modules field
     * @param miner The object the client want's to build
     * @return an BitcoinMiner object that the player has build.
     * @throws NotEnoughBitcoinsException when the player doesn't have enough bitcoins to build the BitcoinMiner
     */
    public BitcoinMiner buildBitcoinMiner(BitCoinMinerTemplate miner, Point position, int width, int height) throws NotEnoughBitcoinsException, RemoteException{
        return (BitcoinMiner)update.buildModule(sessionKey, account.getUID(), miner, position, width, height);
    }
    
    /**
     * Retrieve a BitcoinMiner object from the modules field, lower the player bitcoins and call the Upgrade method from inside the class
     * @param miner The object the client want's to upgrade
     * @throws NotEnoughBitcoinsException when the player doesn't have enough bitcoins to upgrade the object
     * @throws NoUpgradeAllowedException when the given parameter is already at it's highest level.
     */
    public void upgradeBitcoinMiner(BitcoinMiner miner) throws NotEnoughBitcoinsException, NoUpgradeAllowedException, RemoteException {
        update.upgradeModule(sessionKey, account.getUID(), miner);
    }
    
    /**
     * Initialize a CpuUpgrade object and add the object to the modules field
     * @param cpu The object the client want's to build
     * @return an CPUUpgrade object that the player has build.
     * @throws NotEnoughBitcoinsException when the player hasn't enough bitcoins to build the CPUUpgrade object
     */
    public CPUUpgrade buildCPUUpgrade(CPUUpgradeTemplate cpu, Point position, int width, int height) throws NotEnoughBitcoinsException, RemoteException{
       return (CPUUpgrade)update.buildModule(sessionKey, account.getUID(), cpu, position, width, height);
    }
    
    /**
     * Upgrade the CPUUpgrade instance to a higher level.
     * @param cpu The object the client want's to upgrade
     * @throws NotEnoughBitcoinsException when the client doesn't have enough bitcoins to upgrade the module
     * @throws NoUpgradeAllowedException when the given parameter is already at it's highest level.
     */
    public void upgradeCPUUpgrade(CPUUpgrade cpu) throws NotEnoughBitcoinsException, NoUpgradeAllowedException, RemoteException {
        update.upgradeModule(sessionKey, account.getUID(), cpu);
    }
    
    /**
     * Build a new Defense module
     * @param defense The module the client want's to build
     * @return An defense object that the player has build.
     * @throws NotEnoughBitcoinsException when the client doesn't have enough bitcoins to build the module
     */
    public Defense buildDefense(DefenseTemplate defense, Point position, int width, int height) throws NotEnoughBitcoinsException, RemoteException{
       return (Defense)update.buildModule(sessionKey, account.getUID(), defense, position, width, height);
    }
    
    /**
     * Upgrade the Defense instance to a higher level
     * @param defense The Defence object the client want's to upgrade
     * @param effect An effect the player want's to give to the module when it's been upgraded.
     * @throws NotEnoughBitcoinsException when the client doesn't have enough bitcoins to upgrade the defense object
     * @throws NoUpgradeAllowedException when the given parameter is already at it's highest level.
     */
    public void upgradeDefense(Defense defense, Effect effect) throws NotEnoughBitcoinsException, NoUpgradeAllowedException, RemoteException{
        update.upgradeModule(sessionKey, account.getUID(), defense);
    }	

    @Override
    public void startGame() {
        
    }

    public void initialize(FXMLDocumentController controller) {
        
        engine = GraphicsEngine.getInstance().initialize(controller);
        
        
        // Initialize all GUI buttons
        ImageView sniperav = (ImageView)engine.getNode("buildSniperAV",null);
        sniperav.setOnMouseClicked(new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                st = engine.drawModuleSpawnTarget(ModuleName.SNIPER_ANTIVIRUS);
                
                engine.getScene(Window.DOWN).setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>(){
                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        st.setX(event.getX() - (st.getImage().getWidth()/2));
                        st.setY(event.getY() - (st.getImage().getHeight()/2));
                    }
                });
                // Set a listener for a second click to occur
                st.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        
                        MouseButton m = event.getButton();
                        if (m == MouseButton.PRIMARY){
                            Point position = new Point((int)event.getX(), (int)event.getY());
                                try {
                                    int x = (int)st.getX();
                                    int y = (int)st.getY();
                                    if(!engine.isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                    {   
                                        Defense defense = buildDefense(Data.DEFAULT_MODULE_DEFENSE_SNIPER_1, position, 50, 50);
                                        engine.spawn(defense, account.getUID());
                                        engine.deSpawn(st, account.getUID());
                                    }
                                    else
                                    {
                                        engine.showError("You are not allowed to build here.");
                                    }
                                } catch (NotEnoughBitcoinsException ex) {
                                    engine.showError(ex.getMessage());
                                } catch (InvalidObjectException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (RemoteException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DuplicateSpawnException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            } 
                        } else {
                            System.out.println("dd");
                            try {
                                engine.deSpawn(st, account.getUID());
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
            }
            
        });
        sniperav.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                try {
                    Module module = new Defense(Data.DEFAULT_MODULE_DEFENSE_SNIPER_1, null, 0,0);
                    engine.drawModuleStats(module);
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        sniperav.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                engine.drawModuleStats(null);
            }
        });
        
        ImageView scaleav = (ImageView)engine.getNode("buildScaleAV",null);
        scaleav.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
 
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                st = engine.drawModuleSpawnTarget(ModuleName.SCALE_ANTIVIRUS);
                engine.getScene(Window.DOWN).setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>(){
                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        st.setX(event.getX() - (st.getImage().getWidth()/2));
                        st.setY(event.getY() - (st.getImage().getHeight()/2));
                    }
                });
                // Set a listener for a second click to occur
                st.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton m = event.getButton();
                        if (m == MouseButton.PRIMARY){
                            Point position = new Point((int)event.getX(), (int)event.getY());
                            try {
                                try {
                                    int x = (int)st.getX();
                                    int y = (int)st.getY();
                                    if(!engine.isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                    {
                                        Defense defense = buildDefense(Data.DEFAULT_MODULE_DEFENSE_SCALE_1, position, 50, 50);
                                        engine.spawn(defense, account.getUID());
                                        engine.deSpawn(st, account.getUID());
                                    }
                                    else{
                                        engine.showError("You are not allowed to build here.");
                                    }
                                } catch (InvalidObjectException ex) {
                                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (DuplicateSpawnException ex) {
                                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                                } 
                            } catch (NotEnoughBitcoinsException ex) {
                                engine.showError(ex.getMessage());
                            }
                        } else {
                            System.out.println("dd");
                            try {
                                engine.deSpawn(st, account.getUID());
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                
                });
            }
            
        });
        scaleav.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                try {
                    Module module = new Defense(Data.DEFAULT_MODULE_DEFENSE_SCALE_1, null, 0,0);
                    engine.drawModuleStats(module);
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        scaleav.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                engine.drawModuleStats(null);
            }
        });
        
        ImageView bottlecapav = (ImageView)engine.getNode("buildBottlecapAV",null);
        bottlecapav.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
 
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                st = engine.drawModuleSpawnTarget(ModuleName.BOTTLECAP_ANTIVIRUS);
                engine.getScene(Window.DOWN).setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>(){
                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        st.setX(event.getX() - (st.getImage().getWidth()/2));
                        st.setY(event.getY() - (st.getImage().getHeight()/2));
                    }
                });
                // Set a listener for a second click to occur
                st.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton m = event.getButton();
                        if (m == MouseButton.PRIMARY){
                            Point position = new Point((int)event.getX(), (int)event.getY());
                            try {
                                try {
                                    int x = (int)st.getX();
                                    int y = (int)st.getY();
                                    if(!engine.isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                    {
                                        Defense defense = buildDefense(Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1, position, 50, 50);
                                        engine.spawn(defense, account.getUID());
                                        engine.deSpawn(st, account.getUID());
                                    }
                                    else
                                    {
                                        engine.showError("You are not allowed to build here.");
                                    }
                                } catch (InvalidObjectException ex) {
                                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (DuplicateSpawnException ex) {
                                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (NotEnoughBitcoinsException ex)
                            {
                                engine.showError(ex.getMessage());
                            }
                        } else {
                            System.out.println("dd");
                            try {
                                engine.deSpawn(st, account.getUID());
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                
                });
            }
            
        });
        bottlecapav.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                try {
                    Module module = new Defense(Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1, null, 0,0);
                    engine.drawModuleStats(module);
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bottlecapav.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                engine.drawModuleStats(null);
            }
        });
        
        ImageView muscleav = (ImageView)engine.getNode("buildMuscleAV",null);
        muscleav.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
 
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                st = engine.drawModuleSpawnTarget(ModuleName.MUSCLE_ANTIVIRUS);
                engine.getScene(Window.DOWN).setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>(){
                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        st.setX(event.getX() - (st.getImage().getWidth()/2));
                        st.setY(event.getY() - (st.getImage().getHeight()/2));
                    }
                });
                // Set a listener for a second click to occur
                st.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton m = event.getButton();
                        if (m == MouseButton.PRIMARY){
                            Point position = new Point((int)event.getX(), (int)event.getY());
                            try {
                                try {
                                    int x = (int)st.getX();
                                    int y = (int)st.getY();
                                    if(!engine.isPointInNode(x, y, (int)st.getImage().getWidth(), (int)st.getImage().getHeight()))
                                    {
                                        Defense defense = buildDefense(Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1, position, 50, 50);
                                        engine.spawn(defense, account.getUID());
                                        engine.deSpawn(st, account.getUID());
                                    }
                                    else
                                    {
                                        engine.showError("You are not allowed to build here.");
                                    }
                                } catch (InvalidObjectException ex) {
                                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (DuplicateSpawnException ex) {
                                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (NotEnoughBitcoinsException ex)
                            {
                                engine.showError(ex.getMessage());
                            }
                        } else {
                            System.out.println("dd");
                            try {
                                engine.deSpawn(st, account.getUID());
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                
                });
            }
            
        });
        muscleav.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                try {
                    Module module = new Defense(Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1, null, 0,0);
                    engine.drawModuleStats(module);
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        muscleav.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                engine.drawModuleStats(null);
            }
        });
        
        ImageView bitcoinminer = (ImageView)engine.getNode("buildBitcoinMiner",null);
        bitcoinminer.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Image i = bitcoinminer.getImage();
                Point p = new Point((int)bitcoinminer.getX(),(int)bitcoinminer.getY());
                try {
                    BitcoinMiner miner = buildBitcoinMiner(Data.DEFAULT_MODULE_BITCOINMINER_1,p,(int)i.getWidth(),(int)i.getHeight());
                    if(miner != null){
                        engine.drawBaseModule(ModuleName.BITCOIN_MINER, true);
                    }
                } catch (NotEnoughBitcoinsException ex) {
                    engine.showError("Not enough bitcoins!");
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        bitcoinminer.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                try {
                    BitcoinMiner module = new BitcoinMiner(Data.DEFAULT_MODULE_BITCOINMINER_1,null,0,0);
                    engine.drawModuleStats(module);
                } catch (InvalidModuleEnumException ex) {
                    Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bitcoinminer.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                engine.drawModuleStats(null);
            }
        });
        
        ImageView spellFirewall = (ImageView)engine.getNode("buildFirewallV",null);
        spellFirewall.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                SpellTemplate spell = Data.DEFAULT_SPELL_FIREWALL;
                Ellipse range = engine.drawSpellRange(new Spell(spell));
                engine.getScene(Window.DOWN).setOnMouseMoved(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        range.setCenterX(event.getSceneX());
                        range.setCenterY(event.getSceneY() - (250 + spell.getRange()) - (spell.getRange()/2));
                    }
                
                });
                range.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton m = event.getButton();
                        if (m == MouseButton.PRIMARY){
                            ArrayList<ITargetable> targets = new ArrayList<ITargetable>();
                            Point p = new Point((int)range.getCenterX(), (int)range.getCenterY());
                            try {
                                if(engine.spellAvailable(spell)){
                                    update.executeSpell(sessionKey, account.getUID(), spell, p);
                                    engine.deSpawn(spell, account.getUID());
                                }
                            } catch (RemoteException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else
                        {
                            try {
                                engine.deSpawn(spell, account.getUID());
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                });
            }
            
        });
        
        
        ImageView spellCorrupt = (ImageView)engine.getNode("buildCorruptV",null);
        spellCorrupt.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                SpellTemplate spell = Data.DEFAULT_SPELL_CORRUPT;
                Ellipse range = engine.drawSpellRange(new Spell(spell));
                engine.getScene(Window.DOWN).setOnMouseMoved(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        range.setCenterX(event.getSceneX());
                        range.setCenterY(event.getSceneY() - (250 + spell.getRange()) - (spell.getRange()/2));
                    }
                    
                });
                range.setOnMouseClicked(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton m = event.getButton();
                        if(m == MouseButton.PRIMARY)
                        {
                            ArrayList<ITargetable> targets = new ArrayList<ITargetable>();
                            Point p = new Point((int)range.getCenterX(), (int)range.getCenterY());
                            try {
                                if(engine.spellAvailable(spell)){
                                    update.executeSpell(sessionKey, account.getUID(), spell, p);
                                    engine.deSpawn(spell, account.getUID());
                                }
                            } catch (RemoteException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else
                        {
                            try {
                                engine.deSpawn(spell, account.getUID());
                            } catch (InvalidObjectException ex) {
                                Logger.getLogger(ClientAdapter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
            }
            
        });
        
    }
    @Override
    public void fire(int moduleid, long minionid){
        Defense source = null;
        Minion target = null;
        
        ArrayList<Node> nodes = engine.getAllNodes();
            for(Node n : nodes)
            {
                if(n instanceof ModuleImage)
                {
                    ModuleImage moi = (ModuleImage)n;
                    Module m = (Module)moi.getReference();
                    if(m.getModuleID() == moduleid){
                        source = (Defense)m;
                    }
                    
                }
                if(n instanceof MinionImage){
                    MinionImage mii = (MinionImage)n;
                    Minion m = (Minion)mii.getReference();
                    if(m.getMinionID() == minionid){
                        target = m;
                    }
                }
            }
            if(source != null && target != null){
                engine.drawAttackLine(source, target);
            }
            
            
    }
}
