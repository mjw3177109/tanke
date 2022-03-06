package com.qin;

import jdk.management.resource.internal.ResourceNatives;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot extends  Tank{

    public Bot(String img,int x,int y,GamePanel gamePanel,String upImg,String leftImg,String rightImg,String downImg){
        super(img,x, y,gamePanel,upImg,leftImg,rightImg,downImg);
    }
    public int moveTime =0;
    public boolean attackCoolDown=true;
    public int attckCoolDownTime =600;
    public Direction getRandownDirection(){
        Random random =new Random();
        int rnum =random.nextInt(4);
        switch (rnum){

            case 0:
                return Direction.LEFT;
            case 1:
                return Direction.RIGHT;

            case 2:
                return Direction.UP;

            case 3:
                return Direction.DOWN;
            default:
                return null;
        }
    }
    public void go(){
        attack();
        if(moveTime >=20){

            direction =getRandownDirection();
            moveTime =0;
        }else{
//            if (x <=0 || x>=800){
//
//                moveTime=20;
//            }else if(y<=0 ||y>=600){
//                moveTime=20;
//            }
            moveTime+=1;
        }
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

    }

    //新建一个线程
    class AttackCD extends Thread{
        public void run(){
            //该攻击功能设置为冷却状态
            attackCoolDown =false;
            //休眠1秒
            try{
                Thread.sleep(attckCoolDownTime);
            }catch (Exception e ){
                e.printStackTrace();
            }
            //该攻击功能解除冷却状态
            attackCoolDown=true;

            //线程终止
            this.stop();

        }


    }
    public void attack(){
        Point p= getHeadPoint();
        Random random =new Random();
        int rnum =random.nextInt(500);

        if (rnum<4){
//            try{
//
//                Thread.sleep(300);
//            }catch (Exception e ){
//                e.printStackTrace();
//            }
            if (attackCoolDown) {
                this.gamePanel.bulletList.add(new enemyBullet("images/enemymissile.gif", p.x, p.y, this.gamePanel, direction));
                new Tank.AttackCD().start();
            }

        }


    }

    public void hitPlayer() {
        ArrayList<Tank> playerlist = this.gamePanel.playerList;
        for (Tank player : playerlist) {
            if (this.getRec().intersects(player.getRec())) {
                this.gamePanel.blastList.add(new Blast("", player.x-34, player.y-14, this.gamePanel));
                player.alive=false;
                this.gamePanel.playerList.remove(player);
                this.gamePanel.removeBotList.add(this);
                break;
            }
        }
    }
    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img,x,y,null);
        this.go();
        this.hitPlayer();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}
