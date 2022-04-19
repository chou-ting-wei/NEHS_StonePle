package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class UpgradePanel extends JPanel implements KeyListener{
    JFrame mainFrame, updateScreen;

    static boolean Start;

    UpgradePanel(JFrame mainFrame, JFrame updateScreen){
        this.mainFrame = mainFrame;
        this.updateScreen = updateScreen;


        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
        
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
