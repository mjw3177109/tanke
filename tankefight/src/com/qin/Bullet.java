package com.qin;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.util.ArrayList;

public class Bullet extends GameObject{

    //尺寸
    int width =10;
    int height =10;

    //速度
    int speed =7;

    //方向
    Direction direction;

    public Bullet(String img,int x, int y,GamePanel gamePanel,Direction direction){
        super(img,x,y,gamePanel);
        this.direction=direction;
    }

    public void leftward(){
        x -=speed;
    }
    public void rightward(){
        x +=speed;
    }

    public void upward(){
        y -=speed;
    }
    public void downward(){
        y +=speed;
    }
    public void go(){

        switch (direction){
            case UP:
                upward();
                break;

            case DOWN:
                downward();
                break;

            case LEFT:
                leftward();
                break;

            case RIGHT:
                rightward();
                break;
        }

        this.hitBot();
        this.hitWall();
        this.hitGai();
        this.moveToBorder(x,y);
        this.hitBase();
    }


    /**
     * 子弹是否出界
     * */
    public void moveToBorder(int x, int y){
        if(x <0 ||x+width>this.gamePanel.getWidth()){
          this.gamePanel.removeList.add(this);

        }

        if(y<0||y+height>this.gamePanel.getHeight()){
            this.gamePanel.removeList.add(this);
        }



    }
    public void hitBot(){
        ArrayList<Bot> botlist = this.gamePanel.botList;
        for (Bot bot:botlist){
            if(this.getRec().intersects(bot.getRec())){
                this.gamePanel.blastList.add(new Blast("",bot.x-34,bot.y-14,this.gamePanel));
                this.gamePanel.botList.remove(bot);
                this.gamePanel.removeList.add(this);
                break;
            }
        }

    }

    public void hitWall(){
        ArrayList<Wall> walllist = this.gamePanel.wallList;
        for (Wall wall:walllist){
            if(this.getRec().intersects(wall.getRec())){

                this.gamePanel.wallList.remove(wall);
                this.gamePanel.removeList.add(this);
                break;
            }
        }

    }

    public void hitBase(){
        ArrayList<Base> baselist = this.gamePanel.baseList;
        for (Base base:baselist){
            if(this.getRec().intersects(base.getRec())){

                this.gamePanel.baseList.remove(base);
                this.gamePanel.removeList.add(this);
                break;
            }
        }

    }

    public void hitGai(){
        ArrayList<Tugai> tugailist = this.gamePanel.tugaiList;
        for (Tugai tugai:tugailist ){
            if(this.getRec().intersects(tugai.getRec())){
                this.gamePanel.removeList.add(this);
                break;
            }
        }

    }

    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img,x,y,null);
        this.go();

    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }

}
