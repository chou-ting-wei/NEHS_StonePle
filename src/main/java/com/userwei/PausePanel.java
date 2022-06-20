package com.userwei;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class PausePanel extends JPanel implements KeyListener{
    Animate animate1;
    JFrame pauseScreen, settingScreen;
    static boolean Start;

    public void init(){
        animate1 = new Animate(1136, 639, 144, 81, "paused.gif");

        JButton pauseButton1 = new JButton(new ImageIcon(PausePanel.class.getResource("Image/icon/home.png")));
        pauseButton1.setBounds(560, 320, 80, 80);
        pauseButton1.setFocusPainted(false);
        pauseButton1.setBorderPainted(false);
        pauseButton1.setBorder(null);
        pauseButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                FieldPanel.monsterReset();
                FieldPanel.mapState_i = 1;
                FieldPanel.mapState_j = 5;
                FieldPanel.characterInitX = 560;
                FieldPanel.characterInitY = 400;
                FieldPanel.reset();

                CavePanel.monsterReset();
                CavePanel.mapState_i = 2;
                CavePanel.mapState_j = 5;
                CavePanel.characterInitX = 640;
                CavePanel.characterInitY = 640;
                CavePanel.reset();

                GamePanel.sudo = true;
                PausePanel.Start = false;
                JFrame nowFrame = GamePanel.frame[1];
                GamePanel.Start = true;
                GamePanel.switchState(1);
                nowFrame.setVisible(true);
                pauseScreen.setVisible(false);
            }
        });
        add(pauseButton1);

        JButton pauseButton2 = new JButton(new ImageIcon(PausePanel.class.getResource("Image/icon/settings.png")));
        pauseButton2.setBounds(1200, 0, 80, 80);
        pauseButton2.setFocusPainted(false);
        pauseButton2.setBorderPainted(false);
        pauseButton2.setBorder(null);
        pauseButton2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                Start = false;
                SettingPanel.Start = true;
                settingScreen.setVisible(true);
                pauseScreen.setVisible(false);
            }
        });
        add(pauseButton2);
    }

    PausePanel(JFrame pauseScreen, JFrame settingScreen){
        this.pauseScreen = pauseScreen;
        this.settingScreen = settingScreen;

        init();

        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
        animate1.draw(g, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        /*
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            System.out.println("-");
            System.out.println("Game State:");
            System.out.println(GamePanel.lastState + " " + GamePanel.nowState);

            System.out.println("Music State:");
            for(int i = 0; i < GamePanel.frameSize - 1; i ++){
                System.out.print(GamePanel.musicSwitched[i] + " ");
            }
            System.out.println(GamePanel.musicSwitched[GamePanel.frameSize - 1]);

            System.out.println("Start State:");
            System.out.println(StartPanel.Start + " " + GamePanel.Start + " " + PausePanel.Start + " " + FieldPanel.Start + " " + CavePanel.Start + " " + InstructionPanel.Start + " " + UpgradePanel.Start);
        }
        */
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
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

        /*
        // Sudo
        // Start Panel
        if(e.getKeyCode() == KeyEvent.VK_0 && Start){
            GamePanel.sudo = true;
            Start = false;
            JFrame nowFrame = GamePanel.frame[0];
            StartPanel.Start = true;
            GamePanel.switchState(0);
            nowFrame.setVisible(true);
            pauseScreen.setVisible(false);
        }
        // Game Panel
        if(e.getKeyCode() == KeyEvent.VK_1 && Start){
            GamePanel.sudo = true;
            Start = false;
            JFrame nowFrame = GamePanel.frame[1];
            GamePanel.Start = true;
            GamePanel.switchState(1);
            nowFrame.setVisible(true);
            pauseScreen.setVisible(false);
        }
        // Field Panel
        if(e.getKeyCode() == KeyEvent.VK_3 && Start){
            GamePanel.sudo = true;
            Start = false;
            JFrame nowFrame = GamePanel.frame[3];
            FieldPanel.Start = true;
            GamePanel.switchState(3);
            nowFrame.setVisible(true);
            pauseScreen.setVisible(false);
        }
        // Cave Panel
        if(e.getKeyCode() == KeyEvent.VK_4 && Start){
            GamePanel.sudo = true;
            Start = false;
            JFrame nowFrame = GamePanel.frame[4];
            CavePanel.Start = true;
            GamePanel.switchState(4);
            nowFrame.setVisible(true);
            pauseScreen.setVisible(false);
        }
        */
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
