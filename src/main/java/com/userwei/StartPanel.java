package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class StartPanel extends JPanel implements KeyListener{

    // Map map;
    Font font1, font2;
    Animate animate1;
    JFrame mainFrame, startScreen;

    static boolean Start = true;

    StartPanel(JFrame mainFrame, JFrame startScreen){
        this.mainFrame = mainFrame;
        this.startScreen = startScreen;

        // map = new Map(0, 0, 1280, 720, "start.png");
        font1 = new Font(240, 100, 800, 400, "welcome.png");
        font2 = new Font(1160, 690, 120, 30, "version_number.png");
        animate1 = new Animate(520, 520, 240, 135, "start.gif");

        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
        // map.draw(g, this);
        font1.draw(g, this);
        font2.draw(g, this);
        animate1.draw(g, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            Start = false;
            GamePanel.Start = true;
            GamePanel.switchState(1);
            mainFrame.setVisible(true);
            startScreen.setVisible(false);
        }
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
