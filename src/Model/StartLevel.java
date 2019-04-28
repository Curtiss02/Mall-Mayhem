package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class StartLevel extends Level {

    public StartLevel(){

        super();
        playerStart = new Point(300,300);
        playerExit = new Point(23*32, 64);
        map = new Map("maps/intro.xml");

        Pickup healthKit = new Pickup( 37*32, 10*32, Pickup.TYPE.HEALTH);
        pickupList.add(healthKit);



        //Add to list of sprites being drawn


        SpawnNPCs();

    }


    private void SpawnNPCs(){
        final int zone1XMax = 480;
        final int zone1XMin = 40;
        final int zone2XMax = 1400;
        final int zone2XMin = 992;
        final int yMax = 864;
        final int yMin = 288;
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
