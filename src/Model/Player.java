package Model;

import View.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DeflaterInputStream;

/* PLAYER CLASS

   The playe class is the user's character
   and is controlled via keybaord input passed
   through the GameController
 */
public class Player extends Character {

    private final int speed = 1;
    private final int invulnTicks = 60;
    private final int shootCooldown = 35;

    private int shootTimer;
    private int invulnTimer;


    //Each of the animations and player states have seperate files
    private String walkUp = "img/player/walk-up.gif";
    private String walkDown = "img/player/walk-down.gif";
    private String walkLeft = "img/player/walk-left.gif";
    private String walkRight = "img/player/walk-right.gif";
    private String stillUp = "img/player/stand-up.png";
    private String stillDown = "img/player/stand-down.png";
    private String stillLeft = "img/player/stand-left.png";
    private String stillRight = "img/player/stand-right.png";

    private String hurtwalkUp = "img/player/walk-up-hurt.gif";
    private String hurtwalkDown = "img/player/walk-down-hurt.gif";
    private String hurtwalkLeft = "img/player/walk-left-hurt.gif";
    private String hurtwalkRight = "img/player/walk-right-hurt.gif";
    private String hurtstillUp = "img/player/stand-up-hurt.png";
    private String hurtstillDown = "img/player/stand-down-hurt.png";
    private String hurtstillLeft = "img/player/stand-left-hurt.png";
    private String hurtstillRight = "img/player/stand-right-hurt.png";

    private String shootSound = "sounds/pewhigh.wav";
    private String hurtSound = "sounds/hurt.wav";




    private boolean superShot = false;

    private boolean isHurt = false;

