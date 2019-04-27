package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shopper extends Enemy {


    boolean hasCollision = true;

    public Shopper(int x, int y){
        super(x, y);
        int spritenum = new Random().nextInt(4) + 1;
        String spriteSrc = "img/enemies/shopper" + String.valueOf(spritenum) + ".png";
        sprite.setImage(spriteSrc);
        width = sprite.getWidth();
        height = sprite.getHeight();
        init();
        this.healthPoints = 10;
        this.damage = 1;

        this.speed = 2;

    }


    public void init(){
        initPatrol();
    }


    public void tick(){
        super.tick();

        if(isWaiting == true){
            waitTimer++;

        }

        if(waitTimer >= waitValue){

            isWaiting = false;

            waitTimer = 0;
        }


    }

    public void move(){
        stop();
        behaviour();
    }
    private void behaviour(){
        meander();
    }




}
