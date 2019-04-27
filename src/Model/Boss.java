package Model;

import View.Sprite;

import java.awt.*;

public class Boss extends Enemy {

    private int shootTimer = 0;
    private int shootBurstCounter = 0;

    private int burstAmount = 5;
    private final int burstCooldown = 20;
    private final int shootCooldown = 120;

    private Sprite right;
    private Sprite left;


    public Boss(int x, int y){
        super(x,y);
        setSprite("img/enemies/tommyg-right.png");
        width = sprite.getWidth();
        height = sprite.getHeight();
        speed = 1;
        damage = 20;
        right = new Sprite(x,y);
        right.setImage("img/enemies/tommyg-right.png");
        left = new Sprite(x,y);
        left.setImage("img/enemies/tommyg-left.png");

        sprite = right;


        initPatrol();

        healthPoints = 1000;


    }

    @Override
    public void move() {
        patrol();
        if(shootTimer == 0) {
            shoot();
            shootTimer++;
            shootBurstCounter++;
        }
    }

    public void tick(){
        super.tick();
        if(shootBurstCounter < burstAmount) {
            if (shootTimer >= burstCooldown) {
                shootTimer = 0;
            }
            if (shootTimer > 0) {
                shootTimer++;
            }
        }
        else{
            if(shootTimer >= shootCooldown){
                shootTimer = 0;
                shootBurstCounter = 0;
            }
            else{
                shootTimer++;
            }

        }

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

    public void shoot(){

        //projectileList.add(new Ball(x,y, 1,1));
        for(int i = 0; i < 360; i+= 10){
            double rads = Math.toRadians(i);
            double xDir = Math.sin(rads);
            double yDir = Math.cos(rads);
            Projectile bullet = new CashBullet(x+width/2,y+height/2 ,xDir, yDir);
            projectileList.add(bullet);

        }
    }
}
