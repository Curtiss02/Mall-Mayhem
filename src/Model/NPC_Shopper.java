package Model;

import java.awt.*;
import java.util.Random;

public class NPC_Shopper extends Enemy {

    public NPC_Shopper(int x, int y){
        super(x,y);
        isInvulnerable = true;
        int spritenum = new Random().nextInt(4) + 1;
        String spriteSrc = "src/img/enemies/shopper" + String.valueOf(spritenum) + ".png";
        sprite.setImage(spriteSrc);
        width = sprite.getWidth();
        height = sprite.getHeight();
        speed = 1;
        damage = 0;
        healthPoints = 1;
    }

    public void move(){
        stop();

        meander();

    }




    public void tick(){
        super.tick();
        isInvulnerable = false;
        if(isWaiting == true){
            waitTimer++;

        }

        if(waitTimer >= waitValue){

            isWaiting = false;

            waitTimer = 0;
        }

    }
}