package com.qin;

import java.awt.*;

public class Blast extends GameObject{

    public  int length=50;
    //爆炸图集
    static Image[] imgs=new Image[8];
    int explodeCount=0;

    static {
        for(int i =0;i<8;i++){
            imgs[i]=Toolkit.getDefaultToolkit().createImage("images/blast/blast"+(i+1)+".gif");
        }
    }

    public Blast(String img,int x,int y,GamePanel gamePanel){
        super(img,x,y,gamePanel);
    }
    @Override
    public void paintSelft(Graphics g) {
        if (explodeCount <8 ){
            g.drawImage(imgs[explodeCount], x,y,gamePanel);
            explodeCount++;

        }

    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,length,length);
    }
}
