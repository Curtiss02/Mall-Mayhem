package Model;

import View.Sprite;

public class Bullet extends Projectile {
    public Bullet(double x, double y, double xDir, double yDir){
        super(x,y,xDir,yDir);

        this.isEnemy = true;
        this.isPlayer = false;

        this.damage = 5;
        this.speed = 6;

        sprite = new Sprite(x, y);

        sprite.setImage("src/img/projectiles/bullet.png");

        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
    }

}
