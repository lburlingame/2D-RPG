package com.dreamstreet.arpg.sfx;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;

/**
 * Created on  / 6/ 0 5.
 */
public class MusicPlayer extends JApplet
{

    private AudioClip song; // Sound player
    private URL songPath; // Sound path

    public MusicPlayer(String filename) {
        try {

            songPath = this.getClass().getResource("res/audio/" + filename);
            song = Applet.newAudioClip(songPath); // Load the Sound
            System.out.println(songPath.toString());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        } // Satisfy the catch
    }

    public void playSound() {
        song.loop(); // Play
    }

    public void stopSound() {
        song.stop(); // Stop
    }

    public void playSoundOnce() {
        song.play(); // Play only once
    }

}
