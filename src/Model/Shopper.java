package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Shopper extends Enemy {
    boolean hasCollision = true;
    public Shopper(int x, int y){
        super(x, y);
        setSprite("src/img/blackblob.png");
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


    }

    public void move(){
        behaviour();
    }
    private void behaviour(){
        patrol();
    }
}
