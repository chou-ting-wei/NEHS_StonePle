package com.userwei;
import java.awt.*;
import javax.swing.ImageIcon;

public class Character extends Rectangle{
    Image pic;
    int movX, movY;

    Character(int x, int y, int mx, int my, int w, int h, String s){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.movX = mx;
        this.movY = my;

        try{
            // pic = Toolkit.getDefaultToolkit().createImage("StonePle/src/Image/character/" + s);
            java.net.URL imgURL = Character.class.getResource("Image/character/" + s);
            pic = new ImageIcon(imgURL).getImage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Component c){
        g.drawImage(pic, x, y, width, height, c);
    }
}
