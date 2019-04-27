package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TileSet {
    private int imageWidth;
    private int imageHeight;
    private int firstGID;
    private String tilesetName;
    private int tileWidth;
    private int tileHeight;
    private String imageSrc;
    private int lastGID;
    private BufferedImage tilesetImage;
    private int widthInTiles;

    public int getFirstGID() {
        return firstGID;
    }

    public int getLastGID() {
        return lastGID;
    }

    public TileSet(int width, int height , int firstGID, String tilesetName, int tileWidth, int tileHeight, String imageSrc){

        this.imageWidth = width;
        this.imageHeight = height;
        this.firstGID = firstGID;
        this.tilesetName = tilesetName;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.imageSrc = imageSrc;
        this.widthInTiles = width/tileWidth;
        this.imageSrc = imageSrc;
        this.lastGID = widthInTiles * (imageHeight / tileHeight) + lastGID - 1;
        loadImage();

    }
    private void loadImage(){

        try {
            URL url = this.getClass().getClassLoader().getResource("maps/" + imageSrc);
            tilesetImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public BufferedImage getTile(int GID){

        //System.out.printf("WIDTH: %d HEIGHT: %d GID: %d WIDTH IN TILES: %d\n", imageWidth, imageHeight, GID, widthInTiles);

        int yPos = (int)(Math.ceil((double)GID/widthInTiles)-1);


        int xPos = GID - (widthInTiles*yPos) - 1;

        xPos *= tileWidth;
        yPos *= tileWidth;




        BufferedImage tile = tilesetImage.getSubimage(xPos, yPos, tileWidth, tileHeight);


        return tile;

    }



}
