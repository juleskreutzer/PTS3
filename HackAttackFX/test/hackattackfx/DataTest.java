/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.Data.*;
import hackattackfx.enums.DefenseType;
import hackattackfx.enums.Effect;
import hackattackfx.enums.MinionType;
import hackattackfx.enums.ModuleName;
import hackattackfx.enums.SpellName;
import hackattackfx.enums.SpellType;
import hackattackfx.exceptions.InvalidDefenseTypeException;
import hackattackfx.exceptions.InvalidEffectException;
import hackattackfx.exceptions.InvalidMinionTypeException;
import hackattackfx.exceptions.InvalidSpellNameException;
import hackattackfx.templates.BitCoinMinerTemplate;
import hackattackfx.templates.CPUUpgradeTemplate;
import hackattackfx.templates.DefenseTemplate;
import hackattackfx.templates.MinionTemplate;
import hackattackfx.templates.SoftwareInjectorTemplate;
import hackattackfx.templates.SpellTemplate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.http.HTTPException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author juleskreutzer
 */
public class DataTest {
    
    private static MinionTemplate DEFAULT_BYTE_TEST;
    private static MinionTemplate DEFAULT_KILOBYTE_TEST;
    private static MinionTemplate DEFAULT_MEGABYTE_TEST;
    private static MinionTemplate DEFAULT_GIGABYTE_TEST;
    private static MinionTemplate DEFAULT_TERABYTE_TEST;
    private static MinionTemplate DEFAULT_PETABYTE_TEST;
    private static SpellTemplate DEFAULT_SPELL_CORRUPT_TEST;
    private static SpellTemplate DEFAULT_SPELL_ENCRYPT_TEST;
    private static SpellTemplate DEFAULT_SPELL_DISRUPT_TEST;
    private static SpellTemplate DEFAULT_SPELL_LOCKDOWN_TEST;
    private static SpellTemplate DEFAULT_SPELL_FIREWALL_TEST;
    private static SpellTemplate DEFAULT_SPELL_VIRUSSCAN_TEST;
    private static BitCoinMinerTemplate DEFAULT_MODULE_BITCOINMINER_1_TEST;
    private static BitCoinMinerTemplate DEFAULT_MODULE_BITCOINMINER_2_TEST;
    private static BitCoinMinerTemplate DEFAULT_MODULE_BITCOINMINER_3_TEST;
    private static CPUUpgradeTemplate DEFAULT_MODULE_CPUUPGRADE_1_TEST;
    private static CPUUpgradeTemplate DEFAULT_MODULE_CPUUPGRADE_2_TEST;
    private static CPUUpgradeTemplate DEFAULT_MODULE_CPUUPGRADE_3_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_BOTTLECAP_1_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_BOTTLECAP_2_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_BOTTLECAP_3_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_MUSCLE_1_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_MUSCLE_2_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_MUSCLE_3_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_SCALE_1_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_SCALE_2_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_SCALE_3_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_SNIPER_1_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_SNIPER_2_TEST;
    private static DefenseTemplate DEFAULT_MODULE_DEFENSE_SNIPER_3_TEST;
    private static SoftwareInjectorTemplate DEFAULT_MODULE_SOFTWAREINJECTOR_1_TEST;
    private static SoftwareInjectorTemplate DEFAULT_MODULE_SOFTWAREINJECTOR_2_TEST;
    private static SoftwareInjectorTemplate DEFAULT_MODULE_SOFTWAREINJECTOR_3_TEST;
    
    public DataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    // Default method to get the data from the API
    private JSONArray sendGet(String url) throws IOException, HTTPException
    {
        try{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"); // Do as if you're using Chrome 41 on Windows 7.
        con.setRequestMethod("GET");
        con.setRequestProperty("X_AUTH_TOKEN", "test"); // API checks access based on x-auth-token header
        
        int responseCode = con.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            String response;
 
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine + "\n");
            }
            in.close();
            response = sb.toString();
 
