package Model;

import View.Sprite;

import java.awt.event.KeyEvent;

public class Player extends Character {


    public Player(int x, int y){
        this.x = x;
        this.y = y;
        sprite = new Sprite(x,y);
        setSprite("src/img/face3.png");

    }
    public void setSprite(String s){
        sprite.setImage(s);
    }
    public int getDirection(){
        return this.direction;
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
    public void moveLeft(){
        dx = -4;
    }
    public void moveRight(){
        dx = 4;
    }
    public void moveUp(){
        dy = -4;
    }
    public void moveDown(){
        dy = 4;
    }

    public void tick(){
        x += dx;
        y += dy;
        sprite.setX(x);
        sprite.setY(y);
    }



}
