package Model;
import View.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Character {
    protected double y;
    protected double x;
    protected double dx, dy;
    protected int width, height;
    protected int healthPoints;
    protected boolean hasCollision = true;
    protected boolean isInvulnerable = false;
    protected Sprite sprite;

    static List<Projectile> projectileList = new ArrayList<Projectile>();

    double xDirection;
    double yDirection;

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    public Rectangle getFutureBounds(){
        return new Rectangle((int)(x+dx), (int)(y+dy), width, height);
    }

    public boolean hasCollision(){
        return hasCollision;
    }

    public void stop(){
        dx = 0;
        dy = 0;
    }
    public abstract void tick();

    public void setInvulnerable(boolean invulnerable) {
        isInvulnerable = invulnerable;
    }

    public void takeDamage(int damage){
        if(!isInvulnerable) {
            healthPoints -= damage;
            isInvulnerable = true;
        }

    }


    public int getHealthPoints(){
        return healthPoints;
    }

    public boolean isInvulnerable(){
        return  isInvulnerable;
    }

    public void setSprite(String s){
        sprite.setImage(s);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addHealth(int health){
        this.healthPoints += health;
    }

    public static List<Projectile> getProjectileList() {
        return projectileList;
    }
}
