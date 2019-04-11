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

    }


    public void init(){
        initPatrol();
    }


    public void tick(){

        x += dx;
        y += dy;

        this.sprite.setY(y);
        this.sprite.setX(x);

    }

    public void move(){
        behaviour();
    }
    private void behaviour(){
        patrol();
    }



    private List<Point> patrolPoints;
    private int pointIndex;
    private final int speed = 2;

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
                dx = speed * direction;
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
                dy = speed * direction;
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
