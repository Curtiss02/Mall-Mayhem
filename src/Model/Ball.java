package Model;

import View.Sprite;

public class Ball extends Projectile {


    //Ball projectile for player attack

    public Ball(double x, double y, double xDir, double yDir){

        super(x,y,xDir,yDir);


        this.isEnemy = false;
        this.isPlayer = true;
        this.damage = 20;
        this.speed = 6;

        sprite = new Sprite(x, y);

        sprite.setImage("img/ball.png");

        this.width = sprite.getWidth();
        this.height = sprite.getHeight();


    }


}
