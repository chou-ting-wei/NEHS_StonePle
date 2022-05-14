package com.userwei;
import java.awt.*;
import javax.swing.ImageIcon;

public class Material extends Rectangle{
    Image pic;
    int amt;

    Material(int x, int y, int w, int h, int amt, String s){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.amt = amt;

        try{
            // pic = Toolkit.getDefaultToolkit().createImage("StonePle/src/Image/font/" + s);
            java.net.URL imgURL = Material.class.getResource("Image/material/" + s);
            pic = new ImageIcon(imgURL).getImage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Component c){
        g.drawImage(pic, x, y, width, height, c);
    }
}
