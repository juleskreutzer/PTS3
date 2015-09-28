/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class Data {
    public final static MinionTemplate DEFAULT_BYTE = new MinionTemplate(1,1,1,false,1);
    public final static MinionTemplate DEFAULT_KILOBYTE = new MinionTemplate(2,2,2,false,2);
    public final static MinionTemplate DEFAULT_MEGABYTE = new MinionTemplate(3,3,3,true,3);
    public final static MinionTemplate DEFAULT_GIGABYTE = new MinionTemplate(4,4,4,false,4);
    public final static MinionTemplate DEFAULT_TERABYTE = new MinionTemplate(5,5,5,true,5);
}
