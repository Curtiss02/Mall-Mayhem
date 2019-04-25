package Model;

import java.awt.*;
import java.util.Random;

public class NPC_Shopper extends Enemy {

    public NPC_Shopper(int x, int y){
        super(x,y);
        isInvulnerable = true;
        sprite.setImage("src/img/roen_001.png");
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


    //Behaviour for aimless wandering
    private final int minWait = 120;
    private final int maxWait = 500;
    private final int minWander = 30;
    private final int maxWander = 200;


    private int waitTimer = 0;
    private int waitValue;
    private boolean isWaiting = false;
    Point currentDestination;

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

    private void meander(){
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
}
