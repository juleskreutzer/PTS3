/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.server.templates;

import hack.attack.rmi.ModuleTemplate;
import hack.attack.rmi.ModuleName;

/**
 *
 * @author juleskreutzer
 */
public class SoftwareInjectorTemplate extends ModuleTemplate {

    public SoftwareInjectorTemplate(double cost, int level, ModuleName name, String desc) {
        super(cost, level, name, desc);
    }
    
}
