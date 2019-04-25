package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class StartLevel extends Level {

    public StartLevel(){

        enemyList = new ArrayList<Enemy>();
        pickupList = new ArrayList<Pickup>();
        playerStart = new Point(300,300);
        map = new Map("src/maps/intro.xml");

        Pickup healthKit = new Pickup( 300, 300, Pickup.TYPE.HEALTH);
        pickupList.add(healthKit);



        //Add to list of sprites being drawn
        SpawnNPCs();

    }


    private void SpawnNPCs(){
        final int zone1XMax = 576;
        final int zone1XMin = 40;
        final int zone2XMax = 1400;
        final int zone2XMin = 900;
        final int yMax = 864;
        final int yMin = 256;
        final int npcCount = 30;
        for (int i = 0; i < npcCount; i++) {
            Random random = new Random();
            int x;
            int y = random.nextInt(yMax - yMin) + yMin;
            if (random.nextBoolean()) {
                x = random.nextInt(zone1XMax - zone1XMin) + zone1XMin;
            } else {
                x = random.nextInt(zone2XMax - zone2XMin) + zone2XMin;
            }
            Enemy currentNPC = new NPC_Shopper(x, y);
            enemyList.add(currentNPC);
        }
    }
}
