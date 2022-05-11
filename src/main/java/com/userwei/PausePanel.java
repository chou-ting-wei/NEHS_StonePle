package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class PausePanel extends JPanel implements KeyListener{

    Animate animate1;
    JFrame pauseScreen;
    static boolean Start;

    PausePanel(JFrame pauseScreen){
        this.pauseScreen = pauseScreen;

        animate1 = new Animate(1136, 639, 144, 81, "paused.gif");

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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
