package com.qin;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.awt.Graphics;

public abstract  class GameObject {

    //坦克父类
    //图片
    public Image img;
    //坐标
    public int x;
    public  int y;
    //游戏界面
    public GamePanel gamePanel;

    public GameObject(String img,int x,int y,GamePanel gamePanel){
        this.img = Toolkit.getDefaultToolkit().getImage(img);
        this.x=x;
        this.y=y;
        this.gamePanel=gamePanel;
    }
    public abstract void paintSelft(Graphics g);

    public abstract Rectangle getRec();
}
