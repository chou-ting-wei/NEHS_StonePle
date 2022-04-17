package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.event.*;
import java.awt.Graphics;

public class InstructionPanel extends JPanel implements KeyListener, MouseInputListener{

    Font font1;
    JFrame mainFrame, instructionScreen;

    static boolean Start;

    InstructionPanel(JFrame mainFrame, JFrame instructionScreen){
        this.mainFrame = mainFrame;
        this.instructionScreen = instructionScreen;

        font1 = new Font(395, 300, 490, 120, "instruction.png");

        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
        font1.draw(g, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            Start = false;
            GamePanel.Start = true;
            GamePanel.switchState(1);
            mainFrame.setVisible(true);
            instructionScreen.setVisible(false);
        }
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            System.out.println("-");
            System.out.println("Game State:");
            System.out.println(GamePanel.lastState + " " + GamePanel.nowState);

            System.out.println("Start State:");
            System.out.println(StartPanel.Start + " " + GamePanel.Start + " " + PausePanel.Start + " " + FieldPanel.Start + " " + CavePanel.Start + " " + InstructionPanel.Start);
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
