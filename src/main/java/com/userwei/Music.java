package com.userwei;
import java.io.*;
import javax.sound.sampled.*;

public class Music{
    AudioInputStream audioStream;
    AudioFormat format;
    File audioFile;
    DataLine.Info info;
    long clipTime;
    Clip clip;

    public Music(String s) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        // audioFile = new File("src/main/java/tw/userwei/Audio/" + s);
        InputStream is = this.getClass().getResourceAsStream("Audio/" + s);
        BufferedInputStream buffer = new BufferedInputStream(is);
        audioStream = AudioSystem.getAudioInputStream(buffer);
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
            // audioStream.close();
        }catch(Exception e){
            e.printStackTrace();
        } 
    }

    public void resetAudioStream(){
        try{
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            this.clip = AudioSystem.getClip();
            clip.open(audioStream);
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
}