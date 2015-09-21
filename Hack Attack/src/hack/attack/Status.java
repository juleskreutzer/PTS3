package hack.attack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bart van Keersop
 */
import java.util.Date;
public class Status 
{  
    private String name; //the name of the status (buff/debuff) inflicted.
    private Date endTime; //the time when the status ends.
    private double multiplier; //the multiplier used for the status.
}
