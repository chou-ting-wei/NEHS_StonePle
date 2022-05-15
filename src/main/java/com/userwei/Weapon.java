package com.userwei;
import java.awt.*;
import javax.swing.ImageIcon;

public class Weapon extends Rectangle{
    Image pic;
    int atk, level, atkAddition;
    boolean unlocked;

    Weapon(int x, int y, int w, int h, int atk, int atkAddition, int level, String s){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.atk = atk;
        this.atkAddition = atkAddition;
        this.level = level;

        try{
            // pic = Toolkit.getDefaultToolkit().createImage("StonePle/src/Image/font/" + s);
            java.net.URL imgURL = Weapon.class.getResource("Image/weapon/" + s);
            pic = new ImageIcon(imgURL).getImage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Component c){
        g.drawImage(pic, x, y, width, height, c);
    }
}
