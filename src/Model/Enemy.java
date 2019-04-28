package Model;

import View.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Enemy extends Character{

    protected int damage;
    private int invulnTimer = 0;
    protected int invulnTicks = 10;
    protected int speed;
    protected boolean isStuck = false;
    protected int stuckCounter = 0;
    static double playerX;
    static double playerY;

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

        this.sprite.setY((int)y);
        this.sprite.setX((int)x);
    }

    public void setStuck(boolean stuck) {
        isStuck = stuck;
    }

    protected List<Point> patrolPoints;
    protected int pointIndex;


    protected void initPatrol(){
        patrolPoints = new ArrayList<Point>();
        pointIndex = 0;
    }


    // This routine decides the movement of the shopper, which patrols from each of the specified points to the next
    protected void patrol(){
        Point currentDest = patrolPoints.get(pointIndex);
        //Check if we have reached the current destination
        if(this.x == currentDest.x && this.y == currentDest.y){
            nextPatrolPoint();
        }
        if(stuckCounter > 30){
            nextPatrolPoint();
        }

        double dist;
        if(this.x != currentDest.x){
            dist = currentDest.x - this.x;
            //Counteracts the effect of speed being greater than the distance, cauing jitter
            if(Math.abs(dist) < speed){
                dx = dist;
            }
            else {
                int direction = (int)(dist/Math.abs(dist));
                switch (direction){
                    case 1:
                        moveRight();
                        break;
                    case -1:
                        moveLeft();
                        break;
                }
            }
        }
        else{
            dx = 0;
        }
        if(this.y != currentDest.y){
            dist = currentDest.y - this.y;
            //Counteracts the effect of speed being greater than the distance, cauing jitter
            if(Math.abs(dist) < speed){
                dy = dist;
            }
            else {
                int direction = (int)(dist/Math.abs(dist));
                switch (direction){
                    case 1:
                        moveDown();
                        break;
                    case -1:
                        moveUp();
                        break;
                }
            }
        }
        else{
            dy = 0;
        }
    }

    protected void addPatrolPoint(Point p){
        patrolPoints.add(p);
    }

    protected void nextPatrolPoint(){
        pointIndex++;
        if(pointIndex == patrolPoints.size()){
            pointIndex = 0;
        }
    }




    //Behaviour for aimless wandering
    protected final int minWait = 120;
    protected final int maxWait = 500;
    protected final int minWander = 30;
    protected final int maxWander = 200;


    protected int waitTimer = 0;
    protected int waitValue;
    protected boolean isWaiting = false;
    protected Point currentDestination;

    protected void meander(){
        if(currentDestination == null){
            getNewMeanderPoint();
        }
        if(stuckCounter > 30){

            getNewMeanderPoint();
        }
        //Check if we have reached the current destination
        if((this.x == currentDestination.x) && (this.y == currentDestination.y)){
            getNewMeanderPoint();
            //System.out.println("REACDHED DEST");
            isWaiting = true;
            Random random = new Random();
            waitValue = random.nextInt(maxWait - minWait) + minWait;

            //System.out.println("Wait value: " + waitValue);

        }


        //System.out.printf("X:%d Y:%d gX:%d Gy:%d\n", x, y, currentDestination.x, currentDestination.y);
        double dist;
        if(!isWaiting) {
            if (this.x != currentDestination.x) {
                dist = currentDestination.x - this.x;
                //Counteracts the effect of speed being greater than the distance, cauing jitter
                if (Math.abs(dist) < speed) {
                    dx = dist;
                } else {
                    int direction = (int) (dist / Math.abs(dist));
                    switch (direction) {
                        case 1:
                            moveRight();
                            break;
                        case -1:
                            moveLeft();
                            break;
                    }
                }
            } else {
                dx = 0;
            }
            if (this.y != currentDestination.y) {
                dist = currentDestination.y - this.y;
                //Counteracts the effect of speed being greater than the distance, cauing jitter
                if (Math.abs(dist) < speed) {
                    dy = dist;
                } else {
                    int direction = (int)(dist / Math.abs(dist));
                    switch (direction) {
                        case 1:
                            moveDown();
                            break;
                        case -1:
                            moveUp();
                            break;
                    }
                }
            } else {
                dy = 0;
            }
        }

    }
    public void getNewMeanderPoint(){
        Random random = new Random();
        int newDist = random.nextInt(maxWander-minWander) + minWander;
        //Choose positive or negative
        if(random.nextBoolean()){
            newDist = -newDist;
        }

        //Choose x or y movement
        if(random.nextBoolean()){
            currentDestination = new Point((int)this.x,(int)this.y + newDist);
        }
        else{
            currentDestination = new Point((int)this.x + newDist, (int)this.y);
        }

    }


    protected double getDistance(double x1, double x2, double y1, double y2){
        double distance = Math.sqrt(((x1-x2)*(x1-x2)) + ((y1-y2)*(y1-y2)));
        return distance;
    }



    public static void setPlayerX(double playerX) {
        Enemy.playerX = playerX;
    }

    public static void setPlayerY(double playerY) {
        Enemy.playerY = playerY;
    }


    public void swarm(double targetX, double targetY){
        double distance = getDistance(x,targetX,y,targetY);
        double xScale = (targetX - x)/distance;
        double yScale = (targetY - y)/distance;

        dx = xScale * speed;
        dy = yScale * speed;

    }
}