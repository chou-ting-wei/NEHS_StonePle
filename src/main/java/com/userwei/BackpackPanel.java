package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

public class BackpackPanel extends JPanel implements KeyListener{
    JFrame mainFrame, backpackScreen;

    Icon icon1;
    Map map;

    static String name[] = {"coin", "herb", "iron", "wood"};
    static Font font[];
    static Material material[];
    static int fontX[], fontY[];
    static int materialCount = 4;

    static boolean Start, materialChanged;

    Thread thread;

    static int findIndex(String s){
        int idx;
        for(idx = 0; idx < materialCount; idx ++){
            if(name[idx] == s) break;
        }
        return idx;
    }

    void addMaterial(int x, int y, int amt, String s){
        int idx = findIndex(s);
        fontX[idx] = x;
        fontY[idx] = y;
        Material nowMaterial = new Material(x * 80 + 10, y * 80 + 10, 60, 60, 0, s + ".png");
        Font nowFont = new Font(x * 80, (y + 1) * 80 - 20, 80, 20, amt + ".png");
        font[idx] = nowFont;
        material[idx] = nowMaterial;
    }

    static public void addMaterialAmount(String s, int addAmt){
        int idx = findIndex(s), nowAmount = material[idx].amt;
        Font nowFont;
        if(nowAmount + addAmt < 0){
            nowFont = new Font(fontX[idx] * 80, (fontY[idx] + 1) * 80 - 20, 80, 20, "EOF.png");
        }
        else if(nowAmount + addAmt <= 99){
            nowFont = new Font(fontX[idx] * 80, (fontY[idx] + 1) * 80 - 20, 80, 20, (nowAmount + addAmt) + ".png");
        }
        else{
            nowFont = new Font(fontX[idx] * 80, (fontY[idx] + 1) * 80 - 20, 80, 20, "99+.png");
        }
        material[idx].amt += addAmt;
        font[idx] = nowFont;
        materialChanged = true;
    }

    static public int getMaterialAmount(String s){
        int idx = findIndex(s);
        return material[idx].amt;
    }

    void init(){
        icon1 = new Icon(10, 10, 60, 60, "backpack_white.png");
        map = new Map(0, 0, 1280, 720, "backpack.png");

        font = new Font[materialCount];
        fontX = new int[materialCount];
        fontY = new int[materialCount];
        material = new Material[materialCount];

        addMaterial(1, 1, 0, "coin");
        addMaterial(2, 1, 0, "herb");
        addMaterial(3, 1, 0, "iron");
        addMaterial(4, 1, 0, "wood");
    }

    BackpackPanel(JFrame mainFrame, JFrame backpackScreen){
        this.mainFrame = mainFrame;
        this.backpackScreen = backpackScreen;

        init();

        addKeyListener(this);
        setFocusable(true);

        thread = new Thread(() -> {
            while(true){
                update();

                try{
                    Thread.sleep(10);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void update(){
        if(materialChanged){
            materialChanged = false;
            repaint();
        }
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        icon1.draw(g, this);
        for(int i = 0; i < materialCount; i ++){
            material[i].draw(g, this);
        }
        for(int i = 0; i < materialCount; i ++){
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
        if(e.getKeyCode() == KeyEvent.VK_Q){
            BackpackPanel.Start = false;
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
            backpackScreen.setVisible(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
