package Model;

import View.Sprite;

import java.awt.*;

public class Pickup {
    private int x, y;
    private Sprite sprite;
    private int width, height;



    private Rectangle collisionBox;
    public enum TYPE{
        HEALTH,
        AMMO,
        SUPERSHOT,
        SUPERSPEED
    }
    private TYPE type;
    public Pickup(int x, int y, TYPE type){
        this.x = x;
        this.y = y;
        this.type = type;
        this.sprite = new Sprite(x,y);

        switch (type){
            case HEALTH:
                sprite.setImage("src/img/pickups/healthkit.png");
                break;
            case AMMO:
                break;
            case SUPERSHOT:
                break;
            case SUPERSPEED:
                break;
        }
        width = sprite.getWidth();
        height = sprite.getHeight();
        collisionBox = new Rectangle(x,y,width,height);
    }

    public TYPE getType(){
        return type;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }
}
