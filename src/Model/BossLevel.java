package Model;

import java.awt.*;
import java.awt.geom.Area;
import java.util.List;
import java.util.Random;

public class BossLevel extends Level {

    public BossLevel() {
        super();
        playerStart = new Point(19*32, 26 * 32);

        map = new Map("maps/BossLevel.xml");

        pickupList.add( new Pickup(42*32, 5*32, Pickup.TYPE.HEALTH));
        pickupList.add( new Pickup(40*32, 24*32, Pickup.TYPE.HEALTH));
        pickupList.add( new Pickup(4*32, 23*32, Pickup.TYPE.HEALTH));
        pickupList.add( new Pickup(3*32, 6*32, Pickup.TYPE.HEALTH));
        spawnEnemies();

        Enemy badBoy = new Boss(576,224);
        badBoy.addPatrolPoint(new Point(34*32, 7*32));
        badBoy.addPatrolPoint(new Point (30*32, 21*32));
        badBoy.addPatrolPoint(new Point(6*32, 21*32));
        badBoy.addPatrolPoint(new Point(15*32,15*32));
        badBoy.addPatrolPoint(new Point(27*32, 15*32));
        badBoy.addPatrolPoint(new Point(27*32, 10*32));
        badBoy.addPatrolPoint(new Point(10*32, 10*32));
        badBoy.addPatrolPoint(new Point(10*32, 7*32));
        badBoy.addPatrolPoint(new Point (18*32, 7*32));
        enemyList.add(badBoy);

    }


    public void spawnEnemies() {
        List<Rectangle> spawnAreaTiles = map.getSpawnAreaCollisions();
        Area spawnArea = new Area();
        for (Rectangle tile : spawnAreaTiles) {
            Area thisTile = new Area(tile);
            spawnArea.add(thisTile);
        }
        int enemyAmount = 10;
        int delay = 0;
        for (int i = 0; i < enemyAmount; ) {
            int x = new Random().nextInt(1440);
            int y = new Random().nextInt((900));
            SecurityGuard thisDude = new SecurityGuard(x, y);
            Rectangle enemyBound = thisDude.getBounds();

            if (spawnArea.contains(enemyBound)) {
                thisDude.attackOffset(delay);


                int patrolPoints = 0;

                while (patrolPoints < 5) {
                    x = new Random().nextInt(1440);
                    y = new Random().nextInt((900));
                    Point thisPoint = new Point(x, y);
                    if (spawnArea.contains(thisPoint)) {
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








