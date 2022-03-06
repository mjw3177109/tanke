package com.qin;

import java.awt.*;
import java.util.ArrayList;

public class enemyBullet extends Bullet{
    public enemyBullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel, direction);
    }


    public void hitPlayer(){
        ArrayList<Tank> playerlist = this.gamePanel.playerList;
        for (Tank player:playerlist){
            if(this.getRec().intersects(player.getRec())){
                this.gamePanel.blastList.add(new Blast("",player.x-34,player.y-14,this.gamePanel));
                player.alive=false;
                this.gamePanel.playerList.remove(player);
                this.gamePanel.removeList.add(this);
                break;
            }
        }

    }


    public void paintSelft(Graphics g) {
        g.drawImage(img,x,y,null);
        this.go();
        this.hitPlayer();
//        this.hitWall();
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}
