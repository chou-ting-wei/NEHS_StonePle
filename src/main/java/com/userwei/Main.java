package com.userwei;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main{
    static boolean loading;
    public static void main(String[] args){
        // 0 Start Panel; 1 Main Panel; 2 Pause Panel; 3 Field Panel; 4 CavePanel; 5 Instruction Panel; 6 Upgrade Panel; 7 Backpack Panel
        JFrame blankFrame = new JFrame("StonePle");

        JFrame mainFrame = new JFrame("StonePle");
        JFrame startScreen = new JFrame("StonePle");
        JFrame pauseScreen = new JFrame("StonePle");
        JFrame fieldScreen = new JFrame("StonePle");
        JFrame caveScreen = new JFrame("StonePle");
        JFrame instructionScreen = new JFrame("StonePle");
        JFrame upgradeScreen = new JFrame("StonePle");
        JFrame backpackScreen = new JFrame("StonePle");
        JFrame settingScreen = new JFrame("StonePle");

        ValueCalculate vc = new ValueCalculate();
        vc.start();

        BackpackPanel backpackPanel = new BackpackPanel(mainFrame, backpackScreen);
        UpgradePanel upgradePanel = new UpgradePanel(mainFrame, upgradeScreen);
        GamePanel gamePanel = new GamePanel(startScreen, mainFrame, pauseScreen, fieldScreen, caveScreen, instructionScreen, upgradeScreen, backpackScreen);
        StartPanel startPanel = new StartPanel(mainFrame, startScreen);
        PausePanel pausePanel = new PausePanel(pauseScreen, settingScreen);
        FieldPanel fieldPanel = new FieldPanel(mainFrame, pauseScreen, fieldScreen, caveScreen, backpackScreen);
        CavePanel cavePanel = new CavePanel(mainFrame, pauseScreen, fieldScreen, caveScreen,backpackScreen);
        InstructionPanel instructionPanel = new InstructionPanel(mainFrame, instructionScreen);
        SettingPanel settingPanel = new SettingPanel(settingScreen, pauseScreen);

        int size_x = 1280, size_y = 720;
        
        String osName = System.getProperty("os.name");
        // System.out.println(osName);
        if(PropertiesUtil.getValue("Custom_Size").startsWith("On")){
            String csx = PropertiesUtil.getValue("Custom_Size_X");
            String csy = PropertiesUtil.getValue("Custom_Size_Y");
            try{
                size_x = Integer.parseInt(csx);
                size_y = Integer.parseInt(csy);
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        else if(osName.startsWith("Mac OS")){
            size_x = 1280;
            size_y = 748;
            PropertiesUtil.setValue("Custom_Size_X", "1280");
            PropertiesUtil.setValue("Custom_Size_Y", "748");
            PropertiesUtil.setValue("Init_Border_X", "1280");
            PropertiesUtil.setValue("Init_Border_Y", "748");
        }
        else if(osName.startsWith("Windows")){
            size_x = 1300;
            size_y = 770;
            PropertiesUtil.setValue("Custom_Size_X", "1300");
            PropertiesUtil.setValue("Custom_Size_Y", "770");
            PropertiesUtil.setValue("Init_Border_X", "1300");
            PropertiesUtil.setValue("Init_Border_Y", "770");
        }
        else{
            // unix or linux
            size_x = 1280;
            size_y = 720;
            PropertiesUtil.setValue("Custom_Size_X", "1280");
            PropertiesUtil.setValue("Custom_Size_Y", "720");
            PropertiesUtil.setValue("Init_Border_X", "1280");
            PropertiesUtil.setValue("Init_Border_Y", "720");
        }

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int loc_x = (int) screensize.getWidth() / 2 - size_x / 2;
        int loc_y = (int) screensize.getHeight() / 2 - size_y / 2;
        
        mainFrame.getContentPane().add(gamePanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(size_x, size_y);
        mainFrame.setResizable(false);
        mainFrame.setLocation(loc_x, loc_y);
        mainFrame.setVisible(false);
        mainFrame.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                mainFrame.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        JButton pauseButton1 = new JButton(new ImageIcon(Main.class.getResource("Image/icon/undo.png")));
        pauseButton1.setBounds(0, 0, 80, 80);
        pauseButton1.setBorderPainted(false);
        pauseButton1.setBorder(null);
        pausePanel.setLayout(null);
        pauseButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                PausePanel.Start = false;
                if(GamePanel.lastState == 1){
                    GamePanel.Start = true;
                }
                else if(GamePanel.lastState == 3){
                    FieldPanel.Start = true;
                }
                else if(GamePanel.lastState == 4){
                    CavePanel.Start = true;
                }
                PausePanel.Start = false;
                JFrame nowFrame = GamePanel.frame[GamePanel.lastState];
                GamePanel.switchState(GamePanel.lastState);
                nowFrame.setVisible(true);
                pauseScreen.setVisible(false);
            }
        });
        pausePanel.add(pauseButton1);

        pauseScreen.getContentPane().add(pausePanel);
        pauseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pauseScreen.setSize(size_x, size_y);
        pauseScreen.setResizable(false);
        pauseScreen.setLocation(loc_x, loc_y); 
        pauseScreen.setVisible(false);
        pauseScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                pauseScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        fieldScreen.getContentPane().add(fieldPanel);
        fieldScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fieldScreen.setResizable(false);
        fieldScreen.setLocation(loc_x, loc_y);
        fieldScreen.setSize(size_x, size_y);
        fieldScreen.setVisible(false);
        fieldScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                fieldScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        caveScreen.getContentPane().add(cavePanel);
        caveScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        caveScreen.setResizable(false);
        caveScreen.setLocation(loc_x, loc_y);
        caveScreen.setSize(size_x, size_y);
        caveScreen.setVisible(false);
        caveScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                caveScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        JButton instructionButton1 = new JButton(new ImageIcon(Main.class.getResource("Image/icon/cross_white.png")));
        instructionButton1.setBounds(1200, 0, 80, 80);
        instructionButton1.setFocusPainted(false);
        instructionButton1.setBorderPainted(false);
        instructionButton1.setBorder(null);
        instructionPanel.setLayout(null);
        instructionButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                InstructionPanel.Start = false;
                GamePanel.Start = true;
                GamePanel.switchState(1);
                mainFrame.setVisible(true);
                instructionScreen.setVisible(false);
            }
        });
        instructionPanel.add(instructionButton1);
       
        instructionScreen.getContentPane().add(instructionPanel);
        instructionScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructionScreen.setResizable(false);
        instructionScreen.setLocation(loc_x, loc_y);
        instructionScreen.setSize(size_x, size_y);
        instructionScreen.setVisible(false);
        instructionScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                instructionScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        JButton upgradeButton1 = new JButton(new ImageIcon(Main.class.getResource("Image/icon/cross_white.png")));
        upgradeButton1.setBounds(1200, 0, 80, 80);
        upgradeButton1.setFocusPainted(false);
        upgradeButton1.setBorderPainted(false);
        upgradeButton1.setBorder(null);
        upgradePanel.setLayout(null);
        upgradeButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                UpgradePanel.Start = false;
                GamePanel.Start = true;
                GamePanel.switchState(1);
                mainFrame.setVisible(true);
                upgradeScreen.setVisible(false);
            }
        });
        upgradePanel.add(upgradeButton1);
       
        upgradeScreen.getContentPane().add(upgradePanel);
        upgradeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        upgradeScreen.setResizable(false);
        upgradeScreen.setLocation(loc_x, loc_y);
        upgradeScreen.setSize(size_x, size_y);
        upgradeScreen.setVisible(false);
        upgradeScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                upgradeScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        JButton backpackButton1 = new JButton(new ImageIcon(Main.class.getResource("Image/icon/cross_white.png")));
        backpackButton1.setBounds(1200, 0, 80, 80);
        backpackButton1.setFocusPainted(false);
        backpackButton1.setBorderPainted(false);
        backpackButton1.setBorder(null);
        backpackPanel.setLayout(null);
        backpackButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }

                BackpackPanel.Start = false;
                if(GamePanel.lastState == 1){
                    GamePanel.Start = true;
                }
                else if(GamePanel.lastState == 3){
                    FieldPanel.Start = true;
                }
                else if(GamePanel.lastState == 4){
                    CavePanel.Start = true;
                }
                JFrame nowFrame = GamePanel.frame[GamePanel.lastState];
                GamePanel.switchState(GamePanel.lastState);
                nowFrame.setVisible(true);
                backpackScreen.setVisible(false);
            }
        });
        backpackPanel.add(backpackButton1);

        backpackScreen.getContentPane().add(backpackPanel);
        backpackScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backpackScreen.setResizable(false);
        backpackScreen.setLocation(loc_x, loc_y);
        backpackScreen.setSize(size_x, size_y);
        backpackScreen.setVisible(false);
        backpackScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                backpackScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        settingScreen.getContentPane().add(settingPanel);
        settingScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingScreen.setResizable(false);
        settingScreen.setLocation(loc_x, loc_y);
        settingScreen.setSize(size_x, size_y);
        settingScreen.setVisible(false);

        JButton settingButton1 = new JButton(new ImageIcon(Main.class.getResource("Image/icon/undo_white.png")));
        settingButton1.setBounds(0, 0, 80, 80);
        settingButton1.setFocusPainted(false);
        settingButton1.setBorderPainted(false);
        settingButton1.setBorder(null);
        settingPanel.setLayout(null);
        settingButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                if(SettingPanel.addx != SettingPanel.lastaddx || SettingPanel.addy != SettingPanel.lastaddy){
                    SettingPanel.addx = SettingPanel.lastaddx;
                    SettingPanel.addy = SettingPanel.lastaddy;
                }
                if(GamePanel.lastState == 0){
                    startScreen.setVisible(true);
                    settingScreen.setVisible(false);
                    StartPanel.Start = true;
                    SettingPanel.Start = false;
                }
                else{
                    SettingPanel.Start = false;
                    PausePanel.Start = true;
                    pauseScreen.setVisible(true);
                    settingScreen.setVisible(false);
                }
            }
        });
        settingPanel.add(settingButton1);

        settingScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                settingScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        // For Cover Loaded Panel
        blankFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        blankFrame.setSize(size_x, size_y);
        blankFrame.setResizable(false);
        blankFrame.setLocation(loc_x, loc_y);
        blankFrame.setVisible(false);
        blankFrame.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                blankFrame.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        // Load Panel
        loading = true;
        pauseScreen.setVisible(true);
        instructionScreen.setVisible(true);
        upgradeScreen.setVisible(true);
        backpackScreen.setVisible(true);
        blankFrame.setVisible(true);

        startScreen.getContentPane().add(startPanel);
        startScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startScreen.setSize(size_x, size_y);
        startScreen.setResizable(false);
        startScreen.setLocation(loc_x, loc_y);
        startScreen.setVisible(true);
        startScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                startScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        if(PropertiesUtil.getValue("Config_Mode").startsWith("On")){
            settingScreen.setVisible(true);
            startScreen.setVisible(false);
            StartPanel.Start = false;
            SettingPanel.Start = true;
        }

        // Close Loaded Panel
        try{
            Thread.sleep(100);
            pauseScreen.setVisible(false);
            instructionScreen.setVisible(false);
            upgradeScreen.setVisible(false);
            backpackScreen.setVisible(false);
            blankFrame.setVisible(false);
            loading = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
