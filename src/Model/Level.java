package Model;

import javax.imageio.ImageIO;
import javax.xml.parsers.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.*;

public class Level {

    private BufferedImage backgroundLayer;
    private BufferedImage collisionLayer;
    private BufferedImage topLayer;
    private List<TileSet> tilesets;

    private int tileWidth;
    private int levelWidth;
    private int levelHeight;

    private int[][] backgroundTileData;
    private int[][] collisionTileData;
    private int[][] topTileData;


    public Level(String xmlFile){
        loadXML(xmlFile);
    }

    public void loadTileset(){

    }
    private void loadXML(String xmlFile) {

/*
        if(tileSet == null) {
            System.out.println("No Tileset loaded");
        }
        else{
*/
        try {

                //Read in map data XML file
                File inputFile = new File(xmlFile);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();

                Element map = (Element)doc.getElementsByTagName("map").item(0);

                tileWidth = Integer.valueOf(map.getAttribute("tilewidth"));
                levelWidth = Integer.valueOf(map.getAttribute("width"));
                levelHeight = Integer.valueOf(map.getAttribute("height"));

                NodeList tilesetNodes = doc.getElementsByTagName("tileset");
                tilesets = new ArrayList<TileSet>();


                for(int i = 0; i < tilesetNodes.getLength(); i++){
                    System.out.println("Accessing" + i);
                    Node tempNode = tilesetNodes.item(0);
                    Element tilesetNode = (Element)tilesetNodes.item(i);



                    Element image =  (Element)tilesetNode.getElementsByTagName("image").item(0);




                    int imageWidth = Integer.valueOf(image.getAttribute("width"));
                    int imageHeight = Integer.valueOf(image.getAttributes().getNamedItem("height").getNodeValue());
                    String imageSrc = image.getAttribute("source");




                    int firstGID = Integer.valueOf(tilesetNode.getAttribute("firstgid"));
                    String tilesetName = tilesetNode.getAttribute("name");

                    int tileWidth = Integer.valueOf(tilesetNode.getAttribute("tilewidth"));
                    int tileHeight = Integer.valueOf(tilesetNode.getAttribute("tileheight"));


                    TileSet currentTileset = new TileSet(imageWidth, imageHeight, firstGID, tilesetName, tileWidth, tileHeight, imageSrc);
                    tilesets.add(currentTileset);
                }


                NodeList layers =  map.getElementsByTagName("layer");

                for(int i = 0; i < layers.getLength(); i++){
                    Element currentLayer = (Element)layers.item(i);
                    String layerName = currentLayer.getAttribute("name");

                    BufferedImage currentImage = new BufferedImage(levelWidth*tileWidth, levelHeight*tileWidth, BufferedImage.TYPE_INT_ARGB);
                    int[][] tileData;

                    //Parse each of the tiles in the layer out of XML ad into an arrayList
                    NodeList tiles = currentLayer.getElementsByTagName("tile");
                    List<Integer> linearTiles = new ArrayList<Integer>();

                    for(int tileNum = 0; tileNum < tiles.getLength(); tileNum++) {
                        //Get current tile ID and convert to integer provided th value is not null
                        //Add the current tile ID to a linear list
                        String gid = ((Element) (tiles.item(tileNum))).getAttribute("gid");
                        if (!gid.isEmpty()) {
                            linearTiles.add(tileNum, Integer.valueOf(gid));
                        } else {
                            linearTiles.add(tileNum, 0);
                        }

                    }

                    tileData = new int[levelWidth][];
                    for(int tileX = 0; tileX  < levelWidth; tileX++){

                        tileData[tileX] = new int[levelHeight];

                        System.out.printf("[");

                        for(int tileY = 0; tileY < levelHeight; tileY++){
                            tileData[tileX][tileY] = linearTiles.get((tileX+(tileY*levelWidth)));

                        }
                        System.out.printf("]\n");

                    }

                    //Draw the fucking image please god i hope this works or im gonna cry

                    Graphics gIMG = currentImage.getGraphics();
                    for(int tileX = 0; tileX < levelWidth; tileX++ ){

                        for(int tileY = 0; tileY < levelHeight; tileY++){

                            int GID = tileData[tileX][tileY];

                            //NO need to draw anything if the current tile is blank
                            if(GID == 0) continue;
                            TileSet currentTileset = tilesets.get(0);
                            for(TileSet testSet : tilesets){
                                if(GID >= testSet.getFirstGID()-1 && GID <= testSet.getLastGID()){
                                    currentTileset = testSet;
                                    break;
                                }
                            }
                            GID -= currentTileset.getFirstGID()-1;
                            int destX = tileX * tileWidth;
                            int destY = tileY * tileWidth;
                            //Draw the tile

                            gIMG.drawImage(currentTileset.getTile(GID), destX, destY, null);


                        }
                    }

                    switch (layerName){
                        case "Background":
                            backgroundLayer = currentImage;
                            backgroundTileData = tileData;
                            break;
                        case "Collision":
                            collisionLayer = currentImage;
                            collisionTileData = tileData;
                            break;
                        case "Top":
                            topLayer = currentImage;
                            topTileData = tileData;
                            break;
                        default:
                            break;
                    }


                    gIMG.dispose();
                }

                    File outputfile = new File("saved.png");
                    ImageIO.write(backgroundLayer, "png", outputfile);


;



            } catch (Exception e) {
                e.printStackTrace();
            }
            }
    //}
        private void buildLayer(BufferedImage layerImage, int[][] layerData){

        }

        public void drawBackground(Graphics2D g){
            g.drawImage(backgroundLayer, 0 , 0, null);
            g.drawImage(collisionLayer, 0, 0, null);
        }

}



