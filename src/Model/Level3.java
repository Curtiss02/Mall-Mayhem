package Model;

import java.awt.*;
import java.awt.geom.Area;
import java.util.List;
import java.util.Random;

public class Level3 extends Level{

    public Level3(){
        super();
        playerStart = new Point(35,13*32);
        playerExit = new Point(19*32, 3*32+5);
        map = new Map("maps/Level3.xml");


        pickupList.add( new Pickup(10*32, 23*32, Pickup.TYPE.HEALTH));
        pickupList.add( new Pickup(39*32, 23*32, Pickup.TYPE.HEALTH));

        spawnEnemies();
    }

    public void spawnEnemies(){
        List<Rectangle> spawnAreaTiles = map.getSpawnAreaCollisions();
        Area spawnArea = new Area();
        for(Rectangle tile : spawnAreaTiles){
            Area thisTile = new Area(tile);
            spawnArea.add(thisTile);
        }
        int enemyAmount = 10;
        int delay = 0;
        for(int i = 0; i < enemyAmount;) {
            int x = new Random().nextInt(1440);
            int y = new Random().nextInt((900));
            SecurityGuard thisDude = new SecurityGuard(x,y);
            Rectangle enemyBound = thisDude.getBounds();

            if(spawnArea.contains(enemyBound)){
                thisDude.attackOffset(delay);


                int patrolPoints = 0;

                while(patrolPoints < 5){
                    x = new Random().nextInt(1440);
                    y = new Random().nextInt((900));
                    Point thisPoint = new Point(x,y);
                    if(spawnArea.contains(thisPoint)){
                        thisDude.addPatrolPoint(thisPoint);
                        patrolPoints++;
                    }
                }
                delay += 60;
                enemyList.add(thisDude);
                i++;
            }
        }

    }

}