            // print result
            return new JSONArray(response);
        }
        else if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED)
        {
            throw new HTTPException(responseCode);
        }
        }
        catch(HTTPException ex)
        {
            System.out.print("HTTP ERROR: " + ex.getStatusCode());
            Logger.getLogger(DataTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IOException ex)
        {
            Logger.getLogger(DataTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void createMinions(JSONArray minions) throws InvalidMinionTypeException
    {
        for(int i = 0; i < minions.length(); i++)
        {
            JSONObject obj = minions.getJSONObject(i);
            //System.out.print(obj.names());
            
            double damage = obj.getDouble("damage");
            double health = obj.getDouble("hitpoints");
            double speed = obj.getDouble("speed");
            int e = obj.getInt("encrypted");
            boolean encrypted = false;
            if(e == 1)
            {
                encrypted = true;
            }
            double reward = obj.getDouble("reward");
            MinionType type;

            switch(obj.getString("name"))
            {
                case "Byte":
                    type = MinionType.b;
                    DEFAULT_BYTE_TEST = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                case "KiloByte":
                    type = MinionType.kb;
                    DEFAULT_KILOBYTE_TEST = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                case "MegaByte":
                    type = MinionType.mb;
                    DEFAULT_MEGABYTE_TEST = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                case "GigaByte":
                    type = MinionType.gb;
                    DEFAULT_GIGABYTE_TEST = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                case "TeraByte":
                    type = MinionType.tb;
                    DEFAULT_TERABYTE_TEST = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                case "PetaByte":
                    type = MinionType.pb;
                    DEFAULT_PETABYTE_TEST = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                default:
                    throw new InvalidMinionTypeException();
            }
        }
    }
    
    private void createSpells(JSONArray spells) throws InvalidSpellNameException
    {
        for(int i = 0; i < spells.length(); i++)
        {
            JSONObject obj = spells.getJSONObject(i);
            
            int cooldown = obj.getInt("cooldown");
            String name = obj.getString("name");
            int range = obj.getInt("defense_range");
            int requiredLevel = obj.getInt("requiredLevel");
            String typeString = obj.getString("type");
            SpellName spellName;
            SpellType type;
            
            switch(name)
            {
                case "Corrupt":
                    spellName = SpellName.CORRUPT;
                    
                    if(typeString == "attack")
                    {
                        type = SpellType.ATTACK;
                    }
                    else
                    {
                        type = SpellType.DEFENSE;
                    }
                    DEFAULT_SPELL_CORRUPT_TEST = new SpellTemplate(spellName, range, type, cooldown, requiredLevel);
                    break;
                case "Encrypt":
                    spellName = SpellName.ENCRYPT;
                    if(typeString == "attack")
                    {
                        type = SpellType.ATTACK;
                    }
                    else
                    {
                        type = SpellType.DEFENSE;
                    }
                    DEFAULT_SPELL_ENCRYPT_TEST = new SpellTemplate(spellName, range, type, cooldown, requiredLevel);
                    break;
                case "Disrupt":
                    spellName = SpellName.DISRUPT;
                    if(typeString == "attack")
                    {
                        type = SpellType.ATTACK;
                    }
                    else
                    {
                        type = SpellType.DEFENSE;
                    }
                    DEFAULT_SPELL_DISRUPT_TEST = new SpellTemplate(spellName, range, type, cooldown, requiredLevel);
                    break;
                case "Lock-down":
                    spellName = SpellName.LOCKDOWN;
                    if(typeString == "attack")
                    {
                        type = SpellType.ATTACK;
                    }
                    else
                    {
                        type = SpellType.DEFENSE;
                    }
                    DEFAULT_SPELL_LOCKDOWN_TEST = new SpellTemplate(spellName, range, type, cooldown, requiredLevel);
                    break;
                case "Firewall":
                    spellName = SpellName.FIREWALL;
                    if(typeString == "attack")
                    {
                        type = SpellType.ATTACK;
                    }
                    else
                    {
                        type = SpellType.DEFENSE;
                    }
                    DEFAULT_SPELL_FIREWALL_TEST = new SpellTemplate(spellName, range, type, cooldown, requiredLevel);
                    break;
                case "Virus-scan":
                    spellName = SpellName.VIRUSSCAN;
                    if(typeString == "attack")
                    {
                        type = SpellType.ATTACK;
                    }
                    else
                    {
                        type = SpellType.DEFENSE;
                    }
                    DEFAULT_SPELL_VIRUSSCAN_TEST = new SpellTemplate(spellName, range, type, cooldown, requiredLevel);
                    break;
                default:
                    throw new InvalidSpellNameException();
            }
            
        }
        
    }
    
    private void createModules(JSONArray modules) throws IOException, InvalidDefenseTypeException, InvalidEffectException
    {
        for(int i = 0; i < modules.length(); i++)
        {
            JSONObject obj = modules.getJSONObject(i);
            
            String moduleClass= obj.getString("class");
            String description = obj.getString("description");
            int frequency = obj.getInt("frequency");
            double cost = obj.getDouble("price");
            int range = obj.getInt("range");
            int tier = obj.getInt("tier");
            String name = obj.getString("name");
            ModuleName moduleName;
            double damage = obj.getDouble("damage");
            String type = obj.getString("type");
            String effectString = obj.getString("effect");
            DefenseType defenseType;
            Effect effect;
            String desc = obj.getString("description");
            
            if(cost < 0)
                throw new IllegalArgumentException("Cost must be higher than 0");
            if(range < 0)
                throw new IllegalArgumentException("Range must be higher than 0");
            if(damage < 0)
                throw new IllegalArgumentException("Damage must be higher than 0");
            
            switch(name)
            {
                case "Bitcoin Miner":
                    switch(tier)
                    {
                        case 1:
                            moduleName = ModuleName.BITCOIN_MINER;
                            DEFAULT_MODULE_BITCOINMINER_1_TEST = new BitCoinMinerTemplate(cost, tier, moduleName, frequency, desc);
                            break;
                        case 2:
                            moduleName = ModuleName.BITCOIN_MINER;
                            DEFAULT_MODULE_BITCOINMINER_2_TEST = new BitCoinMinerTemplate(cost, tier, moduleName, frequency, desc);
                            break;
                        case 3:
                            moduleName = ModuleName.BITCOIN_MINER;
                            DEFAULT_MODULE_BITCOINMINER_3_TEST = new BitCoinMinerTemplate(cost, tier, moduleName, frequency, desc);
                            break;
                        default:
                            throw new IOException("Tier not recognized (Bitcoin Miner)");
                    }
                    break;
                case "CPU Upgrade":
                    switch(tier)
                    {
                        case 1:
                            moduleName = ModuleName.CPU_UPGRADE;
                            DEFAULT_MODULE_CPUUPGRADE_1_TEST = new CPUUpgradeTemplate(cost, tier, moduleName, frequency, desc);
                            break;
                        case 2:
                            moduleName = ModuleName.CPU_UPGRADE;
                            DEFAULT_MODULE_CPUUPGRADE_2_TEST = new CPUUpgradeTemplate(cost, tier, moduleName, frequency, desc);
                            break;
                        case 3:
                            moduleName = ModuleName.CPU_UPGRADE;
                            DEFAULT_MODULE_CPUUPGRADE_3_TEST = new CPUUpgradeTemplate(cost, tier, moduleName, frequency, desc);
                            break;
                        default:
                            throw new IOException("Tier not recognized (CPU Upgrade)");
                    }
                    break;
                case "Software Injector":
                    switch(tier)
                    {
                        case 1:
                            moduleName = ModuleName.SOFTWARE_INJECTOR;
                            DEFAULT_MODULE_SOFTWAREINJECTOR_1_TEST = new SoftwareInjectorTemplate(cost, tier, moduleName, desc);
                            break;
                        case 2:
                            moduleName = ModuleName.SOFTWARE_INJECTOR;
                            DEFAULT_MODULE_SOFTWAREINJECTOR_2_TEST = new SoftwareInjectorTemplate(cost, tier, moduleName, desc);
                            break;
                        case 3:
                            moduleName = ModuleName.SOFTWARE_INJECTOR;
                            DEFAULT_MODULE_SOFTWAREINJECTOR_3_TEST = new SoftwareInjectorTemplate(cost, tier, moduleName, desc);
                            break;
                        default:
                            throw new IOException("Tier not recognized (Software Injector)");
                    }
                    break;
                case "Bottlecap Antivirus":
                    switch(tier)
                    {
                        case 1:
                            moduleName = ModuleName.BOTTLECAP_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_BOTTLECAP_1_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        case 2:
                            moduleName = ModuleName.BOTTLECAP_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_BOTTLECAP_2_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        case 3:
                            moduleName = ModuleName.BOTTLECAP_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_BOTTLECAP_3_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        default:
                            throw new IOException("Tier not recognized (Bottlecap Antivirus)");
                    }
                    break;
                case "Muscle Antivirus":
                    switch(tier)
                    {
                        case 1:
                            moduleName = ModuleName.MUSCLE_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_MUSCLE_1_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        case 2:
                            moduleName = ModuleName.MUSCLE_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_MUSCLE_2_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        case 3:
                            moduleName = ModuleName.MUSCLE_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_MUSCLE_3_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        default:
                            throw new IOException("Tier not recognized (Muscle Antivirus)");
                    }
                break;
                case "Scale Antivirus":
                    switch(tier)
                    {
                        case 1:
                            moduleName = ModuleName.SCALE_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_SCALE_1_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        case 2:
                            moduleName = ModuleName.MUSCLE_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_SCALE_2_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        case 3:
                            moduleName = ModuleName.MUSCLE_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_SCALE_3_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        default:
                            throw new IOException("Tier not recognized (Scale Antivirus)");                            
                    }
                    break;
                case "Sniper Antivirus":
                    switch(tier)
                    {
                        case 1:
                            moduleName = ModuleName.SNIPER_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_SNIPER_1_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        case 2:
                            moduleName = ModuleName.SNIPER_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_SNIPER_2_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        case 3:
                            moduleName = ModuleName.SNIPER_ANTIVIRUS;
                            switch(type)
                            {
                                case "CHEAP": 
                                    defenseType = DefenseType.CHEAP;
                                    break;
                                case "RANGE":
                                    defenseType = DefenseType.RANGE;
                                    break;
                                case "BALANCED":
                                    defenseType = DefenseType.BALANCED;
                                    break;
                                case "STRONG":
                                    defenseType = DefenseType.STRONG;
                                    break;
                                case "":
                                    defenseType = null;
                                    break;
                                default:
                                    throw new InvalidDefenseTypeException();
                                                                   
                            }
                            
                            switch(effectString)
                            {
                                case "SLOWED":
                                    effect = Effect.SLOWED;
                                    break;
                                case "POISENED":
                                    effect = Effect.POISENED;
                                    break;
                                case "SPASH":
                                    effect = Effect.POISENED;
                                    break;
                                case "DECRYPTED":
                                    effect = Effect.DECRYPTED;
                                    break;
                                case "":
                                    effect = null;
                                    break;
                                default:
                                    throw new InvalidEffectException();
                            }
                            DEFAULT_MODULE_DEFENSE_SNIPER_3_TEST = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
                            break;
                        default:
                            throw new IOException("Tier not recognized (Sniper Antivirus)");
                    }
                    break;
                default:
                    throw new IOException(String.format("Module name %s not recognized.", name));
            }
        }
        
    }
    
    @Test
    public void TestBicoinTemplateConstructor() throws IOException, InvalidMinionTypeException, InvalidSpellNameException, InvalidDefenseTypeException, InvalidEffectException
    {
        Data data = new Data(new Data.UpdateProgress() {
                    
                    @Override
                    public void update(double value) {
                        
                    }
                });
        String ModuleURLBM = "https://api.nujules.nl/module/money";  
        JSONArray json = this.sendGet(ModuleURLBM);
        this.createModules(json);
        assertEquals(Data.DEFAULT_MODULE_BITCOINMINER_1.getValuePerSecond(), DEFAULT_MODULE_BITCOINMINER_1_TEST.getValuePerSecond());
        assertEquals(Data.DEFAULT_MODULE_BITCOINMINER_1.getCost(), DEFAULT_MODULE_BITCOINMINER_1_TEST.getCost(), 0);
        assertEquals(Data.DEFAULT_MODULE_BITCOINMINER_1.getLevel(), DEFAULT_MODULE_BITCOINMINER_1_TEST.getLevel());
        assertEquals(Data.DEFAULT_MODULE_BITCOINMINER_1.getModuleName(), DEFAULT_MODULE_BITCOINMINER_1_TEST.getModuleName());
        assertEquals(Data.DEFAULT_MODULE_BITCOINMINER_1.getDisplayName(), DEFAULT_MODULE_BITCOINMINER_1_TEST.getDisplayName());  
        
        JSONArray jsonWrongClass = json;
        
        try{
            JSONArray temp = new JSONArray();
            for(int i = 0; i < jsonWrongClass.length(); ++i)
            {
                JSONObject obj = jsonWrongClass.getJSONObject(i);
                
                obj.remove("class");
                obj.append("class", "moneie");
                
                temp.put(obj);
            }
            
            this.createModules(temp);
        }
        catch(IOException ex)
        {
            
        }
    }
}
