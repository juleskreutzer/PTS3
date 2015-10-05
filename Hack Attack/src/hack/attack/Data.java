/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import hack.attack.enums.MinionType;
import hack.attack.exceptions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;


/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class Data {
    
    public final static MinionTemplate DEFAULT_BYTE = new MinionTemplate(MinionType.b,1,1,1,false,1);
    public final static MinionTemplate DEFAULT_KILOBYTE = new MinionTemplate(MinionType.kb,2,2,2,false,2);
    public final static MinionTemplate DEFAULT_MEGABYTE = new MinionTemplate(MinionType.mb,3,3,3,true,3);
    public final static MinionTemplate DEFAULT_GIGABYTE = new MinionTemplate(MinionType.gb,4,4,4,false,4);
    public final static MinionTemplate DEFAULT_TERABYTE = new MinionTemplate(MinionType.tb,5,5,5,true,5);
<<<<<<< HEAD
    
    private static String urlMinion = "http://api.nujules.nl/minion";
    
    public Data() throws IOException, InvalidMinionTypeException
    {
        JSONArray minions = sendGet(urlMinion);
        
        for(int i = 0; i < minions.length(); i++)
        {
            JSONObject obj = minions.getJSONObject(i);
            double damage = Double.parseDouble(obj.getString("attack"));
            double health = Double.parseDouble(obj.getString("hp"));
            double speed = Double.parseDouble(obj.getString("speed"));
            int e = obj.getInt("encrypted");
            boolean encrypted = false;
            if(e == 1)
            {
                encrypted = true;
            }
            double reward = Double.parseDouble(obj.getString("reward"));
            MinionType type;
            
            switch(obj.getString("name"))
            {
                case "Byte":
                    type = MinionType.b;
                    break;
                case "KiloByte":
                    type = MinionType.kb;
                    break;
                case "MegaByte":
                    type = MinionType.mb;
                    break;
                case "GigaByte":
                    type = MinionType.gb;
                    break;
                case "TeraByte":
                    type = MinionType.tb;
                    break;
                default:
                    throw new InvalidMinionTypeException();
            }
        
        }
            
        
        
    }
    
    private JSONArray sendGet(String url) throws IOException
    {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"); // Do as if you're using Chrome 41 on Windows 7.
        con.setRequestMethod("GET");
        con.setRequestProperty("x-auth-token", "test"); // API checks access based on x-auth-token header
        
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
    
=======
    public final static MinionTemplate DEFAULT_PETABYTE = new MinionTemplate(MinionType.pb,5,5,5,true,5);
>>>>>>> origin/master
}
