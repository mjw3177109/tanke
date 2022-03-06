package com.qin;

import com.qin.Direction;

import java.awt.*;
import java.util.ArrayList;

public abstract  class Tank extends GameObject{

    //尺寸
    public int width =40;
    public int height =50;
    //速度
    private int speed =3;
    //方向
    //坦克初始方向(向上)
    public   Direction direction = Direction.UP;
    //四个方向的图片
    private String upImg;
    private String leftImg;
    private String rightImg;
    private String downImg;

    //坦克的攻击冷却状态
    private boolean attackCoolDown =true;

    //坦克是否存活
    public boolean alive =false;

    //攻击冷却事件毫秒间隔100ms
    private int attckCoolDownTime =800;

    public Tank(String img,int x,int y,GamePanel gamePanel,String upImg,String leftImg,String rightImg,String downImg){
        super(img,x,y,gamePanel);
        this.upImg=upImg;
        this.leftImg=leftImg;
        this.rightImg=rightImg;
        this.downImg=downImg;
    }
    public void leftward(){

        direction =Direction.LEFT;
        setImg(leftImg);
        if (!hitWall(x-speed,y) && !moveToBorder(x-speed,y)){
            this.x -=speed;
        }


    }
    public void rightward(){

        direction =Direction.RIGHT;
        setImg(rightImg);
        if (!hitWall(x+speed,y) && !moveToBorder(x+speed,y)){
            this.x +=speed;
        }


    }
    public void upward(){

        direction =Direction.UP;
        setImg(upImg);
        if (!hitWall(x,y-speed)&& !moveToBorder(x,y-speed)){
            this.y -=speed;
        }


    }
    public void downward(){

        direction =Direction.DOWN;
        setImg(downImg);
        if (!hitWall(x,y+speed)&& !moveToBorder(x,y+speed)){
            this.y +=speed;
        }


    }
    public void setImg(String img){
        this.img =Toolkit.getDefaultToolkit().getImage(img);
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
        if (attackCoolDown && alive){
            Point p =this.getHeadPoint();
            Bullet bullet =new Bullet("images/tankmissile.gif",p.x,p.y,this.gamePanel,direction);
            this.gamePanel.bulletList.add(bullet);
            //线程开始
            new AttackCD().start();
        }


    }
    /**
     * 与墙体发生碰撞
     */
    public boolean hitWall(int x, int y){
        ArrayList<Wall> walllist = this.gamePanel.wallList;
        Rectangle next =new Rectangle(x,y,width,height);
        for (Wall wall:walllist){
            if (next.intersects(wall.getRec())){
                return true;
            }
        }

        return false;
    }

    /**
     * 坦克是否出界
     * */
    public boolean moveToBorder(int x, int y){
        if(x <0){
            return true;
        }else if(x+width>this.gamePanel.getWidth()){
            return true;
        }else if(y<0){
            return true;
        }else if(y+height>this.gamePanel.getHeight()) {
            return true;
        }


        return false;
    }
    public Point getHeadPoint(){
        switch (direction){
            case LEFT:
                return new Point(x-10,y+height /2);

            case RIGHT:
                return new Point(x+width+10,y+height/2);

            case UP:
                return new Point(x+width/2 ,y-10);
            case DOWN:
                return new Point(x+width/2 ,y+height+10);

            default:
                return null;
        }

    }

    @Override
    public abstract void paintSelft(Graphics g);

    @Override
    public abstract Rectangle getRec();
}
