package Model;

import View.Sprite;

public abstract class Enemy extends Character{



    public Enemy(int x, int y){
        this.x = x;
        this.y = y;
        sprite = new Sprite(x,y);
    }

    //Used before collision detection
    public abstract void move();

    //Should be used after collision detection
    public abstract void tick();


}