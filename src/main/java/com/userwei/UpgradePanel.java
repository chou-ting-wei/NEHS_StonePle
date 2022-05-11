package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class UpgradePanel extends JPanel implements KeyListener{
    JFrame mainFrame, upgradeScreen;
    
    Icon icon1;
    Map map;

    String name[] = {"sword1", "sword2"};
    Weapon weapon[];
    int weaponCount = 2;
    int weaponAtk[][] = {
        {1, 2, 3, 4, 5},
        {3, 4, 6, 8, 10}
    };

    static boolean Start;

    Thread thread;

    int findIndex(String s){
        int idx;
        for(idx = 0; idx < weaponCount; idx ++){
            if(name[idx] == s) break;
        }
        return idx;
    }

    void addWeapon(int x, int y, int atk, int level, String s){
        int idx = findIndex(s);
        Weapon nowWeapon = new Weapon(x * 80 + 10, y * 80 + 10, 60, 60, atk, level, s + ".png");
        weapon[idx] = nowWeapon;
    }

    void init(){
        icon1 = new Icon(10, 10, 60, 60, "arrow_up_white.png");
        map = new Map(0, 0, 1280, 720, "upgrade.png");

        weapon = new Weapon[weaponCount];

        addWeapon(1, 1, 1, 1, "sword1");
        addWeapon(2, 1, 0, 0, "sword2");
    }

    UpgradePanel(JFrame mainFrame, JFrame upgradeScreen){
        this.mainFrame = mainFrame;
        this.upgradeScreen = upgradeScreen;

        addKeyListener(this);
        setFocusable(true);

        init();
        

    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        icon1.draw(g, this);
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
