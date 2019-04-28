package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Level {
    protected Map map;
    protected List<Enemy> enemyList;
    protected List<Pickup> pickupList;
    protected Point playerStart;

    public Level(){
        enemyList = new ArrayList<Enemy>();
        pickupList = new ArrayList<Pickup>();

    }

    public Model.Map getMap() {
        return map;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public List<Pickup> getPickupList() {
        return pickupList;
    }

    public Point getPlayerStart() {
        return playerStart;
    }

}



