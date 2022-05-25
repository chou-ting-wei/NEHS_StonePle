package com.userwei;
import java.awt.*;
import javax.swing.ImageIcon;

public class Monster extends Rectangle{

    Image pic;
    boolean destroyed, materialAdded;

    int movX, movY, lif, monsterIdx;
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
            lif = randomNumber(3, 5);
            // 面前
            atk[1][0] = 2;
            atk[1][1] = 2;
            atk[0][1] = 2;
            atk[1][0] = 2;
        }
        else if(monsterIdx == 1){
            lif = randomNumber(5, 10);
            // 上下左右
            atk[1][0] = 5;
            atk[1][1] = 5;
            atk[0][1] = 5;
            atk[1][0] = 5;
        }
        else if(monsterIdx == 2){
            lif = randomNumber(20, 30);
            // 面前
            atk[1][0] = 8;
            atk[1][1] = 8;
            atk[0][1] = 8;
            atk[1][0] = 8;
        }
        else if(monsterIdx == 3){
            lif = randomNumber(40, 60);
            // 全
            atk[1][0] = 12;
            atk[1][1] = 12;
            atk[0][1] = 12;
            atk[1][0] = 12;
            atk[0][0] = 12;
            atk[0][2] = 12;
            atk[2][0] = 12;
            atk[2][2] = 12;
        }
        else if(monsterIdx == 4){
            lif = 100;
            // 全
            atk[1][0] = 8;
            atk[1][1] = 8;
            atk[0][1] = 8;
            atk[1][0] = 8;
            atk[0][0] = 30;
            atk[0][2] = 30;
            atk[2][0] = 30;
            atk[2][2] = 30;
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
        if(lif < 0){
            destroyed = true;
            if(!materialAdded){
                materialAdded = true;
                if(monsterIdx == 0){
                    BackpackPanel.addMaterialAmount("wood", randomNumber(1, 3));
                    BackpackPanel.addMaterialAmount("coin", randomNumber(1, 2));
                    ValueCalculate.characterExp += 2;
                }
                else if(monsterIdx == 1){
                    BackpackPanel.addMaterialAmount("herb", randomNumber(1, 2));
                    BackpackPanel.addMaterialAmount("coin", randomNumber(1, 2));
                    ValueCalculate.characterExp += 6;
                }
                else if(monsterIdx == 2){
                    BackpackPanel.addMaterialAmount("iron", randomNumber(1, 2));
                    BackpackPanel.addMaterialAmount("coin", randomNumber(2, 3));
                    ValueCalculate.characterExp += 8;
                }
                else if(monsterIdx == 3){
                    BackpackPanel.addMaterialAmount("coin", randomNumber(5, 10));
                    ValueCalculate.characterExp += 5;
                }
                else if(monsterIdx == 4){
                    ValueCalculate.characterExp += 50;
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
