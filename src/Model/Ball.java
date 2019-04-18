package Model;

import View.Sprite;

public class Ball extends Projectile {

    private final int speed = 6;
    private final int damage = 1;

    public Ball(int x, int y, int xDir, int yDir){
        this.x = x;
        this.y = y;
        xDirection = xDir;
        yDirection = yDir;
        sprite = new Sprite(x, y);
        width = sprite.getWidth();
        height = sprite.getHeight();
        sprite.setImage("src/img/ball.png");

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
