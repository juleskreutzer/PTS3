/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import java.io.Serializable;

/**
 *
 * @author juleskreutzer
 */
public class Account implements Serializable {
    
    /**
     * The uID is a unique identifier for the user that has been used in the database.
     * The uID can be used to store data for the specific user in the database using an API call.
     */
    private final int uID;
    
    /**
     * The username is used by a player to log in. The username is also required to join a custom match.
     */
    private final String username;
    
    /**
     * The displayName will be displayed in-game.
     * The displayName isn't unique (in the database).
     */
    private final String displayName;
    
    /**
     * This is the userScore how it is saved in the database when the user logged in.
     * The userScore is displayer in-game. When a game is finished, this username will <b>not</b> be used
     * to increase the score in the database
     */
    private final int userScore;
    
    public Account(int uID, String username, String displayName, int userScore)
    {
        this.uID = uID;
        this.username = username;
        this.displayName = displayName;
        this.userScore = userScore;
    }
    
    /**
     * Get the unique ID of the user
     * @return unique id of the user
     */
    public int getUID()
    {
        return this.uID;
    }
    
    /**
     * Get the unique username for the logged in user
     * @return unique username for the logged in user
     */
    public String getUsername()
    {
        return this.username;
    }
    
    /**
     * Get the displayname for the logged in user
     * @return displayname for the logged in user
     */
    public String getDisplayName()
    {
        return this.displayName;
    }
    
    /**
     * Get the score for the logged in user as how it was saved in the database when the user logged in
     * @return score of the user
     */
    public int getUserScore()
    {
        return this.userScore;
    }
}
