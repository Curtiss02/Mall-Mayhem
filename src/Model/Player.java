package Model;

import Audio.SoundPlayer;
import View.GUIPanel;
import View.SettingsMenu;
import View.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Player extends Character {

    private final int speed = 1;
    private final int invulnTicks = 120;
    private final int shootCooldown = 20;

    private int shootTimer;
    private int invulnTimer;

    private String walkUp = "src/img/player/walk-up.gif";
    private String walkDown = "src/img/player/walk-down.gif";
    private String walkLeft = "src/img/player/walk-left.gif";
    private String walkRight = "src/img/player/walk-right.gif";
    private String stillUp = "src/img/player/stand-up.png";
    private String stillDown = "src/img/player/stand-down.png";
    private String stillLeft = "src/img/player/stand-left.png";
    private String stillRight = "src/img/player/stand-right.png";
    private boolean superShot = true;
    public static int stamina = 300;
    SoundPlayer soundPlayer = new SoundPlayer();


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
        setSprite("src/img/player/stand-right.png");
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.projectileList = new ArrayList<Projectile>();

        projectileHeight = new Ball(0,0,0,0).getHeight();
        projectileWidth =  new Ball(0,0,0,0).getWidth();

        this.invulnTimer = 0;
        this.yDirection = 0;
        this.xDirection = 1;
        this.direction = Direction.RIGHT;
        this.healthPoints = 90;
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
    public void reverse(){
        dx = -dx;
        dy = -dy;
    }

    public void knockBack(int xDirection, int yDirection, int distance){
        dx = distance*xDirection;
        dy = distance*yDirection;
    }
    public void moveLeft(){
        dx = -speed;
        direction = Direction.LEFT;
        sprite.setImage(walkLeft);
    }
    public void moveRight(){
        dx = speed;
        direction = Direction.RIGHT;
        sprite.setImage(walkRight);
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
        sprite.setImage(walkUp);
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
        sprite.setImage(walkDown);
    }

    public void sprint(){
        if(stamina > 1) {
            dx *= 2;
            dy *= 2;
            stamina -= 3;
        }
    }


    public void shoot(){
        if(shootTimer == 0) {

            if(superShot) {
                for (Direction direction : Direction.values()) {
                    setXandYDirection(direction);
                    projectileList.add(new Ball(x+projectileXOffset, y+projectileYOffset, xDirection, yDirection));
                }
            }
            else {

                projectileList.add(new Ball(x+projectileXOffset, y+projectileYOffset, xDirection, yDirection));
                if(SettingsMenu.soundOn == 1) {
                    soundPlayer.playSound("C:\\Users\\josha\\IdeaProjects\\2019-Java-Group42\\src\\Audio\\click_x.wav");
                }
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
        if(isInvulnerable){
            invulnTimer++;
        }
        if(invulnTimer >= invulnTicks){
            setInvulnerable(false);
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


    public Rectangle getFutureBoundsY(){
        return new Rectangle((int)(x), (int)(y + dy), width, height);
    }
    public Rectangle getFutureBoundsX(){
        return new Rectangle((int)(x+dx), (int)(y), width, height);
    }
    public void stopY(){
        dy = 0;
    }
    public void stopX(){
        dx = 0;
    }
}
