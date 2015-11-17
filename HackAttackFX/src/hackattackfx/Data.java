/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.templates.*;
import hackattackfx.enums.*;
import hackattackfx.exceptions.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.ws.http.HTTPException;
import org.json.*;


/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class Data {
    
    public interface UpdateProgress {
        void update(double value);
    }
    
    // Minion Template
    public static MinionTemplate DEFAULT_BYTE;
    public static MinionTemplate DEFAULT_KILOBYTE;
    public static MinionTemplate DEFAULT_MEGABYTE;
    public static MinionTemplate DEFAULT_GIGABYTE;
    public static MinionTemplate DEFAULT_TERABYTE;
    public static MinionTemplate DEFAULT_PETABYTE;
    
    // Spell Templates
    public static SpellTemplate DEFAULT_SPELL_CORRUPT;
    public static SpellTemplate DEFAULT_SPELL_ENCRYPT;
    public static SpellTemplate DEFAULT_SPELL_DISRUPT;
    public static SpellTemplate DEFAULT_SPELL_LOCKDOWN;
    public static SpellTemplate DEFAULT_SPELL_FIREWALL;
    public static SpellTemplate DEFAULT_SPELL_VIRUSSCAN;
    
    /**
     * MODULE TEMPLATES - START
     */
    
    // Bitcoin Miner Templates
    public static BitCoinMinerTemplate DEFAULT_MODULE_BITCOINMINER_1;
    public static BitCoinMinerTemplate DEFAULT_MODULE_BITCOINMINER_2;
    public static BitCoinMinerTemplate DEFAULT_MODULE_BITCOINMINER_3;
    
    // CPU Upgrade Templates
    public static CPUUpgradeTemplate DEFAULT_MODULE_CPUUPGRADE_1;
    public static CPUUpgradeTemplate DEFAULT_MODULE_CPUUPGRADE_2;
    public static CPUUpgradeTemplate DEFAULT_MODULE_CPUUPGRADE_3;
    
    // Defense Templates
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_BOTTLECAP_1;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_BOTTLECAP_2;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_BOTTLECAP_3;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_MUSCLE_1;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_MUSCLE_2;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_MUSCLE_3;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_SCALE_1;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_SCALE_2;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_SCALE_3;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_SNIPER_1;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_SNIPER_2;
    public static DefenseTemplate DEFAULT_MODULE_DEFENSE_SNIPER_3;
    
    // Software Injector Templates
    public static SoftwareInjectorTemplate DEFAULT_MODULE_SOFTWAREINJECTOR_1;
    public static SoftwareInjectorTemplate DEFAULT_MODULE_SOFTWAREINJECTOR_2;
    public static SoftwareInjectorTemplate DEFAULT_MODULE_SOFTWAREINJECTOR_3;
    
    public static String playerAName;
    public static String playerBName;
    
    /**
     * MODULE TEMPLATES - END
     */
    
    private static String urlMinion = "https://api.nujules.nl/minion";
    private static String urlSpell = "https://api.nujules.nl/spell";
    private static String urlModule = "https://api.nujules.nl/module";
    
    public Data(UpdateProgress callback) throws IOException, InvalidMinionTypeException, InvalidSpellNameException, InvalidDefenseTypeException, InvalidEffectException
    {
        /**
         * First we create the JSONArray object by requesting the data from the HackAttack API.
         * Then we call the create method for the objects we want te create which will parse the JSONArray.
         */
        JSONArray minions = sendGet(urlMinion);
        JSONArray spells = sendGet(urlSpell);
        JSONArray modules = sendGet(urlModule);
        
        createMinions(minions);
        callback.update(0.33);
        createSpells(spells);
        callback.update(0.66);
        createModules(modules);
        callback.update(0.99);
    }
    
    public static JSONArray sendPost(String url) throws IOException, HTTPException
    {
        try{
            URL obj = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection)obj.openConnection();
            
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A");
            conn.setRequestProperty("X_AUTH_TOKEN", "test");
            
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.flush();
		wr.close();
                
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpsURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IOException ex)
        {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private JSONArray sendGet(String url) throws IOException, HTTPException
    {
        try{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A");
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
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IOException ex)
        {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
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
                    DEFAULT_BYTE = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                case "KiloByte":
                    type = MinionType.kb;
                    DEFAULT_KILOBYTE = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                case "MegaByte":
                    type = MinionType.mb;
                    DEFAULT_MEGABYTE = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                case "GigaByte":
                    type = MinionType.gb;
                    DEFAULT_GIGABYTE = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                case "TeraByte":
                    type = MinionType.tb;
                    DEFAULT_TERABYTE = new MinionTemplate(type, health, speed, damage, encrypted, reward);
                    break;
                case "PetaByte":
                    type = MinionType.pb;
                    DEFAULT_PETABYTE = new MinionTemplate(type, health, speed, damage, encrypted, reward);
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
            int duration = obj.getInt("duration");
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
                    DEFAULT_SPELL_CORRUPT = new SpellTemplate(spellName, range, type, cooldown, requiredLevel, duration);
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
                    DEFAULT_SPELL_ENCRYPT = new SpellTemplate(spellName, range, type, cooldown, requiredLevel, duration);
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
                    DEFAULT_SPELL_DISRUPT = new SpellTemplate(spellName, range, type, cooldown, requiredLevel, duration);
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
                    DEFAULT_SPELL_LOCKDOWN = new SpellTemplate(spellName, range, type, cooldown, requiredLevel, duration);
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
                    DEFAULT_SPELL_FIREWALL = new SpellTemplate(spellName, range, type, cooldown, requiredLevel, duration);
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
                    DEFAULT_SPELL_VIRUSSCAN = new SpellTemplate(spellName, range, type, cooldown, requiredLevel, duration);
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
            String desc = obj.getString("description");
            DefenseType defenseType;
            Effect effect;
            
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
                            DEFAULT_MODULE_BITCOINMINER_1 = new BitCoinMinerTemplate(cost, tier, moduleName, frequency, desc);
                            break;
                        case 2:
                            moduleName = ModuleName.BITCOIN_MINER;
                            DEFAULT_MODULE_BITCOINMINER_2 = new BitCoinMinerTemplate(cost, tier, moduleName, frequency, desc);
                            break;
                        case 3:
                            moduleName = ModuleName.BITCOIN_MINER;
                            DEFAULT_MODULE_BITCOINMINER_3 = new BitCoinMinerTemplate(cost, tier, moduleName, frequency, desc);
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
                            DEFAULT_MODULE_CPUUPGRADE_1 = new CPUUpgradeTemplate(cost, tier, moduleName, frequency, desc);
                            break;
                        case 2:
                            moduleName = ModuleName.CPU_UPGRADE;
                            DEFAULT_MODULE_CPUUPGRADE_2 = new CPUUpgradeTemplate(cost, tier, moduleName, frequency, desc);
                            break;
                        case 3:
                            moduleName = ModuleName.CPU_UPGRADE;
                            DEFAULT_MODULE_CPUUPGRADE_3 = new CPUUpgradeTemplate(cost, tier, moduleName, frequency, desc);
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
                            DEFAULT_MODULE_SOFTWAREINJECTOR_1 = new SoftwareInjectorTemplate(cost, tier, moduleName, desc);
                            break;
                        case 2:
                            moduleName = ModuleName.SOFTWARE_INJECTOR;
                            DEFAULT_MODULE_SOFTWAREINJECTOR_2 = new SoftwareInjectorTemplate(cost, tier, moduleName, desc);
                            break;
                        case 3:
                            moduleName = ModuleName.SOFTWARE_INJECTOR;
                            DEFAULT_MODULE_SOFTWAREINJECTOR_3 = new SoftwareInjectorTemplate(cost, tier, moduleName, desc);
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
                            DEFAULT_MODULE_DEFENSE_BOTTLECAP_1 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_BOTTLECAP_2 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_BOTTLECAP_3 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_MUSCLE_1 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_MUSCLE_2 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_MUSCLE_3 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_SCALE_1 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_SCALE_2 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_SCALE_3 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_SNIPER_1 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_SNIPER_2 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
                            DEFAULT_MODULE_DEFENSE_SNIPER_3 = new DefenseTemplate(cost, tier, moduleName, damage, range, defenseType, effect, frequency, desc);
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
}
