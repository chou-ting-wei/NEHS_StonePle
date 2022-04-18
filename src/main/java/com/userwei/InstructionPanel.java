package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class InstructionPanel extends JPanel implements KeyListener{

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
            System.out.println(StartPanel.Start + " " + GamePanel.Start + " " + PausePanel.Start + " " + FieldPanel.Start + " " + CavePanel.Start + " " + InstructionPanel.Start);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
