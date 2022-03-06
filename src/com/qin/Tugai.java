package com.qin;

import java.awt.*;

public class Tugai extends GameObject{
    public  int length=50;

    public Tugai(String img,int x,int y,GamePanel gamePanel){
        super(img,x,y,gamePanel);
    }
    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img,x,y,gamePanel);
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,length,length);
    }
}
