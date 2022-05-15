package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class InstructionPanel extends JPanel implements KeyListener{
    JFrame mainFrame, instructionScreen;

    Font font1;
    Icon icon1;
    Map map;

    static boolean Start;

    public void init(){
        font1 = new Font(80, 80, 1120, 560, "instruction.png");
        icon1 = new Icon(10, 10, 60, 60, "exclamation_white.png");
        map = new Map(0, 0, 1280, 720, "instruction.png");
    }

    InstructionPanel(JFrame mainFrame, JFrame instructionScreen){
        this.mainFrame = mainFrame;
        this.instructionScreen = instructionScreen;

        init();

        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        icon1.draw(g, this);
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
            System.out.println(StartPanel.Start + " " + GamePanel.Start + " " + PausePanel.Start + " " + FieldPanel.Start + " " + CavePanel.Start + " " + InstructionPanel.Start + " " + UpgradePanel.Start);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
