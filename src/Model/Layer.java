package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Layer {

    private boolean hasCollision;
    private boolean hasDamage;
    private int width;
    private int height;
    List<Integer> tiles;
    public Layer(int width, int height){
        this.width = width;
        this.height = height;
    }
    public void setCollision(boolean collision){
        this.hasCollision = collision;
    }
    public boolean hasCollison(){
        return this.hasCollision;
    }
    public void setDamage(boolean damage){
        this.hasDamage = damage;
    }
    public boolean hasDamage(){
        return hasDamage;
    }

    public void setTileData(String s){
        String[] tempList = (s.split(","));
        tiles = new ArrayList<Integer>();
        for(String tile : tempList){
            tiles.add(Integer.valueOf(tile.trim()));
        }
    }
    public int getTile(int row, int column){
        return tiles.get(row*width + column);
    }






}
