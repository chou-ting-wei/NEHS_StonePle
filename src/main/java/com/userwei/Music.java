package com.userwei;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class Music{
    AudioInputStream audioStream;
    URL url;
    Clip clip;

    public Music(String s) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        // audioFile = new File("src/main/java/tw/userwei/Audio/" + s);
        url = Music.class.getResource("Audio/" + s); 
        audioStream = AudioSystem.getAudioInputStream(url);
        this.clip = AudioSystem.getClip();
        clip.open(audioStream);
    }

    public void play(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();  
    }

    public void stop(){
        try{
            clip.close();
        }catch(Exception e){
            e.printStackTrace();
        } 
    }

    public void resetAudioStream(){
        try{
            audioStream = AudioSystem.getAudioInputStream(url);
            this.clip = AudioSystem.getClip();
            clip.open(audioStream);
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
}