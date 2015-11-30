/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.nio.file.Paths;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Bart van Keersop
 */
public class SoundEngine {
    
    private static SoundEngine instance;
    
    public SoundEngine(){
        instance = this;
    }
    
    public static SoundEngine getInstance(){
        return instance == null ? new SoundEngine() : instance;
    }
    
    public void playMinionDeath(){
        Media hit = new Media(Paths.get("C:\\Users\\Bart van Keersop\\Documents\\GitHub\\PTS3\\HackAttackFX\\src\\hackattackfx\\resources\\interface\\Sounds\\Fireball.mp3").toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
}
