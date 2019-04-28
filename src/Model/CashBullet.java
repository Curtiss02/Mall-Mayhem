package Model;

import View.Sprite;

public class CashBullet extends Projectile {
    public CashBullet(double x, double y, double xDir, double yDir){
        super(x,y,xDir,yDir);

        this.isEnemy = true;
        this.isPlayer = false;

        this.damage = 5;
        this.speed = 2;

        sprite = new Sprite(x, y);

        sprite.setImage("src/img/projectiles/cash.png");

        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
    }
}
