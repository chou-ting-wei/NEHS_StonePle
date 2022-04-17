package com.userwei;
import java.awt.*;
import javax.swing.ImageIcon;

public class Monster extends Rectangle{

    Image pic;
    boolean destroyed;

    int movX, movY, lifeValue, defenseValue;

    Monster(int x, int y, int mx, int my, int w, int h, int l, int d, String s){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.movX = mx;
        this.movY = my;

        this.lifeValue = l;
        this.defenseValue = d;

        try{
            // pic = Toolkit.getDefaultToolkit().createImage("StonePle/src/Image/monster/" + s);
            java.net.URL imgURL = Monster.class.getResource("Image/monster/" + s);
            pic = new ImageIcon(imgURL).getImage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Component c){
        if(!destroyed){
            g.drawImage(pic, x, y, width, height, c);
        }
    }
}
