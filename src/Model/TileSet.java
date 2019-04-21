package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            tilesetImage = ImageIO.read(new File("src/maps/" + imageSrc));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public BufferedImage getTile(int GID){

        //System.out.printf("WIDTH: %d HEIGHT: %d GID: %d WIDTH IN TILES: %d\n", imageWidth, imageHeight, GID, widthInTiles);

        int yPos = (int)(Math.ceil((double)GID/widthInTiles)-1);
        System.out.println(yPos);

        int xPos = GID - (widthInTiles*yPos) - 1;

        xPos *= tileWidth;
        yPos *= tileWidth;

        System.out.printf("Tile Width:%d X:%d Y:%d GID:%d Accessig file: %s\n",tileWidth, xPos, yPos, GID, imageSrc);


        BufferedImage tile = tilesetImage.getSubimage(xPos, yPos, tileWidth, tileHeight);


        return tile;

    }



}
