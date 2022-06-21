package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.*;
import java.awt.Graphics;

public class GameFinishPanel extends JPanel implements KeyListener{
    Map map;
    JFrame gameFinishScreen, caveScreen;

    static boolean Start;

    public void init(){
        map = new Map(0, 0, 1280, 720, "game_finish.png");
    }

    GameFinishPanel(JFrame gameFinishScreen, JFrame caveScreen){
        this.gameFinishScreen = gameFinishScreen;
        this.caveScreen = caveScreen;

        init();

        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            CavePanel.Start = true;
            Start = false;
            caveScreen.setVisible(true);
            gameFinishScreen.setVisible(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
