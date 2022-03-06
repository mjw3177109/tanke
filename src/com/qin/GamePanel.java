package com.qin;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JFrame {

    //窗口长度
    int width =800;
    int height =610;
    //定义指针初始坐标
    int y =150;
    //定义指针图片
    Image select =Toolkit.getDefaultToolkit().getImage("images/selecttank.gif");
    //定义游戏模式 0代表未开始 1代表单人 2代表双人
    int state =0;

    int a =0;

    //定义双缓存图片
    Image offScreenImage =null;
    //定义子弹列表
    public ArrayList<Bullet> bulletList =new ArrayList<Bullet>();

    //创建敌方坦克的列表
    ArrayList<Bot> botList =new ArrayList<Bot>();


    //删除敌方坦克列表
    ArrayList<Bot> removeBotList =new ArrayList<Bot>();
    //删除子弹列表
    public ArrayList<Bullet> removeList =new ArrayList<Bullet>();

    //定义玩家列表
    public ArrayList<Tank> playerList =new ArrayList<>();

    //新建一个围墙列表
    public ArrayList<Wall> wallList =new ArrayList<Wall>();

    //新建一个草丛列表
    public ArrayList<Grass> grassList =new ArrayList<Grass>();

    //新建一个钢墙列表
    public ArrayList<Tugai> tugaiList =new ArrayList<Tugai>();

    //添加一个基地列表
    public ArrayList<Base> baseList =new ArrayList<Base>();


    //添加一个爆炸元素的列表
    public ArrayList<Blast> blastList =new ArrayList<Blast>();
    //定义添加坦克的次数,重绘画布的次数
    int count =0;

    //敌方坦克的数量
    int enemycount =0;

    boolean restart =false;

    //定义坦克移动的次数

    //定义玩家一
    PlayerOne playerOne =new PlayerOne("images/player1/p1tankU.gif",125,510,this,"images/player1/p1tankU.gif",
            "images/player1/p1tankL.gif","images/player1/p1tankR.gif","images/player1/p1tankD.gif");

    //定义玩家2
    PlayerTwo playerTwo =new PlayerTwo("images/player2/p2tankU.gif",625,510,this,"images/player2/p2tankU.gif",
            "images/player2/p2tankL.gif","images/player2/p2tankR.gif","images/player2/p2tankD.gif");

    //定义敌方坦克
//    Bot bot =new Bot("images/enemy/enemy1D.gif",100,50,this,"images/enemy/enemy1U.gif",
//            "images/enemy/enemy1L.gif","images/enemy/enemy1R.gif","images/enemy/enemy1D.gif");
    //窗口启动方法
    public void launch(){
        //标题
        setTitle("坦克大战");
        //窗口初始大小
        setSize(width,height);
        //使屏幕居中
        setLocationRelativeTo(null);
        //添加关闭事件
        setDefaultCloseOperation(3);
        //用户不能调整大小
        setResizable(false);
        //使窗口可见
        setVisible(true);
        //添加键盘监视器
        this.addKeyListener(new KeyMonitor());

        //添加围墙
        for(int i=0;i <14;i++){
            wallList.add(new Wall("images/walls.gif",i*60,170,this));
//            grassList.add(new Grass("images/grass.png",60+i*60,400,this));
        }
        for(int i=0;i <5;i++){

            grassList.add(new Grass("images/grass.png",60+i*60*2,300,this));


//            tugaiList.add(new Tugai("images/tugai.gif",i*60,230,this));
        }

        wallList.add(new Wall("images/walls.gif",305,560,this));
        wallList.add(new Wall("images/walls.gif",305,500,this));
        wallList.add(new Wall("images/walls.gif",365,500,this));
        wallList.add(new Wall("images/walls.gif",425,500,this));
        wallList.add(new Wall("images/walls.gif",425,560,this));


        baseList.add(new Base("images/star.gif",375,563,this));











        //重绘制
        while(true){
            if (restart){
                playerList.clear();
                baseList.clear();
                blastList.clear();
                botList.clear();
                bulletList.clear();
                wallList.clear();
                baseList.add(new Base("images/star.gif",375,563,this));
                for(int i=0;i <14;i++){
                    wallList.add(new Wall("images/walls.gif",i*60,170,this));
                }
                wallList.add(new Wall("images/walls.gif",305,560,this));
                wallList.add(new Wall("images/walls.gif",305,500,this));
                wallList.add(new Wall("images/walls.gif",365,500,this));
                wallList.add(new Wall("images/walls.gif",425,500,this));
                wallList.add(new Wall("images/walls.gif",425,560,this));
                playerOne.x=125;
                playerOne.y=510;
                playerOne.upward();
                playerTwo.x=625;
                playerTwo.y=510;
                playerTwo.upward();
                enemycount=0;



                restart=false;
            }
            if(botList.size()==0 &&enemycount==10){
                state=5;
            }
            if((playerList.size()==0 &&(state==1||state==2))||baseList.size()==0){
                state= 4;
            }

            if (count % 100 ==1 && enemycount <10 &&(state==1||state==2)){
                Random random =new Random();
                int rnum =random.nextInt(800);
                int cnum =random.nextInt(100);
                if (cnum <50){
                    cnum=50;
                }

                botList.add(new Bot("images/enemy/enemy1D.gif",rnum,cnum,this,"images/enemy/enemy1U.gif",
                        "images/enemy/enemy1L.gif","images/enemy/enemy1R.gif","images/enemy/enemy1D.gif"));
                enemycount+=1;
            }



            repaint();
            try{
                Thread.sleep(60);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g){

        //创建和窗口一样大小的Image图片
        if (offScreenImage == null){
            offScreenImage=this.createImage(width,height);
        }

        //获取图片的画笔
        Graphics gImage =offScreenImage.getGraphics();
        //设置画笔颜色
        gImage.setColor(Color.gray);

        //绘制实心矩形
        gImage.fillRect(0,0,width,height);

        //改变画笔颜色
        gImage.setColor(Color.blue);

        //改变文字大小和样式
        gImage.setFont(new Font("伪宋",Font.BOLD,50));
        if (state ==0){
            //添加文字
            gImage.drawString("选择游戏模式",220,100);
            gImage.drawString("选择单人模式",220,200);
            gImage.drawString("选择双人模式",220,300);
            //绘制指针
            gImage.drawImage(select,160,y,null);


        }else if (state ==1 ||state==2){
            gImage.drawString("游戏开始",220,100);
            if (state ==1){
                gImage.setFont(new Font("伪宋",Font.BOLD,30));
                gImage.setColor(Color.RED);
                gImage.drawString(String.format("剩余敌人:%s",botList.size()),10,70);

            }else{
                gImage.setFont(new Font("伪宋",Font.BOLD,30));
                gImage.setColor(Color.RED);
                gImage.drawString(String.format("剩余敌人:%s",botList.size()),10,70);
                //gImage.drawString("双人模式",220,200);

            }

            //添加游戏元素
            for (Tank player:playerList){
                player.paintSelft(gImage);
            }
//            playerOne.

            //批量绘制坦克
            for(Bot bot:botList){
                bot.paintSelft(gImage);
            }

            //从子弹列表里绘制子弹
            for(Bullet bullet:bulletList){
                bullet.paintSelft(gImage);
            }
            //sSystem.out.println(bulletList.size());

            //绘制围墙元素
            for(Wall wall:wallList){
                wall.paintSelft(gImage);
            }
            for(Grass grass:grassList){
                grass.paintSelft(gImage);
            }
//            for(Tugai tugai:tugaiList){
//                tugai.paintSelft(gImage);
//            }
            for(Base base:baseList){
                base.paintSelft(gImage);
            }

            for (Blast blast:blastList){
                blast.paintSelft(gImage);
            }
            botList.removeAll(removeBotList);
            bulletList.removeAll(removeList);
            count +=1;
//            bot.paintSelft(gImage);




        }else if(state ==5){

            gImage.setFont(new Font("伪宋",Font.BOLD,70));
            gImage.drawString("游戏胜利",220,200);
//            gImage.drawString("按Y重新来过",220,280);

        }else if(state ==4){

            gImage.setFont(new Font("伪宋",Font.BOLD,70));
            gImage.drawString("游戏失败",220,200);
            gImage.drawString("按Y重新来过",220,280);

        }else if(state ==3){

            gImage.setFont(new Font("伪宋",Font.BOLD,70));
            gImage.drawString("游戏暂停",220,200);

        }
        /** 将图片一次性绘制到窗口中*/
        g.drawImage(offScreenImage,0,0,null);




    }

    //内部类 键盘监视器
    class KeyMonitor extends KeyAdapter{

        //按下按键
        @Override
        public void keyPressed(KeyEvent e){
//            System.out.println(e.getKeyChar());
            //按下按键
            int key =e.getKeyCode();
            System.out.println(e.getKeyCode());
            System.out.println(KeyEvent.VK_ENTER);
            System.out.println(KeyEvent.VK_2);
            switch (key){
                case KeyEvent.VK_1:
                    a=1;
                    y=150;
                    break;

                case KeyEvent.VK_2:
                    a=2;
                    y=250;
                    break;
                case KeyEvent.VK_ENTER:
                    state =a;
                    playerList.add(playerOne);
                    //以后添加playerTwo
                    if (state ==2){
                        playerList.add(playerTwo);
                        playerTwo.alive=true;
                    }
                    playerOne.alive=true;

                    break;

                case KeyEvent.VK_P:
                    if (state!=3){
                        a=state;
                        state =3;
                    }else
                    {
                        state=a;
                        if(a==0){
                            a=1;
                        }
                    }

                    break;

                case KeyEvent.VK_Y:
                    if (state==4){
                        restart=true;
                        a=0;
                        state =a;

                    }
                    break;
                default:
                    playerOne.keyPressed(e);
                    playerTwo.keyPressed(e);
            }
        }
        //松开键盘
        @Override
        public void keyReleased(KeyEvent e){
            playerOne.keyReleased(e);
            playerTwo.keyReleased(e);

        }

    }

}
