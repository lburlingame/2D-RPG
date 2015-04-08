package com.dreamstreet.arpg.sfx;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer{


    Clip clip = null;

    public AudioPlayer(String path) {
        try {
            File file = new File(path);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;

            stream = AudioSystem.getAudioInputStream(file);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            try {
                clip = (Clip)AudioSystem.getLine(info);
            } catch (LineUnavailableException e1) {
                e1.printStackTrace();
            }

            try {
                clip.open(stream);
            } catch (LineUnavailableException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            clip.start();
        }catch(Exception e) {
            //whatevers
        }
    }

    public void stop() {
        clip.stop();
    }
    public void start() {
        clip.start();
    }

}