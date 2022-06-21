package com.userwei;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class Music{
    AudioInputStream audioInputStream;
    URL url;
    Clip clip;

    public Music(String s) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        // audioFile = new File("src/main/java/tw/userwei/Audio/" + s);
        url = Music.class.getResource("Audio/" + s); 
        audioInputStream = AudioSystem.getAudioInputStream(url);
        AudioFormat audioFormat = audioInputStream.getFormat();
        int bufferSize = (int) Math.min(audioInputStream.getFrameLength() * audioFormat.getFrameSize(), Integer.MAX_VALUE); //緩衝大小，如果音訊檔案不大，可以全部存入緩衝空間。這個數值應該要按照用途來決定
        DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, audioFormat, bufferSize);
        clip = (Clip) AudioSystem.getLine(dataLineInfo);
        clip.open(audioInputStream);
    }

    public void play(){
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playOnce(int time){
        clip.start();
        /*
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clip.close();
        */
        if(time == 4000){
            String nowMusicName = GamePanel.musicName.get(GamePanel.nowState);

            if(nowMusicName != "null"){
                if(GamePanel.musicSwitched[GamePanel.nowState]){
                    GamePanel.music.get(GamePanel.nowState).resetAudioStream();
                    GamePanel.musicSwitched[GamePanel.nowState] = false;
                }
                GamePanel.music.get(GamePanel.nowState).play();
            }
        }
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
            audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
}