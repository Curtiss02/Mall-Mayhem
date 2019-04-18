package Model;

import View.Sprite;

public abstract class Enemy extends Character{

    protected int damage;

    public Enemy(int x, int y){
        this.x = x;
        this.y = y;
        sprite = new Sprite(x,y);
    }

    //Used before collision detection
    public abstract void move();

    public int getDamage(){
        return damage;
    }
    //Should be used after collision detection
    public void tick(){
        if(healthPoints <= 0){
            sprite.setVisible(false);
        }

        x += dx;
        y += dy;

        this.sprite.setY(y);
        this.sprite.setX(x);
    }



}