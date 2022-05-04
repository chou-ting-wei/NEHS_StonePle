package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class BackpackPanel extends JPanel implements KeyListener{
    JFrame mainFrame, backpackScreen;

    Icon icon1;
    Map map;

    String name[] = {"coin", "herb", "iron", "wood"};
    Font font[];
    Material material[];
    Weapon weapon[];
    int fontX[], fontY[];

    int materialCount = 4, weaponCount = 0;

    static boolean Start;

    int findIndex(String s){
        int idx;
        for(idx = 0; idx < materialCount + weaponCount; idx ++){
            if(name[idx] == s) break;
        }
        return idx;
    }

    void addMaterial(int x, int y, String s){
        int idx = findIndex(s);
        fontX[idx] = x;
        fontY[idx] = y;
        Material nowMaterial = new Material(x * 80 + 10, y * 80 + 10, 60, 60, 0, s + ".png");
        Font nowFont = new Font(x * 80, (y + 1) * 80 - 20, 80, 20, "0.png");
        font[idx] = nowFont;
        material[idx] = nowMaterial;
        // materialCount ++;
    }

    void addWeapon(int x, int y, String s){
        int idx = findIndex(s);
        fontX[idx] = x;
        fontY[idx] = y;
        Weapon nowWeapon = new Weapon(x * 80 + 10, y * 80 + 10, 60, 60, s + ".png");
        Font nowFont = new Font(x * 80, (y + 1) * 80 - 20, 80, 20, "0.png");
        font[idx] = nowFont;
        weapon[idx] = nowWeapon;
        // weaponCount ++;
    }

    void addMaterialAmount(String s, int addAmt){
        int idx = findIndex(s), nowAmount = material[idx].amt;
        Font nowFont = new Font(fontX[idx] * 80, (fontY[idx] + 1) * 80 - 20, 80, 20, (nowAmount + addAmt) + ".png");
        material[idx].amt += addAmt;
        font[idx] = nowFont;
        repaint();
    }

    int getMaterialAmount(String s){
        int idx = findIndex(s);
        return material[idx].amt;
    }

    void init(){
        icon1 = new Icon(10, 10, 60, 60, "backpack_white.png");
        map = new Map(0, 0, 1280, 720, "backpack.png");

        font = new Font[materialCount + weaponCount];
        fontX = new int[materialCount + weaponCount];
        fontY = new int[materialCount + weaponCount];
        material = new Material[materialCount];
        weapon = new Weapon[weaponCount];

        addMaterial(1, 1, "coin");
        addMaterial(2, 1, "herb");
        addMaterial(3, 1, "iron");
        addMaterial(4, 1, "wood");
    }

    BackpackPanel(JFrame mainFrame, JFrame backpackScreen){
        this.mainFrame = mainFrame;
        this.backpackScreen = backpackScreen;

        init();

        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        icon1.draw(g, this);
        for(int i = 0; i < materialCount; i ++){
            material[i].draw(g, this);
        }
        for(int i = 0; i < weaponCount; i ++){
            weapon[i].draw(g, this);
        }
        for(int i = 0; i < materialCount + weaponCount; i ++){
            font[i].draw(g, this);
        }
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
        if(e.getKeyCode() == KeyEvent.VK_0){
            addMaterialAmount("coin", 1);
        }
        if(e.getKeyCode() == KeyEvent.VK_1){
            addMaterialAmount("herb", 1);
        }
        if(e.getKeyCode() == KeyEvent.VK_2){
            addMaterialAmount("iron", 1);
        }
        if(e.getKeyCode() == KeyEvent.VK_3){
            addMaterialAmount("wood", 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
