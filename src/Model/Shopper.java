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



    private List<Point> patrolPoints;
    private int pointIndex;


    private void initPatrol(){
        patrolPoints = new ArrayList<Point>();
        pointIndex = 0;
    }


    // This routine decides the movement of the shopper, which patrols from each of the specified points to the next
    private void patrol(){
        Point currentDest = patrolPoints.get(pointIndex);
        //Check if we have reached the current destination
        if(this.x == currentDest.x && this.y == currentDest.y){
            nextPatrolPoint();
        }

        int dist;
        if(this.x != currentDest.x){
            dist = currentDest.x - this.x;
            //Counteracts the effect of speed being greater than the distance, cauing jitter
            if(Math.abs(dist) < speed){
                dx = dist;
            }
            else {
                int direction = dist/Math.abs(dist);
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
                int direction = dist/Math.abs(dist);
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

    public void addPatrolPoint(Point p){
        patrolPoints.add(p);
    }

    private void nextPatrolPoint(){
        pointIndex++;
        if(pointIndex == patrolPoints.size()){
            pointIndex = 0;
        }
    }
}
