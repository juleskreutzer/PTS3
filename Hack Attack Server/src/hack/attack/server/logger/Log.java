/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server.logger;

import hack.attack.server.enums.LogState;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author juleskreutzer
 */
public class Log {
    private LogState state;
    private String message;
    private String currentDate;
    
    public Log(LogState state, String message)
    {
        this.state = state;
        this.message = message;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.currentDate = dateFormat.format(date);
        
        new ConsoleHandler(this);
    }
    
    public LogState getLogstate()
    {
        return this.state;
    }
    
    public String getMessage()
    {
        return this.message;
    }
    
    public String getDate()
    {
        return this.currentDate;
    }
    
    @Override
    public String toString(){
        return getDate() + " - STATUS : " + state + " - " + getMessage() + "\n";
    }
}
