package Model;

import java.awt.*;

public class Boss extends Enemy {

    private int shootTimer = 0;
    private int shootBurstCounter = 0;

    private int burstAmount = 5;
    private final int burstCooldown = 20;
    private final int shootCooldown = 120;

    public Boss(int x, int y){
        super(x,y);
        setSprite("src/img/enemies/tommyg-right.png");
        width = sprite.getWidth();
        height = sprite.getHeight();
        speed = 1;
        damage = 20;
        healthPoints = 1000;
        initPatrol();
        addPatrolPoint(new Point(300,300));
        addPatrolPoint(new Point(300,900));
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


    public void shoot(){
        System.out.println("SHOOTING NOW");
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
