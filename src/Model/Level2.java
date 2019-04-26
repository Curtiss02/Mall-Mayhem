package Model;

import java.awt.*;

public class Level2 extends Level {


    public Level2(){

        super();
        playerStart = new Point(23*32,28*32);
        map = new Map("src/maps/Level2.xml");

        Pickup healthKit = new Pickup( 300, 300, Pickup.TYPE.HEALTH);
        pickupList.add(healthKit);
    }
}
