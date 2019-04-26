package Model;

import View.Sprite;

public class Ball extends Projectile {




    public Ball(double x, double y, double xDir, double yDir){

        super(x,y,xDir,yDir);


        this.isEnemy = false;
        this.isPlayer = true;
        this.damage = 20;
        this.speed = 6;

        sprite = new Sprite(x, y);

        sprite.setImage("src/img/ball.png");

        this.width = sprite.getWidth();
        this.height = sprite.getHeight();


    }


}
