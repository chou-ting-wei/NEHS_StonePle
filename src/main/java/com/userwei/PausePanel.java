package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.event.*;
import java.awt.Graphics;

public class PausePanel extends JPanel implements KeyListener, MouseInputListener{

    Animate animate1;
    Font font1;
    JFrame pauseScreen;
    static boolean Start;

    PausePanel(JFrame pauseScreen){
        this.pauseScreen = pauseScreen;

        animate1 = new Animate(1136, 639, 144, 81, "paused.gif");
        font1 = new Font(376, 200, 528, 251, "sudo.png");

        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
        animate1.draw(g, this);
        font1.draw(g, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE && Start){
            Start = false;
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
            pauseScreen.setVisible(false);
        }
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            System.out.println("-");
            System.out.println("Game State:");
            System.out.println(GamePanel.lastState + " " + GamePanel.nowState);

            System.out.println("Start State:");
            System.out.println(StartPanel.Start + " " + GamePanel.Start + " " + PausePanel.Start + " " + FieldPanel.Start + " " + CavePanel.Start + " " + InstructionPanel.Start);
        }

        // Sudo
        // StartPanel
        if(e.getKeyCode() == KeyEvent.VK_0 && Start){
            GamePanel.sudo = true;
            Start = false;
            JFrame nowFrame = GamePanel.frame[0];
            StartPanel.Start = true;
            GamePanel.switchState(0);
            nowFrame.setVisible(true);
            pauseScreen.setVisible(false);
        }
        // GamePanel
        if(e.getKeyCode() == KeyEvent.VK_1 && Start){
            GamePanel.sudo = true;
            Start = false;
            JFrame nowFrame = GamePanel.frame[1];
            GamePanel.Start = true;
            GamePanel.switchState(1);
            nowFrame.setVisible(true);
            pauseScreen.setVisible(false);
        }
        // FieldPanel
        if(e.getKeyCode() == KeyEvent.VK_3 && Start){
            GamePanel.sudo = true;
            Start = false;
            JFrame nowFrame = GamePanel.frame[3];
            FieldPanel.Start = true;
            GamePanel.switchState(3);
            nowFrame.setVisible(true);
            pauseScreen.setVisible(false);
        }
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

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
}
