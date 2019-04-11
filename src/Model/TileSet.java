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

    public TileSet(int width, int height ,int firstGID, String tilesetName, int tileWidth, int tileHeight, String imageSrc){

        this.imageWidth = width;
        this.imageHeight = height;
        this.firstGID = firstGID;
        this.tilesetName = tilesetName;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.imageSrc = imageSrc;
        int widthInTiles = width/tileWidth;
        this.lastGID = widthInTiles * (imageHeight / tileHeight) + lastGID - 1;
        loadImage();

    }
    private void loadImage(){

        try {
            tilesetImage = ImageIO.read(new File("src/maps" + imageSrc));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
