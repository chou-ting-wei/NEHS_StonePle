package com.userwei;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class SettingPanel extends JPanel implements KeyListener{
    JFrame settingScreen, pauseScreen;
    Map map;
    Icon icon;
    Font font1, font2, font3, font4, font5;

    static boolean Start;

    public void init(){
        map = new Map(0, 0, 1280, 720, "setting.png");
        icon = new Icon(1210, 10, 60, 60, "settings_white.png");
        font1 = new Font(80, 80, 160, 80, "music_reset_title.png");
        font2 = new Font(240, 160, 400, 80, "music_reset_annotation.png");
        font3 = new Font(80, 240, 160, 80, "config_mode_title.png");
        font4 = new Font(240, 320, 400, 80, "border_reset_annotation1.png");
        font5 = new Font(80, 560, 560, 80, "border_reset_annotation2.png");

        JButton settingButton1 = new JButton(new ImageIcon(SettingPanel.class.getResource("Image/font/music_reset.png")));
        settingButton1.setBounds(80, 160, 160, 80);
        settingButton1.setFocusPainted(false);
        // settingButton1.setBorderPainted(false);
        // settingButton1.setBorder(null);
        settingButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                                
                for(int nowState = 0; nowState <= 7; nowState ++){
                    String nowMusicName = GamePanel.musicName.get(nowState);
                    if(nowMusicName != "null"){
                        GamePanel.music.get(nowState).stop();
                        GamePanel.musicSwitched[nowState] = true;
                    }
                }

                String nowMusicName = GamePanel.musicName.get(GamePanel.lastState);
                if(nowMusicName != "null"){
                    if(GamePanel.musicSwitched[GamePanel.lastState]){
                        GamePanel.music.get(GamePanel.lastState).resetAudioStream();
                        GamePanel.musicSwitched[GamePanel.lastState] = false;
                    }
                    GamePanel.music.get(GamePanel.lastState).play();
                }
            }
        });
        add(settingButton1);

        JButton settingButton2 = new JButton(new ImageIcon(SettingPanel.class.getResource("Image/font/" + PropertiesUtil.getValue("Config_Mode") + ".png")));
        settingButton2.setBounds(80, 320, 160, 80);
        settingButton2.setFocusPainted(false);
        // settingButton2.setBorderPainted(false);
        // settingButton2.setBorder(null);
        settingButton2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }

                if(PropertiesUtil.getValue("Config_Mode").startsWith("On")){
                    PropertiesUtil.setValue("Config_Mode", "Off");
                    String name = PropertiesUtil.getValue("Config_Mode");
                    settingButton2.setIcon(new ImageIcon(SettingPanel.class.getResource("Image/font/" + name + ".png")));
                }
                else{
                    PropertiesUtil.setValue("Config_Mode", "On");
                    String name = PropertiesUtil.getValue("Config_Mode");
                    settingButton2.setIcon(new ImageIcon(SettingPanel.class.getResource("Image/font/" + name + ".png")));
                }
            }
        });
        add(settingButton2);
    }

    SettingPanel(JFrame settingScreen, JFrame pauseScreen){
        this.settingScreen = settingScreen;
        this.pauseScreen = pauseScreen;

        init();

        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        icon.draw(g, this);
        font1.draw(g, this);
        font2.draw(g, this);
        font3.draw(g, this);
        font4.draw(g, this);
        font5.draw(g, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
