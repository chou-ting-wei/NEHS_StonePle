package com.userwei;
import java.awt.*;
import javax.swing.ImageIcon;

public class Monster extends Rectangle{

    Image pic;
    boolean destroyed, materialAdded;

    int movX, movY, lif, def, atk, idx;
    int monsterCount = 5;
    Thread thread;

    String name[] = {"slime", "brownie", "drackmage", "mimic", "overkilling_machine"};

    // "coin", "herb", "iron", "wood"
    int addMAterialMin[][] = {
        {},
        {},
        {},
        {},
        {}
    };
    int addMAterialMax[][] = {
        {},
        {},
        {},
        {},
        {}
    };

    public int findIndex(String s){
        int idx;
        for(idx = 0; idx < monsterCount; idx ++){
            if(name[idx] == s) break;
        }
        return idx;
    }

    public void init(){

    }

    Monster(int x, int y, int mx, int my, int w, int h, String s){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.movX = mx;
        this.movY = my;

        init();

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

        try{
            // pic = Toolkit.getDefaultToolkit().createImage("StonePle/src/Image/monster/" + s);
            java.net.URL imgURL = Monster.class.getResource("Image/monster/" + s);
            pic = new ImageIcon(imgURL).getImage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void update(){
        if(lif < 0){
            destroyed = true;
            if(!materialAdded){
                materialAdded = true;
                for(){

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
