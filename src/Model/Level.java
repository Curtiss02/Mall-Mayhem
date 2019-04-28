package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/*
    The level class contains all non playr data in each level
    including pikcups, enemies and the map data
 */
public abstract class Level {

    protected Map map;
    protected List<Enemy> enemyList;
    protected List<Pickup> pickupList;
    protected Point playerStart;
    protected Point playerExit;

    public Level(){
        enemyList = new ArrayList<Enemy>();
        pickupList = new ArrayList<Pickup>();

    }

    public Map getMap() {
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

    public Point getPlayerExit() {
        return playerExit;
    }
}
