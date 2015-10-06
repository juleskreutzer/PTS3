/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.MinionType;
import hackattackfx.exceptions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import org.json.*;


/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class Data {
    
    public static MinionTemplate DEFAULT_BYTE;
    public static MinionTemplate DEFAULT_KILOBYTE;
    public static MinionTemplate DEFAULT_MEGABYTE;
    public static MinionTemplate DEFAULT_GIGABYTE;
    public static MinionTemplate DEFAULT_TERABYTE;
    public static MinionTemplate DEFAULT_PETABYTE;
    
    private static String urlMinion = "http://api.nujules.nl/minion";
    private static String urlSpell = "http://api.nujules.nl/spell";
    private static String urlModule = "http://api.nujules.nl/module";
    
    public Data() throws IOException, InvalidMinionTypeException
    {
        /**
         * First we create the JSONArray object by requesting the data from the HackAttack API.
         * Then we call the create method for the objects we want te create which will parse the JSONArray.
         */
        JSONArray minions = sendGet(urlMinion);
        JSONArray spells = sendGet(urlSpell);
        JSONArray modules = sendGet(urlModule);
        
        createMinions(minions);
    }
    
    private JSONArray sendGet(String url) throws IOException
    {
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
    
    private void createSpells(JSONArray spells)
    {
        for(int i = 0; i < spells.length(); i++)
        {
            
        }
        
    }
    
    private void createModules(JSONArray modules)
    {
        for(int i = 0; i < modules.length(); i++)
        {
            
        }
        
    }
}
