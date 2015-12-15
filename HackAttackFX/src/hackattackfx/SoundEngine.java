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
    private Media themeSong;
    private Media machineGun;
    private Media shotGun;
    private Media fireBall; 
    private MediaPlayer themeSongPlayer;
    private MediaPlayer bottleCapPlayer;
    private MediaPlayer musclePlayer;
    private MediaPlayer scalePlayer;
    private MediaPlayer sniperPlayer;
    
    public SoundEngine(){
        instance = this;
        themeSong = new Media(Paths.get("C:\\Users\\Bart van Keersop\\Documents\\GitHub\\PTS3\\HackAttackFX\\src\\hackattackfx\\resources\\interface\\Sounds\\Theme.mp3").toUri().toString());
        machineGun = new Media(Paths.get("C:\\Users\\Bart van Keersop\\Documents\\GitHub\\PTS3\\HackAttackFX\\src\\hackattackfx\\resources\\interface\\Sounds\\Machinegun.mp3").toUri().toString());
        shotGun = new Media(Paths.get("C:\\Users\\Bart van Keersop\\Documents\\GitHub\\PTS3\\HackAttackFX\\src\\hackattackfx\\resources\\interface\\Sounds\\Shotgun.mp3").toUri().toString()); 
    }
    
    public static SoundEngine getInstance(){
        return instance == null ? new SoundEngine() : instance;
    }
    
    public void playMinionDeath(){
        Media hit = new Media(Paths.get("C:\\Users\\Bart van Keersop\\Documents\\GitHub\\PTS3\\HackAttackFX\\src\\hackattackfx\\resources\\interface\\Sounds\\Fireball.mp3").toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
    
    public void playThemeSong(){
        themeSongPlayer = new MediaPlayer(themeSong);
        themeSongPlayer.play();
        themeSongPlayer.setOnEndOfMedia(new Runnable() {
       public void run() {
         themeSongPlayer.stop();
         themeSongPlayer.play();
       }});
        themeSongPlayer.play();
    }

    public void playFireSound(Defense defense){
        switch(defense.moduleName)
        {
            case BOTTLECAP_ANTIVIRUS:
                bottleCapPlayer = new MediaPlayer(machineGun);
                bottleCapPlayer.play();
                break;
            case MUSCLE_ANTIVIRUS:
        
                break;
            case SCALE_ANTIVIRUS:
                
                break;
            case SNIPER_ANTIVIRUS:
                sniperPlayer = new MediaPlayer(shotGun);
                sniperPlayer.play();
                break;
        }
    }
}
