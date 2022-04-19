package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class StartPanel extends JPanel implements KeyListener{

    Font font1, font2;
    Animate animate1;
    JFrame mainFrame, startScreen;

    static boolean Start = true;

    StartPanel(JFrame mainFrame, JFrame startScreen){
        this.mainFrame = mainFrame;
        this.startScreen = startScreen;

        font1 = new Font(240, 100, 800, 400, "welcome.png");
        font2 = new Font(1200, 690, 80, 45, "version_number.png");
        animate1 = new Animate(520, 520, 240, 135, "start.gif");

        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
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

            System.out.println("Start State:");
            System.out.println(StartPanel.Start + " " + GamePanel.Start + " " + PausePanel.Start + " " + FieldPanel.Start + " " + CavePanel.Start + " " + InstructionPanel.Start + " " + UpgradePanel.Start);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
