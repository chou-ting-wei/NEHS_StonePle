package com.userwei;
import java.awt.*;
import javax.swing.ImageIcon;

public class Monster extends Rectangle{

    Image pic;
    boolean destroyed, materialAdded;

    int movX, movY, maxlif, nowlif, monsterIdx, initX, initY;
    int atk[][];
    int monsterCount = 5;
    Thread thread;

    String name[] = {"slime.gif", "brownie.gif", "drackmage.gif", "mimic.gif", "overkilling_machine.gif"};

    public int randomNumber(int srt, int end){
        return (int)(Math.random() * (end - srt + 1)) + srt;
    }

    public int findIndex(String s){
        int idx;
        for(idx = 0; idx < monsterCount; idx ++){
            if(name[idx] == s) break;
        }
        return idx;
    }

    public void init(){
        atk = new int[5][5];

        if(monsterIdx == 0){
            maxlif = randomNumber(3, 5);
            nowlif = maxlif;
            // 面前
            atk[0][1] = 2;
            atk[1][0] = 2;
            atk[2][1] = 2;
            atk[1][2] = 2;
        }
        else if(monsterIdx == 1){
            maxlif = randomNumber(5, 10);
            nowlif = maxlif;
            // 上下左右
            atk[0][1] = 5;
            atk[1][0] = 5;
            atk[2][1] = 5;
            atk[1][2] = 5;
        }
        else if(monsterIdx == 2){
            maxlif = randomNumber(20, 30);
            nowlif = maxlif;
            // 面前
            atk[0][1] = 8;
            atk[1][0] = 8;
            atk[2][1] = 8;
            atk[1][2] = 8;
        }
        else if(monsterIdx == 3){
            maxlif = randomNumber(40, 60);
            nowlif = maxlif;
            // 全
            atk[0][1] = 12;
            atk[1][0] = 12;
            atk[2][1] = 12;
            atk[1][2] = 12;
            atk[0][0] = 12;
            atk[0][2] = 12;
            atk[2][0] = 12;
            atk[2][2] = 12;
        }
        else if(monsterIdx == 4){
            maxlif = 300;
            nowlif = maxlif;
            // 全
            atk[0][1] = 8;
            atk[0][2] = 8;
            atk[1][0] = 8;
            atk[2][0] = 8;
            atk[3][1] = 8;
            atk[3][2] = 8;
            atk[1][3] = 8;
            atk[2][3] = 8;
            atk[0][0] = 30;
            atk[0][3] = 30;
            atk[3][0] = 30;
            atk[3][3] = 30;
        }
    }

    Monster(int x, int y, int mx, int my, int w, int h, String s){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.movX = mx;
        this.movY = my;
        monsterIdx = findIndex(s);
        initX = x;
        initY = y;

        try{
            // pic = Toolkit.getDefaultToolkit().createImage("StonePle/src/Image/monster/" + s);
            java.net.URL imgURL = Monster.class.getResource("Image/monster/" + s);
            pic = new ImageIcon(imgURL).getImage();
        }catch(Exception e){
            e.printStackTrace();
        }

        init();

        thread = new Thread(() -> {
            while(true){
                update();

                try{
                    Thread.sleep(100);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void update(){
        if(nowlif < 0){
            destroyed = true;
            if(!materialAdded){
                materialAdded = true;
                if(monsterIdx == 0){
                    BackpackPanel.addMaterialAmount("wood", randomNumber(1, 3));
                    BackpackPanel.addMaterialAmount("coin", randomNumber(1, 2));
                    ValueCalculate.characterExp += 2;
                    ValueCalculate.characterExpChange = true;
                }
                else if(monsterIdx == 1){
                    BackpackPanel.addMaterialAmount("herb", randomNumber(1, 2));
                    BackpackPanel.addMaterialAmount("coin", randomNumber(1, 2));
                    ValueCalculate.characterExp += 6;
                    ValueCalculate.characterExpChange = true;
                }
                else if(monsterIdx == 2){
                    BackpackPanel.addMaterialAmount("iron", randomNumber(1, 2));
                    BackpackPanel.addMaterialAmount("coin", randomNumber(2, 3));
                    ValueCalculate.characterExp += 8;
                    ValueCalculate.characterExpChange = true;
                }
                else if(monsterIdx == 3){
                    BackpackPanel.addMaterialAmount("coin", randomNumber(5, 10));
                    ValueCalculate.characterExp += 5;
                    ValueCalculate.characterExpChange = true;
                }
                else if(monsterIdx == 4){
                    ValueCalculate.characterExp += 50;
                    ValueCalculate.characterExpChange = true;
                    CavePanel.bossDied = true;
                }
            }
        }
    }

    public void draw(Graphics g, Component c){
        if(!destroyed){
            g.drawImage(pic, x, y, width, height, c);
        }
    }
}
