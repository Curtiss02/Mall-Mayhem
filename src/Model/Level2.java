package Model;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.List;

public class Level2 extends Level {


    public Level2(){

        super();
        playerStart = new Point(23*32,27*32);
        playerExit = new Point(43*32,10*32);
        map = new Map("maps/Level2.xml");

        spawnEnemies();
    }

    private void spawnEnemies(){
        List<Rectangle> spawnAreaTiles = map.getSpawnAreaCollisions();
        Area spawnArea = new Area();
        for(Rectangle tile : spawnAreaTiles){
            Area thisTile = new Area(tile);
            spawnArea.add(thisTile);
        }

        int enemyAmount = 30;
        for(int i = 0; i < enemyAmount;) {
            int x = new Random().nextInt(1440);
            int y = new Random().nextInt((900));
            Enemy thisShopper = new Shopper(x, y);
            Rectangle enemyBound = thisShopper.getBounds();
            if (spawnArea.contains(enemyBound)) {
                enemyList.add(thisShopper);
                i++;
            }
        }
        SecurityGuard sec1 = new SecurityGuard(100,208);

        sec1.addPatrolPoint(new Point(100,208));
        sec1.addPatrolPoint(new Point(1000,208));

        SecurityGuard sec2 = new SecurityGuard(500, 208);
        sec2.addPatrolPoint(new Point(100,208));
        sec2.addPatrolPoint(new Point (1000,208));
        sec2.attackOffset(60);

        SecurityGuard sec3 = new SecurityGuard(1000, 208);
        sec3.addPatrolPoint(new Point(100,208));
        sec3.addPatrolPoint(new Point (1000,208));
        sec3.attackOffset(120);

        enemyList.add(sec1);
        enemyList.add(sec2);
        enemyList.add(sec3);
    }
}
