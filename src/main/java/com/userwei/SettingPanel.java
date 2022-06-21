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
    Font font6, font7, font8, font9;
    Thread thread;
    JButton settingButton2, settingButton3, settingButton4, settingButton5, settingButton6, settingButton7, settingButton8;

    static int addx = 0, addy = 0;
    static int lastaddx = 0, lastaddy = 0;
    int initborderx = Integer.parseInt(PropertiesUtil.getValue("Init_Border_X"));
    int initbordery = Integer.parseInt(PropertiesUtil.getValue("Init_Border_Y"));
    static boolean Start;

    public void init(){
        addx = Integer.parseInt(PropertiesUtil.getValue("Custom_Add_X"));
        addy = Integer.parseInt(PropertiesUtil.getValue("Custom_Add_Y"));
        lastaddx = Integer.parseInt(PropertiesUtil.getValue("Custom_Last_Add_X"));
        lastaddy = Integer.parseInt(PropertiesUtil.getValue("Custom_Last_Add_Y"));

        map = new Map(0, 0, 1280, 720, "setting.png");
        icon = new Icon(1210, 10, 60, 60, "settings_white.png");
        font1 = new Font(80, 80, 160, 80, "music_reset_title.png");
        font2 = new Font(240, 160, 400, 80, "music_reset_annotation.png");
        font3 = new Font(80, 240, 160, 80, "config_mode_title.png");
        font4 = new Font(240, 320, 400, 80, "border_reset_annotation1.png");
        font5 = new Font(80, 560, 560, 80, "border_reset_annotation2.png");
        font6 = new Font(80, 400, 160, 80, "border_setting_width.png");
        font7 = new Font(80, 480, 160, 80, "border_setting_height.png");
        font8 = new Font(160, 410, 240, 60, "0.png");
        font9 = new Font(160, 490, 240, 60, "0.png");

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
                    music.playOnce(350);
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                                
                resetMusic("Setting");
            }
        });
        add(settingButton1);

        settingButton2 = new JButton(new ImageIcon(SettingPanel.class.getResource("Image/font/" + PropertiesUtil.getValue("Config_Mode") + ".png")));
        settingButton2.setBounds(80, 320, 160, 80);
        settingButton2.setFocusPainted(false);
        // settingButton2.setBorderPainted(false);
        // settingButton2.setBorder(null);
        settingButton2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce(350);
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
        
        settingButton3 = new JButton(new ImageIcon(SettingPanel.class.getResource("Image/font/plus.png")));
        settingButton3.setBounds(410, 410, 60, 60);
        settingButton3.setFocusPainted(false);
        // settingButton3.setBorderPainted(false);
        // settingButton3.setBorder(null);
        settingButton3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce(350);
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                addx += 1;
            }
        });
        add(settingButton3);

        settingButton4 = new JButton(new ImageIcon(SettingPanel.class.getResource("Image/font/minus.png")));
        settingButton4.setBounds(250, 410, 60, 60);
        settingButton4.setFocusPainted(false);
        // settingButton4.setBorderPainted(false);
        // settingButton4.setBorder(null);
        settingButton4.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce(350);
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                addx -= 1;
            }
        });
        add(settingButton4);

        settingButton5 = new JButton(new ImageIcon(SettingPanel.class.getResource("Image/font/plus.png")));
        settingButton5.setBounds(410, 490, 60, 60);
        settingButton5.setFocusPainted(false);
        // settingButton5.setBorderPainted(false);
        // settingButton5.setBorder(null);
        settingButton5.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce(350);
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                addy += 1;
            }
        });
        add(settingButton5);

        settingButton6 = new JButton(new ImageIcon(SettingPanel.class.getResource("Image/font/minus.png")));
        settingButton6.setBounds(250, 490, 60, 60);
        settingButton6.setFocusPainted(false);
        // settingButton6.setBorderPainted(false);
        // settingButton6.setBorder(null);
        settingButton6.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce(350);
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                addy -= 1;
            }
        });
        add(settingButton6);
        
        settingButton7 = new JButton(new ImageIcon(SettingPanel.class.getResource("Image/font/apply_setting.png")));
        settingButton7.setBounds(480, 400, 160, 80);
        settingButton7.setFocusPainted(false);
        // settingButton7.setBorderPainted(false);
        // settingButton7.setBorder(null);
        settingButton7.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce(350);
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                lastaddx = addx;
                lastaddy = addy;

                PropertiesUtil.setValue("Custom_Size_X", String.valueOf(initborderx + addx));
                PropertiesUtil.setValue("Custom_Size_Y", String.valueOf(initbordery + addy));
                PropertiesUtil.setValue("Custom_Size", "On");
                PropertiesUtil.setValue("Custom_Add_X", String.valueOf(addx));
                PropertiesUtil.setValue("Custom_Add_Y", String.valueOf(addy));
                PropertiesUtil.setValue("Custom_Last_Add_X", String.valueOf(lastaddx));
                PropertiesUtil.setValue("Custom_Last_Add_Y", String.valueOf(lastaddy));
            }
        });
        add(settingButton7);

        settingButton8 = new JButton(new ImageIcon(SettingPanel.class.getResource("Image/font/reset_setting.png")));
        settingButton8.setBounds(480, 480, 160, 80);
        settingButton8.setFocusPainted(false);
        // settingButton8.setBorderPainted(false);
        // settingButton8.setBorder(null);
        settingButton8.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce(350);
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                PropertiesUtil.resetValue();
                addx = 0;
                addy = 0;
                lastaddx = 0;
                lastaddy = 0;
            }
        });
        add(settingButton8);
    }

    SettingPanel(JFrame settingScreen, JFrame pauseScreen){
        this.settingScreen = settingScreen;
        this.pauseScreen = pauseScreen;

        init();

        thread = new Thread(() -> {
            while(true){
                update();

                try{
                    Thread.sleep(100);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        addKeyListener(this);
        setFocusable(true);
    }

    public void update(){
        if(addx == lastaddx && addy == lastaddy){
            settingButton7.setEnabled(false);
        }
        else{
            settingButton7.setEnabled(true);
        }
        if(addx == 0 && addy == 0){
            settingButton8.setEnabled(false);
        }
        else{
            settingButton8.setEnabled(true);
        }

        if(addx == 50){
            settingButton3.setEnabled(false);
        }
        else{
            settingButton3.setEnabled(true);
        }
        if(addx == -50){
            settingButton4.setEnabled(false);
        }
        else{
            settingButton4.setEnabled(true);
        }

        if(addy == 50){
            settingButton5.setEnabled(false);
        }
        else{
            settingButton5.setEnabled(true);
        }
        if(addy == -50){
            settingButton6.setEnabled(false);
        }
        else{
            settingButton6.setEnabled(true);
        }

        if(addx < 0){
            font8 = new Font(160, 410, 240, 60, "m" + Math.abs(addx) + ".png");
            repaint();
        }
        else{
            font8 = new Font(160, 410, 240, 60, addx + ".png");
            repaint();
        }
        if(addy < 0){
            font9 = new Font(160, 490, 240, 60, "m" + Math.abs(addy) + ".png");
            repaint();
        }
        else{
            font9 = new Font(160, 490, 240, 60, addy + ".png");
            repaint();
        }
        if(PropertiesUtil.getValue("Config_Mode").startsWith("Off")){
            settingButton2.setIcon(new ImageIcon(SettingPanel.class.getResource("Image/font/Off.png")));
        }
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        icon.draw(g, this);
        font1.draw(g, this);
        font2.draw(g, this);
        font3.draw(g, this);
        font4.draw(g, this);
        font5.draw(g, this);
        font6.draw(g, this);
        font7.draw(g, this);
        font8.draw(g, this);
        font9.draw(g, this);
    }

    static public void resetMusic(String s){
        for(int nowState = 0; nowState <= 7; nowState ++){
            String nowMusicName = GamePanel.musicName.get(nowState);
            if(nowMusicName != "null"){
                GamePanel.music.get(nowState).stop();
                GamePanel.musicSwitched[nowState] = true;
            }
        }

        if(s == "Setting"){
            String nowMusicName = GamePanel.musicName.get(GamePanel.lastState);
            if(nowMusicName != "null"){
                if(GamePanel.musicSwitched[GamePanel.lastState]){
                    GamePanel.music.get(GamePanel.lastState).resetAudioStream();
                    GamePanel.musicSwitched[GamePanel.lastState] = false;
                }
                GamePanel.music.get(GamePanel.lastState).play();
            }
        }
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
