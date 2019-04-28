package Model;

import View.Sprite;

import java.util.Random;
/*
    Securtiy guard patrols and shoots at player
 */
public class SecurityGuard extends Enemy {

    private int attackTimer = 0;
    private final int attackCooldown = 240;


    private Sprite left;
    private Sprite right;

    private String shootSound = "sounds/shootgun.wav";

    public SecurityGuard(int x, int y){
        super(x,y);
        int spriteNum = new Random().nextInt(3) + 1;
        healthPoints = 20;
        speed = 1;
        String spritePath = "img/enemies/security" + String.valueOf(spriteNum);
        left = new Sprite(x,y);
        left.setImage(spritePath + "-left.png");
        right = new Sprite(x,y);
        right.setImage(spritePath + "-right.png");
        sprite = right;
        width = sprite.getWidth();
        height = sprite.getHeight();

        initPatrol();

    }



    public void move() {
        patrol();
        attack();

    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        sprite = left;
    }

    @Override
    public void moveRight() {
        super.moveRight();
        sprite = right;
    }

    public void attack(){
        if(attackTimer == 0) {
            soundList.add(shootSound);
            double distance = getDistance(x, playerX, y, playerY);
            double xDir = (playerX - x) / distance;
            double yDir = (playerY - y) / distance;
            Projectile bullet = new Bullet(x, y, xDir, yDir);
            projectileList.add(bullet);
            attackTimer++;
        }

    }

    @Override
    public void tick() {
        super.tick();

        if(attackTimer > attackCooldown){
            attackTimer = 0;
        }
        if(attackTimer > 0){
            attackTimer++;
        }
    }

    public void attackOffset(int offsetTicks){
        attackTimer += offsetTicks;
    }

}
