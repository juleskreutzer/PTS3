/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author juleskreutzer
 */
public class ServerAdapter {
    private ExecutorService tPool;
    private List<Account> availableUsers;
    
    public ServerAdapter()
    {
        availableUsers = new ArrayList<>();
    }
}