    private enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT

    }
    private Direction direction;




    public Player(int x, int y){
        this.x = x;
        this.y = y;
        sprite = new Sprite(x,y);
        setSprite("img/player/stand-right.png");
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.projectileList = new ArrayList<Projectile>();


        projectileHeight = new Ball(0,0,0,0).getHeight();
        projectileWidth =  new Ball(0,0,0,0).getWidth();

        this.invulnTimer = 0;
        this.yDirection = 0;
        this.xDirection = 1;
        this.direction = Direction.RIGHT;
        this.healthPoints = 100;
        this.shootTimer = 0;


        ;


    }



    public void setSpriteFiles() {

    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }


    public void stop(){
        dx = 0;
        dy = 0;
        if(healthPoints > 100){
            healthPoints = 100;
        }
        if(isHurt) {
            switch (direction) {

                case UP:
                    sprite.setImage(hurtstillUp);
                    break;
                case DOWN:
                    sprite.setImage(hurtstillDown);
                    break;
                case LEFT:
                    sprite.setImage(hurtstillLeft);
                    break;
                case RIGHT:
                    sprite.setImage(hurtstillRight);
                    break;
                case UP_LEFT:
                    sprite.setImage(hurtstillLeft);
                    break;
                case UP_RIGHT:
                    sprite.setImage(hurtstillRight);
                    break;
                case DOWN_LEFT:
                    sprite.setImage(hurtstillLeft);
                    break;
                case DOWN_RIGHT:
                    sprite.setImage(hurtstillRight);
                    break;
            }
        }
        else{
            switch (direction) {

                case UP:
                    sprite.setImage(stillUp);
                    break;
                case DOWN:
                    sprite.setImage(stillDown);
                    break;
                case LEFT:
                    sprite.setImage(stillLeft);
                    break;
                case RIGHT:
                    sprite.setImage(stillRight);
                    break;
                case UP_LEFT:
                    sprite.setImage(stillLeft);
                    break;
                case UP_RIGHT:
                    sprite.setImage(stillRight);
                    break;
                case DOWN_LEFT:
                    sprite.setImage(stillLeft);
                    break;
                case DOWN_RIGHT:
                    sprite.setImage(stillRight);
                    break;
            }
        }
    }
    public void reverse(){
        dx = -dx;
        dy = -dy;
    }

    public void knockBack(double xDirection, double yDirection, int distance){
        dx = distance*xDirection;
        dy = distance*yDirection;
    }
    public void moveLeft(){
        dx = -speed;
        direction = Direction.LEFT;
        if(isHurt){
            sprite.setImage(hurtwalkLeft);
        }
        else {
            sprite.setImage(walkLeft);
        }
    }
    public void moveRight(){
        dx = speed;
        direction = Direction.RIGHT;
        if(isHurt){
            sprite.setImage(hurtwalkRight);
        }
        else {
            sprite.setImage(walkRight);
        }
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
        if(isHurt){
            sprite.setImage(hurtwalkUp);
        }
        else {
            sprite.setImage(walkUp);
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
        if(isHurt){
            sprite.setImage(hurtwalkDown);
        }
        else {
            sprite.setImage(walkDown);
        }
    }

    public void sprint(){
        dx *= 2;
        dy *= 2;
    }


    public void shoot(){
        if(shootTimer == 0) {
            soundList.add(shootSound);
            if(superShot) {
                for (Direction direction : Direction.values()) {
                    setXandYDirection(direction);
                    projectileList.add(new Ball(x+projectileXOffset, y+projectileYOffset, xDirection, yDirection));
                }
            }
            else {

                projectileList.add(new Ball(x+projectileXOffset, y+projectileYOffset, xDirection, yDirection));
            }

            shootTimer++;
        }

    }

    //Applys any movement that has been processed through player input
    public void tick(){
        x += dx;
        y += dy;
        sprite.setX(x);
        sprite.setY(y);
        //Checks for invulnerbalitlty status
        if(healthPoints > 100){
            healthPoints = 100;
        }
        else if (healthPoints <= 0){

            healthPoints = 0;
            shootTimer = 1;
        }
        if(isInvulnerable){
            invulnTimer++;
        }
        if(invulnTimer >= invulnTicks){
            setInvulnerable(false);
            isHurt = false;
            invulnTimer = 0;
        }

        if(shootTimer > 0){
            shootTimer++;
        }
        if(shootTimer > shootCooldown){
            shootTimer = 0;
        }
        //Clean up projectile list for expired
        Iterator<Projectile> projectileIterator = projectileList.iterator();
        while(projectileIterator.hasNext()){
            Projectile thisProjectile = projectileIterator.next();
            if(thisProjectile.getHealthPoints() <= 0){
                projectileIterator.remove();
            }
        }

        setXandYDirection(direction);




    }

    private int projectileXOffset;
    private int projectileYOffset;
    private int projectileWidth;
    private int projectileHeight;

    public void setXandYDirection(Direction direction){

        switch (direction) {
            case UP:
                xDirection = 0;
                yDirection = -1;
                projectileXOffset = (width/2) - projectileWidth/2;
                projectileYOffset = 0;
                break;
            case DOWN:
                xDirection = 0;
                yDirection = 1;
                projectileXOffset = (width/2) - projectileWidth/2;
                projectileYOffset = (height);
                break;
            case LEFT:
                xDirection = -1;
                yDirection = 0;
                projectileXOffset = 0;
                projectileYOffset = height/2 - projectileHeight/2;
                break;
            case RIGHT:
                xDirection = 1;
                yDirection = 0;
                projectileXOffset = width - projectileWidth/2;
                projectileYOffset = height/2 - projectileHeight/2;
                break;
            case UP_LEFT:
                xDirection = -0.707;
                yDirection = -0.707;
                projectileXOffset = 0;
                projectileYOffset = 0;
                break;
            case UP_RIGHT:
                xDirection = 0.707;
                yDirection = -0.707;
                projectileXOffset = width;
                projectileYOffset = 0;
                break;
            case DOWN_LEFT:
                xDirection = -0.707;
                yDirection = 0.707;
                projectileXOffset = 0;
                projectileYOffset = height;
                break;
            case DOWN_RIGHT:
                xDirection = 0.707;
                yDirection = 0.707;
                projectileXOffset = width;
                projectileYOffset = height;
                break;
        }
    }

    public void setSuperShot(boolean superShot) {
        this.superShot = superShot;
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if(damage > 0){
            soundList.add(hurtSound);
            isHurt = true;
        }
    }
}
