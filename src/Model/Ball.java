package Model;

import View.Sprite;

public class Ball extends Projectile {

    private final int speed = 6;



    public Ball(int x, int y, int xDir, int yDir){
        this.x = x;
        this.y = y;
        this.healthPoints = 1;
        xDirection = xDir;
        yDirection = yDir;
        sprite = new Sprite(x, y);

        this.isEnemy = false;
        this.isPlayer = true;
        this.damage = 2;

        sprite.setImage("src/img/ball.png");

        this.width = sprite.getWidth();
        this.height = sprite.getHeight();

    }

    public void tick(){
        y += dy;
        x += dx;
        sprite.setX(x);
        sprite.setY(y);
    }
    public void move(){
        dy = speed * yDirection;
        dx = speed * xDirection;
    }
}
