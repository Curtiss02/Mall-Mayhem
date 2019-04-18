package Model;

import View.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Character {

    private final int speed = 3;
    private final int invulnTicks = 120;
    private final int shootCooldown = 20;

    private int shootTimer;
    private int invulnTimer;

    private List<Projectile> projectileList;

    public Player(int x, int y){
        this.x = x;
        this.y = y;
        sprite = new Sprite(x,y);
        setSprite("src/img/face3.png");
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.projectileList = new ArrayList<Projectile>();
        this.invulnTimer = 0;
        this.yDirection = 0;
        this.xDirection = 1;
        this.healthPoints = 10;
        this.shootTimer = 0;


    }




    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }


    public void stop(){
        dx = 0;
        dy = 0;
    }
    public void reverse(){
        dx = -dx;
        dy = -dy;
    }

    public void knockBack(int xDirection, int yDirection, int distance){
        dx = distance*xDirection;
        dy = distance*yDirection;
    }
    public void moveLeft(){
        dx = -speed;
        xDirection = -1;
    }
    public void moveRight(){
        dx = speed;
        xDirection = 1;
    }
    public void moveUp(){
        dy = -speed;
        yDirection = -1;
    }
    public void moveDown(){
        dy = speed;
        yDirection = 1;
    }


    public void shoot(){
        if(shootTimer == 0) {
            projectileList.add(new Ball(x, y, xDirection, yDirection));
            shootTimer++;
        }

    }

    //Applys any movement that has been processed through player input
    public void tick(){
        x += dx;
        y += dy;
        sprite.setX(x);
        sprite.setY(y);
        //Checks for invulnerbalitlty status
        if(isInvulnerable){
            invulnTimer++;
        }
        if(invulnTimer >= invulnTicks){
            setInvulnerable(false);
            invulnTimer = 0;
        }

        if(shootTimer > 0){
            shootTimer++;
        }
        if(shootTimer > shootCooldown){
            shootTimer = 0;
        }



    }

    public List<Projectile> getProjectileList() {
        return projectileList;
    }
}
