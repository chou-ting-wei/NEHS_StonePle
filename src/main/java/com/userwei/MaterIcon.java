package com.userwei;
import java.awt.*;
import javax.swing.ImageIcon;

public class MaterIcon extends Rectangle{
    Image pic;

    MaterIcon(int x, int y, int w, int h, String s){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        try{
            // pic = Toolkit.getDefaultToolkit().createImage("StonePle/src/Image/icon/" + s);
            java.net.URL imgURL = Icon.class.getResource("Image/material/" + s);
            pic = new ImageIcon(imgURL).getImage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Component c){
        g.drawImage(pic, x, y, width, height, c);
    }
}
