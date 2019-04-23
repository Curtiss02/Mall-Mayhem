package Model;

import View.Sprite;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Character{

    protected int damage;
    protected List<Projectile> projectileList = new ArrayList<Projectile>();
    private int invulnTimer = 0;
    protected int invulnTicks = 10;
    protected int speed;
    protected boolean isStuck = false;
    protected int stuckCounter = 0;

    protected enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT

    }
    protected Direction direction;

    public Enemy(int x, int y){
        this.x = x;
        this.y = y;
        sprite = new Sprite(x,y);
    }

    //Used before collision detection
    public abstract void move();
    public void moveLeft(){
        dx = -speed;
        direction = Direction.LEFT;
    }
    public void moveRight(){
        dx = speed;
        direction = Direction.RIGHT;
    }
    public void moveUp(){
        dy = -speed;
        if(direction == Direction.LEFT){
            direction = Direction.UP_LEFT;
        }
        else if (direction == Direction.RIGHT){
            direction = direction.UP_RIGHT;
        }
        else{
            direction = Direction.UP;
        }
    }
    public void moveDown(){
        dy = speed;
        if(direction == Direction.LEFT){

            direction = Direction.DOWN_LEFT;
        }
        else if (direction == Direction.RIGHT){

            direction = direction.DOWN_RIGHT;
        }
        else{
            direction = Direction.DOWN;
        }
    }

    public int getDamage(){
        return damage;
    }
    //Should be used after collision detection
    public void tick(){
        if(healthPoints <= 0){
            sprite.setVisible(false);
        }
        if(isInvulnerable){
            invulnTimer++;
        }
        if(invulnTimer >= invulnTicks){
            setInvulnerable(false);
            invulnTimer = 0;
        }
        //System.out.println(stuckCounter);
        //System.out.println(isStuck);

        if(isStuck){
            stuckCounter++;
        }
        else{
            stuckCounter = 0;
        }

        x += dx;
        y += dy;

        this.sprite.setY(y);
        this.sprite.setX(x);
    }

    public void setStuck(boolean stuck) {
        isStuck = stuck;
    }
}