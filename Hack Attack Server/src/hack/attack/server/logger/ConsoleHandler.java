/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server.logger;

import hack.attack.server.HackAttackServer;
import hack.attack.server.enums.LogState;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author juleskreutzer
 */
public class ConsoleHandler {
    
    private PrintWriter pr = null;
    
    public ConsoleHandler(Log log)
    {
        String state = "";
        
        
        LogState logState = log.getLogstate();
        switch(logState)
        {
            case OK:
                state = "OK";
                break;
            case SUCCESS:
                state = "SUCCESS";
                break;
            case WARNING:
                state = "WARNING";
                break;
            case ERROR:
                state = "ERROR";
                break;
            case INFO:
                state = "INFO";
                break;
        }
        
        try{
            pr = new PrintWriter("serverLog.txt", "UTF-8");
            pr.append(log.getDate() + " - STATUS : " + state + " - " + log.getMessage());
        }
        catch(FileNotFoundException | UnsupportedEncodingException ex)
        {
            HackAttackServer.writeConsole(new Log(LogState.ERROR, ex.toString()));
        }
        finally{
            pr.close();
        }
        
        
    }
    
}
