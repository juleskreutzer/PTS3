/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx.interfaces;

/**
 *
 * @author juleskreutzer
 */
public interface IMoveable {
    void move(double deltaTime);
    Object getCollision();
}
