package Model;

import View.Sprite;

import java.awt.event.KeyEvent;

public class Player extends Character {

    private final int speed = 3;
    private final int invulnTicks = 120;
    private int invulnTimer;

    public Player(int x, int y){
        this.x = x;
        this.y = y;
        sprite = new Sprite(x,y);
        setSprite("src/img/face3.png");
        width = sprite.getWidth();
        height = sprite.getHeight();
        invulnTimer = 0;

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
    }
    public void moveRight(){
        dx = speed;
    }
    public void moveUp(){
        dy = -speed;
    }
    public void moveDown(){
        dy = speed;
    }

    //Applys any movement that has been processed through player input
    public void tick(){
        x += dx;
        y += dy;
        sprite.setX(x);
        sprite.setY(y);
        if(isInvulnerable){
            invulnTimer++;
        }
        if(invulnTimer >= invulnTicks){
            setInvulnerable(false);
            invulnTimer = 0;
        }
    }



}
